var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
const app = getApp()

Page({
  /**
   * 页面的初始数据
   */
  data: {
    //roomId: 0,
    isShow: true,
    room: [],
    currentTab: 0,
    swiperHeight: '',
    service: [],
    addservice: {
      tagList: [],
      remark: '',
      appointmentTime: ''
    },
    startDate: util.formatTime(new Date()),
    endDate: util.formatTime(new Date(+new Date() + 2 * 1000 * 60 * 60 * 24))
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    // if (options.roomId != "undefined" && typeof(options.roomId) != "undefined") {
    //   this.setData({
    //     roomId: options.roomId,
    //   })
    // }
    if (app.globalData.roomId > 0) {
      let token = wx.getStorageSync('token');
      // 页面显示
      if (!token) {
        wx.showToast({
          title: '请先登录',
          icon: 'none',
          duration: 2000
        })
        wx.redirectTo({
          url: '/pages/auth/login/login?id=-5&param=' + options.roomId
        })
        wx.removeStorageSync('userInfo');
        return;
      }
      this.getRoom()
    } else {
      this.setData({
        isShow: false
      })
    }
  },
  getRoom: function() {
    let that = this
    util.request(api.CompanyRoomDetail, {
      roomId: app.globalData.roomId,
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          room: res.data,
        })
        that.getService();
      }
    })
  },
  getService: function() {
    let that = this
    util.request(api.CompanyServiceGroup, {
      companyId: that.data.room.companyId
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          service: res.data,
        })
        that.getSwiper(0)
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {},
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {},

  // scanWifi: function() {
  //   wx.scanCode({
  //     onlyFromCamera: true, // 只允许从相机扫码
  //     success(res) {
  //       console.log(res)
  //       console.log(res.path)
  //       wx.navigateTo({
  //         url: "../../../" + res.path,
  //       })
  //     }
  //   })
  // },
  scanRoom: function() {
    wx.scanCode({
      onlyFromCamera: true, // 只允许从相机扫码
      success(res) {
        console.log(res)
        console.log(res.path)
        wx.navigateTo({
          url:"/"+ res.path,
        })
      }
    })
  },
  itemChange: function(e) { // 点击tab切换
    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      this.setData({
        currentTab: e.target.dataset.current
      })
    }
    this.getSwiper(e.target.dataset.current)
  },
  indexChange: function(e) { // 滑动切换tab
    this.setData({
      currentTab: e.detail.current,
    });
    this.getSwiper(e.detail.current)
  },
  getSwiper(n) { //获取swiperItem的高度,异步给swiper设置高度
    let that = this;
    this.setData({
      "addservice.tagList": []
    })
    wx.createSelectorQuery().select('.swiperitem' + n).boundingClientRect(function(rect) {
      let res = rect.height + 'px'
      console.log(res);
      that.setData({
        swiperHeight: res
      })
    }).exec();
  },
  classCheck: function(e) {
    var array = this.data.addservice.tagList;
    var val = e.currentTarget.dataset.value;
    if (array.indexOf(val) >= 0) {
      for (var i = 0; i < array.length; i++) {
        if (array[i] == val) {
          array.splice(i, 1);
        }
      }
    } else {
      array.push(val)
    }
    this.setData({
      "addservice.tagList": array
    })
  },
  bindinputValue(event) {
    switch (event.currentTarget.dataset.type) {
      case "remark":
        this.setData({
          "addservice.remark": event.detail.value
        });
        break;
    }
  },
  onPickerChange(e) {
    this.setData({
      "addservice.appointmentTime": e.detail.dateString
    })
  },
  saveService: function(e) {
    let that = this;
    let datas = this.data.addservice;
    var sc = e.currentTarget.dataset.class;
    if (datas.tagList == null || datas.tagList.length == 0) {
      util.showErrorToast('请选择服务项目');
      return false;
    }
    if (sc == "clean") {
      if (!datas.appointmentTime) {
        util.showErrorToast('请选择处理时间');
        return false;
      }
    }
    util.request(api.ServiceRoomSave, {
      roomId: app.globalData.roomId,
      serviceClass: sc,
      tagList: datas.tagList,
      remark: datas.remark,
      appointmentTime: datas.appointmentTime
    }, 'POST').then(function(res) {
      if (res.errno === 0) {
        wx.showToast({
          title: '提交成功'
        });
        that.setData({
          "addservice.tagList": [],
          "addservice.remark": '',
          "addservice.appointmentTime": ''
        })
      } else {
        util.showErrorToast(res.errmsg);
      }
    })
  },
  callPhone: function (e) {
    wx.makePhoneCall({
      phoneNumber: e.currentTarget.dataset.text
    })
  },
})