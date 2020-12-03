package com.mengka.springboot.DP_factory_01;

import com.mengka.springboot.DP_factory_01.keyboard.Keyboard;
import com.mengka.springboot.DP_factory_01.mainframe.MainFrame;
import com.mengka.springboot.DP_factory_01.monitor.Monitor;

/**
 *  抽象工厂模式
 *
 *  抽象工厂：声明了创建抽象产品对象的操作接口
 *
 *  》》类结构关系：
 *  https://mmbiz.qpic.cn/mmbiz_png/33P2FdAnju81K7bYZy1ib0CaulrxWB3cHscjT6hNeviaSWDoJrH3Yymcicp5qXLnvLbgrQV8BBB5gj5bmMpGdmtDA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1
 *
 *  》》使用情景：
 *  1.在编码时不能预见需要创建哪种类的实例。
 *  2.系统不应依赖于产品类实例如何被创建、组合和表达的细节。
 *
 *  》》总之，
 *  工厂模式就是为了方便创建同一接口定义的具有复杂参数和初始化步骤的不同对象。工厂模式一般用来创建复杂对象。
 *
 *  》》文档：
 *  https://mp.weixin.qq.com/s/9GeZoXPncZlyIg2nr4101g
 *
 * @author mengka
 * @version 2020/12/3
 * @since
 */
public interface IFactory {

    MainFrame createMainFrame();

    Monitor createMonitor();

    Keyboard createKeyboard();
}
