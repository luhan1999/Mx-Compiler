package Mx.frontend;

import Mx.Ast.*;
import Mx.Type.*;
import Mx.scope.*;


public class ClassVarMemberScanner extends BaseScopeScanner {
    private Scope globalScope, currentClassScope;
    private String currentClassName;
    private int currentOffset = 0;

    public ClassVarMemberScanner(Scope globalScope) {
        this.globalScope = globalScope;
    }

    public Scope getGlobalScope() {
        return globalScope;
    }

    @Override
    public void visit(ProgramNode node) {
        for (DeclNode Node : node.getDecls()) {
            if (!(Node instanceof ClassDeclNode)) continue;
            Node.accept(this);
        }
    }

    @Override
    public void visit(ClassDeclNode node) {
        ClassEntity entity = (ClassEntity) globalScope.getCheck(node.location(), node.getName(), Scope.classKey(node.getName()));
        currentClassScope = entity.getScope();
        currentClassName = entity.getName();
        currentOffset = 0;
        for (VarDeclNode varMemDecl : node.getVarMember()) {
            varMemDecl.accept(this);
        }
        entity.setMemorySize(currentOffset);
    }

    @Override
    public void visit(VarDeclNode node) {
        if (node.getType().getType() instanceof ClassType) {
            String className = ((ClassType) node.getType().getType()).getName();
            currentClassScope.assertContainsExactKey(node.location(), className, Scope.classKey(className));
        }
        checkVarDeclInit(node);
        VarEntity entity = new VarEntity(node.getName(), node.getType().getType(), currentClassName);
        entity.setAddrOffset(currentOffset);
        currentOffset += node.getType().getType().getVarSize();
        currentClassScope.putCheck(node.location(), node.getName(), Scope.varKey(node.getName()), entity);
    }
}
