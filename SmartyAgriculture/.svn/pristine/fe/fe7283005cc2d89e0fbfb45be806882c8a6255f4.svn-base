package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.GoodsListMapper;
import cn.bupt.smartyagl.dao.autogenerate.QRCodeRecodeMapper;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.GoodsListExample;
import cn.bupt.smartyagl.entity.autogenerate.QRCodeRecode;
import cn.bupt.smartyagl.entity.autogenerate.QRCodeRecodeExample;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IQRCodeService;
import cn.bupt.smartyagl.util.MD5Util;
import cn.bupt.smartyagl.util.pinyin4jUtil;
import oracle.sql.DATE;
@Service
public class QRCodeServiceImpl implements IQRCodeService {

	@Autowired
	QRCodeRecodeMapper qrCodeRecodeMapper;
	@Autowired
	GoodsListMapper goodsListMapper;
	@Override
	public List<GoodsList> getQRcodeGoodsList() {
		GoodsListExample ge = new GoodsListExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsListExample.Criteria ctr = ge.createCriteria();
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
		//只搜索有扫码价格的商品
		ctr.andHasVipPriceEqualTo(true);
		//查询
		List<GoodsList> goodsList = goodsListMapper.selectByExample(ge);
		return goodsList;
	}
	@Override
	public String addQRCodeRecord(Integer goodsId, Integer userId) {
		QRCodeRecodeExample qre = new QRCodeRecodeExample();
		qre.createCriteria().andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId);
		List<QRCodeRecode> list  = qrCodeRecodeMapper.selectByExample(qre);
		String token = null;
		if(list.size() == 0) {
			QRCodeRecode qrr = new QRCodeRecode();
			qrr.setGoodsId(goodsId);
			qrr.setUserId(userId);
			token = MD5Util.MD5(goodsId.toString()+userId.toString()) ;
//			System.out.println(token);
			qrr.setToken(token);
			qrCodeRecodeMapper.insert(qrr);
			return token;
		}
		else {
			return list.get(0).getToken();			
		}
	}
	@Override
	public QRCodeRecode getQRCodeRecode(int goodsId, Integer userId) {
		QRCodeRecodeExample qre = new QRCodeRecodeExample();
		qre.createCriteria().andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId);
		List<QRCodeRecode> list  = qrCodeRecodeMapper.selectByExample(qre);
		if(list.size() == 1)
			return list.get(0);
		else
			return null;
	}
	@Override
	public QRCodeRecode validateToken(String qrCodeToken) {
		QRCodeRecodeExample qre = new QRCodeRecodeExample();
		qre.createCriteria().andTokenEqualTo(qrCodeToken);
		List<QRCodeRecode> list = qrCodeRecodeMapper.selectByExample(qre);
		if(list.size() == 1)
			return list.get(0);
		else
			return null;
	}
}
