package Mx.Ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Location {
    private final int line, column;

    public Location(int line, int column){
        this.line = line;
        this.column = column;
    }

    public Location(Token token){
        this.line = token.getLine();
        this.column = token.getCharPositionInLine();
    }

    static public Location fromCtx(ParserRuleContext ctx){
        return new Location(ctx.getStart());
    }

    @Override
    public String toString() { return String.format("(%d:%d)", line , column);}
}