const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const user = require('../../../services/user.js');

Page({
    data: {
        nowPgae: 1,
        startX: 0,
        slider: false,
        animationData: {},
        cardInfoList: [{
            name: 1
        }, {
            name: 2
        }, {
            name: 3
        }],


        param: "",
        loginOpenid: "",
        cards: [],
        mycards: [],
        maskHidden: false,
        isNewUser: true,
        isZiji: false,
        isCollectBtn: false,
        shareUserId: 0,


        // 页面配置
        winHeight: 0,
        // tab切换
        current: 0,
    },
    touchstart(e) {
        this.setData({
            startX: e.changedTouches[0].clientX,
        })
    },
    touchend(e) {
        let that = this;
        let startX = this.data.startX;
        let endX = e.changedTouches[0].clientX;
        if (this.data.slider) return;

        // 下一页(左滑距离大于30)
        if (startX - endX > 30) {
            this.setData({
                slider: true
            });
            //尾页(当前页 等于 总页数)
            if (this.data.nowPgae == this.data.cardInfoList.length) {
                this.setData({
                    slider: false
                });
                wx.showToast({
                    title: '已经是最后一张了',
                    icon: 'none'
                });
                return;
            };

            //创建动画   5s将位置移动到-150%,-150%
            let animation = wx.createAnimation({
                duration: 500,
            });
            //animation.translateX('-150%').translateY('-150%').rotate(60).step();
            animation.translateX('100%').translateY('-100%').rotateX(90).rotateY(0).step();
            this.setData({
                animationData: animation.export()
            });

            // 移动完成后
            setTimeout(function () {
                var cardInfoList = that.data.cardInfoList;
                var slidethis = that.data.cardInfoList.shift(); //删除数组第一项
                that.data.cardInfoList.push(slidethis); //将第一项放到末尾
                //创建动画   将位置归位
                let animation = wx.createAnimation({
                    duration: 0,
                });
                animation.translateX('-50%').translateY('-50%').rotate(0).step();

                that.setData({
                    cardInfoList: that.data.cardInfoList,
                    animationData: animation.export(),
                    slider: false,
                    nowPgae: that.data.nowPgae + 1
                });
            }, 500)
        }

        // 上一页
        if (endX - startX > 30) {
            this.setData({
                slider: true
            })
            //首页
            if (this.data.nowPgae == 1) {
                this.setData({
                    slider: false
                })
                wx.showToast({
                    title: '已经到第一张了',
                    icon: 'none'
                })
                return;
            };

            //创建动画  移动到-150%,-150%
            let animation = wx.createAnimation({
                duration: 0,
            });
            //animation.translateX('-150%').translateY('-150%').rotate(100).step();         
            animation.translateX('-150%').translateY('-150%').rotateX(180).rotateY(0).step();

            var cardInfoList = that.data.cardInfoList;

            var slidethis = that.data.cardInfoList.pop(); //删除数组末尾项
            that.data.cardInfoList.unshift(slidethis); //将删除的末尾项放到第一项
            that.setData({
                animationData: animation.export(),
                cardInfoList: that.data.cardInfoList,
            });

            setTimeout(function () {
                //创建动画   5s将位置移动到原位
                let animation2 = wx.createAnimation({
                    duration: 500,
                    // timingFunction: 'cubic-bezier(.8,.1,.2,0.8)',
                });
                animation2.translateX('-50%').translateY('-50%').rotate(0).step();
                that.setData({
                    animationData: animation2.export()
                });
                that.setData({
                    slider: false,
                    nowPgae: that.data.nowPgae - 1
                });
            }, 50)
        }
    },









    onLoad: function (options) {
        wx.stopPullDownRefresh();
        let that = this;
        that.setData({
            loginOpenid: wx.getStorageSync('token')
        });
        if (typeof (options) != "undefined") {
            if (options.scene) {
                let scene = '?' + decodeURIComponent(options.scene);
                that.setData({
                    param: util.getQueryString(scene, 'param'),
                })
            } else if (options.param != "undefined" && typeof (options.param) != "undefined") {
                that.setData({
                    param: options.param,
                })
            }
        }
        that.getOwnCardInfo();
    },

    onShareAppMessage: function (res) {
        util.request(api.SaveShare, {}, 'POST', 'application/x-www-form-urlencoded').then(function (res) {});
        if (res.from == 'button') {
            if (res.target.dataset.sharetype == "mycard") {
                console.log("share:" + this.data.mycards.param)
                return {
                    title: "您好，我是" + this.data.mycards.realname + "，请惠存我的名片",
                    desc: '销擎名片管理',
                    path: '/pages/card/indexs/indexs?param=' + this.data.mycards.param,
                    imageUrl: '/static/images/card/p_card.jpg',
                    success: function (res) {
                        // 转发成功
                        console.log('转发成功')
                        //this.setInfo(res);
                    },
                    fail: function (res) {
                        // 转发失败
                        console.log('转发失败')
                    }
                }
            }
        }
        console.log("share:" + this.data.cards.param)
        return {
            title: "您好，我是" + this.data.cards.realname + "，请惠存我的名片",
            desc: '销擎名片管理',
            path: '/pages/card/indexs/indexs?param=' + this.data.cards.param,
            success: function (res) {
                // 转发成功
                console.log('转发成功')
            },
            fail: function (res) {
                // 转发失败
                console.log('转发失败')
            }
        }
    },
    getOwnCardInfo: function () {
        let that = this;
        if (that.data.loginOpenid) {
            util.request(api.CardInfoByOpenID, {
                openid: that.data.loginOpenid
            }).then(function (res) {
                if (res.errno === 0) {
                    if (res.data.realname) {
                        that.setData({
                            isNewUser: false,
                        });
                    }
                    if (res.data.qrCode) {
                        res.data.qrCode = "https://emiaoweb.com/business/upload/" + res.data.qrCode;
                    }
                    that.setData({
                        mycards: res.data,
                    });
                    if (!that.data.param || that.data.param == res.data.param) {
                        that.setData({
                            param: res.data.param,
                            cards: res.data,
                            isZiji: true
                        });
                        wx.hideLoading();
                    } else {
                        that.getCardInfo()
                    }
                } else {
                    console.log("that.data.loginOpenid:" + that.data.loginOpenid + "=>errno:" + res.errno + "=>errmsg:" + res.errmsg)
                    // if (res.errmsg == "未注册名片") {
                    //   wx.redirectTo({
                    //     url: '/pages/card/adduser/adduser',
                    //   })
                    // }
                    that.getCardInfo()
                }
            }).catch((err) => {
                that.getCardInfo()
                console.log("catch" + err)
            });
        } else {
            that.getCardInfo()
        }
    },
    getCardInfo: function () {
        let that = this;
        if (!that.data.param) {
            // that.setData({
            //     param: '4~6CkgY',
            // })
            wx.navigateTo({
                url: '/pages/card/adduser/adduser'
            })
            return;
        }
        util.request(api.CardInfoByParam, {
            param: that.data.param
        }).then(function (res) {
            if (res.errno === 0) {
                if (res.data.qrCode) {
                    res.data.qrCode = "https://emiaoweb.com/business/upload/" + res.data.qrCode;
                }
                that.setData({
                    cards: res.data
                });
                wx.hideLoading();
                that.isCollect()
                that.record()
            } else {
                util.showErrorToast(res.errmsg);
            }
        }).catch((err) => {
            wx.hideLoading();
            wx.showModal({
                title: "服务连接出错",
                content: "请卸载小程序，稍后再访问"
            });
        });
    },
    copyText: function (e) {
        wx.setClipboardData({
            data: e.currentTarget.dataset.text,
            success() {
                wx.showToast({
                    title: '复制成功',
                    icon: 'success',
                    duration: 1000
                })
            },
            fail() {
                wx.showToast({
                    title: '复制失败',
                    icon: 'error',
                    duration: 1000
                })
            }
        })
    },
    callPhone: function (e) {
        wx.makePhoneCall({
            phoneNumber: e.currentTarget.dataset.text
        })
    },
    addPhone: function () {
        let that = this;
        // 添加到手机通讯录
        wx.addPhoneContact({
            firstName: that.data.cards.realname, //联系人姓名
            mobilePhoneNumber: that.data.cards.mobile, //联系人手机号
            weChatNumber: that.data.cards.wechat,
            addressState: that.data.cards.province,
            addressCity: that.data.cards.city,
            addressStreet: that.data.cards.county,
            organization: that.data.cards.companyName,
            title: that.data.cards.position,
            email: that.data.cards.email,
        })
    },
    showQrCode: function (e) {
        let that = this;
        if (that.data.cards.qrCode) {
            this.setData({
                maskHidden: true
            });
        } else {
            wx.showLoading({
                title: '生成中',
            })
            util.request(api.CreateCardQrCode, {
                param: that.data.param
            }).then(function (res) {
                if (res.errno === 0) {
                    if (res.data) {
                        that.setData({
                            maskHidden: true,
                            "cards.qrCode": "https://emiaoweb.com/business/upload/" + res.data
                        })
                    }
                    wx.hideLoading()
                }
            });
        }
    },
    saveQRCode() {
        let that = this
        wx.downloadFile({
            url: that.data.cards.qrCode,
            success: function (res) {
                if (res.statusCode === 200) {
                    let img = res.tempFilePath;
                    wx.saveImageToPhotosAlbum({
                        filePath: img,
                        success(res) {
                            wx.showToast({
                                title: '保存成功',
                                icon: 'success',
                                duration: 2000
                            })
                            that.setData({
                                maskHidden: false
                            })
                        },
                        fail(res) {
                            util.showErrorToast('保存失败');
                        }
                    });
                } else {
                    util.showErrorToast('下载失败');
                }
            }
        });
    },
    //关闭
    closeQrCode: function () {
        this.setData({
            maskHidden: false
        })
    },
    collect: function () {
        let that = this;
        if (!that.data.loginOpenid) {
            wx.showToast({
                title: "请先登录",
                icon: 'loading',
                duration: 2000
            });
            setTimeout(function () {
                wx.navigateTo({
                    url: '/pages/auth/login/login?id=-1&type='
                })
                wx.removeStorageSync('userInfo');
            }, 2000)
        } else {
            //if (!that.data.isCollectBtn) {
            util.request(api.CardSaveCollect, {
                param: that.data.param
            }, 'POST', 'application/x-www-form-urlencoded').then(function (res) {
                if (res.errno === 0) {
                    if (that.data.isCollectBtn) {
                        that.setData({
                            isCollectBtn: false
                        })
                        wx.showToast({
                            title: '取消收藏成功'
                        });
                    } else {
                        that.setData({
                            isCollectBtn: true
                        })
                        wx.showToast({
                            title: '收藏到名片夹'
                        });
                    }
                } else {
                    util.showErrorToast(res.errmsg);
                }
            });
            // }
            //  else {
            //   wx.showToast({
            //     title: '已收藏'
            //   });
            // }
        }
    },
    isCollect: function () {
        let that = this;
        if (that.data.loginOpenid) {
            util.request(api.CardIsCollect, {
                param: that.data.param
            }).then(function (res) {
                if (res.errno === 0) {
                    if (res.data > 0) {
                        that.setData({
                            isCollectBtn: true
                        })
                    }
                }
            });
        }
    },
    record: function () {
        let that = this;
        if (that.data.loginOpenid) {
            util.request(api.CardSaveRecord, {
                param: that.data.param
            }, 'POST', 'application/x-www-form-urlencoded').then(function (res) {});
        }
    },
    backMeCard: function () {
        this.setData({
            param: ''
        })
        this.getOwnCardInfo()
    },
    //预览图片
    topicPreview: function (e) {
        var that = this;
        var url = e.currentTarget.dataset.url;
        if (!url) {
            return false;
        }
        var images = new Array();
        if (typeof (e.currentTarget.dataset.type) != "undefined") {
            var imagearr = ["jpg", "bmp", "gif", "png", "jpeg"];
            for (var i = 0; i < that.data.cards.company.fileList.length; i++) {
                var str = that.data.cards.company.fileList[i].fileurl
                var type = (str.substring(str.lastIndexOf(".") + 1, str.length)).toLowerCase();
                if (imagearr.indexOf(type) > -1) {
                    images.push(str)
                }
            }
        } else {
            images.push(url)
        }
        wx.previewImage({
            current: url, // 当前显示图片的http链接
            urls: images // 需要预览的图片http链接列表
        })
    },









    bindChange: function (e) {
        // 滑动切换tab
        let comheight = e.detail.currentItemId;
        if (comheight % 2 != 0) {
            comheight = parseInt(comheight) + 1;
        }
        this.setData({
            current: e.detail.current,
            winHeight: 458 * comheight / 2
        });
    },
    swichNav: function (e) {
        // 点击tab切换
        if (this.data.current === e.target.dataset.current) {
            return false;
        } else {
            this.setData({
                current: e.target.dataset.current
            })
        }
    }
})