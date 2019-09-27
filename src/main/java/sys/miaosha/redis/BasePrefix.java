package sys.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefinx;

    public BasePrefix(String prefinx)
    {
        //默认0代表永不过期
        this(0,prefinx);
    }

    public BasePrefix(int expireSeconds, String prefinx){
        this.expireSeconds = expireSeconds;
        this.prefinx = prefinx;
    }
    @Override
    public int exprreSeconds() {

        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefinx;
    }
}
