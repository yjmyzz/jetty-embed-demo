package test.yjmyzz.jetty.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;

/**
 * {type your description }
 *
 * @since: 12/11/15.
 * @author: yangjunming
 */
public class SocketTest {

    private static Logger logger = LoggerFactory.getLogger(SocketTest.class);


    public static void main(String[] args) {
        logger.debug("正在测试端口可用性...");
        checkPort(9090);
        logger.debug("测试完毕!");
    }

    private static void checkPort(int port) {

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            logger.debug(port + "端口连接成功!");
            //serverSocket.close();
        } catch (Exception e) {
            logger.debug(port + "端口连接失败!");
            System.exit(0);
        }

        System.out.println("1");

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            logger.debug(port + "端口连接成功!");
        } catch (Exception e) {
            logger.debug(port + "端口连接失败!");
            System.exit(0);
        }

        System.out.println("2");
    }
}
