$(function () {
    $("#jqGrid").Grid({
        url: '../companyfile/list',
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '企业名称', name: 'companyName', index: 'companyName', width: 80},
            {
                label: '附件', name: 'fileurl', index: 'fileurl', width: 80, formatter: function (value) {
                    return transImg(value);
                }
            },
            {
                label: '创建时间', name: 'createTime', index: 'create_time', width: 80, formatter: function (value) {
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
        companyFile: {},
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            companyName: ''
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
            vm.getCompany();
            vm.companyFile = {companyId:null};
        },
        update: function (event) {
            let id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getCompany();
            Ajax.request({
                url: "../companyfile/info/" + id,
                async: true,
                successCallback: function (r) {
                    vm.companyFile = r.companyFile;
                }
            });
        },
        saveOrUpdate: function (event) {
            let url = vm.companyFile.id == null ? "../companyfile/save" : "../companyfile/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.companyFile),
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
            let ids = getSelectedRows("#jqGrid");
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../companyfile/delete",
                    params: JSON.stringify(ids),
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
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        reloadSearch: function () {
            vm.q = {
                name: ''
            }
            vm.reload();
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },
        getCompany: function () {//获取所有企业
            Ajax.request({
                url: "../company/getAll",
                async: true,
                successCallback: function (r) {
                    vm.companyList = r.list;
                    if (r.cid != 0) {
                        vm.companyFile.companyId = r.cid;
                    }
                }
            });
        },
        handleSuccessNewPicUrl: function (res, file) {
            vm.companyFile.fileurl = file.response.url;
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
                desc: '文件 ' + file.name + ' 太大，不能超过 1024KB。'
            });
        },
        eyeImageNewPicUrl: function () {
            var url = vm.companyFile.fileurl;
            eyeImage(url);
        }
    }
});