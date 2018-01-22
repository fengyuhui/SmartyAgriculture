package cn.bupt.smartyagl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;

import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.HotSearch;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.model.MenuModel;



/** 
 * @author  zxy 
 * @param <LikeGoodsList>
 */
public interface IMayBeLikeService<LikeGoodsList> {
	public List<OrderView> getOrderList(Integer userId);//根据用户id获取订单列表
	
	public List<GoodsList> getGoodsList(int status, int saleStatus, String productName);

	public Map<String, Object> getOrderGoodstype(Integer orderId, String type);

	public List<GoodsList> getGoodsList(Integer userId); 

	public void printList(List<LikeGoodsList> GoodsList);

	public Map<String, Object> getOrderGoodstype(Integer orderId, String type,
			Integer userId);

	public Map<String, Object> getOrderGoodstype(Integer orderId);

	public List<GoodsList> getHotSaleGoodsList();



	
}
