package Mx.Ast;

public class MemberAccessExprNode extends ExprNode {
    private ExprNode expr;
    private String member;

    public MemberAccessExprNode(ExprNode _expr, String _member, Location _location) {
        expr = _expr;
        member = _member;
        location = _location;
    }

    public ExprNode getExpr() {
        return expr;
    }
    public String getMember() {
        return member;
    }

//    @Override
//    public void accept(ASTVisitor visitor) {
//        visitor.visit(this);
//    }
}

