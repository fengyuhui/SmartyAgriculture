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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.CookLeisureMapper;
import cn.bupt.smartyagl.entity.autogenerate.CookLeisure;
import cn.bupt.smartyagl.service.ICookLeisureService;
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
@RequestMapping("/cookLeisure")
public class CookLeisureController extends BaseController {

	@Autowired
	ICookLeisureService cookLeisureService;
	
	@Autowired
	CookLeisureMapper cookLeisureMapper;

	int pageSize = Constants.PAGESIZE;// 每一页的大小
 
    int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
    	
    /**
     * 显示农场信息列表
     * @author zxy
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{types}")
    public ModelAndView cookLeisureIndex(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,CookLeisure cookLeisureSelective,HttpServletRequest request) {
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
        ModelAndView modelAndView = new ModelAndView(Constants.CookLeisure_INDEX);
        //System.out.println(cookLeisureSelective.getMessageName());
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<CookLeisure> cookLeisureList = cookLeisureService.getCookLeisureList(cookLeisureSelective);
        modelAndView.addObject("cookLeisureList",cookLeisureList);
        //总页数
        allPages = page.getPages();
        modelAndView.addObject("allPages", allPages);
        // 当前页码
        currentPage = page.getPageNum();
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("resultMsg", request.getParameter("resultMsg"));
        if(cookLeisureSelective.getMessageName() != null)
        	modelAndView.addObject("MessageName", cookLeisureSelective.getMessageName() );
        return modelAndView;
    }

    /**
     * 显示农场信息权限列表
     * @author 
     */
    @RequestMapping(value="/judge/{allPages}/{currentPage}/{types}")
    public ModelAndView judgeList(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,CookLeisure cookLeisureSelective,HttpServletRequest request) {
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
        ModelAndView modelAndView = new ModelAndView(Constants.CookLeisure_AuthList);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<CookLeisure> cookLeisureList = cookLeisureService.getCookLeisureDraftList();
        modelAndView.addObject("cookLeisureList",cookLeisureList);
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
     * 添加休闲美食信息
     * @param request
     * @param CookLeisure
     * @return
     */
  	@RequestMapping("/add")
  	public ModelAndView addCookLeisure(HttpServletRequest request,CookLeisure cookLeisure){

  		ModelAndView mv = new ModelAndView(Constants.CookLeisure_ADD);
  		
  		return mv;
  	}

	/**
	 * 
	 * @param CookLeisure
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/addPost")
	public ModelAndView addCookLeisurePost(CookLeisure cookLeisure,MultipartFile file,
			HttpServletRequest request)
			throws Exception {		
		ModelAndView mv = new ModelAndView(Constants.CookLeisure_ADD);
		if(cookLeisure != null && cookLeisure.getMessageName() != null && cookLeisure.getMessageType()!=null){
	//		CookLeisure cookLeisure = cookLeisureMapper.selectByPrimaryKey( id );
			cookLeisure.setCreatTime(new Date());
	//  		cookLeisure.setTopOrNot(cookLeisure.getTopOrNot());
	  		cookLeisure.setStatus(1);
	  		cookLeisure.setOriginalMessageId(0);
			// 上传文件路径
	  		Long time =System.currentTimeMillis();
			String fileName = time.toString();
			String tempString = Constants.COOKLEISURE_TYPE_STRING + fileName ;
		    String path = request.getSession().getServletContext().getRealPath("")+"/../"
							+ Constants.FILE_PATH+tempString;
		    tempString += "/" + ImageUtil.uploadSuoLveImage(file, path);
		    cookLeisure.setIcon("/"+ Constants.FILE_PATH+tempString);
			// 上传文件
//			List<String> filePath = new ArrayList<String>();
//			filePath = this.uploadFile(request, path);
//			if (filePath != null) {
//				// json格式化
//				ObjectMapper mapper = new ObjectMapper();
//				String filePathJsonString = mapper.writeValueAsString(filePath);
//			}
		boolean flag = cookLeisureService.addCookLeisures(cookLeisure);//
		if (flag) {
			mv.addObject("resultMsg", "添加信息成功");
//			return modelAndView;
			// return Constants.PRODUCT_INDEX;
		} else {
			mv.addObject("resultMsg", "添加信息失败");
			
		}
		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
		mv.setView(redirectView);
		}
		return mv;
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
//						String tempString = "goods/" + fileName + "_" + i;
						String tempString = "cookLeisure/" + fileName + "_" + i;
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
	public ModelAndView getcookLeisuretDetail(Integer id,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(Constants.CookLeisure_FIND);
		CookLeisure cookLeisureList = cookLeisureService.getCookLeisureDetail(id);
		modelAndView.addObject("cookLeisureList", cookLeisureList);
		
		// 状态
		int status = cookLeisureList.getStatus();
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
		//cookLeisureList.setCurrentMessage( cookLeisureList.getCurrentMessage() );
		modelAndView.addObject("currentMessage", cookLeisureList.getCurrentMessage());
		modelAndView.addObject("status", statusString);
		modelAndView.addObject("messageName", cookLeisureList.getMessageName());
		modelAndView.addObject("icon",cookLeisureList.getIcon());
		modelAndView.addObject("dietitian",cookLeisureList.getDietitian());
		modelAndView.addObject("subTitle",cookLeisureList.getSubTitle());
		return modelAndView;
	}


	/**
	* 发布草稿
	* @param request 
	* @return
	*/
	@RequestMapping("/update/{id}")
	public ModelAndView update(@PathVariable(value = "id") int id) {
		CookLeisure cookLeisure = cookLeisureService.getCookLeisureDetail(id);
		ModelAndView modelAndView = new ModelAndView(Constants.CookLeisure_EDIT);
		modelAndView.addObject("cookLeisure", cookLeisure);
		return modelAndView;
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
  	public ModelAndView updatePost(HttpServletRequest request,CookLeisure cookLeisure,MultipartFile file) throws IllegalStateException, IOException{
  		ModelAndView mv = new ModelAndView(Constants.CookLeisure_EDIT);
  		if(cookLeisure != null && cookLeisure.getId() != null){
  			if(file.getSize() != 0) {
  		  		Long time =System.currentTimeMillis();
  				String fileName = time.toString();
  				String tempString = Constants.COOKLEISURE_TYPE_STRING + fileName ;
  			    String path = request.getSession().getServletContext().getRealPath("")+"/../"
  								+ Constants.FILE_PATH+tempString;
  			    tempString += "/" + ImageUtil.uploadSuoLveImage(file, path);
  			    cookLeisure.setIcon("../"+ Constants.FILE_PATH+tempString);
  			}
  			else {
  				cookLeisure.setIcon(cookLeisureService.getCookLeisureById(cookLeisure.getId()).getIcon());
  			}
  	  		boolean rst = cookLeisureService.updateCookLeisure(cookLeisure);
  	  		if(rst){
  	  			mv.addObject("resultMsg", "申请修改成功");
  	  		}else{
  	  			mv.addObject("resultMsg", "申请修改失败");
  	  		}
  			
  	  		//跳转到首页
  	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
  	  		mv.setView(redirectView);
  		}
  		return mv;
  	}
	
// /**
// * 发布草稿
// * @param request
// * @param cookLeisure
// * @return
// * @throws IOException 
// * @throws IllegalStateException 
// */
//	@RequestMapping("/updatePost")
//	public ModelAndView updatePost(HttpServletRequest request,@RequestParam MultipartFile file,CookLeisure cookLeisure) throws IllegalStateException, IOException{
//		ModelAndView mv = new ModelAndView(Constants.CookLeisure_EDIT);
//  		cookLeisure.setUpdateTime(new Date());
//		if(cookLeisure != null && cookLeisure.getId() != null){
//			// 上传文件路径
//			Long time = System.currentTimeMillis();
//			String fileName = time.toString();
//			//存在数据库文件路径
//			String tempString = Constants.COOKLEISURE_TYPE_STRING + fileName ;
//  		String path = request.getSession().getServletContext().getRealPath("")+"/../"
//				+ Constants.FILE_PATH+tempString;
//	  		boolean rst = cookLeisureService.updateCookLeisure(cookLeisure);
//	  		if(rst){
//	  			mv.addObject("resultMsg", "修改休闲美食信息成功");
//	  		}else{
//	  			mv.addObject("resultMsg", "修改休闲美食信息失败");
//	  		}
//	  		//跳转到首页
//	  		RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
//	  		mv.setView(redirectView);
//		}
//		return mv;
//	}
	
	//删除
	@RequestMapping("/delete")
	public ModelAndView deleteAdmin(HttpServletRequest request,Integer id){
		ModelAndView mv = new ModelAndView("");
		boolean rs = cookLeisureService.deleteCookLeisure(id);
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
	public ModelAndView judgePublish(HttpServletRequest request,Integer id){
		ModelAndView mv = new ModelAndView("");
		boolean rs = cookLeisureService.verifyAddCookLeisure(id);
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
		public ModelAndView judgeDraft(HttpServletRequest request, Integer id){
			ModelAndView mv = new ModelAndView("");
			boolean rs = cookLeisureService.verifyChangeCookLeisure(id);
			if(rs){
				mv.addObject("resultMsg", "审核草稿成功");
	  	}else{
	  		mv.addObject("resultMsg", "审核草稿失败");
	  	}
			RedirectView redirectView = new RedirectView( "judge/0/1/prvious" );
			mv.setView(redirectView);
	  	return mv;
		}
	
	@RequestMapping("/topOrNot")
	@ResponseBody
		public Object top(HttpServletRequest request, Integer id,Integer topId){
		Map<String, String>map = new HashMap<String, String>();
		boolean rs = cookLeisureService.changeTop(id, topId);
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
     * @return
     */
    @RequestMapping("/deleteRequest")
    public ModelAndView deleteRequest(HttpServletRequest request,Integer id){
        ModelAndView mv = new ModelAndView("");
        Map<String, String>map = new HashMap<String, String>();
        CookLeisure fa = new CookLeisure();
        fa.setId( id );
        fa.setStatus( ConstantsSql.Audit_WaitDelete );
        boolean rs = cookLeisureService.deletePostCookLeisure(fa);
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
     * @return
     */
    @RequestMapping("/rebackDeleteRequest")
    public ModelAndView rebackDeleteRequest(HttpServletRequest request,Integer id){
        ModelAndView mv = new ModelAndView("");
        Map<String, String>map = new HashMap<String, String>();
        CookLeisure fa = new CookLeisure();
        fa.setId( id );
        fa.setStatus( ConstantsSql.Audit_Finish );
        boolean rs = cookLeisureService.deletePostCookLeisure(fa);
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