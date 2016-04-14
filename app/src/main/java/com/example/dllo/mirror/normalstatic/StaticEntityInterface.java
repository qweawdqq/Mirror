package com.example.dllo.mirror.normalstatic;

/**
 * Created by dllo on 16/3/30.
 */
public interface StaticEntityInterface {

    // 总接口 拼接网址头  START
    String NET_START = "http://api101.test.mirroreye.cn/";


    /**
     * 菜单接口 : 此接口 接口文档没给,从官方软件抓包得到
     * 输入参数  < 用户凭证 token; 用户id  uid; 设备类型  device_type; 请求条数 page; 最后一条数据插入时间 last_time  >
     */

    String MENU_LIST = NET_START + "index.php/index/menu_list";


    /**
     * 故事列表
     * 输入参数  < 用户凭证 token; 用户id  uid; 设备类型  device_type; 请求条数 page; 最后一条数据插入时间 last_time  >
     */
    String STORY_LIST = NET_START + "index.php/story/story_list";

    String TOKEN = "token"; // 用户凭证
    String UID = "uid";      // 用户id
    String DEVICE_TYPE = "device_type"; // 设备类型
    String PAGE = "page";       // 请求条数
    String LAST_TIME = "last_time"; //最后一条数据插入时间

    
    /**
     * 故事详情
     * 输入参数  < 用户凭证 token; 设备类型  device_type; 故事ID story_id  >
     */
    String STORY_INFO = NET_START + "index.php/story/info";

    String STORY_ID = "story_id"; //故事ID


    /**
     * 用户注册
     * 输入参数  < 手机号 phone_number; 验证码 number; 密码 password   >
     */

    String USER_REG = NET_START + "index.php/user/reg";

    String PHONE_NUMBER = "phone_number"; // 手机号
    String NUMBER = "number"; // 验证码
    String PASSWORD = "password";  // 密码


    /**
     * 登录
     * 输入参数  < 手机号 phone_number; 密码 password   >
     */

    String USER_LOGIN = NET_START + "index.php/user/login";


    /**
     * 绑定账号
     * 输入参数  < 1.微博2.微信  iswb_orwx; iswb_orwx为1时有值 wb_name; iswb_orwx为1时有值 wb_img;
     * iswb_orwx为1时有值  wb_id; iswb_orwx为2时有值  wx_name; iswb_orwx为2时有值 wx_img; iswb_orwx为2时有值 wx_id  >
     */

    String USER_BUNDLING = NET_START + "index.php/user/bundling";

    String BUNDLING_ISWB_ORWX = "iswb_orwx"; // 1.微博2.微信
    String BUNDLING_WB_NAME = "wb_name"; // iswb_orwx为1时有值
    String BUNDLING_WB_IMG = "wb_img"; // iswb_orwx为1时有值
    String BUNDLING_WB_ID = "wb_id";   // iswb_orwx为1时有值
    String BUNDLING_WX_NAME = "wx_name";  // iswb_orwx为2时有值
    String BUNDLING_WX_IMG = "wx_img";  // iswb_orwx为2时有值
    String BUNDLING_WX_ID = "wx_id";  // iswb_orwx为2时有值


    /**
     * 获取验证码
     * 输入参数 < 电话号码  phone number  >
     */

    String USER_SEND_CODE = NET_START + "index.php/user/send_code";

    String CODE_PHONE_NUMBER = "phone number"; // 电话号码


    /**
     * 商品列表
     * 输入参数 < 设备类型  device_type; 请求条数 page; 最后一条数据插入时间 last_time; 商品列表分类ID category_id; 版本 version  >
     */

    String GOODS_LIST = NET_START + "index.php/products/goods_list";

    String GOODS_LIST_CATEGORY_ID = "category_id"; // 商品列表分类ID
    String GOODS_LIST_VERSION = "version"; // 版本


    /**
     * 商品详情
     * 输入参数  < 用户凭证 token; 设备类型  device_type; 商品详情分类ID goods_id  >
     */

    String GOODS_INFO = NET_START + "index.php/products/goods_info";

    String GOODS_INFO_GOODS_ID = "goods_id"; //商品详情分类ID


    /**
     * 商品分类列表
     * 输入参数  < 用户凭证 token >
     */

    String CATEGORY_LIST = NET_START + "index.php/products/category_list";


    /**
     * 个人中心
     * 输入参数  < 用户凭证 token >
     */

    String USER_INFO = NET_START + "index.php/user/user_info";


    /**
     * 加入购物车
     * 输入参数  < 用户凭证 token; 商品详情分类ID goods_id>
     */

    String JOIN_SHOPPING_CART = NET_START + "index.php/order/join_shopping_cart";


    /**
     * 订单列表
     * 输入参数  < 用户凭证 token; 设备类型  device_type; 请求条数 page; 最后一条数据插入时间 last_time >
     */

    String ORDER_LIST = NET_START + "index.php/order/order_list";


    /**
     * 购物车列表
     * 输入参数  < 用户凭证 token; 设备类型 device_type; 请求条数 page; 最后一条数据插入时间 last_time >
     */

    String SHOPPING_CART_LIST = NET_START + "index.php/order/shopping_cart_list";


    /**
     * 我的收获地址列表
     * 输入参数  < 用户凭证 token; 设备类型 device_type; 请求条数 page; 最后一条数据插入时间 last_time >
     */

    String ADDRESS_LIST = NET_START + "index.php/user/address_list";


