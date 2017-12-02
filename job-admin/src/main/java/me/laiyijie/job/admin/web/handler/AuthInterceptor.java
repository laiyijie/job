package me.laiyijie.job.admin.web.handler;

import me.laiyijie.job.admin.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by laiyijie on 12/2/17.
 */
public class AuthInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.debug("in pre handle , request path :  " + httpServletRequest.getRequestURI());
        String username = (String) httpServletRequest.getSession().getAttribute("ADMIN_USER");
        if (username != null)
            return true;
        else {
            httpServletResponse.setStatus(401);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
