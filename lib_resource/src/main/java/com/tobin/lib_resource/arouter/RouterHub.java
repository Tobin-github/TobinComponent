package com.tobin.lib_resource.arouter;

/**
 * RouterHub 用来定义路由器的路由地址, 以组件名作为前缀, 对每个组件的路由地址进行分组, 可以统一查看和管理所有分组的路由地址
 *
 * 路由地址的命名规则为 组件名 + 页面名, 如订单组件的订单详情页的路由地址可以命名为 "/order/OrderDetailActivity"
 *
 * ARouter 将路由地址中第一个 '/' 后面的字符称为 Group, 比如上面的示例路由地址中 order 就是 Group, 以 order 开头的地址都被分配该 Group 下
 * 切记不同的组件中不能出现名称一样的 Group, 否则会发生该 Group 下的部分路由地址找不到的情况!!!
 * 所以每个组件使用自己的组件名作为 Group 是比较好的选择, 毕竟组件不会重名
 *
 */
public interface RouterHub {
    /**
     * 组名
     */
    String APP = "/app";    //宿主 App 组件
    String RECIPE = "/recipe";  //菜谱组件
    String HOME = "/home";  //首页组件
    String MINE = "/mine";  //我的组件

    /**
     * 服务组件, 用于给每个组件暴露特有的服务
     */
    String SERVICE = "/service";

    /**
     * 宿主 App 分组
     */
    String APP_SPLASH_ACTIVITY = APP + "/SplashActivity";
    String APP_MAIN_ACTIVITY = APP + "/MainActivity";

    /**
     * 主页
     */
    String APP_HOME_ACTIVITY = HOME + "/HomeActivity";
    String APP_HOME_FRAGMENT = HOME + "/HomeFragment";

    /**
     * 我的
     */
    String APP_MINE_ACTIVITY = MINE + "/MineActivity";
    String APP_MINE_FRAGMENT = MINE + "/MineFragment";

    /**
     * 菜谱分组
     */
    String RECIPE_SERVICE_INFO = RECIPE + SERVICE + "/RecipeInfoService";
    String RECIPE_RECIPE_ACTIVITY = RECIPE + "/RecipeActivity";
    String RECIPE_RECIPE_FRAGMENT = RECIPE + "/RecipeClassFragment";
    String RECIPE_RESULT_FRAGMENT = RECIPE + "/RecipeResultFragment";
    String RECIPE_RESULT_SEARCH_FRAGMENT = RECIPE + "/RecipeSearchResultFragment";
    String RECIPE_SEARCH_ACTIVITY = RECIPE + "/SearchActivity";


}
