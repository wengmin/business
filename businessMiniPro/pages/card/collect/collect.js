const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');

Page({
  data: {
    cardList: [],
    page: 1,
    size: 10,
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
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.data.cardList = [];
    this.onLoad();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    var that = this;
    //that.data.cardList=[];
    if (that.data.page < that.data.pages) {
      that.setData({
        page: that.data.page + 1
      });
      that.getDates();
    }
  },
  getDates: function() {
    let that = this;
    util.request(api.CardListCollect, {
      page: that.data.page,
      size: that.data.size
    }).then(function(res) {
      if (res.errno === 0) {
        var resdate = that.data.cardList.concat(res.data.list);
        if (resdate.length == 0) {
          that.setData({
            cardList: resdate,
            pages: res.data.totalPage,
            nonedata: false,
            moredata: false
          })
        } else {
          for (var i = 0; i < resdate.length; i++) {
            resdate[i].createTime = util.formatTime(new Date(resdate[i].createTime))
          }
          if (res.data.currPage === res.data.totalPage) {
            that.setData({
              cardList: resdate,
              pages: res.data.totalPage,
              nonedata: true,
              moredata: false
            })
          } else {
            that.setData({
              cardList: resdate,
              pages: res.data.totalPage,
              nonedata: false,
              moredata: true
            })
          }
        }
      }
    });
  }
})