package top.kly.qqserver.service;

import top.kly.qqcommon.Message;
import top.kly.qqcommon.MessageType;
import top.kly.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听9999端口，等待客户端的链接，保持通信
 */
public class QQServer {
    private ServerSocket ss = null;
    // 创建一个集合，存放多个用户，模拟用户数据库
    // 使用ConcurrentHashMap的原因是：ConcurrentHashMap处理了HashMap的多线程安全问题
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    static { // 在静态代码块中，初始化validUsers，模拟用户的数据库
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("大河", new User("大河", "123456"));
        validUsers.put("小钱", new User("小钱", "123456"));
    }

    /**
     * 验证用户是否合法
     *
     * @param userId 用户名
     * @param passwd 密码
     * @return 用户名和密码是否正确
     */
    private boolean checkUser(String userId, String passwd) {
        User user = validUsers.get(userId);
        if (user == null) { // 用户名不存在
            return false;
        }
        if (!user.getPasswd().equals(passwd)) { // 密码错误！
            return false;
        }
        return true;
    }

    public QQServer() throws IOException, ClassNotFoundException {
        System.out.println("服务端在9999端口监听...");
        // 启动推送消息服务线程
        new Thread(new SendNewsToAllService()).start();
        ss = new ServerSocket(9999);
        while (true) {
            Socket socket = ss.accept();
            // 得到socket关联的对象输入流
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            User u = (User) ois.readObject();
            Message message = new Message();
            // 验证用户，这里写一个死密码
            if (checkUser(u.getUserId(), u.getPasswd())) {
                message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                // 将message对象回复
                oos.writeObject(message);
                // 创建线程，持有socket对象
                ServerConnectClientThread scct = new ServerConnectClientThread(socket, u.getUserId());
                scct.start();
                // 将该线程对象放入一个集合中进行管理
                ManageClientThreads.addClientThread(u.getUserId(), scct);

            } else {
                // 失败，返回失败消息
                message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                oos.writeObject(message);
                // 关闭socket
                socket.close();
            }
        }
    }
}
