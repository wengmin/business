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
      simpleName: '',
      logo: '',
      introduction: '',
      fileList: []
    },

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    util.request(api.CompanyDetailsByUserID).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          company: res.data
        });
      }
    }).catch((err) => {
      wx.showModal({
        title: "服务连接出错",
        content: "请稍后再访问"
      });
    });
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
  changeImage: function (e) {
    var that = this
    wx.chooseImage({
      count: 9, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        var successUp = 0; //成功
        var failUp = 0; //失败
        var length = res.tempFilePaths.length; //总数
        var count = 0; //第几张
        that.uploadOneByOne(res.tempFilePaths, successUp, failUp, count, length);
      }
    })
  },
  changeVideo: function (e) {
    var that = this
    wx.chooseVideo({
      sourceType: ['album', 'camera'],
      maxDuration: 60,
      camera: 'back',
      success: function (res) {
        var tempFilePath = res.tempFilePath;
        wx.showLoading({
          title: '上传中',
        })
        //这里是上传操作
        wx.uploadFile({
          url: api.Upload, //里面填写你的上传图片服务器API接口的路径 
          filePath: tempFilePath, //要上传文件资源的路径 String类型 
          name: 'file', //按个人情况修改，文件对应的 key,开发者在服务器端通过这个 key 可以获取到文件二进制内容，(后台接口规定的关于图片的请求参数)
          header: {
            "Content-Type": "multipart/form-data" //记得设置
          },
          formData: {
            //和服务器约定的token, 一般也可以放在header中
            'X-Nideshop-Token': wx.getStorageSync('token')
          },
          success: function (res) {
            let resjson = JSON.parse(res.data)
            //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
            if (resjson.errno === 0) {
              var obj = that.data.company.fileList;
              obj.push({
                "fileurl": resjson.data
              })
              that.setData({
                "company.fileList": obj
              });
            } else {
              wx.showToast({
                title: '视频上传失败',
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
      success: function (res) {
        successUp++; //成功+1
        let resjson = JSON.parse(res.data)
        if (resjson.errno === 0) {
          var obj = that.data.company.fileList;
          obj.push({
            "fileurl": resjson.data
          })
          that.setData({
            "company.fileList": obj
          });
        } else {
          failUp++; //失败+1
        }
      },
      fail: function (e) {
        failUp++; //失败+1
      },
      complete: function (e) {
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
  },
  deleteFile: function (e) {
    let that = this
    wx.showModal({
      title: '删除附件',
      content: '是否删除该附件',
      success: function (res) {
        if (res.confirm) {
          var obj = that.data.company.fileList;
          obj.splice(e.target.dataset.index, 1)
          that.setData({
            "company.fileList": obj
          });
        }
      }
    })
  },
  //预览图片
  topicPreview: function (e) {
    var that = this;
    var url = e.currentTarget.dataset.url;
    var images = new Array();
    var imagearr = ["jpg", "bmp", "gif", "png", "jpeg"];
    for (var i = 0; i < that.data.company.fileList.length; i++) {
      var str = that.data.company.fileList[i].fileurl
      var type = (str.substring(str.lastIndexOf(".") + 1, str.length)).toLowerCase();
      if (imagearr.indexOf(type) > -1) {
        images.push(str)
      }
    }
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: images // 需要预览的图片http链接列表
    })
  },
  bindinputValue(event) {
    switch (event.currentTarget.dataset.type) {
      case "name":
        this.setData({
          "company.name": event.detail.value
        });
        break;
      case "simpleName":
        this.setData({
          "company.simpleName": event.detail.value
        });
        break;
      case "introduction":
        this.setData({
          "company.introduction": event.detail.value
        });
        break;
    }
  },
  submit(e) {
    console.log(this.data.company)
    let company = this.data.company;
    if (company.name == '') {
      util.showErrorToast('请输入公司名称');
      return false;
    }
    if (company.introduction == '') {
      util.showErrorToast('请输入公司描述');
      return false;
    }
    wx.showLoading({
      title: '提交中...',
      mask: true
    });
    let that = this;
    let filename = e.detail.value;
    var obj = that.data.company.fileList;
    obj.forEach(function (item, index) {
      item.name = filename["filename" + index]
    })
    that.setData({
      "company.fileList": obj
    });
    util.request(api.CompanySave, {
      logo: company.logo,
      name: company.name,
      simpleName: company.simpleName,
      introduction: company.introduction,
      fileList: company.fileList,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        //wx.hideLoading();
        // wx.showToast({
        //   title: '编辑成功'
        // });
        if (that.data.isFirst) {
          that.setData({
            lastpage: true,
            fromThree: false
          })
        } else {
          wx.redirectTo({
            url: '/pages/card/indexs/indexs?param=',
          })
        }
      } else {
        util.showErrorToast(res.errmsg);
      }
    })
    // .catch((err) => {
    //   util.showErrorToast("api地址错误");
    //   wx.hideLoading();
    // });
  },
})