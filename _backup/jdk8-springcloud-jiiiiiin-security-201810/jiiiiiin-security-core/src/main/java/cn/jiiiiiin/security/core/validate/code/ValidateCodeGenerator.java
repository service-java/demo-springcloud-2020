package cn.jiiiiiin.security.core.validate.code;

import cn.jiiiiiin.security.core.validate.code.entity.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author jiiiiiin
 */
public interface ValidateCodeGenerator {

    /**
     * 生成图形验证码
     *
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
