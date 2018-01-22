package cn.bupt.smartyagl.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.Addition;
import cn.bupt.smartyagl.entity.autogenerate.AdditionView;
import cn.bupt.smartyagl.entity.autogenerate.AdminUser;
import cn.bupt.smartyagl.service.IAdditionService;
import cn.bupt.smartyagl.util.ImageUtil;
import cn.jpush.api.common.connection.IHttpClient.RequestMethod;
@Controller
@RequestMapping("/addition")
public class AdditionController extends BaseController {
	@Autowired
	IAdditionService additionService;
	/*
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "addition/add";
	}
	@RequestMapping(value = "/saveAdd")
	public ModelAndView saveAdd(Addition add,HttpServletRequest request) 
			throws IllegalStateException, IOException {
		ModelAndView mv = new ModelAndView(Constants.ADDITION_INDEX);
		add.setStatus((byte)1);
		AdminUser user = (AdminUser)request.getSession().getAttribute("user");
		add.setOperId(user.getId());
		add.setModifyid(0);
		String path = request.getServletContext().getRealPath("") + "/../" + Constants.FILE_PATH;
		uploadFile(request, path);
		if(additionService.save(add))
			mv.addObject("oper_res", Constants.ADD_OK);
		else
			mv.addObject("oper_res", Constants.ADD_FAIl);
		return mv;
	}
	private void uploadFile(HttpServletRequest request,String path) throws IllegalStateException, IOException {
		CommonsMultipartResolver cmpr = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		MultipartHttpServletRequest req = (MultipartHttpServletRequest)request;
		if(cmpr.isMultipart(request)) {
			Iterator<String> it = req.getFileNames();
			int suffix = 0;
			while(it.hasNext()) {
				MultipartFile file = req.getFile(it.next());
				StringBuilder sb = new StringBuilder();
				sb.append(path+"goods/");
				if(file != null) {
					Long time = System.currentTimeMillis();
					//String myFileName = "addition/"+time.toString()+ "_" + suffix;
					String myFileName = sb.append(time.toString()).append("_").append(suffix++).toString();
					System.out.println(file.getContentType());
					//ImageUtil.uploadImage(file, myFileName);
					file.transferTo(new File(myFileName));
				}
			}
		}
	}
	*/

	@RequestMapping("/toEdit")
	public ModelAndView toEdit() {
		ModelAndView mv = new ModelAndView(Constants.ADDITION_CHO);
		List<Addition> list = additionService.getAllFourAddition();
		mv.addObject("records", list);
		return mv;
	}
	@RequestMapping("/chooseEdit")
	public ModelAndView chooseEdit(@RequestParam int id){
		ModelAndView mv = new ModelAndView(Constants.ADD_EDIT);
		Addition ad = additionService.getAdditionById(id);
		mv.addObject("addition", ad);			
		return mv ;
	}
	@RequestMapping("/saveEdit")
	public ModelAndView saveEdit(Addition add,HttpServletRequest request,@RequestParam("id") int id) 
			throws IllegalStateException, IOException {
		ModelAndView mv = new ModelAndView("forward:/addition/toEdit");
		additionService.setModified(id);
		AdminUser user = (AdminUser)request.getSession().getAttribute("user");
		add.setOper_id(user.getId());
		add.setModifyid(id);
		add.setStatus((byte)2);
		add.setId(null);
		//String path = request.getServletContext().getRealPath("") + "/../" + Constants.FILE_PATH;
		//uploadFile(request, path);
		if(additionService.save(add))
			mv.addObject("oper_res", Constants.ADD_OK);
		else
			mv.addObject("oper_res", Constants.ADD_FAIl);
		return mv;
	}
	@RequestMapping("/toReview")
	public ModelAndView toReview() {
		ModelAndView mv = new ModelAndView(Constants.ADD_REVIEW);
		List<AdditionView> list = additionService.getUnreviewedMsg();
		mv.addObject("records", list);
		return mv ;
	}
	@RequestMapping("/toDetail")
	public ModelAndView toDetail(@RequestParam int id,@RequestParam String comeFrom){
		ModelAndView mv = new ModelAndView(Constants.ADD_DETAIL);
		mv.addObject("addition", additionService.getAdditionById(id));
		if("rev".equals(comeFrom))
			mv.addObject("href", "addition/toReview");
		else
			mv.addObject("href", "addition/toEdit");
		//System.out.println(additionService.getAdditionById(id).getContent());
		return mv;		
	}
	@RequestMapping("/detail")
	public ModelAndView detail(@RequestParam int id){
		ModelAndView mv = new ModelAndView(Constants.ADD_DETAIL);
		mv.addObject("addition", additionService.getAdditionById(id));
		
		//System.out.println(additionService.getAdditionById(id).getContent());
		return mv;		
	}
	@RequestMapping("/doReview")
	public ModelAndView doReview(@RequestParam int id){
		ModelAndView mv = new ModelAndView("forward:/addition/toReview");
		if(additionService.setReviewed(id))
			mv.addObject("oper_res", Constants.ADD_OK);
		else {
			mv.addObject("oper_res", Constants.ADD_FAIl);
		}
		return mv;
	}
	@RequestMapping("/unDo")
	public ModelAndView unDo(@RequestParam int id){
		ModelAndView mv = new ModelAndView("forward:/addition/toReview");
		if(additionService.unDo(id))
			mv.addObject("oper_res", Constants.ADD_OK);
		else
			mv.addObject("oper_res", Constants.ADD_FAIl);
		return mv;
	}
}