package Mx.Ast;

abstract  public class Node {
    protected Location location;

    public Node() {}

    public Location location() {return location;}

    //abstract public void accpet(ASTVisitor visitor);
}