    /**
     * 添加收获地址
     * 输入参数  < 用户凭证 token; 收货人 username ; 收货人电话 cellphone ; 详情地址 addr_info ; 邮编 zip_code ; 城市 city  >
     */

    String ADD_ADDRESS = NET_START + "index.php/user/add_address";

    String ADD_ADDRESS_USERNAME = "username"; //收货人
    String ADD_ADDRESS_CELLPHONE = "cellphone"; //收货人电话
    String ADD_ADDRESS_ADDR_INFO = "addr_info"; //详情地址
    String ADD_ADDRESS_ZIP_CODE = "zip_code"; //邮编
    String ADD_ADDRESS_CITY = "city"; //城市


    /**
     * 编辑收获地址
     * 输入参数  < 用户凭证 token; 收货地址ID ; 收货人 ; 收货人电话 ; 详情地址 ; 邮编 ; 城市  >
     */

    String EDIT_ADDRESS = NET_START + "index.php/user/edit_address";

    String ADDR_ID = "addr_id"; // 收货地址ID


    /**
     * 删除收获地址
     * 输入参数  < 用户凭证 token; 收货地址ID  >
     */

    String DEL_ADDRESS = NET_START + "index.php/user/del_address";


    /**
     * 默认收获地址
     * 输入参数  < 用户凭证 token; 收货地址ID  >
     */

    String MR_ADDRESS = NET_START + "index.php/user/mr_address";


    /**
     * 下订单
     * 输入参数  < 用户凭证 token; 商品详情分类id goods_id; 商品数量 goods_num; 订单价格 price; 优惠券ID discout_id; 设备类型 device_type  >
     */

    String ORDER_SUB = NET_START + "index.php/order/sub";

    String GOODS_NUM = "goods_num";  //商品数量
    String PRICE = "price";          //订单价格
    String DISCOUT_ID = "discout_id";  //优惠券ID


    /**
     * 支付宝支付
     * 输入参数  < 用户凭证 token; 订单id  order_no; 收货地址ID  addr_id; 商品名 goodsname  >
     */

    String ALI_PAY_SUB = NET_START + "index.php/pay/ali_pay_sub";

    String GOODSNAME = "goodsname"; // 商品名
    String ALI_PAY_SUB_ORDER_NO = "order_no"; //订单id


    /**
     * 微信支付
     * 输入参数  < 用户凭证 token; 订单id  order_no; 收货地址ID  addr_id; 商品名 goodsname  >
     */

    String WX_PAY_SUB = NET_START + "index.php/pay/wx_pay_sub";


    /**
     * 获取微信支付结果
     * 输入参数  < 用户凭证 token; 订单id  order_no  >
     */

    String WX_ORDERQUERY = NET_START + "index.php/pay/wx_orderquery";


    /**
     * 我的订单详情
     * 输入参数  < 凭证 token; 设备类型 device_type; 订单号 order_id  >
     */

    String ORDER_INFO = NET_START + "index.php/order/info";

    String ORDER_ID = "order_id"; // 订单号


    /**
     * 物流信息查询
     * 输入参数  < 凭证 token; 设备类型 device_type; 订单号 order_id  >
     */

    //// TODO: 16/3/30   没有请求类名


    /**
     * 申请退款
     * 输入参数  < 凭证 token; 订单ID order_id; 原因ID reason_id; 补充说明 note  >
     */

    // // TODO: 16/3/30  没有请求类名


    /**
     * 退款原因列表
     * 输入参数  < 凭证 token  >
     */

    // // TODO: 16/3/30  没有请求类名


    /**
     * 我的优惠券列表
     * 输入参数  < 凭证 token  >
     */

    String DISCOUNT_LIST = NET_START + "index.php/user/discount_list";


    /**
     * 测试故事列表
     * 输入参数  < 凭证 token; 用户id  uid; 设备类型 device_type; 请求条数 page; 最后一条数据插入时间 last_time  >
     */

    String STORY_TEST_STORY_LIST = NET_START + "index.php/story_test/story_list";


    /**
     * 测试故事详情
     * 输入参数  < 凭证 token; 设备类型 device_type; 故事ID story_id  >
     */

    String STORY_TEST_INFO = NET_START + "index.php/story_test/info";


    /**
     * 测试商品列表
     * 输入参数  < 凭证 token; 设备类型 device_type; 请求条数 page; 最后一条数据插入时间 last_time; 商品列表分类ID category_id  >
     */

    String PRODUCTS_TEST_GOODS_LIST = NET_START + "index.php/products_test/goods_list";


    /**
     * 测试商品详情
     * 输入参数  < 凭证 token; 设备类型 device_type; 商品详情分类ID goods_id >
     */

    String PRODUCTS_TEST_GOODS_INFO = NET_START + "index.php/products_test/goods_info";


    /**
     * 每日推荐列表
     * 输入参数  < 凭证 token; 设备类型 device_type; 请求条数 page; 最后一条数据插入时间 last_time  >
     */

    String MRTJ = NET_START + "index.php/index/mrtj";


    /**
     * 启动图
     * 输入参数  < 无 >
     */

    String STARTED_IMG = NET_START + "index.php/index/started_img";


    /**
     * 分享开关
     * 输入参数  < 无 >
     */

    String SHARE_SWITCH = NET_START + "index.php/index/share_switch";


    /**
     * 分享开关
     * 输入参数  < 当前版本号 version >
     */

    String SYS = NET_START + "index.php/sys";

    String VERSION = "version"; // 当前版本号

}
