package cn.bupt.smartyagl.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.GoodsType;
import cn.bupt.smartyagl.model.GoodsTypeModel;
import cn.bupt.smartyagl.service.IGoodsTypeService;
import cn.bupt.smartyagl.service.IStatisticsService;
import cn.bupt.smartyagl.util.ImageUtil;

/**
 * 
 * <p>
 * Title:TypeController
 * </p>
 * <p>
 * Description:商品类别管理
 * </p>
 * 
 * @author waiting
 * @date 2016年6月28日 上午9:47:30
 */
@Controller
@RequestMapping("/type")
public class TypeController extends BaseController {

	@Autowired
	IGoodsTypeService goodsTypeService;
	@Autowired
	IStatisticsService statisticsService;
	
	int pageSize=Constants.PAGESIZE;
   /**
    * 产品类比列表
    * @param allPages
    * @param currentPage
    * @param type
    * @param level
    * @param parentId
    * @return
    */
	@RequestMapping(value = "/getTypeList/{allPages}/{currentPage}/{type}/{level}/{parentId}")
	public ModelAndView getTypeList(
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value = "level") int level,
			@PathVariable(value="parentId") int parentId) {

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
		ModelAndView modelAndView=new ModelAndView(Constants.TYPE_INDEX);
		goodsTypeService.getTypeList(level,parentId, currentPage, pageSize, modelAndView);
		List<GoodsTypeModel> GoodsTypeModel=goodsTypeService.getDisplayGoodsType();
		modelAndView.addObject("parentList",GoodsTypeModel);
		modelAndView.addObject("level", level);
		modelAndView.addObject("parentId", parentId);
		return modelAndView;
	}
	/**
	 * 添加商品类别跳转
	 */
	@RequestMapping("/addType")
	public ModelAndView addType(){
		ModelAndView modelAndView=new ModelAndView(Constants.TYPE_ADD);
		List<GoodsTypeModel> GoodsTypeModel=goodsTypeService.getDisplayGoodsType();
		modelAndView.addObject("parentList",GoodsTypeModel);
		return modelAndView;
	}
	@RequestMapping("/typeReview")
	public ModelAndView typeReview(){
		ModelAndView modelAndView = new ModelAndView(Constants.TYPE_REVIEWED);
		List<GoodsTypeModel> list = goodsTypeService.getUnreviewedTypes();
		modelAndView.addObject("records",list);
		return modelAndView;
	}
	@RequestMapping("/doReview")
	public String doReview(@RequestParam("id") int id){
		goodsTypeService.setReviewed(id);
		return "forward:/type/typeReview";
	}
	@RequestMapping("/unDo")
	public String unDo(@RequestParam("id") int id){
		goodsTypeService.unDo(id);
		return "forward:/type/typeReview";
	}
	/**
	 * 添加商品类别
	 */
	@RequestMapping("addTypePost")
	public ModelAndView addTypePost(GoodsType goodsType,MultipartFile file, HttpServletRequest request) 
			throws IllegalStateException, IOException{
		// 上传文件路径
		Long time = System.currentTimeMillis();
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getContentType());
		System.out.println(file.getName());
		System.out.println(file.getSize());
		String fileName = time.toString();
		String tempString = Constants.GOODS_TYPE_PICTURE_STRING + fileName ;
	    String path = request.getSession().getServletContext().getRealPath("")+"/../"
						+ Constants.FILE_PATH+tempString;
	     // json格式化
