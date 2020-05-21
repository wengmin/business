$(function () {
    $("#jqGrid").Grid({
        url: '../company/list',
        colModel: [
            {label: 'companyId', name: 'companyId', index: 'company_id', key: true, hidden: true},
            {label: '企业名称', name: 'name', index: 'name', width: 80},
            {label: '省份', name: 'province', index: 'province', width: 80},
            {label: '城市', name: 'city', index: 'city', width: 80},
            {label: '区县', name: 'county', index: 'county', width: 80},
            {label: '地址', name: 'address', index: 'address', width: 80},
            {label: '电话', name: 'phone', index: 'phone', width: 80},
            {
                label: 'LOGO图标', name: 'logo', index: 'logo', width: 80, formatter: function (value) {
                    return transImg(value);
                }
            },
            {label: '规模', name: 'scale', index: 'scale', width: 80},
            {label: '行业', name: 'trade', index: 'trade', width: 80},
            {label: '公司简介', name: 'introduction', index: 'introduction', width: 80},
            {
                label: '二维码', name: 'qrcode', index: 'qrcode', width: 80, formatter: function (value) {
                    return transImg(value);
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
        companyInfo: {},
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
        cid:-1
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.companyInfo = {};
            this.getProvinceNames();
            this.getCityNames();
            this.getCountyNames();
        },
        update: function (event) {
            let companyId = getSelectedRow("#jqGrid");
            if (companyId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            this.getProvinceNames();
            this.getCityNames();
            this.getCountyNames();
            Ajax.request({
                url: "../company/info/" + companyId,
                async: true,
                successCallback: function (r) {
                    vm.companyInfo = r.companyInfo;
                }
            });
        },
        saveOrUpdate: function (event) {
            let url = vm.companyInfo.companyId == null ? "../company/save" : "../company/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.companyInfo),
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
            let companyIds = getSelectedRows("#jqGrid");
            if (companyIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../company/delete",
                    params: JSON.stringify(companyIds),
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
        handleSuccessNewPicUrl: function (res, file) {
            vm.companyInfo.logo = file.response.url;
        },
        handleFormatError: function (file) {
            this.$Notice.warning({
                title: '文件格式不正确',
                desc: '文件 ' + file.name + ' 格式不正确，请上传 jpg 或 png 格式的图片。'
            });
        },
        handleMaxSize: function (file) {
            this.$Notice.warning({
                title: '超出文件大小限制',
                desc: '文件 ' + file.name + ' 太大，不能超过 200KB。'
            });
        },
        eyeImageNewPicUrl: function () {
            var url = vm.companyInfo.logo;
            eyeImage(url);
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