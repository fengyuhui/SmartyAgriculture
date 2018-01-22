package cn.bupt.smartyagl.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.GeneratedCardsMapper;
import cn.bupt.smartyagl.dao.autogenerate.GeneratedCardsOperMapper;
import cn.bupt.smartyagl.dao.autogenerate.GeneratedDetailsMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsCardLogMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsCardLogViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsCardMapper;
import cn.bupt.smartyagl.dao.autogenerate.HasBuyRecordGoodsCardMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderListMapper;
import cn.bupt.smartyagl.dao.autogenerate.UserMapper;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedCards;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedCardsOper;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedCardsOperExample;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedDetails;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedDetailsExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCard;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLog;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLogExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLogView;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLogViewExample;
import cn.bupt.smartyagl.entity.autogenerate.HasBuyRecordGoodsCard;
import cn.bupt.smartyagl.entity.autogenerate.HasBuyRecordGoodsCardExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.OrderList;
import cn.bupt.smartyagl.model.GeneratedDetailsModel;
import cn.bupt.smartyagl.service.IGoodsCardService;
import cn.bupt.smartyagl.util.RandomCodeUtil;
import cn.bupt.smartyagl.util.SendSMSUtil;
import cn.bupt.smartyagl.util.TimeUtil;
import cn.bupt.smartyagl.util.propertiesUtil;
import cn.bupt.smartyagl.util.excel.ExportExcel;
import cn.bupt.smartyagl.util.excel.ExportExcelUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-2 下午4:04:09 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:/spring-*.xml","classpath*:/spring.xml"})
public class GoodsCardServiceImpl implements IGoodsCardService{
	/**
	 * 购物卡mapper
	 */
	@Autowired
	GeneratedCardsOperMapper generatedCardsOperMapper;
	@Autowired
	GeneratedDetailsMapper generatedDetailsMapper;
	@Autowired
	GeneratedCardsMapper generatedCardsMapper;
	@Autowired
	GoodsCardMapper goodsCardMapper;
	/**
	 * 商品mapper
	 */
	@Autowired
	GoodsMapper goodsMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	GoodsCardLogMapper goodsCardLogMapper;
	
	@Autowired
	GoodsCardLogViewMapper goodsCardLogViewMapper;
	
	@Autowired
	OrderListMapper orderListMapper;
	
	@Autowired 
	HasBuyRecordGoodsCardMapper hasBuyRecordGoodsCardMapper;
	
	@Test
	//测试类
	public void test(){
		//reduceMoney(166638, 20.0);
		GoodsCard gc = this.generateCode(10110);
		System.out.println(gc.getNumber());
		System.out.println(gc.getPasswd());
	}
	

	
	@Override
	public int getCardNum(int id) {
		return generatedCardsMapper.selectByPrimaryKey(id).getCardNum();
	}
	@Override
	public void setReviewed(int id,boolean rev) {
		GeneratedCards gc = generatedCardsMapper.selectByPrimaryKey(id);
		gc.setReviewed(rev);
		generatedCardsMapper.updateByPrimaryKey(gc);
	}
	@Override
	public double getDeno(int id) {
		return generatedCardsMapper.selectByPrimaryKey(id).getDenomination();
	}
	@Override
	public void saveGeneratedCards(GeneratedCards s) {
		generatedCardsMapper.insert(s);
	}
	@Override
	public List<GeneratedCardsOper> getReviewedRecords() {
		GeneratedCardsOperExample gcoe = new GeneratedCardsOperExample();
		gcoe.setOrderByClause("id desc");
		gcoe.createCriteria().andReviewedEqualTo(true);
		return generatedCardsOperMapper.selectByExample(gcoe);
	}
	@Override
	public List<GeneratedCardsOper> getUnReviewedRecords() {
		GeneratedCardsOperExample gcoe = new GeneratedCardsOperExample();
		gcoe.setOrderByClause("id desc");
		gcoe.createCriteria().andReviewedEqualTo(false);
		return generatedCardsOperMapper.selectByExample(gcoe);
	}
	/*
	@Override
	public List<GeneratedCards> getReviewedRecords() {
		GeneratedCardsExample gce = new GeneratedCardsExample();
		gce.createCriteria().andReviewedEqualTo(true);
		return generatedCardsMapper.selectByExample(gce);
	}*/
	/*
	@Override
	public List<GeneratedCards> getUnReviewedRecords() {
		GeneratedCardsExample gce = new GeneratedCardsExample();
		gce.createCriteria().andReviewedEqualTo(false);
		return generatedCardsMapper.selectByExample(gce);
	}
	*/
	@Override
	public List<GeneratedDetails> getReviewedCardsDetails(int id){
		GeneratedDetailsExample gde = new GeneratedDetailsExample();
		gde.setOrderByClause("id desc");
		gde.createCriteria().andGeneratedIdEqualTo(id);
		return generatedDetailsMapper.selectByExample(gde);
	}
	
