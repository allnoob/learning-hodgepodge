# web前端ios端踩坑
## 1.ios手机audio不能自动触发
解决方案： 1.利用微信sdk解决此问题  
引入微信sdk  
```js
 <script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
 ```
利用微信api解决问题  
```js
wx.config({
        // 配置信息, 即使不正确也能使用 wx.ready
    　　　　debug: false,
    　　　　appId: '',
    　　　　timestamp: 1,
    　　　　nonceStr: '',
    　　　　signature: '',
    　　　　jsApiList: []
    　　});
    　　wx.ready(function() {
    　　　　document.getElementById('shakingAudio').play();
    　　}); 
```