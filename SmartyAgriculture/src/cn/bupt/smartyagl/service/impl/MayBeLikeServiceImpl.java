package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.GoodsListMapper;
import cn.bupt.smartyagl.dao.autogenerate.HotSearchMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.dao.autogenerate.MenuMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderListMapper;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.GoodsListExample;
import cn.bupt.smartyagl.entity.autogenerate.HotSearch;
import cn.bupt.smartyagl.entity.autogenerate.HotSearchExample;
import cn.bupt.smartyagl.entity.autogenerate.LikeGoodsView;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.entity.autogenerate.OrderViewExample;
import cn.bupt.smartyagl.model.MenuModel;
import cn.bupt.smartyagl.service.IMayBeLikeService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.RedisUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;

@Service
public class MayBeLikeServiceImpl implements IMayBeLikeService{

//	private static final String TypeParent = null;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsListMapper goodsListMapper;
	//订单表
	@Autowired
	OrderListMapper orderMapper;
	//订单表视图
	@Autowired
	OrderViewMapper orderViewMapper;
	@Autowired
	HotSearchMapper hotSearchMapper;
	

			@Override
			public List<GoodsList> getHotSaleGoodsList() {
				try{
					HotSearchExample ae = new HotSearchExample();
//					Criteria cta = ae.createCriteria();
					String nau = new String();
					Map<String, Integer> map = new HashMap<String, Integer>();
					Page page = PageHelper.startPage(1, 15, "buyNum DESC");
					GoodsListExample ge = new GoodsListExample();
					cn.bupt.smartyagl.entity.autogenerate.GoodsListExample.Criteria ctr = ge.createCriteria();
					//get  map
					Set<String> set =  map.keySet();
			        List<String> mapValuesList = new ArrayList<String>(map.keySet());  
			        if(mapValuesList.size() > 0 ){
			        	ctr.andTypeParentIn(mapValuesList);
			        }
			    	/**
					 * 搜索的只能是发布成功商品
					 */
					List<Integer> authList = new ArrayList<Integer>();
					authList.add( ConstantsSql.Audit_Finish );
					authList.add( ConstantsSql.Audit_Finish_hasDraft );
					ctr.andAuditStatusIn(authList);
					List<GoodsList> hotLists = goodsListMapper.selectByExample(ge);
				
					return hotLists;
				}catch (Exception e) {
					// TODO: handle exception
					return null;
				}					
		}
	/*@Override
	public List<OrderView> getOrderList( Integer userId) {
		
		// 类型，ID 模型  list 返回list
		
		cta.andUserIdEqualTo(userId);
		List<OrderView> list1 = orderViewMapper.selectByExample(ae);
		return list1;
	}*/		
	@Override
	public Map<String, Integer> getOrderGoodstype(Integer orderId, String type,Integer userId) {	
		OrderViewExample ae = new OrderViewExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderViewExample.Criteria cta = ae.createCriteria();
		cta.andUserIdEqualTo(userId);
		List<OrderView> list = orderViewMapper.selectByExample(ae);
		Map<String, Integer> map = new HashMap<String, Integer>();
		//得到订单商品中的类别ID和类别，计算类别下商品数量
		for(int i=0;i<list.size();i++){ 
			OrderView orderView = list.get(i);
//			int typeIdParent = orderView.getTypeIdParent();
			orderView.getTypeParent();
			String typeParent = orderView.getTypeParent();
			
			if(map.get(typeParent)==null){
				map.put(typeParent, 1);
			
			}else{
				int num= map.get(typeParent);
				num ++;
				map.put(typeParent, num);
			}
		}		
		return map;
	
	}
	//根据typeparent 推荐商品列表中的商品
	@Override
	public List<GoodsList> getGoodsList(Integer userId) {			
		OrderViewExample ae = new OrderViewExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderViewExample.Criteria cta = ae.createCriteria();
		
		cta.andUserIdEqualTo(userId);
		List<OrderView> list = orderViewMapper.selectByExample(ae);
		Map<String, Integer> map = new HashMap<String, Integer>();
		//得到订单商品中的类别ID和类别，计算类别下商品数量
		for(int i=0;i<list.size();i++){ 
			OrderView orderView = list.get(i);
//				int typeIdParent = orderView.getTypeIdParent();
			orderView.getTypeParent();
			String typeParent = orderView.getTypeParent();
			//如果还没有这个类别，这录入这个类别，设置这个类别的商品为1
			if(map.get(typeParent)==null){
				map.put(typeParent, 1);
			}else{//已有这个类别，就num++
				int num= map.get(typeParent);
				num ++;
				map.put(typeParent, num);
			}
		}				
		//分页，默认按购买数量排序
		Page page = PageHelper.startPage(1, 15, "buyNum DESC");
		// TODO Auto-generated method stub
		GoodsListExample ge = new GoodsListExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsListExample.Criteria ctr = ge.createCriteria();
		/**
		 * 搜索的只能是发布成功商品
		 */
		List<Integer> authList = new ArrayList<Integer>();
		authList.add( ConstantsSql.Audit_Finish );
		authList.add( ConstantsSql.Audit_Finish_hasDraft );
		ctr.andAuditStatusIn(authList);
		
		//get  map
		//通过类型获取id,再在ctr中搜索
		Set<String> set =  map.keySet();
        List<String> mapValuesList = new ArrayList<String>(map.keySet());  
        if(mapValuesList.size() > 0 ){
        	ctr.andTypeParentIn(mapValuesList);
        }
		List<GoodsList> goodsLists = goodsListMapper.selectByExample(ge);
		return goodsLists;		
	}

	@Override
	public List<OrderView> getOrderList(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getOrderGoodstype(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	public GoodsMapper getGoodsMapper() {
		return goodsMapper;
	}

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}


	@Override
	public List getGoodsList(int status, int saleStatus, String productName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map getOrderGoodstype(Integer orderId, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void printList(List GoodsList) {
		// TODO Auto-generated method stub
		
	}


	/*@Override
	public void printList(List GoodsList) {
		// TODO Auto-generated method stub
		
	}*/
}

