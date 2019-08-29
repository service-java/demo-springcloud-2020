package cn.jiiiiiin.security.core.validate.code.image;

import cn.jiiiiiin.security.core.validate.code.entity.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author jiiiiiin
 */
public class ImageCode extends ValidateCode {

    private static final long serialVersionUID = -5743733411744551242L;
    /**
     * 根据验证码生成的图片
     */
    private BufferedImage image;

    public ImageCode(String code, BufferedImage image, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    /**
     * @param code
     * @param image
     * @param expireIn 多少秒后过期
     */
    public ImageCode(String code, BufferedImage image, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
