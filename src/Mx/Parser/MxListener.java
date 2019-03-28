// Generated from F:/LhCompiler/src/Mx/Parser\Mx.g4 by ANTLR 4.7.2
package Mx.Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#programSection}.
	 * @param ctx the parse tree
	 */
	void enterProgramSection(MxParser.ProgramSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#programSection}.
	 * @param ctx the parse tree
	 */
	void exitProgramSection(MxParser.ProgramSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(MxParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(MxParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(MxParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(MxParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#memberDecl}.
	 * @param ctx the parse tree
	 */
	void enterMemberDecl(MxParser.MemberDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#memberDecl}.
	 * @param ctx the parse tree
	 */
	void exitMemberDecl(MxParser.MemberDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(MxParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(MxParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#variableList}.
	 * @param ctx the parse tree
	 */
	void enterVariableList(MxParser.VariableListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#variableList}.
	 * @param ctx the parse tree
	 */
	void exitVariableList(MxParser.VariableListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(MxParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(MxParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parameterDeclList}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclList(MxParser.ParameterDeclListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parameterDeclList}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclList(MxParser.ParameterDeclListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MxParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MxParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#typeORvoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeORvoid(MxParser.TypeORvoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#typeORvoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeORvoid(MxParser.TypeORvoidContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arraytype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArraytype(MxParser.ArraytypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arraytype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArraytype(MxParser.ArraytypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonArrayTypetype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterNonArrayTypetype(MxParser.NonArrayTypetypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonArrayTypetype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitNonArrayTypetype(MxParser.NonArrayTypetypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#nonArraytype}.
	 * @param ctx the parse tree
	 */
	void enterNonArraytype(MxParser.NonArraytypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#nonArraytype}.
	 * @param ctx the parse tree
	 */
	void exitNonArraytype(MxParser.NonArraytypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bodyStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBodyStmt(MxParser.BodyStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bodyStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBodyStmt(MxParser.BodyStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCondStmt(MxParser.CondStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCondStmt(MxParser.CondStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStmt(MxParser.LoopStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStmt(MxParser.LoopStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStmt(MxParser.JumpStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStmt(MxParser.JumpStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blankStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlankStmt(MxParser.BlankStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blankStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlankStmt(MxParser.BlankStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(MxParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(MxParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmt}
	 * labeled alternative in {@link MxParser#bodyStatement}.
	 * @param ctx the parse tree
	 */
	void enterStmt(MxParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmt}
	 * labeled alternative in {@link MxParser#bodyStatement}.
	 * @param ctx the parse tree
	 */
	void exitStmt(MxParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDeclStmt}
	 * labeled alternative in {@link MxParser#bodyStatement}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclStmt(MxParser.VarDeclStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDeclStmt}
	 * labeled alternative in {@link MxParser#bodyStatement}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclStmt(MxParser.VarDeclStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#conditionStatement}.
	 * @param ctx the parse tree
	 */
	void enterConditionStatement(MxParser.ConditionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#conditionStatement}.
	 * @param ctx the parse tree
	 */
	void exitConditionStatement(MxParser.ConditionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link MxParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(MxParser.ContinueStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link MxParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(MxParser.ContinueStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MxParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(MxParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MxParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(MxParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(MxParser.PrefixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExpr(MxParser.PrefixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(MxParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(MxParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscriptExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptExpr(MxParser.SubscriptExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscriptExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptExpr(MxParser.SubscriptExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuffixExpr(MxParser.SuffixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuffixExpr(MxParser.SuffixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessExpr(MxParser.MemberAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessExpr(MxParser.MemberAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallExpr(MxParser.FuncCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallExpr(MxParser.FuncCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpr(MxParser.IdentifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpr(MxParser.IdentifierExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(MxParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(MxParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstExpr(MxParser.ConstExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstExpr(MxParser.ConstExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subExpr}
	 * labeled alternative in {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpr(MxParser.SubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subExpr}
	 * labeled alternative in {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpr(MxParser.SubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intConst}
	 * labeled alternative in {@link MxParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterIntConst(MxParser.IntConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intConst}
	 * labeled alternative in {@link MxParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitIntConst(MxParser.IntConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringConst}
	 * labeled alternative in {@link MxParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterStringConst(MxParser.StringConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringConst}
	 * labeled alternative in {@link MxParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitStringConst(MxParser.StringConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullConst}
	 * labeled alternative in {@link MxParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNullConst(MxParser.NullConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullConst}
	 * labeled alternative in {@link MxParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNullConst(MxParser.NullConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolConst}
	 * labeled alternative in {@link MxParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterBoolConst(MxParser.BoolConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolConst}
	 * labeled alternative in {@link MxParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitBoolConst(MxParser.BoolConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code errorCreator}
	 * labeled alternative in {@link MxParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterErrorCreator(MxParser.ErrorCreatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code errorCreator}
	 * labeled alternative in {@link MxParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitErrorCreator(MxParser.ErrorCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayCreator}
	 * labeled alternative in {@link MxParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreator(MxParser.ArrayCreatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayCreator}
	 * labeled alternative in {@link MxParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreator(MxParser.ArrayCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonArrayCreater}
	 * labeled alternative in {@link MxParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterNonArrayCreater(MxParser.NonArrayCreaterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonArrayCreater}
	 * labeled alternative in {@link MxParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitNonArrayCreater(MxParser.NonArrayCreaterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(MxParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(MxParser.ParameterListContext ctx);
}