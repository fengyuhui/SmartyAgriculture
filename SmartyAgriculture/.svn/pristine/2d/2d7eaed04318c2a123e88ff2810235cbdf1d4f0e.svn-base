package cn.bupt.smartyagl.util.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.ss.formula.functions.T;

//import testExport.ExportExcel;
//import testExport.Student;


import com.hp.hpl.sparta.xpath.ThisNodeTest;

public class ExportExcelUtil {

	public static void  exportExcel(String path, HttpServletResponse response,ExportExcel<T> ex,String []headers) {
		//(new ExportExcel()).test();
//		ExportExcel exportExcelClass=new ExportExcel();
//		exportExcelClass.exportExcel(dataset, out);
//		try {
//			OutputStream out = new FileOutputStream(path);	
//			ex.exportExcel(headers, dataset, out);
//			out.close();
//			JOptionPane.showMessageDialog(null, "导出成功!");
//			System.out.println("excel导出成功！");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		//download(path, response);
	}
	public static void download(String path, HttpServletResponse response) {
		try {
			
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			// 清空response
			// 设置response的Header
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes(), "iso-8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			byte[] buffer = new byte[1024*8];
			int len;
			while((len = fis.read(buffer)) != -1 ) {
				toClient.write(buffer, 0, len);
			}
			
			fis.close();
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static String getDeskPath() {
		 File desktopDir = FileSystemView.getFileSystemView()
    			 .getHomeDirectory();
    	String desktopPath = desktopDir.getAbsolutePath();
    	return desktopPath;
	}
}
