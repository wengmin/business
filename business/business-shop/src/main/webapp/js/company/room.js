$(function () {
    $("#jqGrid").Grid({
        url: '../companyroom/list',
        colModel: [
            {label: 'roomId', name: 'roomId', index: 'room_id', key: true, hidden: true},
            {label: '企业名称', name: 'companyName', index: 'company_id', width: 80},
            {label: '房价号', name: 'name', index: 'name', width: 80},
            {label: '栋', name: 'tung', index: 'tung', width: 80},
            {label: '楼层', name: 'floor', index: 'floor', width: 80},
            {label: 'wifi名称', name: 'wifiname', index: 'wifiname', width: 80},
            {label: 'wifi密码', name: 'wifipass', index: 'wifipass', width: 80},
            {label: 'wifimac地址', name: 'wifimac', index: 'wifimac', width: 80},
            {label: '房价二维码', name: 'qrcode', index: 'qrcode', width: 80},
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
            {label: '更新时间', name: 'updateTime', index: 'update_time', width: 80}]
    });
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        companyRoom: {},
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
            vm.companyRoom = {};
            vm.getCompany();
        },
        update: function (event) {
            let roomId = getSelectedRow("#jqGrid");
            if (roomId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(roomId)
        },
        saveOrUpdate: function (event) {
            let url = vm.companyRoom.roomId == null ? "../companyroom/save" : "../companyroom/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.companyRoom),
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
            let roomIds = getSelectedRows("#jqGrid");
            if (roomIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../companyroom/delete",
                    params: JSON.stringify(roomIds),
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
        getInfo: function (roomId) {
            Ajax.request({
                url: "../companyroom/info/" + roomId,
                async: true,
                successCallback: function (r) {
                    vm.companyRoom = r.companyRoom;
                    vm.getCompany();
                }
            });
        },
        getCompany: function () {//获取商品顶部轮播图
            Ajax.request({
                url: "../company/queryAll",
                async: true,
                successCallback: function (r) {
                    vm.companyList = r.list;
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