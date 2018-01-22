package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bupt.smartyagl.service.IGetPicService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

public class GetPicServiceImpl implements IGetPicService{

    @Override
    public List<String> getImageSrc(String htmlCode) {
        try{List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
     
             src="http://pic.gicpic.cn/html/gettyback/images1/20120301/dd85987a2.jpg";
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);
     
        }
        return imageSrcList;
        
        }catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
}