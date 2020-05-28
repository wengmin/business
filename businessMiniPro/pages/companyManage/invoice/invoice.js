var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
const app = getApp()

Page({
  /**
   * 页面的初始数据
   */
  data: {
    invoiceId: 0,
    invoice: {},
    reply: ""
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (typeof(options) != "undefined" && typeof(options.invoiceId) != "undefined" && options.invoiceId > 0) {
      this.setData({
        invoiceId: options.invoiceId,
      })
      this.getInvoiceId()
    }
  },
  getInvoiceId: function() {
    let that = this;
    util.request(api.ServiceInvoiceDetail, {
      companyId: app.globalData.companyId,
      invoiceId: that.data.invoiceId
    }).then(function(res) {
      if (res.errno === 0) {
        res.data.createTime = util.formatTime(new Date(res.data.createTime))
        if (res.data.updateTime)
          res.data.updateTime = util.formatTime(new Date(res.data.updateTime))
        that.setData({
          invoice: res.data,
        })
      }
    })
  },
  bindinputValue(event) {
    switch (event.currentTarget.dataset.type) {
      case "reply":
        this.setData({
          reply: event.detail.value
        });
        break;
    }
  },
  //开票
  billing: function(e) {
    let that = this;
    util.request(api.ServiceInvoiceUpdate, {
      companyId: app.globalData.companyId,
      invoiceId: that.data.invoiceId,
      status: 1,
      reply: that.data.reply
    }, "POST", 'application/x-www-form-urlencoded').then(function(res) {
      if (res.errno === 0) {
        that.getInvoiceId()
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