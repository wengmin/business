const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const user = require('../../../services/user.js');
const QQMapWX = require('../../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
Page({
  data: {
    outpage: 0,
    nowpage: 1,
    startX: 0,
    animationMain: null, //正面
    animationBack: null, //背面 
    param: "",
    loginOpenid: "",
    cards: [],
    isZiji: false,
    isCollectBtn: false,
    maskHidden: false,
    leftshow: false,
    rightshow: true,
    showhidebtn: false,
    btnanimation1: null,
  },
  touchstart(e) {
    this.setData({
      startX: e.changedTouches[0].clientX,
    })
  },
  touchend(e) {
    let that = this;
    var id = parseInt(e.currentTarget.dataset.id);
    let startX = this.data.startX;
    let endX = e.changedTouches[0].clientX;

    that.animation_main = wx.createAnimation({
      duration: 500,
      timingFunction: 'linear'
    })
    that.animation_back = wx.createAnimation({
      duration: 500,
      timingFunction: 'linear'
    })

    // 下一页(左滑距离大于30)
    if (startX - endX > 30) {
      //尾页(当前页 等于 总页数)
      if (that.data.nowpage == 3) {
        return;
      };
      that.setData({
        nowpage: id + 1,
      })
      if (that.data.nowpage == 3) {
        setTimeout(function () {
          that.setData({
            outpage: 1,
          })
        }, 250)
        that.animation_main.rotateY(0).step()
        that.animation_back.rotateY(-180).step()
        that.setData({
          animationMain: that.animation_main.export(),
          animationBack: that.animation_back.export(),
        })
      } else {
        that.animation_main.rotateY(-180).step()
        that.animation_back.rotateY(0).step()
        that.setData({
          animationMain: that.animation_main.export(),
          animationBack: that.animation_back.export(),
        })
      }
    }


    // 上一页
    if (endX - startX > 30) {
      if (that.data.nowpage == 1) {
        return;
      };
      if (that.data.nowpage == 3) {
        setTimeout(function () {
          that.setData({
            outpage: 0,
          })
        }, 500)
        that.animation_main.rotateY(-180).step()
        that.animation_back.rotateY(0).step()
        that.setData({
          animationMain: that.animation_main.export(),
          animationBack: that.animation_back.export(),
        })
      } else if (that.data.nowpage == 2) {
        that.setData({
          outpage: 0,
        })
        setTimeout(function () {
          that.animation_main.rotateY(0).step()
          that.animation_back.rotateY(180).step()
          that.setData({
            animationMain: that.animation_main.export(),
            animationBack: that.animation_back.export(),
          })
        }, 100)
      } else {
        setTimeout(function () {
          that.setData({
            outpage: 0,
          })
        }, 500)
        that.animation_main.rotateY(0).step()
        that.animation_back.rotateY(180).step()
        that.setData({
          animationMain: that.animation_main.export(),
          animationBack: that.animation_back.export(),
        })
      }
      that.setData({
        nowpage: id - 1,
      })
    }

    // 移动完成后
    setTimeout(function () {
      if (that.data.nowpage == 3) {
        that.setData({
          leftshow: true,
          rightshow: false
        })
      } else if (that.data.nowpage == 2) {
        that.setData({
          leftshow: true,
          rightshow: true
        })
      } else {
        that.setData({
          leftshow: false,
          rightshow: true
        })
      }
    }, 500)
  },












  onLoad: function (options) {
    wx.stopPullDownRefresh();
    let that = this;
    that.setData({
      loginOpenid: wx.getStorageSync('token')
    });
    if (typeof (options) != "undefined") {
      if (options.scene) {
        let scene = '?' + decodeURIComponent(options.scene);
        that.setData({
          param: util.getQueryString(scene, 'param'),
        })
      } else if (options.param != "undefined" && typeof (options.param) != "undefined") {
        that.setData({
          param: options.param,
        })
      }
    }
    that.getCardInfo();

    qqmapsdk = new QQMapWX({
      key: '3QHBZ-TT4W2-LPIUI-CAGGZ-OK425-HCBBR'
    });
  },
  getOwnCardInfo: function () {
    let that = this;
    util.request(api.CardInfoByOpenID, {
      openid: that.data.loginOpenid
    }).then(function (res) {
      if (res.errno === 0) {
        if (res.data.qrCode) {
          res.data.qrCode = "https://emiaoweb.com/business/upload/" + res.data.qrCode;
        }
        that.setData({
          cards: res.data
        });
      } else {
        console.log("that.data.loginOpenid:" + that.data.loginOpenid + "=>errno:" + res.errno + "=>errmsg:" + res.errmsg)
        // if (res.errmsg == "未注册名片") {
        //   wx.redirectTo({
        //     url: '/pages/card/adduser/adduser',
        //   })
        // }
        wx.navigateTo({
          url: '/pages/ucenter/index/index'
        })
      }
    }).catch((err) => {
      console.log("catch" + err)
      wx.navigateTo({
        url: '/pages/ucenter/index/index'
      })
    });
  },
  getCardInfo: function () {
    let that = this;
    if (that.data.param) {
      // that.setData({
      //     param: '4~6CkgY',
      // })
      util.request(api.CardInfoByParam, {
        param: that.data.param
      }).then(function (res) {
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
    } else {
      if (that.data.loginOpenid) {
        that.getOwnCardInfo();
      } else {
        wx.navigateTo({
          url: '/pages/ucenter/index/index'
        })
      }
    }
  },
  copyText: function (e) {
    let that = this
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
  callPhone: function (e) {
    wx.makePhoneCall({
      phoneNumber: e.currentTarget.dataset.text
    })
  },
  addPhone: function () {
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
  showQrCode: function (e) {
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
      }).then(function (res) {
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
      success: function (res) {
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
  closeQrCode: function () {
    this.setData({
      maskHidden: false
    })
  },
  collect: function () {
    let that = this;
    if (!that.data.loginOpenid) {
      wx.showToast({
        title: "请先登录",
        icon: 'loading',
        duration: 2000
      });
      setTimeout(function () {
        wx.navigateTo({
          url: '/pages/auth/login/login?id=-1&type='
        })
        wx.removeStorageSync('userInfo');
      }, 2000)
    } else {
      //if (!that.data.isCollectBtn) {
      util.request(api.CardSaveCollect, {
        param: that.data.param
      }, 'POST', 'application/x-www-form-urlencoded').then(function (res) {
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
  isCollect: function () {
    let that = this;
    if (that.data.loginOpenid) {
      util.request(api.CardIsCollect, {
        param: that.data.param
      }).then(function (res) {
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
  record: function () {
    let that = this;
    if (that.data.loginOpenid) {
      util.request(api.CardSaveRecord, {
        param: that.data.param
      }, 'POST', 'application/x-www-form-urlencoded').then(function (res) {});
    }
  },
  navigate() {
    wx.showLoading({
      title: '获取中',
    })
    var addres = this.data.cards.province + this.data.cards.city + this.data.cards.county + this.data.cards.address;
    if (!addres) {
      wx.showToast({
        title: "地址为空转换失败",
        icon: 'loading',
        duration: 2000
      });
      return;
    }
    qqmapsdk.geocoder({
      address: addres,
      success: function (res) {
        console.log(res);
        let location = res.result.location
        //使用微信内置地图查看标记点位置，并进行导航
        wx.openLocation({
          type: 'gcj02', // 返回可以用于wx.openLocation的经纬度 
          latitude: location.lat,
          longitude: location.lng,
          scale: 18
        })
      },
      fail: function (res) {
        console.log(res);
        wx.showToast({
          title: "地址转换失败",
          icon: 'loading',
          duration: 2000
        });
      },
      complete: function (res) {
        wx.hideLoading()
      }
    });
  },
  showbtn: function () {
    let that = this;
    if (that.data.showhidebtn) {
      that.setData({
        showhidebtn: false,
        btnanimation1: that.slideupshow(that, 0, 0, 'down')
      })
    } else {
      that.setData({
        showhidebtn: true,
        btnanimation1: that.slideupshow(that, 1, 0, 'up')
      })
    }
  },
  /**
   * 动画实现
   * @method animationShow
   * @param {that} 当前卡片
   * @param {opacity} 透明度
   * @param {delay} 延迟
   * @param {isUp} 移动方向
   */
  slideupshow: function (that, opacity, delay, isUp) {
    let animation = wx.createAnimation({
      duration: 300,
      timingFunction: 'ease',
      delay: delay
    });
    if (isUp == 'down') {
      animation.translateX(0).translateY('180%').opacity(opacity).step()
    } else if (isUp == 'up') {
      animation.translateX(0).translateY(0).opacity(opacity).step()
    } else {
      animation.translateY(0).opacity(opacity).step()
    }
    let params = ''
    params = animation.export()
    return params
  },
})