## 关于
感谢原作者的技术分享和实践[rebeyond/Behinder](https://github.com/rebeyond/Behinder),当前反编译版本为Beta6

## 声明
本项目旨在学习，请勿用于非法用途！

## 测试环境
Win10 + jdk1.8 + IDEA

java内存马: tomcat7-9, websphere 12.2.1.3.0, wildfly17、21, websphere 20.0.0.12 Liberty

## update
- [x] 解决连接weblogic时，返回包开头有两个换行，导致一系列问题
- [x] 添加多种java环境下(tomcat,weblogic,wildfly,websphere)，多种内存马(冰蝎，reGeorg)的支持
- [x] 添加shell生成菜单
- [x] 网站文件zip压缩功能(jsp,php,aspx)
- [x] 启用修改文件时间戳功能(jsp,php,aspx)
- [x] 添加.Net环境下aspx内存马的支持
- [x] 添加shell导入导出功能
- [x] php下bypass open_basedir，代码直接用哥斯拉的

## 感谢
* [rebeyond/Behinder](https://github.com/rebeyond/Behinder)
* [feihong-cs/memShell](https://github.com/feihong-cs/memShell)
* [BeichenDream/Godzilla](https://github.com/BeichenDream/Godzilla)
* [pwntester/ysoserial.net](https://github.com/pwntester/ysoserial.net)