package cn.jiiiiiin.security.core.validate.code;

import cn.jiiiiiin.security.core.properties.SecurityProperties;
import cn.jiiiiiin.security.core.validate.code.sms.DefaultSmsCodeSender;
import cn.jiiiiiin.security.core.validate.code.image.ImageValidateCodeGenerator;
import cn.jiiiiiin.security.core.validate.code.sms.SmsCodeSender;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiiiiiin
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private Producer captchaProducer;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        final ImageValidateCodeGenerator imageValidateCodeGenerator = new ImageValidateCodeGenerator(securityProperties, captchaProducer);
        return imageValidateCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeSender")
    public SmsCodeSender smsCodeSender() {
        final DefaultSmsCodeSender defaultSmsCodeSender= new DefaultSmsCodeSender();
        return defaultSmsCodeSender;
    }


}
