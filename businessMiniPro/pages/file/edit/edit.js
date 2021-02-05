var api = require('../../../config/api.js');

// pages/file/edit/edit.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  chooseMessageFile: function (e) {
      var that = this;
      wx.chooseMessageFile({
        count: 1,
        type: 'file',
        success(res) {
          var filename = res.tempFiles[0].name
          console.info(filename);
          that.setData({filename:filename});
   
   
          wx.uploadFile({
            url: api.UploadFile,
            filePath: res.tempFiles[0].path,
            name: 'file',
            success(res) {
              console.info(res);
              //json字符串 需用JSON.parse 转
            }
          })
   
   
   
        }
      });
    }
})