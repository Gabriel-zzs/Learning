# 关于学习git指令和使用git的一些思考以及解决问题心得
## 1. git指令
|指令|作用||
|:---|:----:|---:|
|git config --global user.name |用户名|
|git config --global user.email| 邮箱|
|git config --global --unset user.name |取消用户名配置信息|
|git config --global --unset user.email |取消邮箱配置信息
|git init |初始化仓库|
|git add . |把所有文件添加到暂存区|
|git commit -m "提交信息" |把暂存区的文件提交到本地仓库|
|git status |查看当前仓库的状态|
|git log |查看提交日志|
|git branch |查看分支|
|git branch 分支名 |创建分支|
|git checkout 分支名 |切换分支|
|git branch -b 分支名 |创建分支并切换到分支|
|git branch -d 分支名 |删除分支|
|git merge 分支名 |合并分支|
|ssh-keygen -t rsa |生成ssh密钥|
|cat ~/.ssh/id_rsa.pub |查看ssh密钥|
|git remote add origin 仓库地址 |添加远程仓库|
|git remote |查看远程仓库|
|git remote rm origin |删除远程仓库|
|git push origin 分支名 |推送分支到远程仓库|
|git push --set-upstream origin 分支名 |推送分支到远程仓库并关联分支|
|git fetch origin 分支名 |抓取远程仓库的分支不会自动与本地仓库合并|
|git pull origin 分支名 |拉取远程仓库的分支会自动与本地仓库合并|
|git clone 仓库地址 |克隆远程仓库|

## 2. 操作过程中遇到的问题
### 1. vpn与github的问题
使用vpn上github，发现通过ssh方式让本地仓库与github远程仓库连接，发现一直报错，查找资料发现是vpn的问题，一般都是三个建议，第一个是使用git config --global --unset http.proxy和git config --global --unset https.proxy两个命令来取消代理，尝试过后没有效果。然后又尝试设置代理，依然毫无作用，搞得满头大汗。
后面继续查找资料，设置ssh代理来防止github被墙，相比于http或https代理来说，是一种更好的方式，通过在.ssh文件夹中添加一个不带后缀的config文件来实行，在config文件中写入配置。
配置如下：
   
    #Windows用户，注意替换你的端口号和connect.exe的路径
    ProxyCommand "C:\APP\Git\mingw64\bin" -S 127.0.0.1:51837 -a none %h %p

    Host github.com
    User git
    Port 22
    Hostname github.com
    IdentityFile "C:\Users\Your_User_Name\.ssh\id_rsa"
    TCPKeepAlive yes

    Host ssh.github.com
    User git
    Port 443
    Hostname ssh.github.com
    IdentityFile "C:\Users\Your_User_Name\.ssh\id_rsa"
    TCPKeepAlive yes 
    
 [解决方法的链接在此](https://zhuanlan.zhihu.com/p/481574024)。