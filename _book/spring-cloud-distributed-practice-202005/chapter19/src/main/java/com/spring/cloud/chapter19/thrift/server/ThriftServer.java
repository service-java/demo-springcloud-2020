package com.spring.cloud.chapter19.thrift.server;

import com.spring.cloud.chapter19.thrift.service.RoleService;
import com.spring.cloud.chapter19.thrift.service.UserService;
import com.spring.cloud.chapter19.thrift.service.impl.RoleServiceImpl;
import com.spring.cloud.chapter19.thrift.service.impl.UserServiceImpl;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**** imports ****/

// Thrift 服务器（由服务提供者实现）
public class ThriftServer {
    // 端口
    public  static  final int  SERVER_PORT = 8888;

    // 启动Thrift服务器
    public static void startServer() {
        try {
            System.out.println("chapter19 thrift starting ...");
            // 定义处理器层 ①
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            // 注册定义的两个服务
            processor.registerProcessor("userSerive",
                    new UserService.Processor<UserService.Iface>(
                            new UserServiceImpl()));
            processor.registerProcessor( "roleSerive",
                    new RoleService.Processor<RoleService.Iface>(
                            new RoleServiceImpl()));
            // 定义服务器，以Socket（套字）的形式传输数据，并设置启动端口 ②
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            // 服务器参数
            TServer.Args tArgs = new TServer.Args(serverTransport);
            // 设置处理器层层
            tArgs.processor(processor);
            // 采用二进制的数据协议 ③
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // 创建简易Thrift服务器（TSimpleServer）对象 ④
            TServer server = new TSimpleServer(tArgs);
            // 启动服务
            server.serve();
        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // 启动服务
        startServer();
    }
}