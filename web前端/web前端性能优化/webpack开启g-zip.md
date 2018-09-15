# g-zip加速
### 前端项目过大一直是让我头疼的问题，最近使用了g-zip加速，感觉效果还不错。本文参考自[segmentfault](https://segmentfault.com/a/1190000013239622)
### 打包时，可以将```config/index.js```文件的```productionGzip```设置为```true```
>主要是webpack里[compression-webpack-plugin](https://webpack.docschina.org/plugins/compression-webpack-plugin/)模块的实现。如果没有此模块需要进行安装。需要注意版本，1.X版本和2.X版本的参数不同，如果出现报错可能是参数的问题。
### 
>使用node服务器需要安装[compression](https://github.com/expressjs/compression)模块
### espress实现
```js
const compression = require('compression')
const express = require('express')
const app = express()
app.use(compression({ threshold: 9 }))
```
### nginx实现
```nginx
//conf文件里
  server {
        listen       8088;
        server_name  localhost;
        location / {
            gzip on;
            gzip_min_length 1k;
            gzip_buffers 16 64k;
            gzip_http_version 1.1;
            gzip_comp_level 9;
            gzip_types text/plain text/javascript application/javascript image/jpeg image/gif image/png application/font-woff application/x-javascript text/css application/xml;
            gzip_vary on;
            root   /xxx/xxx/xxx;
            index index.html
        }
        }
```