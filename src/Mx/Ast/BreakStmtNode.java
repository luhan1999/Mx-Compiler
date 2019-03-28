package Mx.Ast;

public class BreakStmtNode extends JumpStmtNode {
    public BreakStmtNode(Location location) {
        this.location = location;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
