package Mx.Ast;

import java.util.List;

public class FuncDeclNode extends DeclNode
{
    private boolean isConstruct;                  //identify constructed function
    private TypeNode returnType;                  //include void or others
    private List<VarDeclNode> parameterList;
    private BlockStmtNode body;

    public FuncDeclNode(TypeNode _returntype, String _name, List<VarDeclNode> _parameterList, BlockStmtNode _body, Location _location)
    {

        if (_returntype == null)
        {
            isConstruct = true;
            returnType = null;
        } else {
            isConstruct = false;
            returnType = _returntype;
        }

        name = _name;
        parameterList = _parameterList;
        body = _body;
        location = _location;
    }

    public boolean isConstruct() {
        return isConstruct;
    }
    public TypeNode getReturnType() { return returnType; }
    public List<VarDeclNode> getParameterList() {
        return parameterList;
    }
    public BlockStmtNode getBody() {
        return body;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
