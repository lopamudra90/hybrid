package com.ranford.functionalities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import utility.screenshot;
import Excel.Excel_Class;
import PageLibrary.AdminPage;
import PageLibrary.BranchesPage;
import PageLibrary.GenericPage;
import PageLibrary.LoginPage;
import TestBase.Base;

public class Repository<extentreport> extends Base{
	
	WebDriver driver;
	public ExtentReports extentreport;
	public ExtenTest extenttest;
	@Parameters("browser")
	@BeforeTest
	public void launch()
	{
		Report_Extent();
		extenttest=extentreport.startTest("start");
	}
	
	public void launch_Application()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		driver=new ChromeDriver();
		Log.info("chrome Browse launched");
		extenttest.log(logstatus.PASS,"Launch success")
		driver.get(read_testdata("sitUrl"));
		Log.info("URL entered"+read_testdata("siturl"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		Log.info("maximize browser");
		String strTitle = driver.getTitle();
		if(strTitle.equals("Kexim BANK")){
		 System.out.println("Title displayed correctely as:"+strTitle);
		 Log.info("Title displayed correctely as:"+strTitle);
	} else  {
		screenshot.CaptureScreenShot("verifyTitle");
		System.out.println("Incorrect Title Displayed as:"+strTitle);
		Log.info("Incorrect Title Displayed as:"+strTitle);
	}
		public void Report_Extent()
		{
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
			String timestamp= df.format(date);
			extentreport = new ExtentReports("C:\\Users\\user\\Downloads\\Ranford\\Reports\\"+ExtentReportsResults);
		}

		//Assert.assertEquals(driver.getTitle(),"HDFC BANK");
		
	}
	
	public void login_Application()
	{
		LoginPage.username_textfield(driver).sendKeys(read_testdata("username"));
		LoginPage.password_textfield(driver).sendKeys(read_testdata("password"));
		LoginPage.login_button(driver).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		boolean binLogout=AdminPage.logout_button(driver).isDisplayed();
		if(binLogout)
		{
			Assert.assertTrue(true,"Login succesful");
		} else {
		
			Assert.assertTrue(false,"login unsuccesful");
		}
		
		
	}
	
	public void clickbranches()
	{
		AdminPage.branches_button(driver).click();
	}
/*		
	public void createNewBranch()
	{
		BranchesPage.newBranch_btn(driver).click();
		BranchesPage.branchName_txt(driver).sendKeys(read_testdata("branchname"));
		BranchesPage.branchAddress1_txt(driver).sendKeys(read_testdata("address"));
		BranchesPage.zipcode_txt(driver).sendKeys(read_testdata("zipcode"));
		GenericPage.dropDownSelection(driver, getlocator("branch_country")).selectByValue(read_testdata("country"));
		GenericPage.dropDownSelection(driver, getlocator("branch_state")).selectByValue(read_testdata("state"));
		GenericPage.dropDownSelection(driver, getlocator("branch_city")).selectByValue(read_testdata("city"));
		BranchesPage.cancel_btn(driver).click();
	}
	*/
	public void createBranch(String bname, String address, String zip, String country, String state, String city)
	{
        BranchesPage.newBranch_btn(driver).click();
        BranchesPage.branchName_txt(driver).sendKeys(bname);
        BranchesPage.branchAddress1_txt(driver).sendKeys(address);
        BranchesPage.zipcode_txt(driver).sendKeys(zip);
		GenericPage.dropDownSelection(driver, getlocator("branch_country")).selectByValue(country);
		GenericPage.dropDownSelection(driver, getlocator("branch_state")).selectByValue(state);
		GenericPage.dropDownSelection(driver, getlocator("branch_city")).selectByValue(city);
		BranchesPage.cancel_btn(driver).click();
		
	}
	
	
	public Object[][] excelContent(String fileName, String sheetName) throws IOException
	{
		Excel_Class.excelconnection(fileName, sheetName);
		int rc = Excel_Class.rcount();
		int cc = Excel_Class.ccount();
		
		String[][] data=new String[rc-1][cc];
		
		for(int r=1;r<rc;r++)
		{
			for(int c=0;c<cc;c++)
			{
				data[r-1][c] = Excel_Class.readdata(c, r);
			}
		}
		
		
		return data;
		
		
	}

	public void logout_Application()
	{
		AdminPage.branches_button(driver).click();
		driver.close();
		extentreport.endTest(extentreport);
		extentrepost.flush();
	}
	
	

}
