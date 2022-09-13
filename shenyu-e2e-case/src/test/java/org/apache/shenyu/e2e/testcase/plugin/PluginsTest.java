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

package org.apache.shenyu.e2e.testcase.plugin;

import org.apache.shenyu.e2e.client.admin.AdminClient;
import org.apache.shenyu.e2e.client.admin.model.ResourcesData;
import org.apache.shenyu.e2e.client.admin.model.ResourcesData.Resource;
import org.apache.shenyu.e2e.client.admin.model.response.SelectorDTO;
import org.apache.shenyu.e2e.client.gateway.GatewayClient;
import org.apache.shenyu.e2e.engine.annotation.ShenYuScenario;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest.Parameter;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest.ServiceConfigure;
import org.apache.shenyu.e2e.engine.config.ShenYuEngineConfigure.Mode;
import org.apache.shenyu.e2e.engine.config.ShenYuEngineConfigure.ServiceType;
import org.apache.shenyu.e2e.engine.scenario.specification.AfterEachSpec;
import org.apache.shenyu.e2e.engine.scenario.specification.BeforeEachSpec;
import org.apache.shenyu.e2e.engine.scenario.specification.CaseSpec;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

@ShenYuTest(
        mode = Mode.HOST,
        services = {
                @ServiceConfigure(
                        serviceName = "admin",
                        baseUrl = "http://{hostname:localhost}:9095",
                        parameters = {
                                @Parameter(key = "username", value = "admin"),
                                @Parameter(key = "password", value = "123456"),
                        }
                ),
                @ServiceConfigure(
                        serviceName = "gateway",
                        baseUrl = "http://{hostname:localhost}:9195",
                        type = ServiceType.SHENYU_GATEWAY
                )
        }
)
public class PluginsTest {
    
    @BeforeAll
    static void setup(AdminClient client) {
        client.login();
        client.deleteAllSelectors();
    }
    
    @BeforeEach
    void before(AdminClient client, GatewayClient gateway, BeforeEachSpec spec) {
        spec.getChecker().check(gateway);
        
        ResourcesData resources = spec.getResources();
        for (Resource res : resources.getResources()) {
            SelectorDTO dto = client.create(res.getSelector());
            
            res.getRules().forEach(rule -> {
                rule.setSelectorId(dto.getId());
                client.create(rule);
            });
        }
        
        spec.getWaiting().waitFor(gateway);
    }
    
    @ShenYuScenario(provider = DividePluginCases.class)
    void testDivide(GatewayClient gateway, CaseSpec spec) {
        spec.getVerifiers()
                .forEach(verifier -> verifier.verify(gateway.getHttpRequesterSupplier().get()));
    }
    
    @AfterEach
    void before(AdminClient client, GatewayClient gateway, AfterEachSpec spec) {
        spec.getDeleter().delete(client);
        spec.getPostChecker().check(gateway);
    }
    
    @AfterAll
    static void teardown(AdminClient client) {
        client.deleteAllSelectors();
    }
}
