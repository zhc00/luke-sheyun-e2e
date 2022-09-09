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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
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

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Stream;

public class LukeTest {
    @RegisterExtension
    static Extension provider = new ParameterResolver() {
        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            return true;
        }
        
        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            System.out.println("parameter : " + extensionContext.getRequiredTestMethod().getName());
            Store store = extensionContext.getStore(Namespace.create(TestSpec.class));
            Type type = parameterContext.getParameter().getParameterizedType();
            System.out.println(type);
    
            CaseDescriptor x = store.get(CaseDescriptor.class, CaseDescriptor.class);
            if (type == BeforeSpec.class) {
                return x.beforeSpec;
            }
            if (type == AfterSpec.class) {
                return x.afterSpec;
            }
            return x.testSpec;
        }
    };
    @RegisterExtension
    static Extension x = new TestTemplateInvocationContextProvider() {
        @Override
        public boolean supportsTestTemplate(ExtensionContext context) {
            System.out.println("xxxxxxx " + context.getRequiredTestMethod());
            return true;
        }
        
        @Override
        public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
            Store store = context.getStore(Namespace.create(TestSpec.class));
            return Stream.<TestTemplateInvocationContext>builder()
                    .add(new TestTemplateInvocationContext() {
                        @Override
                        public String getDisplayName(int invocationIndex) {
                            store.put(CaseDescriptor.class, new CaseDescriptor("yy-" + invocationIndex));
                            return "xxx" + invocationIndex;
                        }
                    })
                    .add(new TestTemplateInvocationContext() {
                        @Override
                        public String getDisplayName(int invocationIndex) {
                            store.put(CaseDescriptor.class, new CaseDescriptor("yy-" + invocationIndex));
                            return "xxx" + invocationIndex;
                        }
                    })
                    .build();
        }
    };
    
    @BeforeAll
    static void setup() {
    }
    
    @BeforeEach
    void before(BeforeSpec spec) {
        System.out.println("each" + spec);
    }
    
    
    @TestTemplate
    void testa(TestSpec spec) {
        System.out.println("test");
    }
    
    @AfterEach
    void after(AfterSpec spec) {
        
        System.out.println("after");
    }
    
    //    @ShenYuScenario
//    public class ChildA {
//
//        @ShenYuScenarioSetup
//        void setup() {
//            System.out.println("sub setup");
//        }
//
//        @Test
//        void test() {
//            System.out.println("sub test");
//        }
//
//        @ShenYuScenarioTeardown
//        void teardown() {
//            System.out.println("sub cleanup");
//        }
//    }
//
    @AfterAll
    static void cleanup() {
        System.out.println("cleanup");
    }
    
    public static class CaseDescriptor {
        BeforeSpec beforeSpec;
        TestSpec testSpec;
        AfterSpec afterSpec;
        
        public CaseDescriptor(String id) {
            beforeSpec = new X("before=" + id);
            testSpec = new X("test=" + id);
            afterSpec = new X("after=" + id);
        }
    }
    
    static class X implements BeforeSpec, TestSpec, AfterSpec {
        @Override
        public String toString() {
            return "X{" +
                    "x='" + x + '\'' +
                    '}';
        }
    
        public X() {
        }
        
        String x;
        
        public X(String x) {
            this.x = x;
        }
    }
    
    public interface BeforeSpec {
    }
    
    public interface TestSpec {
    }
    
    public interface AfterSpec {
    }
    
    Collection<CaseDescriptor> provider() {
        return null;
    }
}
