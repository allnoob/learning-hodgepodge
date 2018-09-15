# vue
在公司已经用vue一段时间了，在此记录下经历的坑。
### 一.组件刷新
vue由于是单页面应用，会遇到组件带有缓存问题，在填写表单等界面会造成填写完后跳转回来还是有数据，造成交互不友好。目前想到有两种处理方式：
#### 1.第一种是在表单提交后将空值填充进表单。但是如果表单有校验会有校验信息缓存的问题存在。
#### 2.第二种是进行组件刷新，这是在[知乎](https://www.zhihu.com/question/49863095/answer/289157209)看到的一个方法，使用使用[provide / inject](https://cn.vuejs.org/v2/api/#provide-inject)来传递```reload```，下面贴入代码。
>在APP.vue
```js
<template>
    <router-view v-if="isRouterAlive" />
</template>
<script>
    export default {
        provide() {
            return {
                reload: this.reload
            }
        },
        data() {
            return {
                isRouterAlive: true
            }
        },
        methods: {
            reload() {
                this.isRouterAlive = false
                this.$nextTick(() => (this.isRouterAlive = true))
            }
        }
    }
</script>
```
>需要刷新的组件
```js
export default {
inject: ['reload']
methods: {
this.reload()//需要刷新的方法中写入
}
}
```
两种方法各有各的优势，如果是多层Dialog中的话使用第一种方法就组件销毁造成交互步骤增加。常规的填充表单就可以使用第二种方法。