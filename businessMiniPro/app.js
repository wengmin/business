var util = require('./utils/util.js');
var api = require('./config/api.js');
var user = require('./services/user.js');

App({
  onLaunch: function(options) {
    //获取用户的登录信息
    user.checkLogin().then(res => {
      console.log('app login')
      this.globalData.userInfo = wx.getStorageSync('userInfo');
      this.globalData.token = wx.getStorageSync('token');
    }).catch(() => {
      wx.removeStorageSync('userInfo');
      wx.removeStorageSync('token');
    });

    console.log("全局onLaunch options==" + options.query.userId)
    if (options.query.userId != "undefined" && typeof(options.query.userId) != "undefined") {
      this.globalData.userId = options.query.userId;
    }
  },
  globalData: {
    userInfo: {
      nickName: 'Hi,游客',
      userName: '点击登录',
      avatarUrl: 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/150547696d798c.png'
    },
    userId: 0,
    token: '',
  }
})