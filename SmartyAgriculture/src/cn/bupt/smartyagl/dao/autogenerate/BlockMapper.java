package cn.bupt.smartyagl.dao.autogenerate;

import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlockMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int countByExample(BlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int deleteByExample(BlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int insert(Block record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int insertSelective(Block record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    List<Block> selectByExample(BlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    Block selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Block record, @Param("example") BlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Block record, @Param("example") BlockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Block record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table block
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Block record);
}