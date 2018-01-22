package cn.bupt.smartyagl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.entity.autogenerate.Users;
import cn.bupt.smartyagl.model.BlockManagerMenuModel;
import cn.bupt.smartyagl.model.BlockManagerModel;
import cn.bupt.smartyagl.service.IBlockManagerMenuService;
import cn.bupt.smartyagl.service.IBlockManagerService;
import cn.bupt.smartyagl.util.MD5Util;

@Controller
@RequestMapping("/blockManagerSystem")
public class BlockManagerPublicController {
	
	@Autowired
	IBlockManagerService blockManagerService;
	
	@Autowired
	IBlockManagerMenuService blockManagerMenuService;
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,BlockManagerModel blockManagerModel) {
		
		ModelAndView modelAndView;
		if ("GET".equals(request.getMethod()) || null == blockManagerModel) {
			modelAndView = new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_LOGIN);
		}else {
			BlockManager blockManager = blockManagerService.findBlockManagerByName(blockManagerModel.getName());
			if (blockManager == null ) {
				request.setAttribute("errorInfo", "该用户不存在！");
				modelAndView = new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_LOGIN);
			} else {
				System.out.println();
				if (!blockManager.getPasswd()
						.equals(MD5Util.MD5(blockManagerModel.getPassword()))) {
					request.setAttribute("errorInfo", "密码错误！");
					modelAndView = new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_LOGIN);
				} else {
                     if(request.getSession().getAttribute(Constants.SESSION_BLOCK_MANAGER)!= null){
                    	 request.getSession().removeAttribute(Constants.SESSION_BLOCK_MANAGER);
                     }
//					sessionSet(request, Constants.SESSION_USER, userList.get(0));
                     request.getSession().setAttribute(Constants.SESSION_BLOCK_MANAGER, blockManager);

					modelAndView = new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_HOME);
//					mav.addObject("user",userList.get(0));
					// 获得菜单
					List<BlockManagerMenuModel> blockManagerMenuModels = blockManagerMenuService.getDisplayMenu();
					modelAndView.addObject("menuList", blockManagerMenuModels);
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
        modelAndView.setViewName( Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_HOME);
        modelAndView.addObject("users", Users);
		return modelAndView;
	}
	
	@RequestMapping("/noPermission")
	public ModelAndView noPermission(HttpServletRequest request) {
		return new ModelAndView( Constants.BLOCK_MANAGER_SYSTEM_ERROR_PAGE );
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
		ModelAndView  modelAndView = new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_LOGIN);
		request.getSession().invalidate();//清除session
		return modelAndView;
	}
	
}
