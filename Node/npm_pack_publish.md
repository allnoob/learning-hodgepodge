# npm打包教程
> 公司大佬让我把一个项目的工具类封装起来，然后发布到npm上，鉴于之前没有做过类似的事情，所以查询资料并记录下怎么去完成这件事。

### 步骤
1. 去Github创建公共的项目，并命名为myTools，记下该项目的地址，以便第2步需要用到（具体步骤不作说明）
2. 首先当然要在本地建一个自己项目的文件夹，然后进入项目文件夹执行命令初始化项目（新建```package.json```）
```
npm init
```
到每一步可输可不输反正后面可在```package.json```文件中改
```
This utility will walk you through creating a package.json file.
It only covers the most common items, and tries to guess sensible defaults.

See `npm help json` for definitive documentation on these fields
and exactly what they do.

Use `npm install <pkg>` afterwards to install a package and
save it as a dependency in the package.json file.

Press ^C at any time to quit.
package name: (mytools) 填你的包的名字
version: (1.0.0) 项目版本
description: 项目说明
entry point: (index.js) 项目主入口
test command: 
git repository: git仓库链接（第一步的项目地址填入到这）
keywords: 项目关键字
author: 作者
license: (ISC)
```
当然默认生成（后面再去该项目自成的生成```package.json```文件下手动改也行）
```
npm init -y
```
3. 成功后查看改项目文件夹下多了个```package.json```的文件（里面的信息大概是像这样）
```
{
  "name": "mytools",
  "version": "1.0.0",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "",
  "license": "ISC",
  "keywords": [],
  "description": ""
  "repository": {
    "type": "git",
    "url": "github url"
  }
}
```
4. 然后就去编代码吧，记住要在主入口下把你需要用到的函数进行导出
```js
module.exports = {}
```
5. 给项目打个包（打完后会发现你的项目主文件夹下出现了一个叫myTools-version.tgz的文件）
```
npm pack
```
6. 然后把当前本地的整个项目推送至github仓库上
7. 去[npm](!https://www.npmjs.com/)网站进行用户注册，记住，邮箱一定要验证！不然后面会出现发布不成功的现象，报403这种错误
8. 用户注册都准备好后，回到项目文件夹里的命令行窗口，添加npm用户到项目环境中
```
npm adduser
```
然后也是填信息，用户名，密码还有验证的邮箱，出现最后一条代表成功
```
Username: username
Password:
Email: (this IS public) email
Logged in as username on https://registry.npmjs.org/.
```
9. 最后一步就是要进行发布啦！
```
npm publish
```
如果没有验证邮箱会出现403的情况，推送失败，当然403错误的情况还有可能出现了同名问题，这时候只需要进```package.json```修改一个名字即可，像我这边myTools出现了同名的问题就会这样
```
npm ERR! publish Failed PUT 403
npm ERR! code E403
npm ERR! Package name too similar to existing packages; try renaming your package to '@username/mytools' and publishing with 'npm publish --access=public' instead : mytools

npm ERR! A complete log of this run can be found in:
npm ERR!     /Users/.npm/_logs/2018-09-23T09_09_10_266Z-debug.log
```
改个名再试一次，当然你可以先上npm上去搜索有没有你要取名的包在进行命名，现在我们改名为mymytoolls再进行发布就会显示成功。
```
+ mymytoolls@1.0.0
```
10. 登录网站后点击package即可看到自己发布的npm包（项目的README.md的内容会自动显示在你的npm包说明里面）
11. 如要进行取消发布执行
> 说明： 你一旦取消发布了之后重新打包发布，会显示不成功，因为npm有个限制就是在24小时之内不得发布同样的名字
```
npm unpublish 包@版本号
```
12. 可能在发布之后想要更新项目的README.md，是不能直接在当前版本进行更新的，我们需要提升版本才能进行更新。具体参考以下网站
https://docs.npmjs.com/getting-started/publishing-npm-packages

