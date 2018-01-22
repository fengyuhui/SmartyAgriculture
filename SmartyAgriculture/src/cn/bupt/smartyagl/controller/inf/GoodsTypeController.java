package cn.bupt.smartyagl.controller.inf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.entity.GoodsTypeList;
import cn.bupt.smartyagl.service.IGoodsTypeService;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

/** 
 * 商品分类相关接口
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-1 下午4:33:53 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/goodsType")
public class GoodsTypeController {
	@Autowired
	IGoodsTypeService goodsTypeService;
	
	/**
	 * 获取商品分类列表
	 * @return
	 */
	@RequestMapping("/getGoodsTypeList")
	@ResponseBody
	public Object getGoodsTypeList(HttpServletRequest request) {
		List<GoodsTypeList> gl = goodsTypeService.getGoodsTypeList(request);
		for(GoodsTypeList aa:gl) {
			aa.setPicture("http://www.zhongyuanfarm.cn"  +":"+request.getLocalPort()+aa.getPicture());
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setResult(1);
		nau.setResultDesp("获得商品类型列表成功");
		nau.setContent(gl);
		return nau;
	}
	
	/**
	 * 获取一级商品分类列表
	 * @return
	 */
	@RequestMapping("/getParentGoodsTypeList")
	@ResponseBody
	public Object getParentGoodsTypeList(HttpServletRequest request) {
		List<GoodsTypeList> gl = goodsTypeService.getPartentTypeList();
		for(GoodsTypeList aa:gl) {
			aa.setPicture("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+aa.getPicture());
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setResult(1);
		nau.setResultDesp("获得商品类型列表成功");
		nau.setContent(gl);
		return nau;
	}
	
	/**
	 * 获取二级商品分类列表
	 * @return
	 */
	@RequestMapping("/getChildGoodsTypeList")
	@ResponseBody
	public Object getChildGoodsTypeList(Integer parentId,Integer pageSize,Integer pageNum,HttpServletRequest request) {
		Page page = PageHelper.startPage(pageNum, pageSize, "id DESC");
		List<GoodsTypeList> gl = goodsTypeService.getChildTypeList(parentId);
		for(GoodsTypeList aa:gl) {
			aa.setPicture("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+aa.getPicture());
			System.out.println(aa);
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		int allPages = page.getPages();
		int currentPage = page.getPageNum();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("currentPage", currentPage);
		map.put("allPages", allPages);
		map.put("childTypeList", gl);
		nau.setContent(map);
		nau.setResult(1);
		nau.setResultDesp("获得商品类型列表成功");
//		nau.setContent(gl);
		return nau;
	}
}
