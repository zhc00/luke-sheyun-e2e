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

package org.apache.shenyu.e2e.test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class B {
    
    @Test
    void test() {
//        System.out.printf("");
//
//        String format = String.format("| %-15s | %-4s |", "xx", "yy");
//        System.out.println(format);

//        System.out.printf("%%-%ds", 1);
        
        TableView table = new TableView("column 1", "column 2", "column 3", "column 4");
        table.addRow("1", "a", "c", "ddd");
        System.out.println(table.print());
    }
    
    
    static class TableView {
        int sizeOfColumnsInRow = 0;
        List<Row> table = new ArrayList<>();
        
        int[] maxContentLengthOfColumns;
        
        private String[] headers;
        
        public TableView(String... headers) {
            this.headers = headers;
            this.maxContentLengthOfColumns = new int[headers.length];
            for (int i = 0; i < headers.length; i++) {
                maxContentLengthOfColumns[i] = Math.max(maxContentLengthOfColumns[i], headers[i].length());
            }
        }
        
        public TableView addRow(List<Object> row) {
            if (row.size() > headers.length) {
                //
            }
            sizeOfColumnsInRow = Math.max(sizeOfColumnsInRow, row.size());
            List<String> collect = row.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            table.add(new Row(collect));
            
            for (int i = 0; i < row.size(); i++) {
                maxContentLengthOfColumns[i] = Math.max(maxContentLengthOfColumns[i], collect.get(i).length());
            }
            return this;
        }
        
        public TableView addRow(Object... row) {
            addRow(List.of(row));
            return this;
        }
        
        public String print() {
            String[] template = new String[maxContentLengthOfColumns.length];
            
            StringBuilder separator = new StringBuilder("+");
            for (int i = 0; i < maxContentLengthOfColumns.length; i++) {
                int length = maxContentLengthOfColumns[i];
                separator.append("-".repeat(length + 2)).append("+");
                template[i] = String.format(" %%-%ds ", length);
            }
            separator.append(System.lineSeparator());
            
            StringBuilder tableBuilder = new StringBuilder();
            tableBuilder.append(printHeaders(headers, template, separator));
            
            for (Row row : table) {
                tableBuilder.append(printRow(row.getColumns(), template, separator));
            }
            
            return tableBuilder.toString();
        }
        
        private StringBuilder printHeaders(String[] headers, String[] template, StringBuilder separator) {
            StringBuilder builder = new StringBuilder(separator);
            for (int i = 0; i < headers.length; i++) {
                builder.append("|").append(String.format(template[i], headers[i]));
            }
            builder.append("|").append(System.lineSeparator()).append(separator);
            return builder;
        }
        
        private StringBuilder printRow(List<String> row, String[] template, StringBuilder separator) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int i = 0; i < row.size(); i++) {
                rowBuilder.append("|").append(String.format(template[i], row.get(i)));
            }
            rowBuilder.append("|").append(System.lineSeparator()).append(separator);
            return rowBuilder;
        }
        
        static class Row {
            List<String> row;
            
            public Row(List<String> row) {
                this.row = row;
            }
            
            
            public List<String> getColumns() {
                return row;
            }
        }
        
    }
}
