package testScripts;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import elementRepository.HomePage;
import elementRepository.LoginPage;
import genericLibrary.BaseTest;
import genericLibrary.DataUtility;

public class TC01_LoginTest extends BaseTest{
	
	
	@Test(dataProvider = "LoginData")
	public void loginTestCase(String email, String password)
	{
		HomePage home_page= new HomePage(driver);
		home_page.getLoginLink().click();
		
		Assert.assertEquals(driver.getTitle(),loginPage_Tile,"Login Page not Displayed");
		Reporter.log("Login Page Displayed...",true);
		
		LoginPage login_Page = new LoginPage(driver);
		login_Page.getEmailTextFeild().sendKeys(email);
		login_Page.getPasswordTextFeild().sendKeys(password);
		login_Page.getLoginButton().click();
		
		
		Assert.assertEquals(driver.getTitle(), "Demo Web Shop");
		Reporter.log("Login Test Case Pass...", true);
		
		home_page.getLogoutLink().click();
		
		
		//System.out.println(email + " - "+ password);
	}
	
	
	@DataProvider(name="LoginData")
	public Object[][] dataSupply() throws EncryptedDocumentException, IOException, SQLException
	{
		return data_Utility.getLoginCredentials();
	}

}
