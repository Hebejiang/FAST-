package sys.miaosha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;
import sys.miaosha.access.UserContext;
import sys.miaosha.domain.MiaoshaUser;
import sys.miaosha.service.MiaoshaUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    MiaoshaUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        return UserContext.getUser();
//        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
//        String cookieToken = getCookieValue(request,MiaoshaUserService.COOKIE_NAME_TOKEN);
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        return userService.getByToken(response,token);
    }

//    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
//        Cookie[] cookies =  request.getCookies();
//        if(cookies == null || cookies.length <= 0){
//            return null;
//        }
//        for(Cookie cookie : cookies){
//            if(cookie.getName().equals(cookieNameToken)){
//                return cookie.getValue();
//            }
//        }
//        return null;
//    }
}
