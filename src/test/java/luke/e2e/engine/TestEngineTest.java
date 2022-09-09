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

package luke.e2e.engine;

import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.LauncherDiscoveryListener;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.suite.api.ExcludeEngines;
import org.junit.platform.suite.api.IncludeEngines;

import java.util.stream.Stream;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

@IncludeEngines("x-engine-y")
@ExcludeEngines("junit-jupiter")
public class TestEngineTest {
    
    
//    @Test
    void test() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
//                .listeners(new LauncherDiscoveryListener() {
//                    @Override
//                    public void launcherDiscoveryStarted(LauncherDiscoveryRequest request) {
//                        System.out.println(">>>>>>>>>>>>.");
//                    }
//                })
//                .selectors(
//                        selectPackage("com.example.mytests"),
//                        selectClass(TestEngineTest.class)
//                )
                .build();
        try (LauncherSession session = LauncherFactory.openSession()) {
            TestPlan testPlan = session.getLauncher().discover(request);
            Stream<TestIdentifier> stream = testPlan.getRoots().stream();
            
            
            // ... discover additional test plans or execute tests
            System.out.println(testPlan);
        }
    }
    
    
    void print(Stream<TestIdentifier> stream) {
        stream.forEach(e -> {
            System.out.println(e.getDisplayName());
        });
    }
}
