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


@Action(name = "Read Column Values From Excel",description = "Read Excel Column",summary = "This action combines and returns all values in the index-given column.")
public class readColumnValue implements WebAction {
    @Parameter(direction = ParameterDirection.INPUT, description = "Sheet Number in Excel (starting from one), Default 1",defaultValue = "1")
    int Sheet=1;
    @Parameter(direction = ParameterDirection.INPUT, description = "Column Index in Excel (starting from one)")
    int Col;
    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file")
    String filePath;
    @Parameter(direction = ParameterDirection.OUTPUT, description = "The value inside the column cells")
    String columnValue;

    @Override
    public ExecutionResult execute(WebAddonHelper helper) throws FailureException {
        if (Sheet<1){
            Sheet=1;
        }
        Reporter reporter = helper.getReporter();
        Workbook workbook = null;
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception ex) {
        }
        assert workbook != null;
        Sheet sheet = workbook.getSheetAt(Sheet-1);
        int rowCount = sheet.getPhysicalNumberOfRows();
        columnValue=" ";
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);

            columnValue+=row.getCell(Col-1) + ",";
            columnValue=columnValue.trim();
            if (row.getCell(Col-1).toString().length()<=0) {
                columnValue="EMPTY";
                reporter.result(String.format("No value found in the provided index: \"" + Col + "\""));
                return ExecutionResult.FAILED;
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
