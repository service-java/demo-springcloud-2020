package cn.iocoder.springboot.labs.lab10.springdatarediswithjedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PubSubTest {

    public static final String TOPIC = "TEST";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test01() throws InterruptedException {
        // @tip 需要再config里开启listener
        for (int i = 0; i < 3; i++) {
            stringRedisTemplate.convertAndSend(TOPIC, "yunai:" + i);
            Thread.sleep(1000L);

/*
输出结果:
收到 ChannelTopic 消息：
线程编号：listenerContainer-2
message：yunai:0
pattern：TEST
收到 ChannelTopic 消息：
线程编号：listenerContainer-3
message：yunai:1
pattern：TEST
收到 ChannelTopic 消息：
线程编号：listenerContainer-4
message：yunai:2
pattern：TEST
*/


        }
    }

}
