package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
import cn.bupt.smartyagl.entity.autogenerate.FarmPhotos;

/**
 * 获得图片列表
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-13 上午10:45:03 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IFarmPhotosService {
	/**
	 * 获得图片列表
	 * @author wjm
	 */
	public List<FarmPhotos> getFarmPhotos( );
}
