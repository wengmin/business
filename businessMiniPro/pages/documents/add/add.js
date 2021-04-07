var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

// pages/file/edit/edit.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    fromOne: true,
    fromTwo: false,

    doc: {
      filepath: '',
      name: "",
      profiles: "",
      isOpen: 0,
      password: "",
    },

    folder: {
      id: 0,
      name: 0
    },

    selectShow: false, //控制下拉列表的显示隐藏，false隐藏、true显示
    selectData: {}, //下拉列表的数据
  },
  // 点击下拉显示框
  selectTap() {
    this.setData({
      selectShow: !this.data.selectShow
    });
  },
  // 点击下拉列表
  optionTap(e) {
    let id = e.currentTarget.dataset.id; //获取点击的下拉列表的下标
    let name = e.currentTarget.dataset.name; //获取点击的下拉列表的下标
    this.setData({
      "folder.id": id,
      "folder.name": name,
      selectShow: !this.data.selectShow
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    util.request(api.documentsFolderList, {}).then(function (res) {
      if (res.errno === 0) {
        var obj = [];
        for (let i = 0; i < res.data.length; i++) {
          obj.push({
            id: res.data[i].folderId,
            name: res.data[i].name
          })
        }
        that.setData({
          selectData: obj
        });
      } else {
        util.showErrorToast(res.errmsg);
      }
    })
  },
  saveFolder: function () {
    let that = this;
    util.request(api.documentsFolderSave, {
      name: that.data.folder.name,
      isOpen: 0,
      sortValue: 0,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        var obj = that.data.selectData;
        obj.push({
          id: res.data,
          name: that.data.folder.name
        })
        that.setData({
          selectData: obj
        });
      } else {
        util.showErrorToast(res.errmsg);
      }
    })
  },
  saveNext() {
    if (this.data.folder.id == 0) {
      util.showErrorToast('请选择文件夹');
      return false;
    }
    this.setData({
      fromTwo: true,
      fromOne: false
    })
  },
  bindinputValue(event) {
    switch (event.currentTarget.dataset.type) {
      case "folderName":
        this.setData({
          "folder.name": event.detail.value
        });
        break;
      case "folderId":
        this.setData({
          "folder.id": event.detail.value
        });
        break;
      case "docName":
        this.setData({
          "doc.name": event.detail.value
        });
        break;
      case "docProfiles":
        this.setData({
          "doc.profiles": event.detail.value
        });
        break;
      case "docIsOpen":
        this.setData({
          "doc.isOpen": event.detail.value
        });
        break;
      case "docPassword":
        this.setData({
          "doc.password": event.detail.value
        });
        break;
    }
  },
  chooseMessageFile: function (e) {
    var that = this;
    wx.chooseMessageFile({
      count: 1,
      type: 'file',
      success(res) {
        that.setData({
          'doc.name': res.tempFiles[0].name,
          'doc.filepath': res.tempFiles[0].path
        })
      }
    });
  },
  saveDoc: function () {
    var that = this;
    if (that.data.doc.filepath == '') {
      util.showErrorToast('请选择文件');
      return false;
    }
    if (that.data.doc.name == '') {
      util.showErrorToast('请输入文件名称');
      return false;
    }
    let folder = that.data.folder;
    let doc = that.data.doc;
    util.upload(api.UploadDocuments, that.data.doc.filepath, 'file', {
      'folderId': folder.id,
      'docName': doc.name,
      'profiles': doc.profiles,
      'isOpen': doc.isOpen,
      'password': doc.password
    }).then(function (res) {
      console.log(res.errno + "..." + res)
      if (res.errno === 0) {
        wx.redirectTo({
          url: '/pages/documents/list/list',
        })
      } else {
        util.showErrorToast(res.errmsg);
      }
    }).catch((err) => {
      console.log("catch" + err)
      wx.showToast({
        title: "文件转为后台上传中，请稍后刷新列表查看",
        icon: 'none',
        duration: 3000
      });
      setTimeout(function () {
        wx.navigateBack({
          delta: 1
        })
      }, 3000)
    });
  }
})