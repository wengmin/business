$(function () {
    $("#jqGrid").Grid({
        url: '../user/list',
        colModel: [
            {label: 'userId', name: 'userId', index: 'user_id', key: true, hidden: true},
            {label: '微信昵称', name: 'nickname', index: 'nickname', width: 80},
            {label: '手机号码', name: 'mobile', index: 'mobile', width: 80},
            {
                label: '微信头像', name: 'avatar', index: 'avatar', width: 80, formatter: function (value) {
                    return transImg(value);
                }
            },
            {label: '性别', name: 'gender', index: 'gender', width: 80},
            {label: 'openid', name: 'openId', index: 'openId', width: 80},
            {label: '省份', name: 'province', index: 'province', width: 80},
            {label: '城市', name: 'city', index: 'city', width: 80},
            {label: '国家', name: 'county', index: 'county', width: 80},
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

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        user: {},
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
            vm.user = {};
        },
        update: function (event) {
            var userId = getSelectedRow("#jqGrid");
            if (userId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(userId)
        },
        saveOrUpdate: function (event) {
            var url = vm.user.userId == null ? "../user/save" : "../user/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.user),
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
            var cardIds = getSelectedRows("#jqGrid");
            if (cardIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../user/delete",
                    params: JSON.stringify(cardIds),
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
        getInfo: function (userId) {
            Ajax.request({
                url: "../user/info/" + userId,
                async: true,
                successCallback: function (r) {
                    vm.user = r.user;
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
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