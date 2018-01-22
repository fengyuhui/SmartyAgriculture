package cn.bupt.smartyagl.controller;

import java.io.IOException;
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
import cn.bupt.smartyagl.entity.autogenerate.Camera;
import cn.bupt.smartyagl.entity.autogenerate.Advertise;
import cn.bupt.smartyagl.service.ICameraService;
import cn.bupt.smartyagl.util.ImageUtil;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-22 下午3:31:51 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("camera")
public class CamerasController  extends BaseController{
	@Autowired
	ICameraService cameraService;
	
	int pageSize=Constants.PAGESIZE;//，每一页的大小
	int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
	
	/**
     * 获取相机列表
     * @author jm
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{types}")
    public ModelAndView Index(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,Camera cameraSelective,HttpServletRequest request) {
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
        ModelAndView modelAndView = new ModelAndView(Constants.Camera_INDEX);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<Camera> cameraList = cameraService.getCameraList();
        modelAndView.addObject("cameraList",cameraList);
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
     * 添加摄像头
     * @param request
     * @param address
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
  	@RequestMapping("/add")
  	public ModelAndView addCamera(HttpServletRequest request,Camera camera) throws IllegalStateException, IOException{
  		ModelAndView mv = new ModelAndView(Constants.Camera_ADD);
  		return mv;
  	}
    
  	/**
     * 添加权限用户
     * @param request
     * @param address
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
  	@RequestMapping("/addPost")
  	public ModelAndView addCameraPost(HttpServletRequest request,Camera camera ) throws IllegalStateException, IOException{
  		ModelAndView mv = new ModelAndView(Constants.Camera_INDEX);
  		//添加
  		if(camera != null && camera.getTitle() != null){
  	  		boolean rst = cameraService.addCamera(camera);
  	  		if(rst){
  	  			mv.addObject("resultMsg", "添加成功");
  	  		}else{
  	  			mv.addObject("resultMsg", "添加失败");
  	  		}
  	  		//跳转到首页
  	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  	  		mv.setView(redirectView);
  		}
  		return mv;
  	}
  	
	/**
     * 查看
     * @param request
     * @param address
     * @return
     */
  	@RequestMapping("/find")
  	public ModelAndView findCamera(HttpServletRequest request,Camera camera){
  		camera = cameraService.findCamera( camera.getId() );
  		ModelAndView mv = new ModelAndView(Constants.Camera_FIND);
  		mv.addObject("camera", camera);
  		return mv;
  	}
  	
    @RequestMapping("/top")
	@ResponseBody
  	public Object top(HttpServletRequest request,Integer cameraId,Integer topId){
		Map<String, String>map = new HashMap<String, String>();
		boolean rs = cameraService.changeTop(cameraId, topId);
		if(rs){
			map.put("resultMsg", "修改成功");
		}else{
			map.put("resultMsg", "修改失败");
		}
		return map;
	}
    
	//删除广告
  	@RequestMapping("/delete")
  	public ModelAndView deleteAdmin(HttpServletRequest request,Integer cameraId){
  		ModelAndView mv = new ModelAndView("");
  		boolean rs = cameraService.deleteCamera(cameraId);
  		if(rs){
  			mv.addObject("resultMsg", "删除成功");
	  	}else{
	  		mv.addObject("resultMsg", "删除失败");
	  	}
  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  		mv.setView(redirectView);
	  	return mv;
  	}
}
