package Mx.frontend;

import Mx.Ast.*;
import Mx.Type.*;
import Mx.ErrorThrow.*;

abstract public class BaseScopeScanner implements AstVisitor {
    protected void checkVarDeclInit(VarDeclNode node) {
        if (node.getInit() != null) {
            node.getInit().accept(this);
            boolean invalidInitType;
            if (node.getType().getType() instanceof VoidType || node.getInit().getType() instanceof VoidType)
                invalidInitType = true;
            else if (node.getType().getType().equals(node.getInit().getType()))
                invalidInitType = false;
            else if (node.getInit().getType() instanceof NullType)
                invalidInitType = !(node.getType().getType() instanceof ClassType || node.getType().getType() instanceof ArrayType);
            else
                invalidInitType = true;
            if (invalidInitType)
                throw new SemanticError(node.location(), String.format("Invalid initialization value, expected \"%s\" but got \"%s\"", node.getType().getType().toString(), node.getInit().getType().toString()));
        }
    }

    @Override
    public void visit(ProgramNode node) {

    }

    @Override
    public void visit(VarDeclListNode node) {

    }

    @Override
    public void visit(FuncDeclNode node) {

    }

    @Override
    public void visit(ClassDeclNode node) {

    }

    @Override
    public void visit(VarDeclNode node) {

    }

    @Override
    public void visit(BlockStmtNode node) {

    }

    @Override
    public void visit(ExprStmtNode node) {

    }

    @Override
    public void visit(CondStmtNode node) {

    }

    @Override
    public void visit(WhileStmtNode node) {

    }

    @Override
    public void visit(ForStmtNode node) {

    }

    @Override
    public void visit(ContinueStmtNode node) {

    }

    @Override
    public void visit(BreakStmtNode node) {

    }

    @Override
    public void visit(ReturnStmtNode node) {

    }

    @Override
    public void visit(SuffixExprNode node) {

    }

    @Override
    public void visit(FuncCallExprNode node) {

    }

    @Override
    public void visit(SubscriptExprNode node) {

    }

    @Override
    public void visit(MemberAccessExprNode node) {

    }

    @Override
    public void visit(PrefixExprNode node) {

    }

    @Override
    public void visit(NewExprNode node) {

    }

    @Override
    public void visit(BinaryExprNode node) {

    }

    @Override
    public void visit(AssignExprNode node) {

    }

    @Override
    public void visit(IdentifierExprNode node) {

    }

    @Override
    public void visit(ThisExprNode node) {

    }

    @Override
    public void visit(IntConstExprNode node) {

    }

    @Override
    public void visit(StringConstExprNode node) {

    }

    @Override
    public void visit(BoolConstExprNode node) {

    }

    @Override
    public void visit(NullExprNode node) {

    }

    @Override
    public void visit(TypeNode node) {

    }
}