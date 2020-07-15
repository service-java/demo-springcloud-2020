package cn.jiiiiiin.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * AuthenticationException是框架身份认证处理异常的基类
 *
 * @author jiiiiiin
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
