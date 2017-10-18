package com.mengka.springboot.activemq_03;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQBytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 定义Topic监听器
 *
 * @author mengka
 * @date 2017/09/20.
 */
@Slf4j
public class NocListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            ActiveMQBytesMessage txtMsg = (ActiveMQBytesMessage) message;
            byte[] data = txtMsg.getContent().getData();

            String payload = asciiToString(data);
            log.info("---------------, receive message = " + payload.toString());

        } catch (Exception e) {
            log.error("onMessage error!", e);
        }
    }


    /**
     * 将ASCII码数组转换为字符串
     *
     * @param data
     * @return
     */
    public static String asciiToString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length);
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {
                throw new IllegalArgumentException();
            }
            sb.append((char) data[i]);
        }
        return sb.toString();
    }
}
