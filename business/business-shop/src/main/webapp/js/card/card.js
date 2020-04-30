$(function () {
    $("#jqGrid").Grid({
        url: '../card/list',
        colModel: [
            {label: 'cardId', name: 'cardId', index: 'card_id', key: true, hidden: true},
            {label: '微信昵称', name: 'nickname', index: 'nickname', width: 80},
            {label: '公司名称', name: 'companyName', index: 'companyName', width: 80},
            {label: '真事姓名', name: 'realname', index: 'realname', width: 80},
            {label: '手机号码', name: 'mobile', index: 'mobile', width: 80},
            {
                label: '照片', name: 'photo', index: 'photo', width: 80, formatter: function (value) {
                    return transImg(value);
                }
            },
            {label: '职位', name: 'position', index: 'position', width: 80},
            {label: '微信号', name: 'wechat', index: 'wechat', width: 80},
            {label: '邮箱地址', name: 'email', index: 'email', width: 80},
            {label: '个人简介', name: 'profile', index: 'profile', width: 80},
            {label: '省份', name: 'province', index: 'province', width: 80},
            {label: '城市', name: 'city', index: 'city', width: 80},
            {label: '区县', name: 'county', index: 'county', width: 80},
            {label: '详细地址', name: 'address', index: 'address', width: 80},
            {label: '座机', name: 'telephone', index: 'telephone', width: 80},
            {
                label: '二维码', name: 'qrCode', index: 'qr_code', width: 80, formatter: function (value) {
                    return transImg("https://emiaoweb.com/business/upload/" + value);
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
        card: {},
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
            vm.card = {};
        },
        update: function (event) {
            let cardId = getSelectedRow("#jqGrid");
            if (cardId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(cardId)
        },
        saveOrUpdate: function (event) {
            let url = vm.card.cardId == null ? "../card/save" : "../card/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.card),
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
            let cardIds = getSelectedRows("#jqGrid");
            if (cardIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../card/delete",
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
        getInfo: function (cardId) {
            Ajax.request({
                url: "../card/info/" + cardId,
                async: true,
                successCallback: function (r) {
                    vm.card = r.card;
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