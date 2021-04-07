//var NewApiRootUrl = 'https://emiaoweb.com/business/api/';
var NewApiRootUrl = 'http://127.0.0.1:8060/api/';
module.exports = {
AuthLoginAuto:NewApiRootUrl + 'auth/loginAuto', //微信登录


  AuthLoginByWeixin: NewApiRootUrl + 'auth/login_by_weixin', //微信登录
  AuthLoginBySilence: NewApiRootUrl + 'auth/loginBySilence', //微信登录(无感知)
  FeedbackAdd: NewApiRootUrl + 'feedback/save', //意见反馈
  RegionList: NewApiRootUrl + 'region/list', //获取区域列表
  Upload: NewApiRootUrl + 'upload/image',
  UploadDocuments: NewApiRootUrl + 'upload/documents',
  MacrosByType: NewApiRootUrl + 'macro/queryMacrosByValue',

  //CardInfoByOpenID: NewApiRootUrl + 'card/cardUserByOpenId',   
  CreateCardQrCode: NewApiRootUrl + 'card/createQrCode',

  CardInfoByParam: NewApiRootUrl + 'card/detailByParam',

  myCardList: NewApiRootUrl + 'card/myCardList',
  CardDetail: NewApiRootUrl + 'card/detail',
  CardDefault: NewApiRootUrl + 'card/detailByLogin', //cardDefault
  CardSave: NewApiRootUrl + 'card/save',

  CardListRecord: NewApiRootUrl + 'card/listRecord',
  CardSaveRecord: NewApiRootUrl + 'card/saveRecord',
  CardDeleteRecord: NewApiRootUrl + 'card/deleteRecord',
  CardListCollect: NewApiRootUrl + 'card/listCollect',
  CardSaveCollect: NewApiRootUrl + 'card/saveCollect',
  CardDeleteCollect: NewApiRootUrl + 'card/deleteCollect',
  CardIsCollect: NewApiRootUrl + 'card/isCollect',
  CardReport: NewApiRootUrl + 'card/report',

  CompanyDetailsByUserID: NewApiRootUrl + 'company/detail',
  CompanyDetailsByID: NewApiRootUrl + 'company/detailById',
  CompanySave: NewApiRootUrl + 'company/save',

  SaveShare: NewApiRootUrl + 'analysis/saveShare',

  documentsList: NewApiRootUrl + 'documents/list',
  documentsListByUser: NewApiRootUrl + 'documents/listByUser',
  documentsDetail: NewApiRootUrl + 'documents/detail',
  documentsFolderDocList: NewApiRootUrl + 'documents/folderDocList',
  documentsFolderList: NewApiRootUrl + 'documents/folderList',
  documentsFolderSave: NewApiRootUrl + 'documents/folderSave',
  documentsFolderDelete: NewApiRootUrl + 'documents/folderDelete',
};