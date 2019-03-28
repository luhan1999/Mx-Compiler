package Mx.Type;

public class IntType extends Type {
    static private IntType instance = new IntType();
    private IntType() {
        hyperType = HyperTypes.INT;
    }
    public static IntType getInstance() {
        return instance;
    }
}
