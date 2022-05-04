package com.blank.ecommerce.filter;

import com.blank.ecommerce.constant.CommonConstant;
import com.blank.ecommerce.util.TokenParseUtil;
import com.blank.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (checkWhiteListUrl(request.getRequestURI())) {
            return true;
        }

        String token = request.getHeader(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception ex) {
            log.error("parse user info from token error. ", ex.getMessage(), ex);
        }

        if (null == loginUserInfo) {
            throw new RuntimeException("cannot parse current user.["+request.getRequestURI()+"]" );
        }

        log.info("set login user info [{}]", request.getRequestURI());
        AccessContext.setLoginUserInfo(loginUserInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (null != AccessContext.getLoginUserInfo()) {
            AccessContext.clearLoginUserInfo();
        }
    }

    private boolean checkWhiteListUrl(String url){
        return StringUtils.containsAny(url,
                "error", "springfox", "swagger", "v2", "v3", "webjars", "doc.html");
    }
}
