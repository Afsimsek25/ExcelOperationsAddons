package main.Runners;

import io.testproject.java.classes.DriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.java.sdk.v2.drivers.WebDriver;import main.Addon.getSumOfTheColumnValues;

public class sumOfTheColumnValuesRunner {
    private  final static String devToken="4FPXDZg2cWzyPlh4CL9QAK1ci4rHDCS-7uD_A-YUDeY1";
    public static void main(String[] args)  throws Exception{
        DriverSettings driverSettings = new DriverSettings(DriverType.Chrome);
        try(Runner runner = new Runner(devToken,driverSettings)){
            getSumOfTheColumnValues getSumOfTheColumnValues = new getSumOfTheColumnValues();
            WebDriver driver = runner.getDriver(getSumOfTheColumnValues);
            runner.run(getSumOfTheColumnValues);
        }

    }
}
