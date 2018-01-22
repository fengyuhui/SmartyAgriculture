package cn.bupt.smartyagl.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisits;
import cn.bupt.smartyagl.entity.autogenerate.Project;
import cn.bupt.smartyagl.service.FarmVisitOrderViewService;
import cn.bupt.smartyagl.service.IFarmVisitOrderService;
import cn.bupt.smartyagl.service.impl.FarmVisitsServiceImpl;
import cn.bupt.smartyagl.service.impl.ProjectServiceImpl;
import cn.bupt.smartyagl.util.DateTag;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/**
 * 参观农场类型信息的创建及修改
 * @author TMing
 *
 */

@Controller	
@RequestMapping("/farmVisits")
public class FarmVisitsController {
	@Autowired
	private FarmVisitOrderViewService farmVisitOrderViewService;
	@Autowired
	private FarmVisitsServiceImpl farmVisitsService;
	@Autowired 
	private ProjectServiceImpl projectService;
	private int pageSize = Constants.PAGESIZE;
	@Autowired
	private IFarmVisitOrderService farmVisitOrderService;
	
	@RequestMapping(value = "/getAllOrders/{allPages}/{currentPage}/{type}/{starttime}/{endtime}")
	public ModelAndView getAllOrders(@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value="starttime")String starttime,
			@PathVariable(value="endtime")String endtime) {
		ModelAndView mv = new ModelAndView(Constants.FARMVISIT_ORDER_LISt);
        Date starttimeDate=null;      
        if (!starttime.equals("0000-00-00")) {
        	starttimeDate= (Date) DateTag.stringConvertDate(starttime);
        	starttime=starttime.replaceFirst("-","年");
        	starttime=starttime.replace("-","月");
        	starttime=starttime+"日";
        	mv.addObject("starttime", "\""+starttime+"\"");
        	
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
        	mv.addObject("endtime", "\""+endtime+"\"");	
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
		farmVisitOrderViewService.getOrderList(mv, currentPage, pageSize, "visitTime desc",starttimeDate
				,endtimeDate);

        //订单状态
		Map<Integer, String> orderStatusMap=new HashMap<Integer, String>();
		return mv;
	}
	@RequestMapping("/updateOrderStatus")
	@ResponseBody
	public Map<String,String> updateOrderStatus(@RequestBody FarmVisitOrder or) {
//		System.out.println("id:"+or.getId());
//		System.out.println("status:"+or.getOrderStatus());
		Map<String,String> map = new HashMap<String, String>();
		if(farmVisitOrderService.updateOrderStatus(or))
			map.put("status", "修改成功！");
		else
			map.put("status", "修改失败！");
		return map;
	}
	@RequestMapping("/exportExcelOrder/{starttime}/{endtime}")
	public void exportExcel(
			@PathVariable(value="starttime") String starttime,
			@PathVariable(value="endtime") String endtime,
			HttpServletResponse response) {
		System.out.println("starttime:" + starttime);
		System.out.println("endtime:" + endtime);
		Date starttimeDate=null;      
        if (!starttime.equals("0000-00-00")) {
        		starttimeDate= (Date) DateTag.stringConvertDate(starttime);	
		}
        Date endtimeDate = null;
        if(endtime.equals("0000-00-00")){
        	endtimeDate=(Date) new java.util.Date();
        }
        else if(starttime!=null){
        	endtimeDate=(Date) DateTag.stringConvertDate(endtime);  	
		}
        Long st = System.currentTimeMillis();
		farmVisitOrderViewService.exportExcelOrder(starttimeDate, endtimeDate, response);
		long end = System.currentTimeMillis();
		System.out.println("总耗时："+(end-st)/1000 +"s");
	}
	/**
	 * 列出所有的已审核参观类型
	 * @return
	 */
	@RequestMapping(value="/index/{allPages}/{currentPage}/{type}")
	public ModelAndView listVisitTypes(
			@PathVariable(value="allPages" ) int allPages,
			@PathVariable(value="currentPage") int currentPage,
			@PathVariable(value="type") String type) {
		
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
		ModelAndView mv = new ModelAndView("farmVisits/projectIndex");
		Page page = PageHelper.startPage(currentPage, pageSize);
		ArrayList<Project> list0 = (ArrayList<Project>) projectService.getAllByStatus(Constants.NORMAL);
		ArrayList<Project> list3 = (ArrayList<Project>) projectService.getAllByStatus(Constants.NORMAL_WITH_EDITED);
		List<Project> list = new ArrayList<Project>();
		list.addAll(list0);
		list.addAll(list3);
		
		mv.addObject("project", list);
		allPages = page.getPages();
		mv.addObject("allPages", allPages);
		// 当前页码
		currentPage = page.getPageNum();
		mv.addObject("currentPage", currentPage);
		return mv;		
	}
	
	/**
	 * 添加参观农场类型信息
	 * @param request
	 * @param farmVisits
	 * @return
	 */
	@RequestMapping(value="/addVisitType", method=RequestMethod.GET)
	public ModelAndView createVisitTypePage() {
		// 获取提交数据页面请求的视图
		ModelAndView mv = new ModelAndView("farmVisits/addType");
		return mv;
	}
	
	/**
	 * 获取修改参观农场项目页面
	 */
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public ModelAndView updatePage(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("farmVisits/editProject");
		Project project = projectService.getType(id);
		String imgPathStr = project.getImages();
		String idStr = project.getTypes();
		
		ObjectMapper mapper = new ObjectMapper();
		List<String> imgPathList = new ArrayList<String>();
		List<Integer> typeIdList = new ArrayList<Integer>();
		// 将Json字符串转换为List对象
		try {
			imgPathList = mapper.readValue(imgPathStr, new TypeReference<List<String>>() {});
			typeIdList = mapper.readValue(idStr, new TypeReference<List<Integer>>() {});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		mv.addObject("id", id);
		mv.addObject("title", project.getTitle());
		mv.addObject("detail", project.getDetail());
		mv.addObject("pictureList", imgPathList);
		
		List<FarmVisits> typeList = new ArrayList<FarmVisits>();
		typeList = farmVisitsService.getFarmVisitTypesBatch(typeIdList);
		mv.addObject("typeList" , typeList);	
		return mv;		
	}
	
	@RequestMapping(value="/updatePost/{id}", method=RequestMethod.POST)
	public ModelAndView updatePage(@PathVariable Integer id, String title, String detail, Integer[] typeId, String[] typeTitle, 
			Double[] price, String[] typeTitleNew, Double[] priceNew, @RequestParam("file") MultipartFile[] files) {
		
		List<Integer> idList = new ArrayList<Integer>();
		// 更新不变和修改的参观类型
		FarmVisits farmVisits = null;
		List<FarmVisits> farmVisitsList = new ArrayList<FarmVisits>();
		for (int i = 0; i < typeId.length; i++) {
			farmVisits = new FarmVisits();
			farmVisits.setId(typeId[i]);
			farmVisits.setPrice(price[i]);
			farmVisits.setTitle(typeTitle[i]);
			farmVisitsList.add(farmVisits);	
			idList.add(typeId[i]);
		}
		boolean isDone1 = farmVisitsService.batchUpdate(farmVisitsList);
		if (isDone1)
			System.out.println("yes");
		else 
			System.out.println("no");
		
		// 提交新增的类型
		FarmVisits farmVisitsAdd = null;
		List<FarmVisits> fvAddList = new ArrayList<FarmVisits>();
		for (int i = 0; i < typeTitleNew.length; i++) {
			farmVisitsAdd = new FarmVisits();
			farmVisits.setPrice(priceNew[i]);
//			System.out.println(priceNew[i]);
//			System.out.println(typeTitleNew[i]);
			farmVisits.setTitle(typeTitleNew[i]);
			fvAddList.add(farmVisitsAdd);
		}
		farmVisitsService.addFarmVisitBatch(fvAddList);
		for (int i = 0; i < fvAddList.size(); i++) {
			idList.add(farmVisitsList.get(i).getId());
		}
		
		for (int i = 0; i < idList.size(); i++) {
			System.out.println(idList.get(i));
		}
		ModelAndView mv = new ModelAndView("redirect:/farmVisits/index/0/1/prvious");
		System.out.println(title);
		return mv;

	}
	
	/**
	 * 获取增加项目页面
	 * @return
	 */
	@RequestMapping("addType")
	public ModelAndView addVisitPage() {
		ModelAndView mv = new ModelAndView("farmVisits/addType");
		return mv;
	}
	
	/**
	 * 处理增加项目提交的数据
	 * @param request
	 * @param files
	 * @param projectTitle
	 * @param title
	 * @param price
	 * @param detail
	 * @return
	 */
	@RequestMapping(value="addTypePost", method=RequestMethod.POST)
	public ModelAndView addVisit(HttpServletRequest request, @RequestParam("file") MultipartFile[] files, 
								 String projectTitle, String[] title, Double[] price, String detail) {
		// TODO 前端和后台表单验证
		ModelAndView mv = new ModelAndView("redirect:/farmVisits/index/0/1/prvious");
		// 图片地址
		List<String> imgPathList = new ArrayList<String>();
		// 参观类型id
		List<Integer> typeIdList = new ArrayList<Integer>();
		// 参观类型
		List<FarmVisits> typeList = new ArrayList<FarmVisits>(); 
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(date);
		
		String path=request.getSession().getServletContext().getRealPath("")+"/../";
		String relaFilePath = File.separator + "upload" + File.separator + "farmVisits" + File.separator + dateStr;
		String filePath = path + relaFilePath;
		String relaPath = "/upload/farmVisits/" + dateStr + "/";
//		System.out.println(filePath);
//		System.out.println(relaPath);
		int numOfImg = files.length;
		for (int i = 0; i < numOfImg; i++) {
			String fileName = getImageFileUtil.rename(files[i]);
			// 注意存储路径
			getImageFileUtil.uploadFile(files[i], fileName, filePath);
			String relaUrl = relaPath + fileName;
			imgPathList.add(relaUrl);
		}
		
		// TODO 对title和price长度进行比较
		for (int i = 0; i < title.length; i++) {
			FarmVisits farmVisits = new FarmVisits();
			farmVisits.setTitle(title[i]);
			farmVisits.setPrice(price[i]);
			typeList.add(farmVisits);			
		}
		
		// 将参观类型添加数据库并返回自增id
		boolean isTypeDone = farmVisitsService.addFarmVisitBatch(typeList);
		for (FarmVisits farmVisit: typeList) {
			typeIdList.add(farmVisit.getId());
		}
		
		// 将图片地址列表和参观类型id列表转换为Json字符串
		ObjectMapper mapper = new ObjectMapper();
		String imgPathStr = "";
		String idStr = "";
		try {
			imgPathStr = mapper.writeValueAsString(imgPathList);
			idStr = mapper.writeValueAsString(typeIdList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		Project project = new Project();
		project.setDetail(detail);
		project.setTitle(projectTitle);
		project.setImages(imgPathStr);
		project.setTypes(idStr);
		project.setCreateTime(date);
		
		// 将项目添加到数据库
		boolean isProDone = projectService.addType(project);
		
		return mv;		
	}
	
	/**
	 * 查看项目信息详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/typeInfo/{id}")
	public ModelAndView infoPage(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("farmVisits/projectDetail");
		Project project = projectService.getType(id);
		String imgPathStr = project.getImages();
		String idStr = project.getTypes();
		
		ObjectMapper mapper = new ObjectMapper();
		List<String> imgPathList = new ArrayList<String>();
		List<Integer> typeIdList = new ArrayList<Integer>();
		// 将Json字符串转换为List对象
		try {
			imgPathList = mapper.readValue(imgPathStr, new TypeReference<List<String>>() {});
			typeIdList = mapper.readValue(idStr, new TypeReference<List<Integer>>() {});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		mv.addObject("id", id);
		mv.addObject("title", project.getTitle());
		mv.addObject("detail", project.getDetail());
		mv.addObject("pictureList", imgPathList);
		mv.addObject("createTime", project.getCreateTime());
		
		List<FarmVisits> typeList = new ArrayList<FarmVisits>();
		typeList = farmVisitsService.getFarmVisitTypesBatch(typeIdList);
		mv.addObject("typeList" , typeList);
		
		return mv;
	}
	
	/**
	 * 请求删除项目，更改审计状态为删除待审核
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteRequest/{id}")
	public ModelAndView deleteProject(@PathVariable Integer id, RedirectAttributes attr) {
		
		ModelAndView mv = new ModelAndView("redirect:/farmVisitsAudit/auditIndex/0/1/prvious");
		boolean isDone = projectService.updateStatus(id, Constants.DELETE_ON_WAIT);
		if (isDone)
			attr.addFlashAttribute("resultMsg", "删除成功等待审核！");
		else
			attr.addFlashAttribute("resultMsg", "删除失败！");
		return mv;
	}

}
 