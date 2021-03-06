package Mx.ir;

import Mx.scope.FuncEntity;
import Mx.scope.VarEntity;

import java.util.*;

public class IRFunction {
    private FuncEntity funcEntity;
    private BasicBlock startBB = null, endBB = null;
    private List<VirtualRegister> argVRegList = new ArrayList<>();
    private List<BasicBlock> reversePostOrder = null;
    private List<BasicBlock> reversePreOrder = null;
    private String name,builtInCallLabel;
    private boolean recursiveCall = false;
    private List<IRReturn> retInstList = new ArrayList<>();
    private Set<PhysicalRegister> usedPhysicalGeneralRegs = new HashSet<>();
    private boolean isBuiltIn = false;

    public  String getName() { return name;}
    public List<VarEntity> getParameters() { return funcEntity.getParameters(); }


    public IRFunction(FuncEntity funcEntity){
        this.funcEntity = funcEntity;
        name = funcEntity.getName();
        if (funcEntity.isMember()) {
            name = IRRoot.irMemberFuncName(funcEntity.getClassName(), name);
        }
    }

    public IRFunction(String name, String builtInCallLabel){
        this.name = name;
        this.builtInCallLabel = builtInCallLabel;
        this.funcEntity = null;
        this.isBuiltIn = true;
    }

    public IRFunction() {}

    public Set<IRFunction> calleeSet = new HashSet<>();
    public Set<IRFunction> recursiveCalleeSet = new HashSet<>();

    public void updateCalleeSet() {
        calleeSet.clear();
        for (BasicBlock bb: getReversePostOrder()){
            for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()){
                if (inst instanceof IRFunctionCall){
                    calleeSet.add(((IRFunctionCall) inst).getFunc());
                }
            }
        }
    }

    public void setArgVRegList(List<VirtualRegister> argVRegList) { this.argVRegList = argVRegList; }
    public List<VirtualRegister> getArgVRegList() { return argVRegList;}
    public void addArgVReg(VirtualRegister vreg) { argVRegList.add(vreg);}

    public BasicBlock genFirstBB() {
        startBB = new BasicBlock(this, funcEntity.getName() + "_entry");
        return startBB;
    }

    public BasicBlock getStartBB() { return startBB; }
    public void setStartBB(BasicBlock startBB) { this.startBB = startBB; }

    public BasicBlock getEndBB() { return endBB;}

    public void setEndBB(BasicBlock endBB) { this.endBB = endBB;    }

    public FuncEntity getFuncEntity() { return funcEntity;}

    private Set<BasicBlock> dfsVisited = null;

    private void dfsPostOrder(BasicBlock bb){
        if (dfsVisited.contains(bb)) return;
        dfsVisited.add(bb);
        for (BasicBlock nextBB : bb.getNextBBSet()){
            dfsPostOrder(nextBB);
        }
        reversePostOrder.add(bb);
    }

    private void dfsPreOrder(BasicBlock bb){
        if (dfsVisited.contains(bb)) return;
        dfsVisited.add(bb);
        reversePreOrder.add(bb);
        for (BasicBlock nextBB : bb.getNextBBSet()) {
            dfsPreOrder(nextBB);
        }
    }

    public List<BasicBlock> getReversePostOrder(){
        if (reversePostOrder == null){
            calcReversePostOrder();
        }
        return reversePostOrder;
    }

    public void calcReversePostOrder() {
        reversePostOrder = new ArrayList<>();
        dfsVisited = new HashSet<>();
        dfsPostOrder(startBB);
        dfsVisited = null;
        for (int i = 0; i < reversePostOrder.size(); ++i){
            reversePostOrder.get(i).setPostOrderIdx(i);
        }
        Collections.reverse(reversePostOrder);
    }

    public List<BasicBlock> getReversePreOrder() {
        if (reversePreOrder == null) {
            calcReversePreOrder();
        }
        return reversePreOrder;
    }

    public void calcReversePreOrder() {
        reversePreOrder = new ArrayList<>();
        dfsVisited = new HashSet<>();
        dfsPreOrder(startBB);
        dfsVisited = null;
        for (int i = 0; i < reversePreOrder.size(); ++i) {
            reversePreOrder.get(i).setPreOrderIdx(i);
        }
        Collections.reverse(reversePreOrder);
    }

    private boolean isMemFunc = false;

    public  void setMemFunc(boolean memFunc) { isMemFunc = memFunc; }

    public boolean isMemFunc() { return isMemFunc; }

    public void setRecursiveCall(boolean recursiveCall)  {this.recursiveCall = recursiveCall;}

    public boolean isRecursiveCall() { return recursiveCall;}

    public List<IRReturn> getRetInstList() { return retInstList; }

    private List<StackSlot> stackSlots = new ArrayList<>();

    public List<StackSlot> getStackSlots() { return stackSlots;}

    public void accept(IRVisitor visitor) { visitor.visit(this);}

    private Map<VirtualRegister, StackSlot> argsStackSlotMap = new HashMap<>();

    public Map<VirtualRegister, StackSlot> getArgsStackSlotMap() { return argsStackSlotMap;}

    public Set<PhysicalRegister> getUsedPhysicalGeneralRegs() {
        return usedPhysicalGeneralRegs;
    }

    public boolean isBuiltIn() { return isBuiltIn; }

    public String getBuiltInCallLabel() { return builtInCallLabel;}
}
