package com.mengka.springboot.DP_Pipeline_01.context;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

/**
 * 模型实例构建的上下文
 */
@Getter
@Setter
public class InstanceBuildContext extends PipelineContext {

    /**
     * 模型 id
     */
    private Long modelId;

    /**
     * 用户 id
     */
    private long userId;

    /**
     * 表单输入
     */
    private Map<String, Object> formInput;

    /**
     * 保存模型实例完成后，记录下 id
     */
    private Long instanceId;

    /**
     * 模型创建出错时的错误信息
     */
    private String errorMsg;

    // 其他参数

    @Override
    public String getName() {
        return "模型实例构建上下文";
    }
}