package Mx.Ast;

public class AssignExprNode extends ExprNode
{
    private ExprNode lhs, rhs;

    public AssignExprNode(ExprNode lhs, ExprNode rhs, Location location)
    {
        this.lhs = lhs;
        this.rhs = rhs;
        this.location = location;
    }

    public ExprNode getLhs() {
        return lhs;
    }
    public ExprNode getRhs() {
        return rhs;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
