package com.mengka.springboot.moxie_01.componet;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize(using = TaskCreateReqSerializer.class)
public class TaskCreateReq {
    private Map<String, String> params = new HashMap<>();

    private TaskCreateReq() {
    }


    public Map<String, String> getParams() {
        return params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, String> params = new HashMap<>();

        public TaskCreateReq build() {
            TaskCreateReq req = new TaskCreateReq();
            req.getParams().putAll(params);
            return req;
        }

        public Builder add(String name, String value) {
            params.put(name, value);
            return this;
        }
    }
}
