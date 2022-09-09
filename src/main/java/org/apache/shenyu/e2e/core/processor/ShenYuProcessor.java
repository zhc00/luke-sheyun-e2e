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

//public abstract class ShenYuProcessor<A extends Annotation> {
//    @Getter(AccessLevel.PROTECTED)
//    private final Class<A> annotationClass;
//
//    protected ShenYuProcessor(Class<A> annotationClass) {
//        this.annotationClass = annotationClass;
//    }
//
//    public void process(@Nullable Store store, Object target, Field field) {
//        Assertions.assertDoesNotThrow(() -> {
//                    Object evaluated = evaluate(store, field, field.getAnnotation(annotationClass));
//                    field.setAccessible(true);
//                    field.set(target, evaluated);
//                },
//                "failed to " // FIXME
//        );
//    }
//
//    protected abstract Object evaluate(Store store, Field field, A annotation) throws Exception;
//
//
//    public static void resolve(Store store, Class<?> testClass) {
//        // 找到所有需要注入的字段
//        List<Field> fields = Lists.newArrayList();
//
//        fields.forEach(field -> {
//            List<? extends ShenYuProcessor<?>> processors = Arrays.stream(field.getAnnotations())
//                    .filter(annotation -> annotation.annotationType().isAnnotationPresent(ShenYuProcess.class))
//                    .map(annotation -> {
//                        ShenYuProcess config = annotation.annotationType().getAnnotation(ShenYuProcess.class);
//                        ShenYuProcessor<?> processor = createProcessor(config.processor());
//                        processor.resolve(store, field);
//                        return processor;
//                    })
//                    .collect(Collectors.toList());
//
//            if (!processors.isEmpty()) {
////                store.put(field, new Processors(field, processors));
//            }
//        });
//    }
//
//    private void resolve(Store store, Field field) {
//
//    }
//
//    public static void evaluate(Store store, Class<?> testClass, Object instance) {
//        // field, processor
//        getAllFields(testClass).stream()
//                .map(store::get)
//                .filter(Objects::nonNull)
//                .map(p -> (Processors) p)
//                .forEach(p -> {
//                    p.evaluate(instance);
//                });
//    }
//
//    private static List<Field> getAllFields(Class<?> testClass) {
//        return Arrays.asList(testClass.getFields());
//    }
//
//    @Getter
//    public static class Tuple {
//        String key;
//        String value;
//    }
//
//    public static void resolveAnnotations(Store store, Object instance, Field field) {
//        Arrays.stream(field.getAnnotations())
//                .filter(annotation -> annotation.annotationType().isAnnotationPresent(ShenYuProcess.class))
//                .map(annotation -> {
//                    ShenYuProcess config = annotation.annotationType().getAnnotation(ShenYuProcess.class);
//                    ShenYuProcessor<?> processor = createProcessor(config.processor());
//
////                    processor.resolve(store, )
//                    store.put(annotation.annotationType(), field);
//
//                    processor.process(store, instance, field);
//
//                    return new Tuple();
//                })
//                .collect(Collectors.toMap(Tuple::getKey, Tuple::getValue));
//    }
//
//    public static void xx(Field field, BiConsumer<Field, Object> consumer) {
//        Arrays.stream(field.getAnnotations())
//                .filter(annotation -> annotation.annotationType().isAnnotationPresent(ShenYuProcess.class))
//                .forEach(annotation -> {
//                    ShenYuProcess config = annotation.annotationType().getAnnotation(ShenYuProcess.class);
//                    ShenYuProcessor processor = createProcessor(config.processor());
//                    try {
//                        Object evaluated = processor.evaluate(null, field, annotation);
//                        consumer.accept(field, evaluated);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//    }
//
//}
