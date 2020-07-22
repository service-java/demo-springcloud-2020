package com.spring.cloud.ms.common.key;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

// 3DES加密和解密工具类
public class DesCoderUtils {

    /**
     * 转换成十六进制字符串
     * @param key 阴匙
     * @return 十六进制字符串
     */
    private static byte[] hex(String key){
        String f = DigestUtils.md5DigestAsHex(key.getBytes());
        byte[] bkeys = new String(f).getBytes();
        byte[] enk = new byte[24];
        for (int i=0;i<24;i++){
            enk[i] = bkeys[i];
        }
        return enk;
    }

    /**
     * 3DES加密
     * @param key -- 阴匙
     * @param srcStr -- 明文
     * @return 密文
     */
    public static String  encode3Des(String key,String srcStr){
        byte[] keybyte = hex(key);
        byte[] src = srcStr.getBytes();
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            //加密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            String pwd = Base64.encodeBase64String(c1.doFinal(src));
            return pwd;
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 3DES解密
     * @param key -- 阴匙
     * @param desStr -- 密文
     * @return 明文
     */
    public static String decode3Des(String key, String desStr){
        Base64 base64 = new Base64();
        byte[] keybyte = hex(key);
        byte[] src = base64.decode(desStr);
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            //解密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            String pwd = new String(c1.doFinal(src));
            return pwd;
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SnowFlakeWorker worker = new SnowFlakeWorker(1002L);
        Long id = worker.nextId();
        System.out.println(id);
    }
}
