package cn.bupt.smartyagl.constant;
/**
 * redis中获取数据key列表
 * @author jm
 *
 */
public class ConstantsRedis {
	
	public static String REDIS_EXT = "SmartCity";//Redis后缀，所有Key都以这个为后缀，和别的信息进行区分
	
	public static final String REDIS_DISPLAY_MENU = "DisplayMenu";//显示菜单key
	
	public static final String REDIS_AUTH_MENU = "AuthMenu";//显示菜单key
	
	public static final String REDIS_EmailCode = "Email_";//key
	  /**
     * redis配置
     */
    // Redis服务器IP
    public static String ADDR = "127.0.0.1";
    // Redis的端口号
    public static int PORT = 6379;
    // 访问密码
    public static String AUTH = "123456";
    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    public static int MAX_ACTIVE = 1024;//1024
    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    public static int MAX_IDLE = 200;//200
    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    public static int MAX_WAIT = 10000;
    public static int TIMEOUT = 10000;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    public static boolean TEST_ON_BORROW = true;
}
