$(function () {
    $("#jqGrid").Grid({
        url: '../dynamic/list',
        colModel: [
            {label: 'dynamicId', name: 'dynamicId', index: 'dynamic_id', key: true, hidden: true},
            {
                label: '类型', name: 'type', index: 'type', width: 80, formatter: function (value) {
                    var typetxt = "";
                    switch (value) {
                        case 1:
                            typetxt = "html格式";
                            break;
                        case 2:
                            typetxt = "url链接";
                            break;
                        case 3:
                            typetxt = "图片";
                            break;
                        case 4:
                            typetxt = "视频";
                            break;
                    }
                    return typetxt;
                }
            },
            {label: '标签', name: 'tag', index: 'tag', width: 80},
            {label: '标题', name: 'title', index: 'title', width: 80},
            {label: '内容', name: 'content', index: 'content', width: 80},
            {label: '封面', name: 'cover', index: 'cover', width: 80},
            {label: '图集', name: 'imgs', index: 'imgs', width: 80},
            {label: '创建人', name: 'createUserId', index: 'create_user_id', width: 80},
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 80}]
    });
    $('#content').editable({
        inlineMode: false,
        alwaysBlank: true,
        height: 'auto', //高度
        language: "zh_cn",
        minHeight: '200px',
        spellcheck: false,
        plainPaste: true,
        enableScript: false,
        imageButtons: ["floatImageLeft", "floatImageNone", "floatImageRight", "linkImage", "replaceImage", "removeImage"],
        allowedImageTypes: ["jpeg", "jpg", "png", "gif"],
        imageUploadURL: '../sys/oss/upload',//上传到本地服务器
        imageUploadParams: {id: "edit"},
        imagesLoadURL: '../sys/oss/queryAll'//管理图片
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        dynamic: {},
        ruleValidate: {
            title: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            name: ''
        },
        uploadList: [],
        imgName: '',
        visible: false,
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.dynamic = {type: 1, tag: 3};
            vm.uploadList = [];

            $('#content').editable('setHTML', '');
        },
        update: function (event) {
            var dynamicId = getSelectedRow("#jqGrid");
            if (dynamicId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            this.uploadList.splice(0, this.uploadList.length);
            vm.getInfo(dynamicId)

            var imgs = vm.dynamic.imgs.split('|')
            this.uploadList = [];
            for (var i = 0; i < imgs.length; i++) {
                if (imgs[i] != "") {
                    this.uploadList.push({name: imgs[i], imgUrl: imgs[i], showProgress: false, status: "finished"})
                }
            }
        },
        saveOrUpdate: function (event) {
            var url = vm.dynamic.dynamicId == null ? "../dynamic/save" : "../dynamic/update";
            debugger
            if (vm.dynamic.type == 3) {
                vm.dynamic.imgs = "";
                for (var i = 0; i < this.uploadList.length; i++) {
                    vm.dynamic.imgs += this.uploadList[i].imgUrl + "|"
                }
                if (vm.dynamic.imgs) {
                    vm.dynamic.imgs = vm.dynamic.imgs.substring(0, vm.dynamic.imgs.length - 1);
                }
            }
            if (vm.dynamic.type == 1) {
                vm.dynamic.content = $('#content').editable('getHTML');
            }
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.dynamic),
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
            var dynamicIds = getSelectedRows("#jqGrid");
            if (dynamicIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../dynamic/delete",
                    params: JSON.stringify(dynamicIds),
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
        getInfo: function (dynamicId) {
            Ajax.request({
                url: "../dynamic/info/" + dynamicId,
                async: false,//设置同步加载,才能生效
                successCallback: function (r) {
                    vm.dynamic = r.dynamic;
                    $('#content').editable('setHTML', vm.dynamic.content);
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
        },
        handleSuccessNewPicUrl: function (res, file) {
            vm.dynamic.cover = file.response.url;
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
                desc: '文件 ' + file.name + ' 太大，不能超过 2M。'
            });
        },
        handleVideoMaxSize: function (file) {
            this.$Notice.warning({
                title: '超出文件大小限制',
                desc: '文件 ' + file.name + ' 太大，不能超过 10M。'
            });
        },
        handleSuccessVideo: function (res, file) {
            vm.dynamic.imgs = file.response.url;
        },
        eyeImageNewPicUrl: function () {
            var url = vm.dynamic.cover;
            eyeImage(url);
        },
        handleView(name) {
            this.imgName = name;
            this.visible = true;
        },
        handleRemove(file) {
            // 从 upload 实例删除数据
            var fileList = this.uploadList;
            this.uploadList.splice(fileList.indexOf(file), 1);
        },
        handleSuccess(res, file) {
            // 因为上传过程为实例，这里模拟添加 url
            file.imgUrl = res.url;
            file.name = res.url;
            vm.uploadList.add(file);
        },
        handleBeforeUpload() {
            var check = this.uploadList.length < 9;
            if (!check) {
                this.$Notice.warning({
                    title: '超过数量了'
                });
            }
            return check;
        }
    },
    mounted() {
        this.uploadList = this.$refs.upload.fileList;
    }
});