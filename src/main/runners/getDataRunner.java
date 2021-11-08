package main.runners;

import io.testproject.java.classes.DriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.java.sdk.v2.drivers.WebDriver;
import main.addon.getData;

public class getDataRunner {
    private  final static String devToken="R8bQ7C2-_WuN5axvQF8WZdDXzzql5r2-2A-DGC3C0pw1";
    public static void main(String[] args)  throws Exception{
        DriverSettings driverSettings = new DriverSettings(DriverType.Chrome);
        try(Runner runner = new Runner(devToken,driverSettings)){
            getData getData = new getData();
            WebDriver driver = runner.getDriver(getData);
            runner.run(getData);
        }

    }
}
