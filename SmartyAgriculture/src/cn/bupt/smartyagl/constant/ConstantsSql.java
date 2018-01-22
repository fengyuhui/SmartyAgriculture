package cn.bupt.smartyagl.constant;

import java.math.BigDecimal;

/**
 * 数据库常量类
 * 
 * @author jm
 * 
 */
public class ConstantsSql {
	//地址表
	public static final Integer isDefaultAddress = 2;//默认地址
	
	public static final Integer notDefaultAddress = 1;//非默认地址
	
	public static final Boolean MENU_STATUS_SHOW=true;//后台菜单显示 1
	
	//商品分类
	/**
	 * 父类商品id
	 */
	public static final Integer parentGoodsTypeId = -1;
	
	//订单表   
	/**
	 * 1未付款 
	 */
	public static final Integer SatusNoPay = 1;
	/**
	 * 2未发货
	 */
	public static final Integer SatusNoSend = 2;
	/**
	 *  3已发货
	 */
	public static final Integer SatusSending = 3;
	/**
	 * 4已收货
	 */
	public static final Integer SatusReceive = 4;
	/**
	 * 5退货中
	 */
	public static final Integer SatusRebackIng = 5;
	/**
	 * 6已上退货
	 */
	public static final Integer SatusReback = 6;
	
	
	
	//商品状态标志
	public static final int ONSALE=0;//上架在售
	public static final int OFFTHESHELVES=1;//下架
		
	//商品销售状态
	public static final int NOMALPRODUCT=0;//普通商品
	public static final int SPECIALPRICE=1;//特价
	public static final int LIMITATIONTIME=2;//限时
	public static final int NEWGOODS=3;//新品
	public static final int HOTGOODS=4;//热卖
	
	
	//设备类型
	/**
	 *	设备类型 Android
	 */
	public static final String DeviceType_Android = "1";
	/**
	 *	设备类型IOS
	 */
	public static final String DeviceType_IOS = "2";
	/**
	 *	设备类型 HTML
	 */
	public static final String DeviceType_HTML = "3";
	/**
	 *	设备类型 WeiXin
	 */
	public static final String DeviceType_WeiXin = "4";
	
	/**
	 * 消息表，针对所有用户信息
	 */
	public static final Integer AllUser = -1;
	
	//推送
	/**
	 * 特价产品
	 */
	public static final Integer PUSH_SPECAIL_OFFER = 1;
	
	/**
	 * 2限时抢购
	 */
	public static final Integer PUSH_FlASH_SALE = 2;
	
	/**
	 * 3商品发货
	 */
	public static final Integer PUSH_SENDGOODS = 3;
	/**
	 * 热卖产品
	 */
	public static final Integer PUSH_HOTSALE = 4;
	/**
	 * 新品上市
	 */
	public static final Integer PUSH_NEWSALE = 6;
	/**
	 * 异地登录
	 */
	public static final Integer PUSH_REMOTELOGIN = 7;
	/**
	 * 退货成功
	 */
	public static final Integer PUSH_RETURNGOODS_Sucess = 8;
	/**
	 * 退货失败
	 */
	public static final Integer PUSH_RETURNGOODS_Fail = 9;
	/**
	 * 评论回复
	 */
	public static final Integer PUSH_COMMON = 10;
	//消息读取状态
	/**
	 * 消息未读
	 */
	public static final Integer MESSAGE_NO_READ = 0;
	/**
	 * 消息已读
	 */
	public static final Integer MESSAGE_HAS_READ = 1;
	
	/**
	 * 支付表相关参数
	 */
	public static final Integer PayStatus_NoPay = 1; //未付款
	public static final Integer PayStatus_HasPay = 2; //已付款
	public static final Integer PayStatus_FinishPay = 2; //完成付款
	public static final Integer PayStatus_ReturnPay = 3; //退货
	public static final Integer PayStatus_CardPaySome = 8; //购物卡已支付部分订单
	
	/**
	 * 支付类型
	 */
	public static final Integer PayType_AliPay = 1; //阿里支付
	public static final Integer PayType_AliPayAndCard = 4; //阿里支付加购物卡
	public static final Integer PayType_WeixinPay = 2;//微信支付
	public static final Integer PayTyep_WeixinPayAndCard = 5;//微信+购物卡
	/**
	 * 商品类型
	 */
	public static final Boolean GoodType_Display = true;//前端显示商品种类
	public static final Boolean GoodType_NoDisplay = false;//前端不显示商品种类
	
	
	//权限管理员表
	/**
	 * 超级管理员
	 */
	public static final Integer Auth_Admin = 1;
	/**
	 * 审查员  
	 */
	public static final Integer Auth_Check = 2;
	/**
	 * 操作员
	 */
	public static final Integer Auth_Operate = 3;  
	
	
	/**
	 * 审核状态（多张表可用）
	 */
	/**
	 * 正常
	 */
	public static final int Audit_Finish = 0;
	/**
	 * 发布未审核
	 */
	public static final int Audit_Publish_NoAuth = 1;
	/**
	 * 草稿
	 */
	public static final int Audit_Draft = 2;
	/**
	 * 正常有草稿
	 */
	public static final int Audit_Finish_hasDraft = 3;
	/**
	 * 删除待审核
	 */
	public static final int Audit_WaitDelete = 4;
	
	/**
	 * 土地审核状态
	 */
	/**
	 * 正常
	 */
	public static final int BLOCK_AUDIT_FINISH = 0;
	/**
	 * 草稿
	 */
	public static final int BLOCK_AUDIT_DRAFT = 1;
	
//	//农场信息状态
//	public static final int NOMAL=0;//正常
//	public static final int PUBLISHNOTCHECK=1;//发布未审核
//	public static final int DRAFT=2;//草稿
//	public static final int NOMALDRAFT=3;//正常草稿
//	public static final int DELETENOTCHECK=4;//删除待审核
	
	/**
	 * 置顶状态
	 */
	public static final Integer  Top_isTop = 1;
	public static final Integer  Top_noTop = 0;
	
	/**
	 * 农场信息
	 */
	public static final Integer FarmType_OurFarm = 1;//我们农场
	public static final Integer FarmType_Technology = 2;//生态技术
	public static final Integer FarmType_Activity = 3;//休闲活动
	public static final Integer FarmType_Knowledge = 1;//有机生活
	public static final Integer FarmType_Healthy = 2;//营养健康
	public static final Integer FarmType_Food = 3;//美食烹饪
	
	
	//评论的状态
	public static final Integer COMMENT_NORMAL=0;//正常
	public static final Integer COMMENT_APPLY_HIDDEN=1;//申请隐藏
	public static final Integer COMMENT_HIDDEN=2;//已隐藏
	
	/**
	 * 商品类型
	 */
	public static final Integer GoodsType_CardId = 44;
	
	
	/**
	 * 支付类型
	 */
	public static final Integer Pay_Goods = 1;
	
	public static final Integer Pay_Visiter = 2;
	/**
	 * 运费类型
	 */
	public static final String Local_City = "郑州市";
	public static final String Local_Province = "河南省";

	public static Integer local_city = 0;
	public static Integer local_pro = 1;
	public static Integer other_pro = 2;
}
