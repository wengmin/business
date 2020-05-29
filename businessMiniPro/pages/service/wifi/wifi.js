var api = require('../../../config/api.js');
const util = require('../../../utils/util.js');
const app = getApp()

Page({
  data: {
    error: '', //错误提示
    platform: '', //系统 android
    ssid: '', //wifi帐号(必填)
    pass: '', //无线网密码(必填)
    bssid: '', //设备号 自动获取
    success: ''
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
  onLoad: function(options) {
    wx.showLoading({
      title: '连接中',
    });
    if (options.scene) {
      let scene = '?' + decodeURIComponent(options.scene);
      this.setData({
        ssid: util.getQueryString(scene, 'id'),
        pass: util.getQueryString(scene, 'pass'),
      })
      this.checkSystem()
    } else {
      this.getRoom();
      // wx.hideLoading()
      // that.setData({
      //   error: that.data.error + "未获取到账号密码。"
      // })
    }
  },
  getRoom: function() {
    let that = this
    util.request(api.CompanyRoomDetail, {
      roomId: app.globalData.roomId,
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          ssid: res.data.wifiname,
          pass: res.data.wifipass,
        })
        that.checkSystem()
      } else {
        wx.hideLoading()
        util.showErrorToast(res.errmsg);
      }
    })
  },
  checkSystem: function() {
    var that = this;
    //检测手机型号
    wx.getSystemInfo({
      success: function(res) {
        console.log("getSystemInfo=>success." + res);
        var system = '';
        if (res.platform == 'android') system = parseInt(res.system.substr(8));
        if (res.platform == 'ios') system = parseInt(res.system.substr(4));
        if (res.platform == 'android' && system < 6) {
          that.setData({
            error: '手机版本不支持',
          })
          return
        }
        if (res.platform == 'ios' && system < 11.2) {
          that.setData({
            error: '手机版本不支持',
          })
          return
        }
        that.setData({
          platform: res.platform
        })
        that.startWifi();
      },
      fail: function(res) {
        console.log("getSystemInfo=>fail." + res.errMsg);
        wx.hideLoading()
        that.setData({
          error: that.data.error + "系统错误：" + res.errMsg + "。"
        })
      }
    })
  },
  //初始化 Wi-Fi 模块
  startWifi: function() {
    let that = this;
    wx.startWifi({
      success: function() {
        //请求成功连接Wifi
        that.connectWifi();
      },
      fail: function(res) {
        console.log("startWifi=>fail." + res.errMsg);
        wx.hideLoading()
        that.setData({
          error: that.data.error + "初始化Wi-Fi失败：" + res.errMsg + "。"
        })
      }
    })
  },
  connectWifi: function() {
    let that = this;
    wx.connectWifi({
      SSID: that.data.ssid,
      BSSID: that.data.bssid,
      password: that.data.pass,
      success: function(res) {
        console.log("connectWifi=>success." + res.errMsg);
        wx.hideLoading()
        that.setData({
          success: "连接成功"
        })
        // if (that.data.platform == "ios") { // 是否是IOS可通过提前调用getSystemInfo知道
        //   console.log("connectWifi=>success.ios");
        //   wx.hideLoading()
        //   that.setData({
        //     error: that.data.error + "连接Wi-Fi失败。"
        //   })
        //   wx.onWifiConnected(result => {
        //     console.log("onWifiConnected=>success." + result.wifi);
        //     if (result.wifi.SSID === that.data.ssid) {
        //       that.setData({
        //         success: "连接成功",
        //         error:""
        //       })
        //     } else {
        //       that.setData({
        //         error: that.data.error + "连接Wi-Fi失败。"
        //       })
        //     }
        //   })
        // } else {
        //   wx.hideLoading()
        //   that.setData({
        //     success: "连接成功"
        //   })
        // }
      },
      fail: function(res) {
        console.log("connectWifi=>fail." + res.errMsg);
        wx.hideLoading()
        that.setData({
          error: that.data.error + "连接Wi-Fi失败：" + res.errMsg + "。"
        })
      }
    })
  }
})