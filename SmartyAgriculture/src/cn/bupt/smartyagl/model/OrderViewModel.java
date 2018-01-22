package cn.bupt.smartyagl.model;

import cn.bupt.smartyagl.entity.autogenerate.OrderMaster;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
/**
 * 订单视图
 *<p>Title:OrderViewModel</p>
 *<p>Description:</p>
 * @author waiting
 *@date 2016年6月20日 上午9:02:34
 */
public class OrderViewModel  {
   public String dateString;
   public OrderMaster orderView;
   public String addressname;//收货人姓名
   public String phone;//联系方式
   public String province;// 省市县
   public String detailAddress;//详细地址

public OrderMaster getOrderView() {
	return orderView;
}

public String getAddressname() {
	return addressname;
}

public void setAddressname(String addressname) {
	this.addressname = addressname;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getProvince() {
	return province;
}

public void setProvince(String province) {
	this.province = province;
}

public String getDetailAddress() {
	return detailAddress;
}

public void setDetailAddress(String detailAddress) {
	this.detailAddress = detailAddress;
}

public void setOrderView(OrderMaster orderView) {
	this.orderView = orderView;
}

public String getDateString() {
	return dateString;
}

public void setDateString(String dateString) {
	this.dateString = dateString;
}
   
}
