package DrinKings.backend.global.utils;

public class Operations {
    public static String trimBrackets(String message) {
        return message.replaceAll("[\\[\\]]", "");
    }
}
