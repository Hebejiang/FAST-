package sys.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sys.miaosha.domain.MiaoshaOrder;
import sys.miaosha.domain.MiaoshaUser;
import sys.miaosha.redis.RedisService;
import sys.miaosha.result.CodeMsg;
import sys.miaosha.result.Result;
import sys.miaosha.service.GoodsService;
import sys.miaosha.service.MiaoshaService;
import sys.miaosha.service.OrderService;
import sys.miaosha.vo.GoodsVo;

@Service
public class MQReceiver {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String message){
        log.info("receive message"+message);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = miaoshaMessage.getUser();
        long goodsId = miaoshaMessage.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            return;
        }
        //判断是否秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if(order != null){
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user,goods);
    }





//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message){
//        log.info("receive message"+message);
//
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message){
//        log.info("topic queue1 message"+message);
//
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message){
//        log.info("topic queue2 message"+message);
//
//    }
//
//    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
//    public void receiveHeadersQueue(byte[] message){
//        log.info("headers queue message"+new String(message));
//
//    }

}