	@Override
	public void generateCard(Double deno,int genId) {
		boolean noId = true;
		Integer randomId = null;
		//生成账号
		while( noId ){//生成id
			//6位数id
			randomId = Integer.parseInt( RandomCodeUtil.getNumberRandomCode(6) );
			GoodsCardExample ge = new GoodsCardExample();
			ge.createCriteria().andNumberEqualTo(randomId);
			List<GoodsCard> tmp = goodsCardMapper.selectByExample(ge);
			if(tmp.size() == 0){
				noId = false;
			} 
		}
		//生成密码
		String passwd = RandomCodeUtil.getNumberRandomCode(8);
		//生成对象并插入
		GeneratedDetails gd = new GeneratedDetails();
		GoodsCard gc = new GoodsCard();
		gc.setCreate_time(new Date());
		gc.setNumber(randomId);
		gc.setPasswd(passwd);
		gc.setMoney(deno);
		gc.setType(2);
		goodsCardMapper.insert(gc);
		gd.setNumber( randomId );
		gd.setMoney(deno);
		gd.setGeneratedId(genId);
		gd.setPasswd(passwd);
		generatedDetailsMapper.insert(gd);
	} 
	@Override
	public synchronized void generateCard(Double deno,int genId,int num) {
		//生成账号
		//生成密码
		
		//生成对象并插入
		List<GeneratedDetails> gds = new ArrayList<GeneratedDetails>();
		List<GoodsCard> gcs = new ArrayList<GoodsCard>();
		GoodsCardExample example = new GoodsCardExample();
		List<GoodsCard> goodsCards = goodsCardMapper.selectByExample(example);
		List<Integer> goodsCardsNumber = new ArrayList<>();
		for(GoodsCard goodsCard : goodsCards) {
			goodsCardsNumber.add(goodsCard.getNumber());
		}
		Set<Integer> exNumbers = new HashSet<Integer>(goodsCardsNumber);
		Integer[] numbers = getNumbers(num,exNumbers);
		String[] passwds = new String[num];
		for(int i = 0;i<num;i++) 
			passwds[i] = RandomCodeUtil.getNumberRandomCode(8);
		for( int i = 0;i < num;i++ ) {
			GeneratedDetails gd = new GeneratedDetails();
			GoodsCard gc = new GoodsCard();
			gc.setCreate_time(new Date());
			gc.setNumber(numbers[i]);
			gc.setPasswd(passwds[i]);
			gc.setMoney(deno);
			gc.setType(2);
	//		goodsCardMapper.insert(gc);
			gcs.add(gc);
			gd.setNumber( numbers[i] );
			gd.setMoney(deno);
			gd.setGeneratedId(genId);
			gd.setPasswd(passwds[i]);
			gds.add(gd);
	//		generatedDetailsMapper.insert(gd);
		}
		for(GoodsCard goodsCard : gcs) {
			goodsCardMapper.insert(goodsCard);
		}
		for(GeneratedDetails generatedDetails : gds) {
			generatedDetailsMapper.insert(generatedDetails);
		}
		return ;		
//		Set<Integer> exNumbers = new HashSet<Integer>(goodsCardMapper.selectAllNumbers());
//		int id = 0;
//		do {
//			id = existNumber(exNumbers,numbers,id);
//			if(id == -1) {
//
//			}
//			else {
//				numbers[id] = Integer.parseInt( RandomCodeUtil.getNumberRandomCode(6));
//			}
//		}
//		while(id != -1);
	}
	private Integer[] getNumbers(int num,Set<Integer> s) {
		Integer[] numbers = new Integer[num];
//		Set<Integer> s = new HashSet<Integer>();
		for(int i = 0;i<num;i++) {
			do {
				numbers[i] = Integer.parseInt( RandomCodeUtil.getNumberRandomCode(6) );
			}while(s.contains(numbers[i]));
			s.add(numbers[i]);
		}
		return numbers;
	}
	private int existNumber(Set<Integer> allNumbers,Integer[] numbers,int st) {
		for(int i = st;i<numbers.length;i++) {
			if(allNumbers.contains(numbers[i]))
				return i;
		}
		return -1;
	}
	@Override
	public GoodsCard generateCode(Integer goodsId) {
		boolean noId = true;
		Integer randomId = null;
		//生成账号
		while( noId ){//生成id
			//6位数id
			randomId = Integer.parseInt( RandomCodeUtil.getNumberRandomCode(6) );
			GoodsCardExample ge = new GoodsCardExample();
			ge.createCriteria().andNumberEqualTo(randomId);
			List<GoodsCard> tmp = goodsCardMapper.selectByExample(ge);
			if(tmp.size() == 0){
				noId = false;
			}
		}
		//生成密码
		String passwd = RandomCodeUtil.getNumberRandomCode(8);
		//获得购物卡价格
		Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
		if( goods.getTypeIdParent() != ConstantsSql.GoodsType_CardId ){
			return null;
		}
		//生成对象并插入
		GoodsCard gc = new GoodsCard();
		gc.setCreate_time(new Date());
		gc.setNumber( randomId );
		gc.setPasswd( passwd );
		gc.setMoney( goods.getPrice() );
		goodsCardMapper.insert(gc);
		//记录非md5密码
		gc.setPasswd(passwd);
		return gc;
	}

