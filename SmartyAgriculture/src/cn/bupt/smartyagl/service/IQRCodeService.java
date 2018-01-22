package cn.bupt.smartyagl.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.QRCodeRecode;

@Service
public interface IQRCodeService {
	/**
	 * 获取所有有扫码价格的商品
	 * @return
	 */
	List<GoodsList> getQRcodeGoodsList();

	String addQRCodeRecord(Integer goodsId, Integer userId);

	QRCodeRecode getQRCodeRecode(int goodsId, Integer userId);

	QRCodeRecode validateToken(String qrCodeToken);
}
