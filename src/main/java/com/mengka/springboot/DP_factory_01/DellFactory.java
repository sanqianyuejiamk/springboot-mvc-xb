package com.mengka.springboot.DP_factory_01;

import com.mengka.springboot.DP_factory_01.keyboard.DellKeyboard;
import com.mengka.springboot.DP_factory_01.keyboard.Keyboard;
import com.mengka.springboot.DP_factory_01.mainframe.DellMainFrame;
import com.mengka.springboot.DP_factory_01.mainframe.MainFrame;
import com.mengka.springboot.DP_factory_01.monitor.DellMonitor;
import com.mengka.springboot.DP_factory_01.monitor.Monitor;

/**
 *   具体产品工厂：实现了抽象工厂的接口，负责创建产品对象。
 *
 *  》》工厂类，专门负责复杂对象的创建。
 *
 * @author mengka
 * @version 2020/12/3
 * @since
 */
public class DellFactory implements IFactory {
    @Override
    public MainFrame createMainFrame() {
        MainFrame mainFrame = new DellMainFrame();
        //...造一个Dell主机;
        return mainFrame;
    }

    @Override
    public Monitor createMonitor() {
        Monitor monitor = new DellMonitor();
        //...造一个Dell显示器;
        return monitor;
    }

    @Override
    public Keyboard createKeyboard() {
        Keyboard keyboard = new DellKeyboard();
        //...造一个Dell键盘;
        return keyboard;
    }
}
