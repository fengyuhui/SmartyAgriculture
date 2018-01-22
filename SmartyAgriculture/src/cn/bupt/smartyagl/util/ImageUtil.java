package cn.bupt.smartyagl.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.bupt.smartyagl.constant.Constants;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Constant;

/**
 * 
 * <p>
 * Title:ImageUtil
 * </p>
 * <p>
 * Description:图片处理类，包括生成缩略图
 * </p>
 * 
 * @author waiting
 * @date 2016年5月30日 上午10:54:41
 */
public class ImageUtil {
	/**
	 * 单张图片上传
	 * 
	 * @param productPic
	 * @return 返回一个数组记录 保存的路径 ，后缀以及比例
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static String uploadImage(MultipartFile productPic, String fileName)
			throws IllegalStateException, IOException {
		// 上传图片
		if (productPic != null) {
			String fileType = productPic.getContentType();// 文件类型 格式如：image/jpeg
			String[] fileTypeArr = fileType.split("/");//

			// 文件类型是图片
			if (fileTypeArr[0].equals("image")) {
				// 存储新图片的物理路径
				String srcPicPathString = fileName;
				// 新建一个文件夹
				ImageUtil.generateFile(srcPicPathString);
				// 原始文件的名字
				productPic.getOriginalFilename();
				// 生成新的图片名称
				String newFileName = Constants.NEW_FILE_NAME + "."
						+ fileTypeArr[1];
				// 新图片地址
				String newFilePathString = srcPicPathString + "/" + newFileName;
				// 创建新图片
				File newFile = new java.io.File(newFilePathString);
				// 将内存中的数据写入磁盘
				productPic.transferTo(newFile);
				// 生成图片缩略图
				int targetFileWidth = 240;
				String targetFile = srcPicPathString + "/" + targetFileWidth+"."
						+ fileTypeArr[1];
				ImageUtil.AffineTransImage(newFilePathString, targetFile,
						targetFileWidth);
				return newFileName;//返回src.jpg这种格式
			} else {
				System.out.println(fileTypeArr[0]);
				return null;
			}
			
		} else {
			return null;
		}
	}
	public static String uploadSuoLveImage(MultipartFile productPic, String fileName)
			throws IllegalStateException, IOException {
		// 上传图片
		if (productPic != null) {
			String fileType = productPic.getContentType();// 文件类型 格式如：image/jpeg
			String[] fileTypeArr = fileType.split("/");//

			// 文件类型是图片
			if (fileTypeArr[0].equals("image")) {
				// 存储新图片的物理路径
				String srcPicPathString = fileName;
				// 新建一个文件夹
				ImageUtil.generateFile(srcPicPathString);
				// 原始文件的名字
				productPic.getOriginalFilename();
				// 生成新的图片名称
				String newFileName = Constants.NEW_FILE_NAME + "."
						+ fileTypeArr[1];
				// 新图片地址
				String newFilePathString = srcPicPathString + "/" + newFileName;
				// 创建新图片
				File newFile = new java.io.File(newFilePathString);
				// 将内存中的数据写入磁盘
				productPic.transferTo(newFile);
				// 生成图片缩略图
				int targetFileWidth = 240;
				String targetFile = srcPicPathString + "/" + targetFileWidth+"."
						+ fileTypeArr[1];
				ImageUtil.AffineTransImage(newFilePathString, targetFile,
						targetFileWidth);
				return targetFileWidth+"." + fileTypeArr[1];//返回src.jpg这种格式
			} else {
				System.out.println(fileTypeArr[0]);
				return null;
			}
			
		} else {
			return null;
		}
	}

	/**
	 * 生成文件夹
	 * 
	 * @param fileName
	 * @return
	 */
	public static void generateFile(String fileName) {
		System.out.println(fileName);
		File file = new File(fileName);
		file.mkdirs();
	}

