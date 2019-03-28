package Mx.Type;

public class BoolType extends Type
{
    static private BoolType instance = new BoolType();
    private BoolType() {
        hyperType = HyperTypes.BOOL;
    }
    public static BoolType getInstance() {
        return instance;
    }
}
