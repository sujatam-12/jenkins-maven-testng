package com.selenium.selenium_maven.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReport() {

        if (extent == null) {

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter("test-output/ExtentReport.html");

            /*--------MI-3-----------*/
           reporter.config().setReportName("Automation Test Report");
            reporter.config().setDocumentTitle("Selenium E2E product Testing");
           

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            
            
            
            /*--------MI-3-----------*/

            extent.setSystemInfo("Project", "Selenium Maven");
            extent.setSystemInfo("Browser", "Chrome / Firefox");
            extent.setSystemInfo("Tester", "Sujata");
        }

        return extent;
    }
}



/*package com.selenium.selenium_maven.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReport() {

        if (extent == null) {

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter("test-output/ExtentReport.html");

            reporter.config().setReportName(
                    "Milestone-3 | AutomationExercise Search & Browse");
            reporter.config().setDocumentTitle(
                    "AutomationExercise | Selenium Test Execution");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            extent.setSystemInfo("Project", "AutomationExercise");
            extent.setSystemInfo("Framework", "Selenium + TestNG + POM");
            extent.setSystemInfo("Tester", "Sujata");
        }
        return extent;
    }
}
*/
