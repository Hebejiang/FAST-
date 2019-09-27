package sys.miaosha.Util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    //将字符串MD5加密
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String input,String saltDB) {
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass,saltDB);
        return dbPass;
    }
    /*
    8694dd505f2f2ee9f32a7262eec95b1d
    d3b1294a61a07da9b49b6e22b2cbd7f9
    1a2b3c4d
    68156ba662e422b83001bdc017eb83bc
    859212df160fcc31906497f6c1a981ae
     */
    public static void main(String[] args){
        //System.out.println(inputPassToDBPass("123456",salt));
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass(inputPassToFormPass("123456"),"1a2b3c4d"));
    }
}
