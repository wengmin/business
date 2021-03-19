// component/authorize/authorize.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    /**
     * 检测用户是否已经授权过某个权限，如果没有授权就调用小程序的授权，如果授权了就返回相应的状态给回调函数
     * scope为具体的某个权限
     * cb为回调
     */
    isAuthorize(scope, cb) {
      let self = this;
      wx.getSetting({
        success(res) {
          if (!res.authSetting['scope.' + scope]) {
            wx.authorize({
              scope: 'scope.' + scope,
              success() {
                return typeof cb == "function" && cb();
              },
              fail() {
                self.setAuthTxt(scope);
                self.callBack = cb;
                self.setData({
                  popShow: true
                })
              }
            })
          } else {
            return typeof cb == "function" && cb();
          }
        }
      })
    },
    setAuthTxt(authType) {
      var authTxt = '';
      switch (authType) {
        case 'userInfo':
          authTxt = '用户信息';
          break;
        case 'userLocation':
          authTxt = '地理位置';
          break;
        case 'record':
          authTxt = '录音功能';
          break;
        case 'writePhotosAlbum':
          authTxt = '保存到相册';
          break;
        case 'camera':
          authTxt = '摄像头';
          break;
      }
      this.setData({
        authType: authType,
        authTxt: authTxt
      })
    },
    getAuthorizeTool: function (res) {
      var scope = 'scope.' + this.data.authType;
      if (res.detail.authSetting[scope]) {
        this.setData({
          popShow: false
        })
        return typeof this.callBack == "function" && this.callBack();
      }
    },
    popClose() {
      this.setData({
        popShow: false
      })
    }
  }
})