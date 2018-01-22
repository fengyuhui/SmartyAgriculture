package cn.bupt.smartyagl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.CommentView;
import cn.bupt.smartyagl.service.ICommentService;
import cn.bupt.smartyagl.util.picture.JsonConvert;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

	@Autowired
	ICommentService commentService;
	
	public int pageSize=Constants.PAGESIZE;
	/**
	 * 评论列表
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/commentIndex/{allPages}/{currentPage}/{type}")
	public ModelAndView commentIndex(@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type){
		ModelAndView modelAndView=new ModelAndView(Constants.COMMENT_INDEX);
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
		commentService.getCommentViewsList(modelAndView, currentPage, pageSize);
		return modelAndView;
	}
	/**
	 * 查看评论详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/commentDetail/{id}")
	public ModelAndView commentDetail(@PathVariable(value="id")int id,HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView(Constants.COMMENT_DETAIL);
		CommentView commentView=commentService.getCommentDetail(id);
		List<String> pictureList=this.dealPicture(commentView, request);
		modelAndView.addObject("commentView", commentView);
		modelAndView.addObject("pictureList",pictureList);
		return modelAndView;
	}
	/**
	 * 申请隐藏评论 、审核是否通过
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateCommentStatus/{id}/{status}")
	public Map<String, String> updateCommentStatus(@PathVariable(value="id")int id,
			@PathVariable(value="status")int status
			){
		Map<String, String> returnMap=new HashMap<String, String>();
		boolean flag=commentService.updateCommentStatus(id, status);
		if(flag){
			returnMap.put("msg", "修改成功");
		}
		else{
			returnMap.put("msg", "修改失败");
		}
		return returnMap;
	}
	/**
	 * 获取评论审核列表
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/commentAuditIndex/{allPages}/{currentPage}/{type}")
	public ModelAndView commentAuditIndex(@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type){
		ModelAndView modelAndView=new ModelAndView(Constants.COMMENT_AUDIT_INDEX);
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
		commentService.getCommentAuditViewsList(modelAndView, currentPage, pageSize);
		return modelAndView;
	}
	/**
	 * 查看待审核评论详情
	 */
	@RequestMapping(value="/commentAuditDetail/{id}")
	public ModelAndView commentAuditDetail(@PathVariable(value="id")int id,HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView(Constants.COMMENT_AUDIT_DETAIL);
		CommentView commentView=commentService.getCommentDetail(id);
		List<String> pictureList=this.dealPicture(commentView, request);
		modelAndView.addObject("commentView", commentView);
		modelAndView.addObject("pictureList",pictureList);
		return modelAndView;
	}
	/**
	 * 处理评论的图片
	 */
	public  List<String> dealPicture(CommentView commentView,
			HttpServletRequest request){
		if(commentView.getPictureList()!=null){
			// 处理商品图片
			List<String> pictureList = new ArrayList<String>();
			try {
				pictureList = JsonConvert.getProductPicture(commentView.getPictureList(),
						request);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pictureList;
		}
		else{
			return null;
		}
	}
}
