package Mx.frontend;

import Mx.Ast.*;
import Mx.ErrorThrow.*;
import Mx.Type.*;
import Mx.scope.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalScopePreScanner extends BaseScopeScanner {
    private Scope scope = new Scope();

    public Scope getScope() {
        return scope;
    }

    private void Build(Scope thisScope, String name, List<VarEntity> parameters, Type returnType) {
        String key = Scope.funcKey(name);
        FuncEntity entity = new FuncEntity(name, new FunctionType(name));
        entity.setParameters(parameters);
        entity.setReturnType(returnType);
        entity.setBuiltIn(true);
        if (!thisScope.isTop()) {
            entity.setMember(true);
        }
        thisScope.putCheck(name, key, entity);
    }

    private void preClassFuncs() {
        // ------ Inner Function -------
        Build(scope,"print", Arrays.asList(new VarEntity("str", StringType.getInstance())), VoidType.getInstance());
        Build(scope,"println", Arrays.asList(new VarEntity("str", StringType.getInstance())), VoidType.getInstance());
        Build(scope,"getString", new ArrayList<>(), StringType.getInstance());
        Build(scope,"getInt", new ArrayList<>(), IntType.getInstance());
        Build(scope,"toString", Arrays.asList(new VarEntity("i", IntType.getInstance())), StringType.getInstance());

        // ------ Class String build -------
        String stringKey = Scope.classKey(Scope.STRING_CLASS_NAME);
        ClassEntity stringEntity = new ClassEntity(Scope.STRING_CLASS_NAME, new ClassType(Scope.STRING_CLASS_NAME), scope);
        Build(stringEntity.getScope(), "length", Arrays.asList(new VarEntity(Scope.THIS_PARA_NAME, StringType.getInstance())), IntType.getInstance());
        Build(stringEntity.getScope(), "substring", Arrays.asList(new VarEntity(Scope.THIS_PARA_NAME, StringType.getInstance()), new VarEntity("left", IntType.getInstance()), new VarEntity("right", IntType.getInstance())), StringType.getInstance());
        Build(stringEntity.getScope(), "parseInt", Arrays.asList(new VarEntity(Scope.THIS_PARA_NAME, StringType.getInstance())), IntType.getInstance());
        Build(stringEntity.getScope(), "ord", Arrays.asList(new VarEntity(Scope.THIS_PARA_NAME, StringType.getInstance()), new VarEntity("pos", IntType.getInstance())), IntType.getInstance());
        scope.putCheck(Scope.STRING_CLASS_NAME, stringKey, stringEntity);

        // ------ Class Array build -------
        String arrayKey = Scope.classKey(Scope.ARRAY_CLASS_NAME);
        ClassEntity arrayEntity = new ClassEntity(Scope.ARRAY_CLASS_NAME, new ClassType(Scope.ARRAY_CLASS_NAME), scope);
        Build(arrayEntity.getScope(), "size", Arrays.asList(new VarEntity(Scope.THIS_PARA_NAME, new ArrayType(null))), IntType.getInstance());
        scope.putCheck(Scope.ARRAY_CLASS_NAME, arrayKey, arrayEntity);
    }

    private void checkMain(FuncEntity mainFunc) {
        if (mainFunc == null) throw new SemanticError("\"main\" missing");
        if (!(mainFunc.getReturnType() instanceof IntType)) throw new SemanticError("\"main\" function's return type error");
        if (!mainFunc.getParameters().isEmpty()) throw new SemanticError("\"main\" with parameters");
    }

    @Override
    public void visit(ProgramNode node)
    {
        preClassFuncs();
        for (DeclNode Node : node.getDecls()) {
            if (Node instanceof VarDeclNode) continue;
            Node.accept(this);
        }
        checkMain((FuncEntity) scope.get(Scope.funcKey("main")));
    }

    @Override
    public void visit(FuncDeclNode node)
    {
        String key = Scope.funcKey(node.getName());
        Entity entity = new FuncEntity(node);
        scope.putCheck(node.location(), node.getName(), key, entity);
    }

    @Override
    public void visit(ClassDeclNode node) {
        String key = Scope.classKey(node.getName());
        Entity entity = new ClassEntity(node, scope);
        scope.putCheck(node.location(), node.getName(), key, entity);
    }
}
