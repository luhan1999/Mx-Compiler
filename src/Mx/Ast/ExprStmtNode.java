package Mx.Ast;

public class ExprStmtNode extends StmtNode
{
    private ExprNode expr;

    public ExprStmtNode(ExprNode expr, Location location)
    {
        this.expr = expr;
        this.location = location;
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
