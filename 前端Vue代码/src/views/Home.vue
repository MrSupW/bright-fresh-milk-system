<template>
  <el-container class="home-container">
    <el-header>
      <div>
        <span><i>Bright Fresh Milk System</i></span>
        <div class="author_info">
          <img src="../assets/images/avatar.jpg" alt="" class="avatar">
          <span class="author">Author@MrSupW</span>
        </div>
      </div>
      <el-button type="info" plain  @click="logout">Logout</el-button>
    </el-header>
    <el-container>
<!--      侧边栏-->
      <el-aside :width="isCollapsed?'64px':'200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu background-color="rgb(74, 142, 117)" text-color="#fff"
                 active-text-color="green" unique-opened :collapse="isCollapsed"
                  :collapse-transition="false">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-goods" style="color:#fff;"></i>
              <span slot="title">Products</span>
            </template>
            <el-menu-item index="1-1" @click="gotoRouter('/product/jelly')">
              <span slot="title">Jelly</span>
            </el-menu-item>
            <el-menu-item index="1-2"  @click="gotoRouter('/product/milkDrink')">
              <span slot="title">MilkDrink</span>
            </el-menu-item>
            <el-menu-item index="1-3" @click="gotoRouter('/product/pureMilk')">
              <span slot="title">PureMilk</span>
            </el-menu-item>
            <el-menu-item index="1-4" @click="gotoRouter('/product/yogurt')">
              <span slot="title">Yogurt</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-s-promotion" style="color:#fff;"></i>
              <span slot="title">Orders</span>
            </template>
            <el-menu-item index="2-1" @click="gotoRouter('/order/currentOrders')">
              <span slot="title">Current Orders</span>
            </el-menu-item>
            <el-menu-item index="2-2" @click="gotoRouter('/order/historyOrders')">
              <span slot="title">History Orders</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu" style="color:#fff;"></i>
              <span slot="title">Storage</span>
            </template>
            <el-menu-item index="3-1" @click="gotoRouter('/storage/jelly')">
              <span slot="title">Jelly</span>
            </el-menu-item>
            <el-menu-item index="3-2" @click="gotoRouter('/storage/milkDrink')">
              <span slot="title">MilkDrink</span>
            </el-menu-item>
            <el-menu-item index="3-3" @click="gotoRouter('/storage/pureMilk')">
              <span slot="title">PureMilk</span>
            </el-menu-item>
            <el-menu-item index="3-4" @click="gotoRouter('/storage/yogurt')">
              <span slot="title">Yogurt</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
<!--      主题内容-->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
  export default {
    name: "Home",
    data(){
      return{
        avatar_path:'../assets/images/avatar.jpg',
        isCollapsed: true,
      }
    },
    methods:{
      logout(){
        window.sessionStorage.removeItem("token");
        this.$message.success("Logout Successfully!")
        this.$router.push("/login");
      },
      toggleCollapse(){
        this.isCollapsed = !this.isCollapsed;
      },
      gotoRouter(router){
        this.$router.push(router);
      }
    }
  }
</script>

<style scoped lang="less">
  .home-container{
    height: 100%;
  }

  .el-header{
    background-color: rgb(173,225,204);
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #fff;
    font-size: 35px;
    font-weight: 500;
    > div{
      display: flex;
      align-items: center;
    }
  }

  .author_info{
    margin-left: 30px;
    display: flex;
    align-items: center;
    .author{
      font-weight: 300;
      font-size: 20px;
    }
    .avatar{
      width: 50px;
      height: 50px;
      border-radius: 50%;
      margin-left: 10px;
    }
  }


  .el-aside{
    background-color: rgb(200,235,223);
  }

  .el-main{
    background-color: rgb(228,245,239);
  }

  .toggle-button{
    background-color: rgb(86, 198, 157);
    letter-spacing: 0.2em;
    font-size: 18px;
    line-height: 30px;
    color: #fff;
    text-align: center;
    cursor: pointer;
  }

</style>