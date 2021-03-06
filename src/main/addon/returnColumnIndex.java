package main.addon;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.reporters.Reporter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileInputStream;
import java.io.IOException;

@Action(name="Get Column Index From Excel",description = "Get Excel Column Index",summary = "This action takes a text as input (header) and returns the index number (column) containing the input text.")
public class returnColumnIndex implements WebAction {

    @Parameter(direction = ParameterDirection.INPUT, description = "Sheet Number in Excel (starting from one), Default 1",defaultValue = "1")
    int Sheet=1;
    @Parameter(direction = ParameterDirection.INPUT,description = "Path to the Excel file")
    String filePath;
    @Parameter(direction = ParameterDirection.INPUT,description = "The text to search in the Columns")
    String textToSearch;
    @Parameter(direction = ParameterDirection.OUTPUT,description = "Column Index in Excel (starting from one)")
    int Col;

    @Override
    public ExecutionResult execute(WebAddonHelper helper){
        String userprofile = System.getenv("USERPROFILE");
        filePath = filePath.replace("%USERPROFILE%", userprofile);
        if (Sheet<1){
            Sheet=1;
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
        Sheet sheet = workbook.getSheetAt(Sheet-1);
        Row row = sheet.getRow(0);
        int cellCount = row.getPhysicalNumberOfCells();
        for (int i = 0; i <cellCount ; i++) {
            if (row.getCell(i).toString().equalsIgnoreCase(textToSearch)) {
                Col=i+1;
                return ExecutionResult.PASSED;
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
        }
        reporter.result(String.format("No value found matching the requested text : \""+textToSearch+"\""));
        return ExecutionResult.FAILED;
    }

}