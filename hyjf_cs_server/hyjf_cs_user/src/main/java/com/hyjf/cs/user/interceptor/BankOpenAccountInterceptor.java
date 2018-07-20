/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.interceptor;

import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.WebUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangqingqing
 * @version BankOpenAccountInterceptor, v0.1 2018/5/31 16:02
 */
public class BankOpenAccountInterceptor implements HandlerInterceptor {

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *
     * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS + token, WebViewUserVO.class);
        // 获取登录的用户
        if (null == webViewUserVO) {
            // 如果用户没有登录
            WebUtils.redirectTargetPage(response, "/user/login/init");
        } else {
                // 判断该用户是否汇付已经开户
                if (webViewUserVO.isBankOpenAccount()) {
                    return true;
                } else {
                    // 重定向到开户页面
                    WebUtils.redirectTargetPage(response, "/bank/hyjf-web/user/bankopen/init");
                }
        }
        return false;
    }
}

