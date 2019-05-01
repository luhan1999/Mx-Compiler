package Mx.Ast;

abstract  public class Node {
    protected Location location;
    protected boolean outInfluence = false;

    public Node() {}

    public Location location() {return location;}

    public boolean isOutInfluence() {
        return outInfluence;
    }

    public void setOutInfluence(boolean outInfluence) {
        this.outInfluence = outInfluence;
    }

    abstract public void accept(AstVisitor visitor);
}
