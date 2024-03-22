<template>
  <div>
    <a-card :bordered="false">
      <div style="display: flex; flex-wrap: wrap">
          <head-info title="我的待办" content="8个预警规则" :bordered="true"/>
          <head-info title="本周预警平均处理时间" content="2小时" :bordered="true"/>
          <head-info title="本周预警漏洞数量" content="24个"/>
      </div>
    </a-card>
    <a-card
      style="margin-top: 24px"
      :bordered="false"
      title="规则列表"
    >
      <div slot="extra">
        <a-radio-group>
          <a-radio-button>全部</a-radio-button>
        </a-radio-group>
        <a-input-search style="margin-left: 16px; width: 272px;" />
      </div>
      <a-button  @click="showModel" type="dashed" style="width: 100%" icon="plus">添加</a-button>
      <a-modal class="modal" :visible="visible" width="1000px" centered title="预警规则-06" @ok="handleOk">
        <label>弱点标签属性：</label>
        <span></span>
        <a-tag color="#22A97E">
          弱点名称
        </a-tag>
        <br>
        <br>
        <label>产品标签属性：</label>
        <a-tag color="#22A97E">
          设备类型
        </a-tag>

        <a-tag color="#22A97E">
          厂商名
        </a-tag>

        <br>
        <br>
        <label>评分标签属性：</label>
        <a-tag color="#22A97E">
          危险级别
        </a-tag>
        <br>
        <br>

        <label>代码节点属性：</label>
        <a-tag color="#22A97E">
          代码类型
        </a-tag>
        
        <br>
        <br>
        
        <label>余弦相似阈值：</label>
        <a-tag width="200px" color="#22A97E">
          0.8
        </a-tag>
      <br>
      <br>
      <span>漏洞数量上限：</span>
      <a-tag color="#22A97E">
        5
      </a-tag>
      </a-modal>
      <a-list size="large" :pagination="{showSizeChanger: true, showQuickJumper: true, pageSize: 5, total: 5}">
        <a-list-item :key="i" >
          <a-list-item-meta
            description=""
          >
            <a-avatar slot="avatar" size="large" shape="square" src="https://redcathost.oss-cn-beijing.aliyuncs.com/Image-Host/alert.png"/>
            <a slot="title">预警规则-06</a>
          </a-list-item-meta>
          <div slot="actions">
            <a>编辑</a>
          </div>
          <div slot="actions">
            <a-dropdown>
              <a-menu slot="overlay">
                <a-menu-item @click="showModel"><a>查看</a></a-menu-item>
                <a-menu-item><a>删除</a></a-menu-item>
              </a-menu>
              <a>更多<a-icon type="down"/></a>
            </a-dropdown>
          </div>
          <div class="list-content">
            <div class="list-content-item">
              <span>Owner</span>
              <p>admin</p>
            </div>
            <div class="list-content-item">
              <span>开始时间</span>
              <p>2022-07-26 22:44</p>
            </div>
            <div class="list-content-item">
              <a-progress :percent="80" style="width: 180px" />
            </div>
          </div>
        </a-list-item>
        
        <a-list-item :key="i" >
          <a-list-item-meta
            description=""
          >
            <a-avatar slot="avatar" size="large" shape="square" src="https://redcathost.oss-cn-beijing.aliyuncs.com/Image-Host/alert.png"/>
            <a slot="title">预警规则-05</a>
          </a-list-item-meta>
          <div slot="actions">
            <a>编辑</a>
          </div>
          <div slot="actions">
            <a-dropdown>
              <a-menu slot="overlay">
                <a-menu-item><a>查看</a></a-menu-item>
                <a-menu-item><a>删除</a></a-menu-item>
              </a-menu>
              <a>更多<a-icon type="down"/></a>
            </a-dropdown>
          </div>
          <div class="list-content">
            <div class="list-content-item">
              <span>Owner</span>
              <p>admin</p>
            </div>
            <div class="list-content-item">
              <span>开始时间</span>
              <p>2022-07-26 22:44</p>
            </div>
            <div class="list-content-item">
              <a-progress :percent="80" style="width: 180px" />
            </div>
          </div>
        </a-list-item>

        <a-list-item :key="i" >
          <a-list-item-meta
            description=""
          >
            <a-avatar slot="avatar" size="large" shape="square" src="https://redcathost.oss-cn-beijing.aliyuncs.com/Image-Host/alert.png"/>
            <a slot="title">预警规则-04</a>
          </a-list-item-meta>
          <div slot="actions">
            <a>编辑</a>
          </div>
          <div slot="actions">
            <a-dropdown>
              <a-menu slot="overlay">
                <a-menu-item><a>查看</a></a-menu-item>
                <a-menu-item><a>删除</a></a-menu-item>
              </a-menu>
              <a>更多<a-icon type="down"/></a>
            </a-dropdown>
          </div>
          <div class="list-content">
            <div class="list-content-item">
              <span>Owner</span>
              <p>admin</p>
            </div>
            <div class="list-content-item">
              <span>开始时间</span>
              <p>2022-07-26 22:44</p>
            </div>
            <div class="list-content-item">
              <a-progress :percent="80" style="width: 180px" />
            </div>
          </div>
        </a-list-item>

        <a-list-item :key="i" >
          <a-list-item-meta
            description=" "
          >
            <a-avatar slot="avatar" size="large" shape="square" src="https://redcathost.oss-cn-beijing.aliyuncs.com/Image-Host/alert.png"/>
            <a slot="title">预警规则-03</a>
          </a-list-item-meta>
          <div slot="actions">
            <a>编辑</a>
          </div>
          <div slot="actions">
            <a-dropdown>
              <a-menu slot="overlay">
                <a-menu-item><a>编辑</a></a-menu-item>
                <a-menu-item><a>删除</a></a-menu-item>
              </a-menu>
              <a>更多<a-icon type="down"/></a>
            </a-dropdown>
          </div>
          <div class="list-content">
            <div class="list-content-item">
              <span>Owner</span>
              <p>admin</p>
            </div>
            <div class="list-content-item">
              <span>开始时间</span>
              <p>2022-07-26 22:44</p>
            </div>
            <div class="list-content-item">
              <a-progress :percent="40" style="width: 180px" />
            </div>
          </div>
        </a-list-item>

        <a-list-item :key="i" >
          <a-list-item-meta
            description=" "
          >
            <a-avatar slot="avatar" size="large" shape="square" src="https://redcathost.oss-cn-beijing.aliyuncs.com/Image-Host/alert.png"/>
            <a slot="title">预警规则-02</a>
          </a-list-item-meta>
          <div slot="actions">
            <a>编辑</a>
          </div>
          <div slot="actions">
            <a-dropdown>
              <a-menu slot="overlay">
                <a-menu-item><a>编辑</a></a-menu-item>
                <a-menu-item><a>删除</a></a-menu-item>
              </a-menu>
              <a>更多<a-icon type="down"/></a>
            </a-dropdown>
          </div>
          <div class="list-content">
            <div class="list-content-item">
              <span>Owner</span>
              <p>admin</p>
            </div>
            <div class="list-content-item">
              <span>开始时间</span>
              <p>2022-07-26 22:44</p>
            </div>
            <div class="list-content-item">
              <a-progress :percent="80" style="width: 180px" />
            </div>
          </div>
        </a-list-item>

        <a-list-item :key="i" >
          <a-list-item-meta
            description=" "
          >
            <a-avatar slot="avatar" size="large" shape="square" src="https://redcathost.oss-cn-beijing.aliyuncs.com/Image-Host/alert.png"/>
            <a slot="title">预警规则-01</a>
          </a-list-item-meta>
          <div slot="actions">
            <a>编辑</a>
          </div>
          <div slot="actions">
            <a-dropdown>
              <a-menu slot="overlay">
                <a-menu-item><a>编辑</a></a-menu-item>
                <a-menu-item><a>删除</a></a-menu-item>
              </a-menu>
              <a>更多<a-icon type="down"/></a>
            </a-dropdown>
          </div>
          <div class="list-content">
            <div class="list-content-item">
              <span>Owner</span>
              <p>admin</p>
            </div>
            <div class="list-content-item">
              <span>开始时间</span>
              <p>2022-07-26 22:44</p>
            </div>
            <div class="list-content-item">
              <a-progress :percent="60" style="width: 180px" />
            </div>
          </div>
        </a-list-item>
      </a-list>
    </a-card>
  </div>
