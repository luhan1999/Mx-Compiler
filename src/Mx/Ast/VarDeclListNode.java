package Mx.Ast;

import java.util.List;

public class VarDeclListNode extends Node
{
    private List<VarDeclNode> decls;

    public VarDeclListNode(List<VarDeclNode> decls)
    {
        this.decls = decls;
        this.location = null;
    }

    public List<VarDeclNode> getDecls() {
        return decls;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
