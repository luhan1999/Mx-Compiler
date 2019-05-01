package Mx.frontend;

import Mx.Configuration;
import Mx.ErrorThrow.CompilerError;
import Mx.ir.*;
import Mx.Ast.*;
import Mx.scope.*;
import Mx.Type.*;

import java.util.ArrayList;
import java.util.List;


public class IRBuilder extends BaseScopeScanner{
    private final String INIT_FUNC_NAME = "__init_func";
    private IRRoot ir = new IRRoot();
    private IRFunction currentFunc = null;
    private BasicBlock currentBB = null;
    private Scope globalScope, currentScope;
    private List<GlobalVarInit> gloabalInitList = new ArrayList<>();
    private boolean isFuncArgDecl = false, wantAddr = false;
    private BasicBlock currentLoopStepBB, currentLoopAfterBB;
    private String currentClassName = null;
    private boolean assignLhs = false, uselessStatic = false;

    public IRRoot getIR() { return ir; }

    public IRBuilder(Scope globalScope) {this.globalScope = globalScope;}

    private FuncDeclNode makeInitFunc(){
        List<Node> stmts = new ArrayList<>();
        for (GlobalVarInit init: gloabalInitList){
            IdentifierExprNode lhs = new IdentifierExprNode(init.getName(), null);
            VarEntity varEntity = (VarEntity) globalScope.get(Scope.varKey(init.getName()));
            lhs.setVarEntity(varEntity);
            AssignExprNode assignExpr = new AssignExprNode(lhs, init.getInitExpr(), null);
            stmts.add(new ExprStmtNode(assignExpr, null));
        }

        BlockStmtNode body = new BlockStmtNode(stmts, null);
        body.initScope((globalScope));
        TypeNode retType = new TypeNode(VoidType.getInstance(),null);
        FuncDeclNode funcNode = new FuncDeclNode(retType, INIT_FUNC_NAME, new ArrayList<>(), body, null);
        FuncEntity funcEntity = new FuncEntity(funcNode);
        globalScope.put(Scope.funcKey(INIT_FUNC_NAME), funcEntity);
        IRFunction newIRFunc = new IRFunction(funcEntity);
        ir.addFunc(newIRFunc);
        return funcNode;
    }

    private void processIRAssign(RegValue dest, int addrOffset, ExprNode rhs, int size, boolean needMemop){
        if (rhs.getTrueBB() != null) {
            BasicBlock mergeBB = new BasicBlock(currentFunc, null);
        }
    }

    @Override
    public void visit(ProgramNode node){
        currentScope = globalScope;

        //pre-scanning for functions
        for (DeclNode decl : node.getDecls()){
            if (decl instanceof  FuncDeclNode){
                FuncEntity funcEntity = (FuncEntity) currentScope.get(Scope.funcKey(decl.getName()));
                IRFunction newIRFunc = new IRFunction(funcEntity);
                ir.addFunc(newIRFunc);
            } else if (decl instanceof VarDeclNode){
                decl.accept(this);
            } else if (decl instanceof ClassDeclNode){
                ClassEntity entity = (ClassEntity) currentScope.get(Scope.classKey(decl.getName()));
                currentScope = entity.getScope();
                for (FuncDeclNode memberFunc: ((ClassDeclNode) decl).getFuncMember()){
                    FuncEntity funcEntity = (FuncEntity) currentScope.get(Scope.funcKey(memberFunc.getName()));
                    IRFunction newIRFunc = new IRFunction(funcEntity);
                    ir.addFunc(newIRFunc);
                }
                currentScope = currentScope.getParent();
            }
        }

        FuncDeclNode initFunc = makeInitFunc();
        initFunc.accept(this);
        for (DeclNode decl : node.getDecls()) {
            if (decl instanceof VarDeclNode) {
                // no actions to take
            } else if (decl instanceof ClassDeclNode) {
                decl.accept(this);
            } else if (decl instanceof FuncDeclNode) {
                decl.accept(this);
            } else {
                throw new CompilerError(decl.location(), "Invalid declaration node type");
            }
        }

        for (IRFunction irFunction : ir.getFuncs().values()) {
            irFunction.updateCalleeSet();
        }
        ir.updateCalleeSet();
    }

