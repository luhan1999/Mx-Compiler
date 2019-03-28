package Mx.Ast;

import java.util.List;

public class ClassDeclNode extends DeclNode {

    private List<VarDeclNode> varMember;
    private List<FuncDeclNode> funcMember;

    public ClassDeclNode(String name, List<VarDeclNode> varMember, List<FuncDeclNode> funcMember, Location location) {
        this.name = name;
        this.varMember = varMember;
        this.funcMember = funcMember;
        this.location = location;
    }

    public List<VarDeclNode> getVarMember() {
        return varMember;
    }
    public List<FuncDeclNode> getFuncMember() {
        return funcMember;
    }

//    @Override
//    public void accept(ASTVisitor visitor) {
//        visitor.visit(this);
//    }
}
