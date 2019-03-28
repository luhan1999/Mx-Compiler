package Mx.Ast;

public class CondStmtNode extends StmtNode
{
    private ExprNode cond;
    private StmtNode thenStmt, elseStmt;

    public CondStmtNode(ExprNode cond, StmtNode thenStmt, StmtNode elseStmt, Location location) {
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
        this.location = location;
    }

    public ExprNode getCond() {
        return cond;
    }
    public StmtNode getThenStmt() {
        return thenStmt;
    }
    public StmtNode getElseStmt() { return elseStmt; }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
