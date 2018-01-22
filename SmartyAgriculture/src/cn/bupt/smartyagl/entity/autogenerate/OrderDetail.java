package cn.bupt.smartyagl.entity.autogenerate;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDetail {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderDetail.detailId
     *
     * @mbggenerated
     */
    private Integer detailId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderDetail.orderId
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderDetail.goodsId
     *
     * @mbggenerated
     */
    private Integer goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderDetail.num
     *
     * @mbggenerated
     */
    private Integer num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderDetail.price
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderDetail.buyTime
     *
     * @mbggenerated
     */
    private Date buyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderDetail.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderDetail.detailId
     *
     * @return the value of orderDetail.detailId
     *
     * @mbggenerated
     */
    public Integer getDetailId() {
        return detailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderDetail.detailId
     *
     * @param detailId the value for orderDetail.detailId
     *
     * @mbggenerated
     */
    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderDetail.orderId
     *
     * @return the value of orderDetail.orderId
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderDetail.orderId
     *
     * @param orderId the value for orderDetail.orderId
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderDetail.goodsId
     *
     * @return the value of orderDetail.goodsId
     *
     * @mbggenerated
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderDetail.goodsId
     *
     * @param goodsId the value for orderDetail.goodsId
     *
     * @mbggenerated
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderDetail.num
     *
     * @return the value of orderDetail.num
     *
     * @mbggenerated
     */
    public Integer getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderDetail.num
     *
     * @param num the value for orderDetail.num
     *
     * @mbggenerated
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderDetail.price
     *
     * @return the value of orderDetail.price
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderDetail.price
     *
     * @param price the value for orderDetail.price
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderDetail.buyTime
     *
     * @return the value of orderDetail.buyTime
     *
     * @mbggenerated
     */
    public Date getBuyTime() {
        return buyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderDetail.buyTime
     *
     * @param buyTime the value for orderDetail.buyTime
     *
     * @mbggenerated
     */
    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderDetail.name
     *
     * @return the value of orderDetail.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderDetail.name
     *
     * @param name the value for orderDetail.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}