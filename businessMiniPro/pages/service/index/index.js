var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentTab: 0,
    swiperHeight: '',
    service: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let that = this
    util.request(api.MacrosByType, {
      value: "serviceClass"
    }).then(function(first) {
      if (first.errno === 0) {
        var list = new Array();
        for (var i = 0; i < first.data.length; i++) {
          var name = first.data[i].name,
            value = first.data[i].value;
          list.push({
            "name": name,
            "value": value
          });
        }
        that.setData({
          service: list
        })
      }
    })
    this.getSwiper(0)
  },
  getItemList: function(name, value) {
    // util.request(api.MacrosByType, {
    //   value: value
    // }).then(function (res) {
    //   if (res.errno === 0) {
    //     list.push({
    //       "name": name,
    //       "value": value,
    //       "list": res.data
    //     });
    //   }
    // })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

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
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  scanWifi: function() {
    wx.scanCode({
      onlyFromCamera: true, // 只允许从相机扫码
      success(res) {
        console.log(res)
        console.log(res.path)
        wx.navigateTo({
          url: "../../../" + res.path,
        })
      }
    })
  },
  itemChange: function(e) { // 点击tab切换
    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      this.setData({
        currentTab: e.target.dataset.current
      })
    }
    this.getSwiper(e.target.dataset.current)
  },
  indexChange: function(e) { // 滑动切换tab
    this.setData({
      currentTab: e.detail.current,
    });
    this.getSwiper(e.detail.current)
  },
  getSwiper(n) { //获取swiperItem的高度,异步给swiper设置高度
    let select = '.swiperitem' + n;
    let that = this;
    wx.createSelectorQuery().select(select).boundingClientRect(function(rect) {
      let res = rect.height + 'px'
      console.log(res);
      that.setData({
        swiperHeight: res
      })
    }).exec();
  },
})