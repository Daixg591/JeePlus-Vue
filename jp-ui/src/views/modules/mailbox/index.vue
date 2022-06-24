<template>
    <div class="jp-common-layout page">
      <div class="jp-common-layout-left">
        <div class="jp-common-title"> 
            <el-button size="small" style="width:100%" type="primary" @click="sendLetter">写信</el-button>
        </div>
        <div class="jp-common-el-tree-scrollbar el-scrollbar">
          <div class="el-scrollbar__wrap">
              <div class="el-scrollbar__view">
                <el-menu @select="select" style="border:0">
                    <el-menu-item-group>
                      <template slot="title">我的信箱</template>
                      <el-menu-item index="1">收件箱 ({{' '+ noReadCount}}/{{mailBoxCount + ' ' }})</el-menu-item>
                      <el-menu-item index="2">已发送 ({{' '+ mailComposeCount +' '}})</el-menu-item>
                      <el-menu-item index="3">草稿箱 ({{' '+ mailDraftCount+' ' }})</el-menu-item>
                      <el-menu-item index="4">已删除 ({{' '+ mailTrashCount+' ' }})</el-menu-item>
                    </el-menu-item-group>
                </el-menu>
            </div>
          </div>
        </div>
      </div>
      <div class="jp-common-layout-center jp-flex-main">
        <el-form size="small" :inline="true"  class="query-form"  ref="searchForm" :model="searchForm" @keyup.enter.native="refreshList()" @submit.native.prevent>
          <el-form-item>
              <el-dropdown @command="changeReadStatus">
                <el-button size="small" type="primary">
                  过滤<i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="0">未读</el-dropdown-item>
                  <el-dropdown-item command="1">已读</el-dropdown-item>
                  <el-dropdown-item command="">全部</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
          </el-form-item>
          <el-form-item>
              <el-button size="small" type="primary" @click="refreshList">
                <i class="el-icon-refresh"></i>
              </el-button>
              <el-button  type="danger" size="small" icon="el-icon-delete" @click="del()" plain>删除
              </el-button>
          </el-form-item>
          <el-form-item>
          </el-form-item>
          <el-form-item class="pull-right">
            <el-input v-model="searchForm.mailDTO.title"  placeholder="请输入标题">
            <el-button slot="append" icon="el-icon-search" @click="refreshList"></el-button>
          </el-input>
          </el-form-item>
        </el-form>

    <div class="bg-white top">
      <el-table
          :data="dataList"
          v-loading="loading"
          size = "small"
          height="calc(100% - 50px)"
          @selection-change="selectionChangeHandle"
          @sort-change="sortChangeHandle"
          class="table">
        <el-table-column
          type="selection"
          header-align="center"
          align="center"
          width="50">
        </el-table-column>
          <el-table-column prop="status"  v-if="index === '2' || index === '3'|| index === '4'" label="状态" >
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.status === '1'">已发送</el-tag>
              <el-tag type="danger" v-if="scope.row.status === '0'">草稿</el-tag>
              <el-tag type="danger" v-if="scope.row.status === '2'">未读</el-tag>
              <el-tag type="success" v-if="scope.row.status === '3'">已读</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="readStatus" v-if="index === '1'" label="状态" >
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.readStatus === '1'">已读</el-tag>
              <el-tag type="danger" v-if="scope.row.readStatus === '0'">未读</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sender.name" v-if="index === '1' || index === '4'" label="发件人" >
        </el-table-column>
        <el-table-column prop="receiverNames"  v-if="index !== '1'|| index === '4'" label="收件人">
        </el-table-column>
        <el-table-column prop="mailDTO.title" show-overflow-tooltip label="标题">
        </el-table-column>
         <el-table-column show-overflow-tooltip prop="sendTime" label="时间" >
        </el-table-column>
         <el-table-column
            header-align="center"
            align="center"
            width="210"
            label="操作">
            <template slot-scope="scope">
              <el-button  type="text"  size="mini"  icon="el-icon-view" 
                        @click="view(scope.row.id)">查阅
              </el-button>
              <el-button v-if="index === '1'" type="text" icon="el-icon-edit" size="mini" @click="reply(scope.row)">
                回复
              </el-button>
              <el-button type="text" icon="el-icon-delete"  size="mini"  @click="del(scope.row.id)">
                删除
              </el-button>
            </template>
          </el-table-column>
      </el-table>
       <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageNo"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="total"
          background
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
    </div>
  <SendEmail @refreshList="refreshList" ref="sendEmail"></SendEmail>
  <SentMailDetail @refreshList="refreshList" ref="sentMailDetail"></SentMailDetail>
  <ReceivedMailDetail @refreshList="refreshList" ref="receivedMailDetail"></ReceivedMailDetail>
  <TrashMailDetail @refreshList="refreshList" ref="trashMailDetail"></TrashMailDetail>
      </div>
    </div>
