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

package org.apache.shenyu.e2e.core.processor;

import com.google.common.annotations.VisibleForTesting;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.shenyu.e2e.config.ShenYuEngineConfigure;
import org.apache.shenyu.e2e.core.annotation.ShenYuE2E;
import org.apache.shenyu.e2e.core.annotation.ShenYuLocal;
import org.apache.shenyu.e2e.core.annotation.ShenYuProcess;
import org.apache.shenyu.e2e.core.annotation.ShenYuValue;
import org.assertj.core.util.Lists;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShenYuAnnotationProcessors {
    
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    public static class FieldAnnotationProcessors {
        private Field field;
        private List<? extends ShenYuAnnotationProcessor> processors;
        
        public void preprocess(Store store) {
            processors.forEach(p -> p.preprocess(store));
        }
        
        public void process(Store store, Object instance) {
            processors.forEach(p -> p.process(store, instance, field));
        }
    }
    
    public static ShenYuEngineConfigure resolveTestClassAnnotations(Class<?> testClass) {
        Assumptions.assumeTrue(testClass.isAnnotationPresent(ShenYuE2E.class), "@ShenYuE2E not found");
        ShenYuE2E config = getAnnotationProxy(testClass.getAnnotation(ShenYuE2E.class));
        boolean isLocal = testClass.isAnnotationPresent(ShenYuLocal.class) || config.isLocal();
//        return new ShenYuEngineConfigure(isRunOnHost, config.value());
        return null;
    }
    
    public static List<FieldAnnotationProcessors> resolveFieldAnnotations(Class<?> testClass) {
        return getAllFields(testClass).stream()
                .map(field -> {
                    List<? extends ShenYuAnnotationProcessor> processors = Arrays.stream(field.getAnnotations())
                            .filter(annotation -> annotation.annotationType().isAnnotationPresent(ShenYuProcess.class))
                            .map(annotation -> {
                                ShenYuProcess config = annotation.annotationType().getAnnotation(ShenYuProcess.class);
                                return createProcessor(config.processor(), annotation);
                            })
                            .collect(Collectors.toList());
                    return new FieldAnnotationProcessors(field, processors);
                }).collect(Collectors.toList());
    }
    
    public static List<Field> getAllFields(Class<?> testClass) {
        List<Field> fields = Lists.newArrayList(testClass.getDeclaredFields());
        fields.addAll(Lists.newArrayList(testClass.getFields()));
        return fields;
    }
    
    public static List<Method> getAllMethods(Class<?> testClass) {
        List<Method> methods = Lists.newArrayList(testClass.getDeclaredMethods());
        methods.addAll(Lists.newArrayList(testClass.getMethods()));
        return methods;
    }
    
    
    private static <A extends Annotation, P extends ShenYuAnnotationProcessor<A>> P createProcessor(Class<P> clazz, A annotation) {
        return Assertions.assertDoesNotThrow(() -> {
            Constructor<P> constructor = clazz.getConstructor(annotation.annotationType());
            return constructor.newInstance(annotation);
        }, "");
    }
    
    @VisibleForTesting // FIXME
    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A getAnnotationProxy(@NotNull A config) {
        Object instance = Proxy.newProxyInstance(
                ShenYuValue.class.getClassLoader(),
                new Class[]{config.annotationType()},
                (proxy, method, args) -> {
                    if (method.isAnnotationPresent(ShenYuValue.class)) {
                        Object value = ShenYuValueProcessor.evaluate(
                                method.getAnnotation(ShenYuValue.class),
                                method.getReturnType()
                        );
                        if (Objects.nonNull(value)) {
                            return value;
                        }
                    }
                    return method.invoke(config);
                });
        return (A) config.annotationType().cast(instance);
    }
    
}
