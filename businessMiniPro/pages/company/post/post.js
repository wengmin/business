const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const user = require('../../../services/user.js');

// pages/company/post/post.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    postId: 0,
    post: [],
    postlist: {},
    company: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (options.postId != "undefined" && typeof(options.postId) != "undefined") {
      this.setData({
        postId: options.postId,
      })
    }
    this.getPost()
  },
  getPost: function() {
    let that = this
    util.request(api.CompanyPostDetail, {
      postId: that.data.postId
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          post: res.data,
        })
        that.getPostList();
        that.getCompany();
      }
    })
  },
  getPostList: function() {
    let that = this
    util.request(api.CompanyPostList, {
      companyId: that.data.post.companyId,
      postId: that.data.postId
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          postlist: res.data,
        })
      }
    })
  },
  getCompany: function() {
    let that = this
    util.request(api.CompanyDetailsByID, {
      companyId: that.data.post.companyId
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          company: res.data,
        })
      }
    })
  },
  copyText: function(e) {
    wx.setClipboardData({
      data: e.currentTarget.dataset.text,
      success() {
        wx.showToast({
          title: '复制成功',
          icon: 'success',
          duration: 1000
        })
      },
      fail() {
        wx.showToast({
          title: '复制失败',
          icon: 'error',
          duration: 1000
        })
      }
    })
  },
  callPhone: function(e) {
    wx.makePhoneCall({
      phoneNumber: e.currentTarget.dataset.text
    })
  },
  addPhone: function() {
    let that = this;
    // 添加到手机通讯录
    wx.addPhoneContact({
      firstName: that.data.post.name, //联系人姓名
      mobilePhoneNumber: that.data.post.mobile, //联系人手机号
      weChatNumber: that.data.post.wechat,
      addressState: that.data.company.province,
      addressCity: that.data.company.city,
      addressStreet: that.data.company.county,
      organization: that.data.company.name,
      title: that.data.post.position,
      email: that.data.post.email,
    })
  },
  //预览图片
  topicPreview: function(e) {
    var that = this;
    var url = e.currentTarget.dataset.url;
    if (!url) {
      return false;
    }
    var images = new Array();
    images.push(url)
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