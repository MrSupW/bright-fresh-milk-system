<template>
  <div class="main_container">
    <h1><i>This is the detail page of the Order({{orderCode}})</i></h1>
    <el-card>
      <el-table :data="saleItemList" border stripe max-height="378" :highlight-current-row="true">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="Code" prop="code"></el-table-column>
        <el-table-column label="Description" prop="description"></el-table-column>
        <el-table-column label="Price" prop="price"></el-table-column>
        <el-table-column label="ProductionDate" prop="productionDate"></el-table-column>
        <el-table-column label="Quantity" prop="quantity"></el-table-column>
        <el-table-column label="Operate" width="150px">
          <template slot-scope="scope">
            <el-tooltip effect="dark" content="Edit this item" placement="top" style="margin-left: 20px" :enterable="false" >
              <el-button type="warning" icon="el-icon-edit" circle @click="submitEdit(scope)" :disabled="is_history" ></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="Delete this item" placement="top-start" style="margin-left: 20px" :enterable="false" >
              <el-button  icon="el-icon-delete" type="danger" circle @click="submitDelete(scope)" :disabled="is_history"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-card style="margin-top: 10px;">
      <el-row :gutter="20">
        <el-col :span="12" style="font-size: 20px!important;">
          Total Price: <span style="color: goldenrod">{{totalPrice}}</span>
        </el-col>
        <el-col :span="6" v-show="!this.is_history">
          <el-button type="primary" @click="submitAddNew">Add SaleItem</el-button>
        </el-col>
        <el-col :span="6" v-show="!this.is_history">
          <el-button type="primary" @click="submitSubmit">Submit the Order</el-button>
        </el-col>
      </el-row>
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

    <!--    确认提交订单的dialog-->
    <el-dialog
            title="Confirm"
            :visible.sync="submitDialogVisible"
            width="30%">
      <span>Are you sure to submit this order?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="submitDialogVisible = false">cancel</el-button>
        <el-button type="primary" @click="confirmSubmit">sure</el-button>
      </span>
    </el-dialog>

    <!--    实现修改功能的dialog-->
    <el-dialog title="Edit" :visible.sync="editDialogVisible">
      <el-form :inline="true" ref="editFormRef" :rules="editFormRules" label-position="top" :model="editFormData">
        <el-row :gutter="5">
          <el-col :span="12">
            <el-form-item label="Code" prop="code">
              <el-input autocomplete="off" placeholder = "Code..." disabled  v-model="editFormData.code" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Description" prop="description">
              <el-input autocomplete="off" placeholder = "Description..." disabled v-model="editFormData.description" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="5">
          <el-col :span="12">
            <el-form-item label="Price" prop="price">
              <el-input type="number" autocomplete="off" placeholder = "Price..." disabled  v-model="editFormData.price" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ProductionDate" prop="productionDate">
              <el-date-picker v-model="editFormData.productionDate" disabled @keypress.native.enter="confirmEdit" ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="5">
          <el-col :span="12">
            <el-form-item label="ShelfLife" prop="shelfLife">
              <el-input autocomplete="off" placeholder = "ShelfLife..." disabled  v-model="editFormData.shelfLife" @keypress.native.enter="confirmEdit"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Quantity" prop="quantity">
              <el-input autocomplete="off" placeholder = "Quantity..." v-model="editFormData.quantity" @keypress.native.enter="confirmEdit"></el-input>
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
      <el-form :inline="true" ref="addNewFormRef" :rules="addNewFormDataRules" label-position="top" :model="addNewFormData">
        <el-row :gutter="5">
          <el-col :span="12">
            <el-form-item label="Code" prop="code">
              <el-input autocomplete="off" placeholder = "Code..."  v-model="addNewFormData.code" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Description" prop="description">
              <el-input autocomplete="off" placeholder = "Description..." disabled v-model="addNewFormData.description" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="5">
          <el-col :span="12">
            <el-form-item label="Price" prop="price">
              <el-input type="number" autocomplete="off" disabled placeholder = "Price..."  v-model="addNewFormData.price" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ProductionDate" prop="productionDate">
              <el-date-picker v-model="addNewFormData.productionDate" disabled @keypress.native.enter="confirmAddNew" ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="5">
          <el-col :span="12">
            <el-form-item label="ShelfLife" prop="shelfLife">
              <el-input autocomplete="off" placeholder = "ShelfLife..." disabled  v-model="addNewFormData.shelfLife" @keypress.native.enter="confirmAddNew"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Quantity" prop="quantity">
              <el-input autocomplete="off" placeholder = "Quantity..." type="age" v-model.number="addNewFormData.quantity" @keypress.native.enter="confirmAddNew"></el-input>
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
    name: "CurrentOrderDetail",
    data(){
      return{
        orderId:'',  // Order's id  // 指定值用于调试
        orderCode:'',
        is_history:false,
        saleItemList:[],
        deleteDialogVisible:false,
        editDialogVisible:false,
        addNewDialogVisible:false,
        submitDialogVisible:false,
        // pageSize:5,
        // currentPageIndex:1,
        total:0,
        idToDelete:0,
        editFormData:{},
        nowCode:'',
        addNewFormData:{},
        searchContent:'',
        isSearching:false,
        editFormRules: {
          code: [
            {required: true, message: 'Please enter the code!', trigger: 'blur'},
            {min:3 , message: 'Should be MORE THAN 3 digits!' , trigger: 'change'}
          ],
          quantity:[
            {required:true, message:'Please enter the quantity', trigger:'blur'}
          ]
        },
        addNewFormDataRules:{
          code: [
            {required: true, message: 'Please enter the code!', trigger: 'blur'},
            {min:3 , message: 'Should be MORE THAN 3 digits!' , trigger: 'change'}
          ],
          quantity:[
            {required:true, message:'Please enter the quantity', trigger:'blur'}
          ]
        },
      }
    },
    methods:{
      refreshData(){
        if(this.isSearching) this.getSaleItemSearchList();
        else this.getSaleItemList();
      },
      async getSaleItemList(){
        const info = {
          orderId: this.orderId
        }
        console.log(info)
        const res = await this.$http.post("getSaleItems/saleItem",info);
        console.log('res',res)
        if(res.data.code === 0){
          this.saleItemList = res.data.saleItemList;
          this.total = res.data.total;
          console.log("data", res.data.saleItemList)
          this.$message.success({message:res.data.msg,duration:1000})
        }else{
          this.$message.error(res.data.msg)
        }
      },
      async getSaleItemSearchList(){
        const info = {
          pageSize :this.pageSize,
          currentPageIndex: this.currentPageIndex,
          searchContent: this.searchContent
        }
        console.log('getSaleItemSearchListInfo',info)
        const res = await this.$http.post("searchSaleItems/saleItem",info);
        console.log(' getSaleItemSearchList res',res)
        if(res.data.code === 0){
          this.saleItemList = res.data.saleItemList;
          this.total = res.data.total;
          console.log("data", res.data.saleItemList)
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
      submitSubmit(){
        this.submitDialogVisible = true;
      },
      cancelEdit(){
        this.editDialogVisible = false;
        this.refreshData();
      },
      async confirmDelete(){
        const info = {orderId:this.orderId,saleItemId:this.idToDelete}
        const res = await this.$http.post("/deleteSaleItem/saleItem",info);
        if(res.data.code === 0){
          this.$message.success({message:res.data.msg,duration:1000})
          this.refreshData();
        }
        else {
          this.$message.error(res.data.msg);
        }
        this.deleteDialogVisible = false;
      },
      async confirmSubmit(){
        const info = {orderId:this.orderId}
        const res = await this.$http.post("/submitOrder/currentOrder",info);
        if(res.data.code === 0){
          this.$message.success({message:res.data.msg,duration:1000})
          this.refreshData();
        }
        else {
          this.$message.error(res.data.msg);
        }
        this.submitDialogVisible = false;
      },
      confirmEdit(){
        this.$refs.editFormRef.validate(async valid => {
          console.log("valid",valid)
          if(valid){
            const info = {saleItemId:this.editFormData.id,quantity:this.editFormData.quantity}
            const res = await this.$http.post("/editSaleItem/saleItem",info);
            if(res.data.code === 0){
              this.$message.success({message:res.data.msg,duration:1000})
              this.refreshData();
            }
            else{
              this.$message.error(res.data.msg);
            }
            this.editDialogVisible = false;
          }else{
            this.$message.error("Please Check Your Input!");
          }
        })
      },
      confirmAddNew(){
        this.$refs.addNewFormRef.validate(async valid => {
          if(valid){
            if(await this.judgeCodeValidation(null, this.addNewFormData.code)){
              const info = {orderId:this.orderId,code:this.addNewFormData.code,quantity:this.addNewFormData.quantity}
              const res = await this.$http.post("/addNewSaleItem/saleItem",info);
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
          this.getSaleItemSearchList();
        }
        else{
          this.isSearching = false;
          this.currentPageIndex = 1;
          this.pageSize = 5;
          this.getSaleItemList();
        }
      },
      async judgeCodeValidation(nowCode, targetCode) {
        const info = {nowCode: nowCode, targetCode: targetCode};
        console.log('info',info)
        const res = await this.$http.post("/publicFunction/judgeCodeValidation", info);
        if(res.data.code === 0){
          this.$message.error("The product doesn't exist!");
          return false;
        }
        else{
          return true;
        }
      }
    },
    computed:{
      totalPrice(){
        let total_price = 0.0;
        for (const saleItem of this.saleItemList){
          total_price += saleItem.price * saleItem.quantity
        }
        return total_price.toFixed(2) + '$';
      }
    },
    beforeMount() {
      if(!this.$route.params.id){
        this.$router.push('/order/currentOrders');
      }
      else{
        this.orderId = this.$route.params.id;
        this.orderCode = this.$route.params.code;
        this.is_history = this.$route.params.is_history;
        console.log("beforeMount")
        this.refreshData();
      }
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