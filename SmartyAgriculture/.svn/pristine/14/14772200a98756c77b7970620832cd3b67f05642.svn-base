package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.Album;
import cn.bupt.smartyagl.entity.autogenerate.Album;

/**
 * 获得图片列表
 * @author zxy
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IAlbumService {
    /**
     * 获得指定图片
     * @param Album
     * @return
     */
    public Album findAlbum(Integer id);
    
    /**
     * 添加图片
     * @param Album
     * @return
     */
    public boolean addAlbum(Album album);
    
    /**
     * 更改图片
     * @param Album
     * @return
     */
    public boolean updateAlbum(Album album);
    
    /**
     * 删除图片
     * @param id
     * @return
     */
    public boolean deleteAlbum(Integer id);
    
    /**
     * 审核添加图片
     * @param Album
     * @return
     */
    public boolean verifyAddAlbum(Integer id);
    
    /**
     * 审核修改图片
     * @param Album
     * @return
     */
    public boolean verifyChangeAlbum(Integer id);
    
    /**
     * 获取图片（已审核）
     * @return
     */
    public List<Album> getAlbumList();
    
    /**
     * 获取图片（待审核）
     * @return
     */
    public List<Album> getAlbumDraftList();
    
    /**
     * 更改图片置顶状态
     * @return
     */
    public boolean changeTop(Integer id,Integer status);
    
    /**
     * 更新图片状态
     */
    public boolean verifyAlbumStatus(Integer id,Integer top);

    boolean deletePostAlbum(Album album);
}
