package cn.bupt.smartyagl.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.entity.CommentAndReply;
import cn.bupt.smartyagl.entity.autogenerate.Comment;
import cn.bupt.smartyagl.entity.autogenerate.CommentView;

/** 
 * @author  zw E-mail:793089768@qq.com 
 * @date 创建时间：2016-5-13 上午10:46:45 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface ICommentService {
	public boolean addComments(Comment comment);//添加评论
	
	public boolean deleteComment(Integer commentId);//删除评论
	
	public List<CommentAndReply> getCommentList(Integer goodsId,Integer level);//获取某个商品的评论列表
	
	public List<Integer> countComment(Integer goodsId);//获取商品不同类型的评论数目
	
	public List<Integer> countMyComment(Integer userId);//获取我的的评论数目
	
	public List<CommentView> personComments(Integer userId,Integer level);//获取个人评论列表
	
	public CommentAndReply getCommentDetail(Integer commentId);//获取商品的评论详情
	
	public boolean updateViewNum(Integer commentId);//更新评论的浏览数
	/**
	 * 后台获取评论列表
	 * @return
	 * @author waiting
	 */
	public void getCommentViewsList(ModelAndView modelAndView,
			int pageNumber, int pageSize);
	/**
	 * 后台 获取评论审核列表
	 * @param modelAndView
	 * @param pageNumber
	 * @param pageSize
	 * @author waiting
	 */
	public void getCommentAuditViewsList(ModelAndView modelAndView,
			int pageNumber, int pageSize);
	/**
	 * 后台 查看评论详情
	 * @param id
	 * @return
	 * @author waiting
	 */
	public CommentView getCommentDetail(int id);
	/**
	 * 修改评论状态
	 * @param id
	 * @param status
	 * @return
	 * @author waiting
	 */
	public boolean updateCommentStatus(int id,int status);
	
	
}
