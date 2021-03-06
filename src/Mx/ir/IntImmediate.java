package Mx.ir;

public class IntImmediate extends RegValue{
    int value;

    public IntImmediate(int value) { this.value = value; }

    public int getValue() { return value;}

    public void accept(IRVisitor visitor) { visitor.visit(this);}

    @Override
    public IntImmediate copy() { return new IntImmediate(value); }
}
