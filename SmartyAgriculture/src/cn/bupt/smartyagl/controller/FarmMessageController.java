package cn.bupt.smartyagl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
import cn.bupt.smartyagl.service.IFarmMessageService;
import cn.bupt.smartyagl.util.ImageUtil;

	/**
 * 
 * <p>
 * Title:ProductController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zxy
 */
@Controller
@RequestMapping("/farmMessage")
public class FarmMessageController extends BaseController {

	@Autowired
	IFarmMessageService farmMessageService;

	int pageSize = Constants.PAGESIZE;// 每一页的大小
 
    int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
    	
    /**
     * 显示农场信息列表
     * @author zxy
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{types}")
    public ModelAndView index(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,FarmMessage farmMessageSelective,HttpServletRequest request) {
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
        ModelAndView modelAndView = new ModelAndView(Constants.FarmMessage_INDEX);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<FarmMessage> farmMessageList = farmMessageService.getFarmMessageList(farmMessageSelective);
        modelAndView.addObject("farmMessageList",farmMessageList);
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
     * 显示农场信息权限列表
     * @author 
     */
    @RequestMapping(value="/judge/{allPages}/{currentPage}/{types}")
    public ModelAndView judgeList(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,FarmMessage farmMessageSelective,HttpServletRequest request) {
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
        ModelAndView modelAndView = new ModelAndView(Constants.FarmMessage_AuthList);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<FarmMessage> farmMessageList = farmMessageService.getFarmMessageDraftList();
        modelAndView.addObject("farmMessageList",farmMessageList);
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
     * 添加农场信息
     * @param request
     * @param FarmMessage
     * @return
     */
  	@RequestMapping("/add")
  	public ModelAndView add(HttpServletRequest request,FarmMessage farmMessage){
  		farmMessage.setCreatTime(new Date());
  		farmMessage.setTopOrNot(farmMessage.getTopOrNot());
  		farmMessage.setStatus(1);
  		ModelAndView mv = new ModelAndView(Constants.FarmMessage_ADD);
  		//添加信息
  		if(farmMessage != null && farmMessage.getMessageName() != null){
  			mv.setViewName(Constants.FarmMessage_ADD);
  			boolean rst = farmMessageService.addFarmMessages(farmMessage);
  	  		if(rst){
  	  			mv.addObject("resultMsg", "添加信息成功");
  	  		}else{
  	  			mv.addObject("resultMsg", "信息已存在");
  	  		}
  	  		//跳转到首页
  	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  	  		mv.setView(redirectView);
  		}
  		return mv;
  	}

	/**
	 * 
	 * @param FarmMessage
	 *            添加农场信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPost")
	public ModelAndView addProductPost(FarmMessage farmMessage,
			HttpServletRequest request)
			throws Exception {
		farmMessage.setCreatTime(new Date());
  		farmMessage.setTopOrNot(farmMessage.getTopOrNot());
  		farmMessage.setStatus(1);
		// 上传文件路径
		String path=request.getSession().getServletContext().getRealPath("")+"/../"+ Constants.FILE_PATH;
		// 上传文件
		List<String> filePath = new ArrayList<String>();
		filePath = this.uploadFile(request, path);
		if (filePath != null) {
			// json格式化
			ObjectMapper mapper = new ObjectMapper();
			String filePathJsonString = mapper.writeValueAsString(filePath);
			farmMessage.setPicture(filePathJsonString);
		}
		
		boolean flag = farmMessageService.addFarmMessages(farmMessage);
		if (flag) {
			ModelAndView modelAndView = this.index( 0, 1, "prvious", farmMessage ,request);
			return modelAndView;
			// return Constants.PRODUCT_INDEX;
		} else {
			ModelAndView modelAndView = this.add(request, farmMessage);
			return modelAndView;
		}

	}
	
	public List<String> uploadFile(HttpServletRequest request, String rootPath) {
		List<String> filePath = new ArrayList<String>();
		try {
			// 设置上下方文
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				if (!iter.hasNext())// 没有文件上传
					return null;
				int i = 0;
				Long time = System.currentTimeMillis();
				String fileName = time.toString();
				while (iter.hasNext()) {// 遍历文件
					// 由CommonsMultipartFile继承而来,拥有上面的方法.
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String tempString = "farmMessage/" + fileName + "_" + i;
						String fileNameThis = rootPath + tempString;
						String fileNameString = ImageUtil.uploadImage(file,
								fileNameThis);
						if (fileNameString != null) {
							tempString += "/" + fileNameString;
							filePath.add(tempString);
							i++;
						} else {
							return null;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filePath;
	}
	
	/**
	 * 查看农场信息详情
	 * 
	 * @param id
	 * @return
	 * @author zxy
	 */
	@RequestMapping("/find")
	public ModelAndView getfarmMessagetDetail( Integer id,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(Constants.FarmMessage_DETAIL);
		FarmMessage farmMessageList = farmMessageService.getFarmMessageDetail(id);
		modelAndView.addObject("farmMessageList", farmMessageList);
		
		// 状态
		int status = farmMessageList.getStatus();
		String statusString = null;
		switch (status) {
		case ConstantsSql.Audit_Finish:
			statusString = "正常";
			break;
		case ConstantsSql.Audit_Publish_NoAuth:
			statusString = "发布待审核";
			break;
		case ConstantsSql.Audit_Draft:
			statusString = "草稿";
			break;
		case ConstantsSql.Audit_Finish_hasDraft:
			statusString = "正常草稿";
			break;
		case ConstantsSql.Audit_WaitDelete:
			statusString = "删除待审核";
			break;
		}
		modelAndView.addObject("status", statusString);
		modelAndView.addObject("currentMessage", farmMessageList.getCurrentMessage());
		modelAndView.addObject("subTitle", farmMessageList.getSubTitle());
		modelAndView.addObject("messageName", farmMessageList.getMessageName());
		return modelAndView;
	}


	 /**
 * 发布草稿
 * @param request
 * @param farmMessage
 * @return
 */
	@RequestMapping("/update/{id}")
	public ModelAndView update(@PathVariable(value = "id") int id){
		FarmMessage farmMessage = farmMessageService.getFarmMessageDetail(id);
		ModelAndView mv = new ModelAndView(Constants.FarmMessage_EDIT);
		mv.addObject("farmMessage", farmMessage);
		return mv;
	}

 /**
 * 发布草稿
 * @param request
 * @param farmMessage
 * @return
 * @throws IOException 
 * @throws IllegalStateException 
 */
	@RequestMapping("/updatePost")
	public ModelAndView updatePost(HttpServletRequest request,FarmMessage farmMessage) throws IllegalStateException, IOException{
		ModelAndView mv = new ModelAndView(Constants.FarmMessage_EDIT);
  		farmMessage.setUpdateTime(new Date());
		if(farmMessage != null && farmMessage.getId() != null){
	  		boolean rst = farmMessageService.updateFarmMessage(farmMessage);
	  		if(rst){
	  			mv.addObject("resultMsg", "修改农场信息成功");
	  		}else{
	  			mv.addObject("resultMsg", "修改农场信息失败");
	  		}
	  		//跳转到首页
	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
	  		mv.setView(redirectView);
		}
		return mv;
	}
	
	//删除  删除前判断，个数不能少于1
	@RequestMapping("/delete")
	public ModelAndView deleteAdmin(HttpServletRequest request,Integer id){
		ModelAndView mv = new ModelAndView("");
		boolean rs = farmMessageService.deleteFarmMessage(id);
		/*if(rs){
			mv.addObject("resultMsg", "删除成功");
  	}else{
  		mv.addObject("resultMsg", "删除失败");
  	}*/
		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
		mv.setView(redirectView);
  	return mv;
	}
	
	@RequestMapping("/judgePublish")
	public ModelAndView judgePublish(HttpServletRequest request,Integer id){
		ModelAndView mv = new ModelAndView("");
		boolean rs = farmMessageService.verifyAddFarmMessage(id);
		/*if(rs){
			mv.addObject("resultMsg", "审核发布成功");
  	}else{
  		mv.addObject("resultMsg", "审核发布失败");
  	}*/
		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
		mv.setView(redirectView);
  	return mv;
	}
	
	@RequestMapping("/judgeDraft")
		public ModelAndView judgeDraft(HttpServletRequest request, Integer id){
			ModelAndView mv = new ModelAndView("");
			boolean rs = farmMessageService.verifyChangeFarmMessage(id);
/*			if(rs){
				mv.addObject("resultMsg", "审核草稿成功");
	  	}else{
	  		mv.addObject("resultMsg", "审核草稿失败");
	  	}*/
			RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
			mv.setView(redirectView);
	  	return mv;
		}
	
	@RequestMapping("/topOrNot")
	@ResponseBody
		public Object top(HttpServletRequest request, Integer id,Integer topId){
		Map<String, String>map = new HashMap<String, String>();
		boolean rs = farmMessageService.changeTop(id, topId);
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
     * @param id
     * @param topId
     * @return
     */
    @RequestMapping("/deleteRequest")
    public ModelAndView deleteRequest(HttpServletRequest request,Integer id){
        ModelAndView mv = new ModelAndView("");
        Map<String, String>map = new HashMap<String, String>();
        FarmMessage fa = new FarmMessage();
        fa.setId( id );
        fa.setStatus( ConstantsSql.Audit_WaitDelete );
        boolean rs = farmMessageService.deletePostFarmMessage(fa);
        if(rs){
            mv.addObject("resultMsg", "申请删除成功");
        }else{
            mv.addObject("resultMsg", "申请删除失败");
        }
        RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
        mv.setView(redirectView);
        return mv;
    }
    
    /**
     * 拒绝删除申请
     * @param request
     * @param id
     * @param topId
     * @return
     */
    @RequestMapping("/rebackDeleteRequest")
    public ModelAndView rebackDeleteRequest(HttpServletRequest request,Integer id){
        ModelAndView mv = new ModelAndView("");
        Map<String, String>map = new HashMap<String, String>();
        FarmMessage fa = new FarmMessage();
        fa.setId( id );
        fa.setStatus( ConstantsSql.Audit_Finish );
        boolean rs = farmMessageService.deletePostFarmMessage(fa);
        if(rs){
            mv.addObject("resultMsg", "撤销删除成功");
        }else{
            mv.addObject("resultMsg", "撤销删除失败");
        }
        RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
        mv.setView(redirectView);
        return mv;
    }
}
