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

package org.apache.shenyu.e2e.client.admin;

import com.google.common.collect.Lists;
import org.apache.shenyu.e2e.client.admin.model.data.Condition;
import org.apache.shenyu.e2e.client.admin.model.data.RuleData;
import org.apache.shenyu.e2e.client.admin.model.data.SearchCondition.SelectorQueryCondition;
import org.apache.shenyu.e2e.client.admin.model.data.SelectorData;
import org.apache.shenyu.e2e.client.admin.model.handle.DivideRuleHandle;
import org.apache.shenyu.e2e.client.admin.model.handle.Upstreams;
import org.apache.shenyu.e2e.client.admin.model.handle.Upstreams.Upstream;
import org.apache.shenyu.e2e.client.admin.model.response.RuleDTO;
import org.apache.shenyu.e2e.client.admin.model.response.SelectorDTO;
import org.apache.shenyu.e2e.matcher.ResourceMatcher;
import org.apache.shenyu.e2e.engine.config.ShenYuEngineConfigure.Mode;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest.Parameter;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest.ServiceConfigure;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

@ShenYuTest(
        mode = Mode.docker,
        services = {
                @ServiceConfigure(
                        baseUrl = "http://localhost:9095",
                        serviceName = "admin",
                        port = 9095,
                        parameters = {
                                @Parameter(key = "username", value = "admin"),
                                @Parameter(key = "password", value = "123456"),
                        }
                )
        },
        dockerComposeFile = "classpath:./docker-compose.yml"
)
public class AdminClientDockerTest {
    
    @BeforeAll
    static void setup(AdminClient client) {
        client.login();
    }
    
    @Test
    void testCreateSelector(AdminClient client) {
        SelectorData selectorData = SelectorData.builder()
                .name("my-plugin-divide")
                .pluginName("divide")
                .type("1")
                .sort(1)
                .matchMode("1")
                .logged(true)
                .enabled(true)
                .continued(true)
                .handle(Upstreams.builder().add(Upstream.builder().upstreamUrl("ss:99").build()).build())
                .conditionList(
                        Lists.newArrayList(Condition.builder().paramType("uri").operator("match").paramName("/").paramValue("/**").build())
                ).build();
        SelectorDTO selector = client.create(selectorData);
        List<SelectorDTO> selectors = client.searchSelector(selector.getName());
        Assertions.assertThat(selectors.size()).isEqualTo(1);
        
        Assertions.assertThat(selectors.get(0)).matches(new ResourceMatcher<>(selectorData), "xx");
    
        RuleDTO ruleDTO = client.create(RuleData.builder()
                .name("my-rule")
                .enabled(true)
                .logged(true)
                .handle(DivideRuleHandle.builder()
                        .loadBalance("hash")
                        .retryStrategy("current")
                        .retry(1)
                        .timeout(3000)
                        .headerMaxSize(10240)
                        .requestMaxSize(10240)
                        .build())
                .sort(1)
                .matchMode(1)
                .selectorName(selector.getName())
                .conditionList(Lists.newArrayList(Condition.builder()
                        .paramValue("/z")
                        .paramType("uri")
                        .paramName("/")
                        .operator("=")
                        .build()))
                .build());
    
        System.out.println(ruleDTO);
    }
    
    @Test
    void testA(AdminClient client) {
        SelectorQueryCondition condition = SelectorQueryCondition.builder()
                .keyword("my-plugin-divide-4b519a373ae8-2b9b5c50")
                .switchStatus(true)
                .build();
    
        List<SelectorDTO> divide = client.searchSelector("divide");
    }
    
    @Test
    void testDeleteAllSelectors(AdminClient client) {
        client.deleteAllSelectors();
    }
    
    
    @Test
    void testListRules(AdminClient client) {
        client.listAllRules().forEach(System.out::println);
    }
}
