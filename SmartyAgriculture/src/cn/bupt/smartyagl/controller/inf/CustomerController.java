package cn.bupt.smartyagl.controller.inf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bupt.smartyagl.controller.BaseController;
import cn.bupt.smartyagl.entity.autogenerate.Advertise;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.propertiesUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-7-12 下午2:26:33 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/customerService")
public class CustomerController  extends BaseController{
		
		@RequestMapping("/getMessage")
		@ResponseBody
		public Object getAdList(HttpServletRequest request){
			Map<String,Object> map = new HashMap<String,Object>();
			propertiesUtil.changePath("customService.properties");
			map.put("customerAccount", propertiesUtil.getValue("CUSTOMERSERVICE"));
			map.put("groupId", Integer.parseInt( propertiesUtil.getValue("GROUPID") ));
			NetDataAccessUtil nau = new NetDataAccessUtil();
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("获取客服信息成功");
			return nau;
		}
}
