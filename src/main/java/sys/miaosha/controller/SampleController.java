package sys.miaosha.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.miaosha.domain.User;
import sys.miaosha.rabbitmq.MQSender;
import sys.miaosha.redis.RedisService;
import sys.miaosha.redis.UserKey;
import sys.miaosha.result.Result;
import sys.miaosha.service.UserService;

@Controller
@RequestMapping("/nmsl")
public class SampleController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

//    @RequestMapping("/mq/headers")
//    @ResponseBody
//    public Result<String> headsers(){
//        sender.sendHeaders("rsndm");
//        return Result.success("hellowdnmd");
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> fanout(){
//        sender.sendFanout("nsnmn");
//        return Result.success("hellowdnmd");
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> topic(){
//        sender.sendTopic("wdnmd");
//        return Result.success("hellonmsl");
//    }
//
//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result<String> mq(){
//        sender.send("wdnmd");
//        return Result.success("nmslwsnd");
//    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
       User user = redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById,""+1,user);//UserKey:id1
        return Result.success(true);
    }
}
