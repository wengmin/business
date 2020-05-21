$(function () {
    $("#jqGrid").Grid({
        url: '../companystaff/list',
        colModel: [
            {label: 'staffId', name: 'staffId', index: 'staff_id', key: true, hidden: true},
            {label: '企业名称', name: 'companyName', index: 'companyName', width: 80},
            {label: '姓名', name: 'name', index: 'name', width: 80},
            {label: '手机号码', name: 'mobile', index: 'mobile', width: 80},
            {label: '岗位', name: 'post', index: 'post', width: 80},
            {
                label: '状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                    var txt = "";
                    if (value == "0") {
                        txt = "未绑定";
                    }
                    if (value == "1") {
                        txt = "待审核";
                    }
                    if (value == "2") {
                        txt = "已审核";
                    }
                    if (value == "3") {
                        txt = "离职";
                    }
                    return txt;
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
        companyStaff: {
            status: 0
        },
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            name: '',
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
            vm.companyStaff = {companyId: null, status: 0};

            vm.getCompany();
        },
        update: function (event) {
            let staffId = getSelectedRow("#jqGrid");
            if (staffId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(staffId)
        },
        saveOrUpdate: function (event) {
            let url = vm.companyStaff.staffId == null ? "../companystaff/save" : "../companystaff/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.companyStaff),
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
            let staffIds = getSelectedRows("#jqGrid");
            if (staffIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../companystaff/delete",
                    params: JSON.stringify(staffIds),
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
        getInfo: function (staffId) {
            Ajax.request({
                url: "../companystaff/info/" + staffId,
                async: true,
                successCallback: function (r) {
                    vm.companyStaff = r.companyStaff;
                    vm.getCompany();
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name, 'companyName': vm.q.companyName},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        reloadSearch: function () {
            vm.q = {
                name: '',
                companyName: ''
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
                        vm.companyStaff.companyId = r.cid;
                    }
                }
            });
        }
    }
});