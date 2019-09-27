package sys.miaosha.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;
import sys.miaosha.domain.MiaoshaUser;
import sys.miaosha.domain.User;
import sys.miaosha.redis.GoodsKey;
import sys.miaosha.redis.RedisService;
import sys.miaosha.redis.UserKey;
import sys.miaosha.result.Result;
import sys.miaosha.service.GoodsService;
import sys.miaosha.service.MiaoshaUserService;
import sys.miaosha.vo.GoodsDetailVo;
import sys.miaosha.vo.GoodsVo;
import sys.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list" , produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request,HttpServletResponse response,Model model, MiaoshaUser user/*
                          @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN , required = false) String cookieToken,
                          @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN , required = false) String paramToken*/)
    {
        /*if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return "login";
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        MiaoshaUser user = userService.getByToken(response,token);*/
        //查询商品列表

        ClassPathXmlApplicationContext
        model.addAttribute("user", user);
        //return "goods_list";
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        GoodsVo gl= new GoodsVo();

        model.addAttribute("goodsList",goodsList);
        //手动渲染
        SpringWebContext ctx = new SpringWebContext(request , response , request.getServletContext() , request.getLocale() , model.asMap() , applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list" , ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);

        }
        return html;
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                                        @PathVariable("goodsId")long goodsId){
        //手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if(now < startAt){
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now)/1000);
            //秒杀未开始，倒计时
        }else if(now > endAt){
            miaoshaStatus = 2;
            remainSeconds = -1;
            //秒杀已经结束
        }else{
            miaoshaStatus = 1;
            remainSeconds = 0;
            //秒杀进行中
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);

        //return "goods_detail";
        return Result.success(vo);
    }


    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                         @PathVariable("goodsId")long goodsId){
        model.addAttribute("user",user);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail,""+goodsId,String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        //手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if(now < startAt){
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now)/1000);
            //秒杀未开始，倒计时
        }else if(now > endAt){
            miaoshaStatus = 2;
            remainSeconds = -1;
            //秒杀已经结束
        }else{
            miaoshaStatus = 1;
            remainSeconds = 0;
            //秒杀进行中
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        SpringWebContext ctx = new SpringWebContext(request , response , request.getServletContext() , request.getLocale() , model.asMap() , applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail" , ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,""+goodsId,html);

        }
        //return "goods_detail";
        return html;
    }
}