</template>

<script>
// import HeadInfo from '../../components/tool/HeadInfo'
// import pUrl from '../../public/alert.png'
// import {ref } from 'vue';
const plainOptions = ['Apple', 'Pear', 'Orange'];

// const value1=  [];
// const value2=  [];
// const value3=  [];
// const value4=  [];

export default {
  data() {
    return {
      visible : true,
      value1: [],
      value2: [],
      value3: [],
      value4: [],
      options1 : [{
        label: '弱点名称',
        value: 'Apple',
        }, {
          label: 'CWE-ID',
          value: 'Pear',
        }],
        options2 : [{
        label: '设备类型',
        value: 'Apple',
        }, {
          label: '厂商名',
          value: 'Pear',
        }, {
          label: '设备名',
          value: 'Orange',
        }],
        options3 : [{
        label: '危险级别',
        value: 'Apple',
        }, {
          label: '危险评分',
          value: 'Pear',
        }, {
          label: '攻击类型',
          value: 'Orange',
        }],
        options4 : [{
        label: '代码类型',
        value: 'Apple',
        }],
        
      plainOptions: plainOptions
    }
  },
  methods: {
    showModal() {
      this.visible = true;
      console.log("hello")
    },
    handleOk(e) {
      console.log(e)
      this.visible = false;
      this.$message.success("新增预警规则",3)
    },
    handleChange1(value) {
      this.value1 = value
      console.log(`selected ${value}`);
    },
    handleChange2(value) {
      this.value2 = value
      console.log(`selected ${value}`);
    },
    handleChange3(value) {
      this.value3 = value
      console.log(`selected ${value}`);
    },
    handleChange4(value) {
      this.value4 = value
      console.log(`selected ${value}`);
    }
  },
  created() {

      this.plainOptions= plainOptions
  }

}
</script>

<style lang="less" scoped>
  .list-content-item{
    color: @text-color-second;
    display: inline-block;
    vertical-align: middle;
    font-size: 14px;
    margin-left: 40px;
    span{
      line-height: 20px;
    }
    p{
      margin: 4px 0 0;
      line-height: 22px;
    }
  }

  .modal {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.modal label {
  margin-right: 10px;
}

.a-tag {
  display: inline-block;
  width: 100px; /* 设置宽度为 100 像素 */
}
</style>

