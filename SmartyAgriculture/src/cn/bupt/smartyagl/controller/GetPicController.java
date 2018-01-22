package cn.bupt.smartyagl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.service.ICommentService;
import cn.bupt.smartyagl.service.IGetPicService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * java抓取网络图片
 * @author zxy
 *
 */
@Controller
@RequestMapping("/getPic")
public class GetPicController {

    // 地址
    private static final String URL = "http://www.csdn.net";
    // 编码
    private static final String ECODING = "UTF-8";
    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";

  @RequestMapping("/getFirPic")
  @ResponseBody
    public static NetDataAccessUtil getFirPic(String[] args) throws Exception {
      NetDataAccessUtil nau = new NetDataAccessUtil();
      try{
      List<String> listFirImgSrc = new ArrayList<String>();
      List<String> listFirImgUrl = new ArrayList<String>();
        GetPicController cm = new GetPicController(); //
        //获得html文本内容
        String HTML = cm.getHTML(URL);
        //获取图片标签
        List<String> imgUrl = cm.getImageUrl(HTML);
        //获取图片src地址
        List<String> imgSrc = cm.getImageSrc(imgUrl);
        //下载图片
        cm.Download(imgSrc);
        listFirImgUrl.add(imgUrl.get(0));
        listFirImgSrc.add(imgSrc.get(0));
        if(listFirImgUrl!=null){
            System.out.println("获取url成功");
            System.out.println(listFirImgUrl);
            nau.setContent(listFirImgUrl);
            nau.setResult(1);
            nau.setResultDesp("获取图片url成功");
            }
//        if(listFirImgSrc!=null){
//            System.out.println("获取src成功");
//            nau.setContent(listFirImgSrc);
//            nau.setResult(1);
//            nau.setResultDesp("获取图片成功");
//            }  
        }catch(Exception e) {
            e.printStackTrace();
            nau.setResult(0);
            nau.setResultDesp("获取图片失败");           
        }
        return nau;
    }
    
    
    /***
     * 获取HTML内容
     * 
     * @param url
     * @return
     * @throws Exception
     */
    private String getHTML(String url) throws Exception {
        URL uri = new URL(url);
        URLConnection connection = uri.openConnection();
        InputStream in = connection.getInputStream();
        byte[] buf = new byte[1024];
        int length = 0;
        StringBuffer sb = new StringBuffer();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, ECODING));
        }
        in.close();
        return sb.toString();
    }

    /***
     * 获取ImageUrl地址
     * 
     * @param HTML
     * @return
     */
    private List<String> getImageUrl(String HTML) {
        
            
            Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
            List<String> listImgUrl = new ArrayList<String>();
            
            while (matcher.find()) {
                listImgUrl.add(matcher.group());
            }
            if(listImgUrl!=null){
                System.out.println("获取成功");
            }
            
         return listImgUrl;
        }
    //      //取出第一张图片
    //        for(listFirImgUrl gd : listImgUrl){
    //            gd.setPicture( JsonConvert.getOnePicture(gd.getPicture(),request) );
    //        }
           

    /***
     * 获取ImageSrc地址
     * 
     * @param listImageUrl
     * @return
     */
    private List<String> getImageSrc(List<String> listImageUrl) {
        List<String> listImgSrc = new ArrayList<String>();
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        if(listImgSrc!=null){
            System.out.println("获取成功");
        }
        
        return listImgSrc;
    }

    /***
     * 下载图片
     * 
     * @param listImgSrc
     */
    private void Download(List<String> listImgSrc) {
        try {
            for (String url : listImgSrc) {
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                URL uri = new URL(url);
                InputStream in = uri.openStream();
                FileOutputStream fo = new FileOutputStream(new File(imageName));
                byte[] buf = new byte[1024];
                int length = 0;
                System.out.println("开始下载:" + url);
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }
                in.close();
                fo.close();
                System.out.println(imageName + "下载完成");
            }
        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }
  
//public class GetPicController {
//    @Autowired
//    static
//    IGetPicService getPicService;
//    
//    @RequestMapping("/getFirPic")
//    @ResponseBody
//    public static NetDataAccessUtil getImageSrc(String htmlCode) {
//        
//        List<String> imageSrcList = new ArrayList<String>();
//        NetDataAccessUtil nau = new NetDataAccessUtil();
//        imageSrcList = getPicService.getImageSrc(htmlCode);
//        if (imageSrcList == null) {
////            nau.setContent(jsonString);
//            nau.setResult(0);
//            nau.setResultDesp("getToken失败");
//        } else {
//            Map<String, Object> map = new HashMap<String, Object>();
////            map.put("expires_in", accessTokenbean.getExpires_in());
////            map.put("access_token", accessTokenbean.getAccess_token());
////            nau.setContent(map);
////          nau.setContent(redis.get(Constants.USERAPPID));
//            nau.setResult(1);
//            nau.setResultDesp("getToken成功");
//        }
//        return nau;
} 
