//const user = require('../services/user.js');

function getQueryString(url, name) {
  var reg = new RegExp('(^|&|/?)' + name + '=([^&|/?]*)(&|/?|$)', 'i')
  var r = url.substr(1).match(reg)
  if (r != null) {
    return r[2]
  }
  return null;
}

function showErrorToast(msg) {
  wx.showToast({
    title: msg,
    image: '/static/images/icon_error.png'
  })
}

function showSuccessToast(msg) {
  wx.showToast({
    title: msg,
  })
}

function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()


  return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function validatePhone(phone) {
  const re = /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/
  return re.test(phone)
}

/**
 * 时间戳转化为年 月 日 时 分 秒
 * number: 传入时间戳
 * format：返回格式，支持自定义，但参数必须与formateArr里保持一致
 */
function nformatTime(number, format) {

  var formateArr = ['Y', 'M', 'D', 'h', 'm', 's'];
  var returnArr = [];

  var date = new Date(number * 1000);
  returnArr.push(date.getFullYear());
  returnArr.push(formatNumber(date.getMonth() + 1));
  returnArr.push(formatNumber(date.getDate()));

  returnArr.push(formatNumber(date.getHours()));
  returnArr.push(formatNumber(date.getMinutes()));
  returnArr.push(formatNumber(date.getSeconds()));

  for (var i in returnArr) {
    format = format.replace(formateArr[i], returnArr[i]);
  }
  return format;
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

/**
 * 封装微信的request
 */
function request(url, data = {}, method = "GET", contentType = "application/json") {
  return new Promise(function (resolve, reject) {
    if (method == "POST") {
      wx.showLoading({
        title: '提交中...',
        mask: true,
      });
    } else {
      wx.showLoading({
        title: '获取中...',
      });
    }
    console.log("token:" + getApp().globalData.token)
    wx.request({
      url: url,
      data: data,
      method: method,
      header: {
        'Content-Type': contentType,
        'X-Nideshop-Token': wx.getStorageSync('token')
      },
      success: function (res) {
        if (res.statusCode == 200) {
          if (res.data.errno == 401) {
            console.log("token：" + wx.getStorageSync('token'))
            getApp().userLogin().then(res => {
              console.log(res);
              if (getCurrentPages().length != 0) {
                getCurrentPages()[getCurrentPages().length - 1].onLoad()
              }
            })
          } else {
            resolve(res.data);
          }
        } else {
          reject(res.errMsg);
        }
        wx.hideLoading();
      },
      fail: function (err) {
        reject(err)
        wx.hideLoading();
      }
    })
  });
}

/**
 * 封装微信的request
 */
function upload(url, filePath, name, formData = {}) {
  return new Promise(function (resolve, reject) {
    wx.showLoading({
      title: '上传中...',
    });
    wx.uploadFile({
      url: url,
      filePath: filePath,
      name: name,
      formData: formData,
      header: {
        'Content-Type': "multipart/form-data",
        'X-Nideshop-Token': wx.getStorageSync('token')
      },
      success(res) {
        console.info(res);
        let data = JSON.parse(res.data)
        if (res.statusCode == 200) {
          if (data.errno == 401) {
            //需要登录后才可以操作
            wx.showModal({
              title: '',
              content: '请先登录',
              success: function (res) {
                if (res.confirm) {
                  wx.removeStorageSync("userInfo");
                  wx.removeStorageSync("token");
                  wx.redirectTo({
                    url: '/pages/ucenter/index/index'
                  });
                }
              }
            });
          } else {
            resolve(data);
          }
        } else {
          reject(data.errMsg);
        }
        wx.hideLoading();
      },
      fail: function (err) {
        reject(err)
        wx.hideLoading();
      }
    })
  });
}

/**
 * 检查微信会话是否过期
 */
function checkSession() {
  return new Promise(function (resolve, reject) {
    wx.checkSession({
      success: function () {
        resolve(true);
      },
      fail: function () {
        reject(false);
      }
    })
  });
}

/**
 * 调用微信登录
 */
function login() {
  return new Promise(function (resolve, reject) {
    wx.login({
      success: function (res) {
        if (res.code) {
          //登录远程服务器
          resolve(res);
        } else {
          reject(res);
        }
      },
      fail: function (err) {
        reject(err);
      }
    });
  });
}

module.exports = {
  formatTime,
  nformatTime,
  request,
  showErrorToast,
  showSuccessToast,
  checkSession,
  validatePhone,
  getQueryString,
  upload,
  login
}