	/**
	 * 生成图片缩略图
	 * 
	 * @param srcFile
	 *            源文件的地址
	 * @param targetFile
	 *            目标文件的地址
	 * @param targetFileWidth
	 *            目标图片的宽度
	 */
	public static double AffineTransImage(String srcFile, String targetFile,
			int targetFileWidth) {
	    double Getratio = 0;
		try {
			File fi = new File(srcFile); // 大图文件
			File fo = new File(targetFile);// 将要转换出的小图文件
			int nw = targetFileWidth; // 新生成的图片的宽度
			/*
			 * AffineTransform 类表示 2D 仿射变换，它执行从 2D 坐标到其他 2D
			 * 坐标的线性映射，保留了线的“直线性”和“平行性”。可以使用一系 列平移、缩放、翻转、旋转和剪切来构造仿射变换。
			 */
			AffineTransform transform = new AffineTransform();
			BufferedImage bis = ImageIO.read(fi); // 读取图片
			int w = bis.getWidth(); // 原图的宽度
			int h = bis.getHeight(); // 原图的高度
			// double scale = (double)w/h;
			int nh = (nw * h) / w;
			double sx = (double) nw / w;
			double sy = (double) nh / h;
			Getratio= sx/sy;
//			System.out.println("Getratio1="+Getratio);
			transform.setToScale(sx, sy); // setToScale(double sx, double sy)
											// 将此变换设置为缩放变换。
			/*
			 * AffineTransformOp类使用仿射转换来执行从源图像或 Raster 中 2D 坐标到目标图像或 Raster 中 2D
			 * 坐标的线性映射。所使用的插值类型由构造方法通过 一个 RenderingHints 对象或通过此类中定义的整数插值类型之一来指定。
			 * 如果在构造方法中指定了 RenderingHints 对象，则使用插值提示和呈现
			 * 的质量提示为此操作设置插值类型。要求进行颜色转换时，可以使用颜色 呈现提示和抖动提示。 注意，务必要满足以下约束：源图像与目标图像
			 * 必须不同。 对于 Raster 对象，源图像中的 band 数必须等于目标图像中 的 band 数。
			 */
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_3BYTE_BGR);
			/*
			 * TYPE_3BYTE_BGR 表示一个具有 8 位 RGB 颜色分量的图像， 对应于 Windows 风格的 BGR
			 * 颜色模型，具有用 3 字节存 储的 Blue、Green 和 Red 三种颜色。
			 */
			ato.filter(bis, bid);
//			System.out.println("Getratio2="+Getratio);
			ImageIO.write(bid, "jpeg", fo);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("Getratio3="+Getratio);
        return  Getratio;
	}
	/**
	 * 多图片上传
	 * @param request 
	 * @param rootPath 文件的存储路径
	 * @return
	 */
	public static String batchFileUpLoad(HttpServletRequest request,String rootPath){
		//图片保存的地址
		String filePathJson="[]";
		//记录多张图片存储在服务器上的地址和名称
		List<String> newFiles = new ArrayList<String>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(  
                request.getSession().getServletContext());  
        // 检查form是否有enctype="multipart/form-data"  
        if (multipartResolver.isMultipart(request)) {  
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;  
            Iterator<String> iter = multiRequest.getFileNames(); 
            if(!iter.hasNext())//没有文件上传
            	filePathJson="[]";
            while (iter.hasNext()) {//遍历文件
                // 由CommonsMultipartFile继承而来,拥有上面的方法.  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if (file != null) {
                	 //获取上传图片的集合
//		                    String fileName =  file.getOriginalFilename();  
		                    //获取当前时间
		                    long time = System.currentTimeMillis();
		                    SimpleDateFormat df = new SimpleDateFormat(
									"yyyyMMddHHmmsss");// 设置日期格式
//							System.out.println(df.format(new Date()));// new
																		// Date()为获取当前系统时间
							String fileName = df.format(new Date());
							if ((!fileName.endsWith(".jpg"))
									|| !(fileName.endsWith(".png"))
									|| !(fileName.endsWith(".jpeg"))) {
								if (fileName.indexOf(".") >= 0) {

									fileName = fileName.substring(0,
											fileName.lastIndexOf("."));
								}

								fileName = (fileName + ".jpg"); // 后缀名不符合规则，改名
							}
		                    String path = rootPath +"/"+ fileName.replace(".JPG", ".jpg");//+time+"_"
//		                    newFiles.SaveAs(FilePath + "\\" + System.IO.Path.GetFileName(UserHPF.FileName));
		                    newFiles.add(path);
		                    //创建文件
		                    File localFile = new java.io.File(path);  
		                    try {
								file.transferTo(localFile);
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
		/*                    if( localFile.getName().contains(".jpg") ){//上传图片，生成缩略图
		                		ImageUtil.AffineTransImage( path,path.replace(".jpg", "_scale.jpg"), 360);
		                	}*/
		                }  
		            }  
		            filePathJson = JSONArray.toJSONString(newFiles);
                }
//            }
//        }
        return filePathJson;
	}
	
/*	*//**
	 * 多图片上传
	 * @param request
	 * @param filePathRoot:图片上传的物理路径目录
	 * @return
	 *//*
	public static List<String> batchFileUpload(List<MultipartFile> files,String filePathRoot){
		//记录多张图片存储在服务器上的地址和名称
		List<String> newFiles = new ArrayList<String>();
		for(int i=0;i<files.size();i++){
			//获取一个文件
			MultipartFile file = files.get(i);
			// 文件类型  格式如：image/jpeg
			String fileType = file.getContentType();
			String[] fileTypeArray = fileType.split("/");
			//上传文件的名字
			String name = file.getName();
			if(!file.isEmpty()&&fileTypeArray[0].equals("image")){
				try{
					byte[] bytes = file.getBytes();
					//上传的文件以文件名+时间戳重新命名
					long currentTime = System.currentTimeMillis();
					String newFileName = currentTime + name + "." + fileTypeArray[1];
					//上传文件的地址
					String filePath = filePathRoot + newFileName;
					BufferedOutputStream stream=new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();
					//记录新文件的地址和文件名
					newFiles.add(filePath);	
				}catch(Exception e){
					return null;
				}
			}else{
				//如果文件为空或者不是图片文件，则不处理
				return null;
			}
		}
		return newFiles;
	}
	*/
	public static boolean deleteFile(File file){
		if(file.isFile()){
			file.delete();
			return true;
		}
		else if (file.isDirectory()) {  
			File[] childFilePaths = file.listFiles();  
            for(File childFilePath : childFilePaths){  
                //File childFile=new File(oldPath.getAbsolutePath()+"\\"+childFilePath);  
                deleteFile(childFilePath);  
            }
            file.delete();
            return true;
		}
		return false;
	}
	/**
	 * 删除图片
	 * @param oldPath
	 * @author waiting
	 */
	public static boolean  deleteFileByPath(String pathname) {
//		if (oldPath.isDirectory()) {
//	           System.out.println(oldPath + "是文件夹--");
//	           File[] files = oldPath.listFiles();
//	           for (File file : files) {
//	             deleteFile(file);
//	           }
//	          }else{
//			oldPath.delete();
//	          }	
		boolean flag=false;
		File file=new File(pathname);
		if(file.isFile()){
			file.delete();
			flag=true;
		}
		else{
			//首先得到当前的路径  
            File[] childFilePaths = file.listFiles();  
            if(childFilePaths!=null){
            	for(File childFilePath : childFilePaths){  
                    //File childFile=new File(oldPath.getAbsolutePath()+"\\"+childFilePath);  
                    deleteFile(childFilePath);  
                }  
                file.delete();
            }
            else {
				return true;
			}
		}
		return flag;
	}
//	public static void main(String [] args) {
//		String pathname="G:/installFile/Apache Software Foundation/Tomcat 7.0/webapps/upload/goods/1468833192092_0/src.jpeg";
//		boolean flag=ImageUtil.deleteFile(pathname);
//		System.out.println(flag);
//	}
	
