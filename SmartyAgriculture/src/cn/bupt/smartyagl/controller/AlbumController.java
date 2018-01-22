package cn.bupt.smartyagl.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.entity.autogenerate.Album;
import cn.bupt.smartyagl.service.IAlbumService;
import cn.bupt.smartyagl.util.ImageUtil;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/** 
 * @author  zxy
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

@Controller
@RequestMapping("/album")
public class AlbumController extends BaseController {
    
    @Autowired
    IAlbumService albumService;
    
    int pageSize=Constants.PAGESIZE;//，每一页的大小
    int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
    
    /**
     * 显示相册列表
     * @author zxy
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{types}")
    public ModelAndView Index(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,Album albumSelective,HttpServletRequest request) 
            		throws UnsupportedEncodingException{
        if ("prvious".equals(types)) {
            if( currentPage > 1 ){//第一页不能往前翻页
                currentPage--;
            }
        } else if ("next".equals(types)) {
            currentPage++;
        } else if ("first".equals(types)) {
            currentPage = 1;
        } else if ("last".equals(types)) {
            currentPage = allPages;
        } else {
            currentPage = Integer.parseInt(types);
        }
        ModelAndView modelAndView = new ModelAndView(Constants.Album_INDEX);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<Album> albumList = albumService.getAlbumList();
        modelAndView.addObject("albumList",albumList);
        //总页数
        allPages = page.getPages();
        modelAndView.addObject("allPages", allPages);
        // 当前页码
        currentPage = page.getPageNum();
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("resultMsg", request.getParameter("resultMsg"));
        return modelAndView;
    }
    
    /**
     * 显示相册权限列表
     * @author zxy
     */
    @RequestMapping(value="/judge/{allPages}/{currentPage}/{types}")
    public ModelAndView judgeList(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage, @PathVariable(value="types") String types,Album albumSelective,HttpServletRequest request) {
        if ("prvious".equals(types)) {
            if( currentPage > 1 ){//第一页不能往前翻页
                currentPage--;
            }
        } else if ("next".equals(types)) {
            currentPage++;
        } else if ("first".equals(types)) {
            currentPage = 1;
        } else if ("last".equals(types)) {
            currentPage = allPages;
        } else {
            currentPage = Integer.parseInt(types);
        }
        ModelAndView modelAndView = new ModelAndView(Constants.Album_AuthList);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "");
        List<Album> albumList = albumService.getAlbumDraftList();
        modelAndView.addObject("albumList",albumList);
        //总页数
        allPages = page.getPages();
        modelAndView.addObject("allPages", allPages);
        // 当前页码
        currentPage = page.getPageNum();
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("resultMsg", request.getParameter("resultMsg"));
        return modelAndView;
    }

    /**
     * 添加相册图片
     * @param request
     * @param album
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
    @RequestMapping("/add")
    public ModelAndView addAlbum(HttpServletRequest request,Album album) throws IllegalStateException, IOException{
        
        ModelAndView mv = new ModelAndView(Constants.Album_ADD);
//        List<GoodsTypeModel> GoodsTypeModel=albumService.getDisplayGoodsType();
//        mv.addObject("parentList",GoodsTypeModel);
        mv.addObject("album", album);
        return mv;
    }
    
    /**
     * 添加相册 图片
     * @param request
     * @param album
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
    @RequestMapping("/addPost")
    public ModelAndView addAlbumPost(HttpServletRequest request, @RequestParam MultipartFile file,Album album) throws IllegalStateException, IOException{
        ModelAndView mv = new ModelAndView(Constants.Album_ADD);
        //添加
        if(album != null && album.getPhotoName() != null){
            // 上传文件路径
            Long time = System.currentTimeMillis();
            String fileName = time.toString();
            //存在数据库文件路径
            String tempString = Constants.ALBUM_TYPE_STRING + fileName ;
            String path = request.getSession().getServletContext().getRealPath("")+"/../"
                            + Constants.FILE_PATH+tempString;
            //上传图片
            tempString += "/" + ImageUtil.uploadImage(file, path);
            album.setPictures(tempString);
            boolean rst = albumService.addAlbum(album);
            if(rst){
                mv.addObject("resultMsg", "添加图片成功");
            }else{
                mv.addObject("resultMsg", "添加图片失败");
            }
            //跳转到首页
            RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
            mv.setView(redirectView);
        }
        return mv;
    }
    
//    /**
//     * 添加相册图片
//     */
//    @RequestMapping("/addPost")
//    public ModelAndView addAlbumPost(Album album,MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException{
//        ModelAndView mv = new ModelAndView(Constants.Album_ADD);
//        if(album != null && album.getPhotoName() != null){
//            // 上传文件路径
//            Long time = System.currentTimeMillis();
//            String fileName = time.toString();
//            String tempString = Constants.ALBUM_TYPE_STRING + fileName ;
//            String path = request.getSession().getServletContext().getRealPath("")+"/../"
//                            + Constants.FILE_PATH+tempString;
//             // json格式化
//    //          ObjectMapper mapper = new ObjectMapper();
//    //          String filePathJsonString = mapper.writeValueAsString(tempString);
//    //          System.out.println(filePathJsonString);
//                //上传图片
//            ImageUtil.uploadImage(file, path);
//    //        album.setIsdisplay(true);
//            album.setPictures(tempString);
//            boolean flag=albumService.addAlbum(album);
//            if (flag) {
////                ModelAndView modelAndView = this.getTypeList(0, 1, "prvious", 0,0);
//                mv.addObject("resultMsg", "添加图片成功");
//            } else {
////                ModelAndView modelAndView = this.addAlbum();
//                mv.addObject("resultMsg", "添加图片失败");
//            }
//          //跳转到首页
//          RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
//          mv.setView(redirectView);
//        }
//        return mv;
//    }
    
     /**
     * 发布草稿
     * @param request
     * @param album
     * @return
     */
    @RequestMapping("/update")
    public ModelAndView update(HttpServletRequest request,Album album){
        album = albumService.findAlbum(album.getId());
        ModelAndView mv = new ModelAndView(Constants.Album_EDIT);
        mv.addObject("album", album);
        return mv;
    }
    
    /**
     * 查看
     * @param request
     * @param album
     * @return
     */
    @RequestMapping("/find")
    public ModelAndView find(HttpServletRequest request,Album album){
        album = albumService.findAlbum(album.getId());
        ModelAndView mv = new ModelAndView(Constants.Album_FIND);
     album.setPictures( getImageFileUtil.getSrcFileImg( album.getPictures()) );
        mv.addObject("album", album);
        return mv;
    }
    
