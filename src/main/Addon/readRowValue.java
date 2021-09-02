package main.Addon;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.reporters.Reporter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;

@Action(name = "Read Row Values From Excel", description = "Read Excel Row", summary = "This action combines and returns all values in the index-given row.")
public class readRowValue implements WebAction {
    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file")
    String filePath;
    @Parameter(direction = ParameterDirection.INPUT, description = "Sheet Number in Excel (starting from one), Default 1", defaultValue = "1")
    int Sheet = 1;
    @Parameter(direction = ParameterDirection.INPUT, description = "Row Index in Excel (starting from one)")
    int Row;
    @Parameter(direction = ParameterDirection.OUTPUT, description = "The values inside the cells in the row")
    String rowValue;

    @Override
    public ExecutionResult execute(WebAddonHelper helper) throws FailureException {
        if (Sheet < 1) {
            Sheet = 1;
        }
        Reporter reporter = helper.getReporter();
        Workbook workbook = null;
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception ex) {
        }
        assert workbook != null;
        Sheet sheet = workbook.getSheetAt(Sheet - 1);
        rowValue = " ";
        Row row = sheet.getRow(Row);
        int cellCount = row.getPhysicalNumberOfCells();


        for (int i = 0; i <cellCount; i++) {
            if (row.getCell(i).toString().length() <= 0) {
                rowValue += " " + ",";
                rowValue = rowValue.trim();
            }else {
                rowValue += row.getCell(i) + ",";
                rowValue = rowValue.trim();
            }
        }
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ExecutionResult.PASSED;
    }
}
