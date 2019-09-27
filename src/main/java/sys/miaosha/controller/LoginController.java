package sys.miaosha.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import sys.miaosha.Util.ValidatorUtil;
import sys.miaosha.domain.User;
import sys.miaosha.redis.RedisService;
import sys.miaosha.redis.UserKey;
import sys.miaosha.result.CodeMsg;
import sys.miaosha.result.Result;
import sys.miaosha.service.MiaoshaUserService;
import sys.miaosha.service.UserService;
import sys.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String tologin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> dologin(@Valid LoginVo loginVo , HttpServletResponse response){
        log.info(loginVo.toString());
        /*
        String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if(StringUtils.isEmpty(passInput))
        {
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile))
        {
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile))
        {
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/
        userService.login(response,loginVo);
        return Result.success(true);
        /*
        if(cm.getCode() == 0){
           return Result.success(true);
        }else{
            return Result.error(cm);
        }
           */
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
