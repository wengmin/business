$(function () {
    $("#jqGrid").Grid({
        url: '../companypost/list',
        colModel: [
            {label: 'postId', name: 'postId', index: 'post_id', key: true, hidden: true},
            {label: '企业名称', name: 'companyName', index: 'companyName', width: 80},
            {
                label: '照片', name: 'photo', index: 'photo', width: 80, formatter: function (value) {
                    return transImg(value);
                }
            },
            {label: '姓名', name: 'name', index: 'name', width: 80},
            {label: '职位', name: 'position', index: 'position', width: 80},
            {label: '手机号码', name: 'mobile', index: 'mobile', width: 80},
            {label: '固定电话', name: 'telephone', index: 'telephone', width: 80},
            {label: '微信号', name: 'wechat', index: 'wechat', width: 80},
            {label: '电子邮箱', name: 'email', index: 'email', width: 80},
            {label: '职位简介', name: 'profile', index: 'profile', width: 80},
            {
                label: '二维码', name: 'qrCode', index: 'qr_code', width: 80, formatter: function (value) {
                    return transImg(value);
                }
            },
            {
                label: '创建时间', name: 'createTime', index: 'create_time', width: 80, formatter: function (value) {
                    return transDate(value);
                }
            },
            {
                label: '更新时间', name: 'updateTime', index: 'update_time', width: 80, formatter: function (value) {
                    return transDate(value);
                }
            }]
    });
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        companyPost: {},
        ruleValidate: {
            name: [
                {required: true, message: '姓名不能为空', trigger: 'blur'}
            ],
            email: [
                {type: 'email', message: '邮箱格式不正确', trigger: 'blur'}
            ],
            mobile: [
                {required: true, message: '手机号码不能为空', trigger: 'blur'}
            ]
        },
        q: {
            name: ''
        },
        companyList: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.companyPost = {companyId: null};
            vm.getCompany();
        },
        update: function (event) {
            let postId = getSelectedRow("#jqGrid");
            if (postId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(postId)
        },
        saveOrUpdate: function (event) {
            let url = vm.companyPost.postId == null ? "../companypost/save" : "../companypost/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.companyPost),
                type: "POST",
                contentType: "application/json",
                successCallback: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        del: function (event) {
            let postIds = getSelectedRows("#jqGrid");
            if (postIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../companypost/delete",
                    params: JSON.stringify(postIds),
                    type: "POST",
                    contentType: "application/json",
                    successCallback: function () {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        },
        getInfo: function (postId) {
            Ajax.request({
                url: "../companypost/info/" + postId,
                async: true,
                successCallback: function (r) {
                    vm.companyPost = r.companyPost;
                    vm.getCompany();
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        getCompany: function () {//获取所有企业
            Ajax.request({
                url: "../company/getAll",
                async: true,
                successCallback: function (r) {
                    vm.companyList = r.list;
                    if (r.cid != 0) {
                        vm.companyPost.companyId = r.cid;
                    }
                }
            });
        },
        reloadSearch: function () {
            vm.q = {
                name: ''
            }
            vm.reload();
        },
        createQrCode: function () {
            let postId = getSelectedRow("#jqGrid");
            if (postId == null) {
                return;
            }
            Ajax.request({
                url: "../companypost/createQrCode/" + postId,
                async: true,
                successCallback: function (r) {
                    vm.reloadSearch();
                }
            });
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },
        handleSuccessNewPicUrl: function (res, file) {
            vm.companyPost.photo = file.response.url;
        },
        handleFormatError: function (file) {
            this.$Notice.warning({
                title: '文件格式不正确',
                desc: '文件 ' + file.name + ' 格式不正确，请上传 jpg 或 png 格式的图片。'
            });
        },
        handleMaxSize: function (file) {
            this.$Notice.warning({
                title: '超出文件大小限制',
                desc: '文件 ' + file.name + ' 太大，不能超过 500KB。'
            });
        },
        eyeImageNewPicUrl: function () {
            var url = vm.companyPost.photo;
            eyeImage(url);
        }
    }
});