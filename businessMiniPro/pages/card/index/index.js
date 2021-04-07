const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const user = require('../../../services/user.js');
const QQMapWX = require('../../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
Page({
  data: {
    loginOpenid: "",
    currentTab: 0,
    param: "",
    cards: [],
    isZiji: false,
    isCollectBtn: false,
    maskHidden: false,
    leftshow: false,
    rightshow: true,
    showhidebtn: false,
    btnanimation1: null,

    folderDocList: [],

    leftMenuWidth: 0,
    leftMenuHeight: 0,
    leftMenuTop: 0,
    leftMenuLeft: 0,
    ucheight: 0
  },
  onReady: function () {
    let that = this;
    // 胶囊位置信息
    let res = wx.getMenuButtonBoundingClientRect();
    wx.getSystemInfo({
      success: function (re) {
        that.setData({
          leftMenuLeft: re.windowWidth - res.right,
        })
      },
    })
    that.setData({
      leftMenuWidth: res.width,
      leftMenuHeight: res.height,
      leftMenuTop: res.top,
      ucheight: res.height + res.top + 6,
    })
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
    util.request(api.CardDefault, {}).then(function (res) {
      if (res.errno === 0) {
        if (!res.data.realname) {
          wx.navigateTo({
            url: '/pages/card/edit/edit'
          })
        }
        that.setData({
          cards: res.data
        });
        that.getFolderDocList(res.data.userId);
      } else {
        console.log("that.data.loginOpenid:" + that.data.loginOpenid + "=>errno:" + res.errno + "=>errmsg:" + res.errmsg)
        wx.navigateTo({
          url: '/pages/card/edit/edit'
        })
      }
    }).catch((err) => {
      console.log("catch" + err)
      wx.navigateTo({
        url: '/pages/card/edit/edit'
      })
    });
  },
  getCardInfo: function () {
    let that = this;
    if (that.data.param) {
      util.request(api.CardInfoByParam, {
        param: that.data.param
      }).then(function (res) {
        if (res.errno === 0) {
          that.setData({
            cards: res.data
          });
          that.getFolderDocList(res.data.userId);
          wx.hideLoading();
        } else {
          that.getOwnCardInfo();
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
          url: '/pages/card/edit/edit'
        })
      }
    }
  },
  onShareAppMessage: function (res) {
    return {
      title: "您好，我是" + this.data.cards.realname + "，请惠存我的名片",
      desc: '销擎名片管理',
      path: '/pages/card/index/index?param=' + this.data.cards.param,
      imageUrl: this.data.cards.photo ? this.data.cards.photo : '/static/images/card/p_card.jpg',
      success: (res) => {
        util.request(api.SaveShare, {}, 'POST', 'application/x-www-form-urlencoded').then(function (ress) {});
      },
      fail: (res) => {
        console.log("转发失败", res);
      }
    }
  },
  copyText: function (e) {
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
    if (wx.getStorageSync('token')) {
      util.request(api.CardSaveRecord, {
        param: that.data.param
      }, 'POST', 'application/x-www-form-urlencoded').then(function (res) {});
    }
  },
  //预览图片
  topicPreview: function (e) {
    var that = this;
    var url = e.currentTarget.dataset.url;
    if (!url) {
      return false;
    }
    var images = new Array();
    if (typeof (e.currentTarget.dataset.type) != "undefined") {
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
  },
  navigate() {
    wx.showLoading({
      title: '获取中',
    })
    var addres = this.data.cards.company.province + this.data.cards.company.city + this.data.cards.company.county + this.data.cards.company.address;
    if (!addres) {
      wx.showToast({
        title: "未填写地址",
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
  /**
   * 点击tab切换
   * @param {*} e 
   */
  itemChange: function (e) {
    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      this.setData({
        currentTab: e.target.dataset.current
      })
    }
  },
  /**
   * 滑动切换tab
   * @param {*} e 
   */
  indexChange: function (e) {
    if (e.detail.current == 0) {
      this.setData({
        leftshow: false,
        rightshow: true,
      });
    } else {
      this.setData({
        leftshow: true,
        rightshow: false,
      });
    }
    this.setData({
      currentTab: e.detail.current,
    });
  },
  /**
   * 获取文档列表
   */
  getFolderDocList: function (userId) {
    let that = this;
    util.request(api.documentsFolderDocList, {
      userId: userId
    }).then(function (res) {
      if (res.errno === 0) {
        for (let i = 0; i < res.data.length; i++) {
          if (i == 0)//if (res.data[i].docList.length > 0)
            res.data[i].show = true
          else
            res.data[i].show = false
        }
        that.setData({
          folderDocList: res.data
        })
      }
    });
  },
  showhide: function (e) {
    let that = this
    let index = e.currentTarget.dataset.index
    let dataList = that.data.folderDocList
    dataList[index].show = !dataList[index].show
    if (dataList[index].show) {
      that.packUp(dataList, index);
    }
    that.setData({
      folderDocList: dataList
    })
  },
  packUp(data, index) {
    for (let i = 0, len = data.length; i < len; i++) {
      if (index != i) {
        data[i].show = false
      }
    }
  },
})