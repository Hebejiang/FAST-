package sys.miaosha.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.miaosha.domain.MiaoshaUser;
import sys.miaosha.domain.OrderInfo;
import sys.miaosha.domain.User;
import sys.miaosha.redis.RedisService;
import sys.miaosha.redis.UserKey;
import sys.miaosha.result.CodeMsg;
import sys.miaosha.result.Result;
import sys.miaosha.service.GoodsService;
import sys.miaosha.service.MiaoshaUserService;
import sys.miaosha.service.OrderService;
import sys.miaosha.vo.GoodsVo;
import sys.miaosha.vo.LoginVo;
import sys.miaosha.vo.OrderDetailVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);



    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> detail(Model model, MiaoshaUser user, @RequestParam("orderId")long orderId){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setGoods(goodsVo);
        vo.setOrder(order);
        return Result.success(vo);
    }

}