	@Override
	public Double reduceMoney(Integer cardNumber, Double money) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		if(money == 0){
			return 0.0;
		}
		GoodsCardExample ge = new GoodsCardExample();
		ge.createCriteria().andNumberEqualTo( cardNumber );
		List<GoodsCard> goodsCardList = goodsCardMapper.selectByExample( ge );
		if( goodsCardList.size() <= 0 ){
			return null;
		}
		GoodsCard gc = goodsCardList.get(0);
		gc.setMoney( gc.getMoney() - money );
		if(gc.getMoney() < 0){
			Double overReduce = gc.getMoney();
			gc.setMoney(0.0);
			goodsCardMapper.updateByPrimaryKeySelective(gc); 
			return overReduce;
		}
		int rs = goodsCardMapper.updateByPrimaryKeySelective(gc); 
		if(rs >0){
			return gc.getMoney();
		}
		return null;
	}

	@Override
	public boolean valideCard(GoodsCard goodsCard) {
		GoodsCardExample ge = new GoodsCardExample();
		ge.createCriteria().andNumberEqualTo(goodsCard.getNumber());
		List<GoodsCard> goodsCardList = goodsCardMapper.selectByExample( ge );
		if( goodsCardList.size() <=0 ){
			return false;
		}
		if( goodsCardList.get(0).getPasswd().equals(  goodsCard.getPasswd()  ) ){
			return true;
		}
		return false;
	}

	@Override
	public boolean allLogic(Integer orderId) {
		OrderList ol = orderListMapper.selectByPrimaryKey(orderId);
		Integer goodsId = ol.getGoodsId();
		String phone = userMapper.selectByPrimaryKey(ol.getUserId()).getPhone();
//		userExample ue = new userExample();
//		ue.createCriteria().andPhoneEqualTo(phone);
//		List<user> userList = userMapper.selectByExample( ue );
	
		GoodsCard goodsCard  = this.generateCode(goodsId);
		if(goodsCard == null){
			return false;
		}
		//获得短信模版id
		propertiesUtil.changePath("sendSms.properties");
		String modelId = propertiesUtil.getValue("Demo_GOODS_CARD_ID");
		//短信告知购物卡
		SendSMSUtil send = new SendSMSUtil();
		send.SendSMS( phone , new String[]{ goodsCard.getNumber().toString(),goodsCard.getPasswd()  }, modelId);
		return true;
	}

	@Override
	public List<GoodsCard> getGoodsCardList(GoodsCard condition) {
		GoodsCardExample ge = new GoodsCardExample();
		List<GoodsCard> list = goodsCardMapper.selectByExample( ge );
		return list;
	}

	@Override
	public List<GoodsCardLogView> getGoodsCardLogList(GoodsCardLogView condition) {
		GoodsCardLogViewExample ge = new GoodsCardLogViewExample();
		ge.setOrderByClause("create_time desc");
		cn.bupt.smartyagl.entity.autogenerate.GoodsCardLogViewExample.Criteria cia = ge.createCriteria();
		if( condition.getCardId() != null ){
			cia.andCardIdEqualTo( condition.getCardId() );
		}
		List<GoodsCardLogView> list = goodsCardLogViewMapper.selectByExample( ge );
		return list;
	}

	@Override
	public boolean addGoodsCartLog(GoodsCardLog condition) {
		if( condition.getCreate_time() != null ){//没有设置时间
			condition.setCreate_time( new Date() );
		}
		int rs = goodsCardLogMapper.insert(condition);
		if( rs > 0 ){
			return true;
		}
		return false;
	}



	@Override
	public GeneratedCards getGeneratedRec(int id) {
		return generatedCardsMapper.selectByPrimaryKey(id);
	}


	@Override
	public void exportExcel(int id,HttpServletResponse response) {
		List<GeneratedDetails> list = getReviewedCardsDetails(id);
		List<GeneratedDetailsModel> model = new ArrayList<GeneratedDetailsModel>();
		String[] headers = {"卡号","密码","面值"};
		String deskPath = ExportExcelUtil.getDeskPath();
		System.out.println(deskPath);
		String filePath = deskPath +File.separator +"购物卡详情-"+id + "-"+TimeUtil.getYMD() + ".xls";
		for(GeneratedDetails gd:list) {
			GeneratedDetailsModel gdm = new GeneratedDetailsModel();
			gdm.setNumber(gd.getNumber());
			gdm.setPasswd(gd.getPasswd());
			gdm.setMoney(gd.getMoney());
			model.add(gdm);
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			ExportExcel<GeneratedDetailsModel> exc = new ExportExcel<GeneratedDetailsModel>();
			exc.exportExcel(headers, model, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} 
		ExportExcelUtil.download(filePath, response);
	}


	@Override
	public GoodsCard getGoodsCart(Integer number, String passwd) {
		GoodsCardExample ge = new GoodsCardExample();
		ge.createCriteria().andNumberEqualTo( number );
		List<GoodsCard> list = goodsCardMapper.selectByExample(ge);
		//购物卡不存在或者密码不对
		if(list.size() == 0 || !list.get(0).getPasswd().equals(passwd) )
			return null;
		return list.get(0);
	}



	@Override
	public boolean addGoodsCartLogByAverage(List<OrderView> orderList,
			Double payMoney,Integer cardId) { 
		Double allPrice = 0.0;
        for (OrderView orderView : orderList) {
            allPrice += orderView.getMoney() + orderView.getFreight();
        }
        
        for( OrderView ov : orderList ){
        	OrderList ol = new OrderList();
        	ol.setId( ov.getId() );
        	ol.setStatus( ConstantsSql.PayStatus_CardPaySome );
        	orderListMapper.updateByPrimaryKeySelective( ol );
        	GoodsCardLog gl = new GoodsCardLog();
        	gl.setCardId( cardId );
        	//计算出每个订单应有的价格
        	gl.setMoney( payMoney * (ov.getMoney() + ov.getFreight())/allPrice  );
        	gl.setCreate_time( new Date() );
        	gl.setOrderId( ov.getId() );
        	goodsCardLogMapper.insert( gl );
        }
		return true;
	}



	@Override
	public List<HasBuyRecordGoodsCard> gethasBuyRecordGoodsCard() {
		HasBuyRecordGoodsCardExample ex = new HasBuyRecordGoodsCardExample();
		return hasBuyRecordGoodsCardMapper.selectByExample(ex);
	}



}
