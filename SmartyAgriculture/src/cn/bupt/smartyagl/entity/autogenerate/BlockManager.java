package cn.bupt.smartyagl.entity.autogenerate;

import java.util.Date;

public class BlockManager {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blockManager.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blockManager.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blockManager.passwd
     *
     * @mbggenerated
     */
    private String passwd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blockManager.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column blockManager.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blockManager.id
     *
     * @return the value of blockManager.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blockManager.id
     *
     * @param id the value for blockManager.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blockManager.name
     *
     * @return the value of blockManager.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blockManager.name
     *
     * @param name the value for blockManager.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blockManager.passwd
     *
     * @return the value of blockManager.passwd
     *
     * @mbggenerated
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blockManager.passwd
     *
     * @param passwd the value for blockManager.passwd
     *
     * @mbggenerated
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blockManager.status
     *
     * @return the value of blockManager.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blockManager.status
     *
     * @param status the value for blockManager.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column blockManager.createTime
     *
     * @return the value of blockManager.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column blockManager.createTime
     *
     * @param createTime the value for blockManager.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}