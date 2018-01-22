package cn.bupt.smartyagl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.AdminUser;
import cn.bupt.smartyagl.entity.autogenerate.Users;
import cn.bupt.smartyagl.model.MenuModel;
import cn.bupt.smartyagl.service.IAdminUserService;
import cn.bupt.smartyagl.service.IMenuService;
import cn.bupt.smartyagl.util.MD5Util;

@Controller
@RequestMapping("/public")
public class PublicController extends BaseController {
	
	@Autowired
	IMenuService menuService;
	
	@Autowired
	IAdminUserService adminUserService;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,Users Users) {
		
		ModelAndView modelAndView;
		if ("GET".equals(request.getMethod()) || null == Users) {
			modelAndView = new ModelAndView(Constants.PUBLIC_LOGIN);
		}else {
			AdminUser adminUser = adminUserService.fidnAdminUser(Users.getName());
			if (adminUser == null ) {
				request.setAttribute("errorInfo", "该用户不存在！");
				modelAndView = new ModelAndView(Constants.PUBLIC_LOGIN);
			} else {
				System.out.println();
				if (!adminUser.getPasswd()
						.equals(MD5Util.MD5(Users.getPasswd()))) {
					request.setAttribute("errorInfo", "密码错误！");
					modelAndView = new ModelAndView(Constants.PUBLIC_LOGIN);
				} else {
                     if(request.getSession().getAttribute(Constants.SESSION_USER)!= null){
                    	 request.getSession().removeAttribute(Constants.SESSION_USER);
                     }
//					sessionSet(request, Constants.SESSION_USER, userList.get(0));
                     request.getSession().setAttribute(Constants.SESSION_USER, adminUser);

					modelAndView = new ModelAndView(Constants.PUBLIC_HOME);
//					mav.addObject("user",userList.get(0));
					// 获得菜单
					List<MenuModel> menuList = menuService.getDisplayMenu();
					modelAndView.addObject("menuList", menuList);
//
//					userService.getUserRoleMenus(request);

				}
			}
		}
		return modelAndView;
	}
	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request,Users Users) {
		ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName( Constants.PUBLIC_HOME);
        modelAndView.addObject("users", Users);
		return modelAndView;
	}
	
	@RequestMapping("/noPermission")
	public ModelAndView noPermission(HttpServletRequest request) {
		return new ModelAndView( Constants.ERROR_PAGE );
	}
	
	@RequestMapping("/test")
	public ModelAndView test(HttpServletRequest request,String test) {
		System.out.println(request.getAttribute("userId"));
		return new ModelAndView();
	}
	/**
	 * 退出
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginOut")
	public ModelAndView loginOut(HttpServletRequest request) {
		ModelAndView  modelAndView = new ModelAndView(Constants.PUBLIC_LOGIN);
		request.getSession().invalidate();//清楚session
		return modelAndView;
	}
}
