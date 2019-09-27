package sys.miaosha.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import sys.miaosha.domain.MiaoshaOrder;
import sys.miaosha.domain.OrderInfo;

@Mapper
@Repository
public interface OrderDao {

    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(@Param("userId")long userId,@Param("goodsId")long goodsId);

    //keyColumn 数据库的列 keyProperty domin所对应数据库列的属性 resultType 结果值类型
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order(user_id,goods_id,order_id)value(#{userId},#{goodsId},#{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id=#{orderId}")
    OrderInfo getOrderById(@Param("orderId")long orderId);
}
