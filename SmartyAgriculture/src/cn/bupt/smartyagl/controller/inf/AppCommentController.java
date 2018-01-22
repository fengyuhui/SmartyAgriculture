package cn.bupt.smartyagl.controller.inf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.entity.CommentAndReply;
import cn.bupt.smartyagl.entity.autogenerate.Comment;
import cn.bupt.smartyagl.entity.autogenerate.CommentView;
import cn.bupt.smartyagl.service.ICommentService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.ImageUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;

/**
 * 评论相关接口
 * @author zw
 */
@Controller
@RequestMapping("interface/commentApp")
public class AppCommentController extends AppBaseController {
	
	@Autowired
	ICommentService commentService;
	@Autowired
	IGoodsService goodsService;
	@Autowired
	IOrderService orderService;

	//发表评论，获取评论列表接口
	@RequestMapping("/publish")
	@ResponseBody
	public Object publishComments(HttpServletRequest request,Comment Comment,Integer serviceMark,String orderId) throws JsonProcessingException {
		//评论列表的绝对路劲++
		String commentPath = request.getSession().getServletContext().getRealPath("")+"/../"+"upload/comment";
		//添加图片上传处理
		String filePathJson = ImageUtil.batchFileUpLoad(request,commentPath);	
//		String filePathJsonString = null;
//		// 上传文件
//				List<String> filePath = new ArrayList<String>();
//				filePath = this.uploadFile(request, commentPath);
//				if (filePath != null) {
//					// json格式化
//					ObjectMapper mapper = new ObjectMapper();
//					filePathJsonString = mapper.writeValueAsString(filePath);
//
//					Comment.setPictureList(filePathJsonString);
//				}
		
		//如果是回复评论
		if((Integer)Comment.getGoodsId()==null){
			//查询commentId表
			Comment.setGoodsId(-1);
		}
		if((Integer)Comment.getLevel()==null){
			Comment.setLevel(-1);
		}
		if((Integer)serviceMark==null){
			serviceMark=-1;
		}
		if((Integer)Comment.getIsAnonymous()==null){
			Comment.setIsAnonymous(1);
		}
		if((Integer)Comment.getViewNum()==null){
			Comment.setViewNum(0);
		}
		//获取当前时间
		Date curTime = new Date();
		//获取用户的id
		Integer userId = (Integer) request.getAttribute("userId");
		Comment.setUserId(userId);
		//设置评分
		Comment.setLevel((Comment.getLevel()+serviceMark)/2);
		Comment.setCreateTime(curTime);
		Comment.setPictureList(filePathJson);
		Comment.setStatus(0);
		boolean result =  commentService.addComments(Comment);
		NetDataAccessUtil nda = new NetDataAccessUtil();
		if(-1!=Comment.getGoodsId()){
			//如果是发表评论   计算好评率
			List<Integer> commentNumber = commentService.countComment(Comment.getGoodsId());
			double favoriteRate = (double)(commentNumber.get(2)+commentNumber.get(3))/(double)commentNumber.get(0);
			boolean ret = goodsService.updateGoodsFavoriteRate(Comment.getGoodsId(),favoriteRate);
			//修改订单状态为交易成功
			boolean res = orderService.updateOrderStatus(orderId,7, filePathJson);//12.3日添加filePathJson
		}
		if(result){
			nda.setContent(null);
			nda.setResult(1);
			nda.setResultDesp("添加评论成功");
		}else{
			nda.setContent(null);
			nda.setResult(0 );
			nda.setResultDesp("添加评论失败");
		}
		return nda;
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
						String tempString = "/" + fileName + "_" + i;
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
	
	//获取商品评论列表
	@RequestMapping("/getCommentList")
	@ResponseBody
	public Object getCommentList(HttpServletRequest request,Comment Comment,Integer pageSize,Integer pageNum) {
		NetDataAccessUtil nda = new NetDataAccessUtil();
		if(pageSize == null || pageSize <= 0){//默认一页10个
			pageSize = 10;
		}
		if(pageNum == null || pageNum <= 0){//默认从第10个开始
			pageNum = 1;
		}
		//分页，默认按评论时间排序
		Page page = PageHelper.startPage(pageNum, pageSize, "createTime DESC");
		//根据level,判断获取评论的类型（1全部  2有图 3好 4中 5差）
		Integer goodsId = Comment.getGoodsId();
		Integer level = Comment.getLevel();
		List<CommentAndReply> commentList = commentService.getCommentList(goodsId, level);
        for(CommentAndReply gd : commentList){
    		if(!gd.getPictureList().equals("[]")){
        		gd.setPictureList(JsonConvert.convertToList(gd.getPictureList(), request));
    		}else{
    			gd.setPictureList("");
    		}
    		//用户图像图片处理
    		if(gd.getPortrait()!=null){
    			gd.setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+gd.getPortrait());
    		}
    	}
        // 总页数
     	int allPages = page.getPages();
     	int currentPage = page.getPageNum();
     	Map<String,Object> map = new HashMap<String,Object>();
     	map.put("currentPage", currentPage);
     	map.put("allPages", allPages);
     	map.put("commentList", commentList);
		nda.setContent(map);
		nda.setResult(1);
		nda.setResultDesp("获取评论列表成功！");
		return nda;
	}
	
