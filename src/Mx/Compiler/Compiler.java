package Mx.Compiler;


import Mx.Ast.*;
import Mx.backend.*;
import Mx.frontend.*;
import Mx.Parser.MxLexer;
import Mx.Parser.MxParser;
import Mx.Parser.SyntaxErrorListener;

import Mx.ir.IRRoot;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.InputStream;
import java.io.PrintStream;


public class Compiler
{
    private  InputStream Fin;
    private  PrintStream Fout,irOutS,nasmOutS;
    private  ProgramNode Ast;

    public Compiler(InputStream fin, PrintStream fout, PrintStream irOutS, PrintStream nasmOutS)
    {
        Fin = fin;
        Fout = fout;
        this.irOutS = irOutS;
        this.nasmOutS = nasmOutS;
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
        //System.out.println("compiler is running");
        buildAst();
        //System.out.println("AST is finished");

        GlobalScopePreScanner globalScopePreScanner = new GlobalScopePreScanner();
        globalScopePreScanner.visit(Ast);
        ClassVarMemberScanner classVarMemberScanner = new ClassVarMemberScanner(globalScopePreScanner.getScope());
        classVarMemberScanner.visit(Ast);
        FunctionScopeScanner functionScopeScanner = new FunctionScopeScanner(classVarMemberScanner.getGlobalScope());
        functionScopeScanner.visit(Ast);

        new StaticUsagePreScanner(globalScopePreScanner.getScope()).visit(Ast);

        IRBuilder irBuilder = new IRBuilder(globalScopePreScanner.getScope());
        irBuilder.visit(Ast);
        //System.out.println("IRbuild is finished");

        IRRoot ir = irBuilder.getIR();
        new FunctionInlineProcessor(ir).run();
        new BinaryOpTransformer(ir).run();
        if (irOutS != null) new IRPrinter(irOutS).visit(ir);
        new StaticDataProcessor(ir).run();
        new RegisterPreprocessor(ir).run();
        new RegLivelinessAnalysis(ir).run();
        new RegisterAllocator(ir).run();
        new NASMTransformer(ir).run();
        new NASMPrinter(nasmOutS).visit(ir);

        //System.out.println("compiler finished.");
    }
}
