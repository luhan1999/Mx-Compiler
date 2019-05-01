package Mx.Ast;

import Mx.scope.FuncEntity;

import java.util.List;

public class FuncCallExprNode extends ExprNode
{
    private ExprNode func;
    private List<ExprNode> args;
    private FuncEntity funcEntity;

    public FuncCallExprNode(ExprNode _func, List<ExprNode> _args, Location _location)
    {
        func = _func;
        args = _args;
        location = _location;
    }

    public ExprNode getFunc() {
        return func;
    }
    public List<ExprNode> getArgs() {
        return args;
    }

    public FuncEntity getFuncEntity() {
        return funcEntity;
    }
    public void setFuncEntity(FuncEntity funcEntity) {
        this.funcEntity = funcEntity;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
