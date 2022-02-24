package pers.gxj.security.oauth2.framework.security.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/14 14:07
 */
public class HttpUtils {

    public static void renderString(HttpServletResponse response, String msg) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