//    /**
//     * 查看农场信息详情
//     * 
//     * @param id
//     * @return
//     * @author zxy
//     */
//    @RequestMapping("/find")
//    public ModelAndView find( Integer id,Album album,
//            HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView(Constants.Album_FIND);
//        Album albumList = albumService.findAlbum(id);
//        modelAndView.addObject("albumList", albumList);
//        
//        if(albumList.getPictures()!=null){
//            // 处理农场图片
//            List<String> pictureList = new ArrayList<String>();
//            try {
//                pictureList = JsonConvert.getFarmMessagePicture(albumList.getPictures(),
//                        request);
//            } catch (JsonParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (JsonMappingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            modelAndView.addObject("pictureList", pictureList);
//        }
//        
//        // 状态
//        int status = albumList.getStatus();
//        String statusString = null;
//        switch (status) {
//        case ConstantsSql.Audit_Finish:
//            statusString = "正常";
//            break;
//        case ConstantsSql.Audit_Publish_NoAuth:
//            statusString = "发布待审核";
//            break;
//        case ConstantsSql.Audit_Draft:
//            statusString = "草稿";
//            break;
//        case ConstantsSql.Audit_Finish_hasDraft:
//            statusString = "正常草稿";
//            break;
//        case ConstantsSql.Audit_WaitDelete:
//            statusString = "删除待审核";
//            break;
//        }
//        //farmMessageList.setCurrentMessage( farmMessageList.getCurrentMessage() );
////      modelAndView.addObject("currentMessage", farmMessageList.getCurrentMessage());
//        modelAndView.addObject("status", statusString);
//        modelAndView.addObject("albumList", albumList);
//        return modelAndView;
//    }
    
     /**
     * 发布草稿
     * @param request
     * @param album
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
    @RequestMapping("/updatePost")
    public ModelAndView updatePost(HttpServletRequest request,@RequestParam MultipartFile file,Album album) throws IllegalStateException, IOException{
        ModelAndView mv = new ModelAndView(Constants.Album_EDIT);
        if(album != null && album.getId() != null){
            // 上传文件路径
            Long time = System.currentTimeMillis();
            String fileName = time.toString();
            //存在数据库文件路径
            String tempString = Constants.ALBUM_TYPE_STRING + fileName ;
            String path = request.getSession().getServletContext().getRealPath("")+"/../"
                    + Constants.FILE_PATH+tempString;
            //上传图片
            tempString += "/" + ImageUtil.uploadImage(file, path) ;
            album.setPictures(tempString);
            boolean rst = albumService.updateAlbum(album);
            if(rst){
                mv.addObject("resultMsg", "修改相册成功");
            }else{
                mv.addObject("resultMsg", "修改相册失败");
            }
            //跳转到首页
            RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
            mv.setView(redirectView);
        }
        return mv;
    }
    
    //删除图片
    @RequestMapping("/delete")
    public ModelAndView deleteAlbum(HttpServletRequest request,Integer id){
        ModelAndView mv = new ModelAndView("");
        boolean rs = albumService.deleteAlbum(id);
        if(rs){
            mv.addObject("resultMsg", "删除成功");
        }else{
            mv.addObject("resultMsg", "删除失败");
        }
        RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
        mv.setView(redirectView);
        return mv;
    }
    
    @RequestMapping("/judgePublish")
    public ModelAndView judgePublish(HttpServletRequest request,Integer id){
        ModelAndView mv = new ModelAndView("");
        boolean rs = albumService.verifyAddAlbum(id);
        if(rs){
            mv.addObject("resultMsg", "审核发布成功");
        }else{
            mv.addObject("resultMsg", "审核发布失败");
        }
        RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
        mv.setView(redirectView);
        return mv;
    }
    
    @RequestMapping("/judgeDraft")
    public ModelAndView judgeDraft(HttpServletRequest request,Integer id){
        ModelAndView mv = new ModelAndView("");
        boolean rs = albumService.verifyChangeAlbum(id);
        if(rs){
            mv.addObject("resultMsg", "审核草稿成功");
        }else{
            mv.addObject("resultMsg", "审核草稿失败");
        }
        RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
        mv.setView(redirectView);
        return mv;
    }
    
    @RequestMapping("/top")
    @ResponseBody
    public Object top(HttpServletRequest request,Integer id,Integer topId){
        Map<String, String>map = new HashMap<String, String>();
        boolean rs = albumService.changeTop(id, topId);
        if(rs){
            map.put("resultMsg", "修改成功");
        }else{
            map.put("resultMsg", "修改失败");
        }
        return map;
    }


/**
 * 提交删除申请，并非真的删除
 * @param request
 * @param id
 * @param topId
 * @return
 */
