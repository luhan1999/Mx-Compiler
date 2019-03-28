package Mx.Compiler;


import Mx.Ast.*;
import Mx.frontend.*;
import Mx.Parser.MxLexer;
import Mx.Parser.MxParser;
import Mx.Parser.SyntaxErrorListener;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.InputStream;
import java.io.PrintStream;


public class Compiler
{
    private  InputStream Fin;
    private  PrintStream Fout;
    private  ProgramNode Ast;

    public Compiler(InputStream _fin, PrintStream _fout)
    {
        Fin = _fin;
        Fout = _fout;
    }

    private void buildAst() throws Exception{
        CharStream input = CharStreams.fromStream(Fin);
        MxLexer lexer = new MxLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MxParser parser = new MxParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new SyntaxErrorListener());
        ParseTree tree = parser.program();
        ASTBuilder astBuilder = new ASTBuilder();
        Ast = (ProgramNode) astBuilder.visit(tree);
    }

    public void compile() throws Exception {
        System.out.println("compiler is running");

        buildAst();

        GlobalScopePreScanner globalScopePreScanner = new GlobalScopePreScanner();
        globalScopePreScanner.visit(Ast);
        ClassVarMemberScanner classVarMemberScanner = new ClassVarMemberScanner(globalScopePreScanner.getScope());
        classVarMemberScanner.visit(Ast);
        FunctionScopeScanner functionScopeScanner = new FunctionScopeScanner(classVarMemberScanner.getGlobalScope());
        functionScopeScanner.visit(Ast);

        System.out.println("compiler finished.");
    }
}
