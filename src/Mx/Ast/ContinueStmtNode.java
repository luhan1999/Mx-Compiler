package Mx.Ast;

public class ContinueStmtNode extends JumpStmtNode
{
    public ContinueStmtNode(Location location) {
        this.location = location;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
