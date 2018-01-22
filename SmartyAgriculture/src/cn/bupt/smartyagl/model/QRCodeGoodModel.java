package cn.bupt.smartyagl.model;

import java.util.Date;

public class QRCodeGoodModel {

	private String name;
	private String title;
	private String picture;
	private Double vipPrice;
	private Date endtime;
	private Double price;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private Integer goodsDetailId;
	private String url;
	public Integer getGoodsDetailId() {
		return goodsDetailId;
	}
	public void setGoodsDetailId(Integer goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}
	public String getName() {
		return name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	private String qrcodeUrl;
}
