package Mx.frontend;

import Mx.Ast.*;
import Mx.Parser.*;
import Mx.Type.*;
import Mx.ErrorThrow.*;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;


public class ASTBuilder extends MxBaseVisitor<Node>{

    private TypeNode typeForVarDecl;  //TODO

    @Override
    public Node visitProgram(MxParser.ProgramContext ctx) {
        List<DeclNode> decls = new ArrayList<>();
        if (ctx.programSection() != null)
        {
            for (ParserRuleContext programSection : ctx.programSection())
            {
                Node decl = visit(programSection);
                if (decl instanceof VarDeclListNode) decls.addAll(((VarDeclListNode) decl).getDecls());
                else decls.add((DeclNode) decl);
            }
        }
        return new ProgramNode(decls, Location.fromCtx(ctx));
    }

    @Override
    public Node visitProgramSection(MxParser.ProgramSectionContext ctx){
        if (ctx.functionDeclaration() != null) return visit(ctx.functionDeclaration());
        else if (ctx.classDeclaration() != null) return visit(ctx.classDeclaration());
        else if (ctx.variableDeclaration() != null) return visit(ctx.variableDeclaration());
        else throw new CompilerError(Location.fromCtx(ctx), "Invalid program section");
    }

    @Override
    public Node visitFunctionDeclaration(MxParser.FunctionDeclarationContext ctx)
    {
        TypeNode returnType;
        if (ctx.typeORvoid() != null) returnType = (TypeNode) visit(ctx.typeORvoid());
        else returnType = null;

        String name = ctx.Identifier().getText();
        List<VarDeclNode> parameterList = new ArrayList<>();
        Node paraDecl;
        if (ctx.parameterDeclList() != null)
        {
            for (ParserRuleContext parameters:  ctx.parameterDeclList().parameter())
            {
                paraDecl = visit(parameters);
                parameterList.add((VarDeclNode) paraDecl);
            }
        }
        BlockStmtNode body = (BlockStmtNode) visit(ctx.body());
        return new FuncDeclNode(returnType, name, parameterList, body, Location.fromCtx(ctx));
    }

    @Override
    public Node visitClassDeclaration(MxParser.ClassDeclarationContext ctx)
    {
        String name = ctx.Identifier().getText();
        List<VarDeclNode> varMember = new ArrayList<>();
        List<FuncDeclNode> funcMember = new ArrayList<>();

        Node element;
        if (ctx.memberDecl() != null)
        {
            for (ParserRuleContext member: ctx.memberDecl())
            {
                element = visit(member);
                if (element instanceof  VarDeclListNode) varMember.addAll(((VarDeclListNode) element).getDecls());
                else if (element instanceof FuncDeclNode) funcMember.add((FuncDeclNode) element);
                else throw new CompilerError(Location.fromCtx(ctx), "Invalid member declaration");
            }
        }
        return new ClassDeclNode(name, varMember, funcMember, Location.fromCtx(ctx));
    };

    @Override
    public Node visitMemberDecl(MxParser.MemberDeclContext ctx)
    {
        if (ctx.functionDeclaration() != null) return visit(ctx.functionDeclaration());
        else if (ctx.variableDeclaration() != null) return visit(ctx.variableDeclaration());
        else throw new CompilerError(Location.fromCtx(ctx), "Invalid member declaration");
    }

    @Override
    public Node visitVariableDeclaration(MxParser.VariableDeclarationContext ctx)
    {
        typeForVarDecl = (TypeNode) visit(ctx.type());
        return visit(ctx.variableList());
    }

    @Override
    public Node visitVariableList(MxParser.VariableListContext ctx)
    {
        List<VarDeclNode> decls = new ArrayList<>();
        for (ParserRuleContext variable : ctx.variable())
        {
            decls.add((VarDeclNode) visit(variable));
        }
        return new VarDeclListNode(decls);
    }

    @Override
    public Node visitVariable(MxParser.VariableContext ctx)
    {
        String name = ctx.Identifier().getText();
        ExprNode init;
        if (ctx.expression() != null) init = (ExprNode) visit(ctx.expression());
        else init = null;
        return new VarDeclNode(typeForVarDecl, name, init, Location.fromCtx(ctx));
    }

    //will not use the node
    @Override
    public Node visitParameterDeclList(MxParser.ParameterDeclListContext ctx) { return super.visitParameterDeclList(ctx);}

    @Override
    public Node visitParameter(MxParser.ParameterContext ctx){
        TypeNode type = (TypeNode) visit(ctx.type());
        String name = ctx.Identifier().getText();
        return new VarDeclNode(type, name, null, Location.fromCtx(ctx));
    };

