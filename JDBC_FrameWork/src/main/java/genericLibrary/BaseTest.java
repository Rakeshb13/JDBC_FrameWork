package genericLibrary;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

/**
 * @author [Rakesh B]
 */
public class BaseTest implements FrameWorkConstant{
	
	
	public WebDriver driver;
	protected Connection connection;
	public static Statement statement ;
	public DataUtility data_Utility;
	
	@BeforeSuite
	public void beforeSuite() throws SQLException, FileNotFoundException
	{
		data_Utility = new DataUtility();
		connection = DriverManager.getConnection(data_Utility.getDataFromProperties(dbUrl_Key), data_Utility.getDataFromProperties(dbUserName_Key), data_Utility.getDataFromProperties(dbPassword_Key));
		statement = connection.createStatement();
	}
	
	@BeforeClass
	public void launchbrowser() throws FileNotFoundException
	{
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit_TimeOut));
		driver.get(data_Utility.getDataFromProperties(testUrl_Key));
	}

	@AfterClass
	public void closeBrowser()
	{
		driver.close();
	}
	
	@AfterSuite
	public void afterSuite() throws SQLException
	{
		connection.close();
	}
}
