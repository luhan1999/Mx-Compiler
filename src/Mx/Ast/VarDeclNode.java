package Mx.Ast;

public class VarDeclNode extends DeclNode
{
    private TypeNode type;
    private ExprNode init;

    public VarDeclNode(TypeNode _type, String _name, ExprNode _init, Location _location)
    {
        type = _type;
        name = _name;
        init = _init;
        location = _location;
    }

    public TypeNode getType() {
        return type;
    }
    public ExprNode getInit() {
        return init;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
