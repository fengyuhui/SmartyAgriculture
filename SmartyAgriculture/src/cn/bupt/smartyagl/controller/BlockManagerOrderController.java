package cn.bupt.smartyagl.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.IPushService;
import cn.bupt.smartyagl.util.DateTag;

@Controller
@RequestMapping("/blockManagerSystem/order")
public class BlockManagerOrderController {

	@Autowired
	IOrderService orderService;
	@Autowired
	IPushService pushService;
	
	int pageSize = Constants.PAGESIZE;// 每一页的大小

	/**
	 * 显示订单列表
	 * 
	 * @author lz_w
	 * @throws UnsupportedEncodingException 
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/unSolvedOrderIndex/{allPages}/{currentPage}/{type}/{starttime}/{endtime}")
	public ModelAndView unSolvedOrderIndex(
			HttpServletRequest request,
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value="starttime")String starttime,
			@PathVariable(value="endtime")String endtime) throws UnsupportedEncodingException {
		ModelAndView modelAndView=new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_UNSOLVED_ORDER_INDEX);
        Date starttimeDate=null;      
        if (!starttime.equals("0000-00-00")) {
        	starttimeDate= (Date) DateTag.stringConvertDate(starttime);
        	starttime=starttime.replaceFirst("-","年");
        	starttime=starttime.replace("-","月");
        	starttime=starttime+"日";
        	modelAndView.addObject("starttime", "\""+starttime+"\"");
        	
		}
        Date endtimeDate = null;
        if(endtime.equals("0000-00-00")){
        	endtimeDate=(Date) new java.util.Date();
        }
        else {
        	endtimeDate=(Date) DateTag.stringConvertDate(endtime);  
        	
        	endtime=endtime.replaceFirst("-","年");
        	endtime=endtime.replace("-","月");
        	endtime=endtime+"日";
        	modelAndView.addObject("endtime", "\""+endtime+"\"");	
		}
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
		BlockManager blockManager = (BlockManager) request.getSession().getAttribute(Constants.SESSION_BLOCK_MANAGER);
        int managerId = blockManager.getId();
        System.out.println("id: "+managerId);

		System.out.println("detail: "+orderService.getOrderDetailByBlockManagerIdAndUnSolved(managerId));
		modelAndView.addObject("orderDetail", orderService.getOrderDetailByBlockManagerIdAndUnSolved(managerId));
        return modelAndView;

	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/SolvedOrderIndex/{allPages}/{currentPage}/{type}/{starttime}/{endtime}")
	public ModelAndView SolvedOrderIndex(
			HttpServletRequest request,
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value="starttime")String starttime,
			@PathVariable(value="endtime")String endtime) throws UnsupportedEncodingException {
		ModelAndView modelAndView=new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_SOLVED_ORDER_INDEX);
        Date starttimeDate=null;      
        if (!starttime.equals("0000-00-00")) {
        	starttimeDate= (Date) DateTag.stringConvertDate(starttime);
        	starttime=starttime.replaceFirst("-","年");
        	starttime=starttime.replace("-","月");
        	starttime=starttime+"日";
        	modelAndView.addObject("starttime", "\""+starttime+"\"");
        	
		}
        Date endtimeDate = null;
        if(endtime.equals("0000-00-00")){
        	endtimeDate=(Date) new java.util.Date();
        }
        else {
        	endtimeDate=(Date) DateTag.stringConvertDate(endtime);  
        	
        	endtime=endtime.replaceFirst("-","年");
        	endtime=endtime.replace("-","月");
        	endtime=endtime+"日";
        	modelAndView.addObject("endtime", "\""+endtime+"\"");	
		}
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
		BlockManager blockManager = (BlockManager) request.getSession().getAttribute(Constants.SESSION_BLOCK_MANAGER);
        int managerId = blockManager.getId();
		modelAndView.addObject("orderDetail", orderService.getOrderDetailByBlockManagerIdAndSolved(managerId));
		
        return modelAndView;

	}
	
	/**
	 * 更改订单详情的状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateOrderDetailStatus/{orderDetailId}")
	@ResponseBody
	public  Map<String, String> updateOrderStatus(@PathVariable(value="orderDetailId") String id,int status) {
		boolean flag=orderService.updateOrderStatus(id, status);
		Map<String, String> returnMap=new HashMap<String, String>();
		if(flag){
			returnMap.put("msg", "修改成功");
		}
		else{
			returnMap.put("msg", "修改失败");
		}
		return returnMap;	
	}
}
