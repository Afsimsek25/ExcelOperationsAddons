package main.addon;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.reporters.Reporter;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


@Action(name = "Update a specific cell in the Excel file",description = "Update a specific cell in the Excel file",summary = "Updates a Excel file at specified row and column")
public class updateCell implements WebAction {
    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file to update")
    String filePath;
    @Parameter(direction = ParameterDirection.INPUT, description = "Sheet Number in Excel (starting from one), Default 1", defaultValue = "1")
    int Sheet=1;
    @Parameter(direction = ParameterDirection.INPUT, description = "Column in Excel (starting from one)")
    int Col;
    @Parameter(direction = ParameterDirection.INPUT, description = "Row Index in Excel (starting from one)")
    int Row;
    @Parameter(direction = ParameterDirection.INPUT, description = "Value to update")
    String value;

    @Override
    public ExecutionResult execute(WebAddonHelper helper)throws FailureException {
        if (Sheet<1){
            Sheet=1;
        }else{
            Sheet-=1;
        }
        Reporter reporter = helper.getReporter();
        Workbook workbook = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception ignored) {
        }
        assert workbook != null;
        Sheet sheet = workbook.getSheetAt(Sheet);
        try {
            Cell cell2Update = sheet.getRow(Row-1).getCell(Col-1);
            cell2Update.setCellValue(value);

        }catch (Exception e){
            reporter.result(e.toString());
            return ExecutionResult.FAILED;
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            reporter.result(e.toString());
            return ExecutionResult.FAILED;
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            reporter.result(e.toString());
            return ExecutionResult.FAILED;
        }
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            reporter.result(e.toString());
            return ExecutionResult.FAILED;
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            reporter.result(e.toString());
            return ExecutionResult.FAILED;
        }
        return ExecutionResult.PASSED;
    }
}
