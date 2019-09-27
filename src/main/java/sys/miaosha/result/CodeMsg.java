package sys.miaosha.result;

public class CodeMsg {

    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVICE_ERROR = new CodeMsg(500100,"服务端异常");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102,"请求非法");
    public static CodeMsg ACCESS_LIMIT_BASY = new CodeMsg(500103,"访问过于频繁请稍后再试");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211,"登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212,"手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213,"手机号格式不正确");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214,"手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215,"密码错误");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101,"绑定异常:%s");
    public static CodeMsg MIAOSHA_OVER = new CodeMsg(500500,"库存不足");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501,"请勿重复参与");
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单为空");
    public static CodeMsg MIAOSHA_FAIL = new CodeMsg(500502,"秒杀失败");
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code,message);
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
