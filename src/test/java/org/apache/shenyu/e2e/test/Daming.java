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

package org.apache.shenyu.e2e.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import org.apache.shenyu.e2e.client.admin.model.data.Condition;
import org.apache.shenyu.e2e.client.admin.model.data.SelectorData;
import org.apache.shenyu.e2e.client.admin.model.handle.Upstreams;
import org.apache.shenyu.e2e.client.admin.model.handle.Upstreams.Upstream;
import org.apache.shenyu.e2e.client.admin.model.response.SelectorDTO;
import org.apache.shenyu.e2e.client.common.IdManagers.Plugins;
import org.apache.shenyu.e2e.client.matcher.SelectorMatcher;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class Daming {
    
    @BeforeAll
    static void setup() {
        HashBiMap<String, String> map = HashBiMap.create();
        map.put("divide", "5");
        Plugins.INSTANCE.set(map);
    }
    
    @Test
    void test() throws JsonProcessingException {
        String text = "{\"id\":\"1568165641701011456\",\"pluginId\":\"5\",\"name\":\"my-plugin-divide-3862e3166c40-27ee9860\",\"matchMode\":1,\"matchModeName\":\"or\",\"type\":1,\"typeName\":\"custom\",\"sort\":1,\"enabled\":true,\"continued\":true,\"handle\":\"[{\\\"upstreamUrl\\\":\\\"httpbin.org:80\\\"}]\",\"dateCreated\":\"2022-09-09 09:13:18.747\",\"dateUpdated\":\"2022-09-09 09:13:18.747\",\"loged\":true,\"selectorConditions\":null}";
        SelectorDTO selector = new ObjectMapper().readValue(text, SelectorDTO.class);
    
        SelectorData selectorData = SelectorData.builder()
                .name("my-plugin-divide")
                .pluginName("divide")
                .type("1")
                .sort(1)
                .matchMode("1")
                .logged(true)
                .enabled(true)
                .continued(true)
                .handle(Upstreams.builder().add(Upstream.builder().upstreamUrl("httpbin.org:80").build()).build())
                .conditionList(
                        Lists.newArrayList(Condition.builder().paramType("uri").operator("match").paramName("/").paramValue("/**").build())
                ).build();
        
        new SelectorMatcher(selectorData).matches(selector);
    }
    
    @Test
    void testA() throws JSONException {
    
        String actualx = "{name:\"John\",id:123}";
        JSONAssert.assertEquals(
                "{id:123,name:\"John\"}", actualx, JSONCompareMode.LENIENT);
    }
}
