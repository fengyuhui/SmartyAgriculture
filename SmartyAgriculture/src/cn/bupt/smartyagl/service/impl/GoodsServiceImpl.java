package cn.bupt.smartyagl.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pagehelper.Page;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.AddressMapper;
import cn.bupt.smartyagl.dao.autogenerate.CollectMapper;
import cn.bupt.smartyagl.dao.autogenerate.CollectViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.FreMapper;
import cn.bupt.smartyagl.dao.autogenerate.FreightMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsListMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.entity.GoodsListAndCollect;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.entity.autogenerate.Collect;
import cn.bupt.smartyagl.entity.autogenerate.CollectExample;
import cn.bupt.smartyagl.entity.autogenerate.CollectView;
import cn.bupt.smartyagl.entity.autogenerate.CollectViewExample;
import cn.bupt.smartyagl.entity.autogenerate.Fre;
import cn.bupt.smartyagl.entity.autogenerate.FreExample;
import cn.bupt.smartyagl.entity.autogenerate.Freight;
import cn.bupt.smartyagl.entity.autogenerate.FreightExample;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.GoodsListExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsListExample.Criteria;
import cn.bupt.smartyagl.entity.autogenerate.OrderList;
import cn.bupt.smartyagl.entity.autogenerate.Project;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IPushService;
import cn.bupt.smartyagl.util.ImageUtil;
import cn.bupt.smartyagl.util.pinyin4jUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;

/** 
 * 商品实现类
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 下午2:23:57 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
@RunWith(SpringJUnit4ClassRunner.class) 
//配置文件路径 ,可用通配符，注意两个变量之间用逗号隔开
@ContextConfiguration(locations = {"classpath*:/spring-*.xml","classpath*:/spring.xml"})
public class GoodsServiceImpl implements IGoodsService{
	@Autowired
	FreMapper freMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private CollectMapper collectMapper;
	@Autowired
	private CollectViewMapper collectDetailViewMapper;
	@Autowired
	private GoodsListMapper goodsListMapper;
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private FreightMapper freightMapper;
	@Autowired
	IPushService pushService;
	
	@Override
	public List<GoodsList> getGoodsList(GoodsList goods, Page page) {
		GoodsListExample ge = new GoodsListExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsListExample.Criteria ctr = ge.createCriteria();
		//条件过滤
		if( goods.getBlockId() !=null && goods.getBlockId() > 0){
			ctr.andBlockIdEqualTo( goods.getBlockId() );
		}
//		if(goods.getName() != null){
//			ctr.andNameLike("%"+goods.getName()+"%");
//		}
		if(goods.getName() != null){
			ctr.andPinyinLike("%"+pinyin4jUtil.getPinyinString(goods.getName())+"%");
		}
		if(goods.getTypeIdChild() != null){
			ctr.andTypeIdChildEqualTo( goods.getTypeIdChild() );
		}
		if(goods.getTypeIdParent() != null){
			ctr.andTypeIdParentEqualTo( goods.getTypeIdParent() );
		}
		if(goods.getSaleStatus() != null){
			ctr.andSaleStatusEqualTo( goods.getSaleStatus() );
		}
		
		//默认搜索上架商品
		ctr.andStatusEqualTo( ConstantsSql.ONSALE );
		/**
		 * 搜索的只能是发布成功商品
		 */
		List<Integer> authList = new ArrayList<Integer>();
		authList.add( ConstantsSql.Audit_Finish );
		authList.add( ConstantsSql.Audit_Finish_hasDraft );
//		authList.add( ConstantsSql.Audit_WaitDelete );
		ctr.andAuditStatusIn(authList);
		//查询
		List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
		return goodsList;
	}
	
    @Override
    public List<GoodsList> getGoodsList(int status,int saleStatus,String productName) {
        GoodsListExample ge = new GoodsListExample();
        Criteria criteria=ge.createCriteria();
        if(status!=3){
        	criteria.andStatusEqualTo(status);	
        }
        if(saleStatus!=-1){
        	criteria.andSaleStatusEqualTo(saleStatus);
        }
        if(productName!=""&&productName!=null&&(!productName.equals("1"))){
        	criteria.andNameLike("%"+productName+"%");
        }
        List<Integer> auditList = new ArrayList<Integer>();
		auditList.add(ConstantsSql.Audit_Finish);
		auditList.add(ConstantsSql.Audit_Finish_hasDraft);
		auditList.add(ConstantsSql.Audit_WaitDelete );
		criteria.andAuditStatusIn(auditList);
		ge.setOrderByClause("id desc");
        List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
        return goodsList;
    }

