package sys.miaosha.redis;

public class AccessKey extends BasePrefix {



    private AccessKey(int expireSeconds, String prefinx) {
        super(expireSeconds,prefinx);
    }

    public static AccessKey withExpire(int expireSeconds){
        return new AccessKey(expireSeconds, "access");
    }


}
