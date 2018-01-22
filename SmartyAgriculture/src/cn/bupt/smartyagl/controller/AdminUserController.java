package cn.bupt.smartyagl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.entity.autogenerate.AdminUser;
import cn.bupt.smartyagl.entity.autogenerate.Auth;
import cn.bupt.smartyagl.entity.autogenerate.UserAuthView;
import cn.bupt.smartyagl.service.IAdminUserService;
import cn.bupt.smartyagl.util.MD5Util;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-8-30 上午10:06:41 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("/adminUser")
public class AdminUserController extends BaseController{
	
	@Autowired
	IAdminUserService adminService;
	
	int pageSize=Constants.PAGESIZE;//，每一页的大小
	int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
	
	 /**
     * 显示权限列表
     * @author jm
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{type}")
    public ModelAndView adminIndex(HttpServletRequest request,@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage,
            @PathVariable(value="type") String type,AdminUser adminSelective) {
        if ("prvious".equals(type)) {
            if( currentPage > 1 ){//第一页不能往前翻页
                currentPage--;
            }
        } else if ("next".equals(type)) {
            currentPage++;
        } else if ("first".equals(type)) {
            currentPage = 1;
        } else if ("last".equals(type)) {
            currentPage = allPages;
        } else {
            currentPage = Integer.parseInt(type);
        }
        
        ModelAndView modelAndView=new ModelAndView(Constants.Auth_INDEX);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<Integer> list = new ArrayList<Integer>();
        list.add(ConstantsSql.Auth_Check);//获取审核员
        list.add(ConstantsSql.Auth_Operate);//操作员
        List<AdminUser> adminList = adminService.getAdminList(adminSelective,list);
        System.out.println(adminList.size());
        modelAndView.addObject("adminList",adminList);
        //总页数
        allPages = page.getPages();
        modelAndView.addObject("allPages", allPages);
        // 当前页码
        currentPage = page.getPageNum();
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("resultMsg", request.getParameter("resultMsg"));
        return modelAndView;
    }

    /**
     * 添加权限用户
     * @param request
     * @param Address
     * @return
     */
  	@RequestMapping("/addAdmin")
  	public ModelAndView addAdmin(HttpServletRequest request,AdminUser adminUser,@RequestParam(name="autherList[]",required=false) List<Integer> autherList){
  		ModelAndView mv = new ModelAndView(Constants.Auth_ADD);
  		//添加管理员
  		if(adminUser != null && adminUser.getName() != null){
  			String passwd =  adminUser.getPasswd();
  	  		adminUser.setPasswd( MD5Util.MD5(passwd) );
  	  		boolean rst = adminService.addAdmin(adminUser,autherList);
  	  		if(rst){
  	  			mv.addObject("resultMsg", "添加用户成功");
  	  		}else{
  	  			mv.addObject("resultMsg", "改用户已存在");
  	  		}
  	  		//跳转到首页
  	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  	  		mv.setView(redirectView);
  		}else{//添加权限
  			List<Auth> authList = adminService.getUserAuthViewName();
  	  		mv.addObject("authList", authList);
  		}
  		return mv;
  	}
  	
  	 /**
     * 查询权限用户
     * @param request
     * @param Address
     * @return
     */
  	@RequestMapping("/selectAdmin")
  	public ModelAndView selectAdmin(HttpServletRequest request,String name){
  		AdminUser adminUser = adminService.fidnAdminUser(name);
  		ModelAndView mv = new ModelAndView(Constants.Auth_Find);
  		mv.addObject("adminUser", adminUser);
  		List<Auth> authList = adminService.getUserAuthViewName();
  		mv.addObject("authList", authList);
  		return mv;
  	}
  	
  	/**
     * 添加草稿
     * @param request
     * @param Address
     * @return
     */
  	@RequestMapping("/update")
  	public ModelAndView update(HttpServletRequest request,AdminUser adminUser,@RequestParam(name="autherList[]",required=false) List<Integer> autherList,Integer postType){
  		ModelAndView mv = new ModelAndView(Constants.Auth_EDIT);
  		//修改管理员
  		if( postType!= null && postType == 1){
  			if(adminUser.getPasswd() != null && !adminUser.getPasswd().equals("")){
  				String passwd =  adminUser.getPasswd();
  	  	  		adminUser.setPasswd( MD5Util.MD5(passwd) );
  			}else{
  				adminUser.setPasswd(null);
  			}
  	  		boolean rst = adminService.updateAdmin(adminUser,autherList);
  	  		if(rst){
  	  			mv.addObject("resultMsg", "修改用户成功");
  	  		}else{
  	  			mv.addObject("resultMsg", "修改用户失败");
  	  		}
  	  		//跳转到首页
  	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  	  		mv.setView(redirectView);
  		}
  		List<UserAuthView> uvs = adminService.getUserAuthViewName(adminUser.getId());
  		Map<Integer,Boolean> hasAuth = new HashMap<Integer, Boolean>();
  		for(UserAuthView uv : uvs){
  			hasAuth.put(uv.getAuth_id(), true);
  		}
  		List<Auth> authList = adminService.getUserAuthViewName();
  		mv.addObject("authList",authList);
  		mv.addObject("hasAuth",hasAuth);
  		mv.addObject("user",adminService.findAdminUser(adminUser.getId()));
  		return mv;
  	}
  	
  	//删除地址
  	@RequestMapping("/deleteAdmin")
  	@ResponseBody
  	public Map<String, String> deleteAdmin(HttpServletRequest request,Integer adminId){
  		boolean rs = adminService.deleteAdmin(adminId);
  		
  		return null;
  	}
}
