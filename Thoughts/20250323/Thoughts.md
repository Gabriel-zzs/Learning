# 学习过程中遇到的问题记录
1.安装Elasticsearch时遇到了阻力，卡了三四个小时，找了很多解决方案，都不管用。
最后发现是windos普通账户默认关闭了一些权限，导致运行Elasticsearch时，
Elasticsearch没有写权限，一直启动不了。后面设置了文件的权限解决。

2.3月23日虚拟机安装centos中遇到的问题
虚拟机无法启动，报错error in supr3hardenedWinReSpwan
解决办法:cmd输入net start vboxsup 来手动启动服务