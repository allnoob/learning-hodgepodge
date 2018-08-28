### Mongoose创建对象要注意的几个点
1. 今天在公司折腾node.js的时候，发现了一个问题，就是我写好模型后进行查询，但是怎么查都查不出来任何东西，一直是空 ```[]```。后来也是找了很久的问题，最终在网上查相关资料才发现：
> When no collection argument is passed, Mongoose produces a collection name by passing the model name to the utils.toCollectionName method. This method pluralizes the name. If you don't like this behavior, either pass a collection name or set your schemas collection name option.


没有传入第三个参数时（collection），就会使用第一个参数产生collectionName,并转换成复数形式，所以要显示指定第三个参数
```js
// ...

// ...

const collectionName = "user";
module.exports = mongoose.model('User', UserSchema, collectionName);
```
2. 默认mongoose每生成一个数据，就会自动产生一个主键```_id```，但这个_id的数据是mongodb自动帮你生成的，如果想要自己指定比如uuid或者自增长的id需要这样写：
```
npm install node-uuid --save
```
```js
const mongoose = require('mongoose');
const uuid = require('node-uuid');
const Schema = mongoose.Schema;

var UserSchema = new Schema({
    _id: {type:String, default:uuid.v1},
    name: String
});
```
3. 关于每次创建一条数据，会自动创建一个字段```__v```版本锁，不想自动生成的话
```js
const mongoose = require('mongoose');
const uuid = require('node-uuid');
const Schema = mongoose.Schema;

var UserSchema = new Schema({
    _id: {type:String, default:uuid.v1},
    name: String
}, {versionKey: false});
```
