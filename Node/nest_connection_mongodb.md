# 对于使用nest.js框架连接mongodb的使用
https://docs.nestjs.cn/5.0/techniques?id=mongo
> 关于使用nest.js框架连接mongodb时，小弟掉进了一个坑里，但是仔细看了文档才得到一个正确的解决
### 前提
version mongoose > 5.0.0
### 问题
使用普通带用户名和密码连接时代码是这样的
```js
@Module({
  imports: [MongooseModule.forRoot('mongodb://username:password@localhost:27017/nest')],
})
```
然而我得到的错误是这样的
```
Error: Password contains an illegal unescaped character
```
### 解决
去搜索了相关资料，发现像 ```@``` 这样的字符会受到限制，也就是这个在进行mongodb连接的时候不能进行正常的解析咯；那我再去看看文档，发现了有以下的一句话
> 关键是在于那一句话（该 forRoot() 和 mongoose 包中的 mongoose.connect() 一样的参数对象。）

在 ```mongoose.connect()``` 当中可以单独把用户名和密码分成参数带进去，那么我们也可以用到这上面来
```js
@Module({
  imports: [MongooseModule.forRoot('mongodb://localhost:27017/nest', {
      auth: {
          user: 'username',
          password: 'password'
      }
  })],
})
```
再运行一次就可以看到正常运行的字眼了