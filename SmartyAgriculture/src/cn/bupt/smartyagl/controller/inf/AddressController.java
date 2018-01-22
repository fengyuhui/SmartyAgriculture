package cn.bupt.smartyagl.controller.inf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.controller.BaseController;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.service.IAddressService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.impl.AddressServiceImpl;
import cn.bupt.smartyagl.util.ImageUtil;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 下午2:26:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/address")
public class AddressController extends AppBaseController{
	@Autowired
	IAddressService addressService;
	
	//添加地址
	@RequestMapping("/addAddress")
	@ResponseBody
	public Object addAddress(HttpServletRequest request,Address address){
		address.getId();
		Integer userId = (Integer) request.getAttribute("userId");
		address.setUserId(userId);
		address.setIsUsed(true);
		boolean rst =  addressService.addAddress(address);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(rst){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("addressId", address.getId());
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("添加地址成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("添加地址失败");
		}
		return nau;
	}
	
	//未登录添加地址
		@RequestMapping("/addAddressWithoutLog")
		@ResponseBody
		public Object addAddresswithoutLog(HttpServletRequest request,Address address){
			address.getId();
//			Integer userId = (Integer) request.getAttribute("userId");
//			address.setUserId(userId);
			address.setIsUsed(true);
			boolean rst =  addressService.addAddress(address);
			NetDataAccessUtil nau = new NetDataAccessUtil();
			if(rst){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("addressId", address.getId());
				nau.setContent(map);
				nau.setResult(1);
				nau.setResultDesp("添加地址成功");
			}else{
				nau.setResult(0 );
				nau.setResultDesp("添加地址失败");
			}
			return nau;
		}
	
	//删除地址
	@RequestMapping("/deleteAddress")
	@ResponseBody
	public Object deleteAddress(HttpServletRequest request,Integer addressId){
		Integer userId = (Integer) request.getAttribute("userId");
		boolean rst =  addressService.deleteAddress(addressId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(rst){
			nau.setResult(1);
			nau.setResultDesp("删除地址成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("删除地址失败");
		}
		return nau;
	}
	
	//获取地址列表
	@RequestMapping("/getAddressList")
	@ResponseBody
	public Object getAddressList(HttpServletRequest request,Address address,int pageSize,int pageNum){
		//分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize, "");
		Integer userId = (Integer) request.getAttribute("userId");
		address.setUserId(userId);
		List<Address> rst =  addressService.getAddressList(address);
		List<Address> res = new ArrayList<Address>();
		for(Address addr:rst)
			if(addr.getIsUsed())
				res.add(addr);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setContent(res);
		nau.setResult(1);
		nau.setResultDesp("获取地址列表成功");
		return nau;
	}
	
	//根据用户id获取地址详情
	@RequestMapping("/getAddressDetail")
	@ResponseBody
	public Object getAddressDetail(HttpServletRequest request,Integer addressId){
		Address rst =  addressService.getAddressDetail(addressId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setContent(rst);
		nau.setResult(1);
		nau.setResultDesp("获取地址详情成功");
		return nau;
	}
	
	//修改地址信息
	@RequestMapping("/changeAddress")
	@ResponseBody
	public Object changeAddress(HttpServletRequest request,Address address){
		Integer userId = (Integer) request.getAttribute("userId");
		address.setUserId(userId);
		address.setIsUsed(true);
		boolean rst =  addressService.updateAddressDefault(address);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(rst){
			nau.setResult(1);
			nau.setResultDesp("修改地址成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("修改地址失败");
		}
		return nau;
	}
	
	/**
     * 上传文件测试用
     * @param request
     * @param Address
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
  	@RequestMapping("/addPost")
  	@ResponseBody
  	public Object addPost(HttpServletRequest request,@RequestParam MultipartFile file) throws IllegalStateException, IOException{
  		NetDataAccessUtil nau = new NetDataAccessUtil();
  		//添加
		if(file!= null && file.getSize()>0){
			// 上传文件路径
			Long time = System.currentTimeMillis();
			String fileName = ""+time;
			//存在数据库文件路径
			String tempString = "log/" + fileName ;
		    String path = request.getSession().getServletContext().getRealPath("")+"/../"
							+ Constants.FILE_PATH + tempString;
	  		 	//上传图片
	  		tempString += "/" + ImageUtil.uploadImageTest(file, path) ;
	  		nau.setContent( getImageFileUtil.getSrcFileImg(tempString) );
	  		nau.setResult(1);
	  		nau.setResultDesp("上传成功");
		}
  		return nau;
  	}
}
