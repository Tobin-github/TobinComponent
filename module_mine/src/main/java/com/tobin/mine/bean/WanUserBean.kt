package com.tobin.mine.bean

/**
 * Created by Tobin on 2021/10/11.
 * Email: 616041023@qq.com
 * Description: User.
 */
data class WanUserBean(
    var id: Float,
    var admin: Boolean?,
    var username: String?,
    var nickname: String?,
    var password: String?,
    var icon: String?,
    var type: Float?,
    var token: String?,
    var publicName: String?,
)