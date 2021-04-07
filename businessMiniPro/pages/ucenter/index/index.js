var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../services/user.js');
var app = getApp();

Page({
  data: {
    userInfo: {},
    cardList: {},
    collectCount: 0,
    recordCount: 0,
    recordTodayCount: 0,
    shareCount: 0
  },
  onLoad: function (options) {
  },
  onShow: function () {
    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    // 页面显示
    if (token) {
      var that = this
      app.globalData.userInfo = userInfo;
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

      util.request(api.myCardList).then(function (res) {
        if (res.errno === 0) {
          that.setData({
            cardList: res.data,
          });
        }
      })
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
    if (res.from == 'button') {
      return {
        title: "您好，我是" + res.target.dataset.realname + "，请惠存我的名片",
        desc: '销擎名片管理',
        path: '/pages/card/index/index?param=' + res.target.dataset.param,
        imageUrl: '/static/images/card/p_card.jpg',
        success: (res) => {
          util.request(api.SaveShare, {}, 'POST', 'application/x-www-form-urlencoded').then(function (ress) {});
        },
        fail: (res) => {
          console.log("转发失败", res);
        }
      }
    }
    return {
      title: '邀请好友',
      path: '/pages/card/index/index?userId=' + wx.getStorageSync('userId')
    }
  }
})