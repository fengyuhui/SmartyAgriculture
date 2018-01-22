package cn.bupt.smartyagl.controller.inf;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.AdditionAndHtml;
import cn.bupt.smartyagl.entity.FarmMessageAndHtml;
import cn.bupt.smartyagl.entity.autogenerate.Addition;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
import cn.bupt.smartyagl.service.IAdditionService;
import cn.bupt.smartyagl.util.BeanUtil;
import cn.bupt.smartyagl.util.IPUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

@Controller
@RequestMapping("/interface/addition")
public class AdditionInterfaceController {
	@Autowired
	IAdditionService additionService;
	@RequestMapping("/additionMessage")
	@ResponseBody
	public Object getAdditionMessage(@RequestParam int id,HttpServletRequest request) throws Exception{

		NetDataAccessUtil nau = new NetDataAccessUtil();
		List<Addition> ar = additionService.getAdditionListById(id);
		List<AdditionAndHtml> list = new ArrayList<AdditionAndHtml>();
		
		for( Addition fm : ar ){
			AdditionAndHtml fh = new AdditionAndHtml();
			BeanUtil.fatherToChild(fm, fh);
			Addition objFarmMessage = additionService.getAdditionDetail(fm.getId());
			String message = objFarmMessage.getContent();
		
			fh = additionService.convertMessage(objFarmMessage);
			fh.setAdditionMessageHtml(message.replace("../upload", "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"));
			
//			fh.setDetailUrl( "http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/SmartyAgriculture"+"/interface/addition/additionHtml?id="+id );
			fh.setDetailUrl( "http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/SmartyAgriculture"+"/addition/detail?id="+id );
			
			list.add(fh); 
			
		}
		
		if( ar.size() > 0 ){
			nau.setContent(list);
			nau.setResult(1);
			nau.setResultDesp("查询成功");
		}else{
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("查询失败");
		}
		
		
		
//		nau.setResult(1);
//		nau.setResultDesp("ok");
//		nau.setContent("http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/SmartyAgriculture"+"/interface/addition/additionHtml?id="+id);
		return nau;
	}
	/**
	 * 附属信息的html页面
	 */
	@RequestMapping("/additionHtml")
	public ModelAndView additionHtml(int id) {
		ModelAndView mv = new ModelAndView(Constants.ADD_MESSAGE);
		Addition add = additionService.getAdditionById(id);
		mv.addObject("item", add);
		return mv;
	}
}
