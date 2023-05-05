package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import java.text.SimpleDateFormat;
import java.util.Date;

@Listeners
public class ExtentReportManager implements ITestListener {
    // TODO: THE XML FILE WILL INTEGRATE THE Tests AND ExtentReportManager
    // The xml will run parallely the Test class and ExtentReportManager class, so we can create the report
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

//        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); //specify location of reports
        sparkReporter = new ExtentSparkReporter("C:\\Users\\Dani\\Desktop\\PRESENTATION_FEBRUARY\\PetStoreAutomation\\reports"); //specify location of reports
//
        System.out.println(".\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject"); // title of report
        sparkReporter.config().setReportName("Pet Store Users API"); // name of report
        sparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", "Pet Store Users API");
        extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment", "QA");
        extentReports.setSystemInfo("user", "Ganio");
    }

    public void onTestSuccess(ITestResult testResult) {
        extentTest = extentReports.createTest(testResult.getName());
        extentTest.assignCategory(testResult.getMethod().getGroups());
        extentTest.createNode(testResult.getName());
        extentTest.log(Status.PASS, "Test Passed");
        extentReports.flush();
    }

    public void onTestFailure(ITestResult testResult) {
        extentTest = extentReports.createTest(testResult.getName());
        extentTest.assignCategory(testResult.getMethod().getGroups());
        extentTest.createNode(testResult.getName());
        extentTest.log(Status.FAIL, "Test Failed");
        extentTest.log(Status.FAIL, testResult.getThrowable().getMessage());
        extentReports.flush();
    }

    public void onTestSkip(ITestResult testResult) {
        extentTest = extentReports.createTest(testResult.getName());
        extentTest.assignCategory(testResult.getMethod().getGroups());
        extentTest.createNode(testResult.getName());
        extentTest.log(Status.SKIP, "Test Skipped");
        extentTest.log(Status.SKIP, testResult.getThrowable().getMessage());
        extentReports.flush();
    }
}
