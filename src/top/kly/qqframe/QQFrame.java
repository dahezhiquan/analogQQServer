package top.kly.qqframe;

import top.kly.qqserver.service.QQServer;

import java.io.IOException;

/**
 * 该类创建QQServer对象，启动后台的服务
 */
public class QQFrame {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQServer();
    }
}
