package Mx.Type;

public class StringType extends Type {
    static private StringType instance = new StringType();
    private StringType() {
        hyperType = HyperTypes.STRING;
    }
    public static StringType getInstance() {
        return instance;
    }
}

