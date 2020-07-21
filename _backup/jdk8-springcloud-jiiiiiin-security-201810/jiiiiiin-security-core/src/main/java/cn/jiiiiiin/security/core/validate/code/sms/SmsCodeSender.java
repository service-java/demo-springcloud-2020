package cn.jiiiiiin.security.core.validate.code.sms;

/**
 * 发送短信验证码接口
 *
 * @author jiiiiiin
 */
public interface SmsCodeSender {

    void send(String mobilePhone, String validateCode);
}