    @Override
    public Node visitTypeORvoid(MxParser.TypeORvoidContext ctx)
    {
        if (ctx.type() != null) return visit(ctx.type());
        else return new TypeNode(VoidType.getInstance(), Location.fromCtx(ctx));
    }

    @Override
    public Node visitArraytype(MxParser.ArraytypeContext ctx)
    {
        TypeNode baseType = (TypeNode) visit(ctx.type());
        return new TypeNode(new ArrayType(baseType.getType()), Location.fromCtx(ctx));
    }

    @Override
    public Node visitNonArrayTypetype(MxParser.NonArrayTypetypeContext ctx) { return visit(ctx.nonArraytype()); }

    Type classifyType(TerminalNode isint, TerminalNode isbool, TerminalNode isstring,TerminalNode isidentifier, Location location)
    {
        Type type;
        if (isint != null) type = IntType.getInstance();
        else if (isbool != null) type = BoolType.getInstance();
        else if (isstring != null) type = StringType.getInstance();
        else if (isidentifier != null) type = new ClassType(isidentifier.getText());
        else throw new CompilerError(location, "Invalid primitive type");
        return type;
    }

    @Override
    public Node visitNonArraytype(MxParser.NonArraytypeContext ctx)
    {
        if (ctx.Identifier() != null) return new TypeNode(new ClassType(ctx.Identifier().getText()), Location.fromCtx(ctx));
        Type type;
        type = classifyType(ctx.Int(), ctx.Bool(), ctx.String(), ctx.Identifier(), Location.fromCtx(ctx));
        return new TypeNode(type, Location.fromCtx(ctx));
    }

    @Override
    public Node visitBodyStmt(MxParser.BodyStmtContext ctx)
    {
        return visit(ctx.body());
    }

    @Override
    public Node visitExprStmt(MxParser.ExprStmtContext ctx)
    {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new ExprStmtNode(expr, Location.fromCtx(ctx));
    }

    @Override
    public Node visitCondStmt(MxParser.CondStmtContext ctx)
    {
        return visit(ctx.conditionStatement());
    }

    @Override
    public Node visitLoopStmt(MxParser.LoopStmtContext ctx)
    {
        return  visit(ctx.loopStatement());
    }

    @Override
    public Node visitJumpStmt(MxParser.JumpStmtContext ctx)
    {
        return visit(ctx.jumpStatement());
    }

    @Override
    public Node visitBlankStmt(MxParser.BlankStmtContext ctx) { return null;}

    public Node visitBody(MxParser.BodyContext ctx)
    {
        List<Node> stmtandvarlist = new ArrayList<>();
        if (ctx.bodyStatement() != null)
        {
            for (ParserRuleContext body : ctx.bodyStatement())
            {
                Node node = visit(body);
                if (node != null)
                {
                    if (node instanceof VarDeclListNode) stmtandvarlist.addAll(((VarDeclListNode) node).getDecls());
                    else stmtandvarlist.add(node);
                }
            }
        }
        return new BlockStmtNode(stmtandvarlist, Location.fromCtx(ctx));
    }

    @Override
    public Node visitStmt(MxParser.StmtContext ctx)
    {
        return visit(ctx.statement());
    }

    @Override
    public Node visitVarDeclStmt(MxParser.VarDeclStmtContext ctx)
    {
        return visit(ctx.variableDeclaration());
    }


    @Override
    public Node visitConditionStatement(MxParser.ConditionStatementContext ctx)
    {
        ExprNode cond = (ExprNode) visit(ctx.expression());
        StmtNode thenStmt = (StmtNode) visit(ctx.thenStmt);
        StmtNode elseStmt;
        if (ctx.elseStmt != null) elseStmt = (StmtNode) visit(ctx.elseStmt);
        else elseStmt = null;
        return new CondStmtNode(cond, thenStmt, elseStmt, Location.fromCtx(ctx));
    }

    @Override
    public Node visitWhileStmt(MxParser.WhileStmtContext ctx)
    {
        ExprNode cond = (ExprNode) visit(ctx.expression());
        StmtNode stmt = (StmtNode) visit(ctx.statement());
        return new WhileStmtNode(cond, stmt, Location.fromCtx(ctx));
    }

