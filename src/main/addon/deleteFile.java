package main.addon;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.reporters.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Action(name = "Delete File (if exist)",description = "Delete File (if exist)",summary = "This Action Deletes the File if it exists and returns True/False")
public class deleteFile implements WebAction {

    @Parameter(direction = ParameterDirection.INPUT, description = "Path to the Excel file")
    String filePath;
    @Parameter(direction = ParameterDirection.OUTPUT, description = "Process result (true/false)")
    boolean result;

    @Override
    public ExecutionResult execute(WebAddonHelper helper){
        Reporter reporter = helper.getReporter();

        File file = new File(filePath);
        try {
            result = Files.deleteIfExists(file.toPath());
            return ExecutionResult.PASSED;
        } catch (IOException e) {
            reporter.result(e.toString());
            result=false;
            return ExecutionResult.FAILED;
        }
    }
}
