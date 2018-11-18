package soexample.umeng.com.myproject.net;

public class Http {
    public static String BASE_URL = "http://www.zhaoapi.cn";
    //轮播图接口
    public static String BANNER_URL = BASE_URL + "/ad/getAd";
    //九宫格接口
    public static String JIU_URL = BASE_URL + "/product/getCatagory";
    //瀑布流
    public static String PUBU_URL = BASE_URL + "/product/getCarts?uid=71";
    //分类
    public static String FENLEI_URL = BASE_URL + "/product/getCatagory";
    //子分类
    public static String FENLEIS_URL = BASE_URL + "/product/getProductCatagory?cid=";
    //注册
    public static String ZHUCE_URL = BASE_URL + "/user/reg";
    //登录
    public static String LOGIN_URL = BASE_URL + "/user/login";
    //添加购物车
    public static String AddCar_URL = BASE_URL + "/product/addCart";
    //商品详情
    public static String SHOP_DETAILS_URL = BASE_URL + "/product/getProductDetail?pid=";
    //获取购物车商品
    public static String ShopShow_URL = BASE_URL + "/product/getCarts";
    //当前子分类下的商品列表（分页）
    public static String ShopFen_URL = "/product/getProducts";
    //根据关键词搜索商品
    public static String ShopGuan_URL = "/product/searchProducts";
    //删除购物车
    public  static  String ShopDelete_URL="/product/deleteCart";
}
