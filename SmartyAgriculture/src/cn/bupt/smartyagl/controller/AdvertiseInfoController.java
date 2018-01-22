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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.entity.autogenerate.Advertise;
import cn.bupt.smartyagl.entity.autogenerate.Advertise;
import cn.bupt.smartyagl.service.IAdevertiseService;
import cn.bupt.smartyagl.util.ImageUtil;
import cn.bupt.smartyagl.util.MD5Util;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-8-31 下午2:07:41 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("/advertise")
public class AdvertiseInfoController extends BaseController {
	
	@Autowired
	IAdevertiseService adevertiseService;
	
	int pageSize=Constants.PAGESIZE;//，每一页的大小
	int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
	
	/**
     * 显示广告权限列表
     * @author jm
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{types}")
    public ModelAndView Index(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,Advertise adminSelective,HttpServletRequest request) {
        if ("prvious".equals(types)) {
            if( currentPage > 1 ){//第一页不能往前翻页
                currentPage--;
            }
        } else if ("next".equals(types)) {
            currentPage++;
        } else if ("first".equals(types)) {
            currentPage = 1;
        } else if ("last".equals(types)) {
            currentPage = allPages;
        } else {
            currentPage = Integer.parseInt(types);
        }
        ModelAndView modelAndView = new ModelAndView(Constants.Advertise_INDEX);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<Advertise> advertiseList = adevertiseService.getAdvertiseList();
        modelAndView.addObject("advertiseList",advertiseList);
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
     * 显示广告权限列表
     * @author jm
     */
    @RequestMapping(value="/judge/{allPages}/{currentPage}/{types}")
    public ModelAndView judgeList(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,Advertise adminSelective,HttpServletRequest request) {
        if ("prvious".equals(types)) {
            if( currentPage > 1 ){//第一页不能往前翻页
                currentPage--;
            }
        } else if ("next".equals(types)) {
            currentPage++;
        } else if ("first".equals(types)) {
            currentPage = 1;
        } else if ("last".equals(types)) {
            currentPage = allPages;
        } else {
            currentPage = Integer.parseInt(types);
        }
        ModelAndView modelAndView = new ModelAndView(Constants.Advertise_AuthList);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<Advertise> advertiseList = adevertiseService.getAdvertiseDraftList();
        modelAndView.addObject("advertiseList",advertiseList);
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
     * @param address
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
  	@RequestMapping("/add")
  	public ModelAndView addAddress(HttpServletRequest request,Advertise advertise) throws IllegalStateException, IOException{
  		ModelAndView mv = new ModelAndView(Constants.Advertise_ADD);
  		return mv;
  	}
  	
  	/**
     * 添加轮播图
     * @param request
     * @param address
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
  	@RequestMapping("/addPost")
  	public ModelAndView addAddressPost(HttpServletRequest request,@RequestParam MultipartFile file,Advertise advertise) throws IllegalStateException, IOException{
  		ModelAndView mv = new ModelAndView(Constants.Advertise_ADD);
  		//添加
  		if(advertise != null && advertise.getTitle() != null){
  			if(file!= null && file.getSize()>0){
  			// 上传文件路径
  	  			Long time = System.currentTimeMillis();
  	  			String fileName = time.toString();
  	  			//存在数据库文件路径
  	  			String tempString = Constants.ADVERTISE_TYPE_STRING + fileName ;
  	  		    String path = request.getSession().getServletContext().getRealPath("")+"/../"
  	  							+ Constants.FILE_PATH + tempString;
  	  		 	//上传图片
  	  		    tempString += "/" + ImageUtil.uploadImage(file, path) ;
  	  		    advertise.setPicture(tempString);
  			}
  	  		boolean rst = adevertiseService.addAdvertise(advertise);
  	  		if(rst){
  	  			mv.addObject("resultMsg", "添加广告成功");
  	  		}else{
  	  			mv.addObject("resultMsg", "添加广告失败");
  	  		}
  	  		//跳转到首页
  	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  	  		mv.setView(redirectView);
  		}
  		return mv;
  	}
  	
  	 /**
     * 发布草稿
     * @param request
     * @param address
     * @return
     */
  	@RequestMapping("/update")
  	public ModelAndView update(HttpServletRequest request,Advertise advertise){
  		advertise = adevertiseService.findAdvertise(advertise.getId());
  		ModelAndView mv = new ModelAndView(Constants.Advertise_EDIT);
  		mv.addObject("advertise", advertise);
  		return mv;
  	}
  	
  	/**
     * 查看
     * @param request
     * @param address
     * @return
     */
  	@RequestMapping("/find")
  	public ModelAndView find(HttpServletRequest request,Advertise advertise){
  		advertise = adevertiseService.findAdvertise(advertise.getId());
  		ModelAndView mv = new ModelAndView(Constants.Advertise_Find);
  		//advertise.setPicture( getImageFileUtil.getSrcFileImg( advertise.getPicture()) );
  		mv.addObject("advertise", advertise);
  		return mv;
  	}
  	
	 /**
     * 发布草稿
     * @param request
     * @param address
     * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
     */
  	@RequestMapping("/updatePost")
  	public ModelAndView updatePost(HttpServletRequest request,@RequestParam MultipartFile file,Advertise advertise) throws IllegalStateException, IOException{
  		ModelAndView mv = new ModelAndView(Constants.Advertise_EDIT);
  		if(advertise != null && advertise.getId() != null){
  			if(file!= null && file.getSize()>0){
  	  			// 上传文件路径
  	  	  			Long time = System.currentTimeMillis();
  	  	  			String fileName = time.toString();
  	  	  			//存在数据库文件路径
  	  	  			String tempString = Constants.ADVERTISE_TYPE_STRING + fileName ;
  	  	  		    String path = request.getSession().getServletContext().getRealPath("")+"/../"
  	  	  							+ Constants.FILE_PATH + tempString;
  	  	  		 	//上传图片
  	  	  		    tempString += "/" + ImageUtil.uploadImage(file, path) ;
  	  	  		    advertise.setPicture(tempString);
  	  	  		System.out.println("文件地址=:" + path);
  			}
  	  		boolean rst = adevertiseService.updateAdvertise(advertise);
//  	  		if(rst){
//  	  			mv.addObject("resultMsg", "修改广告成功");
//  	  		}else{
//  	  			mv.addObject("resultMsg", "修改广告失败");
//  	  		}
  	  		//跳转到首页
  	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  	  		mv.setView(redirectView);
  		}
  		return mv;
  	}
  	
  	//删除广告
  	@RequestMapping("/delete")
  	public ModelAndView deleteAdmin(HttpServletRequest request,Integer advertiseId){
  		ModelAndView mv = new ModelAndView("");
  		boolean rs = adevertiseService.deleteAdvertise(advertiseId);
  		if(rs){
  			mv.addObject("resultMsg", "删除成功");
	  	}else{
	  		mv.addObject("resultMsg", "删除失败");
	  	}
  		RedirectView redirectView = new RedirectView( "judge/0/1/prvious" );
  		mv.setView(redirectView);
	  	return mv;
  	}
  	
  	@RequestMapping("/judgePublish")
  	public ModelAndView judgePublish(HttpServletRequest request,Integer advertiseId){
  		ModelAndView mv = new ModelAndView("");
  		boolean rs = adevertiseService.verifyAddAdvertise(advertiseId);
  		if(rs){
  			mv.addObject("resultMsg", "审核发布成功");
	  	}else{
	  		mv.addObject("resultMsg", "审核发布失败");
	  	}
  		RedirectView redirectView = new RedirectView( "judge/0/1/prvious" );
  		mv.setView(redirectView);
	  	return mv;
  	}
  	
	@RequestMapping("/judgeDraft")
  	public ModelAndView judgeDraft(HttpServletRequest request,Integer advertiseId){
  		ModelAndView mv = new ModelAndView("");
  		boolean rs = adevertiseService.verifyChangeAdvertise(advertiseId);
  		if(rs){
  			mv.addObject("resultMsg", "审核草稿成功");
	  	}else{
	  		mv.addObject("resultMsg", "审核草稿失败");
	  	}
  		RedirectView redirectView = new RedirectView( "judge/0/1/prvious" );
  		mv.setView(redirectView);
	  	return mv;
  	}
	
	@RequestMapping("/top")
	@ResponseBody
  	public Object top(HttpServletRequest request,Integer advertiseId,Integer topId){
		Map<String, String>map = new HashMap<String, String>();
		boolean rs = adevertiseService.changeTop(advertiseId, topId);
		if(rs){
			map.put("resultMsg", "修改成功");
		}else{
			map.put("resultMsg", "修改失败");
		}
		return map;
	}
	
	/**
	 * 提交删除申请，并非真的删除
	 * @param request
	 * @param advertiseId
	 * @param topId
	 * @return
	 */
	@RequestMapping("/deleteRequest")
  	public ModelAndView deleteRequest(HttpServletRequest request,Integer advertiseId){
		ModelAndView mv = new ModelAndView("");
		Map<String, String>map = new HashMap<String, String>();
		Advertise ad = new Advertise();
		ad.setId( advertiseId );
		ad.setStatus( ConstantsSql.Audit_WaitDelete );
		boolean rs = adevertiseService.deletePostAdvertise(ad);
//		if(rs){
//			mv.addObject("resultMsg", "申请删除成功");
//		}else{
//			mv.addObject("resultMsg", "申请删除失败");
//		}
		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  		mv.setView(redirectView);
	  	return mv;
	}
	
	/**
	 * 提交删除申请，并非真的删除
	 * @param request
	 * @param advertiseId
	 * @param topId
	 * @return
	 */
	@RequestMapping("/rebackDeleteRequest")
	public ModelAndView rebackDeleteRequest(HttpServletRequest request,Integer advertiseId){
		ModelAndView mv = new ModelAndView("");
		Map<String, String>map = new HashMap<String, String>();
		Advertise ad = new Advertise();
		ad.setId( advertiseId );
		ad.setStatus( ConstantsSql.Audit_Finish );
		boolean rs = adevertiseService.deletePostAdvertise(ad);
		if(rs){
			mv.addObject("resultMsg", "撤销删除成功");
		}else{
			mv.addObject("resultMsg", "撤销删除失败");
		}
		RedirectView redirectView = new RedirectView( "judge/0/1/prvious" );
  		mv.setView(redirectView);
	  	return mv;
	}
}
