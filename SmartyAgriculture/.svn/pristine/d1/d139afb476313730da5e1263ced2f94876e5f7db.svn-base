package cn.bupt.smartyagl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    /**
     * 获取session
     * 
     * @param request
     * @return
     */
    public HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    /**
     * 获取session
     * 
     * @param attr
     * @param request
     * @return
     */
    public Object sessionGet(String attr, HttpServletRequest request) {
        return request.getSession().getAttribute(attr);
    }

    /**
     * 设置session
     * 
     * @param name
     * @param value
     * @param request
     */
    public void sessionSet(String name, String value, HttpServletRequest request) {
        request.getSession().setAttribute(name, value);
    }
    /**
     * 设置session
     * @param name
     * @param value
     * @param request
     */
    public void sessionSet(String name, Object value, HttpServletRequest request) {
        request.getSession().setAttribute(name, value);
    }

    /**
     * 清除session
     * 
     * @param name
     * @param request
     */
    public void sessionRemove(String name, HttpServletRequest request) {
        request.getSession().removeAttribute(name);
    }
}
