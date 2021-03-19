/**
 * 用户相关服务
 */

const util = require('../utils/util.js');
const api = require('../config/api.js');


/**
 * 调用微信登录
 */
function loginByWeixin(userInfo) {
  console.log("-----1----")
  console.log(userInfo.userInfo)
  let code = null;
  return new Promise(function (resolve, reject) {
    return util.login().then((res) => {
      code = res.code;
      return userInfo;
    }).then((userInfo) => {
      //登录远程服务器
      let params = {};
      params.code = code;
      params.encryptedData = userInfo.encryptedData;
      params.iv = userInfo.iv;
      params.avatarUrl = userInfo.userInfo.avatarUrl;
      params.city = userInfo.userInfo.city;
      params.country = userInfo.userInfo.country;
      params.gender = userInfo.userInfo.gender;
      params.language = userInfo.userInfo.language;
      params.nickName = userInfo.userInfo.nickName;
      params.province = userInfo.userInfo.province;
      params.promoterId = wx.getStorageSync('userId') || 0;
      console.log('-----********---------', JSON.stringify(params))
      util.request(api.AuthLoginByWeixin, params, 'POST').then(res => {
        if (res.errno === 0) {
          //存储用户信息
          wx.setStorageSync('token', res.data.openid);
          wx.setStorageSync('unionId', res.data.userVo.unionid);
          wx.setStorageSync('sessionKey', res.data.sessionKey);
          wx.setStorageSync('userInfo', res.data.userVo);
          wx.setStorageSync('userId', res.data.userVo.userId);
          resolve(res);
        } else {
          util.showErrorToast(res.errmsg)
          reject(res);
        }
      }).catch((err) => {
        reject(err);
      });
    }).catch((err) => {
      reject(err);
    })
  });
}

/**
 * 调用微信登录
 */
function loginForever() {
  return new Promise(function (resolve, reject) {
    return util.login().then((res) => {
      let params = {};
      params.code = res.code;
      util.request(api.AuthLoginBySilence, params).then(resdata => {
        console.log('-----loginForever-----', JSON.stringify(resdata))
        if (resdata.errno === 0) {
          //存储用户信息
          wx.setStorageSync('token', resdata.data.openid);
          wx.setStorageSync('unionId', resdata.data.unionid);
          wx.setStorageSync('sessionKey', resdata.data.sessionKey);
          wx.setStorageSync('userId', resdata.data.userVo.userId);
          wx.setStorageSync('userInfo', resdata.data.userVo);
          resolve(resdata);
        } else {
          util.showErrorToast(resdata.errmsg)
          reject(resdata);
        }
      }).catch((err) => {
        reject(err);
      });
    }).catch((err) => {
      reject(err);
    })
  });
}

/**
 * 判断用户是否登录
 */
function checkLogin() {
  return new Promise(function (resolve, reject) {
    if (wx.getStorageSync('userInfo') && wx.getStorageSync('token')) {

      util.checkSession().then(() => {
        resolve(true);
      }).catch(() => {
        reject(false);
      });

    } else {
      reject(false);
    }
  });
}


module.exports = {
  loginByWeixin,
  loginForever,
  checkLogin,
};