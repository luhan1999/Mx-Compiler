package Mx;

import Mx.Compiler.Compiler;
import java.io.*;



public class Main
{
    public static void main(String arg[])throws Exception
    {
        String fin = null;
        String fout = null;
        //String nasmoutFile = "F:\\LhCompiler\\src\\Mx\\testcase\\nasm1.txt";
        String nasmoutFile = null;

        InputStream inS;
        PrintStream outS;
        PrintStream nasmout;
        if (fin == null) inS = System.in;
        else inS = new FileInputStream(fin);
        if (fout == null) outS = System.out;
        else outS = new PrintStream(new FileOutputStream(fout));
        if (nasmoutFile == null) nasmout = System.out;
        else nasmout = new PrintStream(new FileOutputStream(nasmoutFile));

        Compiler compiler = new Compiler(inS, outS, nasmout);
        try {
            compiler.compile();
        }
        catch (Error e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
