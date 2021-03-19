/**
 * Created by sail on 2017/6/1.
 */
var api = require('../../config/api.js');
import WeCropper from '../../lib/we-cropper/we-cropper.js'

const app = getApp()

const device = wx.getSystemInfoSync()
const width = device.windowWidth
const height = device.windowHeight - 50

Page({
  data: {
    cropperOpt: {
      id: 'cropper',
      targetId: 'targetCropper',
      pixelRatio: device.pixelRatio,
      width,
      height,
      scale: 2.5,
      zoom: 8,
      cut: {
        x: (width - 300) / 2,
        y: (height - 300) / 2,
        width: 300,
        height: 300
      },
      boundStyle: {
        color: "green",
        mask: 'rgba(0,0,0,0.5)',
        lineWidth: 1
      }
    },
    isshowuploadbtn: false
  },
  touchStart(e) {
    this.cropper.touchStart(e)
  },
  touchMove(e) {
    this.cropper.touchMove(e)
  },
  touchEnd(e) {
    this.cropper.touchEnd(e)
  },

  //当点击生成图片按钮的时候，得到图片的src后，调用wx.uploadFile()上传图片，成功后可以再跳转到想要去的页面
  getCropperImage() {
    console.log("123")
    wx.showLoading({
      title: "生成中...",
      mask: true
    });
    this.cropper.getCropperImage().then((src) => {
      wx.uploadFile({
        url: api.Upload, //这里是上传的服务器地址
        filePath: src,
        name: "file",
        success: function (res) {
          wx.hideLoading();
          console.log(res);
          console.log("uploadOK");
          let datas = JSON.parse(res.data)
          if (datas.errno === 0) {
            let pages = getCurrentPages();
            let prevPage = pages[pages.length - 2]; //上一页面
            prevPage.setData({
              "cardInfo.photo": datas.data, //放回上一页携带的参数
            })
            // setTimeout(() => {
              wx.navigateBack({
                delta: 1 //返回的层级
              })
            // }, 1000)
          } else {
            wx.showToast({
              title: '图片上传失败',
              icon: 'success',
              duration: 3000
            })
          }
        }
      })
    }).catch((err) => {
      wx.hideLoading();
      wx.showModal({
        title: '温馨提示',
        content: err.message
      })
    })
  },
  uploadTap() {
    const self = this
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success(res) {
        const src = res.tempFilePaths[0]
        //  获取裁剪图片资源后，给data添加src属性及其值

        self.cropper.pushOrign(src)
      }
    })
  },
  onLoad(option) {
    const {
      cropperOpt
    } = this.data

    cropperOpt.boundStyle.color = "green"

    this.setData({
      cropperOpt
    })

    this.cropper = new WeCropper(cropperOpt).on('ready', (ctx) => {
      console.log(`wecropper is ready for work!`)
    }).on('beforeImageLoad', (ctx) => {
      wx.showToast({
        title: '上传中',
        icon: 'loading',
        duration: 20000
      })
    }).on('imageLoad', (ctx) => {
      wx.hideToast()
    })

    if (typeof (option) != "undefined") {
      this.cropper.pushOrign(option.src)
    } else {
      that.setData({
        isshowuploadbtn: true
      });
    }
  }
})