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

package org.apache.shenyu.e2e.testcase.common.specification;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.shenyu.e2e.common.IdGenerator;
import org.apache.shenyu.e2e.engine.scenario.specification.AfterEachSpec;
import org.apache.shenyu.e2e.engine.scenario.specification.BeforeEachSpec;
import org.apache.shenyu.e2e.engine.scenario.specification.CaseSpec;
import org.apache.shenyu.e2e.engine.scenario.specification.ScenarioSpec;
import org.junit.jupiter.api.Assertions;

@Getter
@AllArgsConstructor
public class ShenYuScenarioSpec implements ScenarioSpec {
    
    private final String name;
    
    private final BeforeEachSpec beforeEachSpec;
    
    private final CaseSpec caseSpec;
    
    private final AfterEachSpec afterEachSpec;
    
    public static ShenYuScenarioSpecBuilder builder() {
        return new ShenYuScenarioSpecBuilder();
    }
    
    public static class ShenYuScenarioSpecBuilder {
        private String name;
        private BeforeEachSpec beforeEachSpec = BeforeEachSpec.DEFAULT;
        private AfterEachSpec afterEachSpec = AfterEachSpec.DEFAULT;
        
        private CaseSpec caseSpec;
        
        public ShenYuScenarioSpecBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public ShenYuScenarioSpecBuilder beforeEachSpec(BeforeEachSpec beforeEachSpec) {
            this.beforeEachSpec = beforeEachSpec;
            return this;
        }
        
        public ShenYuScenarioSpecBuilder caseSpec(CaseSpec caseSpec) {
            this.caseSpec = caseSpec;
            return this;
        }
        
        public ShenYuScenarioSpecBuilder afterEachSpec(AfterEachSpec afterEachSpec) {
            this.afterEachSpec = afterEachSpec;
            return this;
        }
        
        public ShenYuScenarioSpec build() {
            Assertions.assertNotNull(caseSpec, "CaseSpec is required.");
            if (Strings.isNullOrEmpty(name)) {
                name = "shenyu-" + IdGenerator.generateTestId();
            }
            return new ShenYuScenarioSpec(name, beforeEachSpec, caseSpec, afterEachSpec);
        }
    }

//    public static void main(String[] args) {
//        new Builder().newScenario("").before().checker()
//    }
//
//    static class Builder {
//
//        Scenario newScenario(String name) {
//            return new Scenario();
//        }
//
//        ShenYuScenarioSpec build() {
//
//        }
//
//        public static class Scenario {
//
//            Builder parent;
//
//            Before before() {
//                return new Before();
//            }
//
//            Case testCase() {
//                return new Case();
//            }
//
//            After after() {
//                return new After();
//            }
//
//            protected void x() {
//
//            }
//
//            Scenario newScenario(String name) {
//                return parent.newScenario(name);
//            }
//
//            public static class Before {
//                Scenario parent;
//                ShenYuBeforeEachSpecBuilder delegate;
//
//                Before checker() {
//                    return this;
//                }
//
//                Before waiting() {
//                    return this;
//                }
//
//                Before addSelectorAndRule(SelectorData selector, RuleData rule) {
//                    return this;
//                }
//
//                Case testCase() {
//                    return parent.testCase();
//                }
//
//                After after() {
//                    return parent.after();
//                }
//
//                Scenario newScenario(String name) {
//                    return parent.newScenario(name);
//                }
//
//            }
//
//            public static class Case {
//                Scenario parent;
//                ShenYuTestCaseSpecBuilder delegate;
//
//                Case add(Verifier verifier) {
//                    return this;
//                }
//
//                Before before() {
//                    return parent.before();
//                }
//
//                After after() {
//                    return parent.after();
//                }
//
//                Scenario newScenario(String name) {
//                    return parent.newScenario(name);
//                }
//            }
//            public static class After {
//                Scenario parent;
//                ShenYuAfterEachSpec.ShenYuAfterEachSpecBuilder delegate;
//
//                Before before() {
//                    return parent.before();
//                }
//
//                Case testCase() {
//                    return parent.testCase();
//                }
//
//                Scenario newScenario(String name) {
//                    return parent.newScenario(name);
//                }
//            }
//        }
//    }
    
}
