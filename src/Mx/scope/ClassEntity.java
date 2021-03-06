package Mx.scope;

import Mx.Ast.ClassDeclNode;
import Mx.Ast.FuncDeclNode;
import Mx.Type.ClassType;
import Mx.Type.Type;

public class ClassEntity extends Entity {
    private Scope scope;
    private int memorySize;

    public ClassEntity(String name, Type type, Scope parentScope) {
        super(name, type);
        scope = new Scope(parentScope, true);
    }

    public ClassEntity(ClassDeclNode node, Scope parentScope) {
        super(node.getName(), new ClassType(node.getName()));
        String key;
        FuncEntity entity;
        scope = new Scope(parentScope, true);
        for (FuncDeclNode funcMemDecl : node.getFuncMember())
        {
            key = Scope.funcKey(funcMemDecl.getName());
            entity = new FuncEntity(funcMemDecl, node.getName());
            scope.putCheck(funcMemDecl.location(), funcMemDecl.getName(), key, entity);
        }
    }

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public Scope getScope() {
        return scope;
    }
}
