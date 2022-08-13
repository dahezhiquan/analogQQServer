# analogQQServer🤷‍♂️
模拟QQ的命令行即时通讯系统服务器端内核，涉及多线程与网络编程

本项目无数据库服务，使用HashMap来模拟数据库

# 项目简介😶‍🌫️
网络socket开发的服务端内核，可支持的实现私聊，群聊，文件传输，服务端新闻/消息推送功能


# 目录简介🙌

## qqcommon
用户网络传输的对象实体信息
- Message.java 客户端与服务端通信时的消息对象
- MessageType.java 表示消息类型有那些。在接口中定义了一些常量，不同的常量的值表示不同的消息类型
- User.java 用户/客户的信息

## qqframe
框架服务层
- QQFrame.java 该类创建QQServer对象，启动后台的服务

## qqserver
业务服务层
- ManageClientThreads.java 该类通过集合管理和客户端通信的线程
- QQServer.java 监听端口，等待客户端的链接，保持通信
- SendNewsToAllService.java 服务端推送消息线程
- ServerConnectClientThread.java 该类的一个对象和某个客户端保持通信

## utils
工具类

# 使用方法🎈
运行 QQFrame.java 即可开启服务端，您可以在QQServer中修改服务器端监听的端口，但是注意此端口要和客户端发送连接的端口保持一致，以进行通信

修改位置如下：
``` java
System.out.println("服务端在9999端口监听...");
// 启动推送消息服务线程
new Thread(new SendNewsToAllService()).start();
ss = new ServerSocket(9999);
```

启动服务端后，您可以自行选择服务器要推送的新闻/消息[输入exit表示退出推送服务]：

服务端演示使用图示：

![image](https://user-images.githubusercontent.com/76278560/184478799-8a11ddff-d99c-47a5-bd03-05c11299bac6.png)



