package cn.bupt.smartyagl.service.impl;

import java.util.List;

import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.AddressMapper;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.entity.autogenerate.AddressExample;
import cn.bupt.smartyagl.entity.autogenerate.AddressExample.Criteria;
import cn.bupt.smartyagl.service.IAddressService;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 下午2:23:41 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
public class AddressServiceImpl implements IAddressService{
	@Autowired
	AddressMapper addressMapper;
	
	@Override
	public boolean addAddress(Address address) {
		Address defaultAddress = this.getDefaultAddress(address.getUserId());//获取默认地址
		if( defaultAddress  == null){//没有设置默认地址，则更为默认地址
			address.setIsDefaultAddress( ConstantsSql.isDefaultAddress );
		}else{//如果已有默认地址
			if( address.getIsDefaultAddress().equals( ConstantsSql.isDefaultAddress) ){//如果此时要设置默认地址，将原有地址置为非空
				defaultAddress.setIsDefaultAddress( ConstantsSql.notDefaultAddress );
				this.updateAddressDefault(defaultAddress);
			}
		}
	    int rs = addressMapper.insert(address);
	    System.out.println(address.getId());
	    if(rs>0){
	    	return true;
	    }
		return false;
	}

	@Override
	public boolean deleteAddress(Integer addressId) {
		Address addr = addressMapper.selectByPrimaryKey(addressId);
		addr.setIsUsed(false);
		int rs = addressMapper.updateByPrimaryKeySelective(addr);
		if(rs>0){
	    	return true;
	    }
		return false;
	}

	@Override
	public List<Address> getAddressList(Address address) {
		AddressExample ae = new AddressExample();
		Criteria cta = ae.createCriteria();
		cta.andUserIdEqualTo(address.getUserId());
		if(address.getIsDefaultAddress()!=null){
			cta.andIsDefaultAddressEqualTo(address.getIsDefaultAddress());
		}
		List<Address> list = addressMapper.selectByExample(ae);
		return list;
	}

	@Override
	public Address getAddressDetail(Integer addressId) {
		Address address =  addressMapper.selectByPrimaryKey(addressId);
		return address;
	}

	@Override
	public boolean updateAddressDefault(Address address) {
		try{
			//如设为默认地址，则将已有默认地址置为非默认地址
			if(address.getIsDefaultAddress() == ConstantsSql.isDefaultAddress){
				Address tmp_ad = this.getDefaultAddress(address.getUserId());
				//将原有地址改为非默认地址
				if(tmp_ad != null && tmp_ad.getId() != address.getId()){
					tmp_ad.setIsDefaultAddress( ConstantsSql.notDefaultAddress );
					addressMapper.updateByPrimaryKey(tmp_ad);
				}
			}
			int rs = addressMapper.updateByPrimaryKeySelective(address);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Address getDefaultAddress(Integer userId) {
		if(userId == null)
			return null;
		AddressExample ae = new AddressExample();
		Criteria cta = ae.createCriteria();
		cta.andUserIdEqualTo(userId);
		cta.andIsDefaultAddressEqualTo(ConstantsSql.isDefaultAddress);
		List<Address> list = addressMapper.selectByExample(ae);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}



}
