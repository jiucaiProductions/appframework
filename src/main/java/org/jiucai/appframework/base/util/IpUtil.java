package org.jiucai.appframework.base.util;

import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过 request 获取客户端 ip
 * 
 * @author zhaidw
 *
 */
public class IpUtil {

    protected static Logger log = LoggerFactory.getLogger(IpUtil.class);

    private static Pattern pattern = Pattern
            .compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = isDottedIp(request.getHeader("Proxy-Client-IP"));
            if (ip != null) {
                return ip;
            }
        } else {
            if (ip.indexOf(",") != -1) {
                String[] s = ip.split(",");
                for (int i = 0; i < s.length; i++) {
                    if (s[i] != null && !"".equals(s[i]) && !"unknown".equals(s[i])) {
                        ip = isDottedIp(s[i].trim());
                        if (ip != null) {
                            return ip;
                        }
                    }
                }
            } else if (ip.indexOf(";") != -1) {
                String[] s = ip.split(";");
                for (int i = 0; i < s.length; i++) {
                    if (s[i] != null && !"".equals(s[i]) && !"unknown".equals(s[i])) {
                        ip = isDottedIp(s[i].trim());
                        if (ip != null) {
                            return ip;
                        }
                    }
                }
            } else {
                if ("unknown".equals(ip)) {
                    ip = isDottedIp(ip.trim());
                    if (ip != null) {
                        return ip;
                    }
                }
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = isDottedIp(request.getHeader("WL-Proxy-Client-IP"));
            if (ip != null) {
                return ip;
            }
        }
        if (ip != null && ip.indexOf(' ') != -1) {
            ip = isDottedIp(ip.substring(0, ip.indexOf(' ')));
            if (ip != null) {
                return ip;
            }
        }
        if (ip == null) {
            if (request.getHeader("x-forwarded-for") != null
                    || request.getHeader("Proxy-Client-IP") != null
                    || request.getHeader("WL-Proxy-Client-IP") != null) {
                Enumeration<?> headers = request.getHeaderNames();
                while (headers.hasMoreElements()) {
                    String headerName = headers.nextElement().toString();
                    log.debug(headerName + " --> " + request.getHeader(headerName));
                }
                log.debug("--------request.getRemoteAddr:" + request.getRemoteAddr() + "--------");
            }
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static String isDottedIp(String ipString) {
        if (ipString != null) {
            Matcher matcher = pattern.matcher(ipString);
            if (matcher.matches()) {
                return matcher.group();
            }
        }
        return null;
    }

}