package cn.bupt.smartyagl.controller.inf;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

@Controller
public class AppBaseController {

    /**
     * 获取userId
     * 
     * @param request
     * @return
     */
    public BigDecimal getUserId(HttpServletRequest request) {
        return (BigDecimal)request.getAttribute("userId");
    }
}