/*	@Override
	public boolean updateGoods(goods goods) {
		try{
			Date createTimeDate=new Date();//设置商品创建时间
			goods.setCreateTime(createTimeDate);
			int rs = goodsMapper.insert(goods);//goodsMapper.updateByPrimaryKeySelective(goods);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}*/
	
	@Override
	public boolean updateGoods(Goods goods) {
		try{
			Date createTimeDate=new Date();//设置商品创建时间
			goods.setCreateTime(createTimeDate);
			int rs = goodsMapper.insert(goods);//goodsMapper.updateByPrimaryKeySelective(goods);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
//	@Override
//	public boolean updateGoods(goods goods) {
//		try{
//			Integer id = goods.getId();
//			//int rs = advertiseMapper.updateByPrimaryKeySelective(advertise);
//			//添加草稿文章数据
//			goods.setCreateTime(new Date());
//			goods.setAuditStatus(ConstantsSql.Audit_Draft);
//			//标记父文章标题
//			goods.setSourceId(id);
//			goods sourceGoods = goodsMapper.selectByPrimaryKey(goods.getSourceId());
////			goods.setId(null);
//			goods.setSaleStatus(sourceGoods.getSaleStatus());
//			goods.setStatus(sourceGoods.getStatus());
//			goods.setId(null);
//			int rs = goodsMapper.insert(goods);
//			
//			//更改原文章有草稿状态
//			this.verifyGoodsStatus(goods, ConstantsSql.Audit_Finish_hasDraft);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
	
	@Override
	public boolean verifyGoodsStatus(Goods goods, Integer status) {
//		goods goods = new goods();
		goods.setId(goods.getId());
		goods.setAuditStatus(status);
		int i = goodsMapper.updateByPrimaryKeySelective(goods);
		if(i>0)
			return true;
		return false;
	}
	
	@Override
	public boolean verifyAddGoods(Goods goods) {
		return this.verifyGoodsStatus(goods, ConstantsSql.Audit_Finish);
	}
	
/*	@Override
	public boolean verifyGoodsStatus(goods goods, Integer AuditStatus) {
		goods gds = new goods();
		gds.setId(gds.getId());
		gds.setAuditStatus(AuditStatus);
		int i = goodsMapper.updateByPrimaryKeySelective(gds);
		if(i>0)
			return true;
		return false;
	}*/
	
	@Override
	public boolean addCollectGoods(Collect collect) {
		CollectExample collectExample = new CollectExample();
		cn.bupt.smartyagl.entity.autogenerate.CollectExample.Criteria criteria = collectExample.createCriteria();
		criteria.andGoodsIdEqualTo( collect.getGoodsId() );
		criteria.andUserIdEqualTo( collect.getUserId() );
		List<Collect> collectExist = collectMapper.selectByExample(collectExample);
		if(collectExist.size()>0){
			return true;
		}
	    int rs = collectMapper.insert( collect );
	    if(rs>0){
	    	return true;
	    }
		return false;
	}

	@Override
	public List<CollectView> getCollectGoodsList(CollectView collectView) {
		CollectViewExample ge = new CollectViewExample();
		cn.bupt.smartyagl.entity.autogenerate.CollectViewExample.Criteria ctr = ge.createCriteria();
		if( collectView.getUserId() != null){
			ctr.andUserIdEqualTo( collectView.getUserId() );
		}
		List<CollectView> collectList = collectDetailViewMapper.selectByExample(ge);
		return collectList;
	}

	@Override
	public boolean deleteCollectGoods(Integer goodsId) {
		CollectExample ce = new CollectExample();
		cn.bupt.smartyagl.entity.autogenerate.CollectExample.Criteria criteria = ce.createCriteria();
		criteria.andGoodsIdEqualTo(goodsId);
		int rs = collectMapper.deleteByExample( ce );
		
		if(rs>0){
	    	return true;
	    }
		return false;
	}
    
	/**
     * 添加商品
     */
	@Override
	public int  addGoods(Goods goods) {
		// TODO Auto-generated method stub
		
		
		Date createTimeDate=new Date();//设置商品创建时间
		goods.setCreateTime(createTimeDate);
		goods.setSaleStatus(ConstantsSql.NOMALPRODUCT);//设置商品销售状态 新品
		goods.setStatus(ConstantsSql.ONSALE);//设置商品状态
		goods.setBuyNum(0);//设置商品购买的数量
		goods.setPinyin( pinyin4jUtil.getPinyinString(goods.getName()) );
		goods.setAuditStatus(ConstantsSql.Audit_Publish_NoAuth);
		goods.setSourceId(0);
		int i=goodsMapper.insert(goods);
		//推送新品
		List<Integer> pushList=new ArrayList<Integer>();
		pushList.add(i);
		pushService.newSale(pushList, "");
		return i;
	}

	@Override
	public boolean deleteCollectGoodsBatch(String goodsIds) {
		CollectExample ge = new CollectExample();
		cn.bupt.smartyagl.entity.autogenerate.CollectExample.Criteria ctr = ge.createCriteria();
		String[] goodsList = goodsIds.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for(String goodsId : goodsList){
			if( !collectMapper.equals("") ){
				list.add( Integer.parseInt( goodsId  ) );
			}
		}
		ctr.andGoodsIdIn(list);
		int rs = collectMapper.deleteByExample(ge);
		if( rs>0 ){
			return true;
		}else{
			return false;
		}
	}
    /**
     * 修改商品状态
     * @author waiting
     */
	@Override
	public boolean updateGoodsStatus(int status, int id) {
		// TODO Auto-generated method stub
		try{
			Goods goods=goodsMapper.selectByPrimaryKey(id);
		    goods.setStatus(status);
		    int i=goodsMapper.updateByPrimaryKeySelective(goods);
		    if(i==1){
		    	return true;
		    }
		    else{
		    	return false;
		    }
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 * 修改商品销售状态
	 * @author waiting
	 */
	@Override
	public boolean updateGoodsSaleStatus(int id,int saleStatus,Date endTime,Date openTime,Double specialprice) {
		// TODO Auto-generated method stub
		try{
			Goods goods=goodsMapper.selectByPrimaryKey(id);
		    goods.setSaleStatus(saleStatus);
    		System.out.println(endTime+"_"+openTime);
		    if(saleStatus==ConstantsSql.LIMITATIONTIME){
		    	goods.setEndTime(endTime);
		    	goods.setStartTime(openTime);
		    }
		    else{
		    	endTime=null;
		    	goods.setEndTime(endTime);
		    	goods.setStartTime(endTime);	
		    }
		    if(saleStatus==ConstantsSql.SPECIALPRICE||saleStatus==ConstantsSql.LIMITATIONTIME){

	    		goods.setSpecialPrice(specialprice);
	    	}
		    int i=goodsMapper.updateByPrimaryKey(goods);
		    if(i==1){
		    	return true;
		    }
		    else{
		    	return false;
		    }
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean isCollect(Integer goodId, Integer userId) {
		if(goodId == null || userId == null){
			return false;
		}
		CollectExample ge = new CollectExample();
		cn.bupt.smartyagl.entity.autogenerate.CollectExample.Criteria ctr = ge.createCriteria();
		ctr.andGoodsIdEqualTo(goodId);
		ctr.andUserIdEqualTo( userId );
		List rs = collectMapper.selectByExample(ge);
		if(rs==null || rs.size()<=0){
			return false;
		}
		return true;
	}

	@Override
	public GoodsListAndCollect convertGoods(GoodsList goods) throws IllegalArgumentException, IllegalAccessException {
		GoodsListAndCollect gc = new GoodsListAndCollect();
		Field[] goods_fields = GoodsList.class.getDeclaredFields();
		Field[] gc_fields = GoodsListAndCollect.class.getDeclaredFields();
		
		for(Field field : goods_fields){
			field.setAccessible(true);//可强行访问
//			for(Field gcField : gc_fields){
//				gcField.setAccessible(true);
//				System.out.println(field.getName() +" "+gcField.getName());
//				if( field.getName().equals(gcField.getName()) ){
//					gcField.set(gc, field.get(goods));
//				}
//			}
			field.set(gc, field.get(goods));
		}
		return gc;
	}

	@Override
	public boolean updateGoodsFavoriteRate(Integer goodsId, double rate) {
		try{
			Goods goods=goodsMapper.selectByPrimaryKey(goodsId);
		    goods.setFavorableRate(rate);
		    int i=goodsMapper.updateByPrimaryKeySelective(goods);
		    if(1==i){
		    	return true;
		    }else{
				return false;
		    }
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	@Override
	public Double calculateGoodsPrice(Integer goodsId,OrderList or) {
		GoodsListExample ge = new GoodsListExample();
		ge.createCriteria().andIdEqualTo(goodsId);
		List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
		if(or.getSharer_id() == null) {

			if( goodsList.size() == 0){
				return 0.0;
			}
			GoodsList goods = goodsList.get(0);
			if( goods.getSaleStatus() == ConstantsSql.SPECIALPRICE ){
				return goods.getSpecialPrice();
			}
			if( goods.getSaleStatus() == ConstantsSql.LIMITATIONTIME ){
				Date date = new Date();
				if( goods.getStartTime().getTime() < date.getTime() && goods.getEndTime().getTime()>date.getTime() ){
					return goods.getSpecialPrice();
				}
			}
			return goods.getPrice();
		}
		else {
			return goodsList.get(0).getVipPrice();
		}
	}

	private Fre findFreightByType(int type) {
		FreExample fe = new FreExample();
		fe.createCriteria().andAuditStatusEqualTo(Constants.FREIGHT_NORMAL).andTypeEqualTo(type);
		List<Fre> list = freMapper.selectByExample(fe);
		return list.size() == 1?list.get(0):null;
	}
	private double calculateFreightPriceByType(int type,Goods g,OrderList or) {
		Fre f = findFreightByType(type);
		double weight = g.getWeight()*or.getNum();
		if(weight <= f.getFirstweight()) {
			return f.getFirstweightprice();
		}
		else {
			double addWei = weight-f.getFirstweight();
			return f.getFirstweight()+Math.ceil(addWei/f.getAdditionweight())*f.getAdditionweightprice();
		}		
	}
	
	private double calculateFreightByType(int type,Integer goodsId,Integer num) {
		Fre f = findFreightByType(type);
		GoodsExample ae = new GoodsExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsExample.Criteria cta = ae.createCriteria();
		cta.andIdEqualTo(goodsId);
		Goods g = goodsMapper.selectByPrimaryKey(goodsId);
		double weight = g.getWeight()*num;
		if(weight <= f.getFirstweight()) {
			return f.getFirstweightprice();
		}
		else {
			double addWei = weight-f.getFirstweight();
			return f.getFirstweight()+Math.ceil(addWei/f.getAdditionweight())*f.getAdditionweightprice();
		}		
	}
	
	
	
	@Override
	public double calculateFreight(int addressId,String goodsIds,String nums) {
		
		Address address = addressMapper.selectByPrimaryKey(addressId);
		//类型转换
		String[] Idlist = goodsIds.split(",");
		String[] nulist = nums.split(",");
		List<Integer> goodsIdList = new ArrayList<Integer>();
		List<Integer> numList = new ArrayList<Integer>();
		for(String goodsId : Idlist){
			if( !goodsId.equals("") ){
				Integer.parseInt( goodsId  );
				goodsIdList.add( Integer.parseInt( goodsId  ) );
			}
		}
		for(String num : nulist){
			if( !num.equals("") ){
				numList.add( Integer.parseInt( num  ) );
			}
		}
		
		GoodsExample ae = new GoodsExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsExample.Criteria cta = ae.createCriteria();
		cta.andIdIn(goodsIdList);
//		goods g = goodsMapper.selectByPrimaryKey(goodsIds);
		List<Project> priceList = new ArrayList<Project>();
		double price = 0.0;
		if(address.getCity().equals(ConstantsSql.Local_City)) {
			/*
			 * 计算本市运费
			 */
			for(int n=0; n<goodsIdList.size(); n++){
				price = price+(calculateFreightByType(ConstantsSql.local_city,goodsIdList.get(n),numList.get(n)));
			}
		}
		else {
			if(ConstantsSql.Local_Province.equals(address.getProvince())) {
				/*
				 * 计算本省运费
				 */
				for(int n=0; n<goodsIdList.size(); n++){
					price=price+ calculateFreightByType(ConstantsSql.local_pro,goodsIdList.get(n),numList.get(n));
				
				}
//				return calculateFreightByType(ConstantsSql.local_pro,g,num);
			}
			else {
				for(int n=0; n<goodsIdList.size(); n++){
					price+=(calculateFreightByType(ConstantsSql.other_pro,goodsIdList.get(n),numList.get(n)));
					System.out.println(calculateFreightByType(ConstantsSql.other_pro, goodsIdList.get(n), numList.get(n)));
//					
				}
//				return calculateFreightByType(ConstantsSql.other_pro, g, num);
			}
		}
		return price;
	}
//	@Override
//	public double calculateFreight(int addressId,int goodsId,int num) {
//		address address = addressMapper.selectByPrimaryKey(addressId);
//		goods g = goodsMapper.selectByPrimaryKey(goodsId);
//		if(address.getCity().equals(ConstantsSql.Local_City)) {
//			/*
//			 * 计算本市运费
//			 */
//			return calculateFreightByType(ConstantsSql.local_city,g,num);
//		}
//		else {
//			if(ConstantsSql.Local_Province.equals(address.getProvince())) {
//				/*
//				 * 计算本省运费
//				 */
//				return calculateFreightByType(ConstantsSql.local_pro,g,num);
//			}
//			else {
//				System.out.println(calculateFreightByType(ConstantsSql.other_pro, g, num));
//				return calculateFreightByType(ConstantsSql.other_pro, g, num);
//			}
//		}
//	}
	
	@Override
	public Double calculateFreightPrice(OrderList or) {
		Address address = addressMapper.selectByPrimaryKey(or.getAddressId());
		Goods g = goodsMapper.selectByPrimaryKey(or.getGoodsId());
		if(address.getCity().equals(ConstantsSql.Local_City)) {
			/*
			 * 计算本市运费
			 */
			return calculateFreightPriceByType(ConstantsSql.local_city,g,or);
		}
		else {
			if(ConstantsSql.Local_Province.equals(address.getProvince())) {
				/*
				 * 计算本省运费
				 */
				return calculateFreightPriceByType(ConstantsSql.local_pro,g,or);
			}
			else {
				System.out.println(calculateFreightPriceByType(ConstantsSql.other_pro, g, or));
				return calculateFreightPriceByType(ConstantsSql.other_pro, g, or);
			}
		}
		//现在运费表中根据地址找对应运费，找到则返回
/*		FreightExample fe = new FreightExample();
		cn.bupt.smartyagl.entity.autogenerate.FreightExample.Criteria cia  = fe.createCriteria();
		cia.andCityEqualTo( address.getCity() );
		cia.andProvinceEqualTo( address.getProvince() );
		cia.andGoodsIdEqualTo( goodsId );
		List<Freight> freights = freightMapper.selectByExample( fe );

		if( freights.size() > 0 ){
			return freights.get(0).getMoney();
		}
		else {
			fe.clear();
			fe.createCriteria().andGoodsIdEqualTo( goodsId ).andProvinceEqualTo( address.getProvince() ).andCityEqualTo("地级市");
			freights = freightMapper.selectByExample( fe );
			if( freights.size() > 0 ){
				return freights.get(0).getMoney();
			}
		}

		//返回商品默认运费，无则返回0
		GoodsListExample ge = new GoodsListExample();
		ge.createCriteria().andIdEqualTo(goodsId);
		List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
		String [] str = null;
		if( goodsList.size() == 0){
			return 0.0;
		}
		GoodsList goods = goodsList.get(0);
		
		if( goods.getFreigth() != null ){
			return goods.getFreigth();
		}
		return 0.0;*/
	}

	@Override
	public List<GoodsList> getGoodsLists() {
		// TODO Auto-generated method stub
		GoodsListExample ge = new GoodsListExample();
        Criteria criteria=ge.createCriteria();
        List<Integer> auditList = new ArrayList<Integer>();
		auditList.add(ConstantsSql.Audit_Publish_NoAuth);
		auditList.add(ConstantsSql.Audit_Draft);
		auditList.add(ConstantsSql.Audit_WaitDelete);
		criteria.andAuditStatusIn(auditList);
        List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
        return goodsList;
	}

	@Override
	public boolean updateAuditStatus(int id,int auditStatus,HttpServletRequest request) {
		// TODO Auto-generated method stub
		Goods goods=goodsMapper.selectByPrimaryKey(id);
		if (goods.getAuditStatus()==ConstantsSql.Audit_Publish_NoAuth) {
			//发布未审核
			return this.updateAuditAdd(id, auditStatus, request);
		}
//		else if(goods.getAuditStatus()==ConstantsSql.Audit_WaitDelete){
//			//删除未审核
//			return this.updateAuditDelete(id, auditStatus, request);
//		}
		
		else if(goods.getAuditStatus()==ConstantsSql.Audit_Draft){
			//编辑未审核
			System.out.print("9456==="+goods.getAuditStatus());
			return this.updateAuditEdit(id, auditStatus, request);
		}
		else {			
//			删除未审核
			return this.updateAuditDelete(id, auditStatus, request);
		}
	}
	/**
	 * 添加的商品修改商品审核状态
	 * @return
	 */
	private boolean updateAuditAdd(int id,int status,HttpServletRequest request) {
		Goods goods=goodsMapper.selectByPrimaryKey(id);
		int i=0;
		if(status==1){
			//审核通过
			goods.setAuditStatus(ConstantsSql.Audit_Finish);
			Freight f = new Freight();
			f.setAuditStatus(ConstantsSql.Audit_Finish);
			FreightExample fe = new FreightExample();
			fe.createCriteria().andGoodsIdEqualTo(goods.getId());
			freightMapper.updateByExampleSelective(f, fe);
			 i=goodsMapper.updateByPrimaryKey(goods);
		}
		else{
			i=this.deleteGoods(goods, request);
		}
		if(i==1){
			return true;
		}
		else{
			return false;
		}	
	}
	/**
	 * 删除的商品修改商品审核状态
	 * @return
	 */
	private boolean updateAuditDelete(int id,int status,HttpServletRequest request) {
		Goods goods=goodsMapper.selectByPrimaryKey(id);
		int i=0;
		if(status==1){
			//审核通过
			i=this.deleteGoods(goods, request);			 
		}
		else{
			goods.setAuditStatus(ConstantsSql.Audit_Finish);
			i=goodsMapper.updateByPrimaryKey(goods);
		}
		if(i==1){
			return true;
		}
		else{
			return false;
		}	
	}
	
	/**
	 * 编辑商品修改商品审核状态
	 * @param id
	 * @param status
	 * @param request
	 * @return
	 */
	private boolean updateAuditEdit(int id,int status,HttpServletRequest request){
//		System.out.println("mygoodsid="+id);
		Goods goods=goodsMapper.selectByPrimaryKey(id);
		System.out.println("mygoodsi7d="+goods.getPicture());
		
		int i=0;
		if(status==1){
			//审核通过
			Goods goodsSrc=goodsMapper.selectByPrimaryKey(goods.getSourceId());
			System.out.println("mygoodsi7d="+goodsSrc.getPicture());
//			i=this.deleteGoods(goodsSrc, request);
			//删除商品图片
			if (goodsSrc.getPicture()!=null && goods.getPicture() != null) {
				// 删除原来的图片
				// 处理商品图片
				List<String> pictureList = new ArrayList<String>();
				try {
					pictureList = JsonConvert.getProductPictureAbsolute(goodsSrc.getPicture(),
							request);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String picture : pictureList) {
					String[] picture_arrString=picture.split("src");
					ImageUtil.deleteFileByPath(picture_arrString[0]);
				}
			}
			
			goodsSrc.setAuditStatus(ConstantsSql.Audit_Finish);
			goodsSrc.setSourceId(0);
			goodsSrc.setTitle(goods.getTitle());
			goodsSrc.setName(goods.getName());
			goodsSrc.setPrice(goods.getPrice());
			if(goods.getPicture() != null)
				goodsSrc.setPicture(goods.getPicture());
			goodsSrc.setHasVipPrice(goods.getHasVipPrice());
			goodsSrc.setVipPrice(goods.getVipPrice());
//			if(!goods.getHasVipPrice())
//				deleteQRCodePic(goods.getId(),request);
			goodsSrc.setUnit(goods.getUnit());
			goodsSrc.setTypeIdChild(goods.getTypeIdChild());
			goodsSrc.setTypeIdParent(goods.getTypeIdParent());
			goodsSrc.setSendTime(goods.getSendTime());
			goodsSrc.setFreigth(goods.getFreigth());
			goodsSrc.setScore(goods.getScore());
			goodsSrc.setBlockId(goods.getBlockId());
//			System.out.println(goods.getGoodsDetail());
			goodsSrc.setGoodsDetail(goods.getGoodsDetail());
//			System.out.println(goodsSrc.getGoodsDetail());
			goodsSrc.setWeight(goods.getWeight());
			goodsMapper.updateByPrimaryKeySelective(goodsSrc);
//			System.out.println("id="+goodsSrc.getId());
			i=goodsMapper.deleteByPrimaryKey(id);
			
			//删除新增加的运费模板
			 FreightExample example=new FreightExample();
			 cn.bupt.smartyagl.entity.autogenerate.FreightExample.Criteria friCriteria=example.createCriteria();
			 friCriteria.andGoodsIdEqualTo(goods.getId());
			 friCriteria.andAuditStatusEqualTo(ConstantsSql.Audit_Draft);
			 List<Freight> freightList=freightMapper.selectByExample(example);
			 for (Freight freight : freightList) {
				freight.setAuditStatus(ConstantsSql.Audit_Finish);
				freightMapper.updateByPrimaryKey(freight);
			}
			 //将删除的运费模板改成正常的
			 FreightExample example2=new FreightExample();
			 cn.bupt.smartyagl.entity.autogenerate.FreightExample.Criteria friCriteria2=example2.createCriteria();
			 friCriteria2.andGoodsIdEqualTo(goods.getSourceId());
			 friCriteria2.andAuditStatusEqualTo(ConstantsSql.Audit_WaitDelete);
			 List<Freight> freightList2=freightMapper.selectByExample(example2);
			 for (Freight freight2 : freightList2) {
				freight2.setAuditStatus(ConstantsSql.Audit_Finish);
				freight2.setIsdisplay(0);
				freightMapper.updateByPrimaryKey(freight2);
			}
		}
		else{
			//审核不通过
			Goods goodsSrc=goodsMapper.selectByPrimaryKey(goods.getSourceId());
			i=this.deleteGoods(goods, request);//删除编辑的信息
			 //删除新增加的运费模板
			 FreightExample example=new FreightExample();
			 cn.bupt.smartyagl.entity.autogenerate.FreightExample.Criteria friCriteria=example.createCriteria();
			 friCriteria.andGoodsIdEqualTo(goods.getSourceId());
			 friCriteria.andAuditStatusEqualTo(ConstantsSql.Audit_Draft);
			 freightMapper.deleteByExample(example);
			 //将删除的运费模板改成正常的
			 FreightExample example2=new FreightExample();
			 cn.bupt.smartyagl.entity.autogenerate.FreightExample.Criteria friCriteria2=example2.createCriteria();
			 friCriteria2.andGoodsIdEqualTo(goods.getSourceId());
			 friCriteria2.andAuditStatusEqualTo(ConstantsSql.Audit_WaitDelete);
			 List<Freight> freightList=freightMapper.selectByExample(example2);
			 for (Freight freight2 : freightList) {
				freight2.setAuditStatus(ConstantsSql.Audit_Finish);
				freightMapper.updateByPrimaryKey(freight2);
			}
			goodsSrc.setAuditStatus(ConstantsSql.Audit_Finish);
			i=goodsMapper.updateByPrimaryKey(goodsSrc);
		}
		if(i==1){
			return true;
		}
		else{
			return false;
		}
	}

	private void deleteQRCodePic(Integer goodsId,HttpServletRequest request) {
		String qrcodePath = request.getSession().getServletContext().getRealPath("")+Constants.QRCODE_PATH;
		File[] files = new File(qrcodePath).listFiles();
		for(File file:files) {
			if(!file.isDirectory() && file.getName().endsWith("_"+goodsId))
				file.delete();
		}
	}

	@Override
	public GoodsList getGoodsDetail(Integer goodsId) {
		List<GoodsList> goodsList = null;
		try{
			 GoodsListExample ge = new GoodsListExample();
			 ge.createCriteria().andIdEqualTo(goodsId);
		     goodsList = goodsListMapper.selectByExampleWithBLOBs(ge);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(goodsList.size() <= 0){
			return null;
		}
		
		return goodsList.get(0);
	}
	


	
	
	@Override
	public boolean verifyChangeGoods(Integer id) {
		Goods ad_draft = goodsMapper.selectByPrimaryKey( id );
		if( ad_draft.getSourceId() == 0 || ad_draft.getAuditStatus() != ConstantsSql.Audit_Draft){
			return false;
		}
		//更新原数据
		Goods ad_yum  = goodsMapper.selectByPrimaryKey( ad_draft.getSourceId() );
		ad_yum.setName( ad_draft.getName() );
		ad_yum.setPrice(ad_draft.getPrice());
		ad_yum.setPicture(ad_draft.getPicture());
		ad_yum.setGoodsDetail(ad_draft.getGoodsDetail());
		goodsMapper.updateByPrimaryKeySelective(ad_yum);
		//删除草稿
		goodsMapper.deleteByPrimaryKey(id);
		return true;
	}
	
	@Override
	public boolean deleteGoods(Integer id) {

	    //如果是草稿，改变原数据参数
		Goods fm = goodsMapper.selectByPrimaryKey(id);
		if( fm.getSourceId() != 0 ){//删除的是草稿，修改原数据为无草稿状态
			Goods ad_par = goodsMapper.selectByPrimaryKey( fm.getSourceId() );
			ad_par.setAuditStatus( ConstantsSql.Audit_Finish );
			goodsMapper.updateByPrimaryKey(ad_par);
		}
		//getTypebyId 删除相应类型的信息前判断剩余信息数量是否足够
		int rs = goodsMapper.deleteByPrimaryKey(id);
		GoodsExample ae = new GoodsExample();
		//删除草稿
		ae.createCriteria().andSourceIdEqualTo(id);
		goodsMapper.deleteByExample(ae);
		if(rs>0){
	    	return true;
	    }
		return false;
	}
	
	@Override
    public boolean deletePostGoods(Goods goods) {
        int i = goodsMapper.updateByPrimaryKeySelective(goods);
        if( i >0 ){
            return true;
        }
        return false;
    }
	
	/**
	 * 删除商品
	 * @param goods
	 * @param request
	 * @return
	 */
	private int deleteGoods(Goods goods,HttpServletRequest request) {
		//审核不通过，删除原来添加的商品以及商品的运费设置
		//审核不通过，删除原来添加的商品以及商品的运费设置
		 FreightExample example=new FreightExample();
		 cn.bupt.smartyagl.entity.autogenerate.FreightExample.Criteria friCriteria=example.createCriteria();
		 friCriteria.andGoodsIdEqualTo(goods.getId());
		 freightMapper.deleteByExample(example);
//		 FreightExample example=new FreightExample();
//		 cn.bupt.smartyagl.entity.autogenerate.FreightExample.Criteria friCriteria=example.createCriteria();
//		 friCriteria.andGoodsIdEqualTo(id);
//		 goods goods=goodsMapper.selectByPrimaryKey(id);
		//删除商品图片
		if (goods.getPicture()!=null) {
			// 删除原来的图片
			// 处理商品图片
			List<String> pictureList = new ArrayList<String>();
			try {
				pictureList = JsonConvert.getProductPictureAbsolute(goods.getPicture(),
						request);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String picture : pictureList) {
				String[] picture_arrString=picture.split("src");
				ImageUtil.deleteFileByPath(picture_arrString[0]);
			}
		}
		int i=goodsMapper.deleteByPrimaryKey(goods.getId());
		return i;
	}

	@Override
	public void updateGoodsBuyNum(int buyNum, int goodsId) {
		Goods good = goodsMapper.selectByPrimaryKey(goodsId);
		if(good != null) {
			good.setBuyNum(good.getBuyNum()+buyNum);
			goodsMapper.updateByPrimaryKeySelective(good);
		}
	}

	@Override
	public Goods findGoods(Integer goodsId) {
		// TODO Auto-generated method stub
		Goods good = goodsMapper.selectByPrimaryKey(goodsId);
		return good;
	}
}
