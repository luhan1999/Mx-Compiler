package Mx.Ast;

public class WhileStmtNode extends LoopStmtNode
{
    private ExprNode cond;
    private StmtNode stmt;

    public WhileStmtNode(ExprNode cond, StmtNode stmt, Location location)
    {
        this.cond = cond;
        this.stmt = stmt;
        this.location = location;
    }

    public ExprNode getCond() {
        return cond;
    }
    public StmtNode getStmt() {
        return stmt;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}