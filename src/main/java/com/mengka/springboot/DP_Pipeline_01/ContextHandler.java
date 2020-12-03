package com.mengka.springboot.DP_Pipeline_01;

import com.mengka.springboot.DP_Pipeline_01.context.PipelineContext;

/**
 * 管道中的上下文处理器
 *
 * 文档：
 * https://mp.weixin.qq.com/s?__biz=MzAxNDEwNjk5OQ==&mid=2650413430&idx=1&sn=32c89ea3222d341bf3014854f69fd239&chksm=8396d16eb4e1587838f99a6859668a3588b0762155c1a13872d3f419efa3f75c5a532d7d38fe&scene=21#wechat_redirect
 */
public interface ContextHandler<T extends PipelineContext> {

    /**
     * 处理输入的上下文数据
     *
     * @param context 处理时的上下文数据
     * @return 返回 true 则表示由下一个 ContextHandler 继续处理，返回 false 则表示处理结束
     */
    boolean handle(T context);
}