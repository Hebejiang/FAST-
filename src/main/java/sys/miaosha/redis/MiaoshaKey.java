package sys.miaosha.redis;

public class MiaoshaKey extends BasePrefix {



    private MiaoshaKey(int expireSeconds, String prefinx) {
        super(expireSeconds,prefinx);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60,"mp");
    public static KeyPrefix getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");

}
