package Mx.Type;

public class VoidType extends PrimitiveType {
    static private VoidType instance = new VoidType();
    private VoidType() {
        hyperType = HyperTypes.VOID;
    }
    public static VoidType getInstance() {
        return instance;
    }
}
