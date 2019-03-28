package Mx.Ast;

public class ReturnStmtNode extends JumpStmtNode
{
    private ExprNode expr;

    public ReturnStmtNode(ExprNode expr, Location location)
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