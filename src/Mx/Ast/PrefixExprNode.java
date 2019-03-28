package Mx.Ast;

public class PrefixExprNode extends ExprNode {
    public enum PrefixOps {
        PREFIX_INC, PREFIX_DEC, POS, NEG, LOGIC_NOT, BITWISE_NOT
    }

    private PrefixOps op;
    private ExprNode expr;

    public PrefixExprNode(PrefixOps _op, ExprNode _expr, Location _location) {
        op = _op;
        expr = _expr;
        location = _location;
    }

    public PrefixOps getOp() {
        return op;
    }
    public ExprNode getExpr() {
        return expr;
    }

//    @Override
//    public void accept(ASTVisitor visitor) {
//        visitor.visit(this);
//    }
}