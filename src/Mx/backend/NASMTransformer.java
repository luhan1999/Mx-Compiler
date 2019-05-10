package Mx.backend;

import Mx.Configuration;
import java.util.*;
import Mx.ir.*;
import static Mx.Nasm.NASMRegisterSet.*;
/*  Copy from DYY
    1. Stack Frame
        low address
    | ...             |
    | temporary[n]    | <--- callee's rsp
    | ...             |
    | temporary[1]    | [rbp - 16]
    | temporary[0]    | [rbp - 8]
    | caller's rbp    | <--- callee's rbp
    | return address  | [rbp + 8]
    | arg[6]          | [rbp + 16]
    | arg[7]          | [rbp + 24]
    | ...             |
        high address
    call(a,b,c,d,e,f,g,h,i)
    2. The first 6 arguments are passed by registers:
    arg[0]  arg[1]  arg[2]  arg[3]  arg[4]  arg[5]
    rdi     rsi     rdx     rcx     r8      r9
     */

public class NASMTransformer {
    private IRRoot ir;

    public NASMTransformer(IRRoot ir) {
        this.ir = ir;
    }

    private class FuncInfo {
        List<PhysicalRegister> usedCallerSaveRegs = new ArrayList<>();
        List<PhysicalRegister> usedCalleeSaveRegs = new ArrayList<>();
        Set<PhysicalRegister> recursiveUsedRegs = new HashSet<>();
        Map<StackSlot, Integer> stackSlotOffsetMap = new HashMap<>();
        int numExtraArgs, numStackSlot = 0;
    }

    private Map<IRFunction, FuncInfo> funcInfoMap = new HashMap<>();

    private void buildfuncInfoMap(){
        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = new FuncInfo();
            for (PhysicalRegister preg : irFunction.getUsedPhysicalGeneralRegs()) {
                if (preg.isCalleeSave()) funcInfo.usedCalleeSaveRegs.add(preg);
                if (preg.isCallerSave()) funcInfo.usedCallerSaveRegs.add(preg);
            }

            funcInfo.usedCalleeSaveRegs.add(rbx);
            funcInfo.usedCalleeSaveRegs.add(rbp);

            funcInfo.numStackSlot = irFunction.getStackSlots().size();
            for (int i = 0; i < funcInfo.numStackSlot; ++i) {
                funcInfo.stackSlotOffsetMap.put(irFunction.getStackSlots().get(i), i * 8);
            }
            // for rsp alignment :rsp must be aligned to a 16-byte boundary
            if ((funcInfo.usedCalleeSaveRegs.size() + funcInfo.numStackSlot) % 2 == 0)  funcInfo.numStackSlot++;

            funcInfo.numExtraArgs = irFunction.getArgVRegList().size() - 6;
            if (funcInfo.numExtraArgs < 0) funcInfo.numExtraArgs = 0;

            int extraArgOffset = (funcInfo.usedCalleeSaveRegs.size() + funcInfo.numStackSlot + 1) * 8; // return address
            for (int i = 6; i < irFunction.getArgVRegList().size(); ++i) {
                funcInfo.stackSlotOffsetMap.put(irFunction.getArgsStackSlotMap().get(irFunction.getArgVRegList().get(i)), extraArgOffset);
                extraArgOffset += 8;
            }
            funcInfoMap.put(irFunction, funcInfo);
        }

