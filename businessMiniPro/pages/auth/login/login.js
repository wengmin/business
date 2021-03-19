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
      let pages = getCurrentPages(); //获取当前页面pages里的所有信息。
      let prevPage = pages[pages.length - 2]; //prevPage 是获取上一个页面的js里面的pages的所有信息。-2 是上一个页面，-3是上上个页面以此类推。                                                           
      prevPage.setData({  // 将我们想要传递的参数在这里直接setData。上个页面就会执行这里的操作。
         id: this.data.orderInfo.id
      })//上一个页面内执行setData操作，将我们想要的信息保存住。当我们返回去的时候，页面已经处理完毕。
      //最后就是返回上一个页面。
      wx.navigateBack({
        delta: 1,  // 返回上一级页面。
        success: function() {
            console.log('成功！')
        }
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