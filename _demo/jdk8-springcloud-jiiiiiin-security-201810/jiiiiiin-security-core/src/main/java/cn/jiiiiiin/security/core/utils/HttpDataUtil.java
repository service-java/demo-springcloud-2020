package cn.jiiiiiin.security.core.utils;

import cn.jiiiiiin.security.core.dict.CommonConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jiiiiiin
 */
public class HttpDataUtil {

    public static void respJson(HttpServletResponse response, String jsonStr) throws IOException {
        response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
        response.getWriter().write(jsonStr);
    }
}
