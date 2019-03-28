package Mx.ErrorThrow;

import Mx.Ast.Location;

public class SyntaxError extends Error {
    public SyntaxError(Location location, String message) {
        super(String.format("[Syntax Error] at %s: %s", location.toString(), message));
    }
}
