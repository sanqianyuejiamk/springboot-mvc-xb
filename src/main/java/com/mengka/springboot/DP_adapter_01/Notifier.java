package com.mengka.springboot.DP_adapter_01;

import java.util.List;

public interface Notifier {

    void notify(List<String> recipients, String message);

}
