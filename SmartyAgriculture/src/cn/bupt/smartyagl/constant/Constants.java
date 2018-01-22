package cn.bupt.smartyagl.constant;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;

public class Constants {
    /**
     * 极光推送AppKey
     */
    public static final String JPUSH_APPKEY = "b359adca4a7fc85a3e9fd5ed";//Appkey
    public static final String JPUSH_SECRETE = "2e393016df8a3ddac5ed4bf0";//AppSecret
    //分页每一页的大小
    public static final int PAGESIZE=10;
    //分页每一页的大小
    public static final int PAGESIZE_SMALL=5;
    //文件的物理路径
    public static final String FILE_PATH="upload/";
    //文件的虚拟路径
    public static final String FILE_VIRTUAL_PATH="/smartyAgl";
    //新文件的文件名
    public static final String NEW_FILE_NAME="src";
    //商品类别图片
    public static final String GOODS_TYPE_PICTURE_STRING="goodsType/";
    //轮播图类别图片
    public static final String ADVERTISE_TYPE_STRING="advertise/";
    
    //农场信息类别图片
    public static final String FARMMESSAGE_TYPE_STRING="farmMessage/";
    
    //休闲美食类别图片
    public static final String COOKLEISURE_TYPE_STRING="cookLeisure/";
    
    //相册类别图片
    public static final String ALBUM_TYPE_STRING="album/";
    
    public static final String PUBLIC_LOGIN = "public/login";//登陆界面
    public static final String REGISTER_PAGE = "/public/register";//注册界面
    public static final String ERROR_PAGE = "public/noPermission";//失败页面
    public static final String PUBLIC_HOME = "public/home";//首页
    //会员管理模块
    public static final String USER_INDEX="user/userIndex";//会员列表
    public static final String USER_DETAIL="user/userDetail";//查看会员信息界面
    public static final String USER_EDIT="user/userEdit";//会员信息编辑界面
    //商品管理模块
    public static final String PRODUCT_ADD="product/addProduct";//添加商品
    public static final String PRODUCT_INDEX="product/productIndex";//后台商品列表
    public static final String PRODUCT_DETAIL="product/productDetail";//商品详情界面列表
    public static final String PRODUCT_EDIT="product/productEdit";//商品编辑界面
    public static final String PRODUCT_AUDIT_INDEX="product/productAudit";//商品审核列表
    public static final String PRODUCT_AUDIT_DETAIL="product/productAuditDetail";//商品审核信息详情
    public static final String PRODUCT_AUDIT_DETA="product/productAuditDetail";
    public static final String PRODUCT_INPUT="product/inputProduct";//商品进货界面
    public static final String PRODUCT_FREIGHT="product/freightEdit";//商品运费编辑
    public static final String PRODUCT_FREIGHT_ADD="product/freightAdd";//商品运费编辑
    
    
    public static final String COMMENT_INDEX="product/commentIndex";//评论列表
    public static final String COMMENT_DETAIL="product/commentDetail";//评论详情
    public static final String COMMENT_AUDIT_INDEX="product/commentAuditIndex";//评论审核列表
    public static final String COMMENT_AUDIT_DETAIL="product/commentAuditDetail";//评论审核详情
    //产品审核
    public static final String TYPE_REVIEWED = "type/unreviewedTypes";
    public static final String PRODUCT_AUDIT_INPUT="product/productInputAudit";//评论审核详情
    
    
    //产品类别
    public static final String TYPE_INDEX="type/typeIndex";//产品类别列表
    public static final String TYPE_ADD="type/addType";//添加产品类别
    public static final String TYPE_EDIT="type/typeEdit";//添加产品类别
    //订单管理模块
    public static final String ORDER_LIST="order/orderList";//订单列表
    public static final String ORDER_FindOrder="order/orderList";//订单查询页
    public static final String ORDER_FindOrderDetail="order/orderDetail";//订单查询详情页
    public static final String ORDERDETAILLIST="order/orderListDetail";//订单详情页
    //H5端 商品详情
    public static final String GOODS_DETAIL ="interface/goodsDetail";//商品详情
    public static final String VISIT_FARM ="tbAlbum";//农场相关H5界面
    //土地管理模块
    public static final String BLOCK_INDEX ="block/blockIndex";//地块列表
    public static final String BLOCK_ADD="block/addBlock";//添加地块
    public static final String BLOCK_DETAIL="block/blockDetail";//地块详情
    public static final String BLOCK_EDIT="block/editBlock";//编辑地块
    
