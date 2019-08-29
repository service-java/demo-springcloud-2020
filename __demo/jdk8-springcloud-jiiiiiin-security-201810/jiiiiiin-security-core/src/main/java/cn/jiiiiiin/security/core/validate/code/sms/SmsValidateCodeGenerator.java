package cn.jiiiiiin.security.core.validate.code.sms;

import cn.jiiiiiin.security.core.properties.SecurityProperties;
import cn.jiiiiiin.security.core.validate.code.ValidateCodeGenerator;
import cn.jiiiiiin.security.core.validate.code.entity.ValidateCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import static cn.jiiiiiin.security.core.dict.SecurityConstants.DEFAULT_PARAMETER_NAME_EXPIRE_IN;

/**
 * 默认的图形验证码生成器
 *
 * @author jiiiiiin
 */
@Component
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    final static Logger L = LoggerFactory.getLogger(SmsValidateCodeGenerator.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        final int expireIn = ServletRequestUtils.getIntParameter(request.getRequest(), DEFAULT_PARAMETER_NAME_EXPIRE_IN, securityProperties.getValidate().getSmsCode().getExpireIn());
        L.debug("短信验证码有效期 {}", expireIn);
        // 生成验证码
        final String code = RandomStringUtils.randomNumeric(Integer.parseInt(securityProperties.getValidate().getSmsCode().getLength()));
        final ValidateCode validateCode = new ValidateCode(code, expireIn);
        return validateCode;
    }
}
