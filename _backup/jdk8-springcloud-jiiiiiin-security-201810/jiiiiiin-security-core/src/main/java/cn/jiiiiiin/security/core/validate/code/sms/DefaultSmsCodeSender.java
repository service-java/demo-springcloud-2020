package cn.jiiiiiin.security.core.validate.code.sms;

import cn.jiiiiiin.security.core.validate.code.ValidateCodeController;
import cn.jiiiiiin.security.core.validate.code.sms.SmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认实现
 *
 * @author jiiiiiin
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    final static Logger L = LoggerFactory.getLogger(ValidateCodeController.class);

    @Override
    public void send(String mobilePhone, String validateCode) {
        L.info("向用户手机 {} 发送验证码 {}", mobilePhone, validateCode);
    }
}
