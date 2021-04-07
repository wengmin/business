var util = require('./utils/util.js');
var api = require('./config/api.js');
var user = require('./services/user.js');

App({
  onLaunch: function (options) {
    if (wx.canIUse("getUpdateManager")) {
      const updateManager = wx.getUpdateManager();
      updateManager.onCheckForUpdate(function (res) {
        // console.log(res.hasUpdate);
        // 请求完新版本信息的回调
        if (res.hasUpdate) {
          updateManager.onUpdateReady(function () {
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
          updateManager.onUpdateFailed(function () {
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
  },

  globalData: {
    userInfo: {
      nickName: 'Hi,游客',
      userName: '点击登录',
      headimgurl: 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/150547696d798c.png'
    },
    token: ''
  },


  /**
   * 用户登录请求封装(解决onlaunch和onload执行顺序问题)
   */
  userLogin: function () {
    wx.showLoading({
      title: '加载中',
    })
    var that = this;
    //定义promise方法
    return new Promise(function (resolve, reject) {
      // 调用登录接口
      wx.login({
        success: function (res) {
          if (res.code) {
            console.log("用户登录授权code为：" + res.code);
            //调用wx.request请求传递code凭证换取用户openid，并获取后台用户信息
            wx.request({
              url: api.AuthLoginAuto, // 后台请求用户信息方法【注意，此处必须为https数字加密证书】
              data: {
                code: res.code //code凭证
              },
              header: {
                'content-type': 'application/json' // 默认值
              },
              success(res) {
                console.log(res.data)
                if (res.data.errno == 0) {
                  //获取用户信息成功
                  that.globalData.token = res.data.data.openid;
                  that.globalData.userInfo = res.data.data.userVo;
                  //存入session缓存中
                  wx.setStorageSync("userInfo", that.globalData.userInfo)
                  wx.setStorageSync("token", that.globalData.token)
                  resolve(res.data);
                } else {
                  reject('error');
                }
              },
              fail: function (res) {
                reject(res);
                wx.showToast({
                  title: '系统错误'
                })
              },
              complete: () => {

              } //complete接口执行后的回调函数，无论成功失败都会调用
            })

          } else {
            reject("error");
          }
        }
      })
    })
  }
})