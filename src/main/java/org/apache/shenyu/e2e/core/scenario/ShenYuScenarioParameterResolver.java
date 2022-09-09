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

package org.apache.shenyu.e2e.core.scenario;

import org.apache.shenyu.e2e.core.annotation.ShenYuScenario;
import org.apache.shenyu.e2e.core.annotation.ShenYuScenarioParameter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.util.AnnotationUtils;

public class ShenYuScenarioParameterResolver implements ParameterResolver {
    
    private final ShenYuScenarioSpec scenarioSpec;
    
    public ShenYuScenarioParameterResolver(ShenYuScenarioSpec scenarioSpec) {
        this.scenarioSpec = scenarioSpec;
    }
    
    
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return AnnotationUtils.isAnnotated(parameterContext.getParameter().getType(), ShenYuScenarioParameter.class);
    }
    
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return scenarioSpec.getByType(parameterContext.getParameter().getType());
    }

//    @Override
//    public void beforeEach(ExtensionContext context) throws Exception {
//        ShenYuScenarioSupplier supplier = getShenYuScenarioSupplier(context);
//        if (supplier.hasNext()) {
//            // do something here...
//        }
//        getStore(context).put(ShenYuScenarioSpec.class, supplier.next());
//    }
//
//    static Store getStore(ExtensionContext context) {
//        return context.getStore(Namespace.GLOBAL);
//    }
//
//    @Override
//    public void afterEach(ExtensionContext context) throws Exception {
//        getStore(context).remove(ShenYuScenarioSpec.class);
//    }
//
//    private static ShenYuScenarioSupplier getShenYuScenarioSupplier(ExtensionContext context) {
//        final Store store = context.getStore(NS.append(context.getRequiredTestMethod()));
//        return store.get(ShenYuScenarioSupplier.class, ShenYuScenarioSupplier.class);
//    }
}
