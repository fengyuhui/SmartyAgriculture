package cn.bupt.smartyagl.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.dao.autogenerate.GoodsRejectedMapper;
import cn.bupt.smartyagl.entity.autogenerate.GoodsRejected;
import cn.bupt.smartyagl.service.IGoodsRejectedService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.IPushService;

/**
 * 
 *<p>Title:GoodsRejectedController</p>
 *<p>Description:退货管理</p>
 * @author waiting
 *@date 2016年9月8日 上午10:18:31
 */
@Controller
@RequestMapping("/goodsRejected")
public class GoodsRejectedController extends BaseController {
	 
	 @Autowired
	 IGoodsRejectedService goodsRejectedService;
	//推送服务
	@Autowired
	IPushService pushService;
	//修改订单状态
	@Autowired
	IOrderService orderService;
	@Autowired
	GoodsRejectedMapper goodsRejectedMapper;
	
	int pageSize=Constants.PAGESIZE;//每一页的大小
    /**
     * 退货列表
     * @param allPages
     * @param currentPage
     * @param type
     * @return
     */
	@RequestMapping(value="/rejectedIndex/{allPages}/{currentPage}/{type}")
	public ModelAndView rejectedIndex(
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type){
		ModelAndView modelAndView=new ModelAndView(Constants.REJECTED_INDEX_STRING);
		if ("prvious".equals(type)) {
			if( currentPage > 1 ){//第一页不能往前翻页
				currentPage--;
			}
		} else if ("next".equals(type)) {
			currentPage++;
		} else if ("first".equals(type)) {
			currentPage = 1;
		} else if ("last".equals(type)) {
			currentPage = allPages;
		} else {
			currentPage = Integer.parseInt(type);
		}
		goodsRejectedService.goodsRejectedIndex(modelAndView, currentPage, allPages);
		return modelAndView;
	}
	/**
	 * 退货处理
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dealRejected/{id}/{status}")
	public Map<String, String> dealRejected(@PathVariable(value="id")int id,
			@PathVariable(value="status")int status){
		Map<String, String> returnMap=new HashMap<String, String>();
		//修改商品订单状态
		GoodsRejected goodsRejected=goodsRejectedMapper.selectByPrimaryKey(id);
		orderService.updateOrderStatus(goodsRejected.getOrderId(), status, "");
		if(status==6){
			//退货成功
			pushService.returnGoodsSucess(goodsRejected.getOrderId());
		}
		else{
			pushService.returnGoodsFail(goodsRejected.getOrderId());
		}
		boolean flag=goodsRejectedService.deleteRejected(id);
		if(flag){
			returnMap.put("msg", "退货成功");
		}
		else{
			returnMap.put("msg", "退货失败");
		}
		return returnMap;
	}
}
