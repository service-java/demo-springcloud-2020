<template>
    <d2-container>
        <zk-table
                ref="table"
                sum-text="sum"
                index-text="#"
                :data="data"
                empty-text="暂无数据"
                :columns="columns"
                :stripe="true"
                :border="true"
                :show-index="false"
                :tree-type="true"
                :is-fold="true"
                :expand-type="false"
                :selection-type="false">
            <template slot="icon" slot-scope="scope">
                <d2-icon v-if="scope.row.icon" :name="scope.row.icon"/>
                <span v-else>空</span>
            </template>
            <template slot="type" slot-scope="scope">
                <el-tag size="mini" type="[scope.row.type ? '': 'success']"> {{ scope.row.type ? '菜单' : '按钮' }}</el-tag>
            </template>
            <template slot="status" slot-scope="scope">
                <el-tag size="mini" type="[scope.row.status ? '': 'success']"> {{ scope.row.status ? '启用' : '停用' }}</el-tag>
            </template>
            <template slot="option" slot-scope="scope">
                <el-button type="primary" plain size="mini" @click="onClickAdd(scope.row)">新增</el-button>
                <el-button plain size="mini" @click="onClickUpdate(scope.row)">修改</el-button>
                <!--根节点才可以删除-->
                <el-button type="danger" plain size="mini" v-if="!scope.row.children" @click="onClickDel(scope.row)">删除</el-button>
            </template>
        </zk-table>

        <el-dialog
                :title="formMode === 'edit' ? '编辑' : '新增'"
                :visible.sync="dialogFormVisible"
                width="70%"
                :modal="true">
            <el-form :model="form">
                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-radio v-model="form.type" label="MENU">菜单</el-radio>
                    <el-radio v-model="form.type" label="BTN">按钮</el-radio>
                </el-form-item>
                <el-form-item label="名称" :label-width="formLabelWidth" :required="true">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="上级菜单" :label-width="formLabelWidth">
                    <el-input v-model="form.pname" autocomplete="off" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="路由" :label-width="formLabelWidth">
                    <el-input v-model="form.path" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="接口" :label-width="formLabelWidth">
                    <el-input v-model="form.url" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="接口类型" :label-width="formLabelWidth">
                    <el-input v-model="form.method" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="状态" :label-width="formLabelWidth">
                    <!--！注意label需要类型匹配-->
                    <el-radio v-model="form.status" :label="1">启用</el-radio>
                    <el-radio v-model="form.status" :label="0">停用</el-radio>
                </el-form-item>
                <el-form-item label="图标" :label-width="formLabelWidth">
                    <el-select v-model="form.icon" placeholder="请选择">
                        <el-option
                                v-for="item in iconOptions"
                                :key="item.key"
                                :value="item.value"
                                :disabled="item.disabled">
                            <div v-if="item.disabled">
                                <span style="float: left">{{ item.label }}</span>
                            </div>
                            <div v-else>
                                <d2-icon :name="item.value" style="width: 30px"/>&nbsp;&nbsp;&nbsp;&nbsp;{{ item.value }}
                                <!--<span></span>-->
                            </div>
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
            </div>
        </el-dialog>
    </d2-container>
</template>

<script>
import icon from './data/index'

export default {
  name: 'resource',
  data() {
    return {
      icon,
      dialogFormVisible: false,
      formLabelWidth: '80px',
      formMode: 'add',
      form: {
        channel: '',
        children: [],
        icon: '',
        id: -1,
        levels: 1,
        method: 'POST',
        name: '',
        num: 1,
        path: '',
        pid: 0,
        pname: '',
        status: 1,
        type: 'MENU',
        url: ''
      },
      data: [],
      columns: [
        {
          label: '名称',
          prop: 'name',
          minWidth: '100px'
        },
        {
          label: '图标',
          prop: 'icon',
          type: 'template',
          template: 'icon',
          width: '50px',
          align: 'center'
        },
        {
          label: '类型',
          prop: 'type',
          type: 'template',
          template: 'type',
          width: '70px',
          align: 'center'
        },
        {
          label: '排序',
          prop: 'num',
          width: '50px',
          align: 'center'
        },
        {
          label: '路由',
          prop: 'path'
        },
        {
          label: '接口',
          prop: 'url'
        },
        {
          label: '接口类型',
          prop: 'method',
          width: '75px',
          align: 'center'
        },
        {
          label: '状态',
          prop: 'status',
          type: 'template',
          template: 'status',
          width: '70px',
          align: 'center'
        },
        {
          label: '操作',
          width: '215px',
          type: 'template',
          template: 'option'
        }
      ]
    };
  },
  computed: {
    iconOptions () {
      const res = []
      let idx = 0
      this.icon.forEach(item => {
        res.push({
          label: item.title,
          disabled: true
        })
        item.icon.forEach((icon) => {
          idx += 1
          res.push({
            key: idx,
            value: icon
          })
        })
      })
      return res
    }
  },
  methods: {
    _preHandlerAddOrUpdate(mode, pMenu) {
      this.form.pid = pMenu.id
      this.form.pname = pMenu.name
      this.formMode = mode
      console.log('AddOrUpdate form', this.form)
      this.dialogFormVisible = true
    },
    onClickAdd(pMenu) {
      this._preHandlerAddOrUpdate('add', pMenu)
    },
    onClickUpdate(pMenu) {
      this._preHandlerAddOrUpdate('edit', pMenu)
    },
    onClickDel() {}
  },
  created() {
    this.$vp.ajaxGet('resource')
      .then(res => {
        this.data = res
      })
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
