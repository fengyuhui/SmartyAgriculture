package cn.bupt.smartyagl.controller.inf;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.CommentAndReply;
import cn.bupt.smartyagl.entity.GoodsListAndCollect;
import cn.bupt.smartyagl.entity.autogenerate.Collect;
import cn.bupt.smartyagl.entity.autogenerate.CollectView;
import cn.bupt.smartyagl.entity.autogenerate.Comment;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.LikeGoodsView;
import cn.bupt.smartyagl.entity.autogenerate.QRCodeRecode;
import cn.bupt.smartyagl.service.ICommentService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IMayBeLikeService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.IQRCodeService;
import cn.bupt.smartyagl.service.IRecentSearchService;
import cn.bupt.smartyagl.service.impl.CommentServiceImpl;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.QRCodeUtil;
import cn.bupt.smartyagl.util.RedisUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;
import net.sf.jsqlparser.statement.select.Pivot;

/**
 * 商品相关
 * 
 * @author jm E-mail:740869614@qq.com
 * @date 创建时间：2016-5-25 下午2:27:01
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Controller
@RequestMapping("interface/goods")
@MultipartConfig
public class GoodsController extends AppBaseController {
	@Autowired
	IGoodsService iGoodsService;
	@Autowired
	IMayBeLikeService mayBeLikeService;
	@Autowired
	ICommentService commentService;
	@Autowired
	IRecentSearchService recentSearchService;
	@Autowired
	IQRCodeService qrcodeService;
	@Autowired
	IOrderService orderService;

	/**
	 * 获取商品列表
	 * 
	 * @param goods
	 * @param page
	 * @return
	 */
	@RequestMapping("/getGoodsList")
	@ResponseBody
	public Object getGoodsList(HttpServletRequest request, GoodsList goods, int pageSize, int pageNum) {
		String token = request.getParameter("token");
		if (token != null) {
			if (RedisUtil.exist(token)) {
				// 如果该token有效，取出对应的UserId信息
				Integer userId = Integer.parseInt((String) RedisUtil.get(token));
				if (userId != null)
					// System.out.println(goods.getName());
					recentSearchService.addSearchRecord(userId, goods);
			}
		}

		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (goods == null) {
			nau.setContent(new ArrayList<GoodsList>());
			nau.setResult(1);
			nau.setResultDesp("获取商品列表成功");
			return nau;
		}
		// 分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize, "buyNum DESC");
		// 搜索
		List<GoodsList> goodsList = iGoodsService.getGoodsList(goods, page);
		// 取出第一张图片
		for (GoodsList gd : goodsList) {
			gd.setPicture(JsonConvert.getOnePicture(gd.getPicture(), request));
		}
		// 总页数
		int allPages = page.getPages();
		int currentPage = page.getPageNum();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("allPages", allPages);
		map.put("goodsList", goodsList);
		nau.setContent(map);
		nau.setResult(1);
		nau.setResultDesp("获取商品列表成功");
		return nau;
	}

	/**
	 * 获取商品详情
	 * 
	 * @param goodsId
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@RequestMapping("/getGoodsDetail")
	@ResponseBody
	public Object find(HttpServletRequest request, Integer goodsId)
			throws IllegalArgumentException, IllegalAccessException {
		String token = request.getParameter("token");
		Integer userId = null;
		if (token != null && !token.equals("") && RedisUtil.exist(token)) {
			userId = Integer.parseInt((String) RedisUtil.get(token));
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		GoodsList obj = iGoodsService.getGoodsDetail(goodsId);
		if (obj == null) {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("该商品不存在");
		} else {
			String goodsDtail = obj.getGoodsDetail();
			if (goodsDtail == null) {
				goodsDtail = "";
			}
			// 图片类型扎转换
			obj.setPicture(JsonConvert.convertToList(obj.getPicture(), request));
			// 图片
			obj.setGoodsDetail("http://" + IPUtil.getIpAddr(request) + ":" + request.getLocalPort()
					+ "/SmartyAgriculture/interface/goods/detailView?goodsId=" + goodsId);
			// 判断是否是收藏商品
			boolean rs = iGoodsService.isCollect(goodsId, userId);
			GoodsListAndCollect gc = iGoodsService.convertGoods(obj);
			gc.setGoodsDetailHTML(goodsDtail.replace("../upload",
					"http://" + IPUtil.getIpAddr(request) + ":" + request.getLocalPort() + "/upload/"));
			gc.setCollect(rs);
			// 获取评论列表
			// 分页，默认按购买数量排序
			Page page = PageHelper.startPage(1, 1, "");
			List<CommentAndReply> commentList = commentService.getCommentList(gc.getId(), 1);
			if (commentList.size() > 0) {
				gc.setComment(commentList.get(0).getContent());
			}
			// 获取评论总数
			List<Integer> commentCountList = commentService.countComment(gc.getId());
			Integer commentCount = commentCountList.get(0);

			// 计算好评率
			if (commentCountList.get(0) > 0) {
				gc.setFavorableRate(
						100 * (commentCountList.get(2) + commentCountList.get(3)) / commentCountList.get(0) * 1.0);
			} else {
				gc.setFavorableRate(100.0);
			}
			gc.setCommentNum(commentCount);
			nau.setContent(gc);
			nau.setResult(1);
			nau.setResultDesp("查询商品成功");
		}
		return nau;
	}

	@RequestMapping("/getQrcodeGoodsDetail")
	@ResponseBody
	public Object getQrcodeGoodsDetail(int goodsId, HttpServletRequest request) throws Exception {
		Integer userId = (Integer) request.getAttribute("userId");
		if (userId == null) {
			NetDataAccessUtil nau = new NetDataAccessUtil();
			nau.setResult(0);
			nau.setResultDesp("获取二维码商品失败，请先登录");
			return nau;
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		ModelAndView mv = new ModelAndView();
		GoodsList obj = iGoodsService.getGoodsDetail(goodsId);
		if (obj == null) {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("该商品不存在");
		}
		if (!obj.getHasVipPrice()) {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("该商品暂时没有扫码价！");
		} else {
			String goodsDtail = obj.getGoodsDetail();
			if (goodsDtail == null) {
				goodsDtail = "";
			}
			// 图片类型扎转换
			obj.setPicture(JsonConvert.convertToList(obj.getPicture(), request));
			// 图片
			obj.setGoodsDetail("http://" + IPUtil.getIpAddr(request) + ":" + request.getLocalPort()
					+ "/SmartyAgriculture/interface/goods/detailView?goodsId=" + goodsId);
			// 判断是否是收藏商品
			boolean rs = iGoodsService.isCollect(goodsId, userId);
			GoodsListAndCollect gc = iGoodsService.convertGoods(obj);
			gc.setGoodsDetailHTML(goodsDtail.replace("../upload",
					"http://" + IPUtil.getIpAddr(request) + ":" + request.getLocalPort() + "/upload/"));
			gc.setCollect(rs);
			// 获取评论列表
			// 分页，默认按购买数量排序
			Page page = PageHelper.startPage(1, 1, "");
			List<CommentAndReply> commentList = commentService.getCommentList(gc.getId(), 1);
			if (commentList.size() > 0) {
				gc.setComment(commentList.get(0).getContent());
			}
			// 获取评论总数
			List<Integer> commentCountList = commentService.countComment(gc.getId());
			Integer commentCount = commentCountList.get(0);

			// 计算好评率
			if (commentCountList.get(0) > 0) {
				gc.setFavorableRate(
						100 * (commentCountList.get(2) + commentCountList.get(3)) / commentCountList.get(0) * 1.0);
			} else {
				gc.setFavorableRate(100.0);
			}
			gc.setCommentNum(commentCount);
			QRCodeRecode qrr = qrcodeService.getQRCodeRecode(goodsId, userId);
			if (qrr == null) {
				String token = qrcodeService.addQRCodeRecord(goodsId, userId);
				String url = "http://www.zhongyuanfarm.cn/SmartAgricultureWechat/detail.html?" + "userId=" + userId
						+ "&goodsId=" + goodsId;
				String fileName = userId + "_" + gc.getId() + ".jpg";
				String base = "http://" + IPUtil.getIpAddr(request) + ":" + request.getLocalPort()
						+ "/"/* +"/SmartyAgriculture" */;
				// System.out.println(request.getSession().getServletContext().getRealPath("/"));
				String projectPath = request.getSession().getServletContext().getRealPath("");
				// System.out.println("pro:"+projectPath);
				String qrcodePath = projectPath.substring(0,
						projectPath.lastIndexOf(File.separator, projectPath.length() - 2)) + File.separator
						+ Constants.QRCODE_PATH;
				// System.out.println("qrc:"+qrcodePath);

				File f = new File(qrcodePath);
				if (!f.exists())
					f.mkdirs();
				QRCodeUtil.encode(url, qrcodePath + File.separator + fileName);
				gc.setQrcodeUrl(base + Constants.QRCODE_PATH + '/' + fileName);
			} else {
				String base = "http://" + IPUtil.getIpAddr(request) + ":" + request.getLocalPort() + "/";
				String fileName = userId + "_" + gc.getId() + ".jpg";
				gc.setQrcodeUrl(base + Constants.QRCODE_PATH + '/' + fileName);
				// System.out.println(base);
			}
			nau.setContent(gc);
			nau.setResult(1);
			nau.setResultDesp("查询商品成功");
		}
		return nau;
	}

	/**
	 * 添加收藏商品
	 * 
	 * @param collect
	 * @return
	 */
	@RequestMapping("/addCollectGoods")
	@ResponseBody
	public Object addCollectGoods(HttpServletRequest request, Collect collect) {
		Integer userId = (Integer) request.getAttribute("userId");
		collect.setUserId(userId);
		boolean rst = iGoodsService.addCollectGoods(collect);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (rst) {
			nau.setResult(1);
			nau.setResultDesp("添加收藏商品成功");
		} else {
			nau.setResult(0);
			nau.setResultDesp("添加收藏商品失败");
		}
		return nau;
	}

	/**
	 * 获取收藏商品列表
	 * 
	 * @param collect
	 * @return
	 */
	@RequestMapping("/getCollectGoodsList")
	@ResponseBody
	public Object getCollectGoodsList(HttpServletRequest request, Integer pageSize, Integer pageNum) {
		if (pageSize == null || pageSize <= 0) {// 默认一页10个
			pageSize = 10;
		}
		if (pageNum == null || pageNum <= 0) {// 默认从第10个开始
			pageNum = 1;
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		CollectView ce = new CollectView();
		Integer userId = (Integer) request.getAttribute("userId");
		ce.setUserId(userId);
		// 分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize, "id DESC");
		List<CollectView> collectDetailView = iGoodsService.getCollectGoodsList(ce);
		// 取出第一张图片
		for (CollectView gd : collectDetailView) {
			gd.setPicture(JsonConvert.getOnePicture(gd.getPicture(), request));
		}
		// 总页数
		int allPages = page.getPages();
		int currentPage = page.getPageNum();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("allPages", allPages);
		map.put("collectList", collectDetailView);
		nau.setContent(map);
		nau.setResult(1);
		nau.setResultDesp("获取收藏商品列表成功");
		return nau;
	}

	/**
	 * 删除收藏商品
	 * 
	 * @param collect
	 * @return
	 */
	@RequestMapping("/deleteCollectGoods")
	@ResponseBody
	public Object deleteCollectGoods(HttpServletRequest request, Integer goodsId) {
		Integer userId = (Integer) request.getAttribute("userId");
		boolean rst = iGoodsService.deleteCollectGoods(goodsId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (rst) {
			nau.setResult(1);
			nau.setResultDesp("删除收藏商品成功");
		} else {
			nau.setResult(0);
			nau.setResultDesp("删除收藏商品失败");
		}
		return nau;
	}

	/**
	 * 批量删除收藏商品
	 * 
	 * @param collect
	 * @return
	 */
	@RequestMapping("/deleteCollectGoodsBatch")
	@ResponseBody
	public Object deleteCollectGoodsBatch(HttpServletRequest request, String collectIdList) {
		Integer userId = (Integer) request.getAttribute("userId");
		boolean rst = iGoodsService.deleteCollectGoodsBatch(collectIdList);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (rst) {
			nau.setResult(1);
			nau.setResultDesp("删除收藏商品成功");
		} else {
			nau.setResult(0);
			nau.setResultDesp("删除收藏商品失败");
		}
		return nau;
	}

	/**
	 * 获取猜你喜欢列表
	 * 
	 * @author zxy
	 * @param LikeGoods
	 * @return
	 */
	@RequestMapping("/getLikeGoodsList")
	@ResponseBody
	public Object getLikeGoodsList(HttpServletResponse response, HttpServletRequest request, Object handler)
			throws Exception {
		String token = request.getParameter("token");
		NetDataAccessUtil nae = new NetDataAccessUtil();
		if (token == null) {
			// 没有传token的情况，则把销量高的商品推荐给用户
			// LikeGoodsView ce = new LikeGoodsView();
			// 先判断ID是否为空，如果为空，则把销量高的商品推荐给用户
			List<GoodsList> hotlist = mayBeLikeService.getHotSaleGoodsList();
			// 取出第一张图片
			for (GoodsList gd : hotlist) {
				gd.setPicture(JsonConvert.getOnePicture(gd.getPicture(), request));
			}
			nae.setContent(hotlist);
			nae.setResult(1);
			nae.setResultDesp("未登录情况下获取猜你喜欢成功");
		}
		// 判断token是否存在或有效
		boolean result = RedisUtil.exist(token);
		if (result) {
			// 如果该token有效，取出对应的UserId信息
			int userId = Integer.parseInt((String) RedisUtil.get(token));
			request.setAttribute("userId", userId);

			LikeGoodsView ce = new LikeGoodsView();
			// Integer userId = (Integer) request.getAttribute("userId");

			ce.setUserId(userId);
			List<GoodsList> list = mayBeLikeService.getGoodsList(userId);
			// 取出第一张图片
			for (GoodsList gd : list) {
				gd.setPicture(JsonConvert.getOnePicture(gd.getPicture(), request));
			}
			nae.setContent(list);
			nae.setResult(1);
			nae.setResultDesp("登录情况下获取猜你喜欢成功");
		} else {
			// token无效 向前端返回消息
			// 没有传token的情况，则把销量高的商品推荐给用户
			// LikeGoodsView ce = new LikeGoodsView();
			// 先判断ID是否为空，如果为空，则把销量高的商品推荐给用户
			List<GoodsList> hotlist = mayBeLikeService.getHotSaleGoodsList();
			// 取出第一张图片
			for (GoodsList gd : hotlist) {
				gd.setPicture(JsonConvert.getOnePicture(gd.getPicture(), request));
			}
			nae.setContent(hotlist);
			nae.setResult(1);
			nae.setResultDesp("未登录情况下获取猜你喜欢成功");
		}
		return nae;
	}

	/**
	 * 商品详情HTMl5界面
	 * 
	 * @author
	 * @param
	 * @return
	 */
	@RequestMapping("/detailView")
	@ResponseBody
	public ModelAndView goodsDetial(HttpServletRequest request, Integer goodsId) {
		GoodsList goodsList = iGoodsService.getGoodsDetail(goodsId);
		ModelAndView mv = new ModelAndView(Constants.GOODS_DETAIL);
		String detail = goodsList.getGoodsDetail();
		if (detail != null) {
			detail = detail.replace("../", "/../");
		} else {
			detail = "";
		}
		mv.addObject("html", detail);
		// System.out.println( detail );
		return mv;
	}

	/**
	 * 获取商品列表
	 * 
	 * @param Goods
	 * @param page
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public Object test(HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("") + "/";
		try {
			// 设置上下方文
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				if (!iter.hasNext())// 没有文件上传
					return false;
				while (iter.hasNext()) {// 遍历文件
					// 由CommonsMultipartFile继承而来,拥有上面的方法.
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String fileName = file.getOriginalFilename();
						String path = rootPath + "/" + fileName.replace(".JPG", ".jpg");
						File localFile = new File(path);
						file.transferTo(localFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 添加评论
	 * @param request
	 *            
	 * @return <code>NetDataAccessUtil</code>类型结果
	 */
	
	@RequestMapping("/addComments")
	@ResponseBody
	public Object addComments(HttpServletRequest request, String orderId, String goodsId, 
			String content, String Level, String isAnonymous, String serviceMark) {

		System.out.println("I'm in");
		//NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		
		Part part = null; // servlet3.0的文件上传新办法  
		
		/*try {
			part = request.getPart("file");
			System.out.println("file: "+part);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		Comment comment = new Comment();
		
		System.out.println("goodsId: "+goodsId);
		System.out.println("orderId: "+orderId);
		System.out.println("content: "+content);
		//System.out.println("pic :"+pic);
		//String userId =(String) request.getAttribute("userId");
		Integer userId = (Integer) request.getAttribute("userId");
		System.out.println("userId: "+userId);
		System.out.println("Level: "+Level);
		System.out.println("isAnonymous: "+isAnonymous);
		System.out.println("serviceMark: "+serviceMark);
		
		String parentMessageId = "1";
		
		//String Level = (String) request.getAttribute("level"); 
		//System.out.println("level: "+Level);
		
		
		//MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		//String Level = multiRequest.getParameter("level"); 
		System.out.println("level: "+Level);
		
		//String goodsId = multiRequest.getParameter("goodsId");
		//String serviceMark = multiRequest.getParameter("serviceMark");
		//String parentMessageId = multiRequest.getParameter("parentMessageId");
		//String isAnonymous = multiRequest.getParameter("isAnonymous");
		
		//String userId = multiRequest.getParameter("userId");
		
		
		
		//String content = multiRequest.getParameter("content");
		//String orderId = multiRequest.getParameter("orderId");
		
		
		
		
		int serMark = 0;
		int level;
		java.util.Date cur = new java.util.Date();
		// goodsId
		if (goodsId == null || goodsId.trim().equals("")) {
			//netDataAccessUtil.setResult(0);
			//netDataAccessUtil.setResultDesp("商品ID为空");
			//return netDataAccessUtil;
			map.put("result", 0);
			map.put("resultDesp", "商品ID为空");
			return map;
		} else {
			comment.setGoodsId(Integer.valueOf(goodsId));
		}
		// userId
		//if (userId == null || userId.trim().equals("")) {
		if (userId == null) {
			//netDataAccessUtil.setResult(0);
			map.put("result", 0);
			map.put("resultDesp", "用户ID为空");
			//netDataAccessUtil.setResultDesp("用户ID为空");
			//return netDataAccessUtil;
			return map;
		} else {
			comment.setUserId(Integer.valueOf(userId));
		}
		// Level
		if (Level == null || Level.trim().equals("")) {
			level = -1;
		} else {
			level = Integer.valueOf(Level);
		}
		// orderId
		if (orderId == null || orderId.trim().equals("")) {
			//netDataAccessUtil.setResult(0);
			//netDataAccessUtil.setResultDesp("订单ID内容为空");
			//return netDataAccessUtil;
			map.put("result", 0);
			map.put("resultDesp", "订单ID内容为空");
			return map;
		}
		// content
		if (content == null || content.trim().equals("")) {
			//netDataAccessUtil.setResult(0);
			//netDataAccessUtil.setResultDesp("评论内容为空");
			//return netDataAccessUtil;
			map.put("result", 0);
			map.put("resultDesp", "评论内容为空");
			return map;
		} else {
			System.out.println("content:" + content);
			comment.setContent(content);
		}
		// serviceMark
		if (serviceMark == null || serviceMark.equals("")) {
			serMark = -1;
		}
		comment.setLevel((level + serMark) / 2);
		// parentMessageId
		if ((parentMessageId == null || parentMessageId.trim().equals(""))) {
			comment.setParentMessageId(-1);
		} else {
			comment.setParentMessageId(Integer.valueOf(parentMessageId));
		}
		// isAnonymous
		if (!(isAnonymous == null || isAnonymous.trim().equals(""))) {
			comment.setIsAnonymous(1);
		}
		// dateTime
		comment.setCreateTime(cur);
		// viewNum，默认
		comment.setViewNum(0);
		// status,默认
		comment.setStatus(0);
		if ((parentMessageId == null || parentMessageId.trim().equals(""))) {
			comment.setParentMessageId(-1);
		} else {
			comment.setParentMessageId(Integer.valueOf(parentMessageId));
		}
		// 文件上传
		//String commentPath = request.getSession().getServletContext().getRealPath("") + "../" + "upload/comment";
		String commentPath = "../" + "upload/comment";
		
		System.out.println("commemtPath:" + commentPath);
		
		//Iterator<String> iter = multiRequest.getFileNames();
		StringBuffer pictureList = new StringBuffer("[");
		/*try {
			while (iter.hasNext()) {// 遍历文件
				// 由CommonsMultipartFile继承而来,拥有上面的方法.
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					String fileName = file.getOriginalFilename();
					String path = commentPath + "/" + fileName.replace(".JPG", ".jpg");
					File localFile = new File(path);
					localFile.getParentFile().mkdirs();
					localFile.createNewFile();
					file.transferTo(localFile);
					pictureList.append("\"").append(fileName).append("\"").append(",");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		if (pictureList.length() > 1) {
			pictureList.deleteCharAt(pictureList.length() - 1);
		}
		pictureList.append("]");
		// System.out.println(pictureList);
		comment.setPictureList(pictureList.toString());

		boolean ans = commentService.addComments(comment);

		// 计算好评率
		List<Integer> commentNumber = commentService.countComment(comment.getGoodsId());
		boolean ret = true;
		if (commentNumber != null&&commentNumber.get(0)!=0) {
			double favoriteRate = (double) (commentNumber.get(2) + commentNumber.get(3))
					/ (double) commentNumber.get(0);
			ret = iGoodsService.updateGoodsFavoriteRate(comment.getGoodsId(), favoriteRate);
		}
		// 修改订单状态为交易成功
		boolean res = orderService.updateOrderStatus(orderId, 7);

		if (ans && res && ret) {
			//netDataAccessUtil.setResult(1);
			//netDataAccessUtil.setResultDesp("添加成功!");
			map.put("result", 1);
			map.put("resultDesp", "添加成功!");
		} else {
			//netDataAccessUtil.setResult(0);
			//netDataAccessUtil.setResultDesp("添加失败！");
			map.put("result", 0);
			map.put("resultDesp", "添加失败！");
		}
		//return netDataAccessUtil;
		return map;
	}
	
	
	/**
	 * 添加评论
	 * @param request
	 *            
	 * @return <code>NetDataAccessUtil</code>类型结果
	 */
	/*@RequestMapping("/addComments")
	@ResponseBody
	public Object addComments(HttpServletRequest request) {
		System.out.println("I'm in");
		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		Comment comment = new Comment();
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		String goodsId = multiRequest.getParameter("goodsId");
		System.out.println("goodsId: "+goodsId);
		String userId = multiRequest.getParameter("userId");
		String Level = multiRequest.getParameter("level");
		String serviceMark = multiRequest.getParameter("serviceMark");
		String parentMessageId = multiRequest.getParameter("parentMessageId");
		String isAnonymous = multiRequest.getParameter("isAnonymous");
		String content = multiRequest.getParameter("content");
		String orderId = multiRequest.getParameter("orderId");
		int serMark = 0;
		int level;
		java.util.Date cur = new java.util.Date();
		// goodsId
		if (goodsId == null || goodsId.trim().equals("")) {
			netDataAccessUtil.setResult(0);
			netDataAccessUtil.setResultDesp("商品ID为空");
			return netDataAccessUtil;
		} else {
			comment.setGoodsId(Integer.valueOf(goodsId));
		}
		// userId
		if (userId == null || userId.trim().equals("")) {
			netDataAccessUtil.setResult(0);
			netDataAccessUtil.setResultDesp("用户ID为空");
			return netDataAccessUtil;
		} else {
			comment.setUserId(Integer.valueOf(userId));
		}
		// Level
		if (Level == null || Level.trim().equals("")) {
			level = -1;
		} else {
			level = Integer.valueOf(Level);
		}
		// orderId
		if (orderId == null || orderId.trim().equals("")) {
			netDataAccessUtil.setResult(0);
			netDataAccessUtil.setResultDesp("订单ID内容为空");
			return netDataAccessUtil;
		}
		// content
		if (content == null || content.trim().equals("")) {
			netDataAccessUtil.setResult(0);
			netDataAccessUtil.setResultDesp("评论内容为空");
			return netDataAccessUtil;
		} else {
			System.out.println("content:" + content);
			comment.setContent(content);
		}
		// serviceMark
		if (serviceMark == null || serviceMark.equals("")) {
			serMark = -1;
		}
		comment.setLevel((level + serMark) / 2);
		// parentMessageId
		if ((parentMessageId == null || parentMessageId.trim().equals(""))) {
			comment.setParentMessageId(-1);
		} else {
			comment.setParentMessageId(Integer.valueOf(parentMessageId));
		}
		// isAnonymous
		if (!(isAnonymous == null || isAnonymous.trim().equals(""))) {
			comment.setIsAnonymous(1);
		}
		// dateTime
		comment.setCreateTime(cur);
		// viewNum，默认
		comment.setViewNum(0);
		// status,默认
		comment.setStatus(0);
		if ((parentMessageId == null || parentMessageId.trim().equals(""))) {
			comment.setParentMessageId(-1);
		} else {
			comment.setParentMessageId(Integer.valueOf(parentMessageId));
		}
		// 文件上传
		String commentPath = request.getSession().getServletContext().getRealPath("") + "../" + "upload/comment";
		System.out.println("picturePath:" + commentPath);
		Iterator<String> iter = multiRequest.getFileNames();
		StringBuffer pictureList = new StringBuffer("[");
		try {
			File dir = new File(commentPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			while (iter.hasNext()) {// 遍历文件
				// 由CommonsMultipartFile继承而来,拥有上面的方法.
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					String fileName = generateFileNamePrefix()+file.getOriginalFilename().replace(".JPG", ".jpg");
					String path = commentPath + "/"+fileName;
					File localFile = new File(path);
					while (localFile.exists()) {
						System.out.println("文件已经存在！"+path);
						fileName = generateFileNamePrefix()+file.getOriginalFilename().replace(".JPG", ".jpg");
						path = commentPath + "/"+fileName;
						localFile = new File(path);
					}
					localFile.createNewFile();
					file.transferTo(localFile);
					pictureList.append("\"").append("comment/").append(fileName).append("\"").append(",");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pictureList.length() > 1) {
			pictureList.deleteCharAt(pictureList.length() - 1);
		}
		pictureList.append("]");
		// System.out.println(pictureList);
		comment.setPictureList(pictureList.toString());

		boolean ans = commentService.addComments(comment);

		// 计算好评率
		List<Integer> commentNumber = commentService.countComment(comment.getGoodsId());
		boolean ret = true;
		if (commentNumber != null&&commentNumber.get(0)!=0) {
			double favoriteRate = (double) (commentNumber.get(2) + commentNumber.get(3))
					/ (double) commentNumber.get(0);
			ret = iGoodsService.updateGoodsFavoriteRate(comment.getGoodsId(), favoriteRate);
		}
		// 修改订单状态为交易成功
		boolean res = orderService.updateOrderStatus(orderId, 7);

		if (ans && res && ret) {
			netDataAccessUtil.setResult(1);
			netDataAccessUtil.setResultDesp("添加成功!");
		} else {
			netDataAccessUtil.setResult(0);
			netDataAccessUtil.setResultDesp("添加失败！");
		}
		return netDataAccessUtil;
	}*/
	/**
	 * 生成随机的文件名前缀
	 * @return 前缀字符串
	 */
	/*private String generateFileNamePrefix() {
		String prefix = new StringBuffer().append(System.currentTimeMillis()).append((int)(Math.random()*1000)).append("_").toString();
		return prefix;
	}*/

}
