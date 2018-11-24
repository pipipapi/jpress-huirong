package io.jpress.web.filter;

import com.jfinal.core.JFinalFilter;
import com.jfinal.kit.LogKit;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ClassName CharacterFilter
 * @Description TODO
 * @Author daosheng.huang
 * @Date 2018/11/25 0:10
 * @Version 1.0
 */
public class CharacterFilter extends JFinalFilter {


    public void init(FilterConfig filterConfig) throws ServletException {
        LogKit.info("CharacterFilter inited....");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
    }

}