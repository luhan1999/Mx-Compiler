package Mx.Ast;
import Mx.Type.Type;
abstract public class ExprNode extends Node {
    private Type type;
    private boolean isLeftValue;

    public void setType(Type type) {
        this.type = type;
    }
    public Type getType() {
        return type;
    }


    public void setLeftValue(boolean leftValue) {
        isLeftValue = leftValue;
    }
    public boolean isLeftValue() {
        return isLeftValue;
    }
}
