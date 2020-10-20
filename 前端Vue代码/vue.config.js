module.exports = {
  publicPath:process.env.NODE_ENV === "production"?"/bright-fresh-milk-system/":"/",
  assetsDir:'./',
  devServer: {
    port: 8080,
    https:false,
    open:true,
    proxy:{
    }
  },
};