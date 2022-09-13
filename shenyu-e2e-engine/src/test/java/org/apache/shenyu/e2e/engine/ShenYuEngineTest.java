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

package org.apache.shenyu.e2e.engine;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.shenyu.e2e.client.admin.AdminClient;
import org.apache.shenyu.e2e.client.admin.model.data.SearchCondition.SelectorQueryCondition;
import org.apache.shenyu.e2e.client.admin.model.response.SelectorDTO;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest.Parameter;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest.ServiceConfigure;
import org.apache.shenyu.e2e.engine.config.ShenYuEngineConfigure.Mode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

@ShenYuTest(
        mode = Mode.HOST,
        services = {
                @ServiceConfigure(
                        serviceName = "admin",
                        baseUrl = "http://localhost:9095",
                        parameters = {
                                @Parameter(key = "username", value = "admin"),
                                @Parameter(key = "password", value = "123456"),
                        }
                )
        }
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShenYuEngineTest {
    
    @BeforeAll
    static void setup(AdminClient client) {
        client.login();
    }
    
    @Test
    @Order(0)
    void testCreateSelector(AdminClient client) throws JsonProcessingException {
    
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
        client.listAllRules().forEach(e -> client.deleteRules(e.getId()));
    }
}
