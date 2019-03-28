package Mx.Ast;

import Mx.Type.Type;

public class TypeNode extends Node
{
    private Type type;

    public TypeNode(Type _type, Location _location){
        type = _type;
        location = _location;
    }

    public void setType(Type type) {this.type = type;}
    public Type getType() {return type;}

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
