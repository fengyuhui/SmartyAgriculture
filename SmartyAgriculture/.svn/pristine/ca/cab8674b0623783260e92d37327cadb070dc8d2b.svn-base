package cn.bupt.smartyagl.dao.individuation;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import cn.bupt.smartyagl.model.UserStatisticsModel;
import cn.bupt.smartyagl.model.inputStatisticsModel;
import cn.bupt.smartyagl.model.totalSalesModel;

/**
 *<p>Title:OrderStatisticsMapper</p>
 *<p>Description:订单统计</p>
 * @author waiting
 *@date 2016年8月30日 上午9:27:24
 */
public interface OrderStatisticsMapper {
	
	/**
	 * 会员统计
	 * @param status 订单状态
	 * @param timeFlag 标志 1：一周；2：一月；3：一年
	 * @return
	 */
    List<UserStatisticsModel> UserStatistics(OrderQueryVo vo
    		);
    /**
     * 总销售额统计
     * @param status
     * @param timeFlag
     * @return
     */
    List<totalSalesModel> totalSalesStatistics(OrderQueryVo vo);
    /**
     * 单个商品销售额统计
     * @param goodsId
     * @param status
     * @param timeFlag
     * @return
     */
    List<totalSalesModel> productStatistics(OrderQueryVo vo);
    /**
     * 单个商品进货统计
     * @param vo
     * @return
     */
    List<inputStatisticsModel> productInputStatics(OrderQueryVo vo);
    /**
     * 一级类型商品销售额统计
     * @param typeIdParent
     * @param status
     * @param timeFlag
     * @return
     */
	List<totalSalesModel> typeStatistics(OrderQueryVo queryVo);
}
