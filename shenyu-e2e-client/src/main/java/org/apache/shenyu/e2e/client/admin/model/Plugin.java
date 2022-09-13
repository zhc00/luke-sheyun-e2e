/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.e2e.client.admin.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Plugin {
    
    SIGN("sign", 1),
    WAF("waf", 2),
    REWRITE("rewrite", 3),
    RATE_LIMITER("rateLimiter", 4),
    DIVIDE("divide", 5),
    DUBBO("dubbo", 6),
    SPRING_CLOUD("springCloud", 8),
    HYSTRIX("hystrix", 9),
    SENTINEL("sentinel", 10),
    RESILIENCE4J("resilience4j", 12),
    TARS("tars", 13),
    CONTEXT_PATH("contextPath", 14),
    GRPC("grpc", 15),
    REDIRECT("redirect", 16),
    MOTAN("motan", 17),
    LOGGING_CONSOLE("loggingConsole", 18),
    JWT("jwt", 19),
    REQUEST("request", 20),
    OAUTH2("oauth2", 21),
    PARAM_MAPPING("paramMapping", 22),
    MODIFY_RESPONSE("modifyResponse", 23),
    CRYPTOR_REQUEST("cryptorRequest", 24),
    CRYPTOR_RESPONSE("cryptorResponse", 25),
    WEBSOCKET("websocket", 26),
    GENERAL_CONTEXT("generalContext", 27),
    MQTT("mqtt", 28),
    LOGGING_ROCKETMQ("loggingRocketMQ", 29),
    CACHE("cache", 30),
    MOCK("mock", 31),
    LOGGING_ELASTIC_SEARCH("loggingElasticSearch", 32),
    LOGGING_KAFKA("loggingKafka", 33),
    LOGGING_ALIYUN_SLS("loggingAliyunSls", 34),
    ;
    
    private final String id;
    private final String alias;
    
    Plugin(String alias, int id) {
        this.alias = alias;
        this.id = String.valueOf(id);
    }
    
    @JsonValue
    public String getId() {
        return id;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public static Map<String, String> toMap() {
        return Arrays.stream(Plugin.values()).collect(Collectors.toUnmodifiableMap(Plugin::getAlias, Plugin::getId));
    }
}
