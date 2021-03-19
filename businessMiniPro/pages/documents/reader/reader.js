var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: 0,
    doc: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.id) {
      this.setData({
        id: options.id
      });
      this.getDates()
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      title: this.data.doc.name,
      desc: this.data.doc.profiles,
      path: '/pages/documents/reader/reader?id=' + this.data.id,
      imageUrl: '/static/images/documents/documents.png',
      success: function (res) {
        // 转发成功
        console.log('转发成功')
        //this.setInfo(res);
      },
      fail: function (res) {
        // 转发失败
        console.log('转发失败')
      }
    }
  },

  getDates: function () {
    let that = this;
    util.request(api.documentsDetail, {
      documentsId: that.data.id
    }).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          doc: res.data
        })
        wx.setNavigationBarTitle({
          title: res.data.name
        })
      }
    });
  },
  //预览图片
  topicPreview: function(e) {
    var that = this;
    var url = e.currentTarget.dataset.url;
    if (!url) {
      return false;
    }
    var images = new Array();
    for (var i = 0; i < that.data.doc.pageList.length; i++) {
      images.push(that.data.doc.pageList[i].url)
    }
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: images // 需要预览的图片http链接列表
    })
  }
})