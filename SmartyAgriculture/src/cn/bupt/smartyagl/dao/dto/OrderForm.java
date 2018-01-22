package cn.bupt.smartyagl.dao.dto;

public class OrderForm {
	
	private Integer userId;
	
	private Integer addressId;
	
	private String message;
	
	private Integer pickUp;

	private String goodsIds;

	public Integer getUserId() {
		return userId;
	}
	
	

	public Integer getPickUp() {
		return pickUp;
	}



	public void setPickUp(Integer pickUp) {
		this.pickUp = pickUp;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}

	public OrderForm(Integer userId, Integer addressId, String message, String goodsIds, Integer pickUp) {
		super();
		this.userId = userId;
		this.addressId = addressId;
		this.message = message;
		this.goodsIds = goodsIds;
		this.pickUp = pickUp;
	}

	public OrderForm() {
		super();
	}
	
	

	
}
