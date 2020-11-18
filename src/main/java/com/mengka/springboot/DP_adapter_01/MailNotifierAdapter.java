package com.mengka.springboot.DP_adapter_01;

import java.util.List;

/**
 *  适配器模式（Adapter Pattern）
 *   适配器模式是作为两个不兼容的接口之间的桥梁。
 *
 *  》》意图：将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
 *
 *  》》举个真实的例子，读卡器是作为内存卡和笔记本之间的适配器。您将内存卡插入读卡器，再将读卡器插入笔记本，这样就可以通过笔记本来读取内存卡。
 *
 */
public class MailNotifierAdapter implements Notifier {

    private static final String NOTIFICATION_TITLE = "System notification";

    public void notify(List<String> recipients, String message) {

        MailSender mailSender = new MailSender(NOTIFICATION_TITLE,message,recipients);
        mailSender.sendMessage();
    }

}
