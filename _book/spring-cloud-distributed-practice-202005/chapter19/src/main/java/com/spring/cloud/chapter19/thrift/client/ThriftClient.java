package com.spring.cloud.chapter19.thrift.client;

import com.spring.cloud.chapter19.utils.R4jUtils;
import com.spring.cloud.chapter19.thrift.pojo.RolePojo;
import com.spring.cloud.chapter19.thrift.pojo.UserPojo;
import com.spring.cloud.chapter19.thrift.service.RoleService;
import com.spring.cloud.chapter19.thrift.service.UserService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.List;

/**** imports ****/

public class ThriftClient {
    public static final String SERVER_IP = "localhost"; // 服务器IP
    public static final int SERVER_PORT = 8888; // 端口
    public static final int TIMEOUT = 30000; // 连接超时时间

    public static void testClient2()  {
        TTransport transport = null;
        try {
            //  传输层
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 数据协议层
            TBinaryProtocol protocol = new TBinaryProtocol( transport);
            // 从处理器层获取业务接口
            TMultiplexedProtocol userServiceMp
                    = new TMultiplexedProtocol(protocol, "userSerive");
            UserService.Client userClient
                    = new UserService.Client(userServiceMp);
            // 打开连接
            transport.open();
            // 获取断路器
            CircuitBreaker circuitBreaker
                = R4jUtils.CircuitBreakerRegistry().circuitBreaker("thrift");
            // 捆绑事件和断路器
            CheckedFunction0<UserPojo> decoratedSupplier =
                CircuitBreaker.decorateCheckedSupplier(circuitBreaker,
                    () -> userClient.getUser(1L));
            // 发送事件
            Try<UserPojo> result = Try.of(decoratedSupplier)
                // 如果发生异常，则执行降级方法
                .recover(ex -> null); // ④
            System.out.println(result.get().getUserName());
        } catch (Exception ex) {
            ex.printStackTrace(); // 异常
        } finally {
            if (transport != null) {
                // 关闭连接  ⑧
                transport.close();
            }
        }
    }

    public static void testClient() {
        TTransport transport = null;
        try {
            //  传输层 ①
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 数据协议层 ②
            TBinaryProtocol protocol = new TBinaryProtocol(transport);
            // 从处理器层获取业务接口 ③
            TMultiplexedProtocol userServiceMp
                    = new TMultiplexedProtocol(protocol, "userSerive");
            TMultiplexedProtocol roleServiceMp
                    = new TMultiplexedProtocol(protocol, "roleSerive");
            // UserService接口的客户端
            UserService.Client userClient
                    = new UserService.Client(userServiceMp);
            // 打开连接 ④
            transport.open();
            long id = 0L;
            long current = System.currentTimeMillis();
            while(true) {
                id ++;
                UserPojo result = userClient.getUser(id); // 服务调用 ⑤
                long now = System.currentTimeMillis();
                // 超出1秒后中断循环  ⑥
                if (now - current >= 1000L) {
                    break;
                }
            }
            // 打印循环次数
            System.out.println("循环了" + id + "次");
            // 获取处理器层的RoleService客户端接口
            RoleService.Client roleClient
                    = new RoleService.Client(roleServiceMp);
            List<RolePojo> roleList
                    = roleClient.getRoleByUserId(1L); // 服务调用 ⑦
            // 打印调用结果
            System.out.println(roleList.get(0).getRoleName());
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                // 关闭连接  ⑧
                transport.close();
            }
        }
    }

    public static void main(String[] args)  throws  Exception {
        testClient2();
    }
}