    public static final String BLOCK_AUDIT_INDEX="block/blockAudit";//地块审核主页
    public static final String BLOCK_AUDIT_DETAIL="block/blockAuditDetail";//地块审核详情
    //土地管理员模块
    public static final String BLOCK_MANAGER_ADD="blockManager/addBlockAuth";//添加地块管理员
    public static final String BLOCK_MANAGER_INDEX="blockManager/authBlockIndex";//地块管理员主页
    public static final String BLOCK_MANAGER_EDIT="blockManager/editBlockAuth";//编辑地块管理员
    public static final String BLOCK_MANAGER_DETAIL="blockManager/findBlockAuth";//地库管理与详情
    public static final String BLOCK_MANAGER_DELETE="blockManager/deleteBlockAuth";//删除区域管理员
    //土地管理员后台管理模块
    public static final String BLOCK_MANAGER_SYSTEM_ERROR_PAGE = "blockManagerSystem/noPermission";//失败页面
    
    public static final String BLOCK_MANAGER_SYSTEM_PUBLIC_LOGIN="blockManagerSystem/login";//区域管理员系统登录页面
    public static final String BLOCK_MANAGER_SYSTEM_PUBLIC_HOME="blockManagerSystem/blockAdminHome";//区域管理员系统主页
    public static final String BLOCK_MANAGER_SYSTEM_PUBLIC_MANAGEMENT="blockManagerSystem/blockManagement";//区域管理员土地管理模块
    public static final String BLOCK_MANAGER_SYSTEM_PUBLIC_EDIT="blockManagerSystem/editAmount";//区域管理员编辑模块    
    public static final String BLOCK_MANAGER_SYSTEM_UNSOLVED_ORDER_INDEX="blockManagerSystem/orderUnsolved";//区域管理员未处理订单模块
    public static final String BLOCK_MANAGER_SYSTEM_SOLVED_ORDER_INDEX="blockManagerSystem/orderSolved";//区域管理员未处理订单模块
    
    public static final String USER_STATISTICS ="statistics/userStatistics";//会员统计
    public static final String TOTAL_SALES_STATISTICS ="statistics/totalSalesStatistics";//总销售额统计
    public static final String PRODUCT_SALES_STATISTICS ="statistics/productStatistics";//商品销售额统计
    public static final String PARENT_TYPE_SALES_STATISTICS ="statistics/parentTypeStatistics";//商品销售额统计
    public static final String PARENT_INPUT_STATISTICS ="statistics/productInputStatistics";//商品进货额统计
    public static final String PARENT_SOURCE_STATISTICS ="statistics/productSourceStatistics";//商品销售资金来源统计
    //商品退货管理
    public static final String REJECTED_INDEX_STRING="goodsRejected/rejectedIndex";//退货列表界面
    //权限管理页
    public static final String Auth_INDEX = "authUser/authIndex";//权限列表
    public static final String Auth_ADD = "authUser/addAuth";//添加权限
    public static final String Auth_DELETE = "authUser/deleteAuth";//删除权限
    public static final String Auth_EDIT = "authUser/editAuth";//编辑权限
    public static final String Auth_Find = "authUser/findAuth";//查看权限
    
    //购物卡管理界面
    public static final String GoodsCard_Index = "goodsCard/index";//购物卡列表
    public static final String GoodsCard_Log_Index = "goodsCard/cardLogIndex";//购物卡列表
    
    //广告页面
    public static final String Advertise_INDEX = "advertise/index";//权限列表
    public static final String Advertise_ADD = "advertise/add";//添加权限
    public static final String Advertise_DELETE = "advertise/delete";//删除权限
    public static final String Advertise_EDIT = "advertise/edit";//编辑权限
    public static final String Advertise_Find = "advertise/find";//查看权限
    public static final String Advertise_AuthList = "advertise/authList";//审批列表
    
    //农场信息查询页
    public static final String FarmMessage_INDEX = "farmMessage/index";//农场信息列表
    public static final String FarmMessage_ADD = "farmMessage/add";//添加农场信息
    public static final String FarmMessage_DETAIL = "farmMessage/find";//查看农场信息详情
    public static final String FarmMessage_EDIT = "farmMessage/edit";//编辑更新农场信息
    public static final String FarmMessage_AuthList = "farmMessage/authList";//审批列表
    public static final String FarmMessage_DELETE = "farmMessage/delete";//删除权限//没用
    
