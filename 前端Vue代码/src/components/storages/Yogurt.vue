<template>
  <div class="main_container">
    <h1><i>This is the storage page of Yogurt</i></h1>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <el-input placeholder="Please enter what you wanna search for..."  v-model="searchContent" @keypress.native.enter="search" >
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="submitAddNew">Add New</el-button>
        </el-col>
      </el-row>
      <el-table :data="YogurtList" border stripe max-height="378" :highlight-current-row="true" >
        <el-table-column type="index"></el-table-column>
        <el-table-column label="Code" prop="code"></el-table-column>
        <el-table-column label="Description" prop="description"></el-table-column>
        <el-table-column label="Price" prop="price"></el-table-column>
        <el-table-column label="ProductionDate" prop="productionDate"></el-table-column>
        <el-table-column label="ShelfLife" prop="shelfLife"></el-table-column>
        <el-table-column label="Type" prop="type"></el-table-column>
        <el-table-column label="DiluteConcentration" prop="diluteConcentration" width="180"></el-table-column>
        <el-table-column label="Quantity" prop="quantity" width="180"></el-table-column>
        <el-table-column label="Operate" width="150px">
          <template slot-scope="scope">
            <el-tooltip effect="dark" content="Edit this item" placement="top" style="margin-left: 20px" :enterable="false" >
              <el-button type="warning" icon="el-icon-edit" circle @click="submitEdit(scope)"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="Delete this item" placement="top-start" style="margin-left: 20px" :enterable="false" >
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

    <!--    确认删除的dialog-->
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

    <!--    实现修改功能的dialog-->
    <el-dialog title="Edit" :visible.sync="editDialogVisible">
      <el-form :inline="true" ref="editFormRef" :rules="editFormRules" label-position="top" :model="editFormData">
        <el-row :gutter="5">
          <el-col :span="8">
            <el-form-item label="Code" prop="code">
              <el-input autocomplete="off" placeholder = "Code..."  v-model="editFormData.code" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Description" prop="description">
              <el-input autocomplete="off" placeholder = "Description..." v-model="editFormData.description" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Price" prop="price">
              <el-input type="number" autocomplete="off" placeholder = "Price..."  v-model="editFormData.price" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="5">
          <el-col :span="8">
            <el-form-item label="ShelfLife" prop="shelfLife">
              <el-input autocomplete="off" placeholder = "ShelfLife..."  v-model="editFormData.shelfLife" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Type" prop="type">
              <el-input autocomplete="off" placeholder = "Type..." v-model="editFormData.type" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="ProductionDate" prop="productionDate">
              <el-date-picker v-model="editFormData.productionDate" @keypress.native.enter="confirmEdit" ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="5">
          <el-col :span="12">
            <el-form-item label="DiluteConcentration" prop="diluteConcentration">
              <el-input autocomplete="off" placeholder = "DiluteConcentration..." v-model="editFormData.diluteConcentration" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Quantity" prop="quantity">
              <el-input autocomplete="off" placeholder = "Quantity..." type="age"  v-model.number="editFormData.quantity" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelEdit">Cancel</el-button>
        <el-button type="primary" @click="confirmEdit">Confirm</el-button>
      </div>
    </el-dialog>


    <!--    实现新增功能的dialog-->
    <el-dialog title="Add New" :visible.sync="addNewDialogVisible">
      <el-form :inline="true" ref="addNewFormRef" :rules="addNewFromDataRules" label-position="top" :model="addNewFromData">
        <el-row :gutter="5">
          <el-col :span="8">
            <el-form-item label="Code" prop="code">
              <el-input autocomplete="off" placeholder = "Code..."  v-model="addNewFromData.code" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Description" prop="description">
              <el-input autocomplete="off" placeholder = "Description..." v-model="addNewFromData.description" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Price" prop="price">
              <el-input type="number" autocomplete="off" placeholder = "Price..."  v-model="addNewFromData.price" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
        </el-row>


        <el-row :gutter="5">
          <el-col :span="8">
            <el-form-item label="ShelfLife" prop="shelfLife">
              <el-input autocomplete="off" placeholder = "ShelfLife..."  v-model="addNewFromData.shelfLife" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Type" prop="type">
              <el-input autocomplete="off" placeholder = "Type..." v-model="addNewFromData.type" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="ProductionDate" prop="productionDate">
              <el-date-picker v-model="addNewFromData.productionDate" @keypress.native.enter="confirmAddNew" ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="5">
          <el-col :span="12">
            <el-form-item label="DiluteConcentration" prop="diluteConcentration">
              <el-input autocomplete="off" placeholder = "DiluteConcentration..." v-model="addNewFromData.diluteConcentration" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Quantity" prop="quantity">
              <el-input autocomplete="off" placeholder = "Quantity..." type="age" v-model.number="addNewFromData.quantity"  @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

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
    name: "S_Yogurt",
    data(){
      return{
        YogurtList:[],
        deleteDialogVisible:false,
        editDialogVisible:false,
        addNewDialogVisible:false,
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
          ],
          price: [
            {required: true, message: 'Please enter the price!', trigger: 'blur'}
          ],
          productionDate: [
            {required: true, message: 'Please enter the productionDate!', trigger: 'blur'}
          ],
          shelfLife: [
            {required: true, message: 'Please enter the shelfLife!', trigger: 'blur'}
          ],
          type: [
            {required: true, message: 'Please enter the type!', trigger: 'blur'}
          ],
          diluteConcentration: [
            {required: true, message: 'Please enter the diluteConcentration!', trigger: 'blur'}
          ],
          quantity: [
            {required: true, message: 'Please enter the quantity!', trigger: 'blur'}
          ]
        },
        addNewFromDataRules:{
          code: [
            {required: true, message: 'Please set the code!', trigger: 'blur'},
            {min:3, message: 'Should be MORE THAN 3 digits!',trigger: 'change'}
          ],
          description: [
            {required: true, message: 'Please enter the description!', trigger: 'blur'},
          ],
          price: [
            {required: true, message: 'Please enter the price!', trigger: 'blur'}
          ],
          productionDate: [
            {required: true, message: 'Please enter the productionDate!', trigger: 'blur'}
          ],
          shelfLife: [
            {required: true, message: 'Please enter the shelfLife!', trigger: 'blur'}
          ],
          type: [
            {required: true, message: 'Please enter the type!', trigger: 'blur'}
          ],
          diluteConcentration: [
            {required: true, message: 'Please enter the diluteConcentration!', trigger: 'blur'}
          ],
          quantity: [
            {required: true, message: 'Please enter the quantity!', trigger: 'blur'}
          ]
        },
      }
    },
    methods:{
      refreshData(){
        if(this.isSearching) this.getYogurtSearchList();
        else this.getYogurtList();
      },
      async getYogurtList(){
        const info = {
          pageSize :this.pageSize,
          currentPageIndex: this.currentPageIndex
        }
        console.log(info)
        const res = await this.$http.post("getStorage/yogurt",info);
        console.log('res',res)
        if(res.data.code === 0){
          this.YogurtList = res.data.YogurtList;
          this.total = res.data.total;
          console.log("data", res.data.YogurtList)
          this.$message.success({message:res.data.msg,duration:1000})
        }else{
          this.$message.error(res.data.msg)
        }
      },
      async getYogurtSearchList(){
        const info = {
          pageSize :this.pageSize,
          currentPageIndex: this.currentPageIndex,
          searchContent: this.searchContent
        }
        console.log('getYogurtSearchListInfo',info)
        const res = await this.$http.post("searchStorage/yogurt",info);
        console.log('getYogurtSearchList res',res)
        if(res.data.code === 0){
          this.YogurtList = res.data.YogurtList;
          this.total = res.data.total;
          console.log("data", res.data.YogurtList)
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
      submitDelete(scope){
        this.deleteDialogVisible = true;
        this.idToDelete = scope.row.id;
      },
      submitEdit(scope){
        this.editDialogVisible = true;
        this.editFormData = scope.row;
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
        const res = await this.$http.post("/deleteStorage/yogurt",info);
        if(res.data.code === 0){
          this.$message.success({message:res.data.msg,duration:1000});
          this.refreshData();
        }
        else {
          this.$message.error(res.data.msg);
        }
        this.deleteDialogVisible = false;
      },
      confirmEdit(){
        this.$refs.editFormRef.validate(async valid => {
          console.log("valid",valid)
          if(valid){
            if(await this.judgeCodeValidation(this.nowCode, this.editFormData.code)){
              const res = await this.$http.post("/editStorage/yogurt",this.editFormData);
              if(res.data.code === 0){
                this.$message.success({message:res.data.msg,duration:1000});
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
              const res = await this.$http.post("/addNewStorage/yogurt",this.addNewFromData);
              if(res.data.code === 0){
                this.$message.success({message:res.data.msg,duration:1000});
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
          this.getYogurtSearchList();
        }
        else{
          this.isSearching = false;
          this.currentPageIndex = 1;
          this.pageSize = 5;
          this.getYogurtList();
        }
      },
      async judgeCodeValidation(nowCode, targetCode) {
        const info = {nowCode: nowCode, targetCode: targetCode};
        console.log('info',info)
        const res = await this.$http.post("/publicFunction/judgeCodeValidation", info);
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