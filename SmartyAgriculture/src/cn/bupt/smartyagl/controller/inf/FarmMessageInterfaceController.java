package cn.bupt.smartyagl.controller.inf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.entity.CommentAndReply;
import cn.bupt.smartyagl.entity.FarmMessageAndHtml;
import cn.bupt.smartyagl.entity.GoodsListAndCollect;
import cn.bupt.smartyagl.entity.autogenerate.Album;
import cn.bupt.smartyagl.entity.autogenerate.CookLeisure;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessageExample;
import cn.bupt.smartyagl.entity.autogenerate.FarmPhotos;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.service.IAlbumService;
import cn.bupt.smartyagl.service.ICookLeisureService;
import cn.bupt.smartyagl.service.IFarmMessageService;
import cn.bupt.smartyagl.service.IFarmPhotosService;
import cn.bupt.smartyagl.util.BeanUtil;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-12 下午3:53:50 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/farmMessage")
public class FarmMessageInterfaceController extends AppBaseController{

	@Autowired
	IFarmMessageService farmMessageService;
	
	@Autowired
	IFarmPhotosService farmPhotosService;
	
	@Autowired
	IAlbumService albumService;
	
	/**
	 * 获取农场信息
	 * 1 我们农场 2 生态技术  3休闲活动
	 * @throws Exception 
	 */
	@RequestMapping("/getFarmMessage")
	@ResponseBody
	public NetDataAccessUtil getFarmMessage(HttpServletRequest request,Integer typeId) throws Exception{
		boolean type = typeId.equals( ConstantsSql.FarmType_Knowledge) || typeId.equals( ConstantsSql.FarmType_Healthy)|| typeId.equals( ConstantsSql.FarmType_Activity);
		List<FarmMessage> ar = farmMessageService.getFarmMessageList(typeId, 1, 1);
		List<FarmMessageAndHtml> list = new ArrayList<FarmMessageAndHtml>();
		
		for( FarmMessage fm : ar ){
			FarmMessageAndHtml fh = new FarmMessageAndHtml();
			BeanUtil.fatherToChild(fm, fh);
			FarmMessage objFarmMessage = farmMessageService.getFarmMessageDetail(fm.getId());
			String message = objFarmMessage.getCurrentMessage();
			objFarmMessage.setCurrentMessage("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/SmartyAgriculture/interface/farmMessage/detailView?messageId="+ fm.getId());
			
			fh = farmMessageService.convertMessage(objFarmMessage);
			fh.setMessageDetailHTML(message.replace("../upload", "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"));
			
			fh.setDetailUrl( "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/SmartyAgriculture/farmMessage/find?id="+ fm.getId() );
			list.add(fh); 
			
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if( ar.size() > 0 ){
			nau.setContent(list);
			nau.setResult(1);
			nau.setResultDesp("查询成功");
		}else{
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("查询失败");
		}
		return nau;
	}
	
	@RequestMapping("/detailView")
	@ResponseBody
	public ModelAndView goodsDetial(HttpServletRequest request,Integer messageId){
		FarmMessage message = farmMessageService.findFarmMessage(messageId);
		ModelAndView mv = new ModelAndView(Constants.CookLeisure_Message);
		String detail = message.getCurrentMessage();
		mv.addObject( "detail" , detail);
		return mv;
	}
	

	/**
	 * 商品详情HTMl5界面
	 * @author 
	 * @param 
	 * @return
	 */
	@RequestMapping("/detView")
	@ResponseBody
	public ModelAndView goodsDetil(HttpServletRequest request,Integer messageId){
		FarmMessage message = farmMessageService.findFarmMessage(messageId);
		ModelAndView mv = new ModelAndView(Constants.VISIT_FARM);
		String detail = message.getPicture();
		ObjectMapper mapper = new ObjectMapper();  
		ArrayList<String> imageList;
	    try {
	    	imageList = mapper.readValue(detail, ArrayList.class);
		} catch (Exception e) {
			e.printStackTrace();
			imageList = new ArrayList<String>();
			System.out.println("json解析异常");
		}
			
		mv.addObject( "imageList" , imageList);
		//System.out.println( detail );
		return mv;
	}
	
	/**
	 * 获取农场信息
	 * 1 我们农场 2 生态技术  3 休闲活动
	 * @throws Exception 
	 */
	@RequestMapping("/getFarmMessageDetail")
	@ResponseBody
	public NetDataAccessUtil getFarmMessageDetail(HttpServletRequest request) throws Exception{
		NetDataAccessUtil nau = new NetDataAccessUtil();
		List<FarmMessageAndHtml> list = new ArrayList<FarmMessageAndHtml>();
		
			FarmMessageAndHtml fh = new FarmMessageAndHtml();

		return nau;
	}
	
	/**
	 * 获取农场照片信息
	 * 
	 */
	@RequestMapping("/getFarmPhoto")
	@ResponseBody
	public NetDataAccessUtil getFarmMessage(HttpServletRequest request,int pageSize,int pageNum){
//		String types;
//		int currentPage;
		//分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize, "");
		NetDataAccessUtil nau = new NetDataAccessUtil();
		List<Album> list = albumService.getAlbumList();

		int allPages = page.getPages();
		
		
		for(Album fp : list){
			fp.setPictures( getImageFileUtil.getSrcFileImg( fp.getPictures() ) );
		}
		int currentPage = page.getPageNum();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("currentPage", currentPage);
		map.put("allPages", allPages);
		map.put("albumList", list);
		nau.setContent(map);
//		map.addObject("allPages", allPages);
//		map.put("allPages", map.setId());
		nau.setResult(1);
		nau.setResultDesp("查询成功");
		return nau;
	}
	
}
