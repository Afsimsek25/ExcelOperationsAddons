package main.addon;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.reporters.Reporter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;

@Action(name = "Get Sum Of The Column Values", description = "Get Sum Of The Column Values", summary = "This Action Return The Sum Of The Column Values")
public class getSumOfTheColumnValues implements WebAction {

    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file")
    String filePath;
    @Parameter(direction = ParameterDirection.INPUT, description = "Sheet Number in Excel (starting from one), Default 1", defaultValue = "1")
    int Sheet;
    @Parameter(direction = ParameterDirection.INPUT, description = "Column Index in Excel (starting from one)")
    int Col;
    @Parameter(direction = ParameterDirection.OUTPUT, description = "The value inside the column cells")
    double columnValue;

    @Override
    public ExecutionResult execute(WebAddonHelper helper){
        String userprofile = System.getenv("USERPROFILE");
        filePath = filePath.replace("%USERPROFILE%", userprofile);
        if (Sheet <=0) {
            Sheet = 1;
        }
        Reporter reporter = helper.getReporter();
        Workbook workbook = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            reporter.result(e.toString());
        }
        assert workbook != null;
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(Sheet - 1);
        int rowCount = sheet.getPhysicalNumberOfRows();

        columnValue=0.0;
        try {
            for (int i = 1; i < rowCount - 1; i++) {
                Row row = sheet.getRow(i);
                double currentValue = row.getCell(Col - 1).getNumericCellValue();
                columnValue += currentValue;
            }

        }catch (Exception e){
            reporter.result("Satır 54 "+e);
            return ExecutionResult.FAILED;
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            reporter.result(e.toString());
            return ExecutionResult.FAILED;
        }
        try {
            workbook.close();
        } catch (IOException e) {
            reporter.result(e.toString());
        }
        return ExecutionResult.PASSED;
    }
}