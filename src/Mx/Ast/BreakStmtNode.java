package Mx.Ast;

public class BreakStmtNode extends JumpStmtNode {
    public BreakStmtNode(Location location) {
        this.location = location;
    }

//    @Override
//    public void accept(ASTVisitor visitor) {
//        visitor.visit(this);
//    }
}
