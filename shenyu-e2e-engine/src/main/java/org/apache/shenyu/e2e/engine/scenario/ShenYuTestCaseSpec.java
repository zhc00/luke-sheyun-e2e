///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package org.apache.shenyu.e2e.engine.scenario;
//
//import org.apache.shenyu.e2e.client.admin.model.ResourcesData;
//import org.apache.shenyu.e2e.engine.scenario.specification.AfterEachSpec;
//import org.apache.shenyu.e2e.engine.scenario.specification.BeforeEachSpec;
//import org.apache.shenyu.e2e.engine.scenario.function.Checker;
//import org.apache.shenyu.e2e.engine.scenario.specification.CaseSpec;
//import org.apache.shenyu.e2e.engine.scenario.function.Waiting;
//
//public class ShenYuTestCaseSpec {
//
//
//    public static class ShenYuTestCaseSpecBuilder {
//
//        public ShenYuTestCaseSpecBuilder beforeEachSpec(BeforeEachSpec beforeEachSpec) {
//            return this;
//        }
//
//        public ShenYuTestCaseSpecBuilder testCaseSpec(CaseSpec testCaseSpec) {
//            return this;
//        }
//
//        public ShenYuTestCaseSpecBuilder AfterEachSpec(AfterEachSpec afterEachSpec) {
//            return this;
//        }
//
//    }
//
//
//    public static interface BeforeSpec {
//
//        Checker getChecker();
//
//        ResourcesData getResourcesData();
//
//        Waiting getWaitingTakeEffect();
//
//    }
//
//    public static class BeforeSpecBuilder {
//
//    }
//
//
//    public static void main(String[] args) {
////        new ShenYuTestCaseSpecBuilder().AfterEachSpec()
//    }
//}
