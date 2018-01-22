package cn.bupt.smartyagl.dao.autogenerate;

import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.GoodsListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    int countByExample(GoodsListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    int deleteByExample(GoodsListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    int insert(GoodsList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    int insertSelective(GoodsList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    List<GoodsList> selectByExampleWithBLOBs(GoodsListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    List<GoodsList> selectByExample(GoodsListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") GoodsList record, @Param("example") GoodsListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") GoodsList record, @Param("example") GoodsListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GoodsList
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") GoodsList record, @Param("example") GoodsListExample example);
}