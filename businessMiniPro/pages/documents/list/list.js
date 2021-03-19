var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    list: [],
    page: 1,
    size: 5,
    pages: 0,
    nonedata: false,
    moredata: false
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.stopPullDownRefresh();
    this.getDates()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.data.list = [];
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
    //that.data.list=[];
    if (that.data.page < that.data.pages) {
      that.setData({
        page: that.data.page + 1
      });
      that.getDates();
    }
  },
  
  getDates: function() {
    let that = this;
    util.request(api.documentsList, {
      page: that.data.page,
      size: that.data.size
    }).then(function(res) {
      if (res.errno === 0) {
        var resdate = that.data.list.concat(res.data.list);
        that.setData({
          list: resdate,
          pages: res.data.totalPage
        })
        if (resdate.length == 0) {
          that.setData({
            nonedata: false,
            moredata: false
          })
        } else {
          if (res.data.currPage === res.data.totalPage) {
            that.setData({
              nonedata: true,
              moredata: false
            })
          } else {
            that.setData({
              nonedata: false,
              moredata: true
            })
          }
        }
      }
    });
  }
})