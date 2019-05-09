package Mx.ir;

import java.util.Map;

public class IRPush extends IRInstruction{
    private RegValue value;

    public IRPush(BasicBlock parentBB, RegValue value) {
        super(parentBB);
        this.value = value;
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
    public void reloadUsedRegistersRegValues() { }

    @Override
    public IRRegister getDefinedRegister() {
        return null;
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) { }

    @Override
    public void setDefinedRegister(IRRegister vreg) { }

    public RegValue getValue() {
        return value;
    }
}
