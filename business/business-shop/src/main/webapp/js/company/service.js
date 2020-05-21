$(function () {
    $("#jqGrid").Grid({
        url: '../companyservice/list',
        colModel: [
            {label: '', name: 'companyId', index: '', key: true, hidden: true},
            {label: '企业名称', name: 'companyName', index: 'companyName', width: 80},
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
            {label: '', name: 'serviceClass', index: '', key: true, hidden: true},
            {
                label: '标签', name: 'listServiceTag', index: 'listServiceTag', width: 80, formatter: function (value) {
                    var txt = "";
                    for (var i = 0; i < value.length; i++) {
                        txt += value[i] + "、";
                    }
                    return txt;
                }
            }]
    });
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        companyService: {},
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            name: ''
        },
        companyList: [],
        serviceClass: [],
        serviceTag: [],
        othertag: ''
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.companyService = {companyId:null};
            vm.othertag = "";

            vm.getServiceClass();
            vm.getCompany();
        },
        update: function (event) {
            let entity = getSelectedRowData("#jqGrid");
            if (entity == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.othertag = "";

            vm.getInfo(entity.companyId, entity.serviceClass)
        },
        saveOrUpdate: function (event) {
            Ajax.request({
                url: "../companyservice/save",
                params: JSON.stringify(vm.companyService),
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
            let entity = getSelectedRowData("#jqGrid");
            if (entity == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../companyservice/delete",
                    params: {
                        "companyId": entity.companyId,
                        "serviceClass": entity.serviceClass
                    },
                    type: "POST",
                    successCallback: function () {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        },
        getInfo: function (companyId, serviceClass) {
            Ajax.request({
                url: "../companyservice/info?companyId=" + companyId + "&serviceClass=" + serviceClass,
                async: true,
                successCallback: function (r) {
                    vm.companyService = r.companyService;
                    vm.getCompany();
                    vm.getServiceClass();
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
        },
        getCompany: function () {//获取所有企业
            Ajax.request({
                url: "../company/getAll",
                async: true,
                successCallback: function (r) {
                    vm.companyList = r.list;
                    if (r.cid != 0) {
                        vm.companyService.companyId = r.cid;
                    }
                }
            });
        },
        getServiceClass: function () {
            Ajax.request({
                url: "../sys/macro/queryMacrosByValue?value=serviceClass",
                async: true,
                successCallback: function (r) {
                    vm.serviceClass = r.list;
                }
            });
        },
        changeServiceClass: function (opt) {
            let model = opt.value
            if (model && (!vm.companyService.companyId || vm.companyService.companyId == 0)) {
                iview.Message.error("请选择企业");
                return
            }
            Ajax.request({
                url: "../companyservice/getServiceTag?companyId=" + vm.companyService.companyId + "&serviceClass=" + model,
                async: true,
                successCallback: function (r) {
                    vm.serviceTag = r.list;
                }
            });
        },
        handleAdd() {
            Ajax.request({
                url: '../companyservice/saveTag',
                params: {
                    "companyId": vm.companyService.companyId,
                    "serviceClass": vm.companyService.serviceClass,
                    "serviceTag": vm.othertag
                },
                type: "POST",
                successCallback: function (r) {
                    vm.serviceTag.push(vm.othertag)
                }
            });
        },
    }
});