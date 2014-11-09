package org.jiucai.appframework.base.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.jiucai.appframework.base.helper.AppRequestHolder;

/**
 * Cookie 工具类
 *
 * @author zhaidangwei
 *
 */
public class CookieUtil {
    public static void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(defaultCookieLife);
        response.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        // 如果把有效周期设置为0，则表示此Cookie对象存放在浏览器后将立即失效，
        // 如果把有效周期设置为任意一个负数，则当浏览器关闭后，此Cookie对象立即失效。
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Cookie getCookieByName(String name) {
        Map<String, Cookie> cookieMap = readCookieMap();
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    public static String getCookieValue(String name) {
        String cookieValue = "";
        Cookie c = getCookieByName(name);

        if (null != c) {
            cookieValue = c.getValue();
        }

        return cookieValue;
    }

    public static Map<String, Cookie> readCookieMap() {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = AppRequestHolder.getRequest().getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    // cookie 默认时间, 1周
    public static int defaultCookieLife = 60 * 60 * 24 * 7;
}
