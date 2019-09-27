package sys.miaosha.redis;

public class OrderKey extends BasePrefix {
    public OrderKey(String prefinx) {
        super(prefinx);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}
