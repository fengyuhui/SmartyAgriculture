package cn.bupt.smartyagl.dao.autogenerate;

import cn.bupt.smartyagl.entity.autogenerate.GeneratedDetails;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GeneratedDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int countByExample(GeneratedDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int deleteByExample(GeneratedDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int insert(GeneratedDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int insertSelective(GeneratedDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    List<GeneratedDetails> selectByExample(GeneratedDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    GeneratedDetails selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int updateByExampleSelective(@Param("record") GeneratedDetails record, @Param("example") GeneratedDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int updateByExample(@Param("record") GeneratedDetails record, @Param("example") GeneratedDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int updateByPrimaryKeySelective(GeneratedDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table generatedDetails
     *
     * @mbggenerated Fri Oct 21 09:19:07 CST 2016
     */
    int updateByPrimaryKey(GeneratedDetails record);
    /**
     * 一次插入一批生成购物卡记录
     * @param records
     * @return
     */
    int insertList(List<GeneratedDetails> records);
}