$(function () {
    $("#jqGrid").Grid({
        url: '../companyservice/list',
        colModel: [
			{label: 'serviceId', name: 'serviceId', index: 'service_id', key: true, hidden: true},
			{label: '企业名称', name: 'companyId', index: 'company_id', width: 80},
			{label: '服务类型', name: 'class', index: 'class', width: 80},
			{label: '标签', name: 'tag', index: 'tag', width: 80},
			{label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
			{label: '更新时间', name: 'updateTime', index: 'update_time', width: 80}]
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
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.companyService = {};
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
            let url = vm.companyService.serviceId == null ? "../companyservice/save" : "../companyservice/update";
            Ajax.request({
			    url: url,
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
            let serviceIds = getSelectedRows("#jqGrid");
			if (serviceIds == null){
				return;
			}

			confirm('确定要删除选中的记录？', function () {
                Ajax.request({
				    url: "../companyservice/delete",
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
		getInfo: function(serviceId){
            Ajax.request({
                url: "../companyservice/info/"+serviceId,
                async: true,
                successCallback: function (r) {
                    vm.companyService = r.companyService;
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
        reloadSearch: function() {
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