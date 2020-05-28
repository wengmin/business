//var NewApiRootUrl = 'https://emiaoweb.com/business/api/';
var NewApiRootUrl = 'http://127.0.0.1:8092/api/';
module.exports = {
  FeedbackAdd: NewApiRootUrl + 'feedback/save',  //意见反馈
  RegionList: NewApiRootUrl + 'region/list',  //获取区域列表
  AuthLoginByWeixin: NewApiRootUrl + 'auth/login_by_weixin', //微信登录
  AuthLoginBySilence: NewApiRootUrl + 'auth/loginBySilence', //微信登录(无感知)
  Upload: NewApiRootUrl + 'upload/image',
  MacrosByType: NewApiRootUrl + 'macro/queryMacrosByValue',

  CardInfoByParam: NewApiRootUrl + 'card/cardUserByParam',
  CardInfoByOpenID: NewApiRootUrl + 'card/cardUserByOpenId', 
  CreateCardQrCode: NewApiRootUrl + 'card/createQrCode',
  CardUserSave: NewApiRootUrl + 'card/saveCardUser',

  UserDetailByOpenID: NewApiRootUrl + 'user/detailByOpenId',

  CardListRecord: NewApiRootUrl + 'card/listRecord',
  CardSaveRecord: NewApiRootUrl + 'card/saveRecord',
  CardDeleteRecord: NewApiRootUrl + 'card/deleteRecord',
  CardListCollect: NewApiRootUrl + 'card/listCollect',
  CardSaveCollect: NewApiRootUrl + 'card/saveCollect',
  CardDeleteCollect: NewApiRootUrl + 'card/deleteCollect',
  CardIsCollect: NewApiRootUrl + 'card/isCollect',

  CompanyDetailsByUserID: NewApiRootUrl + 'company/detail',
  CompanyDetailsByID: NewApiRootUrl + 'company/detailById',
  CompanySave: NewApiRootUrl + 'company/save',
  CompanyPostDetail: NewApiRootUrl + 'company/postDetail',
  CompanyPostList: NewApiRootUrl + 'company/postList',
  CompanyRoomDetail: NewApiRootUrl + 'company/roomDetail',
  CompanyServiceList: NewApiRootUrl + 'company/serviceList',
  CompanyServiceGroup: NewApiRootUrl + 'company/serviceGroup',
  CompanyStaffBind: NewApiRootUrl + 'company/staffBind',

  ServiceRoomSave: NewApiRootUrl + 'serviceroom/save',
  ServiceRoomList: NewApiRootUrl + 'serviceroom/list',
  ServiceRoomDetail: NewApiRootUrl + 'serviceroom/detail',
  ServiceRoomUpdate: NewApiRootUrl + 'serviceroom/update',
  ServiceRoomLogList: NewApiRootUrl + 'serviceroom/logList',  

  ServiceInvoiceSave: NewApiRootUrl + 'serviceinvoice/save',
  ServiceInvoiceList: NewApiRootUrl + 'serviceinvoice/list',
  ServiceInvoiceDetail: NewApiRootUrl + 'serviceinvoice/detail',
  ServiceInvoiceUpdate: NewApiRootUrl + 'serviceinvoice/update',

  CreateQrCodeByWifi: NewApiRootUrl + 'card/createQrCodeByWifi',
};