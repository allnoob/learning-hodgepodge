# web前端安卓端踩坑
## 1.安卓手机video由于层级过高会覆盖别的内容
解决方案：1.video标签中添加```x5-video-player-type='h5' x5-video-player-fullscreen='true'```  
```html
 <video x5-video-player-type='h5' x5-video-player-fullscreen='true'></video>
 ```


