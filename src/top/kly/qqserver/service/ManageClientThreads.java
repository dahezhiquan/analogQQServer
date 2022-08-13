package top.kly.qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 该类通过集合管理和客户端通信的线程
 */
public class ManageClientThreads {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    /**
     * 添加线程到集合中
     */
    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    /**
     * 移除一个线程
     *
     * @param userId 用户名
     */
    public static void delClientThread(String userId) {
        hm.remove(userId);
    }

    /**
     * 通过userId返回线程
     *
     * @param userId 用户名
     * @return 该用户名的socket线程
     */
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }

    /**
     * 返回在线用户列表
     *
     * @return 在线用户列表
     */
    public static String getOnlineUser() {
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while (iterator.hasNext()) {
            onlineUserList += iterator.next() + " ";
        }
        return onlineUserList;
    }

    /**
     * 获取所有的线程列表
     * @return 一个HashMap集合，里面管理了所有的socket线程
     */
    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }
}
