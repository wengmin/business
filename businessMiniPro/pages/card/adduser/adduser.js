var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();
Page({
  data: {
    user: {
      companyName: '',
      position: '',
      realname: '',
      mobile: '',
      photo: '',
      email: '',
      wechat: '',
      province: '',
      city: '',
      county: '',
      address: '',
      profile: '',
      telephone: ''
    },
    isFirst: true,
    tip: true,
    lastpage: false,
    fromOne: false,
    fromTwo: false,
    fromThree: false,
    rcardname: '',
    rcardava: ''
  },
  onShareAppMessage: function(res) {},
  onLoad: function(options) {
    var that = this
    if (options.name) {
      that.setData({
        rcardname: options.name,
        rcardava: options.ava
      });
    }
    let token = wx.getStorageSync('token');
    if (!token) {
      wx.showToast({
        title: "请先登录",
        icon: 'loading',
        duration: 2000
      });
      setTimeout(function() {
        wx.navigateTo({
          url: '/pages/auth/login/login?id=-3&type='
        })
        wx.removeStorageSync('userInfo');
      }, 2000)
    } else {
      util.request(api.CardInfoByOpenID, {
        openid: wx.getStorageSync('token')
      }).then(function(res) {
        if (res.errno === 0) {
          if (res.data) {
            that.setData({
              user: res.data
            });
          }
          if (res.data.realname) {
            that.setData({
              isFirst: false,
              tip: false,
              fromOne: true
            })
          }
          wx.hideLoading();
        }
      });
    }
  },
  bindinputValue(event) {
    switch (event.currentTarget.dataset.type) {
      case "companyname":
        this.setData({
          "user.companyName": event.detail.value
        });
        break;
      case "position":
        this.setData({
          "user.position": event.detail.value
        });
        break;
      case "realname":
        this.setData({
          "user.realname": event.detail.value
        });
        break;
      case "mobile":
        this.setData({
          "user.mobile": event.detail.value
        });
        break;
      case "email":
        this.setData({
          "user.email": event.detail.value
        });
        break;
      case "wechat":
        this.setData({
          "user.wechat": event.detail.value
        });
        break;
      case "address":
        this.setData({
          "user.address": event.detail.value
        });
        break;
      case "profile":
        this.setData({
          "user.profile": event.detail.value
        });
        break;
      case "telephone":
        this.setData({
          "user.telephone": event.detail.value
        });
        break;
    }
  },
  onRegionClick(e) {
    console.log('组件传递的内容:', e.detail)
    this.setData({
      "user.province": e.detail.province_name,
      "user.city": e.detail.city_name,
      "user.county": e.detail.county_name
    });
  },
  tipbtn() {
    this.setData({
      fromOne: true,
      tip: false
    })
  },
  nextOne() {
    if (this.data.user.companyName == '') {
      util.showErrorToast('请输入公司名称');
      return false;
    }
    if (this.data.user.position == '') {
      util.showErrorToast('请输入职位');
      return false;
    }
    this.setData({
      fromOne: false,
      fromTwo: true
    })
  },
  nextTwo() {
    if (this.data.user.realname == '') {
      util.showErrorToast('请输入姓名');
      return false;
    }
    if (this.data.user.mobile == '') {
      util.showErrorToast('请输入手机号码');
      return false;
    }
    if (!util.validatePhone(this.data.user.mobile)) {
      util.showErrorToast('请输入正确手机号码');
      return false;
    }
    this.setData({
      fromTwo: false,
      fromThree: true
    })
  },
  saveUser() {
    console.log(this.data.user)
    let user = this.data.user;
    if (user.companyName == '') {
      util.showErrorToast('请输入公司名称');
      return false;
    }
    if (user.position == '') {
      util.showErrorToast('请输入职位');
      return false;
    }
    if (user.realname == '') {
      util.showErrorToast('请输入姓名');
      return false;
    }
    if (user.mobile == '') {
      util.showErrorToast('请输入手机号码');
      return false;
    }
    if (!util.validatePhone(user.mobile)) {
      util.showErrorToast('请输入正确手机号码');
      return false;
    }
    wx.showLoading({
      title: '提交中...',
      mask: true,
      success: function() {

      }
    });
    // wx.showLoading({
    //   title: '提交中...',
    //   mask: true,
    // });
    // if (user.city == 0) {
    //   util.showErrorToast('请输入省市区');
    //   return false;
    // }
    // if (user.address == '') {
    //   util.showErrorToast('请输入详细地址');
    //   return false;
    // }
    let that = this;
    util.request(api.CardUserSave, {
      photo: user.photo,
      companyName: user.companyName,
      realname: user.realname,
      position: user.position,
      mobile: user.mobile,
      email: user.email,
      wechat: user.wechat,
      profile: user.profile,
      address: user.address,
      province: user.province,
      city: user.city,
      county: user.county,
      telephone: user.telephone,
    }, 'POST').then(function(res) {
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
            url: '/pages/card/index/index?param=',
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
  lastpageBtn() {
    wx.redirectTo({
      url: '/pages/card/index/index?param=',
    })
  },
  //点击图片选择手机相册或者电脑本地图片
  changePhoto: function(e) {
    var _this = this
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function(res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
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
          success: function(res) {
            let datas = JSON.parse(res.data)
            //当调用uploadFile成功之后，再次调用后台修改的操作，这样才真正做了修改头像
            if (datas.errno === 0) {
              wx.showToast({
                title: '上传成功',
                icon: 'success',
                duration: 2500
              })
              _this.setData({
                "user.photo": datas.data
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
          fail: function() {
            wx.hideLoading()
          }
        })
      }
    })
  },
  onReady: function() {},
  onShow: function() {
    // 页面显示
  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  }
})