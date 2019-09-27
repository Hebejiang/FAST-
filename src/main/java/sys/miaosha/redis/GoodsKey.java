package sys.miaosha.redis;

public class GoodsKey extends BasePrefix {



    private GoodsKey(int expireSeconds,String prefinx) {
        super(expireSeconds,prefinx);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");
    //public static GoodsKey getByName = new GoodsKey("name");
}