	/**
	 * 单张图片上传
	 * 
	 * @param productPic
	 * @return 返回一个数组记录 保存的路径 ，后缀以及比例
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static String uploadImageTest(MultipartFile productPic, String fileName)
			throws IllegalStateException, IOException {
		// 上传图片
		if (productPic != null) {
			String fileType = productPic.getContentType();// 文件类型 格式如：image/jpeg
			String[] fileTypeArr = fileType.split("/");//
			// 文件类型是图片
			// 存储新图片的物理路径
			String srcPicPathString = fileName;
			// 新建一个文件夹
			ImageUtil.generateFile(srcPicPathString);
			// 原始文件的名字
			productPic.getOriginalFilename();
			// 生成新的图片名称
			String newFileName = Constants.NEW_FILE_NAME + "."
					+ fileTypeArr[1];
			// 新图片地址
			String newFilePathString = srcPicPathString + "/" + newFileName;
			// 创建新图片
			File newFile = new java.io.File(newFilePathString);
			// 将内存中的数据写入磁盘
			productPic.transferTo(newFile);
			// 生成图片缩略图
			int targetFileWidth = 240;
			String targetFile = srcPicPathString + "/" + targetFileWidth+"."
					+ fileTypeArr[1];
			ImageUtil.AffineTransImage(newFilePathString, targetFile,
					targetFileWidth);
			return newFileName;//返回src.jpg这种格式
		} else {
			return null;
		}
	}
}
