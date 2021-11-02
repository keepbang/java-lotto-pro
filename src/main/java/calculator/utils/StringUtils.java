package calculator.utils;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
