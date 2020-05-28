var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
// pages/company/staffBind/staffBind.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    companyId: 0,
    openid: wx.getStorageSync('token'),
    name: '',
    mobile: '',
    avatar: '',
    disabled: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    //禁止分享转发
    wx.hideShareMenu()
    // 页面显示
    if (!this.data.openid) {
      wx.showToast({
        title: '请先登录',
        icon: 'none',
        duration: 2000
      })
      wx.redirectTo({
        url: '/pages/auth/login/login?id=-6'
      })
      wx.removeStorageSync('userInfo');
      return;
    }
    let that = this;
    that.setData({
      companyId: options.companyId
    })
    util.request(api.UserDetailByOpenID, {
      openid: that.data.openid
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          avatar: res.data.avatar
        })
      } else {
        util.showErrorToast(res.errmsg);
      }
    })
  },
  bindinputValue(event) {
    switch (event.currentTarget.dataset.type) {
      case "name":
        this.setData({
          name: event.detail.value
        });
        break;
      case "mobile":
        this.setData({
          mobile: event.detail.value
        });
        break;
    }
  },
  bindStaff() {
    let that = this;
    if (that.data.companyId == '0') {
      util.showErrorToast('参数格式有误');
      return false;
    }
    if (that.data.name == '') {
      util.showErrorToast('请输入姓名');
      return false;
    }
    if (that.data.mobile == '') {
      util.showErrorToast('请输入手机号码');
      return false;
    }
    util.request(api.CompanyStaffBind, {
      companyId: that.data.companyId,
      name: that.data.name,
      mobile: that.data.mobile
    }, 'POST').then(function(res) {
      if (res.errno === 0) {
        that.setData({
          disabled: true
        })
        wx.hideLoading();
        wx.showToast({
          title: '绑定成功'
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