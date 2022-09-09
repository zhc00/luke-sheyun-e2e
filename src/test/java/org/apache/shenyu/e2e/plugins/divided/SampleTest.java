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

package org.apache.shenyu.e2e.plugins.divided;

import com.google.common.collect.Lists;
import luke.e2e.engine.MyShenYuScenarioProvider;
import org.apache.shenyu.e2e.client.admin.AdminClient;
import org.apache.shenyu.e2e.config.ShenYuEngineConfigure.Mode;
import org.apache.shenyu.e2e.core.annotation.ShenYuAdminClient;
import org.apache.shenyu.e2e.core.annotation.ShenYuScenario;
import org.apache.shenyu.e2e.core.annotation.ShenYuTest;
import org.apache.shenyu.e2e.core.scenario.TestCaseSpec;
import org.assertj.core.internal.bytebuddy.description.method.MethodDescription;
import org.assertj.core.internal.bytebuddy.implementation.MethodCall.ArgumentLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

@ShenYuTest(
        mode = Mode.host,
        services = {

        },
        dockerComposeFile = "classpath:./docker-compose.yml"
)
public class SampleTest {
    
    @ShenYuScenario(provider = MyShenYuScenarioProvider.class)
    void test(AdminClient client, TestCaseSpec spec) {
        System.out.println(spec);
    }
    
//    @BeforeAll
//    static void setup(@ShenYuAdminClient AdminClient client) {
//        // login, create selector, create rules
//    }
//

}
