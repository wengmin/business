var app = getApp();
var user = require('../../services/user.js');

// component/register/register.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    canIUseGetUserProfile: {
      type: Boolean,
      value: true
    },
    buttonText: {
      type: String,
      value: '授权登录'
    },
    className: {
      type: String,
      value: ''
    },
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    getUserProfile(e) {
      let that = this
      // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
      // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
      wx.getUserProfile({
        desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
        success: (res) => {
          var userInfo = res.userInfo;
          userInfo.encryptedData = res.encryptedData;
          userInfo.iv = res.iv;
          user.loginByWeixin(userInfo).then(res => {
            let userInfo = wx.getStorageSync('userInfo');
            app.globalData.userInfo = userInfo;
            app.globalData.token = res.data.openid;

            //传递到page
            this.triggerEvent('successLogin', userInfo)

          }).catch((err) => {
            console.log(err)
          });
        },
        fail: (res) => {
          console.log("getUserProfile=>fail:" + res.errMsg)
        },
        complete: (res) => {

        },
      })
    },
    getUserInfo(e) {
      // 不推荐使用getUserInfo获取用户信息，预计自2021年4月13日起，getUserInfo将不再弹出弹窗，并直接返回匿名的用户个人信息
      let that = this
      let token = wx.getStorageSync('token');
      if (token) {
        return false;
      } else {
        if (e.detail.userInfo) {
          //用户按了允许授权按钮
          user.loginByWeixin(e.detail).then(res => {
            let userInfo = wx.getStorageSync('userInfo');
            app.globalData.userInfo = userInfo.userInfo;
            app.globalData.token = res.data.openid;
          }).catch((err) => {
            console.log(err)
          });
        } else {
          //用户按了拒绝按钮
          wx.showModal({
            title: '警告通知',
            content: '您点击了拒绝授权,将无法正常显示个人信息,点击确定重新获取授权。',
            success: function (res) {
              if (res.confirm) {
                wx.openSetting({
                  success: (res) => {
                    if (res.authSetting["scope.userInfo"]) { ////如果用户重新同意了授权登录
                      user.loginByWeixin(e.detail).then(res => {
                        let userInfo = wx.getStorageSync('userInfo');
                        app.globalData.userInfo = userInfo.userInfo;
                        app.globalData.token = res.data.openid;
                      }).catch((err) => {
                        console.log(err)
                      });
                    }
                  }
                })
              }
            }
          });
        }
      }
    },
  }
})