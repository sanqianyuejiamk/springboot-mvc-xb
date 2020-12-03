package com.mengka.springboot.DP_Pipeline_01;

import com.mengka.springboot.DP_Pipeline_01.context.InstanceBuildContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ModelInstanceSaver implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--保存模型实例到相关DB表--");

        // 假装保存模型实例

        return true;
    }
}