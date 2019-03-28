package Mx.scope;

import Mx.Ast.VarDeclNode;
import Mx.Type.Type;

public class VarEntity extends Entity {
    public VarEntity(String name, Type type) { super(name, type); }

    public VarEntity(VarDeclNode node) {
        super(node.getName(), node.getType().getType());
    }
}
