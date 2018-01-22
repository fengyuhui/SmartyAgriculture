package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsTypeMapper;
import cn.bupt.smartyagl.entity.GoodsTypeList;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsType;
import cn.bupt.smartyagl.entity.autogenerate.GoodsTypeExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsTypeExample.Criteria;
import cn.bupt.smartyagl.model.GoodsTypeModel;
import cn.bupt.smartyagl.service.IGoodsTypeService;

@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService {

	@Autowired
	GoodsTypeMapper goodsTypeMapper;
	@Autowired
	GoodsMapper goodsMapper;
	@Override
	public void unDo(int id) {
		GoodsType gt = goodsTypeMapper.selectByPrimaryKey(id);
		if(gt.getModifyId() == -1) {
			goodsTypeMapper.deleteByPrimaryKey(id);
		}
		else {
			GoodsType gto = goodsTypeMapper.selectByPrimaryKey(gt.getModifyId());
			gto.setStatus(0);
			goodsTypeMapper.deleteByPrimaryKey(id);
			goodsTypeMapper.updateByPrimaryKey(gto);
		}
	}
	@Override
	public void setReviewed(int id) {
		GoodsType gt = goodsTypeMapper.selectByPrimaryKey(id);
		if(gt.getModifyId() == -1) {
			gt.setStatus(0);
			gt.setIsdisplay(true);
			goodsTypeMapper.updateByPrimaryKey(gt);
		}
		else {
			GoodsType goodsTypeOld = goodsTypeMapper.selectByPrimaryKey(gt.getModifyId());
			goodsTypeOld.setName(gt.getName());
			goodsTypeOld.setPicture(gt.getPicture());
			goodsTypeOld.setStatus(0);
			goodsTypeMapper.updateByPrimaryKey(goodsTypeOld);
			goodsTypeMapper.deleteByPrimaryKey(id);
		}
	}
	@Override
	public List<GoodsTypeModel> getUnreviewedTypes() {
		List<GoodsTypeModel> ans = new ArrayList<GoodsTypeModel>();
		GoodsTypeExample gte = new GoodsTypeExample();
		gte.createCriteria().andStatusBetween(1, 2);
		List<GoodsType> list = goodsTypeMapper.selectByExample(gte);
		for(GoodsType gt:list) {
			GoodsTypeModel gtm = new GoodsTypeModel();
			gtm.setId(gt.getId());
			gtm.setName(gt.getName());
			gtm.setPicture(gt.getPicture());
			gtm.setLevel(gt.getLevel());
			gtm.setStatus(gt.getStatus());
			if(gt.getParentId() == -1)
				gtm.setParentName("无");
			else {
				GoodsType par = goodsTypeMapper.selectByPrimaryKey(gt.getParentId());
				gtm.setParentName(par.getName());
			}
			ans.add(gtm);
		}
		return ans;
	}

	@Override
	public List<GoodsTypeModel> getDisplayGoodsType() {
		// TODO Auto-generated method stub
		List<GoodsTypeModel> goodsTypeList = null;
		Integer parentId = -1;// 一级菜单类型
		goodsTypeList = this.getSubType(parentId);
		List<GoodsTypeModel> subGoodsTypeModels = null;
		for (GoodsTypeModel goodsTypeModel : goodsTypeList) {
			parentId = goodsTypeModel.getId();
			subGoodsTypeModels = this.getSubType(parentId);
			goodsTypeModel.setSubGoodsTypeList(subGoodsTypeModels);
		}
		return goodsTypeList;
	}

	/**
	 * 查找子类型
	 */
	@Override
	public List<GoodsTypeModel> getSubType(Integer parentId) {
		// TODO Auto-generated method stub
		GoodsTypeExample GoodsTypeExample = new GoodsTypeExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsTypeExample.Criteria criteria = GoodsTypeExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		criteria.andIsdisplayEqualTo(ConstantsSql.GoodType_Display);
		List<GoodsType> GoodsTypeList = goodsTypeMapper
				.selectByExample(GoodsTypeExample);
		List<GoodsTypeModel> goodsTypeModelList = new ArrayList<GoodsTypeModel>();
		List<Goods> goodsList = new ArrayList<>();
		for (GoodsType goodsType : GoodsTypeList) {
			GoodsTypeModel goodsTypeModel = new GoodsTypeModel();
			goodsTypeModel.setId(goodsType.getId());
			goodsTypeModel.setName(goodsType.getName());
			goodsTypeModel.setLevel(goodsType.getLevel());
			goodsTypeModel.setParentId(goodsType.getParentId());
			goodsList = this.getGoodsList(goodsType.getId(), parentId);
			goodsTypeModel.setGoodsList(goodsList);
			//goodsTypeModel.setStatus(goodsType.getStatus());
			goodsTypeModelList.add(goodsTypeModel);
			// goodsType.get
		}
		return goodsTypeModelList;
	}

	@Override
	public List<GoodsTypeList> getGoodsTypeList(HttpServletRequest request) {
		List<GoodsTypeList> goodsTypeLists = this.getPartentTypeList();
		for (GoodsTypeList goodsTypeList : goodsTypeLists) {// 查找子菜单
			List<GoodsTypeList> tmpList = this.getChildTypeList(goodsTypeList
					.getId());
			for(GoodsTypeList aa:tmpList) {
				aa.setPicture("http://www.zhongyuanfarm.cn"  +":"+request.getLocalPort()+aa.getPicture());
			}
			goodsTypeList.setChildList(tmpList);
		}
		return goodsTypeLists;
	}

	@Override
	public List<GoodsTypeList> getPartentTypeList() {
		GoodsTypeExample ge = new GoodsTypeExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsTypeExample.Criteria criteria = ge.createCriteria();
		criteria.andParentIdEqualTo(ConstantsSql.parentGoodsTypeId);
		criteria.andIsdisplayEqualTo(ConstantsSql.GoodType_Display);
		
		List<GoodsType> goodsTypes = goodsTypeMapper.selectByExample(ge);

		List<GoodsTypeList> gyls = this
				.convertGoodsTypeToGoodsTypeListAll(goodsTypes);
//		 for (GoodsTypeList goodsTypeList : gyls) {
//		 List<GoodsTypeList> childTypeList =
//		 getChildTypeList(goodsTypeList.getId());
//		 goodsTypeList.setChildList(childTypeList);
//		 }
		return gyls;
	}

	@Override
	public List<GoodsTypeList> getChildTypeList(Integer parentId) {
		GoodsTypeExample ge = new GoodsTypeExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsTypeExample.Criteria criteria = ge.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		criteria.andIsdisplayEqualTo(ConstantsSql.GoodType_Display);
		List<GoodsType> goodsTypes = goodsTypeMapper.selectByExample(ge);
		List<GoodsTypeList> gyls = this
				.convertGoodsTypeToGoodsTypeListAll(goodsTypes);
		return gyls;
	}

	/**
	 * 将数据库表类型变为接口可用类型
	 * 
	 * @param goodsType
	 * @return
	 */
	public List<GoodsTypeList> convertGoodsTypeToGoodsTypeListAll(
			List<GoodsType> goodsTypes) {
		List<GoodsTypeList> goodsTypeLists = new ArrayList<GoodsTypeList>();
		for (GoodsType goodsType : goodsTypes) {
			GoodsTypeList gtl = this.convertGoodsTypeToGoodsTypeList(goodsType);
			goodsTypeLists.add(gtl);
		}
		return goodsTypeLists;
	}

	/**
	 * 将数据库表类型变为接口可用类型
	 * 
	 * @param goodsType
	 * @return
	 */
	public GoodsTypeList convertGoodsTypeToGoodsTypeList(GoodsType goodsType) {
		GoodsTypeList goodsTypeList = new GoodsTypeList();
		goodsTypeList.setId(goodsType.getId());
		goodsTypeList.setLevel(goodsType.getLevel());
		goodsTypeList.setName(goodsType.getName());
		goodsTypeList.setParentId(goodsType.getParentId());
		goodsTypeList.setPicture(goodsType.getPicture());
		return goodsTypeList;
	}

	@Override
	/**
	 * 产品类别列表
	 * @auto waiting
	 */
	public ModelAndView getTypeList(Integer level, Integer parentId,int pageNumber,
			int pageSize, ModelAndView modelAndView) {
		// TODO Auto-generated method stub
		GoodsTypeExample goodsTypeExample = new GoodsTypeExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsTypeExample.Criteria criteria = goodsTypeExample
				.createCriteria();
		if (level != 0) {
			criteria.andLevelEqualTo(level);
		}
		if(parentId!=0){
			criteria.andParentIdEqualTo(parentId);
		}
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(3);
		criteria.andStatusIn(list);
		// 分页
		@SuppressWarnings("rawtypes")
		Page page = PageHelper.startPage(pageNumber, pageSize, "");
		List<GoodsType> goodsTypeList=goodsTypeMapper.selectByExample(goodsTypeExample);
		List<GoodsTypeModel> goodsTypeModelList=this.formatGoodsTypeList(goodsTypeList);
		modelAndView.addObject("goodsTypeList", goodsTypeModelList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
		return modelAndView;
	}
	/**
	 * 格式化返回数据
	 * @param goodsTypeList
	 * @return
	 */
	private List<GoodsTypeModel> formatGoodsTypeList(List<GoodsType> goodsTypeList){
		List<GoodsTypeModel> goodsTypeModelList=new ArrayList<GoodsTypeModel>();
		for (GoodsType goodsType : goodsTypeList) {
			GoodsTypeModel goodsTypeModel=new GoodsTypeModel();
			goodsTypeModel.setId(goodsType.getId());
			goodsTypeModel.setName(goodsType.getName());
			goodsTypeModel.setPicture(goodsType.getPicture());
			goodsTypeModel.setLevel(goodsType.getLevel());
			goodsTypeModel.setIsdisplay(goodsType.getIsdisplay());
			goodsTypeModel.setStatus(goodsType.getStatus());
			GoodsType parentGoodsType=goodsTypeMapper.selectByPrimaryKey(goodsType.getParentId());
			if(parentGoodsType!=null){
				goodsTypeModel.setParentName(parentGoodsType.getName());
			}
			else{
				goodsTypeModel.setParentName("无");
			}
			goodsTypeModelList.add(goodsTypeModel);
		}
		return goodsTypeModelList;
	}
    /**
     * 添加商品类型
     */
	@Override
	public boolean addType(GoodsType goodsType) {
		if(goodsType.getLevel()==1){
			goodsType.setParentId(-1);
		}
		int i=goodsTypeMapper.insert(goodsType);
		if (i==1) {
			return true;
		}
		else{
			return false;
		}

	}

	@Override
	public GoodsType getGoodsTypeById(int id) {
		// TODO Auto-generated method stub
		GoodsType goodsType=goodsTypeMapper.selectByPrimaryKey(id);
		return goodsType;
	}

	@Override
	public boolean updateGoodsType(GoodsType goodsType) {
		// TODO Auto-generated method stub
		int i=goodsTypeMapper.updateByPrimaryKey(goodsType);
		if (i==1) {
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean updateGoodsISdisplay(boolean isdisplay, int typeId) {
		// TODO Auto-generated method stub
		GoodsType goodsType=goodsTypeMapper.selectByPrimaryKey(typeId);
		goodsType.setIsdisplay(isdisplay);
		//代表是1级菜单类别，需要修改
		if (goodsType.getLevel()==1) {

			GoodsTypeExample goodsTypeExample=new GoodsTypeExample();
			Criteria criteria=goodsTypeExample.createCriteria();
			criteria.andParentIdEqualTo(goodsType.getId());
			List<GoodsType> goodsTypelList=goodsTypeMapper.selectByExample(goodsTypeExample);
		 for (GoodsType goodsType2 : goodsTypelList) {
			 goodsType2.setIsdisplay(isdisplay);
			 this.updateGoodsType(goodsType2); 
		 }	
		}
		return this.updateGoodsType(goodsType);
	}

	@Override
	public void removeById(int id) {
		goodsTypeMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<Goods> getGoodsList(Integer subId, Integer parentId) {
		GoodsExample goodsExample = new GoodsExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsExample.Criteria criteria = goodsExample.createCriteria();
		criteria.andTypeIdChildEqualTo(subId);
		criteria.andTypeIdParentEqualTo(parentId);
		List<Goods> goods = goodsMapper.selectByExample(goodsExample);
		return goods;
	}
		
}
