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

import com.google.common.collect.LinkedListMultimap;
import org.apache.shenyu.e2e.core.annotation.ShenYuTest;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.junit.platform.engine.support.discovery.EngineDiscoveryRequestResolver;
import org.junit.platform.launcher.core.EngineExecutionOrchestrator;

import java.util.Collection;
import java.util.Map;

public class MyTestEngine implements TestEngine {
    @Override
    public String getId() {
        return "x-y";
    }
    
    @Override
    public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
        LinkedListMultimap<String, Object> multimap = LinkedListMultimap.create();
    
        EngineDescriptor descriptor = new EngineDescriptor(uniqueId, "y-x");
        EngineDiscoveryRequestResolver<TestDescriptor> resolver = EngineDiscoveryRequestResolver.builder()
                .addClassContainerSelectorResolver(c -> {
                    if (c.isAnnotationPresent(ShenYuTest.class)) {
                        ShenYuTest configure = c.getAnnotation(ShenYuTest.class);
                        multimap.put(configure.sharedKey(), configure);
                    }
                    return true;
                })
                .build();
        resolver.resolve(discoveryRequest, descriptor);
    
        Map<String, Collection<Object>> map = multimap.asMap();
        return descriptor;
    }
    
    EngineExecutionOrchestrator orchestrator =  new EngineExecutionOrchestrator();
    
    @Override
    public void execute(ExecutionRequest request) {
        orchestrator.execute(null, request.getEngineExecutionListener());
//        orchestrator.execute(request, request.getEngineExecutionListener());
    }
}
