package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.CommentMapper;
import cn.bupt.smartyagl.dao.autogenerate.CommentViewMapper;
import cn.bupt.smartyagl.entity.CommentAndReply;
import cn.bupt.smartyagl.entity.autogenerate.CommentExample;
import cn.bupt.smartyagl.entity.autogenerate.Comment;
import cn.bupt.smartyagl.entity.autogenerate.CommentView;
import cn.bupt.smartyagl.entity.autogenerate.CommentViewExample;
import cn.bupt.smartyagl.entity.autogenerate.CommentViewExample.Criteria;
import cn.bupt.smartyagl.service.ICommentService;
import cn.bupt.smartyagl.util.BeanUtil;

/**
 * @author jm E-mail:740869614@qq.com
 * @date 创建时间：2016-5-13 下午2:23:41
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Service
public class CommentServiceImpl implements ICommentService {
	@Autowired
	CommentMapper commentMapper;
	@Autowired
	CommentViewMapper commentViewMapper;

	@Override
	public boolean addComments(Comment comment) {
		// TODO Auto-generated method stub
		Integer res = commentMapper.insert(comment);
		if (res > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		// 删除评论
		Integer res = commentMapper.deleteByPrimaryKey(commentId);
		// 判断是否存在回复
		int num = this.getReplyNum(commentId);
		if (num > 0) {
			// 删除评论所有的回复
			CommentExample ce = new CommentExample();
			cn.bupt.smartyagl.entity.autogenerate.CommentExample.Criteria criteria = ce
					.createCriteria();
			criteria.andParentMessageIdEqualTo(commentId);
			Integer ret = commentMapper.deleteByExample(ce);
			if (ret > 0) {
				return true;
			} else {
				return false;
			}
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取评论列表 goodsId:商品id,level：评论类别
	 */
	@Override
	public List<CommentAndReply> getCommentList(Integer goodsId, Integer level) {
		// TODO Auto-generated method stub
		CommentViewExample cve = new CommentViewExample();
		cve.setOrderByClause("createTime desc");
		Criteria criteria = cve.createCriteria();
		criteria.andGoodsIdIsNotNull();
		criteria.andGoodsIdEqualTo(goodsId);
		// 根据level,判断获取评论的类型（1全部 2有图 3好 4中 5差）
		switch (level) {
		case 1:// 全部评论
			break;
		case 2:// 有图的评论
			criteria.andPictureListNotEqualTo("[]");
			break;
		case 3:// 好评（4-5）
			criteria.andLevelBetween(4, 5);
			break;
		case 4:// 中评（2-3）
			criteria.andLevelBetween(2, 3);
			break;
		case 5:// 差评（1）
			criteria.andLevelEqualTo(1);
			break;
		}
		List<CommentView> commentList = commentViewMapper.selectByExample(cve);

		List<CommentAndReply> commentListWithReply = new ArrayList<CommentAndReply>();
		for (CommentView car : commentList) {
			CommentAndReply commentAndReply = new CommentAndReply();
			try {
				BeanUtil.fatherToChild(car, commentAndReply);
				// 获取回复的数量
				int replyNum = getReplyNum(car.getId());
				commentAndReply.setReplyNum(replyNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			commentListWithReply.add(commentAndReply);
		}
		return commentListWithReply;
	}

	/**
	 * 获取商品的不同类别评论的数量 goodsId：商品id
	 */
	@Override
	public List<Integer> countComment(Integer goodsId) {
		// TODO Auto-generated method stub
		List<Integer> commentNumber = new ArrayList<Integer>();
		// 根据level,判断获取评论的类型（1全部 2有图 3好 4中 5差）
		// 全部评论
		int totalNumber = this.countCommentNum(goodsId, 1);
		if (-1 == totalNumber) {
			return null;
		}
		commentNumber.add(totalNumber);

		// 有图的评论
		int picCommentNum = this.countCommentNum(goodsId, 2);
		if (-1 == picCommentNum) {
			return null;
		}
		commentNumber.add(picCommentNum);

		// 好评（4-5）
		int goodCommentNum = this.countCommentNum(goodsId, 3);
		if (-1 == goodCommentNum) {
			return null;
		}
		commentNumber.add(goodCommentNum);

		// 中评（2-3）
		int middleCommentNum = this.countCommentNum(goodsId, 4);
		if (-1 == middleCommentNum) {
			return null;
		}
		commentNumber.add(middleCommentNum);

		// 差评（1）
		int badCommentNum = this.countCommentNum(goodsId, 5);
		if (-1 == badCommentNum) {
			return null;
		}
		commentNumber.add(badCommentNum);

		return commentNumber;
	}

	/**
	 * 获取个人的不同类别评论的数量 userId：商品id
	 */
	@Override
	public List<Integer> countMyComment(Integer userId) {
		// TODO Auto-generated method stub
		List<Integer> commentNumber = new ArrayList<Integer>();
		// 根据level,判断获取评论的类型（1全部 2有图 3好 4中 5差）
		// 全部评论
		int totalNumber = this.countMyCommentNum(userId, 1);
		if (-1 == totalNumber) {
			return null;
		}
		commentNumber.add(totalNumber);

		// 有图的评论
		int picCommentNum = this.countMyCommentNum(userId, 2);
		if (-1 == picCommentNum) {
			return null;
		}
		commentNumber.add(picCommentNum);

		// 好评（4-5）
		int goodCommentNum = this.countMyCommentNum(userId, 3);
		if (-1 == goodCommentNum) {
			return null;
		}
		commentNumber.add(goodCommentNum);

		// 中评（2-3）
		int middleCommentNum = this.countMyCommentNum(userId, 4);
		if (-1 == middleCommentNum) {
			return null;
		}
		commentNumber.add(middleCommentNum);

		// 差评（1）
		int badCommentNum = this.countMyCommentNum(userId, 5);
		if (-1 == badCommentNum) {
			return null;
		}
		commentNumber.add(badCommentNum);

		return commentNumber;
	}

	/**
	 * 查询不同评论类别的数量
	 * 
	 * @param goodsId
	 *            商品id
	 * @param level
	 *            评论类型
	 * @return 成功：评论数量，失败：-1
	 */
	private int countCommentNum(Integer goodsId, Integer level) {
		int number = -1;
		CommentViewExample cve = new CommentViewExample();
		Criteria criteria = cve.createCriteria();
		criteria.andGoodsIdIsNotNull();
		criteria.andGoodsIdEqualTo(goodsId);
		criteria.andParentMessageIdEqualTo(-1);
		// 根据level,判断获取评论的类型（1全部 2有图 3好 4中 5差）
		switch (level) {
		case 1:// 全部评论
			number = commentViewMapper.countByExample(cve);
			break;
		case 2:// 有图的评论
			criteria.andPictureListNotEqualTo("[]");
			number = commentViewMapper.countByExample(cve);
			break;
		case 3:// 好评（4-5）
			criteria.andLevelBetween(4, 5);
			number = commentViewMapper.countByExample(cve);
			break;
		case 4:// 中评（2-3）
			criteria.andLevelBetween(2, 3);
			number = commentViewMapper.countByExample(cve);
			break;
		case 5:// 差评（1）
			criteria.andLevelEqualTo(1);
			number = commentViewMapper.countByExample(cve);
			break;
		}
		return number;
	}

	/**
	 * 查询个人的不同评论类别的数量
	 * 
	 * @param userId
	 *            用户id
	 * @param level
	 *            评论类型
	 * @return 成功：评论数量，失败：-1
	 */
	private int countMyCommentNum(Integer userId, Integer level) {
		int number = -1;
		CommentViewExample cve = new CommentViewExample();
		Criteria criteria = cve.createCriteria();
		criteria.andGoodsIdIsNotNull();
		criteria.andUserIdEqualTo(userId);
		criteria.andParentMessageIdEqualTo(-1);
		// 根据level,判断获取评论的类型（1全部 2有图 3好 4中 5差）
		switch (level) {
		case 1:// 全部评论
			number = commentViewMapper.countByExample(cve);
			break;
		case 2:// 有图的评论
			criteria.andPictureListNotEqualTo("[]");
			number = commentViewMapper.countByExample(cve);
			break;
		case 3:// 好评（4-5）
			criteria.andLevelBetween(4, 5);
			number = commentViewMapper.countByExample(cve);
			break;
		case 4:// 中评（2-3）
			criteria.andLevelBetween(2, 3);
			number = commentViewMapper.countByExample(cve);
			break;
		case 5:// 差评（1）
			criteria.andLevelEqualTo(1);
			number = commentViewMapper.countByExample(cve);
			break;
		}
		return number;
	}

	/**
	 * 获取商品某一评价的回复条数
	 * 
	 * @param goodsId
	 * @return
	 */
	private int getReplyNum(Integer commentId) {
		int number = 0;
		CommentViewExample cve = new CommentViewExample();
		Criteria criteria = cve.createCriteria();
		criteria.andParentMessageIdEqualTo(commentId);
		number = commentViewMapper.countByExample(cve);
		return number;
	}

	/**
	 * 查询个人的商品评论列表 只查找对商品的原始评论，不包含对评论的回复
	 * userId:用户id,level:评论类别（1：全部，2：有图，3：好评，4：中评，5：差评）
	 */

	@Override
	public List<CommentView> personComments(Integer userId, Integer level) {
		// TODO Auto-generated method stub
		CommentViewExample cve = new CommentViewExample();
		cve.setOrderByClause("createTime desc");
		Criteria criteria = cve.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andParentMessageIdEqualTo(-1);
		// 根据level,判断获取评论的类型（1全部 2有图 3好 4中 5差）
		switch (level) {
		case 1:// 全部评论
			break;
		case 2:// 有图的评论
			criteria.andPictureListNotEqualTo("[]");
			break;
		case 3:// 好评（4-5）
			criteria.andLevelBetween(4, 5);
			break;
		case 4:// 中评（2-3）
			criteria.andLevelBetween(2, 3);
			break;
		case 5:// 差评（1）
			criteria.andLevelEqualTo(1);
			break;
		}
		List<CommentView> commentList = commentViewMapper.selectByExample(cve);
		return commentList;
	}

	/**
	 * 获取评论详情
	 * 
	 * @param commentId
	 *            评论id
	 */
	@Override
	public CommentAndReply getCommentDetail(Integer commentId) {
		// TODO Auto-generated method stub
		CommentAndReply commentAndReply = new CommentAndReply();
		CommentViewExample cve = new CommentViewExample();
		Criteria criteria = cve.createCriteria();
		criteria.andGoodsIdIsNotNull();
		criteria.andIdEqualTo(commentId);
		List<CommentView> commentList = commentViewMapper.selectByExample(cve);
		if (commentList.isEmpty()) {
			return null;
		}
		// 取出评论
		CommentView commentDetail = commentList.get(0);
		try {
			BeanUtil.fatherToChild(commentDetail, commentAndReply);
			// 获取回复的数量
			int replyNum = getReplyNum(commentId);
			commentAndReply.setReplyNum(replyNum);
			// 获取回复的详情
			List<CommentView> commentReplyList = this
					.getCommentReplyList(commentId);
			commentAndReply.setReplyList(commentReplyList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentAndReply;
	}

	/**
	 * 获取回复列表
	 * 
	 * @param commentId
	 * @return
	 */
	private List<CommentView> getCommentReplyList(Integer commentId) {
		CommentViewExample cve = new CommentViewExample();
		Criteria criteria = cve.createCriteria();
		criteria.andIdIsNotNull();
		criteria.andParentMessageIdEqualTo(commentId);
		List<CommentView> commentList = commentViewMapper.selectByExample(cve);
		return commentList;
	}

	@Override
	public boolean updateViewNum(Integer commentId) {
		// TODO Auto-generated method stub
		CommentExample ce = new CommentExample();
		cn.bupt.smartyagl.entity.autogenerate.CommentExample.Criteria criteria = ce
				.createCriteria();
		criteria.andIdEqualTo(commentId);
		Comment Comment = commentMapper.selectByPrimaryKey(commentId);
		if (Comment.equals(null)) {
			return false;
		}
		Comment.setViewNum(Comment.getViewNum() + 1);
		Integer ret = commentMapper.updateByPrimaryKey(Comment);
		if (ret > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void getCommentViewsList(ModelAndView modelAndView,
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		CommentViewExample commentViewExample = new CommentViewExample();
		Criteria criteria = commentViewExample.createCriteria();
		criteria.andCommentStatusNotEqualTo(ConstantsSql.COMMENT_HIDDEN);
		// 分页
		Page page = PageHelper.startPage(pageNumber, pageSize, "createTime desc");
		List<CommentView> commentList=commentViewMapper.selectByExample(commentViewExample);
		modelAndView.addObject("commentList", commentList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
	}

	@Override
	public CommentView getCommentDetail(int id) {
		// TODO Auto-generated method stub
		CommentViewExample commentViewExample=new CommentViewExample();
		Criteria criteria=commentViewExample.createCriteria();
		criteria.andIdEqualTo(id);
		List<CommentView> commentViews=commentViewMapper.selectByExample(commentViewExample);
		return commentViews.get(0);
	}

	@Override
	public boolean updateCommentStatus(int id, int status) {
		// TODO Auto-generated method stub
		Comment comment=commentMapper.selectByPrimaryKey(id);
		comment.setStatus(status);
		int i=commentMapper.updateByPrimaryKey(comment);
		if(i!=0){
			return true;
		}
		else{
			return false;
		}
		
	}

	@Override
	public void getCommentAuditViewsList(ModelAndView modelAndView,
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		CommentViewExample commentViewExample = new CommentViewExample();
		Criteria criteria = commentViewExample.createCriteria();
		criteria.andCommentStatusEqualTo(ConstantsSql.COMMENT_APPLY_HIDDEN);
		// 分页
		Page page = PageHelper.startPage(pageNumber, pageSize, "createTime desc");
		List<CommentView> commentList=commentViewMapper.selectByExample(commentViewExample);
		modelAndView.addObject("commentList", commentList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
	}


}
