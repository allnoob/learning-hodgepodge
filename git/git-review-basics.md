# git-review-patch
### 说明
我们现有代码提交在gerrit上，因为目前每次都是本地进行修改并推送的，所以并不会察觉到一些问题；当如果在公司和家里使用不同的电脑，想对同一个gerrit代码提交进行修改的时候，
就会发现处理起来比较麻烦，这个工具就是可以帮助我们简化这部分操作的；以及在紧急的时候，先手修改他人创建的待审核代码也是可以的。
> 参考文档：https://gerrit-review.googlesource.com/Documentation/

### 安装
##### Linux
```shell
sudo apt-get install git-review
```
##### OS X
1. [brew](https://coolestguidesontheplanet.com/installing-homebrew-on-os-x-el-capitan-10-11-package-manager-for-unix-apps/)
```shell
brew install git-review
```
2. pip
```shell
sudo easy_install pip
sudo pip install -U setuptools
pip install --user git-review
```
> 此处只列出简单的两个安装，具体的请查看 https://www.mediawiki.org/wiki/Gerrit/git-review#Installation
### 基本使用
##### 对一个在gerrit上的待合并代码进行修改
1. 每一次提交在url上都会有一个序列号，请记住这个序列号，比如这个url的序列号是3409：
```http://localhost:8080/#/c/project/+/3409/```<br/>
2. 然后把目光转移到cmd，在该项目下去执行命令（你会发现，当前分支改变了，里面的内容也都是3409此次代码提交的更改）
```shell
git review -d 3409
```
3. 随便修改一个文件（比方，我在某个文件加上一行注释 // test git-review）<br/>
4. 提交（请勿使用-m提交，会覆盖先前并重新生成Change-Id，使用文本编辑器修改，则可以保持先前Change-Id不变）
```shell
git add [modify file]
git commit --amend --all
git review -R
```
5. 最后回到gerrit上查看，就可以看到此次通过git-review添加的一行注释
### 参数
**一些常用命令，更多的请参考 ```git review --help```**
* -d [change]

从Gerrit下载更改到本地分支。分支将以review/[author]/[number]。如果本地分支已存在，它将尝试使用最新的补丁集进行更新以进行此更改。
* -f

提交时删除review/[author]/[number]，并切换回master分支
* -n

dry-run，演习执行命令
* -s

只做当前git项目的一些配置，不做任何其他事
* -t [topic]

就在推送时设置此次提交的主题，默认不设置则是Topic
* -u

跳过缓存的本地副本并强制从网络资源更新
* -l

列出该项目在此次提交上的所有评论
* -y

Indicate that you do, in fact, understand if you are submitting more than one patch. 试了一下我也不太清楚这个是干嘛用的。。
* v

提交的时候打出更详细的输出，相当于是提交日志也一并展示啦
* R

提交的时候，不要自动执行rebase
