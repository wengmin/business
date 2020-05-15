const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const user = require('../../../services/user.js');

//获取应用实例
const app = getApp()
Page({
  data: {
    param: "",
    loginOpenid: "",
    cards: [],
    mycards: [],
    maskHidden: false,
    isNewUser: true,
    isZiji: false,
    isCollectBtn: false,
    shareUserId: 0,
    isComfileshowhide: false
  },
  onLoad: function(options) {
    wx.stopPullDownRefresh();
    let that = this;
    that.setData({
      loginOpenid: wx.getStorageSync('token')
    });
    if (typeof(options) != "undefined") {
      if (options.scene) {
        let scene = '?' + decodeURIComponent(options.scene);
        that.setData({
          param: util.getQueryString(scene, 'param'),
        })
        app.globalData.param = that.data.param;
      } else if (options.param != "undefined" && typeof(options.param) != "undefined") {
        that.setData({
          param: options.param,
        })
        app.globalData.param = options.param;
      } else if (app.globalData.param != "undefined" && typeof(app.globalData.param) != "undefined" && app.globalData.param != '') {
        that.setData({
          param: app.globalData.param,
        })
      }
    }
    that.getOwnCardInfo();
  },
  onShow: function() {
    // 页面显示
    //this.getOwnCardInfo();
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
  onPullDownRefresh: function() {
    // 页面相关事件处理函数--监听用户下拉动作
    this.onLoad(); //重新加载onLoad()
  },

  onShareAppMessage: function(res) {
    if (res.from == 'button') {
      if (res.target.dataset.sharetype == "mycard") {
        console.log("share:" + this.data.mycards.param)
        return {
          title: "您好，我是" + this.data.mycards.realname + "，请惠存我的名片",
          desc: '销擎名片管理',
          path: '/pages/card/index/index?param=' + this.data.mycards.param,
          imageUrl: '/static/images/card/p_card.jpg',
          success: function(res) {
            // 转发成功
            console.log('转发成功')
            //this.setInfo(res);
          },
          fail: function(res) {
            // 转发失败
            console.log('转发失败')
          }
        }
      }
    }
    console.log("share:" + this.data.cards.param)
    return {
      title: "您好，我是" + this.data.cards.realname + "，请惠存我的名片",
      desc: '销擎名片管理',
      path: '/pages/card/index/index?param=' + this.data.cards.param,
      success: function(res) {
        // 转发成功
        console.log('转发成功')
      },
      fail: function(res) {
        // 转发失败
        console.log('转发失败')
      }
    }
  },
  getOwnCardInfo: function() {
    let that = this;
    if (that.data.loginOpenid) {
      util.request(api.CardInfoByOpenID, {
        openid: that.data.loginOpenid
      }).then(function(res) {
        if (res.errno === 0) {
          if (res.data.realname) {
            that.setData({
              isNewUser: false,
            });
          }
          if (res.data.qrCode) {
            res.data.qrCode = "https://emiaoweb.com/business/upload/" + res.data.qrCode;
          }
          that.setData({
            mycards: res.data,
          });
          if (!that.data.param || that.data.param == res.data.param) {
            that.setData({
              param: res.data.param,
              cards: res.data,
              isZiji: true
            });
            wx.hideLoading();
          } else {
            that.getCardInfo()
          }
        } else {
          console.log("that.data.loginOpenid:" + that.data.loginOpenid + "=>errno:" + res.errno + "=>errmsg:" + res.errmsg)
          // if (res.errmsg == "未注册名片") {
          //   wx.redirectTo({
          //     url: '/pages/card/adduser/adduser',
          //   })
          // }
          that.getCardInfo()
        }
      }).catch((err) => {
        that.getCardInfo()
        console.log("catch" + err)
      });
    } else {
      that.getCardInfo()
    }
  },
  getCardInfo: function() {
    let that = this;
    if (!that.data.param) {
      that.setData({
        param: '4~6CkgY',
      })
    }
    util.request(api.CardInfoByParam, {
      param: that.data.param
    }).then(function(res) {
      if (res.errno === 0) {
        if (res.data.qrCode) {
          res.data.qrCode = "https://emiaoweb.com/business/upload/" + res.data.qrCode;
        }
        that.setData({
          cards: res.data
        });
        wx.hideLoading();
        that.isCollect()
        that.record()
      } else {
        util.showErrorToast(res.errmsg);
      }
    }).catch((err) => {
      wx.hideLoading();
      wx.showModal({
        title: "服务连接出错",
        content: "请卸载小程序，稍后再访问"
      });
    });
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
        param: that.data.param
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
  saveQRCode() {
    let that = this
    wx.downloadFile({
      url: that.data.cards.qrCode,
      success: function(res) {
        if (res.statusCode === 200) {
          let img = res.tempFilePath;
          wx.saveImageToPhotosAlbum({
            filePath: img,
            success(res) {
              wx.showToast({
                title: '保存成功',
                icon: 'success',
                duration: 2000
              })
              that.setData({
                maskHidden: false
              })
            },
            fail(res) {
              util.showErrorToast('保存失败');
            }
          });
        } else {
          util.showErrorToast('下载失败');
        }
      }
    });
  },
  //关闭
  closeQrCode: function() {
    this.setData({
      maskHidden: false
    })
  },
  collect: function() {
    let that = this;
    if (!that.data.loginOpenid) {
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
      //if (!that.data.isCollectBtn) {
      util.request(api.CardSaveCollect, {
        param: that.data.param
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
              title: '收藏到名片夹'
            });
          }
        } else {
          util.showErrorToast(res.errmsg);
        }
      });
      // }
      //  else {
      //   wx.showToast({
      //     title: '已收藏'
      //   });
      // }
    }
  },
  isCollect: function() {
    let that = this;
    if (that.data.loginOpenid) {
      util.request(api.CardIsCollect, {
        param: that.data.param
      }).then(function(res) {
        if (res.errno === 0) {
          if (res.data > 0) {
            that.setData({
              isCollectBtn: true
            })
          }
        }
      });
    }
  },
  record: function() {
    let that = this;
    if (that.data.loginOpenid) {
      util.request(api.CardSaveRecord, {
        param: that.data.param
      }, 'POST', 'application/x-www-form-urlencoded').then(function(res) {});
    }
  },
  backMeCard: function() {
    this.setData({
      param: ''
    })
    this.getOwnCardInfo()
  },
  comfileshowhide: function() {
    if (this.data.isComfileshowhide) {
      this.setData({
        isComfileshowhide: false
      })
    } else {
      this.setData({
        isComfileshowhide: true
      })
    }
  },
  //预览图片
  topicPreview: function(e) {
    var that = this;
    var url = e.currentTarget.dataset.url;
    var images = new Array();
    if (typeof(e.currentTarget.dataset.type) != "undefined") {
      var imagearr = ["jpg", "bmp", "gif", "png", "jpeg"];
      for (var i = 0; i < that.data.cards.company.fileList.length; i++) {
        var str = that.data.cards.company.fileList[i].fileurl
        var type = (str.substring(str.lastIndexOf(".") + 1, str.length)).toLowerCase();
        if (imagearr.indexOf(type) > -1) {
          images.push(str)
        }
      }
    } else {
      images.push(url)
    }
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: images // 需要预览的图片http链接列表
    })
  }
})