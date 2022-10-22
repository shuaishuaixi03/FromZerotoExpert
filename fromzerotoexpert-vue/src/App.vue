<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  name: "app",
  created() {
    // alert("app")
    this.localSocket()
  },
  methods: {
    localSocket() {
      let that = this;
      if ("WebSocket" in window) {
        // console.log("您的浏览器支持 WebSocket!");
        // location.host
        that.ws = new WebSocket("ws://47.98.198.2:8060/fromzerotoexpert/websocket/test");
        that.global.setWs(that.ws);
        that.ws.onopen = function () {
          console.log('websocket连接成功');
        };

        that.ws.onclose = function () {
          // 关闭 websocket
          console.log("连接已关闭...");
          //断线重新连接
          setTimeout(() => {
            that.localSocket();
          }, 2000);
        };
      } else {
        // 浏览器不支持 WebSocket
        console.log("您的浏览器不支持 WebSocket!");
        this.openNotificationWithIcon('error', '浏览器', '您的浏览器不支持显示消息请更换', 1,1)
      }
    },
  }
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
