package sys.miaosha.redis;

public interface KeyPrefix {

    //过期时间
    public int exprreSeconds();

    public String getPrefix();

}
