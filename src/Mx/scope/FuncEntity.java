package Mx.scope;

import Mx.Ast.FuncDeclNode;
import Mx.Ast.VarDeclNode;
import Mx.Type.ClassType;
import Mx.Type.FunctionType;
import Mx.Type.Type;

import java.util.ArrayList;
import java.util.List;

public class FuncEntity extends Entity {
    private List<VarEntity> parameters;
    private Type returnType;
    private String className;
    private boolean isConstruct = false, isMember = false, isBuiltIn = false;
    private boolean outInfluence = false;

    public FuncEntity(String name, Type type) {
        super(name, type);
    }
    public FuncEntity(String name, Type type,List<VarEntity> parameters, Type returnType)
    {
        super(name, type);
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public FuncEntity(FuncDeclNode node) {
        super(node.getName(), new FunctionType(node.getName()));
        parameters = new ArrayList<>();
        for (VarDeclNode paraDecl : node.getParameterList()) {
            parameters.add(new VarEntity(paraDecl));
        }
        if (node.getReturnType() == null) returnType = null;
        else returnType = node.getReturnType().getType();
        isConstruct = node.isConstruct();
        isMember = false;
        className = null;
    }

    public FuncEntity(FuncDeclNode node, String className)
    {
        super(node.getName(), new FunctionType(node.getName()));
        parameters = new ArrayList<>();
//        parameters.add(new VarEntity(Scope.THIS_PARA_NAME, new ClassType(className))); //To Think why to add this line
        for (VarDeclNode paraDecl : node.getParameterList())
        {
            parameters.add(new VarEntity(paraDecl));
        }
        if (node.getReturnType() == null) returnType = null;
        else returnType = node.getReturnType().getType();
        isConstruct = node.isConstruct();
        isMember = true;
        this.className = className;
    }

    public void setParameters(List<VarEntity> parameters) {
        this.parameters = parameters;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<VarEntity> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public boolean isConstruct() {
        return isConstruct;
    }

    public boolean isMember() {
        return isMember;
    }

    public boolean isBuiltIn() {
        return isBuiltIn;
    }

    public void setBuiltIn(boolean builtIn) {
        isBuiltIn = builtIn;
        if (builtIn) outInfluence = true;
    }

    public String getClassName() {
        return className;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public boolean isOutInfluence() {
        return outInfluence || isBuiltIn;
    }

    public void setOutInfluence(boolean outInfluence) {
        this.outInfluence = outInfluence;
    }
}
