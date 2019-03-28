package Mx.Ast;

import java.util.List;

public class FuncCallExprNode extends ExprNode {
    private ExprNode func;
    private List<ExprNode> args;

    public FuncCallExprNode(ExprNode _func, List<ExprNode> _args, Location _location) {
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

//    @Override
//    public void accept(ASTVisitor visitor) {
//        visitor.visit(this);
//    }
}
