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

@Action(name = "Get Data From Excel",description = "Get Data From Excel", summary = "This action takes index number (row, column) and returns the cell contents.")
public class getData implements WebAction {

    @Parameter(direction = ParameterDirection.INPUT, description = "Sheet Number in Excel (starting from one), Default 1",defaultValue = "1")
    int Sheet=1;
    @Parameter(direction = ParameterDirection.INPUT, description = "Row Index in Excel (starting from one)")
    int Row=5;
    @Parameter(direction = ParameterDirection.INPUT, description = "Column Index in Excel (starting from one)")
    int Col=6;
    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file")
    String filePath = "%USERPROFILE%/Downloads/Islemler.xlsx";
    @Parameter(direction = ParameterDirection.OUTPUT, description = "The value inside the column cells")
    String Value;



    @Override
    public ExecutionResult execute(WebAddonHelper helper){
        String userprofile = System.getenv("USERPROFILE");
        if (filePath.startsWith("%USERPROFILE%")){
            filePath =  filePath.replace("%USERPROFILE%",userprofile);
        }
        if (Sheet<1){
            Sheet=1;
        }
        Reporter reporter = helper.getReporter();
        Workbook workbook = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception ex) {
            reporter.result(ex.toString());
        }
        assert workbook != null;
        Sheet sheet = workbook.getSheetAt(Sheet-1);
        Value=" ";
        Row row = sheet.getRow(Row-1);
        Value+=row.getCell(Col-1);
        Value=Value.trim();
        System.out.println(Value);
        if (Value.length()<=0){
            Value="EMPTY";
            reporter.result("No value found in the provided index: \"" + Col + Row+"\"");
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