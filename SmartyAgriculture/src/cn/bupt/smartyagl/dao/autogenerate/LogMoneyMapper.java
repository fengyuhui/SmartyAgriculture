package cn.bupt.smartyagl.dao.autogenerate;

import cn.bupt.smartyagl.entity.autogenerate.LogMoney;
import cn.bupt.smartyagl.entity.autogenerate.LogMoneyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogMoneyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int countByExample(LogMoneyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int deleteByExample(LogMoneyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int insert(LogMoney record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int insertSelective(LogMoney record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    List<LogMoney> selectByExample(LogMoneyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    LogMoney selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LogMoney record, @Param("example") LogMoneyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LogMoney record, @Param("example") LogMoneyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LogMoney record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logMoney
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LogMoney record);
}