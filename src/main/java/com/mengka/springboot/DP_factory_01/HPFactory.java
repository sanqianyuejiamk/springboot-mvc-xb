package com.mengka.springboot.DP_factory_01;

import com.mengka.springboot.DP_factory_01.keyboard.HPKeyboard;
import com.mengka.springboot.DP_factory_01.keyboard.Keyboard;
import com.mengka.springboot.DP_factory_01.mainframe.HPMainFrame;
import com.mengka.springboot.DP_factory_01.mainframe.MainFrame;
import com.mengka.springboot.DP_factory_01.monitor.HPMonitor;
import com.mengka.springboot.DP_factory_01.monitor.Monitor;

/**
 * @author mengka
 * @version 2020/12/3
 * @since
 */
public class HPFactory implements IFactory {
    @Override
    public MainFrame createMainFrame() {
        MainFrame mainFrame = new HPMainFrame();
        //...造一个HP主机;
        return mainFrame;
    }

    @Override
    public Monitor createMonitor() {
        Monitor monitor = new HPMonitor();
        //...造一个HP显示器;
        return monitor;
    }

    @Override
    public Keyboard createKeyboard() {
        Keyboard keyboard = new HPKeyboard();
        //...造一个HP键盘;
        return keyboard;
    }
}
