package Mx.scope;

import Mx.Ast.FuncDeclNode;
import Mx.Ast.VarDeclNode;
import Mx.Type.FunctionType;
import Mx.Type.Type;

import java.util.ArrayList;
import java.util.List;

public class FuncEntity extends Entity {
    private List<VarEntity> parameters;
    private Type returnType;
    private String className;
    private boolean isConstruct, isMember;

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
        for (VarDeclNode paraDecl : node.getParameterList())
        {
            parameters.add(new VarEntity(paraDecl));
        }
        if (node.getReturnType() == null) returnType = null;
        else returnType = node.getReturnType().getType();
        isConstruct = node.isConstruct();
        isMember = false;
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
    public String getClassName() {
        return className;
    }
}
