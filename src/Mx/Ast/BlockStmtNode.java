package Mx.Ast;

import Mx.scope.Scope;
import java.util.List;

public class BlockStmtNode extends StmtNode
{
    private List<Node> stmtsAndVarDecls;
    private List<StmtNode> stmts;
    private Scope scope;

    public BlockStmtNode(List<Node> stmtsAndVarDecls, Location location)
    {
        this.stmtsAndVarDecls = stmtsAndVarDecls;
        this.location = location;
    }

    public List<Node> getStmtsAndVarDecls() {
        return stmtsAndVarDecls;
    }

    public void initScope(Scope parentScope) { scope = new Scope(parentScope, false); }

    public List<StmtNode> getStmts() {
        return stmts;
    }
    public Scope getScope() {
        return scope;
    }

    @Override
    public void accept(AstVisitor visitor)
    {
        visitor.visit(this);
    }
}
