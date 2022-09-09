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

import org.apache.shenyu.e2e.core.scenario.AfterEachSpec;
import org.apache.shenyu.e2e.core.scenario.BeforeEachSpec;
import org.apache.shenyu.e2e.core.scenario.ShenYuScenarioProvider;
import org.apache.shenyu.e2e.core.scenario.ShenYuScenarioSpec;
import org.apache.shenyu.e2e.core.scenario.TestCaseSpec;

import java.util.List;

public class MyShenYuScenarioProvider implements ShenYuScenarioProvider {
    @Override
    public List<ShenYuScenarioSpec> get() {
        return List.of(new C("Xx"), new C("Yx"));
    }
    
    
    static class C implements ShenYuScenarioSpec {
        
        String name;
        
        C(String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return name;
        }
        
        @Override
        public BeforeEachSpec getBeforeEachSpec() {
            return new BeforeEachSpec() {
            };
        }
        
        @Override
        public TestCaseSpec getTestCaseSpec() {
            return new TestCaseSpec() {
                @Override
                public String getName() {
                    return null;
                }
                
                @Override
                public <T> T get(Class<T> type) {
                    return null;
                }
            };
        }
        
        @Override
        public AfterEachSpec getAfterEachSpec() {
            return new AfterEachSpec() {
            };
        }

//        Object rst = 13213;
//
//        @Override
//        public <T> T getByType(Class<T> type) {
//            return type.cast(rst);
//        }
    }
}
