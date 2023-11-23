package kuit2.server.util;

import jakarta.servlet.http.HttpSession;
import kuit2.server.dao.UserDao;

public class UserSessionUtils {
    public static final String USER_SESSION_KEY = "user";

    public static UserDao getUserFromSession(HttpSession session) {
        Object user = session.getAttribute(USER_SESSION_KEY);
        if (user == null) {
            return null;
        }
        return (UserDao) user;
    }

    public static boolean isLoggedIn(HttpSession session) {
        return getUserFromSession(session) != null;
    }
}
