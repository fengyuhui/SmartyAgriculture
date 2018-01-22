package cn.bupt.smartyagl.controller.inf;

import java.io.File;
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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.QRCodeRecode;
import cn.bupt.smartyagl.model.QRCodeGoodModel;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IQRCodeService;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.QRCodeUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;

@Controller
@RequestMapping("/interface/QRCode")
public class QRCodeInterfaceController<E> {
	@Autowired
	IGoodsService goodsService;
	@Autowired
	IQRCodeService qrCodeservice;
	@RequestMapping("/getQRCode")
	@ResponseBody
	public Object sendQRCode(HttpServletRequest request,int pageSize,int pageNum) {
		
		if(request.getAttribute("userId") == null) {
			NetDataAccessUtil nau = new NetDataAccessUtil();
			nau.setResult(0);
			nau.setResultDesp("获取二维码商品失败，请先登录");
	 		return nau;
		}
		Integer userId = (Integer) request.getAttribute("userId");	
		Page page = PageHelper.startPage(pageNum, pageSize, "buyNum DESC");
		//搜索
		List<GoodsList> goodsList = qrCodeservice.getQRcodeGoodsList();
		 //取出第一张图片
		List<QRCodeGoodModel> qrcodeGoods = new ArrayList<QRCodeGoodModel>();
        for(GoodsList gd : goodsList){
        	if(gd.getHasVipPrice()) {
	        	QRCodeGoodModel qrgd = new QRCodeGoodModel();
	        	qrgd.setEndtime(gd.getEndTime());
	        	qrgd.setName(gd.getName());
	        	qrgd.setPicture(JsonConvert.getOnePicture(gd.getPicture(),request));
	        	//gd.setPicture( JsonConvert.getOnePicture(gd.getPicture(),request) );
	        	qrgd.setVipPrice(gd.getVipPrice());
	        	qrgd.setTitle(gd.getTitle());
	        	qrgd.setPrice(gd.getPrice());
	        	qrgd.setGoodsDetailId(gd.getId());
//	        	String token = qrCodeservice.addQRCodeRecord(gd.getId(),userId);
	        	qrgd.setQrcodeUrl(generateQRCode(request, gd));
	        	qrgd.setUrl("http://www.zhongyuanfarm.cn/SmartAgricultureWechat/detail.html?" + "userId="+userId+"&goodsId="+gd.getId());
	        	qrcodeGoods.add(qrgd);
        	}
        }
		// 总页数
		int allPages = page.getPages();
		int currentPage = page.getPageNum();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("currentPage", currentPage);
		map.put("allPages", allPages);
		map.put("goodsList", qrcodeGoods);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		/*
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		String url = "" + "&userid="+userId;
		String fileName = userId + "_" + good.getId()+".jpg";
		String base = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/SmartyAgriculture";
		String qrcodePath = request.getSession().getServletContext().getRealPath("") + Constants.QRCODE_PATH ;
		File f = new File(qrcodePath);
		if(!f.exists())
			f.mkdirs();
		QRCodeUtil.encode(url, qrcodePath+"\\"+fileName);
		Map<String,Object> content = new HashMap<String, Object>();
		content.put("qrcodeurl", base + Constants.QRCODE_PATH+'/'+fileName);
		content.put("goods", good);*/
		nau.setContent(map);
		nau.setResult(1);
		nau.setResultDesp("获取二维码商品成功");
 		return nau;
	}
	/**
	 * 返回访问生成的二维码的url
	 * @param request
	 * @param goodId
	 * @return
	 */
	private String generateQRCode(HttpServletRequest request,GoodsList good) {
		Integer userId = (Integer) request.getAttribute("userId");
		String url = "http://www.zhongyuanfarm.cn/SmartAgricultureWechat/detail.html?" + "userId="+userId+"&goodsId="+good.getId();
		String fileName = userId + "_" + good.getId()+".jpg";
		String base = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort() + "/"/*+"/SmartyAgriculture"*/;
		//System.out.println(request.getSession().getServletContext().getRealPath("/"));
		String projectPath = request.getSession().getServletContext().getRealPath("");
		//System.out.println("pro:"+projectPath);
		String qrcodePath = projectPath.substring(0, projectPath.lastIndexOf(File.separator, projectPath.length()-2)) + File.separator + Constants.QRCODE_PATH;
		//System.out.println("qrc:"+qrcodePath);
		
		File f = new File(qrcodePath);
		if(!f.exists())
			f.mkdirs();
		QRCodeUtil.encode(url, qrcodePath+File.separator+fileName);
		return base + Constants.QRCODE_PATH+'/'+fileName;
	}
	@RequestMapping("/validateQRCode")
	@ResponseBody
	public Object validateQRCode(String qrCodeToken)  {
		NetDataAccessUtil nau = new NetDataAccessUtil();
		QRCodeRecode qrr = qrCodeservice.validateToken(qrCodeToken);
		if(qrr != null) {
			nau.setContent(qrr);
			nau.setResult(1);
			nau.setResultDesp("验证成功");
			return nau;
		}
		else {
			nau.setContent(qrr);
			nau.setResult(0);
			nau.setResultDesp("验证失败，并没有对应的二维码信息");
			return nau;	
		}
	}
}
