package Mx.Ast;

public class NullExprNode extends ExprNode {
    public NullExprNode(Location location) {
        this.location = location;
    }

//    @Override
//    public void accept(ASTVisitor visitor) {
//        visitor.visit(this);
//    }
}
