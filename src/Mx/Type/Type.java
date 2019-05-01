package Mx.Type;

public class Type
{
    public enum HyperTypes {
        VOID, INT, STRING, BOOL, CLASS, ARRAY, FUNCTION , NULL
    }
    int varSize;
    HyperTypes hyperType;
    public HyperTypes getHyperType() {
        return hyperType;
    }

    public int getVarSize() {
        return varSize;
    }
    @Override
    public String toString() {
        return String.format("PrimitiveType(%s)", hyperType.toString());
    }
}
