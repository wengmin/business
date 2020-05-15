$(function () {
    $("#jqGrid").Grid({
        url: '../companystaff/list',
        colModel: [
			{label: 'staffId', name: 'staffId', index: 'staff_id', key: true, hidden: true},
			{label: '企业编号', name: 'companyId', index: 'company_id', width: 80},
			{label: '对应后台账号', name: 'sysUserId', index: 'sys_user_id', width: 80},
			{label: '名片', name: 'cardId', index: 'card_id', width: 80},
			{label: '是否管理员', name: 'isAdmin', index: 'is_admin', width: 80},
			{label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
			{label: '更新时间', name: 'updateTime', index: 'update_time', width: 80}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		companyStaff: {},
		ruleValidate: {
			name: [
				{required: true, message: '名称不能为空', trigger: 'blur'}
			]
		},
		q: {
		    name: ''
		},
        provinceNames:[],
        selProId:0,
        selCityProId:0,
        cityNames:[],
        selCityId:0,
        countyNames:[],
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.companyStaff = {};
            this.getProvinceNames();
            this.getCityNames();
            this.getCountyNames();
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
			if (staffIds == null){
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
		getInfo: function(staffId){
            Ajax.request({
                url: "../companystaff/info/"+staffId,
                async: true,
                successCallback: function (r) {
                    vm.companyStaff = r.companyStaff;
                }
            });
            this.getProvinceNames();
            this.getCityNames();
            this.getCountyNames();
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
        },
        /**
         * 获取省列表
         */
        getProvinceNames: function () {
            Ajax.request({
                url: "../sys/region/getAllProvice?areaId="+1,
                async: true,
                successCallback: function (r) {
                    vm.provinceNames = r.list;
                }
            });
        },
        /**
         * 选择省操作调用方法
         */
        proNameChange: function(val){
            console.log('-------val',val);
            if(val == null || val == ""){
                console.log('proNameChange-------val',val);
                return;
            }
            vm.selProId = val;
            vm.getCityNames();
        },
        /**
         * 获取市列表
         */
        getCityNames: function () {
            if(vm.selProId == null || vm.selProId == ""){
                console.log('getCityNames-------vm.selProId',vm.selProId);
                return;
            }
            Ajax.request({
                url: "../sys/region/getAllCityByName?areaName="+vm.selProId,
                async: true,
                successCallback: function (r) {
                    vm.cityNames = r.list;
                }
            });
        },
        /**
         * 选择市操作调用方法
         */
        proNameCityChange: function(val){
            console.log('-------val2',val);
            if(val == null || val == ""){
                console.log('proNameCityChange-------val',val);
                return;
            }
            vm.selCityProId = val;
            vm.getCountyNames();
        },
        /**
         * 获取区列表
         */
        getCountyNames: function () {
            if(vm.selCityProId == null || vm.selCityProId == ""){
                console.log('getCountyNames-------vm.selCityProId',vm.selCityProId);
                return;
            }
            Ajax.request({
                url: "../sys/region/getChildrenDistrictByName?areaName="+vm.selCityProId,
                async: true,
                successCallback: function (r) {
                    vm.countyNames = r.list;
                }
            });
        },
	}
});