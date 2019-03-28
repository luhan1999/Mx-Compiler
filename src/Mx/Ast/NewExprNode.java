package Mx.Ast;

import java.util.List;

public class NewExprNode extends ExprNode {
    private TypeNode newType;
    private List<ExprNode> dims;
    private int numDim;

    public NewExprNode(TypeNode _newType, List<ExprNode> _dims, int _numDim, Location _location) {
        newType = _newType;
        dims = _dims;
        numDim = _numDim;
        location = _location;
    }

    public TypeNode getNewType() {
        return newType;
    }
    public List<ExprNode> getDims() {
        return dims;
    }
    public int getNumDim() {
        return numDim;
    }

//    @Override
//    public void accept(ASTVisitor visitor) {
//        visitor.visit(this);
//    }
}
