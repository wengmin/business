var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../services/user.js');
var app = getApp();
Page({
  data: {
    cardInfo: {
      cardId: 0,
      companyName: '',
      position: '',
      realname: '',
      mobile: '',
      photo: '',
      photoRemark: '',
      email: '',
      wechat: '',
      province: '',
      city: '',
      county: '',
      address: '',
      profile: '',
      phone: ''
    },
    isFirst: true,
    tip: true,
    lastpage: false,
    fromOne: false,
    fromTwo: false,
    fromThree: false,
    showOfficial: true,
  },
  onLoad: function (options) {
    var that = this
    let token = wx.getStorageSync('token');
    if (token) {
      that.setData({
        isFirst: false,
        tip: false,
        fromOne: true
      })
      if (options.cardId) {
        that.setData({
          "cardInfo.cardId": options.cardId
        });
        util.request(api.CardDetail, {
          cardId: options.cardId
        }).then(function (res) {
          if (res.errno === 0) {
            if (res.data) {
              that.setData({
                cardInfo: res.data,
                "cardInfo.companyName": res.data.company.name,
                "cardInfo.phone": res.data.company.phone,
                "cardInfo.province": res.data.company.province,
                "cardInfo.city": res.data.company.city,
                "cardInfo.county": res.data.company.county,
                "cardInfo.address": res.data.company.address,
              });
            }
          }
        });
      } else {
        util.request(api.CardDefault, {}).then(function (res) {
          if (res.errno === 0) {
            if (res.data) {
              that.setData({
                cardInfo: res.data,
                "cardInfo.companyName": res.data.company.name,
                "cardInfo.phone": res.data.company.phone,
                "cardInfo.province": res.data.company.province,
                "cardInfo.city": res.data.company.city,
                "cardInfo.county": res.data.company.county,
                "cardInfo.address": res.data.company.address,
              });
            }
          }
        });
      }
    }
  },
  bindinputValue(event) {
    switch (event.currentTarget.dataset.type) {
      case "companyname":
        this.setData({
          "cardInfo.companyName": event.detail.value
        });
        break;
      case "position":
        this.setData({
          "cardInfo.position": event.detail.value
        });
        break;
      case "realname":
        this.setData({
          "cardInfo.realname": event.detail.value
        });
        break;
      case "mobile":
        this.setData({
          "cardInfo.mobile": event.detail.value
        });
        break;
      case "email":
        this.setData({
          "cardInfo.email": event.detail.value
        });
        break;
      case "wechat":
        this.setData({
          "cardInfo.wechat": event.detail.value
        });
        break;
      case "address":
        this.setData({
          "cardInfo.address": event.detail.value
        });
        break;
      case "profile":
        this.setData({
          "cardInfo.profile": event.detail.value
        });
        break;
      case "phone":
        this.setData({
          "cardInfo.phone": event.detail.value
        });
        break;
      case "photoRemark":
        this.setData({
          "cardInfo.photoRemark": event.detail.value
        });
        break;
    }
  },
  onRegionClick(e) {
    console.log('组件传递的内容:', e.detail)
    this.setData({
      "cardInfo.province": e.detail.province_name,
      "cardInfo.city": e.detail.city_name,
      "cardInfo.county": e.detail.county_name
    });
  },
  tipbtn() {
    this.setData({
      fromOne: true,
      tip: false
    })
  },
  nextOne() {
    if (this.data.cardInfo.realname == '') {
      util.showErrorToast('请输入姓名');
      return false;
    }
    if (this.data.cardInfo.position == '') {
      util.showErrorToast('请输入职位');
      return false;
    }
    if (this.data.cardInfo.mobile == '') {
      util.showErrorToast('请输入手机号码');
      return false;
    }
    if (!util.validatePhone(this.data.cardInfo.mobile)) {
      util.showErrorToast('请输入正确手机号码');
      return false;
    }
    this.setData({
      fromOne: false,
      fromTwo: true
    })
  },
  nextTwo() {
    if (this.data.cardInfo.companyName == '') {
      util.showErrorToast('请输入公司名称');
      return false;
    }
    this.setData({
      fromTwo: false,
      fromThree: true
    })
  },
  saveUser: function () {
    let cardInfo = this.data.cardInfo;
    if (cardInfo.realname == '') {
      util.showErrorToast('请输入姓名');
      return false;
    }
    if (cardInfo.position == '') {
      util.showErrorToast('请输入职位');
      return false;
    }
    if (cardInfo.mobile == '') {
      util.showErrorToast('请输入手机号码');
      return false;
    }
    if (!util.validatePhone(cardInfo.mobile)) {
      util.showErrorToast('请输入正确手机号码');
      return false;
    }
    if (cardInfo.companyName == '') {
      util.showErrorToast('请输入公司名称');
      return false;
    }
    wx.showLoading({
      title: '提交中...',
      mask: true,
      success: function () {}
    });
    let that = this;
    util.request(api.CardSave, {
      cardId: cardInfo.cardId,
      photo: cardInfo.photo,
      photoRemark: cardInfo.photoRemark,
      companyName: cardInfo.companyName,
      realname: cardInfo.realname,
      position: cardInfo.position,
      mobile: cardInfo.mobile,
      email: cardInfo.email,
      wechat: cardInfo.wechat,
      profile: cardInfo.profile,
      address: cardInfo.address,
      province: cardInfo.province,
      city: cardInfo.city,
      county: cardInfo.county,
      phone: cardInfo.phone,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        wx.hideLoading();
        // wx.showToast({
        //   title: '编辑成功'
        // });
        // that.setData({
        //   lastpage: true,
        //   fromThree: false
        // })
        wx.redirectTo({
          url: '/pages/ucenter/index/index?param=',
        })
      } else {
        util.showErrorToast(res.errmsg);
      }
    }).catch((err) => {
      util.showErrorToast("api地址错误");
      wx.hideLoading();
    });
  },
  binderror: function (e) {
    console.log(e)
    this.setData({
      showOfficial: false
    })
    // wx.redirectTo({
    //   url: '/pages/ucenter/index/index',
    // })
  },
  //点击图片选择手机相册或者电脑本地图片
  changePhoto: function (e) {
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success(res) {
        const src = res.tempFilePaths[0]
        //  获取裁剪图片资源后，给data添加src属性及其值
        wx.navigateTo({
          url: '/pages/upload/index?src=' + src,
        })
      }
    })
  },
  bindGetUserInfo(e) {
    let that = this
    if (e.detail.userInfo) {
      //用户按了允许授权按钮
      user.loginByWeixin(e.detail).then(res => {
        let userInfo = wx.getStorageSync('userInfo');
        app.globalData.userInfo = userInfo.userInfo;
        app.globalData.token = res.data.openid;
        util.request(api.CardDefault, {}).then(function (res) {
          if (res.errno === 0) {
            if (res.data) {
              that.setData({
                cardInfo: res.data,
                "cardInfo.companyName": res.data.company.name,
                "cardInfo.phone": res.data.company.phone,
                "cardInfo.province": res.data.company.province,
                "cardInfo.city": res.data.company.city,
                "cardInfo.county": res.data.company.county,
                "cardInfo.address": res.data.company.address,
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
        this.setData({
          fromOne: true,
          tip: false
        })
      }).catch((err) => {
        console.log(err)
      });
    } else {
      //用户按了拒绝按钮
      wx.showModal({
        title: '警告通知',
        content: '您点击了拒绝授权,将无法正常显示个人信息,点击确定重新获取授权。',
        success: function (res) {
          if (res.confirm) {
            wx.openSetting({
              success: (res) => {
                if (res.authSetting["scope.userInfo"]) { ////如果用户重新同意了授权登录
                  user.loginByWeixin(e.detail).then(res => {
                    let userInfo = wx.getStorageSync('userInfo');
                    app.globalData.userInfo = userInfo.userInfo;
                    app.globalData.token = res.data.openid;
                    this.setData({
                      fromOne: true,
                      tip: false
                    })
                  }).catch((err) => {
                    console.log(err)
                  });
                }
              }
            })
          }
        }
      });
    }
  },
  onReady: function () {},
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },
  onShareAppMessage: function (res) {},
})