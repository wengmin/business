$(function () {
    $("#jqGrid").Grid({
        url: '../serviceinvoice/list',
        colModel: [
            {label: 'invoiceId', name: 'invoiceId', index: 'invoice_id', key: true, hidden: true},
            {label: '企业名称', name: 'companyName', index: 'companyName', width: 80},
            {label: '房间号', name: 'roomName', index: 'roomName', width: 80},
            // {label: '用户微信昵称', name: 'nickname', index: 'nickname', width: 80},
            {label: '处理员工姓名', name: 'staffName', index: 'staffName', width: 80},
            {label: '发票内容', name: 'content', index: 'content', width: 80},
            {
                label: '类型', name: 'titletype', index: 'titletype', width: 80, formatter: function (value) {
                    var txt = "";
                    if (value == "1") {
                        txt = "个人";
                    }
                    if (value == "2") {
                        txt = "单位";
                    }
                    return txt;
                }
            },
            {label: '发票抬头、单位名称', name: 'titlename', index: 'titlename', width: 80},
            {label: '发票税号', name: 'taxno', index: 'taxno', width: 80},
            {label: '注册地址', name: 'registaddress', index: 'registaddress', width: 80},
            {label: '注册电话', name: 'registphone', index: 'registphone', width: 80},
            {label: '开户银行', name: 'bank', index: 'bank', width: 80},
            {label: '银行账号', name: 'bankaccount', index: 'bankaccount', width: 80},
            {
                label: '开票状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                    var txt = "";
                    if (value == "0") {
                        txt = "未开票";
                    }
                    if (value == "1") {
                        txt = "已开票";
                    }
                    return txt;
                }
            },
            {label: '备注', name: 'remark', index: 'remark', width: 80},
            {label: '回复', name: 'reply', index: 'reply', width: 80},
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
        serviceInvoice: {},
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
            vm.serviceInvoice = {};
        },
        update: function (event) {
            let invoiceId = getSelectedRow("#jqGrid");
            if (invoiceId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(invoiceId)
        },
        saveOrUpdate: function (event) {
            let url = vm.serviceInvoice.invoiceId == null ? "../serviceinvoice/save" : "../serviceinvoice/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.serviceInvoice),
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
            let invoiceIds = getSelectedRows("#jqGrid");
            if (invoiceIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../serviceinvoice/delete",
                    params: JSON.stringify(invoiceIds),
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
        getInfo: function (invoiceId) {
            Ajax.request({
                url: "../serviceinvoice/info/" + invoiceId,
                async: true,
                successCallback: function (r) {
                    vm.serviceInvoice = r.serviceInvoice;
                    vm.serviceInvoice.titletypetext = vm.serviceInvoice.titletype == 1 ? "个人" : "企业"
                    vm.serviceInvoice.staffName = vm.serviceInvoice.staffId == -1 ? "管理员" : vm.serviceInvoice.staffName
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