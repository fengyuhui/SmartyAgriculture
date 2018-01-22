package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.Camera;

/**
 * 摄像头功能 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-22 下午2:26:55 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface ICameraService {
	/**
	 * 添加摄像头
	 * @param camera
	 * @return
	 */
	public boolean addCamera(Camera camera);
	
	/**
	 * 删除摄像头
	 */
	public boolean deleteCamera(Integer cameraId);
	
	/**
	 * 获取摄像头列表
	 */
	public List<Camera> getCameraList();
	
	/**
	 * 查看摄像头信息
	 */
	public Camera findCamera(Integer id);
	
	/**
	 * 修改置顶
	 * @param cameraId
	 * @param topId
	 * @return
	 */
	public boolean changeTop(Integer cameraId, Integer topId);
}
