package com.saucedemo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {
    private static final String EXCEL_PATH = "test-cases/testCases.xlsx";
    
    public static class TestCaseData {
        private String testCaseId;
        private String module;
        private String testScenario;
        private String testSteps;
        private String testData;
        private String expectedResult;
        private String priority;

        public TestCaseData(String testCaseId, String module, String testScenario, 
                           String testSteps, String testData, String expectedResult, String priority) {
            this.testCaseId = testCaseId;
            this.module = module;
            this.testScenario = testScenario;
            this.testSteps = testSteps;
            this.testData = testData;
            this.expectedResult = expectedResult;
            this.priority = priority;
        }

        // Getters
        public String getTestCaseId() { return testCaseId; }
        public String getModule() { return module; }
        public String getTestScenario() { return testScenario; }
        public String getTestSteps() { return testSteps; }
        public String getTestData() { return testData; }
        public String getExpectedResult() { return expectedResult; }
        public String getPriority() { return priority; }
        
        public Map<String, String> parseTestData() {
            Map<String, String> dataMap = new HashMap<>();
            if (testData != null && !testData.isEmpty() && !testData.equals("nan")) {
                String[] pairs = testData.split(",");
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }
            }
            return dataMap;
        }
    }

    public static List<TestCaseData> getAllTestCases() {
        List<TestCaseData> testCases = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell idCell = row.getCell(0);
                    Cell moduleCell = row.getCell(1);
                    Cell scenarioCell = row.getCell(2);
                    Cell stepsCell = row.getCell(3);
                    Cell dataCell = row.getCell(4);
                    Cell expectedCell = row.getCell(5);
                    Cell priorityCell = row.getCell(6);
                    
                    String testCaseId = getCellValue(idCell);
                    String module = getCellValue(moduleCell);
                    String testScenario = getCellValue(scenarioCell);
                    String testSteps = getCellValue(stepsCell);
                    String testData = getCellValue(dataCell);
                    String expectedResult = getCellValue(expectedCell);
                    String priority = getCellValue(priorityCell);
                    
                    if (testCaseId != null && !testCaseId.isEmpty()) {
                        testCases.add(new TestCaseData(testCaseId, module, testScenario, 
                                                     testSteps, testData, expectedResult, priority));
                    }
                }
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + EXCEL_PATH, e);
        }
        
        return testCases;
    }

    public static TestCaseData getTestCaseById(String testCaseId) {
        return getAllTestCases().stream()
                .filter(tc -> tc.getTestCaseId().equals(testCaseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test case not found: " + testCaseId));
    }

    public static List<TestCaseData> getTestCasesByModule(String module) {
        return getAllTestCases().stream()
                .filter(tc -> tc.getModule().equalsIgnoreCase(module))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
