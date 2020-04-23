const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const user = require('../../../services/user.js');

//获取应用实例
const app = getApp()
Page({
  data: {
    openid: "",
    cards: [],
    mycards: [],
    maskHidden: false,
    showCreate: false,
    isZiji: false,
    isCollectBtn: false
  },
  onShareAppMessage: function(res) {
    if (res.target.dataset.sharetype == "mycard") {
      return {
        title: this.data.mycards.realname ? this.data.mycards.realname + '的名片' : "销擎名片管理",
        desc: '销擎名片管理',
        path: '/pages/card/index/index?userId=' + this.data.mycards.userId,
        imageUrl: this.data.mycards.photo,
        success: function(res) {
          // 转发成功
          console.log('转发成功')
        },
        fail: function(res) {
          // 转发失败
          console.log('转发失败')
        }
      }
    } else if (res.target.dataset.sharetype == "card") {
      console.log("share:" + this.data.cards.userId)
      return {
        title: this.data.cards.realname ? this.data.cards.realname + '的名片' : "销擎名片管理",
        desc: '销擎名片管理',
        path: '/pages/card/index/index?userId=' + this.data.cards.userId,
        imageUrl: this.data.cards.photo,
        success: function(res) {
          // 转发成功
          console.log('转发成功')
        },
        fail: function(res) {
          // 转发失败
          console.log('转发失败')
        }
      }
    } else {
      console.log("share:" + this.data.cards.userId)
      return {
        title: this.data.cards.realname ? this.data.cards.realname + '的名片' : "销擎名片管理",
        desc: '销擎名片管理',
        path: '/pages/card/index/index?userId=' + this.data.cards.userId,
      }
    }
  },
  getCardInfoByToken: function() {
    let that = this;
    let token = wx.getStorageSync('token');
    util.request(api.CardInfoByOpenID, {
      openid: that.data.openid
    }).then(function(res) {
      if (res.errno === 0) {
        if (!res.data.realname) {
          that.setData({
            showCreate: true,
          });
        }
        if (res.data.qrCode) {
          res.data.qrCode = "https://emiaoweb.com/business/upload/" + res.data.qrCode;
        }
        if (that.data.openid == token) {
          that.setData({
            openid: res.data.openid,
            mycards: res.data,
            cards: res.data,
            isZiji: true
          });
        } else {
          that.isCollect()
          that.record()
          that.setData({
            cards: res.data
          });
        }
      }
    });
  },
  onLoad: function(options) {
    let that = this;
    if (options.scene) {
      let scene = '?' + decodeURIComponent(options.scene);
      that.setData({
        openid: util.getQueryString(scene, 'openid'),
      })
      app.globalData.openid = that.data.openid;
    } else if (options.openid != "undefined" && typeof(options.openid) != "undefined") {
      that.setData({
        openid: options.openid,
      })
      app.globalData.openid = options.openid;
    } else if (app.globalData.openid != "undefined" && typeof(app.globalData.openid) != "undefined" && app.globalData.openid != 0) {
      that.setData({
        openid: app.globalData.openid,
      })
    } else {
      that.setData({
        openid: 'oMn0a4xHKn3wzkU4qFHFz3bRtm2Y',
      })
    }
    that.getCardInfoByToken();
  },
  onShow: function() {
    // 页面显示
    this.getCardInfoByToken();
  },
  onReady: function() {
    // 页面渲染完成
  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  },
  copyText: function(e) {
    wx.setClipboardData({
      data: e.currentTarget.dataset.text,
      success() {
        wx.showToast({
          title: '复制成功',
          icon: 'success',
          duration: 1000
        })
      },
      fail() {
        wx.showToast({
          title: '复制失败',
          icon: 'error',
          duration: 1000
        })
      }
    })
  },
  callPhone: function(e) {
    wx.makePhoneCall({
      phoneNumber: e.currentTarget.dataset.text
    })
  },
  addPhone: function() {
    let that = this;
    // 添加到手机通讯录
    wx.addPhoneContact({
      firstName: that.data.cards.realname, //联系人姓名
      mobilePhoneNumber: that.data.cards.mobile, //联系人手机号
      weChatNumber: that.data.cards.wechat,
      addressState: that.data.cards.province,
      addressCity: that.data.cards.city,
      addressStreet: that.data.cards.county,
      organization: that.data.cards.companyName,
      title: that.data.cards.position,
      email: that.data.cards.email,
    })
  },
  showQrCode: function(e) {
    let that = this;
    if (that.data.cards.qrCode) {
      this.setData({
        maskHidden: true
      });
    } else {
      wx.showLoading({
        title: '生成中',
      })
      util.request(api.CreateCardQrCode, {
        openid: that.data.openid
      }).then(function(res) {
        if (res.errno === 0) {
          if (res.data) {
            that.setData({
              maskHidden: true,
              "cards.qrCode": "https://emiaoweb.com/business/upload/" + res.data
            })
          }
          wx.hideLoading()
        }
      });
    }
  },
  //关闭
  closeQrCode: function() {
    this.setData({
      maskHidden: false
    })
  },
  collect: function() {
    let that = this;
    let token = wx.getStorageSync('token');
    if (!token) {
      wx.showToast({
        title: "请先登录",
        icon: 'loading',
        duration: 2000
      });
      setTimeout(function() {
        wx.navigateTo({
          url: '/pages/auth/login/login?id=-1&type='
        })
        wx.removeStorageSync('userInfo');
      }, 2000)
    } else {
      if (!that.data.isCollectBtn) {
        util.request(api.CardSaveCollect, {
          openid: that.data.openid
        }, 'POST', 'application/x-www-form-urlencoded').then(function(res) {
          if (res.errno === 0) {
            if (that.data.isCollectBtn) {
              that.setData({
                isCollectBtn: false
              })
              wx.showToast({
                title: '取消收藏成功'
              });
            } else {
              that.setData({
                isCollectBtn: true
              })
              wx.showToast({
                title: '收藏成功'
              });
            }
          } else {
            util.showErrorToast(res.errmsg);
          }
        });
      } else {
        wx.showToast({
          title: '已收藏'
        });
      }
    }
  },
  isCollect: function() {
    let that = this;
    util.request(api.CardIsCollect, {
      openid: that.data.openid
    }).then(function(res) {
      if (res.errno === 0) {
        if (res.data > 0) {
          that.setData({
            isCollectBtn: true
          })
        }
      }
    });
  },
  record: function() {
    let that = this;
    let token = wx.getStorageSync('token');
    if (token) {
      util.request(api.CardSaveRecord, {
        openid: that.data.openid
      }, 'POST', 'application/x-www-form-urlencoded').then(function(res) {});
    }
  },
  backMeCard: function() {
    this.setData({
      openid: ''
    })
    this.getCardInfoByToken()
  }
})