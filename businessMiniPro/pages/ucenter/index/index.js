var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../services/user.js');
var app = getApp();

Page({
  data: {
    userInfo: {},
    hasMobile: '',
    commission: {
      allProfit: 0,
      getProfit: 0
    },
    isshow: false,
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log('----dd----', app.globalData)
  },
  onReady: function() {

  },
  onShow: function() {
    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    // 页面显示
    if (token) {
      this.setData({
        isshow: true
      });
      var that = this
      this.setData({
        isshow: true
      });
      app.globalData.userInfo = userInfo.userInfo;
      app.globalData.token = token;
    } else {
      wx.redirectTo({
        url: '/pages/auth/login/login?id=-2'
      })
      wx.removeStorageSync('userInfo');
    }
    this.setData({
      userInfo: app.globalData.userInfo
    });

  },
  noLogin() {
    if (!wx.getStorageSync('token')) {
      console.log("}}}}}}")
      wx.showToast({
        title: '请先登录～',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
  },
  onHide: function() {
    // 页面隐藏 
  },
  onUnload: function() {
    // 页面关闭 
  },
  onShareAppMessage: function() {
    this.noLogin();
    return {
      title: '邀请好友',
      path: '/pages/card/index/index?userId=' + wx.getStorageSync('userId')
    }
  },
  bindGetUserInfo(e) {
    let token = wx.getStorageSync('token');
    if (token) {
      return false;
    }
    if (e.detail.userInfo) {
      //用户按了允许授权按钮
      user.loginByWeixin(e.detail).then(res => {
        let userInfo = wx.getStorageSync('userInfo');
        this.setData({
          userInfo: userInfo.userInfo,
          isshow: true
        });
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
        success: function(res) {
          if (res.confirm) {
            wx.openSetting({
              success: (res) => {
                if (res.authSetting["scope.userInfo"]) { ////如果用户重新同意了授权登录
                  user.loginByWeixin(e.detail).then(res => {
                    let userInfo = wx.getStorageSync('userInfo');
                    this.setData({
                      userInfo: userInfo.userInfo,
                      isshow: true
                    });
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
  },
  // exitLogin: function() {
  //   let that = this
  //   wx.showModal({
  //     title: '',
  //     confirmColor: '#b4282d',
  //     content: '退出登录？',
  //     success: function(res) {
  //       console.log("------:", res)
  //       if (res.confirm) {
  //         wx.removeStorageSync('token');
  //         wx.removeStorageSync('userInfo');
  //         wx.removeStorageSync('userId');
  //         app.globalData.userInfo = {}
  //         that.setData({
  //           userInfo: {},
  //         });
  //         wx.navigateTo({
  //           url: '/pages/card/index/index' //'/pages/index/index'
  //         });
  //       }
  //     }
  //   })
  // },
  handlerGobackClick() {
    wx.showModal({
      title: '你点击了返回',
      content: '是否确认放回',
      success: e => {
        if (e.confirm) {
          const pages = getCurrentPages();
          if (pages.length >= 2) {
            wx.navigateBack({
              delta: 1
            });
          } else {
            wx.switchTab({
              url: '/pages/card/index/index?userId=0'
            });
          }
        }
      }
    });
  },
  handlerGohomeClick() {
    wx.switchTab({
      url: '/pages/card/index/index?userId=0'
    });
  }
})