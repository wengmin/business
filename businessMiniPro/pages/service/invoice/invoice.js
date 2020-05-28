var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
const app = getApp()
// pages/service/invoice/invoice.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    invoice: {
      titletype: 1,
      content: '住店'
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },
  radioChange: function(e) {
    if (e.detail.value == 1) {
      this.setData({
        "invoice.titletype": 1,
        "invoice.titlename": '',
        "invoice.taxno": '',
        "invoice.registaddress": '',
        "invoice.registphone": '',
        "invoice.bank": '',
        "invoice.bankaccount": '',
        "invoice.remark": ''
      })
    } else {
      this.setData({
        "invoice.titletype": 2,
        "invoice.titlename": '',
        "invoice.taxno": '',
        "invoice.registaddress": '',
        "invoice.registphone": '',
        "invoice.bank": '',
        "invoice.bankaccount": '',
        "invoice.remark": ''
      })
    }
  },
  bindVal(event) {
    switch (event.currentTarget.dataset.type) {
      case "titlename":
        this.setData({
          "invoice.titlename": event.detail.value
        });
        break;
      case "taxno":
        this.setData({
          "invoice.taxno": event.detail.value
        });
        break;
      case "registaddress":
        this.setData({
          "invoice.registaddress": event.detail.value
        });
        break;
      case "registphone":
        this.setData({
          "invoice.registphone": event.detail.value
        });
        break;
      case "bank":
        this.setData({
          "invoice.bank": event.detail.value
        });
        break;
      case "bankaccount":
        this.setData({
          "invoice.bankaccount": event.detail.value
        });
        break;
      case "remark":
        this.setData({
          "invoice.remark": event.detail.value
        });
        break;
    }
  },
  save:function(){
    let that = this;
    let datas = this.data.invoice;
    if (datas.titletype == 1) {
      if (!datas.titlename) {
        util.showErrorToast('请输入发票抬头');
        return false;
      }
    }
    if (datas.titletype == 2) {
      if (!datas.titlename) {
        util.showErrorToast('请输入单位名称');
        return false;
      }
    }
    util.request(api.ServiceInvoiceSave, {
      roomId: app.globalData.roomId,
      titletype: datas.titletype,
      content: datas.content,
      titlename: datas.titlename,
      taxno: datas.taxno,
      registaddress: datas.registaddress,
      registphone: datas.registphone,
      bank: datas.bank,
      bankaccount: datas.bankaccount,
      remark: datas.remark,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        wx.showToast({
          title: '提交成功'
        });
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