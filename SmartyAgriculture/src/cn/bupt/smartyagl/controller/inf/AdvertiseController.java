package cn.bupt.smartyagl.controller.inf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bupt.smartyagl.controller.BaseController;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.entity.autogenerate.Advertise;
import cn.bupt.smartyagl.service.IAddressService;
import cn.bupt.smartyagl.service.IAdevertiseService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/** 
 * 广告相关接口
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 下午2:26:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/advertise")
public class AdvertiseController extends BaseController{
	@Autowired
	IAdevertiseService adevertiseService;
	
	//获取广告列表
	@RequestMapping("/getAdList")
	@ResponseBody
	public Object getAdList(HttpServletRequest request){
		List<Advertise> advertisesList =  adevertiseService.getAdvertiseList();
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(advertisesList!=null && advertisesList.size() > 0){
			nau.setContent(advertisesList);
			nau.setResult(1);
			nau.setResultDesp("获取广告列表成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("获取广告列表失败");
		}
		return nau;
	}
	
}
