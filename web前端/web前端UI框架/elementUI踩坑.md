# elementUI踩坑
> elementUI是饿了么开发的一套基
于Vue 2.0 的桌面端组件库

### 最近公司开发在使用elementui，也遇到不少坑，列出来以防以后忘记

1.elementUI的Form表单可以添加校验规则，但是格式有规定要求，必须是在外层的```<el-form></el-form>```
中添加```:model :rules ```
而且组件中的```v-model```需要和```:model```进行绑定，不然会造成校验规则失效。