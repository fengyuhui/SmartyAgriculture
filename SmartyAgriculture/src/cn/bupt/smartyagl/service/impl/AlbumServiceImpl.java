package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.AlbumMapper;
import cn.bupt.smartyagl.entity.autogenerate.AlbumExample;
import cn.bupt.smartyagl.entity.autogenerate.Album;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
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
@Service
public class AlbumServiceImpl implements IAlbumService{
	@Autowired
	AlbumMapper albumMapper;
	
//	@Override
//	public List<Album> getAlbum() {
//	    AlbumExample fe = new AlbumExample();
//		List<Album> list = albumMapper.selectByExample( fe );
//		return list;
//	}
	
	
    @Override
    public boolean addAlbum(Album album) {
         album.setCreatTime(new Date());
         album.setRatio( (float)ImageUtil.AffineTransImage(null, null, 0));
         album.setStatus(ConstantsSql.Audit_Publish_NoAuth);
         int rs = albumMapper.insert(album);
            if(rs>0){
                return true;
            }
            return false;
    }

    @Override
    public boolean deleteAlbum(Integer id) {
        //如果是草稿，改变原数据参数
        Album ad = albumMapper.selectByPrimaryKey(id);
        if( ad.getParentId() != null ){//删除的是草稿，修改原数据为无草稿状态
            Album ad_par = albumMapper.selectByPrimaryKey( ad.getParentId() );
            ad_par.setStatus( ConstantsSql.Audit_Finish );
            albumMapper.updateByPrimaryKey(ad_par);
        }
        int rs = albumMapper.deleteByPrimaryKey(id);
        AlbumExample ae = new AlbumExample();
        //删除草稿
        ae.createCriteria().andParentIdEqualTo(id);
        albumMapper.deleteByExample(ae);
        
        if(rs>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Album> getAlbumList() {
        AlbumExample ae = new AlbumExample();
        ae.setOrderByClause("top desc,creatTime desc");
        List<Integer> auditList = new ArrayList<Integer>();
        auditList.add(ConstantsSql.Audit_Finish);
        auditList.add(ConstantsSql.Audit_Finish_hasDraft);
        ae.createCriteria().andStatusIn(auditList);
        List<Album> list = albumMapper.selectByExample(ae);
        return list;
    }

    @Override
    public boolean updateAlbum(Album album) {
        try{
            Integer id = album.getId();
            //int rs = albumMapper.updateByPrimaryKeySelective(album);
            //添加草稿文章数据
            album.setCreatTime(new Date());
            album.setStatus(ConstantsSql.Audit_Draft);
            //标记父文章标题
            album.setParentId(id);
            album.setId(null);
            int rs = albumMapper.insert(album);
            
            //更改原文章有草稿状态
            this.verifyAlbumStatus(id, ConstantsSql.Audit_Finish_hasDraft);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean verifyAddAlbum(Integer id) {
        return this.verifyAlbumStatus(id, ConstantsSql.Audit_Finish);
    }

    @Override
    public boolean verifyChangeAlbum(Integer id) {
        Album ad_draft = albumMapper.selectByPrimaryKey( id );
        if( ad_draft.getParentId() == null || ad_draft.getStatus() != ConstantsSql.Audit_Draft){
            return false;
        }
        //更新原数据
        Album ad_yum  = albumMapper.selectByPrimaryKey( ad_draft.getParentId() );
        ad_yum.setPictures( ad_draft.getPictures() );
        ad_yum.setPhotoName( ad_draft.getPhotoName() );
        albumMapper.updateByPrimaryKeySelective(ad_yum);
        //删除草稿
        albumMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public List<Album> getAlbumDraftList() {
        AlbumExample ae = new AlbumExample();
        List<Integer> auditList = new ArrayList<Integer>();
        auditList.add(ConstantsSql.Audit_Draft);
        auditList.add(ConstantsSql.Audit_Publish_NoAuth);
        auditList.add(ConstantsSql.Audit_WaitDelete);
        ae.createCriteria().andStatusIn(auditList);
        List<Album> list = albumMapper.selectByExample(ae);
        return list;
    }

    @Override
    public boolean verifyAlbumStatus(Integer id, Integer status) {
        Album album = new Album();
        album.setId(id);
        album.setStatus(status);
        int i = albumMapper.updateByPrimaryKeySelective(album);
        if(i>0)
            return true;
        return false;
    }

    @Override
    public boolean changeTop(Integer id, Integer top) {
        Album album = new Album();
        album.setId(id);
        album.setTop(top);
        int i = albumMapper.updateByPrimaryKeySelective(album);
        if(i>0)
            return true;
        return false;
    }

    @Override
    public Album findAlbum(Integer id) {
        return albumMapper.selectByPrimaryKey( id );
    }

    @Override
    public boolean deletePostAlbum(Album album) {
        int i = albumMapper.updateByPrimaryKeySelective(album);
        if( i >0 ){
            return true;
        }
        return false;
    }
    
}

