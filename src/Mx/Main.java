package Mx;

import Mx.Compiler.Compiler;
import java.io.*;



public class Main
{
    public static void main(String arg[])throws Exception
    {
        String fin = "E:\\1_Compiler\\fz\\EM-Compiler\\testcase\\input.txt";
        String fout = null;

        InputStream inS;
        PrintStream outS;
        if (fin == null) inS = System.in;
        else inS = new FileInputStream(fin);
        if (fout == null) outS = System.out;
        else outS = new PrintStream(new FileOutputStream(fout));

        Compiler compiler = new Compiler(inS, outS);
        try {
            compiler.compile();
        }
        catch (Error e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
