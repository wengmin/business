$(function () {
    $("#jqGrid").Grid({
        url: '../sys/staff/list',
        colModel: [
            {label: '员工ID', name: 'staffId', index: "staffId", key: true, hidden: true},
            {label: '员工姓名', name: 'staffName', width: 75},
            {label: '职位', name: 'position', width: 75},
            {label: '手机号码', name: 'mobile', width: 75},
            {label: '邮箱', name: 'email', width: 90},
            {label: '微信号', name: 'wechat', width: 100},
            {label: '身份证', name: 'idcard', width: 100},
            {label: '地址', name: 'address', width: 100},
            {
                label: '创建时间', name: 'createTime', index: "create_time", width: 80, formatter: function (value) {
                    return transDate(value);
                }
            }]
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            staffName: null
        },
        showList: true,
        title: null,
        staff: {
            staffName: 1,
        },
        ruleValidate: {
            staffName: [
                {required: true, message: '姓名不能为空', trigger: 'blur'}
            ],
            email: [
                {required: true, message: '邮箱不能为空', trigger: 'blur'},
                {type: 'email', message: '邮箱格式不正确', trigger: 'blur'}
            ],
            mobile: [
                {required: true, message: '手机号不能为空', trigger: 'blur'}
            ]
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增(默认密码：888888)";
            vm.staff = {staffName: ''};
        },
        update: function () {
            var staffId = getSelectedRow("#jqGrid");
            if (staffId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            Ajax.request({
                url: "../sys/staff/info/" + staffId,
                async: true,
                successCallback: function (r) {
                    vm.staff = r.staff;
                }
            });

        },
        del: function () {
            var staffIds = getSelectedRows("#jqGrid");
            if (staffIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../sys/staff/delete",
                    params: JSON.stringify(staffIds),
                    contentType: "application/json",
                    type: 'POST',
                    successCallback: function () {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.staff.staffId == null ? "../sys/staff/save" : "../sys/staff/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.staff),
                contentType: "application/json",
                type: 'POST',
                successCallback: function () {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'staffName': vm.q.staffName},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },
        eyeImageNewPicUrl: function () {
            var url = vm.staff.headimg;
            eyeImage(url);
        },
        handleMaxSize: function (file) {
            this.$Notice.warning({
                title: '超出文件大小限制',
                desc: '文件 ' + file.name + ' 太大，不能超过 2M。'
            });
        },
        handleFormatError: function (file) {
            this.$Notice.warning({
                title: '文件格式不正确',
                desc: '文件 ' + file.name + ' 格式不正确，请上传 jpg 或 png 格式的图片。'
            });
        },
        handleSuccessNewPicUrl: function (res, file) {
            vm.staff.headimg = file.response.url;
        },
    }
});