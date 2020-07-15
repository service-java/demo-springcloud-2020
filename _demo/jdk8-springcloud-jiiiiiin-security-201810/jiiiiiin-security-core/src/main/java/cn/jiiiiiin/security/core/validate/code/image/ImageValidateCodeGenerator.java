package cn.jiiiiiin.security.core.validate.code.image;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.properties.SecurityProperties;
import cn.jiiiiiin.security.core.validate.code.ValidateCodeGenerator;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.image.BufferedImage;

/**
 * 默认的图形验证码生成器
 *
 * @author jiiiiiin
 */
public class ImageValidateCodeGenerator implements ValidateCodeGenerator {

    final static Logger L = LoggerFactory.getLogger(ImageValidateCodeGenerator.class);

    private final SecurityProperties securityProperties;

    private final Producer captchaProducer;

    public ImageValidateCodeGenerator(SecurityProperties securityProperties, Producer captchaProducer) {
        this.securityProperties = securityProperties;
        this.captchaProducer = captchaProducer;
    }

    @Override
    public ImageCode generate(ServletWebRequest request) {
        final int expireIn = ServletRequestUtils.getIntParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_EXPIRE_IN, securityProperties.getValidate().getImageCode().getExpireIn());
        // 生成验证码
        final String capText = captchaProducer.createText();
        final BufferedImage bi = captchaProducer.createImage(capText);
        final ImageCode imageCode = new ImageCode(capText, bi, expireIn);

        L.info("图形验证码 {} 有效期 {}", capText, expireIn);
        return imageCode;
    }
}
