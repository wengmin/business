const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');

// pages/company/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    companyId:0,
    company: [],
    postlist: {}
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.companyId != "undefined" && typeof (options.companyId) != "undefined") {
      this.setData({
        companyId: options.companyId,
      })
    }
    this.getCompany()
    this.getPostList()
  },
  getCompany: function () {
    let that = this
    util.request(api.CompanyDetailsByID, {
      companyId: that.data.companyId
    }).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          company: res.data,
        })
      }
    })
  },
  getPostList: function () {
    let that = this
    util.request(api.CompanyPostList, {
      companyId: that.data.companyId
    }).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          postlist: res.data,
        })
      }
    })
  },
  //预览图片
  topicPreview: function (e) {
    var that = this;
    var url = e.currentTarget.dataset.url;
    if (!url) {
      return false;
    }
    var images = new Array();
    if (typeof (e.currentTarget.dataset.type) != "undefined") {
      var imagearr = ["jpg", "bmp", "gif", "png", "jpeg"];
      for (var i = 0; i < that.data.company.fileList.length; i++) {
        var str = that.data.company.fileList[i].fileurl
        var type = (str.substring(str.lastIndexOf(".") + 1, str.length)).toLowerCase();
        if (imagearr.indexOf(type) > -1) {
          images.push(str)
        }
      }
    } else {
      images.push(url)
    }
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: images // 需要预览的图片http链接列表
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