$(function () {
    $("#jqGrid").Grid({
        url: '../serviceroom/list',
        colModel: [
            {label: 'serviceId', name: 'serviceId', index: 'service_id', key: true, hidden: true},
            {label: '企业名称', name: 'companyName', index: 'companyName', width: 80},
            {label: '房间号', name: 'roomName', index: 'roomName', width: 80},
            // {label: '用户微信昵称', name: 'nickname', index: 'nickname', width: 80},
            {
                label: '服务类型', name: 'serviceClass', index: '', width: 80, formatter: function (value) {
                    var txt = "";
                    if (value == "clean") {
                        txt = "清洁";
                    }
                    if (value == "lease") {
                        txt = "物品租借";
                    }
                    if (value == "repair") {
                        txt = "维修";
                    }
                    if (value == "complaint") {
                        txt = "投诉";
                    }
                    return txt;
                }
            },
            {label: '项', name: 'serviceTag', index: 'service_tag', width: 80},
            {label: '备注', name: 'remark', index: 'remark', width: 80},
            {
                label: '预约时间',
                name: 'appointmentTime',
                index: 'appointment_time',
                width: 80,
                formatter: function (value) {
                    return transDate(value);
                }
            },
            {
                label: '状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                    var txt = "";
                    if (value == "0") {
                        txt = "未处理";
                    }
                    if (value == "1") {
                        txt = "处理中";
                    }
                    if (value == "2") {
                        txt = "已处理";
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
        serviceRoom: {},
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            name: ''
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.serviceRoom = {};
        },
        update: function (event) {
            let serviceId = getSelectedRow("#jqGrid");
            if (serviceId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(serviceId)
        },
        saveOrUpdate: function (event) {
            let url = vm.serviceRoom.serviceId == null ? "../serviceroom/save" : "../serviceroom/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.serviceRoom),
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
            let serviceIds = getSelectedRows("#jqGrid");
            if (serviceIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../serviceroom/delete",
                    params: JSON.stringify(serviceIds),
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
        getInfo: function (serviceId) {
            Ajax.request({
                url: "../serviceroom/info/" + serviceId,
                async: true,
                successCallback: function (r) {
                    vm.serviceRoom = r.serviceRoom;
                    vm.serviceRoom.appointmentTimeText = transDate(vm.serviceRoom.appointmentTime);
                    vm.serviceRoom.remarkuser = vm.serviceRoom.remark;
                    vm.serviceRoom.remark = "";
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
        }
    }
});