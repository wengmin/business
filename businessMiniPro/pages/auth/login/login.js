var app = getApp();
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../services/user.js');

Page({
  data: {
    id: 0,
    type: null,
    userId: 0,
    userInfo: {},
    param: ''
  },
  onLoad: function(options) {
    var that = this;
    if (options.id) {
      that.setData({
        id: options.id
      });
    }
    if (options.type) {
      that.setData({
        type: options.type,
      });
    }
    if (options.param) {
      that.setData({
        param: options.param,
      });
    }
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {

  },
  bindGetUserInfo(e) {
    let that = this
    let token = wx.getStorageSync('token');
    if (token) {
      that.setFid()
      return false;
    } else {
      if (e.detail.userInfo) {
        //用户按了允许授权按钮
        user.loginByWeixin(e.detail).then(res => {
          let userInfo = wx.getStorageSync('userInfo');
          that.setData({
            userInfo: userInfo.userInfo
          });
          app.globalData.userInfo = userInfo.userInfo;
          app.globalData.token = res.data.openid;
          that.setFid()
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
                      that.setData({
                        userInfo: userInfo.userInfo
                      });
                      app.globalData.userInfo = userInfo.userInfo;
                      app.globalData.token = res.data.openid;
                      that.setFid()
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
  setFid: function() {
    let that = this
    if (that.data.id == -1) {
      wx.redirectTo({
        url: '/pages/card/index/index?param='
      })
    } else if (that.data.id == -2) {
      wx.redirectTo({
        url: '/pages/ucenter/index/index'
      })
    } else if (that.data.id == -3) {
      wx.redirectTo({
        url: '/pages/card/adduser/adduser'
      })
    } else if (that.data.id == -4) {
      wx.redirectTo({
        url: '/pages/card/index/index?param=' + that.data.param
      })
    } else if (that.data.id == -5) {
      // wx.redirectTo({
      //   url: '/pages/service/index/index?roomId=' + that.data.param
      // })
      wx.navigateBack({
        delta: 3
      })
    } else if (that.data.id == -6) {
      wx.redirectTo({
        url: '/pages/company/staffBind/staffBind'
      })
    } else {
      wx.redirectTo({
        url: '/pages/card/index/index?param='
      })
    }
    wx.hideLoading()
  },
  goUrl: function() {
    wx.navigateBack({
      delta: 3
    })
  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  }
})