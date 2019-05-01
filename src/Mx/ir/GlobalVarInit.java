package Mx.ir;

import Mx.Ast.ExprNode;

public class GlobalVarInit {
    private String name;
    private ExprNode initExpr;

    public GlobalVarInit(String name, ExprNode initExpr){
        this.name = name;
        this.initExpr = initExpr;
    }

    public String getName() { return name;}

    public ExprNode getInitExpr() { return  initExpr; }
}
