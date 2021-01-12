# 组件化开发

MVVM 架构 （Rxjava2 + Retrofit2 + Okhttp3 + Room + ARouter + dataBinding）

## 网络

项目统一使用Retrofit作为基础网络框架，基本使用如下：
```java
Box.getRetrofit(RecipeApi.class)
        .recipesClass(bodyMaps)
        .compose(RxUtils.httpResponseTransformer(false))
        .subscribe(new CommonObserver<RecipesClassBean>() {
            @Override
            public void onNext(RecipesClassBean recipesClassBean) {

            }

            @Override
            protected void onError(ApiException ex) {
                super.onError(ex);

            }

            @Override
            protected void onNetError() {

            }
        });
```

## 用户系统

```java
//设置进去
Box.getSessionManager().setUser(objects[0]);
//全局任意位置获取
SessionUserInfo userInfo=Box.getSessionManager().getUser();
```

## 路由
路由使用ARouter，项目地址：https://github.com/alibaba/ARouter

## 其他
- 在Fragment中使用color、string等资源文件禁止直接使用getContext()获取到的上下文，而应该使用Box.class中提供的全局上下文，具体请查看Box.class
- 全局所有配置均在GlobalConfiguration.class这个文件中
- 如果因为主线程耗时监听出现烦人的声音，可以先将lib-core下AndroidManifest.xml中的这句代码暂时注释掉：
```java
 <meta-data
      android:name="com.gzq.lib_core.base.quality.QualityBlockCanary"
      android:value="AppLifecycle" />
```