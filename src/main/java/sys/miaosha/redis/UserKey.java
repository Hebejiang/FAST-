package sys.miaosha.redis;

public class UserKey extends BasePrefix {



    private UserKey(String prefinx) {
        super(prefinx);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
