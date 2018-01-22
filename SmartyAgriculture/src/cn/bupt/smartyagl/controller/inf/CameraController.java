package cn.bupt.smartyagl.controller.inf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.entity.autogenerate.Camera;
import cn.bupt.smartyagl.entity.autogenerate.Advertise;
import cn.bupt.smartyagl.service.ICameraService;
import cn.bupt.smartyagl.service.impl.CameraServiceImpl;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-22 下午2:39:22 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/camera")
public class CameraController extends AppBaseController{
	@Autowired
	ICameraService cameraService;
	
	//获取摄像头
	@RequestMapping("/getCameraList")
	@ResponseBody
	public Object getAdList(HttpServletRequest request,Integer pageNum,Integer pageSize){
		//分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize, "is_top desc,id desc");
		//TODO 这里是写死的
		List<Camera> cameras = cameraService.getCameraList(); 
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setContent(cameras);
		nau.setResult(1);
		nau.setResultDesp("获取摄像头列表成功");
		return nau;
	}
}
