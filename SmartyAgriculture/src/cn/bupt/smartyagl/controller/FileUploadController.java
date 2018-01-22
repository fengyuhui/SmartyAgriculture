package cn.bupt.smartyagl.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller("FileUploadController")
@RequestMapping("/fileUpload")
public class FileUploadController {
	
	// 处理文件上传二
	@RequestMapping(value = "/fileUpload2", method = RequestMethod.POST)
	@ResponseBody
	public String fileUpload2(HttpServletRequest request)
			throws IllegalStateException, IOException {
		// 设置上下方文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		// 检查form是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {

				// 由CommonsMultipartFile继承而来,拥有上面的方法.
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					file.getName();
					String fileName = "demoUpload" + file.getOriginalFilename();
					String prepath=request.getSession().getServletContext().getRealPath("/");
					System.out.println("地址1" +prepath);
					String rearpath = "upload/portrait/" + fileName;
					System.out.println("地址2" +rearpath);
					String path=prepath+rearpath;
					System.out.println("文件地址" +path);
					File localFile = new File(path);
					file.transferTo(localFile);
					break;
				}

			}
		}
		return "dataSuccess";
	}
}