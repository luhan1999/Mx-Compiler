package Mx.ir;

import java.util.Map;

public class IRPop extends IRInstruction{
    private PhysicalRegister preg;

    public IRPop(BasicBlock parentBB, PhysicalRegister preg) {
        super(parentBB);
        this.preg = preg;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public IRInstruction copyRename(Map<Object, Object> renameMap) {
        return null;
    }

    @Override
    public void reloadUsedRegistersRegValues() {}

    @Override
    public IRRegister getDefinedRegister() {
        return null;
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) {}

    @Override
    public void setDefinedRegister(IRRegister vreg) {}

    public PhysicalRegister getPreg() {
        return preg;
    }
}
