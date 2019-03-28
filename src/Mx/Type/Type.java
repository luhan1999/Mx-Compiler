package Mx.Type;

public class Type {
    public enum HyperTypes {
        VOID, INT, STRING, BOOL, CLASS, ARRAY, FUNCTION , NULL
    }

    HyperTypes hyperType;
    public HyperTypes getHyperType() {
        return hyperType;
    }

    @Override
    public String toString() {
        return String.format("PrimitiveType(%s)", hyperType.toString());
    }
}
