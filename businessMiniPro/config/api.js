//var NewApiRootUrl = 'https://emiaoweb.com/business/api/';
var NewApiRootUrl = 'http://127.0.0.1:8090/api/';
module.exports = {
  AuthLoginByWeixin: NewApiRootUrl + 'auth/login_by_weixin', //微信登录
  Upload: NewApiRootUrl + 'auth/upload/upload',
  CreateCardQrCode: NewApiRootUrl + 'card/createQrCode',
  CardInfoByOpenID: NewApiRootUrl + 'card/cardUserByOpenId', 
  CardUserSave: NewApiRootUrl + 'card/saveCardUser',

  RegionList: NewApiRootUrl + 'region/list',  //获取区域列表
  CardListRecord: NewApiRootUrl + 'card/listRecord',
  CardSaveRecord: NewApiRootUrl + 'card/saveRecord',
  CardDeleteRecord: NewApiRootUrl + 'card/deleteRecord',
  CardListCollect: NewApiRootUrl + 'card/listCollect',
  CardSaveCollect: NewApiRootUrl + 'card/saveCollect',
  CardDeleteCollect: NewApiRootUrl + 'card/deleteCollect',
  CardIsCollect: NewApiRootUrl + 'card/isCollect',
};