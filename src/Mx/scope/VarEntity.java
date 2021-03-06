package Mx.scope;

import Mx.Ast.VarDeclNode;
import Mx.Type.Type;
import Mx.ir.IRRegister;

public class VarEntity extends Entity {
    private IRRegister irRegister;
    private int addrOffset;
    private boolean isMember = false;
    private String className;
    private boolean isGlobal = false;
    private boolean unUsed = false;

    public VarEntity(String name, Type type) {
        super(name, type);
    }

    public VarEntity(String name, Type type, String className) {
        super(name, type);
        isMember = true;
        this.className = className;
    }

    public VarEntity(VarDeclNode node) {
        super(node.getName(), node.getType().getType());
    }

    public IRRegister getIrRegister() {
        return irRegister;
    }

    public void setIrRegister(IRRegister irRegister) {
        this.irRegister = irRegister;
    }

    public int getAddrOffset() {
        return addrOffset;
    }

    public void setAddrOffset(int addrOffset) {
        this.addrOffset = addrOffset;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public boolean isUnUsed() {
        return unUsed;
    }

    public void setUnUsed(boolean unUsed) {
        this.unUsed = unUsed;
    }
}
