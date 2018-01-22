package cn.bupt.smartyagl.controller;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.User;
import cn.bupt.smartyagl.model.UserModel;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.IUserService;
import cn.bupt.smartyagl.util.DateTag;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	IUserService userService;
	@Autowired
	IOrderService orderService;
	
	int pageSize=Constants.PAGESIZE;//，每一页的大小
	int pageSizeSmall=Constants.PAGESIZE_SMALL;//分页，每一页数目比较少
	
	/**
	 * 获取会员列表
	 * 分页功能
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/index/{allPages}/{currentPage}/{type}/{userName}/{phoneNumber}")
	public ModelAndView userIndex(@PathVariable(value="allPages") int allPages,
			@PathVariable(value="currentPage") int currentPage,
			@PathVariable(value="type") String type,
			@PathVariable(value="userName")String userName,
			@PathVariable(value="phoneNumber")String phoneNumber) throws UnsupportedEncodingException{
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
		
		userName = new String(userName.getBytes("iso8859-1"), "UTF-8");
		phoneNumber = new String(phoneNumber.getBytes("iso8859-1"), "UTF-8");
		
		ModelAndView modelAndView=new ModelAndView(Constants.USER_INDEX);
		
		Page page = PageHelper.startPage(currentPage, pageSize, "id");
		
		List<User> userList=userService.getUserList(userName,phoneNumber);
		
		modelAndView.addObject("userList",userList);
		//总页数
		allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
		
		if (!userName.equals("1")) {
			modelAndView.addObject("userName", userName);
		} else {
			modelAndView.addObject("userName", "");
		}
		if (!phoneNumber.equals("1")) {
			modelAndView.addObject("phoneNumber", phoneNumber);
		} else {
			modelAndView.addObject("phoneNumber", "");
		}
		
		return modelAndView;
	}
	/**
	 * 删除用户
	 */
	@RequestMapping(value="/delete/{currentPage}/{userId}")
	public ModelAndView deleteUserById(@PathVariable(value="currentPage") int currentPage,
			@PathVariable(value="userId") Integer userId){
		if(userId!=null&&userService.deleteUserById(userId)){
			ModelAndView modelAndView=new ModelAndView(Constants.USER_INDEX);
			userService.getUserList(null,null);
			return modelAndView;
		}
		else {
			return null;
		}	
	}
	/**
	 * 查看用户信息
	 * @author waiting
	 */
	@RequestMapping(value="/detail/{userId}/{allPages}/{currentPage}/{type}")
	public ModelAndView userInfo(@PathVariable(value="userId") Integer userId,
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type){
		
		if ("prvious".equals(type)) {
			if (currentPage > 1) {// 第一页不能往前翻页
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
		ModelAndView modelAndView=new ModelAndView(Constants.USER_DETAIL);
		User User=userService.getUserInfoById(userId);
		UserModel userInfoModel=new UserModel();
		userInfoModel.setUserInfo(User);
		//转换时间
		String createString=DateTag.dateTimaFormat(User.getCreateTime());
		userInfoModel.setCreateTimeString(createString);
		
		modelAndView.addObject("user", userInfoModel);
		orderService.getOrderList(userId, modelAndView, currentPage, pageSize, "buyTime desc", null, null,"");
		return modelAndView;	
	}
	/**
	 * 编辑用户信息
	 * @author wlz
	 */
	@RequestMapping(value="/edit/{userId}")
	public String  editUserInfo(@PathVariable(value="userId")Integer userId,
	        Model model,HttpServletRequest request){
//		String requestMethod=request.getMethod();
//		if("GET".equals(requestMethod)){
			//get请求方法，用户编辑跳转
		User UserInfo=userService.getUserInfoById(userId);
			    model.addAttribute("user", UserInfo);
			    return Constants.USER_EDIT;
//		}
//		else if("POST".equals(requestMethod)){
//			//get请求方法，用户编辑跳转
//			user UserInfo=userService.getUserInfoById(userId);
//			model.addAttribute("user", UserInfo);
//			return Constants.USER_EDIT;
//		}
//		return null;
		
	}
	
	/*
	 * 更新用户信息
	 */
	@RequestMapping(value="/update/{userId}")
	public ModelAndView updateUserInfo(@PathVariable(value="userId")Integer userId,
	       HttpServletRequest request) throws UnsupportedEncodingException {
	    
	         
		User User=userService.getUserInfoById(userId);
	        
	        if(!(request.getParameter("userName").isEmpty())) 
	            User.setName(request.getParameter("userName"));
	        /*if(!(request.getParameter("password").isEmpty())) 
	            User.setPasswd(request.getParameter("password"));*/
	        if(!(request.getParameter("phoneNumber").isEmpty())) 
	            User.setPhone(request.getParameter("phoneNumber"));
//	        if(!(request.getParameter("money").isEmpty()))
//	            User.setMoney((Double.parseDouble(request.getParameter("money"))));
//	        if(!(request.getParameter("score").isEmpty()))
//	            User.setScore(Integer.parseInt(request.getParameter("score")));
	        
	        userService.updateMyuserInfo(User);
	        ModelAndView modelAndView = userIndex(0,1,"prvious","1","1"); 
            return modelAndView;
    }
	
	
	
	
//	public  void printUser(user User) {
//		System.out.print("shchu");
//		System.out.print(User.getName());
//		System.out.print(User.getId());
//		System.out.print(User.getPhone());
//	}
	/**
	 * 修改用户状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateUserStatus")
	@ResponseBody
	public Map<String,String> updateUserStatus(Integer id,Integer status){
		boolean flag=userService.UpdateUserStatus(id, status);
		Map<String, String> resultMap=new HashMap<String, String>();
		if (flag) {
			resultMap.put("msg", "修改成功");
		}
		else{
			resultMap.put("msg", "修改失败");
		}
		return resultMap;
	}
	/**
	 * 会员统计
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @param status 订单状态
	 * @param timeFlag 时间标志
	 * @return
	 */
	@RequestMapping(value = "/userStatisticsIndex/{allPages}/{currentPage}/{type}/{status}/{starttime}/{endtime}")
	public ModelAndView userStatistics(@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value = "status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value="endtime") String endtime
			) {
		ModelAndView modelAndView=new ModelAndView(Constants.USER_STATISTICS);
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
		if(starttime.equals("0")){
			starttime=null;
		}
		if(endtime.equals("0")){
			endtime=null;
		}
		userService.getUserStatistics(modelAndView,currentPage, pageSize,status,starttime,endtime);
		modelAndView.addObject("status", status);
		//返回时间
		if ((starttime!=null)&&(!starttime.equals("0"))) {
        	starttime=starttime.replaceFirst("-","年");
        	starttime=starttime.replace("-","月");
        	starttime=starttime+"日";
        	modelAndView.addObject("starttime", "\""+starttime+"\"");	
		}
		if ((endtime!=null)&&(!endtime.equals("0"))) {
			endtime=endtime.replaceFirst("-","年");
			endtime=endtime.replace("-","月");
			endtime=endtime+"日";
        	modelAndView.addObject("endtime", "\""+endtime+"\"");	
		}
		return modelAndView;
	}
	@RequestMapping(value = "/exportExcelUserStatistics/{status}/{starttime}/{endtime}")
	public void ExportExcelUserStatistics(
			@PathVariable(value="status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value="endtime") String endtime,
			HttpServletResponse response){
		userService.exportExcelUserStatistics(status, starttime,endtime, response);
	}
	
}
