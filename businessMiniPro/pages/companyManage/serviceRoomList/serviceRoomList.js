var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    serviceClass: '',
    serviceStatus: 0,
    serviceStatusList: [],
    page: 1,
    size: 10,
    totalPage: 0,
    serviceList: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.stopPullDownRefresh();
    this.getServiceStatus()
  },
  getServiceStatus: function(val) {
    let that = this;
    util.request(api.MacrosByType, {
      value: 'serviceStatus'
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          serviceStatusList: res.data,
        })
        that.getServiceByStatus(that.data.serviceStatus)
      }
    })
  },
  getServiceByStatus: function(val) {
    let that = this;
    util.request(api.ServiceRoomList, {
      page: that.data.page,
      size: that.data.size,
      companyId: app.globalData.companyId,
      serviceClass: that.data.serviceClass,
      status: val
    }).then(function(res) {
      if (res.errno === 0) {
        var resdate = that.data.serviceList.concat(res.data.list);
        for (var i = 0; i < resdate.length; i++) {
          resdate[i].appointmentTime = util.formatTime(new Date(resdate[i].appointmentTime))
          resdate[i].createTime = util.formatTime(new Date(resdate[i].createTime))
        }
        that.setData({
          serviceList: resdate,
          totalPage: res.data.totalPage,
        })
      }
    })
  },
  statusCheck: function(e) {
    var value = e.target.dataset.value;
    if (value != this.data.serviceStatus) {
      this.data.serviceList = [];
      this.setData({
        serviceStatus: value
      })
      this.getServiceByStatus(value)
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    this.data.serviceList = [];
    this.setData({
      page: 1
    });
    this.onLoad();
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    var that = this;
    if (that.data.page < that.data.totalPage) {
      that.setData({
        page: that.data.page + 1
      });
      that.getServiceByStatus(that.data.serviceStatus);
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
})