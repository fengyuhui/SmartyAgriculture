package cn.bupt.smartyagl.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.InputGoodsViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.dao.autogenerate.InputGoodsMapper;
import cn.bupt.smartyagl.entity.autogenerate.InputGoodsView;
import cn.bupt.smartyagl.entity.autogenerate.InputGoodsViewExample;
import cn.bupt.smartyagl.entity.autogenerate.InputGoodsViewExample.Criteria;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.service.IGoodsInputService;
import cn.bupt.smartyagl.entity.autogenerate.InputGoods;
@Service
public class GoodsInputServiceImpl implements IGoodsInputService {
	
	@Autowired
	InputGoodsMapper inputGoodsMapper;
	@Autowired
	InputGoodsViewMapper inputGoodsViewMapper;
	@Autowired
	GoodsMapper goodsMapper;

	@Override
	public boolean inputGoods(
			cn.bupt.smartyagl.entity.autogenerate.InputGoods InputGoods) {
		// TODO Auto-generated method stub
		InputGoods.setStatus(0);
		Date createTimeDate=new Date();//设置商品创建时间
		InputGoods.setAdd_time(createTimeDate);
		int i=inputGoodsMapper.insert(InputGoods);
		if(i!=0){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public List<InputGoodsView> getInputGoods(
			int status) {
		// TODO Auto-generated method stub
		InputGoodsViewExample example=new InputGoodsViewExample();
		Criteria criteria=example.createCriteria();
		criteria.andStatusEqualTo(status);
		List<InputGoodsView> inputGoodsViewsList=inputGoodsViewMapper.selectByExample(example);
		return inputGoodsViewsList;
	}

	@Override
	public boolean auditInputGoods(int id, int status) {
		// TODO Auto-generated method stub
		//修改进货商品状态
		InputGoods inputGoods=inputGoodsMapper.selectByPrimaryKey(id);
		inputGoods.setStatus(status);
		int i=inputGoodsMapper.updateByPrimaryKeySelective(inputGoods);
		if(status==1&&i==1){
			//审核成功
			boolean flag=this.auditPass(id);
			return flag;
		}
		else if(i==1){
			return true;
		}
		return false;
	}
	/**
	 * 商品进货审核通过
	 * 修改商品库存
	 */
	public boolean auditPass(int id){
		InputGoods inputGoods=inputGoodsMapper.selectByPrimaryKey(id);
		Goods goods=goodsMapper.selectByPrimaryKey(inputGoods.getGoods_id());
		goods.setStock(goods.getStock()+inputGoods.getNum());
		int i=goodsMapper.updateByPrimaryKey(goods);
		if(i!=0){
			return true;
		}
		else{
			return false;
		}
	}

}
