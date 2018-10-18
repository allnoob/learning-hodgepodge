# MongoDB聚合函数的平均数问题
### 问题
> 对于MongoDB聚合函数中求平均数该怎么过滤掉为null的字段
小弟在使用完 ```$project``` 投射并且对一些字段做了判断之后的数据，是存在空值的，比如：
```json
{
    "_id" : "0",
    "saturday" : Number(4),
    "sunday" : null
}

/* 2 */
{
    "_id" : "0",
    "saturday" : Number(2),
    "sunday" : Number(3)
}

/* 3 */
{
    "_id" : "0",
    "saturday" : null,
    "sunday" : Number(1)
}
...
...
...
```
看到了吧，里面有些字段不对称性的为null，一开始我就在这些数据中为了分组而懊恼，不知道怎么取平均数，因为这种为null的值肯定是不能带进去求得，但是又怕到时候处以总数量的时候这些null值也算在总数里面，这样子就造成了一些问题的产生。
### 解决
但后面我自己换了好几组数据后，对数据进行了一个简单的运算，发现我的顾虑是多余的，因为在求平均数的时候，mongodb已经自动过滤掉那些为空的数，所以我们根本不需要担心。即可直接执行操作
```js
...
{
    $group: {
        "_id": "$id",
        "saturdayCount": {
            $avg: "$saturday"
        },
        "sundayCount": {
            $avg: "$sunday"
        }
    }
}
```
得到的数据就是我最终想要的
```json
{
    "_id" : "0",
    "saturdayCount" : 3,
    "sundayCount" : 2
}
```