var util = require('./utils/util.js');
var api = require('./config/api.js');
var user = require('./services/user.js');

App({
  onLaunch: function(options) {
    if (wx.canIUse("getUpdateManager")) {
      const updateManager = wx.getUpdateManager();
      updateManager.onCheckForUpdate(function(res) {
        // console.log(res.hasUpdate);
        // 请求完新版本信息的回调
        if (res.hasUpdate) {
          updateManager.onUpdateReady(function() {
            wx.showModal({
              title: "更新提示",
              content: "新版本已经准备好，是否重启应用？",
              success(res) {
                if (res.confirm) {
                  // 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
                  updateManager.applyUpdate();
                }
              }
            });
          });
          updateManager.onUpdateFailed(function() {
            // 新的版本下载失败
            wx.showModal({
              title: "发现新版本",
              content: "新版本已经上线，请您删除当前小程序，重新搜索打开"
            });
          });
        }
      });
    } else {
      wx.showModal({
        title: "提示",
        content: "当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。"
      });
    }

    //获取用户的登录信息
    user.checkLogin().then(res => {
      console.log('app login')
      this.globalData.userInfo = wx.getStorageSync('userInfo');
      this.globalData.token = wx.getStorageSync('token');
    }).catch(() => {
      wx.removeStorageSync('userInfo');
      wx.removeStorageSync('token');
    });

    if (typeof(options) != "undefined") {
      if (typeof(options.query.companyId) != "undefined" && options.query.companyId > 0) {
        this.globalData.companyId = options.query.companyId;
      } else if (typeof(options.query.scene) != "undefined") {
        let scene = '?' + decodeURIComponent(options.query.scene);
        this.globalData.companyId = util.getQueryString(scene, 'companyId');
      }

      if (typeof (options.query.roomId) != "undefined" && options.query.roomId > 0) {
        this.globalData.roomId = options.query.roomId;
      } 
    }
  },
  globalData: {
    userInfo: {
      nickName: 'Hi,游客',
      userName: '点击登录',
      avatarUrl: 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/150547696d798c.png'
    },
    token: '',
    companyId: 0,
    roomId:0
  }
})