package main.runners;

import io.testproject.java.classes.DriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.java.sdk.v2.drivers.WebDriver;
import main.addon.deleteFile;

public class deleteFileRunners {
    private  final static String devToken="wThDxIHw4zNaCydjAhcfe81WrQPYlMBFI7FPwUwV8M41";
    public static void main(String[] args)  throws Exception{
        DriverSettings driverSettings = new DriverSettings(DriverType.Chrome);
        try(Runner runner = new Runner(devToken,driverSettings)){
            deleteFile deleteFile = new deleteFile();
            WebDriver driver  =runner.getDriver(deleteFile);
            runner.run(deleteFile);
        }
    }
}
