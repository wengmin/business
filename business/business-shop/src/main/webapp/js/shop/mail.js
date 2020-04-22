$(function () {
    $("#jqGrid").Grid({
        url: '../mail/list',
        colModel: [
            {label: '', name: 'mailId', index: "mail_id", key: true, hidden: true},
            {label: '员工姓名', name: 'staffName'},
            {label: '手机号码', name: 'mobile'},
            {
                label: '状态', name: 'status', index: "status", formatter: function (value) {
                    var statustxt = "";
                    switch (value) {
                        case 1:
                            statustxt = "初次拜访";
                            break;
                        case 2:
                            statustxt = "方案报价";
                            break;
                        case 3:
                            statustxt = "商务谈判";
                            break;
                        case 4:
                            statustxt = "确认意向";
                            break;
                        case 5:
                            statustxt = "签约合作";
                            break;
                        case 6:
                            statustxt = "发现商机";
                            break;
                    }
                    return statustxt;
                }
            },
            {
                label: '创建时间', name: 'createTime', index: "create_time", formatter: function (value) {
                    return transDate(value);
                }
            },
            {
                label: '操作', width: 160, align: 'center', sortable: false, formatter: function (value, col, row) {
                    return '<button class="btn btn-outline btn-info" onclick="vm.lookFollow(' + row.mailId + ')"><i class="fa fa-info-circle"></i>&nbsp;跟进记录</button>';
                }
            }
        ]
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            staffName: null
        },
        followList: false,
        showList: true,
        title: null,
        follow: {
            mailId: 0,
            status: 1,
        },
        ruleValidate: {
            status: [
                {required: true, message: '请选择状态', trigger: 'blur'}
            ]
        },
        followData: {},
    },
    methods: {
        query: function () {
            vm.reload();
        },
        followd: function () {
            var mailId = getSelectedRow("#jqGrid");
            if (mailId == null) {
                return;
            }
            vm.follow.mailId = mailId;
            vm.showList = false;
            vm.title = "新增跟进记录";

            // Ajax.request({
            //     url: "../mail/info/" + mailId,
            //     async: true,
            //     successCallback: function (r) {
            //         vm.follow = r.follow;
            //     }
            // });

        },
        saveOrUpdate: function (event) {
            if (vm.follow.mailId == null) {
                return;
            }
            var url = "../mail/saveFollow";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.follow),
                contentType: "application/json",
                type: 'POST',
                successCallback: function () {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        lookFollow: function (rowId) { //
            vm.showList = false;
            vm.followList = true;
            vm.title = "跟进列表";
            Ajax.request({
                url: "../mail/followList/" + rowId,
                async: true,
                successCallback: function (r) {
                    var resdate = r.list;
                    for (var i = 0; i < resdate.length; i++) {
                        switch (resdate[i].status) {
                            case 1:
                                resdate[i].statusTxt = "初次拜访";
                                break;
                            case 2:
                                resdate[i].statusTxt = "方案报价";
                                break;
                            case 3:
                                resdate[i].statusTxt = "商务谈判";
                                break;
                            case 4:
                                resdate[i].statusTxt = "确认意向";
                                break;
                            case 5:
                                resdate[i].statusTxt = "签约合作";
                                break;
                            case 6:
                                resdate[i].statusTxt = "发现商机";
                                break;
                        }
                        resdate[i].createTime = transDate(resdate[i].createTime);
                    }
                    vm.followData = resdate;
                }
            });
            // $("#followGrid").Grid({
            //     url: "../mail/followList/" + rowId,
            //     colModel: [
            //         {label: '员工姓名', name: 'status', index: "status"},
            //         {label: '手机号码', name: 'remark', width: 75},
            //         {
            //             label: '创建时间', name: 'createTime', index: "create_time", width: 80, formatter: function (value) {
            //                 return transDate(value);
            //             }
            //         }
            //     ]
            // });
        },
        reload: function (event) {
            vm.showList = true;
            vm.followList = false;
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
        }
    }
});