    @Override
    public void visit(FuncDeclNode node){
        String funcName = node.getName();
        if (currentClassName != null) {
            funcName = IRRoot.irMemberFuncName(currentClassName, funcName);
        }
        currentFunc = ir.getFunc(funcName);
        currentBB = currentFunc.genFirstBB();
        //for parameter declaration
        Scope currentScopeBak = currentScope;
        currentScope = node.getBody().getScope();
        if (currentClassName != null){
            VarEntity entity = (VarEntity) currentScope.get(Scope.varKey(Scope.THIS_PARA_NAME));
            VirtualRegister vreg = new VirtualRegister(Scope.THIS_PARA_NAME);
            entity.setIrRegister(vreg);
            currentFunc.addArgVReg(vreg);
        }
        isFuncArgDecl = true;
        for (VarDeclNode argDecl : node.getParameterList()) {
            argDecl.accept(this);
        }
        isFuncArgDecl = false;
        currentScope = currentScopeBak;

        // call global init function
        if (node.getName().equals("main")) {
            currentBB.addInst(new IRFunctionCall(currentBB, ir.getFunc(INIT_FUNC_NAME), new ArrayList<>(), null));
        }
        node.getBody().accept(this);
        if (!currentBB.isHasJumpInst()) {
            if (node.getReturnType() == null || node.getReturnType().getType() instanceof VoidType) {
                currentBB.setJumpInst(new IRReturn(currentBB, null));
            } else {
                currentBB.setJumpInst(new IRReturn(currentBB, new IntImmediate(0)));
            }
        }

        // merge multiple return instructions to a single end basic block
        if (currentFunc.getRetInstList().size() > 1) {
            BasicBlock mergeEndBB = new BasicBlock(currentFunc, currentFunc.getName() + "_end");
            VirtualRegister retReg;
            if (node.getReturnType() == null || node.getReturnType().getType() instanceof VoidType) {
                retReg = null;
            } else {
                retReg = new VirtualRegister("return_value");
            }
            List<IRReturn> retInstList = new ArrayList<>(currentFunc.getRetInstList());
            for (IRReturn retInst : retInstList) {
                BasicBlock bb = retInst.getParentBB();
                if (retInst.getRetValue() != null) {
                    retInst.prependInst(new IRMove(bb, retReg, retInst.getRetValue()));
                }
                retInst.remove();
                bb.setJumpInst(new IRJump(bb, mergeEndBB));
            }
            mergeEndBB.setJumpInst(new IRReturn(mergeEndBB, retReg));
            currentFunc.setEndBB(mergeEndBB);
        } else {
            currentFunc.setEndBB(currentFunc.getRetInstList().get(0).getParentBB());
        }

        currentFunc = null;
    }

    @Override
    public void visit(ClassDeclNode node){
        currentClassName = node.getName();
        currentScope = globalScope;
        for (FuncDeclNode decl : node.getFuncMember()){
            decl.accept(this);
        }
        currentClassName = null;
    }

    @Override
    public void visit(VarDeclNode node){
        VarEntity entity = (VarEntity) currentScope.get(Scope.varKey(node.getName()));
        if (entity.isUnUsed()) return;
        if (currentScope.isTop()){
            //global variables should be placed in data section
            Type type = node.getType().getType();
            StaticData data = new StaticVar(node.getName(), Configuration.getRegSize());
            ir.addStaticData(data);
            entity.setIrRegister(data);
            if (node.getInit() != null) {
                GlobalVarInit init = new GlobalVarInit(node.getName(), node.getInit());
                gloabalInitList.add(init);
            }
        } else {
            VirtualRegister vreg = new VirtualRegister(node.getName());
            entity.setIrRegister(vreg);
            if (isFuncArgDecl){
                currentFunc.addArgVReg(vreg);
            }
            if (node.getInit() == null) {
                if (!isFuncArgDecl) {
                    // set default value to 0 if variable is not initialized
                    currentBB.addInst(new IRMove(currentBB, vreg, new IntImmediate(0)));
                }
            } else {
                if (node.getInit().getType() instanceof BoolType && !(node.getInit() instanceof BoolConstExprNode)) {
                    node.getInit().setTrueBB(new BasicBlock(currentFunc, null));
                    node.getInit().setFalseBB(new BasicBlock(currentFunc, null));
                }
                node.getInit().accept(this);
                processIRAssign(vreg, 0, node.getInit(), node.getInit().getType().getVarSize(), false);
            }
        }
    }
}
