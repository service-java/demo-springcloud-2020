package cn.jiiiiiin.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 框架验证码配置类
 *
 * @author jiiiiiin
 */
@Setter
@Getter
public class ValidateCodeProperties {

    private ImageCodeProperties imageCode = new ImageCodeProperties();
    private SmsCodeProperties smsCode = new SmsCodeProperties();

}
