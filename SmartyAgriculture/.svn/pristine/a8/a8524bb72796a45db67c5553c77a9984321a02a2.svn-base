package cn.bupt.smartyagl.controller.inf;

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

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.CookLeisureAndHtml;
import cn.bupt.smartyagl.entity.autogenerate.CookLeisure;
import cn.bupt.smartyagl.service.ICookLeisureService;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

@Controller
@RequestMapping("/interface/cookleisure")
public class CookLeisureInterfaceController {
	@Autowired
	ICookLeisureService cookLeisureService;
	@RequestMapping("/getCookLeisureMessage")
	@ResponseBody
	public NetDataAccessUtil getCookLeisureMessage(HttpServletRequest request,int typeId,int pageSize,int pageNum) throws Exception{
		Map<String,Object> m = new HashMap();
		NetDataAccessUtil nau = new NetDataAccessUtil();
		List<CookLeisure> clList = cookLeisureService.getCookLeisureList(typeId, pageSize, pageNum,m);
		List<CookLeisureAndHtml> claList = new ArrayList<CookLeisureAndHtml>();
		for( CookLeisure cl : clList ){
			CookLeisureAndHtml cla = new CookLeisureAndHtml();//"http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/SmartyAgriculture/farmMessage/detailView?messageId="+ fm.getId() 
//			BeanUtil.fatherToChild(cl, cla);//interface/cookleisure/detailView?messageId=
			CookLeisure objCookLeisure = cookLeisureService.getCookLeisureDetail(cl.getId());
			String message = objCookLeisure.getCurrentMessage();
			objCookLeisure.setCurrentMessage("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/SmartyAgriculture/interface/cookLeisure/detailView?messageId="+ cl.getId());
			
			cla = cookLeisureService.convertMessage(objCookLeisure);
			cla.setMessageDetailHTML(message.replace("../upload", "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"));
			cla.setIcon("http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+cl.getIcon());
			cla.setDetailUrl( "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/SmartyAgriculture/cookLeisure/find?id="+ cl.getId());
			claList.add(cla); 
			
		}
		if( clList.size() > 0 ){
			m.put("cookLeisures",claList);
//			nau.setContent(claList);
			nau.setContent(m);
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
		CookLeisure message = cookLeisureService.findCookLeisure(messageId);
		ModelAndView mv = new ModelAndView(Constants.CookLeisure_Message);
		String detail = message.getCurrentMessage();
		mv.addObject( "detail" , detail);
		return mv;
	}
}
