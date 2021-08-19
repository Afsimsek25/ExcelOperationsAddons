package main.Runners;

import io.testproject.java.classes.DriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.java.sdk.v2.drivers.WebDriver;
import main.Addon.getData;

public class getDataRunner {
    private  final static String devToken="4FPXDZg2cWzyPlh4CL9QAK1ci4rHDCS-7uD_A-YUDeY1";
    public static void main(String[] args)  throws Exception{
        DriverSettings driverSettings = new DriverSettings(DriverType.Chrome);
        try(Runner runner = new Runner(devToken,driverSettings)){
            getData getData = new getData();
            WebDriver driver = runner.getDriver(getData);
            runner.run(getData);
        }

    }
}