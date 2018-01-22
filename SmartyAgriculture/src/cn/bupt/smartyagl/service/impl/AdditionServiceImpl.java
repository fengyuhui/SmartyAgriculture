package cn.bupt.smartyagl.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.AdditionMapper;
import cn.bupt.smartyagl.dao.autogenerate.AdditionViewMapper;
import cn.bupt.smartyagl.entity.AdditionAndHtml;
import cn.bupt.smartyagl.entity.autogenerate.Addition;
import cn.bupt.smartyagl.entity.autogenerate.AdditionExample;
import cn.bupt.smartyagl.entity.autogenerate.AdditionView;
import cn.bupt.smartyagl.entity.autogenerate.AdditionViewExample;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessageExample;
import cn.bupt.smartyagl.service.IAdditionService;

@Service
public class AdditionServiceImpl implements IAdditionService {
	@Autowired
	AdditionMapper additionMapper;
	@Autowired
	AdditionViewMapper additionViewMapper;
	@Override
	public boolean save(Addition add) {
		return  additionMapper.insert(add) == 0 ? false:true;
	}
	@Override
	public Addition getAdditionByTitle(String title) {
		AdditionExample ae = new AdditionExample();
		List<Byte> list = new ArrayList<Byte>();
		list.add((byte)0);
		list.add((byte)3);
		ae.createCriteria().andTitleEqualTo(title).andStatusIn(list);
		return additionMapper.selectByExampleWithBLOBs(ae).get(0);
	}
	@Override
	public void setModified(int id) {
		Addition ad = additionMapper.selectByPrimaryKey(id);
		ad.setStatus((byte)3);
		additionMapper.updateByPrimaryKey(ad);
	}
	@Override
	public List<AdditionView> getUnreviewedMsg() {
		AdditionViewExample ave = new AdditionViewExample();
		ave.createCriteria().andStatusEqualTo((byte)2);
		return additionViewMapper.selectByExample(ave);
	}
	@Override
	public List<Addition> getAllFourAddition() {
		AdditionExample ae = new AdditionExample();
		List<String> titList = new ArrayList<String>();
		titList.add("用户协议");
		titList.add("关于我们");
		titList.add("常见问题");
//		titList.add("新闻接口");
		List<Byte> staList = new ArrayList<Byte>();
		staList.add((byte)0);
		staList.add((byte)3);
		ae.createCriteria().andTitleIn(titList).andStatusIn(staList);
		return additionMapper.selectByExample(ae);
	}
	
	@Override
	public List<Addition> getAdditionListById(Integer id) {
		//限制查询个数
		
		AdditionExample fe = new AdditionExample();
		cn.bupt.smartyagl.entity.autogenerate.AdditionExample.Criteria ca = fe.createCriteria();
		ca.andIdEqualTo(id);
		
		return additionMapper.selectByExample( fe );
	}
	@Override
	public Addition getAdditionById(int id) {
		return additionMapper.selectByPrimaryKey(id);
	}
	@Override
	public boolean setReviewed(int id) {
		Addition add = additionMapper.selectByPrimaryKey(id);
		Addition par = additionMapper.selectByPrimaryKey(add.getModifyid());
		par.setContent(add.getContent());
		par.setStatus((byte)0);
		par.setOper_id(add.getOper_id());
		return additionMapper.updateByPrimaryKeyWithBLOBs(par) == 1 && additionMapper.deleteByPrimaryKey(id) == 1;
	}
	@Override
	public boolean unDo(int id) {
		Addition add = additionMapper.selectByPrimaryKey(id);
		Addition par = additionMapper.selectByPrimaryKey(add.getModifyid());
		par.setStatus((byte)0);
		return additionMapper.updateByPrimaryKey(par) == 1 && additionMapper.deleteByPrimaryKey(id) == 1;
	}

	@Override
	public Addition getAdditionDetail(Integer id) {
		List<Addition> additionList = null;
		try{
			AdditionExample ge = new AdditionExample();
			 ge.createCriteria().andIdEqualTo(id);
			 additionList = additionMapper.selectByExampleWithBLOBs(ge);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(additionList.size() <= 0){
			return null;
		}
		
		return additionList.get(0);
	}
	
	@Override
	public AdditionAndHtml convertMessage(Addition addition) throws IllegalArgumentException, IllegalAccessException {
		AdditionAndHtml gc = new AdditionAndHtml();
		Field[] message_fields = Addition.class.getDeclaredFields();
		for(Field field : message_fields){
			field.setAccessible(true);//可强行访问

			field.set(gc, field.get(addition));
		}
		return gc;
	}
}
