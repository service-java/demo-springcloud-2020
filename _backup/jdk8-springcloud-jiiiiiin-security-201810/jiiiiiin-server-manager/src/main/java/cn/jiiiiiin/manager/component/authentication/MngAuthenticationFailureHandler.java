package cn.jiiiiiin.manager.component.authentication;

import cn.jiiiiiin.security.browser.component.authentication.BrowserAuthenticationFailureHandler;
import cn.jiiiiiin.security.browser.utils.HttpUtils;
import cn.jiiiiiin.security.core.dict.CommonConstants;
import cn.jiiiiiin.security.core.support.SimpleResponse;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 登录失败处理接口
 *
 * @author jiiiiiin
 */
@Slf4j
@Component("authenticationFailureHandler")
public class MngAuthenticationFailureHandler extends BrowserAuthenticationFailureHandler {

    @Override
    protected void respJson(HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
        // 将authentication转换成json str输出
        response.getWriter().write(objectMapper.writeValueAsString(R.failed(exception.getMessage())));
    }
}
