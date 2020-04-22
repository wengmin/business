// component/card-holder/card-holder.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    title: String, // 简化的定义方式
  },

  /**
   * 组件的初始数据
   */
  data: {
    index: "/pages/ucenter/index/index", // 首页
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 关闭所有页面回到首页。
    usercenter: function() {
      wx.navigateTo({
        url: this.data.index
      })
    },
  },

  pageLifetimes: {
    // 组件所在页面的生命周期函数
    show: function() {
      let that = this;
      wx.getSystemInfo({
        success: function(res) {
          that.setData({
            windowWidth: res.windowWidth
          })
        },
      })
      // 胶囊位置信息
      let res = wx.getMenuButtonBoundingClientRect();
      that.setData({
        ucheight: res.height + res.top + 6,
        leftMenuWidth: that.data.windowWidth - res.width - 2 * (that.data.windowWidth - res.right),
        leftMenuHeight: res.height,
        menuButtonWidth: res.width,
        top: res.top,
        left: that.data.windowWidth - res.right,
        centerTitle: res.width,
      })
    }
  }
})