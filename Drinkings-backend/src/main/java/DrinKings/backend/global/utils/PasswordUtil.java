package DrinKings.backend.global.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash a plain password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify a password against a hashed password
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