	//获取商品评论数
	@RequestMapping("/getCommentNumber")
	@ResponseBody
	public Object getCommentNumber(HttpServletRequest request,Comment Comment) {
		NetDataAccessUtil nda = new NetDataAccessUtil();
		Integer goodsId = Comment.getGoodsId();
		List<Integer> commentNumber = commentService.countComment(goodsId);
		if(null!=commentNumber){
			nda.setContent(commentNumber);
			nda.setResult(1);
			nda.setResultDesp("获取评论数成功！");
		}else{
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("获取评论数失败！");
		}
		return nda;
	}
	
	//获取“个人”的我的评论列表
	@RequestMapping("/getMyComments")
	@ResponseBody
	public Object getMyComments(HttpServletRequest request,Integer level,Integer pageSize,Integer pageNum) {
		NetDataAccessUtil nda = new NetDataAccessUtil();
		//只查找对商品的原始评论，不包含对评论的回复
		int userId = (Integer)request.getAttribute("userId");
		//分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize, "createTime DESC");
		List<CommentView> commentList = commentService.personComments(userId,level);
        for(CommentView gd : commentList){
    		if(!gd.getPictureList().equals("[]")){
        		gd.setPictureList(JsonConvert.convertToList(gd.getPictureList(), request));
    		}else{
    			gd.setPictureList("");
    		}
    		//用户图像图片处理
    		if(gd.getPortrait()!=null){
    			gd.setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+gd.getPortrait());
    		}
        }
		// 总页数
		int allPages = page.getPages();
		int currentPage = page.getPageNum();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("currentPage", currentPage);
		map.put("allPages", allPages);
		map.put("commentList", commentList);
		nda.setContent(map);
		nda.setResult(1);
		nda.setResultDesp("获取个人评论列表成功！");
		return nda;
	}
	
	//获取“个人”的我的评论数量
	@RequestMapping("/getMyCommentNumber")
	@ResponseBody
	public Object getMyCommentNumber(HttpServletRequest request) {
		NetDataAccessUtil nda = new NetDataAccessUtil();
		int userId = (Integer)request.getAttribute("userId");
		List<Integer> commentNumber = commentService.countMyComment(userId);
		if(null!=commentNumber){
			nda.setContent(commentNumber);
			nda.setResult(1);
			nda.setResultDesp("获取评论数成功！");
		}else{
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("获取评论数失败！");
		}
		return nda;
	}
	
	//获取评论详情
	@RequestMapping("/getCommentDetail")
	@ResponseBody
	public Object getCommentDetail(HttpServletRequest request,Integer commentId) {
		NetDataAccessUtil nda = new NetDataAccessUtil();
		CommentAndReply commentDetail = commentService.getCommentDetail(commentId);
		if(commentDetail==null){
			nda.setContent(null);
			nda.setResult(1);
			nda.setResultDesp("获取评论详情失败！");
			return nda;
		}
		//处理图片信息
		if(!commentDetail.getPictureList().equals("[]")){
			commentDetail.setPictureList(JsonConvert.convertToList(commentDetail.getPictureList(), request));
		}else{
			commentDetail.setPictureList("");
		}
		
		//用户图像图片处理
		if(commentDetail.getPortrait()!=null){
			commentDetail.setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+commentDetail.getPortrait());
		}
		//回复列表中的图片的处理
		if(commentDetail.getReplyList()!=null){
			List<CommentView> replyList = commentDetail.getReplyList();
			for(CommentView gd : replyList){
				if(!gd.getPictureList().equals("[]")){
	        		gd.setPictureList(JsonConvert.convertToList(gd.getPictureList(), request));
	    		}else{
	    			gd.setPictureList("");
	    		}
	    		//用户图像图片处理
	    		if(gd.getPortrait()!=null){
	    			gd.setPortrait("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+gd.getPortrait());
	    		}
			}
		}
		
		//增加评论的浏览量
		boolean ret = commentService.updateViewNum(commentId);
		nda.setContent(commentDetail);
		nda.setResult(1);
		nda.setResultDesp("获取评论详情成功！");
		return nda;
	}
	
	/**
	 * 删除评论及其回复信息
	 */
	@RequestMapping("/deleteComment")
	@ResponseBody
	public Object deleteComment(HttpServletRequest request,Integer commentId) {
		NetDataAccessUtil nda = new NetDataAccessUtil();
		boolean ret = commentService.deleteComment(commentId);
		if(ret==true){
			nda.setContent(null);
			nda.setResult(1);
			nda.setResultDesp("删除评论成功！");
		}else{
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("删除评论失败！");
		}
		return nda;
	}
}