    @Override
    public Node visitForStmt(MxParser.ForStmtContext ctx)
    {
        ExprNode init, cond, step;
        if (ctx.init != null) init = (ExprNode) visit(ctx.init);
        else init = null;
        if (ctx.cond != null) cond = (ExprNode) visit(ctx.cond);
        else cond = null;
        if (ctx.step != null) step = (ExprNode) visit(ctx.step);
        else step = null;
        StmtNode stmt = (StmtNode) visit(ctx.statement());
        return new ForStmtNode(init, cond, step, stmt, Location.fromCtx(ctx));
    }


    @Override
    public Node visitContinueStmt(MxParser.ContinueStmtContext ctx){
        return new ContinueStmtNode(Location.fromCtx(ctx));
    }

    @Override
    public Node visitBreakStmt(MxParser.BreakStmtContext ctx) {
        return new BreakStmtNode(Location.fromCtx(ctx));
    }

    @Override
    public Node visitReturnStmt(MxParser.ReturnStmtContext ctx)
    {
        ExprNode expr;
        if (ctx.expression() != null) expr = (ExprNode) visit(ctx.expression());
        else expr = null;
        return new ReturnStmtNode(expr, Location.fromCtx(ctx));
    }

    @Override
    public Node visitNewExpr(MxParser.NewExprContext ctx) {
        return visit(ctx.creator());
    }

    @Override
    public Node visitPrefixExpr(MxParser.PrefixExprContext ctx)
    {
        PrefixExprNode.PrefixOps op;
        switch (ctx.op.getText()) {
            case "++" : op = PrefixExprNode.PrefixOps.PREFIX_INC; break;
            case "--" : op = PrefixExprNode.PrefixOps.PREFIX_DEC; break;
            case "+"  : op = PrefixExprNode.PrefixOps.POS; break;
            case "-"  : op = PrefixExprNode.PrefixOps.NEG; break;
            case "!"  : op = PrefixExprNode.PrefixOps.LOGIC_NOT; break;
            case "~"  : op = PrefixExprNode.PrefixOps.BITWISE_NOT; break;
            default   : throw new CompilerError(Location.fromCtx(ctx), "Invalid prefix operator");
        }
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new PrefixExprNode(op, expr, Location.fromCtx(ctx));
    }

    @Override
    public Node visitPrimaryExpr(MxParser.PrimaryExprContext ctx) {
        return visit(ctx.primaryExpression());
    }

    @Override
    public Node visitSubscriptExpr(MxParser.SubscriptExprContext ctx)
    {
        ExprNode arr = (ExprNode) visit(ctx.arr);
        ExprNode sub = (ExprNode) visit(ctx.sub);
        return new SubscriptExprNode(arr, sub, Location.fromCtx(ctx));
    }

    @Override
    public Node visitSuffixExpr(MxParser.SuffixExprContext ctx)
    {
        SuffixExprNode.SuffixOps op;
        switch (ctx.op.getText()) {
            case "++" : op = SuffixExprNode.SuffixOps.SUFFIX_INC; break;
            case "--" : op = SuffixExprNode.SuffixOps.SUFFIX_DEC; break;
            default   : throw new CompilerError(Location.fromCtx(ctx), "Invalid suffix operator");
        }
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new SuffixExprNode(op, expr, Location.fromCtx(ctx));
    }

    @Override
    public Node visitBinaryExpr(MxParser.BinaryExprContext ctx)
    {
        BinaryExprNode.BinaryOps op;
        switch (ctx.op.getText()) {
            case "*"  : op = BinaryExprNode.BinaryOps.MUL; break;
            case "/"  : op = BinaryExprNode.BinaryOps.DIV; break;
            case "%"  : op = BinaryExprNode.BinaryOps.MOD; break;
            case "+"  : op = BinaryExprNode.BinaryOps.ADD; break;
            case "-"  : op = BinaryExprNode.BinaryOps.SUB; break;
            case "<<" : op = BinaryExprNode.BinaryOps.SHL; break;
            case ">>" : op = BinaryExprNode.BinaryOps.SHR; break;
            case "<"  : op = BinaryExprNode.BinaryOps.LESS; break;
            case ">"  : op = BinaryExprNode.BinaryOps.GREATER; break;
            case "<=" : op = BinaryExprNode.BinaryOps.LESS_EQUAL; break;
            case ">=" : op = BinaryExprNode.BinaryOps.GREATER_EQUAL; break;
            case "==" : op = BinaryExprNode.BinaryOps.EQUAL; break;
            case "!=" : op = BinaryExprNode.BinaryOps.INEQUAL; break;
            case "&"  : op = BinaryExprNode.BinaryOps.BITWISE_AND; break;
            case "^"  : op = BinaryExprNode.BinaryOps.BITWISE_XOR; break;
            case "|"  : op = BinaryExprNode.BinaryOps.BITWISE_OR; break;
            case "&&" : op = BinaryExprNode.BinaryOps.LOGIC_AND; break;
            case "||" : op = BinaryExprNode.BinaryOps.LOGIC_OR; break;
            default   : throw new CompilerError(Location.fromCtx(ctx), "Invalid binary operator");
        }
        ExprNode lhs = (ExprNode) visit(ctx.lhs);
        ExprNode rhs = (ExprNode) visit(ctx.rhs);
        return new BinaryExprNode(op, lhs, rhs, Location.fromCtx(ctx));
    }

