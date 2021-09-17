package main.addon;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

import java.io.File;

@Action(name = "Wait Until File Exist",description = "Wait Until File Exist",summary = "This Action Waits Until The Downloaded File Appears In The System")
public class waitUntilFileExist implements WebAction {

    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file")
    String filePath;
    @Parameter(direction = ParameterDirection.INPUT, description = "timeout in milliseconds")
    int Timeout;
    @Parameter(direction = ParameterDirection.OUTPUT, description = "Process result (true/false)")
    boolean result;


    @Override
    public ExecutionResult execute(WebAddonHelper helper){
        File file = new File(filePath);
        result = file.exists();
        for (int i = 0; i <Timeout;) {
            if (result){
                return ExecutionResult.PASSED;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        return ExecutionResult.FAILED;
    }
}