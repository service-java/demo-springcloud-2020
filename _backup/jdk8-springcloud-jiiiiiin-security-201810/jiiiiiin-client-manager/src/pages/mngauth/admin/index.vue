<template>
  <d2-container>
    <d2-crud
            :columns="columns"
            :data="data"
            :options="options">
      <el-button slot="headerButton" type="primary" size="small" @click="onClickAdd">新增</el-button>
    </d2-crud>
  </d2-container>
</template>

<script>
export default {
  name: 'resource',
  data() {
    return {
      columns: [
        {
          title: '日期',
          key: 'date',
          width: '180'
        },
        {
          title: '姓名',
          key: 'name',
          width: '180'
        },
        {
          title: '地址',
          key: 'address'
        }
      ],
      data: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }
      ],
      options: {
        border: true
      }
    };
  },
  methods: {
    onClickAdd() {
      alert('add')
    }
  },
  created() {
    // this.$vp.ajaxGet('sys/resource')
    //   .then(res => {
    //     console.log(res)
    //   })
  }
};
</script>

<style lang="scss" scoped>
  @import '~@/assets/style/public.scss';
  .icon-card {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    height: 50px;
    &:hover {
      .icon {
        transform: scale(1.1);
      }
      .icon-title {
        color: $color-text-main;
      }
    }
  }
  .icon {
    height: 30px;
    width: 30px;
    transition: all .3s;
    cursor: pointer;
  }
  .icon-title {
    font-size: 12px;
    margin-top: 3px;
    color: $color-text-normal;
  }
</style>