        for (IRFunction builtinFunc : ir.getBuiltInFuncs().values()) {
            funcInfoMap.put(builtinFunc, new FuncInfo());
        }
        for (IRFunction irFunction : funcInfoMap.keySet()) {
            FuncInfo funcInfo = funcInfoMap.get(irFunction);
            funcInfo.recursiveUsedRegs.addAll(irFunction.getUsedPhysicalGeneralRegs());
            for (IRFunction calleeFunc : irFunction.recursiveCalleeSet) {
                funcInfo.recursiveUsedRegs.addAll(calleeFunc.getUsedPhysicalGeneralRegs());
            }
        }
    }

    public void run() {
        buildfuncInfoMap();

        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = funcInfoMap.get(irFunction);
            // transform function entry
            BasicBlock entryBB = irFunction.getStartBB();
            IRInstruction firstInst = entryBB.getFirstInst();

            //Callee should be first push and finally pop
            for (PhysicalRegister preg : funcInfo.usedCalleeSaveRegs)
                firstInst.prependInst(new IRPush(entryBB, preg));

            if (funcInfo.numStackSlot > 0)
                firstInst.prependInst(new IRBinaryOperation(entryBB, rsp, IRBinaryOperation.IRBinaryOp.SUB, rsp, new IntImmediate(funcInfo.numStackSlot * 8)));
            firstInst.prependInst(new IRMove(entryBB, rbp, rsp));

            for (BasicBlock bb : irFunction.getReversePostOrder()) {
                for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                    if (inst instanceof IRFunctionCall) {
                        IRFunction calleeFunc = ((IRFunctionCall) inst).getFunc();
                        FuncInfo calleeInfo = funcInfoMap.get(calleeFunc);
                        // push caller save registers which would be changed by callee
                        int numPushCallerSave = 0;
                        for (PhysicalRegister preg : funcInfo.usedCallerSaveRegs) {
                            if (preg.isArg6() && preg.getArg6Idx() < irFunction.getArgVRegList().size()) continue;
                            if (calleeInfo.recursiveUsedRegs.contains(preg)) {
                                ++numPushCallerSave;
                                inst.prependInst(new IRPush(inst.getParentBB(), preg));
                            }
                        }

                        // push argument registers
                        int numPushArg6Regs = irFunction.getArgVRegList().size() <= 6 ? irFunction.getArgVRegList().size() : 6;
                        for (int i = 0; i < numPushArg6Regs; ++i)
                            inst.prependInst(new IRPush(inst.getParentBB(), arg6.get(i)));
                        numPushCallerSave += numPushArg6Regs;

                        // set arguments
                        boolean extraPush = false;
                        List<RegValue> args = ((IRFunctionCall) inst).getArgs();
                        List<Integer> arg6BakOffset = new ArrayList<>();
                        Map<PhysicalRegister, Integer> arg6BakOffsetMap = new HashMap<>();

                        // for rsp alignment
                        if ((numPushCallerSave + calleeInfo.numExtraArgs) % 2 == 1) {
                            extraPush = true;
                            inst.prependInst(new IRPush(inst.getParentBB(), new IntImmediate(0)));
                        }
                        for (int i = args.size() - 1; i > 5; --i) {
                            if (args.get(i) instanceof StackSlot) {
                                inst.prependInst(new IRLoad(inst.getParentBB(), rax, Configuration.getRegSize(), rbp, funcInfo.stackSlotOffsetMap.get(args.get(i))));
                                inst.prependInst(new IRPush(inst.getParentBB(), rax));
                            } else {
                                inst.prependInst(new IRPush(inst.getParentBB(), args.get(i)));
                            }
                        }

                        int bakOffset = 0;
                        for (int i = 0; i < 6; ++i) {
                            if (args.size() <= i) break;
                            if (args.get(i) instanceof PhysicalRegister && ((PhysicalRegister) args.get(i)).isArg6() && ((PhysicalRegister) args.get(i)).getArg6Idx() < args.size()) {
                                PhysicalRegister preg = (PhysicalRegister) args.get(i);
                                if (arg6BakOffsetMap.containsKey(preg)) {
                                    arg6BakOffset.add(arg6BakOffsetMap.get(preg));
                                } else {
                                    arg6BakOffset.add(bakOffset);
                                    arg6BakOffsetMap.put(preg, bakOffset);
                                    inst.prependInst(new IRPush(inst.getParentBB(), preg));
                                    ++bakOffset;
                                }
                            } else {
                                arg6BakOffset.add(-1);
                            }
                        }

                        for (int i = 0; i < 6; ++i) {
                            if (args.size() <= i) break;
                            if (arg6BakOffset.get(i) == -1) {
                                if (args.get(i) instanceof StackSlot) {
                                    inst.prependInst(new IRLoad(inst.getParentBB(), rax, 8, rbp, funcInfo.stackSlotOffsetMap.get(args.get(i))));
                                    inst.prependInst(new IRMove(inst.getParentBB(), arg6.get(i), rax));
                                } else {
                                    inst.prependInst(new IRMove(inst.getParentBB(), arg6.get(i), args.get(i)));
                                }
                            } else {
                                inst.prependInst(new IRLoad(inst.getParentBB(), arg6.get(i), 8, rsp, 8 * (bakOffset - arg6BakOffset.get(i) - 1)));
                            }
                        }

                        if (bakOffset > 0)
                            inst.prependInst(new IRBinaryOperation(inst.getParentBB(), rsp, IRBinaryOperation.IRBinaryOp.ADD, rsp, new IntImmediate(bakOffset * 8)));

                        // get return value
                        if (((IRFunctionCall) inst).getDest() != null) inst.appendInst(new IRMove(inst.getParentBB(), ((IRFunctionCall) inst).getDest(), rax));

                        // restore caller save registers
                        for (PhysicalRegister preg : funcInfo.usedCallerSaveRegs) {
                            if (preg.isArg6() && preg.getArg6Idx() < irFunction.getArgVRegList().size()) continue;
                            if (calleeInfo.recursiveUsedRegs.contains(preg)) {
                                inst.appendInst(new IRPop(inst.getParentBB(), preg));
                            }
                        }

                        // restore argument registers
                        for (int i = 0; i < numPushArg6Regs; ++i)
                            inst.appendInst(new IRPop(inst.getParentBB(), arg6.get(i)));

                        // remove extra arguments
                        if (calleeInfo.numExtraArgs > 0 || extraPush) {
                            int numPushArg = extraPush ? calleeInfo.numExtraArgs + 1 : calleeInfo.numExtraArgs;
                            inst.appendInst(new IRBinaryOperation(inst.getParentBB(), rsp, IRBinaryOperation.IRBinaryOp.ADD, rsp, new IntImmediate(numPushArg * Configuration.getRegSize())));
                        }
                    } else if (inst instanceof IRHeapAlloc) {
                        // push caller save registers which would be changed by callee
                        int numPushCallerSave = 0;
                        //inst.prependInst(new IRPush(inst.getParentBB(), rdi));
                        for (PhysicalRegister preg : funcInfo.usedCallerSaveRegs) {
                            ++numPushCallerSave;
                            // could be optimized known which reg would not be changed by malloc
                            inst.prependInst(new IRPush(inst.getParentBB(), preg));
                        }
                        // set arg
                        inst.prependInst(new IRMove(inst.getParentBB(), rdi, ((IRHeapAlloc) inst).getAllocSize()));
                        // for rsp alignment
                        if (numPushCallerSave % 2 == 1) {
                            inst.prependInst(new IRPush(inst.getParentBB(), new IntImmediate(0)));
                        }
                        // get return value
                        inst.appendInst(new IRMove(inst.getParentBB(), ((IRHeapAlloc) inst).getDest(), rax));
                        // restore caller save registers
                        //inst.appendInst(new IRPop(inst.getParentBB(), rdi));
                        for (PhysicalRegister preg : funcInfo.usedCallerSaveRegs) {
                            // could be optimized known which reg would not be changed by malloc
                            inst.appendInst(new IRPop(inst.getParentBB(), preg));
                        }
                        // restore rsp
                        if (numPushCallerSave % 2 == 1) {
                            inst.appendInst(new IRBinaryOperation(inst.getParentBB(), rsp, IRBinaryOperation.IRBinaryOp.ADD, rsp, new IntImmediate(Configuration.getRegSize())));
                        }

                    } else if (inst instanceof IRLoad) {
                        if (((IRLoad) inst).getAddr() instanceof StackSlot) {
                            ((IRLoad) inst).setAddrOffset(funcInfo.stackSlotOffsetMap.get(((IRLoad) inst).getAddr()));
                            ((IRLoad) inst).setAddr(rbp);
                        }
                    } else if (inst instanceof IRStore) {
                        if (((IRStore) inst).getAddr() instanceof StackSlot) {
                            ((IRStore) inst).setAddrOffset(funcInfo.stackSlotOffsetMap.get(((IRStore) inst).getAddr()));
                            ((IRStore) inst).setAddr(rbp);
                        }
                    } else if (inst instanceof IRMove) {
                        // remove useless move: a <- a
                        if (((IRMove) inst).getLhs() == ((IRMove) inst).getRhs()) {
                            inst.remove();
                        }
                    }
                }
            }

            IRReturn retInst = irFunction.getRetInstList().get(0);
            if (retInst.getRetValue() != null) {
                retInst.prependInst(new IRMove(retInst.getParentBB(), rax, retInst.getRetValue()));
            }

            // transform function exit
            BasicBlock exitBB = irFunction.getEndBB();
            IRInstruction lastInst = exitBB.getLastInst();
            if (funcInfo.numStackSlot > 0)
                lastInst.prependInst(new IRBinaryOperation(entryBB, rsp, IRBinaryOperation.IRBinaryOp.ADD, rsp, new IntImmediate(funcInfo.numStackSlot * Configuration.getRegSize())));
            for (int i = funcInfo.usedCalleeSaveRegs.size() - 1; i >= 0; --i) {
                lastInst.prependInst(new IRPop(entryBB, funcInfo.usedCalleeSaveRegs.get(i)));
            }
        }

        InstructionOptimizer();
    }

    private void InstructionOptimizer(){
        for (IRFunction func : ir.getFuncs().values()){
            for (BasicBlock bb : func.getReversePostOrder()){
                for (IRInstruction inst = bb.getFirstInst(), lastInst = null; inst != null; inst = inst.getNextInst()){
                    boolean remove = false;
                    if (inst instanceof IRMove){
                        IRMove moveInst = (IRMove) inst;
                        if (moveInst.getLhs() == moveInst.getRhs()) remove = true;
                        else if (lastInst instanceof IRMove && moveInst.getLhs() == ((IRMove) lastInst).getRhs()
                                 && moveInst.getRhs() == ((IRMove) lastInst).getLhs())
                                remove = true;
                    } else if (inst instanceof IRLoad) {
                        if (lastInst instanceof IRStore &&
                                ((IRStore) lastInst).getValue() == ((IRLoad) inst).getDest() &&
                                ((IRStore) lastInst).getAddr() == ((IRLoad) inst).getAddr() &&
                                ((IRStore) lastInst).getAddrOffset() == ((IRLoad) inst).getAddrOffset() &&
                                ((IRStore) lastInst).getSize() == ((IRLoad) inst).getSize())
                            remove = true;
                    } else if (inst instanceof IRStore) {
                        if (lastInst instanceof IRLoad &&
                                ((IRLoad) lastInst).getDest() == ((IRStore) inst).getValue() &&
                                ((IRLoad) lastInst).getAddr() == ((IRStore) inst).getAddr() &&
                                ((IRLoad) lastInst).getAddrOffset() == ((IRStore) inst).getAddrOffset() &&
                                ((IRLoad) lastInst).getSize() == ((IRStore) inst).getSize())
                            remove = true;
                    }
                    if (remove) inst.remove();
                    else lastInst = inst;
                }
            }
        }
    }
}


