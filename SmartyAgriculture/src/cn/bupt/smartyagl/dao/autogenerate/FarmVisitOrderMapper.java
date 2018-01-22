package cn.bupt.smartyagl.dao.autogenerate;

import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FarmVisitOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int countByExample(FarmVisitOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int deleteByExample(FarmVisitOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int insert(FarmVisitOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int insertSelective(FarmVisitOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    List<FarmVisitOrder> selectByExample(FarmVisitOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    FarmVisitOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FarmVisitOrder record, @Param("example") FarmVisitOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FarmVisitOrder record, @Param("example") FarmVisitOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FarmVisitOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmVisitOrder
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FarmVisitOrder record);
}