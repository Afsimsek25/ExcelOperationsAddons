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

@Action(name = "Get Data From Excel",description = "Get Data From Excel", summary = "Get Data value from an Excel file using specific Column and Row index")
public class getData implements WebAction {

    @Parameter(direction = ParameterDirection.INPUT, description = "Sheet Number in Excel (starting from one), Default 1",defaultValue = "1")
    int Sheet;
    @Parameter(direction = ParameterDirection.INPUT, description = "Row Index in Excel (starting from one)")
    int Row;
    @Parameter(direction = ParameterDirection.INPUT, description = "Column Index in Excel (starting from one)")
    int Col;
    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file")
    String filePath;
    @Parameter(direction = ParameterDirection.OUTPUT, description = "The value inside the column cells")
    String Value;



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
        Value=" ";
        Row row = sheet.getRow(Row-1);
        Value+=row.getCell(Col-1);
        Value=Value.trim();
        if (Value.length()<=0){
            Value="EMPTY";
            reporter.result(String.format("No value found in the provided index: \"" + Col + Row+"\""));
            return ExecutionResult.FAILED;
        }
        return ExecutionResult.PASSED;

    }
}
