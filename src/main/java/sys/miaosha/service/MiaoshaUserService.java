package sys.miaosha.service;

import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import sys.miaosha.Util.MD5Util;
import sys.miaosha.Util.UUIDUtil;
import sys.miaosha.dao.MiaoshaUserDao;
import sys.miaosha.domain.MiaoshaUser;
import sys.miaosha.exception.GlobalException;
import sys.miaosha.redis.MiaoshaUserKey;
import sys.miaosha.redis.RedisService;
import sys.miaosha.result.CodeMsg;
import sys.miaosha.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getByMobile(long id){
        return miaoshaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response , LoginVo loginVo){
        if(loginVo == null){
            throw new GlobalException(CodeMsg.SERVICE_ERROR);

        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getByMobile(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass,dbSalt);
        System.out.println(MD5Util.inputPassToFormPass("123456"));
        System.out.println(formPass);
        System.out.println(dbSalt);
        System.out.println(dbPass);
        System.out.println(calcPass);
        String calcPass2 = MD5Util.formPassToDBPass(formPass,dbSalt);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        System.out.println("1");
        String token = UUIDUtil.uuid();
        System.out.println("2");
        addCookie(response,token,user);
        System.out.println("3");
        return true;

    }

    private void addCookie(HttpServletResponse response ,String token , MiaoshaUser user){
        System.out.println("1");
        redisService.set(MiaoshaUserKey.token,token,user);System.out.println("2");
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);System.out.println("3");
        cookie.setMaxAge(MiaoshaUserKey.token.exprreSeconds());System.out.println("4");
        cookie.setPath("/");System.out.println("5");
        response.addCookie(cookie);System.out.println("6");
    }

    public MiaoshaUser getByToken(HttpServletResponse response , String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        //延长有效时间
        if(user != null) {
            addCookie(response,token , user);
        }
        return user;

    }

    public MiaoshaUser getById(long id){
        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById,""+id,MiaoshaUser.class);
        if(user != null){
            return user;
        }
        //取数据库
        user = miaoshaUserDao.getById(id);
        if(user != null){
            redisService.set(MiaoshaUserKey.getById,""+id,user);
        }
        return user;
    }

    public boolean updatePassword(String token,long id, String newPassword){
        MiaoshaUser user = getById(id);
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库         只更新需要更新的字段所以创建新对象
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(newPassword, user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(MiaoshaUserKey.getById, ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);

        return true;
    }
}
