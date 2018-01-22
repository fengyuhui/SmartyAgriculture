package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.Address;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 上午10:46:45 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IAddressService {
	public boolean addAddress(Address address);//添加地址
	
	public boolean deleteAddress(Integer addressId);//删除地址
	
	public List<Address> getAddressList(Address address);//获取地址列表
	
	public Address getAddressDetail(Integer addressId);//根据用户id获取地址详情
	
	public boolean updateAddressDefault(Address address);//更改地址信息
	
	public Address getDefaultAddress(Integer userId);//获取默认地址
}
