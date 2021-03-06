package Mx.Ast;

public class BoolConstExprNode extends ConstExprNode {
    private boolean value;

    public BoolConstExprNode(boolean value, Location location)
    {
        this.value = value;
        this.location = location;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