    @Override
    public Node visitMemberAccessExpr(MxParser.MemberAccessExprContext ctx)
    {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        String member = ctx.Identifier().getText();
        return new MemberAccessExprNode(expr, member, Location.fromCtx(ctx));
    }

    @Override
    public Node visitFuncCallExpr(MxParser.FuncCallExprContext ctx)
    {
        ExprNode func = (ExprNode) visit(ctx.expression());
        List<ExprNode> args = new ArrayList<>();
        if (ctx.parameterList() != null) {
            for (ParserRuleContext parameter : ctx.parameterList().expression()) {
                args.add((ExprNode) visit(parameter));
            }
        }
        return new FuncCallExprNode(func, args, Location.fromCtx(ctx));
    }

    @Override
    public Node visitAssignExpr(MxParser.AssignExprContext ctx)
    {
        ExprNode lhs = (ExprNode) visit(ctx.lhs);
        ExprNode rhs = (ExprNode) visit(ctx.rhs);
        return new AssignExprNode(lhs, rhs, Location.fromCtx(ctx));
    }

    @Override
    public Node visitIdentifierExpr(MxParser.IdentifierExprContext ctx)
    {
        return new IdentifierExprNode(ctx.Identifier().getText(), Location.fromCtx(ctx));
    }

    @Override
    public Node visitThisExpr(MxParser.ThisExprContext ctx)
    {
        return new ThisExprNode(Location.fromCtx(ctx));
    }

    @Override
    public Node visitConstExpr(MxParser.ConstExprContext ctx) {
        return visit(ctx.constant());
    }

    @Override
    public Node visitSubExpr(MxParser.SubExprContext ctx)  {
        return visit(ctx.expression());
    }

    @Override
    public Node visitIntConst(MxParser.IntConstContext ctx){
        int value;
        try {
            value = Integer.parseInt(ctx.getText());
        }
        catch (Exception e) {
            throw new SemanticError(Location.fromCtx(ctx), "Invalid integer constant: " + e);
        }
        return new IntConstExprNode(value, Location.fromCtx(ctx));
    }

    @Override
    public Node visitStringConst(MxParser.StringConstContext ctx){
        return new StringConstExprNode(ctx.getText(), Location.fromCtx(ctx));
    }

    @Override
    public Node visitNullConst(MxParser.NullConstContext ctx)
    {
        return new NullExprNode(Location.fromCtx(ctx));
    }

    @Override
    public Node visitBoolConst(MxParser.BoolConstContext ctx)
    {
        boolean value;
        switch (ctx.getText())
        {
            case "true"  : value = true; break;
            case "false" : value = false; break;
            default      : throw new CompilerError(Location.fromCtx(ctx), "Invalid boolean constant");
        }
        return new BoolConstExprNode(value, Location.fromCtx(ctx));
    }

    @Override
    public Node visitErrorCreator(MxParser.ErrorCreatorContext ctx){
        throw new SemanticError(Location.fromCtx(ctx), "Invalid creator for new expression");
    }

    //TODO
    @Override
    public Node visitArrayCreator(MxParser.ArrayCreatorContext ctx){
        TypeNode newType = (TypeNode) visit(ctx.nonArraytype());
        List<ExprNode> dims = new ArrayList<>();
        for (ParserRuleContext dim : ctx.expression()) {
            dims.add((ExprNode) visit(dim));
        }
        int numDim = (ctx.getChildCount() - 1 - dims.size()) / 2;
        for (int i = 0; i < numDim; ++i) newType.setType(new ArrayType(newType.getType()));
        return new NewExprNode(newType, dims, numDim, Location.fromCtx(ctx));
    }


    @Override
    public Node visitNonArrayCreater(MxParser.NonArrayCreaterContext ctx){
        TypeNode newType = (TypeNode) visit(ctx.nonArraytype());
        return new NewExprNode(newType, null, 0, Location.fromCtx(ctx));
    }


    @Override
    public Node visitParameterList(MxParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }
}
