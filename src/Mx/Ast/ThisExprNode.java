package Mx.Ast;

public class ThisExprNode extends ExprNode {
    public ThisExprNode(Location location) {
        this.location = location;
    }

//    @Override
//    public void accept(ASTVisitor visitor) {
//        visitor.visit(this);
//    }
}
