package top.kly.qqserver.service;


import top.kly.qqcommon.Message;
import top.kly.qqcommon.MessageType;
import top.kly.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 服务端推送消息
 */
public class SendNewsToAllService implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.print("请输入服务器要推送的新闻/消息[输入exit表示退出推送服务]：");
            String news = Utility.readString(100);
            // 退出推送服务线程
            if ("exit".equals(news)) {
                break;
            }
            Message message = new Message();
            message.setSender("服务器");
            message.setContent(news);
            message.setSendTime(new Date().toString());
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            System.out.println("服务器推送消息给所有的人：" + news);

            // 遍历所有通讯线程，发送message
            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onlineUserId = iterator.next();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(hm
                            .get(onlineUserId).getSocket().
                            getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
