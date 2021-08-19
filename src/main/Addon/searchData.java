package main.Addon;

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

@Action(name = "Read Excel Column",description = "Read column value from an Excel file using specific index")
public class searchData implements WebAction {
    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file")
    String filePath;
    @Parameter(direction = ParameterDirection.INPUT, description = "Sheet Number in Excel (starting from one)")
    int Sheet;
    @Parameter(direction = ParameterDirection.INPUT, description = "Searched Text in The All Data")
    String TextToSearch;
    @Parameter(direction = ParameterDirection.OUTPUT, description = "Row Index in Excel (starting from one)")
    int Row;
    @Parameter(direction = ParameterDirection.OUTPUT, description = "Column Index in Excel (starting from one)")
    int Col;

    @Override
    public ExecutionResult execute(WebAddonHelper helper) throws FailureException {

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

        for(int i=0; i< rowCount; i++)
        {
            Row row= sheet.getRow(i);
            int cellCount=row.getPhysicalNumberOfCells();
            for(int j=0; j < cellCount;j++ )
            {
                if (row.getCell(i).toString().equalsIgnoreCase(TextToSearch)){
                    Col = i+1;
                    Row = j+1;
                    return ExecutionResult.PASSED;
                }else {
                    reporter.result(String.format("No value found matching the requested text : \""+TextToSearch+"\""));
                    return ExecutionResult.FAILED;
                }
            }
        }


        return null;
    }
}
