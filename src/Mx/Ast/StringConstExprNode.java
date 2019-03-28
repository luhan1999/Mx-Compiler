package Mx.Ast;

public class StringConstExprNode extends ConstExprNode
{
    private String value;

    public StringConstExprNode(String value, Location location) {
        this.value = value;
        this.location = location;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
