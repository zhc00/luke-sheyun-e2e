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

import org.apache.shenyu.e2e.config.ShenYuEngineConfigure;
import org.apache.shenyu.e2e.core.service.DockerServiceCompose;
import org.apache.shenyu.e2e.core.service.HostServiceCompose;
import org.apache.shenyu.e2e.core.service.ServiceCompose;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.LauncherSessionListener;

import java.util.Objects;

public class ShenYuLauncherSessionListener implements LauncherSessionListener {
    ServiceCompose serviceCompose;
    
    @Override
    public void launcherSessionOpened(LauncherSession session) {
//        session.getLauncher().registerTestExecutionListeners(new TestExecutionListener() {
//            @Override
//            public void testPlanExecutionStarted(TestPlan testPlan) {
//                if (Boolean.getBoolean("e2e.seen")) {
//                    // -De2e.config=./shenyu-e2e.yml
//                    final String configureFile = System.getProperty("e2e.config");
//                    ShenYuEngineConfigure configure = ShenYuEngineConfigure.loadConfigure(configureFile);
//                    if (Objects.isNull(serviceCompose)) {
//                        serviceCompose = create(configure);
//                    } else {
//                        // throws exception
//                    }
//                }
//            }
//        });
    }
    
    @Override
    public void launcherSessionClosed(LauncherSession session) {
        if (Objects.nonNull(serviceCompose)) {
            serviceCompose.stop();
        }
    }
    
    static ServiceCompose create(ShenYuEngineConfigure configure) {
        if (configure.isRunOnHost()) {
            return new HostServiceCompose(configure.getHostConfigure());
        } else {
            return new DockerServiceCompose(configure.getDockerConfigure());
        }
    }
}
