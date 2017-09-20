package com.mengka.springboot.activemq_02.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *  定义Topic监听器
 *
 * @author mengka
 * @date 2017/09/20.
 */
@Slf4j
public class NocListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        String key = "aa";
        String content = receive(message, key, String.class);
        log.info("---------------, receive message = " + content);
    }

    /**
     * 获取MQ消息,键对应的值<br>
     * 接收 key-value 的 value, 现在只处理MapMessage类型<br>
     */
    public <T> T receive(Message message, final String key, final Class<T> c) {

        if (message instanceof MapMessage) {
            MapMessage map = (MapMessage) message;
            try {
                String json = map.getStringProperty(key);
                log.info("MQ receive Message: key=" + key + ",value=" + json);
                return (T)json;
            } catch (JMSException e) {
                log.error("MapMessage map getStringProperty error key=" + key, "errMsg=" + e.getMessage());
                return null;
            }
        } else {
            log.error("the mq message type must be mdpmessage, message=" + JSONObject.toJSONString(message));
            return null;
        }
    }

}
