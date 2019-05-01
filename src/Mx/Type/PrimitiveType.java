package Mx.Type;

abstract public class PrimitiveType extends Type {
    @Override
    public String toString() {
        return String.format("PrimitiveType(%s)", hyperType.toString());
    }
}

