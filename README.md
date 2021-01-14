# 组件化开发

MVVM 架构 （Rxjava2 + Retrofit2 + Okhttp3 + Room + ARouter + Jetpack）

## 网络

项目统一使用Retrofit作为基础网络框架，基本使用如下：
```java
// ViewModel 中请求网络
addDisposable(Box.getRetrofit(RecipeApi.class)
        .recipesClass()
        .compose(RxUtils.httpResponseTransformer())
        .subscribe(recipesClassBean -> {
            Timber.tag(TAG).i(recipesClassBean.toString());
            recipesClassLiveData.postValue(recipesClassBean);
        }, throwable ->{
            Timber.tag(TAG).i("throwable message: %s", throwable.getMessage());
            error.postValue(throwable);
        } , () -> {
            Timber.tag(TAG).e("onComplete");
            // 请求结束
        }));
```

## 用户系统

```java
//设置进去
Box.getSessionManager().setUser(objects[0]);

//全局任意位置获取
SessionUserInfo userInfo = Box.getSessionManager().getUser();
```

## 路由
路由使用ARouter，项目地址：https://github.com/alibaba/ARouter

## 其他
- 在Fragment中使用color、string等资源文件禁止直接使用getContext()获取到的上下文，而应该使用Box.class中提供的全局上下文，具体请查看Box.class
- 全局所有配置均在GlobalConfiguration.class这个文件中