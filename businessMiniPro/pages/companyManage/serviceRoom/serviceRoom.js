var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    serviceId: 0,
    service: {},
    serviceLog: {},
    remark: ""
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (typeof(options) != "undefined" && typeof(options.serviceId) != "undefined" && options.serviceId > 0) {
      this.setData({
        serviceId: options.serviceId,
      })
      this.getService()
    }
  },
  getService: function() {
    let that = this;
    util.request(api.ServiceRoomDetail, {
      companyId: app.globalData.companyId,
      serviceId: that.data.serviceId
    }).then(function(res) {
      if (res.errno === 0) {
        res.data.appointmentTime = util.formatTime(new Date(res.data.appointmentTime))
        res.data.createTime = util.formatTime(new Date(res.data.createTime))
        that.setData({
          service: res.data,
        })
        if (res.data.status > 0) {
          that.getServiceLog();
        }
      }
    })
  },
  getServiceLog: function() {
    let that = this;
    util.request(api.ServiceRoomLogList, {
      companyId: app.globalData.companyId,
      serviceId: that.data.serviceId
    }).then(function(res) {
      if (res.errno === 0) {
        for (var i = 0; i < res.data.length; i++) {
          res.data[i].createTime = util.formatTime(new Date(res.data[i].createTime))
        }
        that.setData({
          serviceLog: res.data,
        })
      }
    })
  },
  bindinputValue(event) {
    switch (event.currentTarget.dataset.type) {
      case "remark":
        this.setData({
          remark: event.detail.value
        });
        break;
    }
  },
  //受理//处理
  acceptHandle: function(e) {
    let that = this;
    util.request(api.ServiceRoomUpdate, {
      companyId: app.globalData.companyId,
      serviceId: that.data.serviceId,
      status: e.currentTarget.dataset.status,
      remark: that.data.remark
    }, "POST", 'application/x-www-form-urlencoded').then(function(res) {
      if (res.errno === 0) {
        that.getService()
      } else {
        util.showErrorToast(res.errmsg);
      }
    })
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
  onPullDownRefresh: function() {},
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {},
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
})