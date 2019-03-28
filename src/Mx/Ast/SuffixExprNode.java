package Mx.Ast;

public class SuffixExprNode extends ExprNode
{
    public enum SuffixOps {
        SUFFIX_INC, SUFFIX_DEC
    }

    private SuffixOps op;
    private ExprNode expr;

    public SuffixExprNode(SuffixOps _op, ExprNode _expr, Location _location)
    {
        op = _op;
        expr = _expr;
        location = _location;
    }

    public SuffixOps getOp() {
        return op;
    }
    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
