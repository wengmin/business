var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../services/user.js');
var app = getApp();

Page({
  data: {
    userInfo: {},
    isshow: false,
    isNewUser: false,
    param: "",
    realname: "",
    collectCount: 0,
    recordCount: 0,
    recordTodayCount: 0,
    shareCount: 0
  },
  checkCard: function () {
    let that = this;
    if (wx.getStorageSync('token')) {
      util.request(api.CardInfoByOpenID, {
        openid: wx.getStorageSync('token')
      }).then(function (res) {
        if (res.errno === 0) {
          if (!res.data.realname) {
            that.setData({
              isNewUser: true,
            });
          } else {
            that.setData({
              param: res.data.param,
              realname: res.data.realname
            })
          }
        } else {
          that.setData({
            isNewUser: true,
          });
        }
      }).catch((err) => {
        that.setData({
          isNewUser: true,
        });
      });
    } else {
      that.setData({
        isNewUser: true,
      });
    }
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log('----dd----', app.globalData)
    this.checkCard()
  },
  onReady: function () {

  },
  onShow: function () {
    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    // 页面显示
    if (token) {
      var that = this
      that.setData({
        isshow: true
      });
      that.setData({
        isshow: true
      });
      app.globalData.userInfo = userInfo.userInfo;
      app.globalData.token = token;

      util.request(api.CardReport, {}).then(function (res) {
        if (res.errno === 0) {
          that.setData({
            collectCount: res.data.collectCount,
            recordCount: res.data.recordCount,
            recordTodayCount: res.data.recordTodayCount,
            shareCount: res.data.shareCount
          });
        }
      }).catch((err) => {
        console.log("catch" + err)
      });

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
  onHide: function () {
    // 页面隐藏 
  },
  onUnload: function () {
    // 页面关闭 
  },
  onShareAppMessage: function (res) {
    util.request(api.SaveShare, {}, 'POST', 'application/x-www-form-urlencoded').then(function (res) {});
    if (res.from == 'button') {
      return {
        title: "您好，我是" + this.data.realname + "，请惠存我的名片",
        desc: '销擎名片管理',
        path: '/pages/card/index2/index2?param=' + this.data.param,
        imageUrl: '/static/images/card/p_card.jpg',
      }
    }
    return {
      title: '邀请好友',
      path: '/pages/card/index2/index2?userId=' + wx.getStorageSync('userId')
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
        success: function (res) {
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
})