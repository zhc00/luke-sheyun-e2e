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

import lombok.SneakyThrows;
import org.apache.shenyu.e2e.client.admin.AdminClient;
import org.apache.shenyu.e2e.config.ShenYuEngineConfigure.Mode;
import org.apache.shenyu.e2e.core.annotation.ShenYuScenario;
import org.apache.shenyu.e2e.core.annotation.ShenYuTest;
import org.apache.shenyu.e2e.core.scenario.AfterEachSpec;
import org.apache.shenyu.e2e.core.scenario.BeforeEachSpec;
import org.apache.shenyu.e2e.core.scenario.ShenYuScenarioProvider;
import org.apache.shenyu.e2e.core.scenario.TestCaseSpec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.platform.commons.support.ReflectionSupport;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

//@ExtendWith(ShenYuScenarioParameterResolver.class)
//@ExtendWith(ShenYuScenarioInvocationContextProvider.class)
@ShenYuTest(
        mode = Mode.host,
        services = {
        
        },
        dockerComposeFile = "classpath:./docker-compose.yml"
)
public class MukeTest {
    
    @BeforeEach
    void each(BeforeEachSpec x) {
        System.out.println(">>>>>>>>>>>..");
    }
    
    @AfterEach
    void after(AfterEachSpec x) {
        System.out.println("<<<<<<<<<<<<<..");
    }
    
    @ShenYuScenario(provider = MyShenYuScenarioProvider.class)
    void xx(AdminClient client, TestCaseSpec x) {
        System.out.println(x);
    }
    
//    @RegisterExtension
    static Extension e = new ParameterResolver() {
        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            return !extensionContext.getRequiredTestMethod().isAnnotationPresent(ShenYuScenario.class);
        }
        
        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext context) throws ParameterResolutionException {
            System.out.println(">>>>>>>>>>.. no execution");
            System.out.println(context.getRequiredTestMethod());
            
            context.getTestMethod().ifPresent(System.out::println);
            
            Store store = context.getStore(Namespace.create(context.getRequiredTestClass()));
            List<? extends Arguments> result = store.get(ShenYuScenario.class, List.class);
            System.out.println(result);
            return result.get(0).get()[0];
        }
    };
    
    //    @RegisterExtension
    static Extension ee = new TestTemplateInvocationContextProvider() {
        @SneakyThrows
        @Override
        public boolean supportsTestTemplate(ExtensionContext context) {
            Method method = context.getRequiredTestMethod();
            if (method.isAnnotationPresent(ShenYuScenario.class)) {
                Class<? extends ShenYuScenarioProvider> providerClass = method.getAnnotation(ShenYuScenario.class).provider();
                ShenYuScenarioProvider provider = ReflectionSupport.newInstance(providerClass);
                
                Store store = context.getStore(Namespace.create(context.getRequiredTestClass()));
                store.put(ShenYuScenario.class, provider.get());
            }
            return true;
        }
        
        @Override
        public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
            return Stream.<TestTemplateInvocationContext>builder().add(new TestTemplateInvocationContext() {
                @Override
                public String getDisplayName(int invocationIndex) {
                    return "my-xx";
                }
            }).build();
        }
    };
    
}
