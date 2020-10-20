<template>
  <div class="main_container">
    <h1><i>This is the page of CurrentOrders</i></h1>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <el-input placeholder="Please enter what you wanna search for..." @keypress.native.enter="search"  v-model="searchContent">
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="submitAddNew">Add New</el-button>
        </el-col>
      </el-row>
      <el-table :data="currentOrderList" border stripe max-height="500" :highlight-current-row="true">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="Code" prop="code"></el-table-column>
        <el-table-column label="Description" prop="description"></el-table-column>
        <el-table-column label="CreateTime" prop="createTime"></el-table-column>
        <el-table-column label="UpdateTime" prop="updateTime"></el-table-column>
        <el-table-column label="Operate" width="250px">
          <template slot-scope="scope">
            <el-tooltip effect="dark" content="Edit this item" placement="top" :enterable="false" >
              <el-button type="warning" icon="el-icon-edit" circle @click="submitEdit(scope)"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="See the details" placement="top" style="margin-left: 20px" :enterable="false" >
              <el-button type="primary"  round plain @click="gotoDetail(scope)">Detail</el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="Delete this item" placement="top-start" :enterable="false" >
              <el-button  icon="el-icon-delete" type="danger" circle @click="submitDelete(scope)"></el-button>
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

    <!--    Order确认删除的dialog-->
    <el-dialog
            title="Confirm"
            :visible.sync="deleteDialogVisible"
            width="30%">
      <span>Are you sure to delete this item?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false">cancel</el-button>
        <el-button type="primary" @click="confirmDelete">sure</el-button>
      </span>
    </el-dialog>
    <!--    Order实现修改功能的dialog-->
    <el-dialog title="Edit" :visible.sync="editDialogVisible">
      <el-form ref="editFormRef" :rules="editFormRules" label-position="left" label-width="150px" :model="editFormData">
        <el-form-item label="Code" prop="code">
          <el-input autocomplete="off" placeholder = "Code..."  v-model="editFormData.code" @keypress.native.enter="confirmEdit"></el-input>
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input autocomplete="off" placeholder = "Description..." v-model="editFormData.description" @keypress.native.enter="confirmEdit"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelEdit">Cancel</el-button>
        <el-button type="primary" @click="confirmEdit">Confirm</el-button>
      </div>
    </el-dialog>
    <!--    Order实现新增功能的dialog-->
    <el-dialog title="Add New" :visible.sync="addNewDialogVisible">
      <el-form :inline="true" ref="addNewFormRef" :model="addNewFromData" :rules="addNewFromDataRules" label-position="left"  label-width="150px">
        <el-form-item label="Code" prop="code">
          <el-input autocomplete="off" placeholder = "Code..."  v-model="addNewFromData.code" @keypress.native.enter="confirmAddNew"></el-input>
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input autocomplete="off" placeholder = "Description..." v-model="addNewFromData.description" @keypress.native.enter="confirmAddNew"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addNewDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="confirmAddNew">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "currentOrder",
    data(){
      return{
        currentOrderList:[],
        editDialogVisible:false,
        addNewDialogVisible:false,
        deleteDialogVisible:false,
        pageSize:5,
        currentPageIndex:1,
        total:0,
        idToDelete:0,
        editFormData:{},
        nowCode:'',
        addNewFromData:{},
        searchContent:'',
        isSearching:false,
        editFormRules: {
          code: [
            {required: true, message: 'Please set the code!', trigger: 'blur'},
            {min:3, message: 'Should be MORE THAN 3 digits!',trigger: 'change'}
          ],
          description: [
            {required: true, message: 'Please enter the description!', trigger: 'blur'},
          ]
        },
        addNewFromDataRules:{
          code: [
            {required: true, message: 'Please enter the code!', trigger: 'blur'},
            {min:3 , message: 'Should be MORE THAN 3 digits!' , trigger: 'change'}
          ],
          description: [
            {required: true, message: 'Please enter the description!', trigger: 'blur'},
          ]
        },
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
        const res = await this.$http.post("getOrders/currentOrder",info);
        console.log('res',res)
        if(res.data.code === 0){
          this.currentOrderList = res.data.currentOrderList;
          this.total = res.data.total;
          console.log("data", res.data.currentOrderList)
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
        const res = await this.$http.post("searchOrders/currentOrder",info);
        console.log(' getCurrentOrderSearchList res',res)
        if(res.data.code === 0){
          this.currentOrderList = res.data.currentOrderList;
          this.total = res.data.total;
          console.log("data", res.data.currentOrderList)
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
            is_history:false
          }
        });
      },
      submitDelete(scope){
        this.deleteDialogVisible = true;
        this.idToDelete = scope.row.id;
      },
      submitEdit(scope){
        this.editDialogVisible = true;
        this.editFormData = scope.row;
        this.editFormData = {id:scope.row.id,code:scope.row.code,description:scope.row.description};
        this.nowCode = scope.row.code;
      },
      submitAddNew(){
        this.addNewDialogVisible = true;
      },
      cancelEdit(){
        this.editDialogVisible = false;
        this.refreshData();
      },
      async confirmDelete(){
        const info = {id:this.idToDelete}
        const res = await this.$http.post("/deleteOrder/currentOrder",info);
        if(res.data.code === 0){
          this.$message.success({message:res.data.msg,duration:1000})
          this.refreshData();
        }
        else {
          this.$message.error(res.data.msg);
        }
        this.deleteDialogVisible = false;
      },
      confirmEdit(){
        this.$refs.editFormRef.validate(async valid => {
          if(valid){
            if(await this.judgeCodeValidation(this.nowCode, this.editFormData.code)){
              const res = await this.$http.post("/editOrder/currentOrder",this.editFormData);
              if(res.data.code === 0){
                this.$message.success({message:res.data.msg,duration:1000})
                this.refreshData();
              }
              else{
                this.$message.error(res.data.msg);
              }
              this.editDialogVisible = false;
            }
          }else{
            this.$message.error("Please Check Your Input!");
          }
        })
      },
      confirmAddNew(){
        this.$refs.addNewFormRef.validate(async valid => {
          if(valid){
            if(await this.judgeCodeValidation(null, this.addNewFromData.code)){
              console.log('addNewFromDate',this.addNewFromData)
              const res = await this.$http.post("/addNewOrder/currentOrder",this.addNewFromData);
              if(res.data.code === 0){
                this.$message.success({message:res.data.msg,duration:1000})
                this.refreshData();
              }
              else{
                this.$message.error(res.data.msg);
              }
              this.addNewDialogVisible = false;
            }
          }else {
            this.$message.error("Please Check Your Input!");
          }
        })
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
      },
      async judgeCodeValidation(nowCode, targetCode) {
        const info = {nowCode: nowCode, targetCode: targetCode};
        console.log('info',info)
        const res = await this.$http.post("/judgeCodeValidation/currentOrder", info);
        if(res.data.code === 0){
          return true;
        }
        else{
          this.$message.error(res.data.msg);
          return false;
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