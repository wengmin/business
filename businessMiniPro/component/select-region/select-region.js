// component/select-region/select-region.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    // province_id: { // 属性名
    //   type: Number, // 类型（必填），目前接受的类型包括：String, Number, Boolean, Object, Array, null（表示任意类型）
    //   value: 0, // 属性初始值（可选），如果未指定则会根据类型选择一个
    //   observer: function (newVal, oldVal) { } // 属性被改变时执行的函数（可选），也可以写成在methods段中定义的方法名字符串, 如：_propertyChange'
    // },
    // city_id: {
    //   type: Number,
    //   value: 0,
    //   observer: function (newVal, oldVal) { }
    // },
    // county_id: {
    //   type: Number,
    //   value: 0,
    //   observer: function (newVal, oldVal) { }
    // },
    // full_region: {
    //   type: String,
    //   value: "",
    //   observer: function (newVal, oldVal) { } 
    // }
    province: String, // 简化的定义方式
    city: String, // 简化的定义方式
    county: String // 简化的定义方式
  },

  /**
   * 组件的初始数据
   */
  data: {
    openSelectRegion: false,
    selectRegionList: [
      { id: 0, name: '省份', parent_id: 1, type: 1 },
      { id: 0, name: '城市', parent_id: 1, type: 2 },
      { id: 0, name: '区县', parent_id: 1, type: 3 }
    ],
    regionType: 1,
    regionList: [],
    selectRegionDone: false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    setRegionDoneStatus() {
      let that = this;
      let doneStatus = that.data.selectRegionList.every(item => {
        return item.id != 0;
      });

      that.setData({
        selectRegionDone: doneStatus
      })
    },
    chooseRegion() {
      let that = this;
      this.setData({
        openSelectRegion: !this.data.openSelectRegion
      });

      //设置区域选择数据
      let data = this.data;
      if (data.province && data.city && data.county) {
        let selectRegionList = this.data.selectRegionList;
        selectRegionList[0].id = data.province_id;
        selectRegionList[0].name = data.province;
        selectRegionList[0].parent_id = 1;

        selectRegionList[1].id = data.city_id;
        selectRegionList[1].name = data.city;
        selectRegionList[1].parent_id = data.province_id;

        selectRegionList[2].id = data.county_id;
        selectRegionList[2].name = data.county;
        selectRegionList[2].parent_id = data.city_id;

        this.setData({
          selectRegionList: selectRegionList,
          regionType: 3
        });

        this.getRegionList(data.city_id);
      } else {
        this.setData({
          selectRegionList: [
            { id: 0, name: '省份', parent_id: 1, type: 1 },
            { id: 0, name: '城市', parent_id: 1, type: 2 },
            { id: 0, name: '区县', parent_id: 1, type: 3 }
          ],
          regionType: 1
        })
        this.getRegionList(1);
      }

      this.setRegionDoneStatus();

    },
    selectRegionType(event) {
      let that = this;
      let regionTypeIndex = event.target.dataset.regionTypeIndex;
      let selectRegionList = that.data.selectRegionList;

      //判断是否可点击
      if (regionTypeIndex + 1 == this.data.regionType || (regionTypeIndex - 1 >= 0 && selectRegionList[regionTypeIndex - 1].id <= 0)) {
        return false;
      }

      this.setData({
        regionType: regionTypeIndex + 1
      })

      let selectRegionItem = selectRegionList[regionTypeIndex];

      this.getRegionList(selectRegionItem.parent_id);

      this.setRegionDoneStatus();

    },
    selectRegion(event) {
      let that = this;
      let regionIndex = event.target.dataset.regionIndex;
      let regionItem = this.data.regionList[regionIndex];
      let regionType = regionItem.type;
      let selectRegionList = this.data.selectRegionList;
      selectRegionList[regionType - 1] = regionItem;


      if (regionType != 3) {
        this.setData({
          selectRegionList: selectRegionList,
          regionType: regionType + 1
        })
        this.getRegionList(regionItem.id);
      } else {
        this.setData({
          selectRegionList: selectRegionList
        })
      }

      //重置下级区域为空
      selectRegionList.map((item, index) => {
        if (index > regionType - 1) {
          item.id = 0;
          item.name = index == 1 ? '城市' : '区县';
          item.parent_id = 0;
        }
        return item;
      });

      this.setData({
        selectRegionList: selectRegionList
      })


      that.setData({
        regionList: that.data.regionList.map(item => {

          //标记已选择的
          if (that.data.regionType == item.type && that.data.selectRegionList[that.data.regionType - 1].id == item.id) {
            item.selected = true;
          } else {
            item.selected = false;
          }

          return item;
        })
      });

      this.setRegionDoneStatus();

    },
    doneSelectRegion() {
      if (this.data.selectRegionDone === false) {
        return false;
      }

      let region = [];
      let selectRegionList = this.data.selectRegionList;
      region.province_id = selectRegionList[0].id;
      region.city_id = selectRegionList[1].id;
      region.county_id = selectRegionList[2].id;
      region.province_name = selectRegionList[0].name;
      region.city_name = selectRegionList[1].name;
      region.county_name = selectRegionList[2].name;
      region.full_region = selectRegionList.map(item => {
        return item.name;
      }).join('');

      this.setData({
        province: region.province_name,
        city: region.city_name,
        county: region.county_name,
        openSelectRegion: false
      });

      //传递到page
      this.triggerEvent('queding', region)

    },
    cancelSelectRegion() {
      this.setData({
        openSelectRegion: false,
        regionType: this.data.regionDoneStatus ? 3 : 1
      });

    },
    getRegionList(regionId) {
      let that = this;
      let regionType = that.data.regionType;
      wx.request({
        url: 'https://emiaoweb.com/business/api/region/list',
        data: { parentId: regionId },
        method: "GET",
        header: {
          'Content-Type': "application/json",
          'X-Nideshop-Token': wx.getStorageSync('token')
        },
        success: function (res) {
          if (res.data.errno === 0) {
            that.setData({
              regionList: res.data.data.map(item => {

                //标记已选择的
                if (regionType == item.type && that.data.selectRegionList[regionType - 1].id == item.id) {
                  item.selected = true;
                } else {
                  item.selected = false;
                }

                return item;
              })
            });
          }

        },
        fail: function (err) {
          console.log(err)
        }
      })
    },
  }
})
