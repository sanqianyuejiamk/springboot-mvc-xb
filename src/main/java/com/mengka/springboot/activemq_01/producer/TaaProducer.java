package com.mengka.springboot.activemq_01.producer;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;

/**
 * @author mengka
 * @date 2017/09/20.
 */
@Slf4j
public class TaaProducer {

    public static void main(String[] args) throws Exception {
        log.info("----------------, activemq_01 TaaProducer test....");

        String serviceConfigXMLs[] = new String[]{"activemq/activemq-context.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(serviceConfigXMLs);

        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTopicTemplate");
        sendMessage(jmsTemplate, "queue-mk01", "aa", TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));

        log.info("----------------, activemq_01 TaaProducer end....");
    }

    /**
     * 发送MQ消息<br>
     */
    public static void sendMessage(JmsTemplate jmsTemplate, String queueName, final String key, final String json) {

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
//                return session.createTextMessage(message);

                // 发送的消息都是MapMessage类型的
                MapMessage message = session.createMapMessage();
                message.setStringProperty(key, json);
                log.info("-------， MQ send Message: key=" + key + ",value=" + json);
                return message;
            }
        });
    }
}
