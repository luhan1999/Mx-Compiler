package Mx;

public class Configuration {
    private static final int REG_SIZE = 8;
    private static final boolean ENABLE_FUNCTION_INLINE = true;

    public static String configInfo() {
        return "";
    }

    public static int getRegSize() {
        return REG_SIZE;
    }

    public static boolean isEnableFunctionInline() {
        return ENABLE_FUNCTION_INLINE;
    }
}
