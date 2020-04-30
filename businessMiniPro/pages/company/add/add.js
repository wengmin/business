var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
// pages/company/add/add.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    company: {
      name: '',
      logo: '',
      introduction: '',
      file: [{
          "fileurl": "http://pic.emiaoweb.com/caiqing/20200430/163912713b9a0.png"
        },
        {
          "fileurl": "http://pic.emiaoweb.com/caiqing/20200409/1132077727fb53.mp4"
        }
      ]
    },

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

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


  changeLogo: function (e) {
    var that = this
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        var tempFilePaths = res.tempFilePaths;
        wx.showLoading({
          title: '上传中',
        })
        //这里是上传操作
        wx.uploadFile({
          url: api.Upload, //里面填写你的上传图片服务器API接口的路径 
          filePath: tempFilePaths[0], //要上传文件资源的路径 String类型 
          name: 'file', //按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数)
          header: {
            "Content-Type": "multipart/form-data" //记得设置
          },
          formData: {
            //和服务器约定的token, 一般也可以放在header中
            'X-Nideshop-Token': wx.getStorageSync('token')
          },
          success: function (res) {
            let datas = JSON.parse(res.data)
            //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
            if (datas.errno === 0) {
              wx.showToast({
                title: '上传成功',
                icon: 'success',
                duration: 2500
              })
              that.setData({
                "company.logo": datas.data
              });
            } else {
              wx.showToast({
                title: '图片上传失败',
                icon: 'success',
                duration: 3000
              })
            }
            wx.hideLoading()
          },
          fail: function () {
            wx.hideLoading()
          }
        })
      }
    })
  },
  changePhoto: function(e) {
    var that = this
    wx.chooseImage({
      count: 9, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function(res) {
        var successUp = 0; //成功
        var failUp = 0; //失败
        var length = res.tempFilePaths.length; //总数
        var count = 0; //第几张
        that.uploadOneByOne(res.tempFilePaths, successUp, failUp, count, length);
      }
    })
  },
  //采用递归的方式上传多张
  uploadOneByOne(imgPaths, successUp, failUp, count, length) {
    var that = this;
    wx.showLoading({
      title: '正在上传第' + count + '个',
    })
    wx.uploadFile({
      url: api.Upload, //仅为示例，非真实的接口地址
      filePath: imgPaths[count],
      name: 'file',
      success: function(res) {
        successUp++; //成功+1
        let resjson = JSON.parse(res.data)
        if (resjson.errno === 0) {
          var obj = that.data.company.file;
          obj.push({
            "fileurl": resjson.data
          })
          that.setData({
            "company.file": obj
          });
        } else {
          failUp++; //失败+1
        }
      },
      fail: function(e) {
        failUp++; //失败+1
      },
      complete: function(e) {
        count++; //下一张
        if (count == length) {
          //上传完毕，作一下提示
          console.log('上传成功' + successUp + ',' + '失败' + failUp);
          wx.showToast({
            title: '上传成功' + successUp,
            icon: 'success',
            duration: 2000
          })
        } else {
          //递归调用，上传下一张
          that.uploadOneByOne(imgPaths, successUp, failUp, count, length);
          console.log('正在上传第' + count + '个');
        }
      }
    })
  }
})