@RequestMapping("/deleteRequest")
public ModelAndView deleteRequest(HttpServletRequest request,Integer id){
    ModelAndView mv = new ModelAndView("");
    Map<String, String>map = new HashMap<String, String>();
    Album fa = new Album();
    fa.setId( id );
    fa.setStatus( ConstantsSql.Audit_WaitDelete );
    boolean rs = albumService.deletePostAlbum(fa);
    if(rs){
        mv.addObject("resultMsg", "申请删除成功");
    }else{
        mv.addObject("resultMsg", "申请删除失败");
    }
    RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
    mv.setView(redirectView);
    return mv;
}

/**
 * 提交删除申请，并非真的删除
 * @param request
 * @param id
 * @param topId
 * @return
 */
@RequestMapping("/rebackDeleteRequest")
public ModelAndView rebackDeleteRequest(HttpServletRequest request,Integer id){
    ModelAndView mv = new ModelAndView("");
    Map<String, String>map = new HashMap<String, String>();
    Album fa = new Album();
    fa.setId( id );
    fa.setStatus( ConstantsSql.Audit_Finish );
    boolean rs = albumService.deletePostAlbum(fa);
    if(rs){
        mv.addObject("resultMsg", "撤销删除成功");
    }else{
        mv.addObject("resultMsg", "撤销删除失败");
    }
    RedirectView redirectView = new RedirectView( "index/0/1/prvious" );
    mv.setView(redirectView);
    return mv;
}
}