  //休闲与美食查询页
    public static final String CookLeisure_INDEX = "cookLeisure/index";//列表
    public static final String CookLeisure_ADD = "cookLeisure/add";//添加农场信息
    public static final String CookLeisure_FIND = "cookLeisure/find";//查看农场信息详情
    public static final String CookLeisure_EDIT = "cookLeisure/edit";//编辑更新农场信息
    public static final String CookLeisure_AuthList = "cookLeisure/authList";//审批列表
    public static final String CookLeisure_DELETE = "cookLeisure/delete";//删除权限
    public static final String CookLeisure_Message = "cookLeisure/cookLeisureMessage";//休闲美食h5页面
    //添加农场相册
    public static final String Album_INDEX = "album/index";//相册列表
    public static final String Album_ADD = "album/add";//添加相册信息
    public static final String Album_FIND = "album/find";//查看相册详情
    public static final String Album_EDIT = "album/edit";//编辑更新相册
    public static final String Album_AuthList = "album/authList";//审批列表
    public static final String Album_DELETE = "album/delete";//删除相册
    
    //摄像头页面
    public static final String Camera_INDEX = "camera/index";//权限列表
    public static final String Camera_ADD = "camera/add";//添加权限
    public static final String Camera_EDIT = "camera/edit";//编辑权限
    public static final String Camera_FIND = "camera/find";//查看
    
    //阿里支付页面
    public static final String PAY_API = "interface/AliPay/alipayapi";//页面核心交易页面
    public static final String PAY_RETURN_URL = "interface/AliPay/returnURL";//页面核心交易页面
    
    //用户默认昵称前缀
    public static final String PRE_NICK_NAME = "YY_";
    
    //第三方用户凭证
    public static final String USERAPPID= "wx08f9772f4ae8de8a";//只与第三方有关？网页授权appid
    public static final String USERAPPSECERT="1d1566c5a63ba09593952eee7f4270a6";//第三方用户唯一凭证密钥
    public static final String APPID= "wx364978a4b74fac34";//公众号的唯一标识,jssdk，accessToken
    public static final String APPSECERT="ca7423b9c2ab76f899160022e7625af4";//公众号的appsecret
    //第三方用户凭证
//    public static final String USERAPPID= "wx08f9772f4ae8de8a";
//    public static final String USERAPPSECERT="1d1566c5a63ba09593952eee7f4270a6";//第三方用户唯一凭证密钥
//    public static final String APPID= "wxc6039a2e364a8e17";//公众号的唯一标识
//    public static final String APPSECERT="faaa55cdbe31189d5198ac2d74d27e81";//公众号的appsecret
    
    /**
     * session记录
     */
    public static String SESSION_USER = "user";// 用户信息session
	public static String SESSION_BLOCK_MANAGER = "blockmanager";//区域管理员信息session
//  //发送短信验证码
//  public static final String ACOUNT_SID="8a216da854ff8dcc015504f93c8d08af";//初始化主帐号
//  public static final String AUTH_TOKEN="578bdf811f4c432dbec29c8e88d2ab75";//主帐号令牌
//  public static final String APP_ID="8a216da854ff8dcc015504f93ced08b5";
//  public static final String Demo_ID="100167";
    //图片尺寸大小
    public static int PRODUCT_WIDTH=240;
    
    //订单状态
    public static final int ORDER_NO_PAY = 1;//订单未支付状态
//    public static final Integer PayStatus_CardPaySome = 8; //购物卡已支付部分订单
    
    //附属信息
    public static final String ADDITION_CHO = "addition/choose";//选择编辑
    public static final String ADD_OK = "ok";//添加成功
    public static final String ADD_FAIl = "fail";//添加失败
	public static final String ADD_EDIT = "addition/edit";//编辑附属信息
	public static final String ADD_REVIEW = "addition/review";//审核编辑
	public static final String ADD_DETAIL = "addition/detail";//信息详情
	public static final String PROJECT_FULL_URL_PREFIX = "http://localhost:8080/SmartyAgriculture";
	public static final String ADD_MESSAGE = "addition/additionMessage";//附属信息h5页面
	
	public static final String QRCODE_PATH = "qrcode";
	
	// 审计状态
	public static final Integer NORMAL = 0;
	public static final Integer ADDED_ON_WAIT = 1;
	public static final Integer EDITED = 2;
	public static final Integer NORMAL_WITH_EDITED = 3;
	public static final Integer DELETE_ON_WAIT = 4;

	//最近搜索记录条数
	public static final int RECENT_SEARCH_NUM = 10;
	//参观农场订单列表
	public static final String FARMVISIT_ORDER_LISt = "farmVisits/farmVisitOrders";
	public static final String FREIGHTINDEX = "freight/index";
	public static final Integer FREIGHT_NORMAL = 0;
	public static final String FREIGHTEDIT = "freight/freightEdit";
	public static final Integer FREIGHT_HASDRAFT = 1;
	public static final Integer FREIGHT_DRAFT = 2;
	public static final String FREIGHTREVIEW = "freight/freightReview";
	
}
