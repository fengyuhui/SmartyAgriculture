package cn.bupt.smartyagl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.FarmPhotosMapper;
import cn.bupt.smartyagl.entity.autogenerate.FarmPhotos;
import cn.bupt.smartyagl.entity.autogenerate.FarmPhotosExample;
import cn.bupt.smartyagl.service.IFarmPhotosService;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-13 上午10:46:24 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
public class FarmPhotosServiceImpl implements IFarmPhotosService{
	@Autowired
	FarmPhotosMapper farmPhotosMapper;
	
	@Override
	public List<FarmPhotos> getFarmPhotos() {
		FarmPhotosExample fe = new FarmPhotosExample();
		List<FarmPhotos> list = farmPhotosMapper.selectByExample( fe );
		return list;
	}

}
