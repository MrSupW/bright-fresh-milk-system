<template>
  <div class="main_container">
    <h1><i>This is the page of HistoryOrders</i></h1>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <el-input placeholder="Please enter what you wanna search for..." @keypress.native.enter="search"  v-model="searchContent">
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
        </el-col>
      </el-row>
      <el-table :data="historyOrderList" border stripe max-height="500" :highlight-current-row="true">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="Code" prop="code"></el-table-column>
        <el-table-column label="Description" prop="description"></el-table-column>
        <el-table-column label="CreateTime" prop="createTime"></el-table-column>
        <el-table-column label="UpdateTime" prop="updateTime"></el-table-column>
        <el-table-column label="Operate" width="250px">
          <template slot-scope="scope">
            <el-tooltip effect="dark" content="See the details" placement="top" style="margin-left: 20px" :enterable="false" >
              <el-button type="primary"  round plain @click="gotoDetail(scope)">Detail</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPageIndex"
              :page-sizes="[2, 5, 10, 20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
      </el-pagination>
    </el-card>

  </div>
</template>

<script>
  export default {
    name: "historyOrder",
    data(){
      return{
        historyOrderList:[],
        editDialogVisible:false,
        addNewDialogVisible:false,
        deleteDialogVisible:false,
        pageSize:5,
        currentPageIndex:1,
        total:0,
        searchContent:'',
        isSearching:false,
      }
    },
    methods:{
      refreshData(){
        if(this.isSearching) this.getCurrentOrderSearchList();
        else this.getCurrentOrderList();
      },
      async getCurrentOrderList(){
        const info = {
          pageSize :this.pageSize,
          currentPageIndex: this.currentPageIndex
        }
        console.log(info)
        const res = await this.$http.post("getOrders/historyOrder",info);
        console.log('res',res)
        if(res.data.code === 0){
          this.historyOrderList = res.data.historyOrderList;
          this.total = res.data.total;
          console.log("data", res.data.historyOrderList)
          this.$message.success({message:res.data.msg,duration:1000})
        }else{
          this.$message.error(res.data.msg)
        }
      },
      async getCurrentOrderSearchList(){
        const info = {
          pageSize :this.pageSize,
          currentPageIndex: this.currentPageIndex,
          searchContent: this.searchContent
        }
        console.log('getCurrentOrderSearchListInfo',info)
        const res = await this.$http.post("searchOrders/historyOrder",info);
        console.log(' getCurrentOrderSearchList res',res)
        if(res.data.code === 0){
          this.historyOrderList = res.data.historyOrderList;
          this.total = res.data.total;
          console.log("data", res.data.historyOrderList)
          this.$message.success({message:res.data.msg,duration:1000})
        }else{
          this.$message.error(res.data.msg)
        }
      },
      handleSizeChange(newSize){
        this.pageSize = newSize;
        this.refreshData();
      },
      handleCurrentChange(newIndex){
        this.currentPageIndex = newIndex;
        this.refreshData();
      },
      gotoDetail(scope){
        // console.log(scope)
        this.$router.push({
          name:'CurrentOrderDetail',
          params:{
            id: scope.row.id,
            code:scope.row.code,
            is_history:true,
          }
        });
      },
      search(){
        if(this.searchContent !== ''){
          this.isSearching = true;
          this.currentPageIndex = 1;
          this.pageSize = 5;
          this.getCurrentOrderSearchList();
        }
        else{
          this.isSearching = false;
          this.currentPageIndex = 1;
          this.pageSize = 5;
          this.getCurrentOrderList();
        }
      }
    },
    created() {
      this.refreshData();
    }
  }
</script>

<style scoped lang="less">
  .main_container{
    position: relative;
    bottom: 30px;
    width: 100%;
    text-align: center;
    h1{
      font-size: 30px;
      color: green;
    }
  }

  .el-table{
    margin-top: 20px;
  }


</style>