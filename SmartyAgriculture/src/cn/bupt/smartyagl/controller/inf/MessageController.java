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

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.entity.autogenerate.Message;
import cn.bupt.smartyagl.service.IGoodsCartService;
import cn.bupt.smartyagl.service.IPushService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

/** 
 * 消息管理接口
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-24 下午2:09:40 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/message")
public class MessageController extends AppBaseController{
	@Autowired
	IPushService pushService;
	@Autowired
	IGoodsCartService goodsCartService;
	/**
	 * 获得消息列表
	 * @param request
	 * @param GoodsCart
	 * @return
	 */
	@RequestMapping("/getMessageList")
	@ResponseBody
	public Object getMessageList(HttpServletRequest request,int pageSize,int pageNum,Message message) {
		//分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize,"createTime DESC");
		Integer userId = (Integer) request.getAttribute("userId");
		message.setId(userId);
		List<Message> list= pushService.getMessageList(message);
		for(Message m : list){
			if( !m.getStatus().equals(ConstantsSql.MESSAGE_HAS_READ) ){
				m.setStatus( ConstantsSql.MESSAGE_HAS_READ );
				pushService.update(m);
			}
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(list != null){
			nau.setContent(list);
			nau.setResult(1);
			nau.setResultDesp("获取消息列表成功");
		}else{
			nau.setResult(0);
			nau.setResultDesp("获取消息列表失败");
		}
		return nau;
	}
	
	/**
	 * 获得未读消息个数
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNoReadMessageCount")
	@ResponseBody
	public Object getNoReadMessageCount(HttpServletRequest request) {
		Integer userId =  (Integer) request.getAttribute("userId");
		Map<String,Object> map = new HashMap<String,Object>();
		Message message = new Message();
		message.setId(userId);
		Integer messageCount =  pushService.getNoReadMessageCount(message);
		Integer goodsCartCount = goodsCartService.getGoodsCartAll(userId, request);
		map.put("messageCount", messageCount);
		map.put("goodsCartCount", goodsCartCount);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setContent(map);
		nau.setResult(1);
		nau.setResultDesp("获取消息列表成功");
		return nau;
	}
}
