package sys.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix {


    public static final int TOKEN_EXPIRE = 3600*24*2;
    private MiaoshaUserKey(int expireSeconds , String prefinx) {
        super(expireSeconds,prefinx);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");

}