//	 		ObjectMapper mapper = new ObjectMapper();
//	 		String filePathJsonString = mapper.writeValueAsString(tempString);
//	 		System.out.println(filePathJsonString);
	 		//上传图片
	    tempString += "/" + ImageUtil.uploadImage(file, path);
		goodsType.setIsdisplay(false);
		goodsType.setPicture("/"+ Constants.FILE_PATH+tempString);
		goodsType.setStatus(1);
		goodsType.setModifyId(-1);
		 //   goodsType.setParentid(-1);
	    boolean flag=goodsTypeService.addType(goodsType);
	    if (flag) {
			ModelAndView modelAndView = this.getTypeList(0, 1, "prvious", 0,0);
			return modelAndView;
		} else {
			ModelAndView modelAndView = this.addType();
			return modelAndView;
		}
	}
	/**
	 * 编辑商品类别界面，跳转
	 * @param id
	 * @return
	 */
	@RequestMapping("/jumpToTypeEdit/{id}")
	public ModelAndView jumpToTypeEdit(@PathVariable(value = "id") int id){
		ModelAndView modelAndView=new ModelAndView(Constants.TYPE_EDIT);
		GoodsType goodsType=goodsTypeService.getGoodsTypeById(id);
		modelAndView.addObject("typeDetail", goodsType);
		GoodsType goodsTypeParent=null;
		if(goodsType.getParentId()!=-1){
			goodsTypeParent=goodsTypeService.getGoodsTypeById(goodsType.getParentId());
		}
		modelAndView.addObject("parentType", goodsTypeParent);
		return modelAndView;
	}
	/**
	 * 保存商品类别编辑
	 * @param goodsType
	 * @param typePicture
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/saveTypeEdit")
	public String saveTypeEdit(GoodsType goodsType,MultipartFile typePicture, HttpServletRequest request) 
			throws IllegalStateException, IOException{
		//ModelAndView modelAndView=new ModelAndView(Constants.TYPE_EDIT);
		GoodsType goodsTypeOld=goodsTypeService.getGoodsTypeById(goodsType.getId());
		if(typePicture.getSize()!=0){
			//删除原来的图片
			String oldPath=goodsTypeOld.getPicture();
			oldPath=request.getSession().getServletContext().getRealPath("")+"/../"+Constants.FILE_PATH+oldPath;
//			ImageUtil.deleteFileByPath(oldPath);
			
			Long time = System.currentTimeMillis();
			String fileName = time.toString();
			String tempString = Constants.GOODS_TYPE_PICTURE_STRING + fileName ;
			goodsType.setId(null);
			goodsType.setIsdisplay(false);
			goodsType.setLevel(goodsTypeOld.getLevel());
			goodsType.setStatus(2);
			goodsType.setParentId(goodsTypeOld.getParentId());
			goodsType.setModifyId(goodsTypeOld.getId());
			goodsTypeOld.setStatus(3);
			tempString = "/"+Constants.FILE_PATH+tempString;
			goodsType.setPicture(tempString + "/"+
					ImageUtil.uploadSuoLveImage(typePicture, request.getServletContext().getRealPath("")+"/../" + tempString));
			goodsTypeService.addType(goodsType);
			goodsTypeService.updateGoodsType(goodsTypeOld);
		}
		else{
			if(goodsType.getName().equals(goodsTypeOld.getName()))
				return "redirect:/type/getTypeList/0/1/prvious/0/0";
			else {
				goodsType.setId(null);
				goodsType.setPicture(goodsTypeOld.getPicture());
				goodsType.setIsdisplay(false);
				goodsType.setLevel(goodsTypeOld.getLevel());
				goodsType.setParentId(goodsTypeOld.getParentId());
				goodsType.setStatus(2);
				goodsTypeOld.setStatus(3);
				goodsType.setModifyId(goodsTypeOld.getId());
				goodsTypeService.addType(goodsType);
				goodsTypeService.updateGoodsType(goodsTypeOld);
			}
		}
		return "redirect:/type/getTypeList/0/1/prvious/0/0";
	}
	/**
	 * 修改商品类型显示状态
	 * @param isdisplay
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/updateTypeIsdisplay")
	@ResponseBody
    public Map<String, String> updateTypeIsdisplay(boolean isdisplay,int typeId){
		Map<String, String> result=new HashMap<String, String>();
		Boolean flag=goodsTypeService.updateGoodsISdisplay(isdisplay, typeId);
		if (flag) {
			result.put("msg", "修改成功");
		} else {
			result.put("msg", "修改失败");
		}
		return result;
    }
	/**
	 * 一级类型商品统计
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @param status
	 * @param timeFlag
	 * @param typeIdParent
	 * @return
	 */
	@RequestMapping(value="/parentTypeStatistics/{allPages}/{currentPage}/{type}/{status}/{starttime}/{endtime}/{typeIdParent}")
	public ModelAndView parentTypeStatistics(@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value = "status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value = "endtime") String endtime,
			@PathVariable(value="typeIdParent") int typeIdParent){
		ModelAndView modelAndView=new ModelAndView(Constants.PARENT_TYPE_SALES_STATISTICS);
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
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		statisticsService.typeStatistics(modelAndView, currentPage, pageSize, status, starttime,endtime, typeIdParent);
		//查询商品名称
		GoodsType goodsType = goodsTypeService.getGoodsTypeById(typeIdParent);
		modelAndView.addObject("typeName",goodsType.getName());
		modelAndView.addObject("typeIdParent", typeIdParent);
		modelAndView.addObject("status", status);
		// 返回时间
		if ((starttime != null) && (!starttime.equals("0"))) {
			starttime = starttime.replaceFirst("-", "年");
			starttime = starttime.replace("-", "月");
			starttime = starttime + "日";
			modelAndView.addObject("starttime", "\"" + starttime + "\"");
		}
		else{
			modelAndView.addObject("starttime", "0");
		}
		if ((endtime != null) && (!endtime.equals("0"))) {
			endtime = endtime.replaceFirst("-", "年");
			endtime = endtime.replace("-", "月");
			endtime = endtime + "日";
			modelAndView.addObject("endtime", "\"" + endtime + "\"");
		}
		else{
			modelAndView.addObject("endtime", "0");
		}
		return modelAndView;	
	}
	/**
	 * 一级类型商品统计 图标数据
	 * @param status
	 * @param timeFlag
	 * @param goodsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/echartsParentTypeStatistics/{status}/{starttime}/{endtime}/{typeIdParent}")
	public Map<String, List<String>> echartsParentTypeStatistics(
			@PathVariable(value="status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value = "endtime") String endtime,
			@PathVariable(value="typeIdParent") int typeIdParent){
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		Map<String, List<String>> returnMap=new HashMap<String, List<String>>();
		statisticsService.echartsTypeStatistics(status,starttime,endtime,returnMap, typeIdParent);
		return	returnMap;
	}
	/**
	 * 一级类型商品统计 导出数据
	 * @param status
	 * @param timeFlag
	 * @param typeIdParent
	 * @param response
	 */
	@RequestMapping(value="/exportProductStatictics/{status}/{starttime}/{endtime}/{typeIdParent}")
	public void  exportProductStatistics(@PathVariable(value="status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value = "endtime") String endtime,
			@PathVariable(value="typeIdParent") int typeIdParent,
			HttpServletResponse response) {
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		GoodsType goodsType = goodsTypeService.getGoodsTypeById(typeIdParent);
		String fileNameString= goodsType.getName()+"商品销售额";
		statisticsService.exportExcelTotalSales(status, starttime,endtime,typeIdParent,fileNameString, response);
	}
}
