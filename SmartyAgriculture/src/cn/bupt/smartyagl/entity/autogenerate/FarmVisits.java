package cn.bupt.smartyagl.entity.autogenerate;

public class FarmVisits {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmVisits.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmVisits.price
     *
     * @mbggenerated
     */
    private Double price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmVisits.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmVisits.status
     *
     * @mbggenerated
     */
    private Boolean status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmVisits.id
     *
     * @return the value of farmVisits.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmVisits.id
     *
     * @param id the value for farmVisits.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmVisits.price
     *
     * @return the value of farmVisits.price
     *
     * @mbggenerated
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmVisits.price
     *
     * @param price the value for farmVisits.price
     *
     * @mbggenerated
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmVisits.title
     *
     * @return the value of farmVisits.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmVisits.title
     *
     * @param title the value for farmVisits.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmVisits.status
     *
     * @return the value of farmVisits.status
     *
     * @mbggenerated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmVisits.status
     *
     * @param status the value for farmVisits.status
     *
     * @mbggenerated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}