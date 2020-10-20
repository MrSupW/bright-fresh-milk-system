<template>
  <div class="login_container">
    <div class="login_box">
<!--      头像区域-->
      <div class="avatar_box">
        <img src="../assets/images/avatar.jpg" alt="">
      </div>
<!--      登录表单区域-->
      <el-form ref="loginFormRef" :model="loginForm" :rules="rules" label-width="0px" class="login_form">
        <el-form-item prop="username">
          <el-input placeholder="Enter your username..." v-model="loginForm.username" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="Enter your password..." type="password" show-password v-model="loginForm.password" prefix-icon="el-icon-s-cooperation"></el-input>
        </el-form-item>
<!--        按钮区域-->
        <el-form-item class="btns">
          <el-button type="primary" @click="login">Login</el-button>
          <el-button type="info" @click="resetForm">Reset</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data(){
      return{
        loginForm:{
          username:'',
          password:''
        },
        rules:{
          username: [
            {required:true , message:'Please Enter Your Username!',trigger:'blur'},
            {min:3 , max:10, message: 'The Length of Username should be IN RANGE 3-10!',trigger: 'change'},
          ],
          password:[
            {required:true , message:'Please Enter Your Password!',trigger:'blur'},
            {min:6 ,max:15, message: 'The Length of Password should be IN RANGE 6-15!',trigger: 'change'}
          ]
        }
      }
    },
    methods:{
      resetForm(){
        this.$refs.loginFormRef.resetFields();
      },
      login(){
        this.$refs.loginFormRef.validate(async valid=>{
          if(valid){
            const res = await this.$http.post("login",this.loginForm);
            console.log(res.data)
            if(res.data.code === 0){
              // 登录成功
              this.$message.success(res.data.msg)
              window.sessionStorage.setItem("token",res.data.token);
              await this.$router.push("/home");
            }else if(res.data.code === 1){
              this.$message.error(res.data.msg);
            }else if(res.data.code === -1){
              this.$message.error(res.data.msg);
            }
          } else {
            this.$message.error("Please check your input!")
          }
        })
      }
    }
  }

</script>

<style scoped lang="less">
  .login_container{
    background-color: rgb(187,230,214);
    height: 100%;
  }
  .login_box{
    width: 450px;
    height: 300px;
    background-color: #fefefe;
    border-radius: 5px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    .avatar_box{
      height: 130px;
      width: 130px;
      border: 1px solid #eee;
      border-radius: 50%;
      padding: 8px;
      box-shadow: 0 0 15px #ccc;
      position: absolute;
      left: 50%;
      transform: translate(-50%,-50%);
      background-color: #fff;
      img{
        width: 100%;
        height: 100%;
        border-radius: 50%;
      }
    }
  }
  .btns{
    display: flex;
    justify-content: flex-end;
  }

  .login_form{
    position: absolute;
    width: 100%;
    padding: 10px;
    bottom: 0;
    box-sizing: border-box;
  }
</style>