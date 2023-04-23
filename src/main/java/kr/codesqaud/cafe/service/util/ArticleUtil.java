package kr.codesqaud.cafe.service.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ArticleUtil {
    public static String getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (String) session.getAttribute("userId");
        }
        return null;
    }
}
