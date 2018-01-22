package cn.bupt.smartyagl.dao.autogenerate;

import cn.bupt.smartyagl.entity.autogenerate.OrderDetail;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int countByExample(OrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int deleteByExample(OrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer detailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int insert(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int insertSelective(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    List<OrderDetail> selectByExample(OrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    OrderDetail selectByPrimaryKey(Integer detailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderDetail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OrderDetail record);
}