</template>
<script>
  import SendEmail from './SendEmail'
  import ReceivedMailDetail from './ReceivedMailDetail'
  import SentMailDetail from './SentMailDetail'
  import TrashMailDetail from './TrashMailDetail'
  import MailBoxService from '@/api/mail/MailBoxService'
  export default {
    data () {
      return {
        searchForm: {
          mailDTO: {
            title: ''
          },
          readStatus: '' // 0未读 1已读 2全部
        },
        dataList: [],
        pageNo: 1,
        pageSize: 10,
        total: 0,
        index: '1', // 1收件箱 2发件箱 3草稿箱
        dataListSelections: [],
        dataUrl: '/mail/box/list',
        delUrl: '/mail/box/delete',
        loading: false,
        noReadCount: 0,
        mailBoxCount: 0,
        mailComposeCount: 0,
        mailDraftCount: 0,
        mailTrashCount: 0

      }
    },
    components: {
      SendEmail,
      SentMailDetail,
      ReceivedMailDetail,
      TrashMailDetail
    },
    mailBoxService: null,
    created () {
      this.mailBoxService = new MailBoxService()
    },
    activated () {
      this.refreshList()
    },
    methods: {
      // 查询状态
      queryStatus () {
        this.mailBoxService.queryStatus().then(({data}) => {
          this.noReadCount = data.noReadCount
          this.mailBoxCount = data.mailBoxCount
          this.mailComposeCount = data.mailComposeCount
          this.mailDraftCount = data.mailDraftCount
          this.mailTrashCount = data.mailTrashCount
        })
      },
      // 获取数据列表
      refreshList () {
        this.loading = true
        this.queryStatus()
        this.$http({
          url: this.dataUrl,
          method: 'get',
          params: {
            'current': this.pageNo,
            'size': this.pageSize,
            ...this.searchForm
          }
        }).then(({data}) => {
          this.dataList = data.records
          this.total = data.total
          this.pageNo = data.current
          this.loading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageNo = 1
        this.refreshList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageNo = val
        this.refreshList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      select (val) {
        this.index = val
        if (val === '1') {
          this.dataUrl = '/mail/box/list'
          this.delUrl = '/mail/box/delete'
        } else if (val === '2') {
          this.dataUrl = '/mail/compose/list?status=1'
          this.delUrl = '/mail/compose/delete'
        } else if (val === '3') {
          this.dataUrl = '/mail/compose/list?status=0'
          this.delUrl = '/mail/compose/delete'
        } else if (val === '4') {
          this.dataUrl = '/mail/trash/list'
          this.delUrl = '/mail/trash/delete'
        }
        this.refreshList()
      },

    // 排序
      sortChangeHandle (obj) {
        if (obj.order === 'ascending') {
          this.orderBy = obj.prop + ' asc'
        } else if (obj.order === 'descending') {
          this.orderBy = obj.prop + ' desc'
        } else {
          this.orderBy = ''
        }
        this.refreshList()
      },
      changeReadStatus (status) {
        this.searchForm.readStatus = status
        this.refreshList()
      },
      // 发信
      sendLetter () {
        this.$refs.sendEmail.init('add', '')
      },
      // 查看
      view (id) {
        if (this.index === '1') {
          this.$refs.receivedMailDetail.init(id)
        } else if (this.index === '2') {
          this.$refs.sentMailDetail.init(id)
        } else if (this.index === '3') {
          this.$refs.sendEmail.init('edit', id)
        } else if (this.index === '4') {
          this.$refs.trashMailDetail.init(id)
        }
      },
      reply (row) {
        let content = `<br/><br/><br/>------------------ 原始邮件 ------------------<br/>发件人:${row.sender.name}<br/>` +
            `发送时间:${row.sendTime}<br/>` +
          `收件人:${row.receiverNames}<br/>` +
            `主题:${row.mailDTO.title}<br/>` +
          this.$utils.unescapeHTML(row.mailDTO.content)
        let obj = {
          sender: {
            id: row.sender.id
          },
          title: '回复:' + row.mailDTO.title,
          content: content
        }
        this.$refs.sendEmail.init('reply', obj)
      },
      // 删除
      del (id) {
        let ids = id || this.dataListSelections.map(item => {
          return item.id
        }).join(',')
        this.$confirm(`确定删除所选项吗?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http.delete(this.delUrl, {params: {
            ids: ids
          }}).then(({data}) => {
            this.$message.success({dangerouslyUseHTMLString: true, message: data})
            this.refreshList()
          })
        })
      },
      resetSearch () {
        this.$refs.searchForm.resetFields()
        this.refreshList()
      }
    }
  }
</script>

<style>
  .el-header {
    color: #333;
    line-height: 60px;
    height: 30px;
  }
  
  .el-aside {
    color: #333;
    border-right: 1px solid rgb(238, 238, 238);
  }
</style>
