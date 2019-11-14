package library;

import static executionEngine.DriverScript.OR;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
//import OpenQA.Selenium.Interactions;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
//import org.openqa.selenium
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.javascript.host.Element;
import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;

import config.Config;
import executionEngine.DriverScript;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import utility.Log;
import utility.ScreenshotCapture;
import utility.Highlight;




import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;




//import org.openqa.selenium.p

public class ActionKeywords<IWebElement> {
	public static WebDriver driver;
	public static WebDriverWait wait;
	private static int defaultWait = 25;
	public static HashMap hm = new HashMap();

	public static WebElement objectLocator(String object) {
		if (object.endsWith("_id"))
			return driver.findElement(By.id(OR.getProperty(object)));
		if (object.endsWith("_class"))
			return driver.findElement(By.className(OR.getProperty(object)));
		if (object.endsWith("_xpath"))
			return driver.findElement(By.xpath(OR.getProperty(object)));
		if (object.endsWith("_css"))
			return driver.findElement(By.cssSelector(OR.getProperty(object)));
		if (object.endsWith("_link"))
			return driver.findElement(By.linkText(OR.getProperty(object)));
		if (object.endsWith("_name"))
			return driver.findElement(By.name(OR.getProperty(object)));
		if (object.endsWith("_tag"))
			return driver.findElement(By.tagName(OR.getProperty(object)));
		if (object.endsWith(")")) {
			String[] obj = object.split("\\(");
			String replaceChar = obj[1].replace(")", "").trim();
			String rObj = OR.getProperty(obj[0].trim()).trim();
			String replacedObj = null;
			if(replaceChar.contains(",")){
				String[] repChar = replaceChar.split(",");
				replacedObj = modifyLocator(rObj,repChar[0],repChar[1]);	
			}else{
				replacedObj = modifyLocator(rObj,replaceChar);
			}
			return driver.findElement(By.xpath(replacedObj));
		}
		return null;
		
		//for stge
	}
	
	public static void clear(String objLocator, String replaceString) {
		Log.info("Entering the text in " + objLocator);
		objectLocator(objLocator).clear();
		waitFor(objLocator,replaceString);
		//reurn
	}
	
	public static String modifyLocator(String objLocator, String replaceString) {
		if (objLocator != null && replaceString != null) {
			String Str = new String(objLocator);
			String mStr = Str.replace("ReplaceString", replaceString);
			return mStr;
		} else {
			Log.error("Replacing string in xpath is failed");
			return null;
		}
	}
	
	public static String modifyLocator(String objLocator, String replaceString1, String replaceString2) {
		if (objLocator != null && replaceString1 != null && replaceString2 != null) {
			String Str = new String(objLocator);
			String mStr1 = Str.replace("ReplaceString1", replaceString1);
			String mStr2 = mStr1.replace("ReplaceString2", replaceString2);
			return mStr2;
		} else {
			Log.error("Replacing string in xpath is failed");
			return null;
			//test123
		}
	}
	
	
//	public static void startServer(String object, String data) {
//		Runtime runtime = Runtime.getRuntime();
//		try {
//			//runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 0.0.0.0 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
//			
//			Log.info("The device attached"+ object + "is" + data);
//			
//			runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 0.0.0.0 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"true\"\"}\"\"");
//			Thread.sleep(10000);
//		} 
//		
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void startServer() {
		Runtime runtime = Runtime.getRuntime();
		try {
			//runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 0.0.0.0 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
			
			runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 0.0.0.0 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"true\"\"}\"\"");
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void setcapabilities(String object, String data) throws MalformedURLException {
	
		
		DesiredCapabilities des =  new DesiredCapabilities();
		des.setCapability("platformName", "Android");
		des.setCapability("deviceName", "Realwear");
		
		//SIGMA
		//des.setCapability("udid", "b8dcd685");
	
		//Epsilon
		des.setCapability("udid", "b8d3d668");
			//des.setCapability(, value);
		des.setCapability("platformVersion", "6.0.1");
			
	des.setCapability("appPackage", object);

	des.setCapability("appActivity", data);
	//des.setCapability(noReset, value);
		
		//des.setCapability("appPackage", "com.honeywell.si2w.proshot");
		
		//des.setCapability("appActivity", "com.honeywell.si2w.proshot.splash.activity.SplashScreen");
		
//		des.setCapability("appPackage", "com.honeywell.si2w.proshot");
//	des.setCapability("appActivity", "com.honeywell.si2w.proshot.login.LoginActivity");
		
//		des.setCapability("appPackage", "com.realwear.launcher");
//		des.setCapability("appActivity", "com.realwear.launcher.ApplicationActivity");
//		//des.setCapability("appPackage", "com.honeywell.si2w.proshot");
//	des.setCapability("appActivity", "com.realwear.launcher.MainActivity");
		
			
			//des.setCapability("appActivity", "com.honeywell.si2w.proshot.home.activity.HomeActivity");

			
		driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), des);		
		
	}

	

 	public static void openBrowser(String object,String data){
		try{	
//			// For browser stack execution
			String brwsStackURL = "";
			String data2=null;
			String data3=null;
			if(data.contains(",")){
				String[] parArray = data.split(",");
				data=parArray[0];
				data2 = parArray[1];
				if(parArray.length > 2){
					data3 = parArray[2];	
				}
			} 
			if(data.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", Config.Base_Dir+"\\lib\\chromedriver.exe");
				driver=new ChromeDriver();
				driver.manage().window().maximize();
				//driver.manage().window().setSize(new Dimension(411,700));
				//driver.manage().window().setSize(new Dimension(768,1024));
				Log.info("Chrome browser started");	
				driver.get(Config.Base_Dir+"/src/test/resources/Config/Web/openingscreen.html");
			}
			if(data.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver", Config.Base_Dir+"\\lib\\geckodriver.exe");
				driver=new FirefoxDriver();
				//driver.manage().window().maximize();
				driver.manage().window().setSize(new Dimension(1366,768));
				Log.info("Firefox browser started");
			}
			if(data.equalsIgnoreCase("ie")){
				System.setProperty("webdriver.ie.driver", Config.Base_Dir+"\\lib\\IEDriverServer.exe");
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				
				ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				ieCapabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
				ieCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
				ieCapabilities.setCapability("nativeEvents", false);
				ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				//ieCapabilities.setJavascriptEnabled(true);
				ieCapabilities.setCapability("ie.ensureCleanSession", true);
				//InternetExplorerHelper.SetZoom100();
				
				//InternetExplorerDriver.SetZoom100();
				ieCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,"https://www.google.com/");
				driver = new InternetExplorerDriver(ieCapabilities);
				driver.manage().deleteAllCookies();
				//driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
				driver.manage().window().maximize();
				
				//driver.get("file///" + Config.Base_Dir+"/src/test/resources/Config/Web/openingscreen.html");
				Log.info("IE browser started");
			}
			
			if(data.equalsIgnoreCase("edge"))
			{
				
				
		
			
				//options.AddAdditionalCapability("InPrivate", true);
				//this.edgeDriver = new EdgeDriver(ptions)
				//System.setProperty("webdriver.edge.driver", Config.Base_Dir+"\\lib\\msedgedriver.exe");
				driver = new EdgeDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
//				
				
			}
			
			if(data.equalsIgnoreCase("safari"))
			{
				driver = new SafariDriver();
				driver.manage().window().maximize();
			}
			if(data.equalsIgnoreCase("opera")){
				System.setProperty("webdriver.chrome.driver", Config.Base_Dir+"\\lib\\operadriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.setBinary("C:\\Program Files\\Opera\\52.0.2871.30\\opera.exe");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver=new ChromeDriver(capabilities);
				driver.manage().window().maximize();
				Log.info("Opera browser started");
			}
			if(data.equalsIgnoreCase("windows")){
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("os", "Windows");
				caps.setCapability("os_version", "10");
				caps.setCapability("browser",data2);
				if(!data3.isEmpty()){
					caps.setCapability("browser_version",data3);
				}
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.debug", "true");
				java.net.URL myURL = new java.net.URL(brwsStackURL);
				driver = new RemoteWebDriver(myURL,caps);
			}
			if(data.equalsIgnoreCase("mac")){
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("os", "OS X");
				caps.setCapability("os_version", "High Sierra");
				caps.setCapability("browser",data2);
				if(!data3.isEmpty()){
					caps.setCapability("browser_version",data3);
				}
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.debug", "true");
				java.net.URL myURL = new java.net.URL(brwsStackURL);
				driver = new RemoteWebDriver(myURL,caps);
			}
			if(data.equalsIgnoreCase("andriod")){
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("realMobile", "true");
				caps.setCapability("platform", "ANDROID");
				caps.setCapability("device",data2);
				//caps.setCapability("device", "Samsung Galaxy Note 8");
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.debug", "true");
				java.net.URL myURL = new java.net.URL(brwsStackURL);
				driver = new RemoteWebDriver(myURL,caps);
			}
			if(data.equalsIgnoreCase("andriod tab")){
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("realMobile", "true");
				caps.setCapability("platform", "ANDROID");
				caps.setCapability("device",data2);
				//caps.setCapability("device", "Samsung Galaxy Tab 4");
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.debug", "true");
				java.net.URL myURL = new java.net.URL(brwsStackURL);
				driver = new RemoteWebDriver(myURL,caps);
			}
			
			if(data.equalsIgnoreCase("Realwear"))
			{
				startServer();
				//AppiumDriver <AndroidElement> driver;
			DesiredCapabilities des =  new DesiredCapabilities();
			des.setCapability("platformName", "Android");
			des.setCapability("deviceName", "Realwear");
			des.setCapability("udid", "b8d3d668");
			des.setCapability("platformVersion", "6.0.1");
			des.setCapability("appPackage", "com.honeywell.si2w.proshot");
			des.setCapability("appActivity", "com.honeywell.si2w.proshot.splash.activity.SplashScreen");
			
			driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), des);
			}
			if(data.equalsIgnoreCase("ios")){
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("realMobile", "true");
				caps.setCapability("platform", "MAC");
				caps.setCapability("device",data2);
				//caps.setCapability("device", "iPhone 8");
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.debug", "true");
				java.net.URL myURL = new java.net.URL(brwsStackURL);
				driver = new RemoteWebDriver(myURL,caps);
			}if(data.equalsIgnoreCase("ios tab")){
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("realMobile", "true");
				caps.setCapability("platform", "MAC");
				caps.setCapability("device",data2);
				//caps.setCapability("device", "iPad Pro");
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.debug", "true");
				java.net.URL myURL = new java.net.URL(brwsStackURL);
				driver = new RemoteWebDriver(myURL,caps);
			}
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}catch (Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}

	public static void navigateURL(String object, String data){
		try{
			Log.info("Navigating to URL");
			System.out.println("Value = " + DriverScript.Auto_Url);
			if (!DriverScript.Auto_Url.equalsIgnoreCase("$Auto_Url")){
				driver.get(DriverScript.Auto_Url);
				ScreenshotCapture.takeScreenShot(driver);
				Log.info("Test script URL is overidden by Octopus variable");
				System.out.println("Test script URL is overidden by Octopus variable");
			} else {
				driver.get(data);
				Log.info("URL present in test script used");
				System.out.println("URL present in test script used");
			}
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}

	public static void clickElement(String object, String data){		
		try{
			
			driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
			Log.info("Clicking on Webelement "+ object);
			//driver.findElement(By.xpath("Value")).click();
			WebElement element =  objectLocator(object);
			Highlight.highlightElement(element);
			element.click();				
			waitFor(object, data);
			DriverScript.bResult = true;
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Notlnk_ able to click --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
	
    public static void doubleClickElement(String object, String data){
    	try{
			Log.info("Double clicking on Webelement "+ object);
			Actions action = new Actions(driver);
			Highlight.highlightElement(objectLocator(object));
			action.moveToElement(objectLocator(object)).doubleClick().perform();
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Notlnk_ able to double click --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
    
    public static void clearField(String object, String data){        
        try{
              Log.info("Clearing the text in " + object);
              Highlight.highlightElement(objectLocator(object));
              objectLocator(object).clear();
              objectLocator(object).sendKeys(Keys.BACK_SPACE);
              Thread.sleep(1000);
              DriverScript.bResult = true;
        }catch(Exception e){
              ScreenshotCapture.takeScreenShot(driver);
              Log.error("Not able to clear data --- " + e.getMessage());
              DriverScript.bResult = false;
              DriverScript.failedException = e.getMessage();
        }
    }

	public static boolean retryingFindClick(String object) {
		boolean result = false;
		int attempts = 0;
		while(attempts < 2) {
			try {
				objectLocator(object).click();
				result = true;
				break;
			} catch(StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}
	
	public static List<WebElement> getElements(String object, String data){
			Log.info("Get all elements within a dom "+object);
			if(object.endsWith("_css"))
				{ return driver.findElements(By.cssSelector(OR.getProperty(object))); }				
			if(object.endsWith("_xpath"))
				{ return driver.findElements(By.xpath(OR.getProperty(object))); }		
			if(object.endsWith("_id"))
				{ return driver.findElements(By.id(OR.getProperty(object))); }		
			if(object.endsWith("_name"))
				{ return driver.findElements(By.name(OR.getProperty(object))); }		
			if(object.endsWith("_class"))
				{ return driver.findElements(By.className(OR.getProperty(object))); }		
			if(object.endsWith("_linktext"))
				{ return driver.findElements(By.linkText(OR.getProperty(object))); }		
			if(object.endsWith("_tag"))
				{ return driver.findElements(By.tagName(OR.getProperty(object))); }
			if (object.endsWith(")")){
				String[] obj = object.split("\\(");
				String replaceChar = obj[1].replace(")", "").trim();
				String rObj = OR.getProperty(obj[0].trim()).trim();
				String replacedObj = modifyLocator(rObj, replaceChar);
				return driver.findElements(By.xpath(replacedObj));
			}
			return null;
	}
	
	public void dropFile(String target, String filename) {
		try {			
			// open upload window
			objectLocator(target).click();
			Thread.sleep(3000);
			Runtime.getRuntime().exec(Config.Base_Dir + "\\bin\\SetFilePath.exe" + " " + Config.Base_Dir + "\\uploadFiles");
			Thread.sleep(2000);
			Runtime.getRuntime().exec(Config.Base_Dir + "\\bin\\FileUpload.exe" + " "+ filename);
			Thread.sleep(7000);
		} catch (Exception e) {
			Log.error("Not able to upload document --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}

	public static void matchElements(String object, String data) {
		try {
			String[] NewData = data.split("\\n");
			Log.info("Expected data : " + Arrays.toString(NewData));
			Log.info("Get all elements within a dom " + OR.getProperty(object));
			List<WebElement> elements = getElements(object, data);
			List<Integer> indexCnt = new ArrayList<Integer>();
			List<String> webList = new ArrayList<String>();
			int cnt = 0;
			for (WebElement elemText : elements) {
				webList.add(elemText.getText());
			}
			for (int i = 0; i < NewData.length; i++) {
				if (webList.toString().contains(NewData[i])) {
					cnt = cnt + 1;
				} else {
					indexCnt.add(i);
				}
			}
			if (cnt == NewData.length) {
				DriverScript.bResult = true;
			} else {
				ScreenshotCapture.takeScreenShot(driver);
				DriverScript.failedException = "false";
				for(int j=0;j<indexCnt.size();j++){
					Log.error("Expected text --- " + NewData[indexCnt.get(j)]);
					Log.error("Expected is not in actual string --- " + webList.toString());
			}
				indexCnt.clear();
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able find elements with text --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}

	public static void enterInput(String object, String data){		
		try{
			
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			
			Log.info("Entering the text in " + object);
			String expectedText = "";
			if (data.trim().toLowerCase().contains("var")) {
				expectedText = (String) hm.get(data);
			} else {
				expectedText = data;
			}
			System.out.print("expectedText : " + expectedText +"\n");
			//highLighterMethod
			WebElement element =  objectLocator(object);
			element.click();
			Highlight.highlightElement(element);
			element.sendKeys(expectedText);
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to Enter UserName --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	
	public static void enterInputnew(String object, String data){		
		try{
			
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			
			Log.info("Entering the text in " + object);
			String expectedText = "";
			if (data.trim().toLowerCase().contains("var")) {
				expectedText = (String) hm.get(data);
			} else {
				expectedText = data;
			}
			System.out.print("expectedText : " + expectedText +"\n");
			//highLighterMethod
			WebElement element =  objectLocator(object);
			//element.click();
			Highlight.highlightElement(element);
			element.sendKeys(expectedText);
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to Enter UserName --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}

	public static void enterUniqueInput(String object, String data){
		try{
			Log.info("Entering the text in " + object);
			data = data + RandomStringUtils.randomAlphanumeric(5).toLowerCase();;
			Log.info("Entering the text in " + object);
			Highlight.highlightElement(objectLocator(object));
			objectLocator(object).sendKeys(data);	
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to Enter UserName --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}

	public static void selectDropdown(String object, String data){
		try{
			Log.info("Selecting the dropdown value in " + object);
			WebElement element = null;
			element = objectLocator(object);
			Highlight.highlightElement(objectLocator(object));
			Select se = new Select(element);
			Log.info(data);
			
			se.selectByVisibleText(data);
			DriverScript.bResult = true;
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to select dropdown value --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
			//Assert.fail();//***//
		}
	}

	public static void selectListBox(String object, String data){	
		try{
			Log.info("Selecting the list value in " + object);
			WebElement element = null;
			element = objectLocator(object);	
			Select listBox = new Select(element);
			System.out.println(data);
			Log.info(data);
			listBox.selectByValue(data);
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to select dropdown value --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}

	public static void checkElement(String object, String data){
		try{
			Log.info("check element: "+ object);
			Boolean isChecked = null;
			isChecked = objectLocator(object).isSelected();
			if(isChecked.equals(false))
				objectLocator(object).click();			
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to check element --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void unCheckElement(String object, String data){
		try{
			Log.info("uncheck element: "+ object);
			Boolean isChecked = null;
			isChecked = objectLocator(object).isSelected();
			if(isChecked.equals(true))
				objectLocator(object).click();			
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to uncheck element --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void waitFor(String object, String data) {
		try{
			Log.info("Wait for " + data + " miliseconds");
			int userwaittime = Integer.parseInt(data);
			Thread.sleep(userwaittime);
			DriverScript.bResult = true;
		
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void waitnew(String object, String data) {
		try{
			Log.info("Wait for " + data + " miliseconds");
			//int userwaittime = Integer.parseInt(data);
			Thread.sleep(3000);
		
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to Wait --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
		
	public static void ScreenshotCapture(String object, String data){
			ScreenshotCapture.takeScreenShot(driver);
	}
	
	public static void closeBrowser(String object, String data){
		try{
			
		//	driver.get(Config.Base_Dir+"\\src\\test\\resources\\Config\\Web\\closingscreen.html");
			Thread.sleep(1000);
			Log.info("Closing the browser");
			//driver.quit();
			driver.close();
			DriverScript.bResult = true;
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void verifyPageTitle(String object, String data){
		try{
			if (driver.getTitle().contains(data) == true) {
				DriverScript.bResult = true;
			}else{
	        	ScreenshotCapture.takeScreenShot(driver);
	        	Log.error("Expected text: " + data + " Actual text: "+ driver.getTitle());
	           	DriverScript.failedException = "false";
			}
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to get page title --- " + e.getMessage());
			DriverScript.failedException = "false";
		}
	}
	
	public static void verifyTextPresent(String object, String data) {
		
		
		
		try {
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			String expectedText = "";
			if (data.trim().toLowerCase().contains("variable")) {
				expectedText = (String) hm.get(data);
			} else {
				expectedText = data;
			}
			String txtElement = objectLocator(object).getText();
			System.out.println(txtElement);
			if (txtElement.trim().toUpperCase().contains(expectedText.trim().toUpperCase()) && !expectedText.equals("")){
				DriverScript.bResult = true;
				Log.info("Expected text: " + expectedText + " ; Actual text: " + txtElement);
				
			} else {
				highLightElementAndScreenCapture(object, data);
				Log.error("Expected text: " + expectedText + " ; Actual text: " + txtElement);
				//DriverScript.bResult = ("Expected text: " + expectedText + " ; Actual text: " + txtElement);
				DriverScript.bResult = false;
				DriverScript.failedException = ("Expected text: " + expectedText + " ; Actual text: " + txtElement);
				
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to get text of the element --- " + e.getMessage());
			DriverScript.failedException = "false";
		}
	}
	
	public static void verifyTextPresentinInputfield(String object, String data) {
		
		try {
			
			String expectedText = "";
			if (data.trim().toLowerCase().contains("variable")) {
				expectedText = (String) hm.get(data);
			} else {
				expectedText = data;
			}
			String txtElement = objectLocator(object).getAttribute("value");
			System.out.println(txtElement);
			if (txtElement.trim().toUpperCase().contains(expectedText.trim().toUpperCase()) && !expectedText.equals("")){
				DriverScript.bResult = true;
				Log.info("Expected text: " + expectedText + " ; Actual text: " + txtElement);
				
			} else {
				highLightElementAndScreenCapture(object, data);
				Log.error("Expected text: " + expectedText + " ; Actual text: " + txtElement);
				//DriverScript.bResult = ("Expected text: " + expectedText + " ; Actual text: " + txtElement);
				DriverScript.bResult = false;
				DriverScript.failedException = ("Expected text: " + expectedText + " ; Actual text: " + txtElement);
				
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to get text of the element --- " + e.getMessage());
			DriverScript.failedException = "false";
		}
	}

	public static void verifyElementPresent(String object, String data){
		try {
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Log.info("Verify Element present " + object);
			boolean exists = getElements(object, data).size() != 0;
			if (exists) {
				Highlight.highlightElement(objectLocator(object));
				DriverScript.bResult = true;
			} else {
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Element not present in the screen");
				DriverScript.bResult = false;
				DriverScript.failedException = "Element not present in the screen";
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to locate element --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void verifyElementenabled(String object, String data)
	{
		try {
			
			Log.info("Verify Element Enabled " + object);
			
			Boolean status=objectLocator(object).isEnabled();
			 if (status==true)
			 {
				Highlight.highlightElement(objectLocator(object));
				DriverScript.bResult = true;
			 }
			 
			 else
			 {
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Element not enabled");
				DriverScript.bResult = false;
				DriverScript.failedException = "Element not disabled";
				 
			 }
			
		}
		
		catch(Exception e)
		{
			
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to locate element --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
			
		}
		
		
	}
	
	
	public static void verifyElementDisablednew(String object, String data)
	{
		
try {
			
			Log.info("Verify Element Disabled " + object);
			
			Boolean status=objectLocator(object).isEnabled();
			 if (status==false)
			 {
				Highlight.highlightElement(objectLocator(object));
				DriverScript.bResult = true;
			 }
			 
			 else
			 {
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Element not enabled");
				DriverScript.bResult = false;
				DriverScript.failedException = "Element is enabled";
				 
			 }
			
		}
		
		catch(Exception e)
		{
			
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to locate element --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
			
		}
		
	
		
		
	}
	
	
	
	
	public static void verifyElementDisabled(String object, String data){
		try {
			
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			Log.info("Verify Element Disabled " + object);
			String eenabled = objectLocator(object).getAttribute("class");
			
			//Log.error(eenabled);
			System.out.println(eenabled);
			if (eenabled.contains("disable")) {
				Highlight.highlightElement(objectLocator(object));
				DriverScript.bResult = true;
			} else {
				ScreenshotCapture.takeScreenShot(driver);
				Highlight.highlightElement(objectLocator(object));
				Log.error("Element not disbaled");
				DriverScript.bResult = false;
				DriverScript.failedException = "Element not disabled";
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to locate element --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	
	public static void verifyElementhidden(String object, String data){
		try {
			
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			Log.info("Verify Element hidden " + object);
			String hiddenelementclass = objectLocator(object).getAttribute("class");
			
			//Log.error(eenabled);
			System.out.println(hiddenelementclass);
			if (hiddenelementclass.contains("hide")) {
				Highlight.highlightElement(objectLocator(object));
				DriverScript.bResult = true;
			} else {
				ScreenshotCapture.takeScreenShot(driver);
				Highlight.highlightElement(objectLocator(object));
				Log.error("Element not disbaled");
				DriverScript.bResult = false;
				DriverScript.failedException = "Element not hidden";
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to locate element --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void verifyelementnotclickable(String object, String data)     
	{
	    try
	    {
	    	
	    	objectLocator(object).click();
	    	Highlight.highlightElement(objectLocator(object));
	    	ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element is clickable");
			DriverScript.bResult = false;
			//DriverScript.failedException = "Element is clickbale";
	       
	    }
	    catch (Exception e)
	    {
	  	
	    	//Highlight.highlightElement(objectLocator(object));
			DriverScript.bResult = true;
			DriverScript.failedException = "Element is not clickable";
	        
	    }
	}
	    
	    
	    public static void verifyelementclickable(String object, String data)     
		{
		    try
		    {
		    	objectLocator(object).click();
		    	ScreenshotCapture.takeScreenShot(driver);
				Log.error("Element is clickbale");
				DriverScript.bResult = true;
				
		       
		    }
		    catch (Exception e)
		    {
		  	
		    	Highlight.highlightElement(objectLocator(object));
				DriverScript.bResult = false;
				DriverScript.failedException = "Element is not clickbale";
		        
		    }
		
	    
//	    catch (Exception e1) {
//			ScreenshotCapture.takeScreenShot(driver);
//			Log.error("Not able to locate element --- " + e.getMessage());
//			DriverScript.bResult = false;
//			DriverScript.failedException = e.getMessage();
//		}
	}
	
	public static void verifyElementNOTPresent(String object, String data){
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
			Log.info("Verify Element is NOT present " + object);
			boolean exists = getElements(object, data).size() == 0;
			if (exists) {
					DriverScript.bResult = true;
					//JOptionPane.showMessageDialog(null, "Element Not displayed on Screen :-)");
					//driver.switchTo().alert().dismiss();
					//Thread.sleep(500);
					//JOptionPane.ABORT();
					//driver.findElement(By.xpath("//*[text()='OK']"));
			} else {
				
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", objectLocator(object));
				Thread.sleep(500); 
				ScreenshotCapture.takeScreenShot(driver);
				Highlight.highlightElement(objectLocator(object));
				Log.error("Element  present in the screen");
				DriverScript.bResult = false;
				DriverScript.failedException = "Element  present in the screen";
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element Present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
		
	public static int pickRandomValue(int maxValue) {
		int r = 1;
		int[] numbers = new int[maxValue];
		for (int i = 0; i < numbers.length; i++) {
			r = (int) (Math.random() * maxValue);
			return r;
		}
		return r;
	}

	public static int getTableRowCount(String object, String data){
		int count = 0;
		try{
			Log.info("Get total row count "+ object);
			count = driver.findElements(By.xpath(object)).size();
			//Log.info(count);
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element not present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		return count;		 
	}
	
	public static int getTableColCount(String object, String data){
		int count = 0;
		try{
			Log.info("Get total row count "+ object);
			count = driver.findElements(By.xpath(object)).size();
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element not present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		return count;		 
	}

	public static String getText(String object, String data){
		String elemText = "";
		try{
			Log.info("Get text of: "+ object);
			Highlight.highlightElement(objectLocator(object));
			elemText = objectLocator(object).getText();
			hm.put(data, elemText);
			
			Log.info("Text of the object is: " + elemText);
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable get text --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		return elemText;
	}
	
	public static void randomNumber(String len,String varName) {
		String genString = "123456789";
		int length = Integer.parseInt(len);

		StringBuilder randString = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int character = (int) (Math.random() * genString.length());
			randString.append(genString.charAt(character));
		}
		System.out.print("Random String : " + randString.toString() + "\n");
		hm.put(varName,randString.toString());
	}
		
	public static void pressEnter(String object, String data) {
		try {
			objectLocator(object).sendKeys(Keys.ENTER);
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to send enter key to the object --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void pageRefresh(String object, String data){
			driver.navigate().refresh();
	}
	
	public static void setDateByTextEntry(String object, String data){
		try{
			Log.info("Get Date: "+object);
			objectLocator(object).sendKeys(data);
			objectLocator(object).sendKeys(Keys.ENTER);
			String dateval = getText(object,data);
			if(dateval.equals(data)){
				Log.info("Date in field -- "+dateval);
				DriverScript.bResult = true;				
			}
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to set date --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}		
	}
	
	public static boolean columnSortingoption(String columntosort, String data) {

		ArrayList<String> beforesort = new ArrayList<String>();
		ArrayList<String> sortedvalue = new ArrayList<String>();

		List<WebElement> columnlisttochksort = driver.findElements(By.cssSelector(columntosort));
		for (WebElement sortingColvalue : columnlisttochksort) {
			beforesort.add(sortingColvalue.getText().trim().toUpperCase());
			sortedvalue.add(sortingColvalue.getText().trim().toUpperCase());
		}
		if (data.equalsIgnoreCase("ascending")){
			Collections.sort(sortedvalue, String.CASE_INSENSITIVE_ORDER);	
		}else if (data.contains("descending")) {
			Collections.sort(sortedvalue, String.CASE_INSENSITIVE_ORDER);
			Collections.reverse(sortedvalue);
		}
		
		System.out.println("before sorting:" + beforesort);
		System.out.println("sortedvalue:" + sortedvalue);
		if (sortedvalue.equals(beforesort)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean columndateASCsortingoption(String columntosort, String data) {

		ArrayList<String> beforesort = new ArrayList<String>();
		ArrayList<String> sortedvalue = new ArrayList<String>();

		List<WebElement> columnlisttochksort = driver.findElements(By.cssSelector(columntosort));
		for (WebElement sortingColvalue : columnlisttochksort)

		{
			if (sortingColvalue.getText().matches("") || sortingColvalue.getText().matches("--")) {
				beforesort.remove(sortingColvalue.getText().trim().toUpperCase());
				sortedvalue.remove(sortingColvalue.getText().trim().toUpperCase());
			} else {
				beforesort.add(sortingColvalue.getText().trim().toUpperCase());
				sortedvalue.add(sortingColvalue.getText().trim().toUpperCase());

			}
		}

		Collections.sort(sortedvalue, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat("MM/dd/yyyy");

			public int compare(String o1, String o2) {
				try {
					return f.parse(o1).compareTo(f.parse(o2));
				} catch (java.text.ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});

		System.out.println("before sorting:" + beforesort);
		System.out.println("revesrted date:" + sortedvalue);
		if (beforesort.equals(sortedvalue)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean columndateDSCsortingoption(String columntosort, String data) {

		ArrayList<String> beforesort = new ArrayList<String>();
		ArrayList<String> sortedvalue = new ArrayList<String>();

		List<WebElement> columnlisttochksort = driver.findElements(By.cssSelector(columntosort));
		for (WebElement sortingColvalue : columnlisttochksort)

		{
			if (sortingColvalue.getText().matches("") || sortingColvalue.getText().matches("--")) {
				beforesort.remove(sortingColvalue.getText().trim().toUpperCase());
				sortedvalue.remove(sortingColvalue.getText().trim().toUpperCase());
			} else {
				beforesort.add(sortingColvalue.getText().trim().toUpperCase());
				sortedvalue.add(sortingColvalue.getText().trim().toUpperCase());

			}
		}

		Collections.sort(sortedvalue, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat("MM/dd/yyyy");

			public int compare(String o1, String o2) {
				try {
					return f.parse(o1).compareTo(f.parse(o2));
				} catch (java.text.ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		System.out.println("intermediate sort: " + sortedvalue);
		Collections.reverse(sortedvalue);
		System.out.println("before sorting:" + beforesort);
		System.out.println("revesrted date:" + sortedvalue);

		if (beforesort.equals(sortedvalue)) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean validateDropdown(String object, String... text) {

		Select selectObject = new Select(objectLocator(object));
		List<String> options = new ArrayList<String>();
		for (int i = 0; i < selectObject.getOptions().size(); i++) {
			options.add(selectObject.getOptions().get(i).getText().toLowerCase());
		}
		if (options.size() == text.length) {
			for (String temp : text)
				if (!options.contains(temp.toLowerCase()))
				{
					System.out.println("Option " + temp + " is not displayed in the options " + options);
					return false;
				}

		} else {
			System.out.println("Options count Mismatch.\n Expected values: " + text + " \n Actual values: " + options);
			return false;
		}
		System.out.println("Options are displayed .\n Expected values: " + text + " \n Actual values: " + options);
		return true;
	}
	
	public static String validateText(String object, String data){
		String expectedText = "";
		if(data.trim().toLowerCase().contains("variable")){
			expectedText = (String) hm.get(data);
		}else{
			expectedText=data;
		}
		WebElement element = objectLocator(object); 			
		if (element.getAttribute("value").trim().equals(expectedText.trim()) && !expectedText.equals("")) {
        	DriverScript.bResult = true;
        	Log.info("Expected text: " + expectedText.trim() + " Actual text: "+ element.getAttribute("value").trim());
		} else{
			highLightElementAndScreenCapture(object,data);
        	Log.error("Expected text: " + expectedText.trim() + " Actual text: "+ element.getAttribute("value").trim());
           	DriverScript.failedException = "false";
		}
		return expectedText;
	}
	
	public static int getInt(String object, String text) {
		int count = 0;
		if (text != null)
			try {
				text = text.replaceAll("(\\D+)(.)*", "").trim();
				if (!"".equals(text))
					count = Integer.parseInt(text.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		return count;
	}
	
	public void isElementPresent(String object, String data) {
		try {
			Log.info("Verify Element isPresent " + object);
			boolean exists = getElements(object,data).size() != 0;
			if(exists) {
				Highlight.highlightElement(objectLocator(object));
//				 WebElement element = objectLocator(object);
//				 JavascriptExecutor js = (JavascriptExecutor) driver;
//			     //use executeScript() method and pass the arguments 
//			     //Here i pass values based on css style. Yellow background color wth solid red color border. 
//				 js.executeScript("arguments[0].setAttribute('style', 'background: dullwhite; border: 2px solid blue;');", element);
//				 Thread.sleep(1000);
//				 js.executeScript("arguments[0].setAttribute('style',arguments[1]);", element, "");
				
				DriverScript.stepRun = true;
				Log.info("Element is present and next step will be executed");
			} else {
				DriverScript.stepRun = false;
				Log.info("Element is not present and next step will be SKIPPED");
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Not able to locate element --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static boolean isValidURL(String object, String pageUrl) {
		try {
			new URL(pageUrl);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}
	
	public boolean isAlertDisplayedWithinTimeout(int timeout) throws InterruptedException{
		for (int counter = 1; counter <= (timeout) / 500; counter++) {
			try {
				Alert alert = driver.switchTo().alert();
				return true;
			} catch (Exception e) {
				Thread.sleep(500);
			}
		}
		return false;
	}
	
	public static boolean isElementByCSSPresent(String selector) {
		int count = driver.findElements(By.cssSelector(selector)).size();
		if (count > 0)
			return true;
		else
			return false;

	}
	
	public static WebElement FindByCssSelector(String selector,String data) {
		try {
			WebElement element = driver.findElement(By.cssSelector(selector));
			return element;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean isDateValid(String actualDate, String expectedDateFormat){
		try {
			SimpleDateFormat format = new SimpleDateFormat(expectedDateFormat);
			format.parse(actualDate);
		} catch (ParseException e) {
			Log.error("Date is not valid --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
		return true;
	}
		
	public static void SelectFromDateInCalendar(String object, String data) {
		try{
			//month-0;date-1;year-2; 
			String[] formatDate = data.split("/");
			String fromMonth=formatDate[0],date=formatDate[1],fromYear=formatDate[2];
			System.out.println("cal: "+fromMonth+date+fromYear);
			objectLocator(object).click();
			Select selectYear = new Select(driver.findElement(By.cssSelector("#calYearPicker")));
			selectYear.selectByVisibleText(fromYear);
			Select selectMonth = new Select(driver.findElement(By.cssSelector("#calMonthPicker")));
			if (fromMonth.matches("\\d*")) {
				// since in calendar values for months are from '0' to '11'
				fromMonth = String.valueOf(Integer.valueOf(fromMonth) - 1);
				selectMonth.selectByValue(fromMonth);
			} else if (fromMonth.matches("\\D*")) {
				selectMonth.selectByVisibleText(fromMonth);
			}
			//driver.findElement(By.xpath(".//*[@id='datePicker']//td[@class='weekday' and text()="+date+"]")).click();
			driver.findElement(By.xpath(".//*[@id='datePicker']//td[contains(@class,'weekday') or @class='selectedDate'] [text()="+date+"]")).click();
			String datetext = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			if(datetext.equals(data)){
				Log.info("Date in field -- "+datetext);
				DriverScript.bResult = true;				
			}
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to set date --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public String selectOptionFromDropDown(String selector, String selectText) {
		try {

			driver.findElement(By.cssSelector(selector)).click();
			new Select(driver.findElement(By.cssSelector(selector))).selectByVisibleText(selectText);

		} catch (Exception e) {
			System.out.println("Exception in selectOptionFromDropDown:" + e);
		}
		return null;
	}
	
	public static void sswitchToNewTab(String object, String data) {
		try {
			String currentTab = "";
			if (data.trim().toLowerCase().contains("variable")) {
				currentTab = (String) hm.get(data);
			} else {
				currentTab = data;
			}
			ArrayList<String> newTabs = new ArrayList<String>(driver.getWindowHandles());
			// To get total tabs
			int totalTabs = newTabs.size();
			int navigateToTab = 0;
			if (totalTabs >= 1) {
				int tabCount = 0;
				for (String newTab : newTabs) {
					if (newTab.equals(currentTab)) {
						navigateToTab = tabCount;
						break;
					}
					tabCount = tabCount + 1;
				}
				if (tabCount == 0 && totalTabs > 1) {
					driver.switchTo().window(newTabs.get(1));
					DriverScript.bResult = true;
				} else {
					driver.switchTo().window(newTabs.get(0));
					DriverScript.bResult = true;
				}

				/*
				 * newTabs.remove(currentTab); totalTabs = newTabs.size();
				 * System.out.println("totalTabs : " + totalTabs);
				 * driver.switchTo().window(newTabs.get(navigateToTab));
				 */
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to close new browser --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
	
	
	
	
	public static void switchToNewTab(String object, String data) {
		try {
			
			String winHandleBefore = driver.getWindowHandle();

			String TestFile = ".\\src\\test\\java\\library\\SessionDetails.txt";
			File FC = new File(TestFile);
			FC.createNewFile();//Create file.
			FileWriter FW = new FileWriter(TestFile);
			BufferedWriter BW = new BufferedWriter(FW);
			BW.write(winHandleBefore); 
			BW.close();

		
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}				
			
		}
		catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to switch --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
	
	public static void switchParentTab(String object, String data) {
		try {
			String Content ="";
			String TestFile = ".\\src\\test\\java\\library\\SessionDetails.txt";
			FileReader FR = new FileReader(TestFile);
			BufferedReader BR = new BufferedReader(FR);
			while((Content = BR.readLine())!= null){
			driver.switchTo().window(Content);
			}				
			
		}
		catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to switch --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
	
		
	public static void switchAndCloseLatestTab(String object, String data) {
		try {
			String parentWindow = getCurrentWindow(object,data);
			ArrayList<String> newTabs = new ArrayList<String>(driver.getWindowHandles());
			int totalTabs = newTabs.size();
			System.out.println("totalTabs : " + totalTabs);
			int navigateToTab = 0;
			if (totalTabs > 1) {
				int tabCount = 0;
				for (String newTab : newTabs) {
					if (newTab.equals(parentWindow)) {
						navigateToTab = tabCount;
						break;
					}
					tabCount = tabCount + 1;
				}
				if (navigateToTab == 0) {
					driver.switchTo().window(newTabs.get(1));
					driver.close();
					driver.switchTo().window(newTabs.get(0));
				} else {
					driver.switchTo().window(newTabs.get(navigateToTab));
					driver.close();
					driver.switchTo().window(newTabs.get(0));
				}
			}
		} catch (Exception e) {
			Log.error("Unable to close new browser --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
	
	public static void navigateBack(String object, String data){
		try {
			Log.info("Browser navigate");
			driver.navigate().back();
			waitFor(object, data);
			DriverScript.bResult = true;
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to navigate back --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
	
	//hover over related to web

	public static void hoverAndClick(String object, String data){
		try {
			Log.info("Hover on menu : "+ data + "; click on object : " + object);
			
			WebElement we = objectLocator(object);
			Actions actions = new Actions(driver);
			actions.moveToElement(we).perform();
			
	
			//actions.moveToElement(we).moveToElement(objectLocator(object)).click().build().perform();

			//WebElement subMenu = driver.findElement(By.xpath("//*[@id='menu-links']/ul/li[2]/a"));
			//actions.moveToElement(objectLocator(object)).build().perform();
			//actions.
			//actions.click().build().perform();
			//actions.build().
			
			
			
			
			//WebElement we = objectLocator(data);
			//actions.moveToElement(we).moveToElement(objectLocator(object)).click().build().perform();
			/*WebDriverWait wait = new WebDriverWait(driver, 30);
		    wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));*/
			waitFor(object,"5000");
			DriverScript.bResult = true;
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to click --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
		
	public static void scrollAndClick(String object, String data){
		try{
			Log.info("Clicking on Element "+ object);
			WebElement element = objectLocator(object);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);				
			waitFor(object, data);
			DriverScript.bResult = true;
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to click --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
		
	public static void scrollToElement(String object, String data){		
		try{
			Log.info("X and Y axis are : "+ data);
			String axis[] = data.split(",");
			int x = Integer.parseInt(axis[0]);
			int y = Integer.parseInt(axis[1]) - 200;
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollTo(" + x + "," + y +")", "");
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to scroll --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
		
	public static void switchToAlert(String object, String data) {
		try {
			Alert alert = driver.switchTo().alert();
			Thread.sleep(1000);
			if (data.trim().toLowerCase().contains("ok")) {
				alert.accept();
			} else if (data.trim().toLowerCase().contains("cancel")){
				alert.dismiss();
			} else {
				Log.info("Switch to alert is successful");
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to identify alert --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
		
	public static String getCurrentWindow(String object,String data){
		String parentWinHandle = null;
		try {
			 Log.info("Get window handle of current window");
			 parentWinHandle = driver.getWindowHandle();
			 System.out.println("handle : " + parentWinHandle);
			 hm.put(data, parentWinHandle);
			 DriverScript.bResult = true;
		} catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to get current window --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		return parentWinHandle;
	}
	
	public static void switchToNewWindow(String object,String data){
		try {
				driver.switchTo().window(object);
		} catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to get window --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void closeWindow(String object, String data){
		try {
			driver.close();
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to close window --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void dragAndDropFile(String object, String filePath) {
		WebElement target = objectLocator(object);
		int offsetX = 0; int offsetY = 0;
	    
	    WebDriver driver = ((RemoteWebElement)target).getWrappedDriver();
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    WebDriverWait wait = new WebDriverWait(driver,30);

	    String JS_DROP_FILE = "var target = arguments[0],offsetX = arguments[1],offsetY = arguments[2]," +
	    		"document = target.ownerDocument || document," +
	    		"window = document.defaultView || window;"+
	    		"var input = document.createElement('INPUT');"+
	    		"input.type = 'file';"+
	    		"input.style.display = 'none';"+
	    		"input.onchange = function () {"+
	    		"target.scrollIntoView(true);"+
	          "var rect = target.getBoundingClientRect(),"+
	              "x = rect.left + (offsetX || (rect.width >> 1)),"+
	              "y = rect.top + (offsetY || (rect.height >> 1)),"+
	              "dataTransfer = {files: this.files};"+
	              "['dragenter', 'dragover', 'drop'].forEach(function (name) {"+
	            "var evt = document.createEvent('MouseEvent');"+
	            "evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);"+
	            "evt.dataTransfer = dataTransfer;"+
	            "target.dispatchEvent(evt);" +
	            "});" +
	            "setTimeout(function() {document.body.removeChild(input);}, 25);" +
	            "}; " +	            
	            "document.body.appendChild(input);" +
	            "return input;";	    	    
	    WebElement input =  (WebElement)js.executeScript(JS_DROP_FILE, target, offsetX, offsetY);
	    input.sendKeys(filePath);
	    wait.until(ExpectedConditions.stalenessOf(input));
	}
	
	public void sliderControl(String object1, String object2) throws InvocationTargetException{
		try{
			WebElement slidebar = objectLocator(object1);
			int widthSlideBar = slidebar.getSize().width;
			System.out.println("widthSlideBar Length: "+widthSlideBar);
			WebElement slider =  objectLocator(object2);
			Actions sliderAction = new Actions(driver);
			sliderAction.clickAndHold(slider);
			sliderAction.moveByOffset(40,0).build().perform();
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to get window --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();			
		}
	 }
	
	public void switchToFrame(String object, String data) {
		try {
			if(data.toLowerCase().trim().contains("frame")){
				WebElement iFrame = objectLocator(object);
				driver.switchTo().frame(iFrame);
				DriverScript.bResult = true;
			}else if (data.toLowerCase().trim().contains("default")){
				driver.switchTo().defaultContent();
				DriverScript.bResult = true;
			}else{
				System.out.println("Frame name doesn't match");
				Log.error("Frame name is not valid");
				DriverScript.bResult = false;
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to switch frame  --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}

	public static void getPageUrl(String object, String data) {
		try {
			String expectedText = "";
			if (data.trim().toLowerCase().contains("variable")) {
				expectedText = (String) hm.get(data);
			} else {
				expectedText = data;
			}
			String URL = driver.getCurrentUrl();
			System.out.println("Current URL: " + URL);
			if (URL.trim().toUpperCase().contains(expectedText.trim().toUpperCase()) && !expectedText.equals(""))
				DriverScript.bResult = true;
			else {
				Log.error("Expected text: " + data + " ; Actual text: " + URL);
				DriverScript.failedException = "false";
			}
		} catch (Exception e) {
			Log.error("Invalid page url --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}

	// Method to Highlight Element

	public static void highLightElementAndScreenCapture(String object, String data){
		try {
			WebElement element = objectLocator(object);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", element);
			executor.executeScript("window.scrollBy(0,-25)", "");
			Thread.sleep(1000);
			executor.executeScript("arguments[0].setAttribute('style',arguments[1]);", element, "border: 2px solid red;");
			ScreenshotCapture.takeScreenShot(driver);
			executor.executeScript("arguments[0].setAttribute('style',arguments[1]);", element, "");
		} catch (InterruptedException e) {
			Log.error("Unable to highlight the element" + e.getMessage());
		} 
	}

	public static void extractDigits(String object, String data) {
		String dataText="";
		if(data.trim().toLowerCase().contains("variable")){
			dataText=(String) hm.get(data);
			System.out.println("dataText : " + dataText);
			final StringBuilder sb = new StringBuilder(dataText.length());
			for (int i = 0; i < dataText.length(); i++) {
				final char c = dataText.charAt(i);
				if (c > 47 && c < 58) {
					sb.append(c);
				}
			}
			// Check if it adds duplicate
			hm.put(data, sb.toString());
			DriverScript.bResult = true;
			System.out.println("extractDigits : " + sb.toString());
			//hm.put(data + "Int", sb.toString());
		}else{
			DriverScript.failedException = "false";
			Log.error("Variable name is expected (Eg: VariabelVal1)");
		}
	}
	
	public static void compareString(String object, String data) {
		String[] dataText = data.split("=");
		String strCmp1 = dataText[0];
		String strCmp2 = dataText[1];

		System.out.println("strCmp1 : " + strCmp1);
		System.out.println("strCmp2 : " + strCmp2);
		
		// Check arithmetic operator is present in string 1
		if (Pattern.compile("[-+*/]").matcher(strCmp1.trim()).find()) {
			// split with arithmetic operator, and operator will also be
			// considered in array Eg: array will have [a, +, b, -, c, *, d, /, e]
			String[] result1 = strCmp1.split("(?<=[-+*/])|(?=[-+*/])");	
			// check if it has a variable name and get value
			if (result1[0].trim().toLowerCase().contains("var")) {
				result1[0] = (String) hm.get(result1[0]);
			}
			if (result1[2].trim().toLowerCase().contains("var")) {
				result1[2] = (String) hm.get(result1[2]);
			}
			
			Double str1 = Double.parseDouble(result1[0]);
			Double str2 = Double.parseDouble(result1[2]);
			Double str3 = 0.00;
			// perform operation accordingly and store the value in string
			if (result1[1].equals("+")) {
				str3 = str1 + str2;
				System.out.println("str3 : " + str3);
			} else if (result1[1].equals("-")) {
				str3 = str1 - str2;
			} else if (result1[1].equals("*")) {
				str3 = str1 * str2;
			}
			strCmp1 = String.valueOf(Math.round(str3));
			System.out.println(strCmp1);

		}
		if (Pattern.compile("[-+*/]").matcher(strCmp2.trim()).find()) {
			String[] result2 = strCmp2.split("(?<=[-+*/])|(?=[-+*/])");
			if (result2[0].trim().toLowerCase().contains("var")) {
				result2[0] = (String) hm.get(result2[0]);
			}
			if (result2[2].trim().toLowerCase().contains("var")) {
				result2[2] = (String) hm.get(result2[2]);
			}
			
			Double str1 = Double.parseDouble(result2[0]);
			Double str2 = Double.parseDouble(result2[2]);
			Double str3 = 0.00;
			if (result2[1].equals("+")) {
				str3 = str1 + str2;
				System.out.println("str3 : " + str3);
			} else if (result2[1].equals("-")) {
				str3 = str1 - str2;
			} else if (result2[1].equals("*")) {
				str3 = str1 * str2;
			}
			strCmp2 = String.valueOf(Math.round(str3));
			System.out.println(strCmp2);
		}

		if (strCmp1.trim().toLowerCase().contains("var")) {
			strCmp1 = (String) hm.get(strCmp1);
		}
		if (strCmp2.trim().toLowerCase().contains("var")) {
			strCmp2 = (String) hm.get(strCmp2);
		}
		
		System.out.println("cmp strCmp1 : " + strCmp1);
		System.out.println("cmp strCmp2 : " + strCmp2);
		
		if (strCmp1.equals(strCmp2)) {
			System.out.println("Scenario1 passed");
			DriverScript.bResult = true;
		} else {
			Log.error("String1 : " + strCmp1 + " ; String2 : " + strCmp2);
			DriverScript.failedException = "false";
		}
	}

	public void verifyAttributeText(String object, String data){
		try{
			String[] dataText = data.split("=");
			String attribute = dataText[0].trim();
			String attributeExpValue = dataText[1].trim();
			String attributeActValue = objectLocator(object).getAttribute(attribute).trim();
			
			if (attributeExpValue.equalsIgnoreCase("blank")){
				if (attributeActValue.isEmpty()){
					DriverScript.bResult = true;
				}else{
		        	highLightElementAndScreenCapture(object,data);
		        	Log.error("Attribute value is not blank");
		           	DriverScript.failedException = "false";
				}
			}else{
				if (attributeActValue.trim().toUpperCase().contains(attributeExpValue.trim().toUpperCase()) && !attributeExpValue.equals("")){
					DriverScript.bResult = true;	
					Log.info("Expected " + attribute + " value: " + attributeExpValue + " ; Actual " + attribute + " value: "+ attributeActValue);
				}else{
		        	highLightElementAndScreenCapture(object,data);
		        	Log.error("Expected " + attribute + " value: " + attributeExpValue + " ; Actual " + attribute + " value: "+ attributeActValue);
		           	DriverScript.failedException = "false";
				}	
			}
		} catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Object attribute not found --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
		}
	}
	
	public static void dragAndDropElement(String Fromobject, String ToObject) {   
		
		
        WebElement From = objectLocator(Fromobject);
        WebElement To = objectLocator(ToObject);
        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(From)
        .moveToElement(To)
        .release(To)
        .build();
        dragAndDrop.perform();
    }
	
	public static void rightClick(String object, String data) {
        try {
        	WebElement element = objectLocator(object);
            Actions action = new Actions(driver).contextClick(element);
            action.build().perform();
            DriverScript.bResult = true;
            waitFor(object,data);
        } catch (Exception e) {
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Object not found to perform right click --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getLocalizedMessage();
        }
	}
	
	public void imageCompare(String object, String data) {
		try {
			// Image compare using Ocular
			String filePath = Config.Base_Dir+"\\Screenshots\\" + data + "\\" + DriverScript.sTestCaseID + "_" + DriverScript.sTestStepID + ".png";
			 Ocular.config()
			 	   .resultPath(Paths.get(Config.Base_Dir+"/Screenshots/Output"))
			 	   .snapshotPath(Paths.get(Config.Base_Dir+"/Screenshots/Output"))
			 	   .globalSimilarity(100).saveSnapshot(true);
			 System.out.println("Ocular : Screen comparision is done");
			 cmpImage(filePath,"");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	 public static void cmpImage(String fileName, String extra){
	        Path expectedFilePath = Paths.get(fileName);
	        OcularResult result = Ocular.snapshot()
	                                    .from(expectedFilePath)	                           
	                                    .sample()
	                                    .using(driver)
	                                    .compare();
	        System.out.println("Result : " + result.getComparisonStatus());
	        System.out.println("Result : " + result.getSimilarity());
	    }
	 
	 
	

	public static void getGIDSave(String object, String data) throws IOException{
			
			try{
				Log.info("Get text of: "+ object);
				Highlight.highlightElement(objectLocator(object));
				String elemText = objectLocator(object).getText();
				String TestFile = ".\\src\\test\\java\\library\\UserDetails.txt";
				File FC = new File(TestFile);
				FC.createNewFile();//Create file.
				FileWriter FW = new FileWriter(TestFile);
				BufferedWriter BW = new BufferedWriter(FW);
				String[] sTemp=elemText.split("\\(");
				String xx = sTemp[1];
				String[] iTemp = xx.split("\\)");
				String yy = iTemp[0];
				BW.write(yy); 
				BW.close();
				
				}
			catch(Exception e){
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Element is not displayed --- " + e.getMessage());
				DriverScript.bResult = false;
				DriverScript.failedException = e.getMessage();
			}
			}
	
	public static void getTextSave(String object, String data) throws IOException{
		
		try{
			Log.info("Get text of: "+ object);
			Highlight.highlightElement(objectLocator(object));
			String elemText = objectLocator(object).getText();
			String TestFile = ".\\src\\test\\java\\library\\UserDetails.txt";
			File FC = new File(TestFile);
			FC.createNewFile();//Create file.
			FileWriter FW = new FileWriter(TestFile);
			BufferedWriter BW = new BufferedWriter(FW);
			//String[] sTemp=elemText.split("\\(");
			//String xx = sTemp[1];
			//String[] iTemp = xx.split("\\)");
			//String yy = iTemp[0];
			BW.write(elemText); 
			BW.close();
			
			}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element is not displayed --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		}

	public static void readTextEnterinput (String object,String data){
		String Content = "";
		try{
		String TestFile = ".\\src\\test\\java\\library\\UserDetails.txt";
		FileReader FR = new FileReader(TestFile);
		BufferedReader BR = new BufferedReader(FR);
		while((Content = BR.readLine())!= null){
		Highlight.highlightElement(objectLocator(object));
		objectLocator(object).sendKeys(Content);
		Log.info("Login with "+ Content);
		
		}
		
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element is not displayed --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
}

	public static void empsearchandClick(String object,String data){
		String Content = "";
		try{
		String TestFile = ".\\src\\test\\java\\library\\UserDetails.txt";
		FileReader FR = new FileReader(TestFile);
		BufferedReader BR = new BufferedReader(FR);
		while((Content = BR.readLine())!= null){
			Highlight.highlightElement(	driver.findElement(By.xpath("//span[text()='"+Content+"']")));
			driver.findElement(By.xpath("//span[text()='"+Content+"']")).click();;
			
				}
			}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element is not displayed --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	
	}
	
	public static void waitUntilldisplayed(String object, String data){
	
	
	try{
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(objectLocator(object)));
		//wait.until(ExpectedConditions.elementToBeClickable(objectLocator(object)));
		//Highlight.highlightElement(objectLocator(object));
		DriverScript.bResult = true;
	}
	catch(Exception e){
		ScreenshotCapture.takeScreenShot(driver);
		Log.error("Element is not displayed --- " + e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
	}
}

	public static void waitUntillclickable(String object, String data){
		
		
		try{
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.elementToBeClickable(objectLocator(object)));
			//Highlight.highlightElement(objectLocator(object));
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element is not clickable --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void waitUntillInvisible(String obejct, String data){
	
	
	try{
		WebDriverWait wait = new WebDriverWait(driver,100);
		Highlight.highlightElement(objectLocator(obejct));
		wait.until(ExpectedConditions.invisibilityOf(objectLocator(obejct)));
	}
	catch(Exception e){
		ScreenshotCapture.takeScreenShot(driver);
		Log.error("Element is not displayed --- " + e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
	}
}
	
	public static void selectMatchingrecord(String object ,String data){
		try {
		String tt ="//td[span[span[contains(text(),'"+ data +"')]]]/following-sibling::td/div/button";
		Highlight.highlightElement(driver.findElement(By.xpath(tt)));
		driver.findElement(By.xpath(tt)).click();;
		
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Team Not Present--- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		
		
	}
	
	public static void verifyobjectpresent(String object ,String data){
		try {
			
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement tt =driver.findElement(By.xpath("//*[contains(text(),'" + data + "')]"));
		//tt.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='5px solid green'", tt);
		Highlight.highlightElement(tt);
		
//		if(driver.findElement(By.xpath("//*[text()='OK']")).isDisplayed())
//		{
//			driver.findElement(By.xpath("//*[text()='OK']")).click();
//			driver.findElement(By.xpath("//*[contains(@class,'searchbtn')]")).click();
//			Highlight.highlightElement(driver.findElement(By.xpath("//*[contains(text(),'" + data + "')]")));
//		}
//		else if(driver.findElement(By.xpath("//*[contains(text(),'" + data + "')]")).isDisplayed()){
//			Highlight.highlightElement(driver.findElement(By.xpath("//*[contains(text(),'" + data + "')]")));
//		}
//		else{
//			Log.info("Object Not Present on UI");
//			ScreenshotCapture.takeScreenShot(driver);
//		}
		
		
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Object Not Present--- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		
		
	}
	
	public static void verifyobjectNOTpresent(String object ,String data){
		try {
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Log.info("Verify Element is NOT present " + data);
			
			
			
			boolean exists = driver.findElements(By.xpath("//*[contains(text(),'" + data + "')]")).size()==0;
			
			if (exists) {
				//DriverScript.bResult = false;
				DriverScript.bResult = true;
				
			} 
			
			else {
				
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[contains(text(),'" + data + "')]")));
				Thread.sleep(500); 
				ScreenshotCapture.takeScreenShot(driver);
				Highlight.highlightElement(driver.findElement(By.xpath("//*[contains(text(),'" + data + "')]")));
				Log.error("Element  present in the screen");
				DriverScript.bResult = false;
				DriverScript.failedException = "Element  present in the screen";
			
			}
		
		}
		catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Object Not Present--- " + e.getMessage());
			DriverScript.bResult = true;
			DriverScript.failedException = e.getMessage();
		}
		
		
	}
	
	
	
	
	
	
	public static void howerandclickbyCord(String object, String data)
	{
		
		//try {
		//WebElement extteam = driver.findElement(By.id("F0_ctl00_ControlRef8_ControlRef15_ControlRef15_ControlRef2_ContainerTemplate2_RA_Label11"));	
		WebElement we = objectLocator(object);
		Actions act = new Actions(driver);
		Point pt = we.getLocation();
		 
		int NumberX=pt.getX();
		int NumberY=pt.getY();
		System.out.println(NumberX);
		System.out.println(NumberY);
		//act.moveToElement(we).build().perform();
		act.moveToElement(we).perform();
		
		
//		catch(Exception e){
//			ScreenshotCapture.takeScreenShot(driver);
//			Log.error("Expected Team Name not Displayed--- " + e.getMessage());
//			DriverScript.bResult = false;
//			DriverScript.failedException = e.getMessage();
//		}
		
	}
	
	public static void messagedisplayed(String object,String data)
		{
		try{
			
			//String errtxt = objectLocator(object).getCssValue("value");
			Highlight.highlightElement(objectLocator(object));
			String errtxt = objectLocator(object).getAttribute("value");
			//System.out.println("errtxt is :"  + errtxt);
			if ((errtxt != null)&(errtxt.contains(data))){
				Log.info(errtxt);
				DriverScript.bResult = true;
				System.out.println("Actual Text " + errtxt + " and " + " Expected Text " + data);
				
			}
			else{
			
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Expected Message Not Displayed");
			DriverScript.bResult = false;
			DriverScript.failedException = ("Actual Text " + errtxt + " and " + " Expected Text " + data);
			//DriverScript.failedException = "false";
			}
		}
			catch(Exception e){
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Expected Message Not Displayed");
				DriverScript.bResult = false;
				DriverScript.failedException = e.getMessage();
			}
		}	
		
	public static void hoveronelementClick(String object,String data ) throws InterruptedException
		
		{
			
			try{
			WebElement hoverEle = objectLocator(object);
			Highlight.highlightElement(hoverEle);
			Actions act = new Actions(driver);
		
			WebElement hoverClickEle = objectLocator(data);
			
			act.moveToElement(hoverEle).clickAndHold().build().perform();
			
			Log.info("Hover on the element");
			Highlight.highlightElement(hoverClickEle);
			hoverClickEle.click();
			Log.info("Clicked on the desired option");
			}
			catch(Exception e)
			{
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Not Able to Click on the --- " + e.getMessage());
				DriverScript.bResult = false;
				DriverScript.failedException = e.getMessage();	
				
				
			}
			
			}
		
	public static void highlightElement(String object,String data) throws InterruptedException
		{
	try {
		 WebElement element = objectLocator(object);
		 JavascriptExecutor js = (JavascriptExecutor) driver;
	   	 js.executeScript("arguments[0].setAttribute('style', 'background: dullwhite; border: 2px solid blue;');", element);
		 Thread.sleep(1000);
		 js.executeScript("arguments[0].setAttribute('style',arguments[1]);", element, "");
	}
	catch (InterruptedException e) {
		Log.error("Unable to highlight the element" + e.getMessage());
	} 
	 }
	
	
	public static void clickenter(String object, String data)
	{
		
		try {
			 WebElement element = objectLocator(object);
			 element.sendKeys(Keys.ENTER);
			
//			Actions act = new Actions(driver);
//			act.sendKeys(Keys.ENTER);
		}
		
		catch(Exception e)
		{
			Log.error("unable to click enter"+e.getMessage());
		}
	}
	
	
	public static void clickusingjava(String object, String data)
	{
		
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		try 
		
		{
			
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(data)));
			//element.click();
			// WebElement element = objectLocator(object);
			 
		 JavascriptExecutor executor = (JavascriptExecutor)driver;
		 executor.executeScript("arguments[0].click();", element);	
		 
		 DriverScript.bResult = true;
//			
		}
		
		catch(Exception e)
		{
			Log.error("unable to click"+e.getMessage());
			
			DriverScript.bResult = false;
			
		}
	}
	
	
	
	public static void clickusingjavanew(String object, String data)
	{
		
		
		
		try 
		
		{
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			//WebDriverWait wait = new WebDriverWait(driver, 30);
			
			WebElement element = objectLocator(object);
			//WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(data)));
	
			 
		 JavascriptExecutor executor = (JavascriptExecutor)driver;
		 executor.executeScript("arguments[0].click();", element);	
		 
		 DriverScript.bResult = true;
//			
		}
		
		catch(Exception e)
		{
			Log.error("unable to click"+e.getMessage());
			
			DriverScript.bResult = false;
			
		}
	}
	
	
	
	public static void entertextusingjava(String object, String data)
	{
		
		try 
		
		{
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			//WebDriverWait wait = new WebDriverWait(driver, 30);
			
			WebElement element = objectLocator(object);
			//WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(data)));
			 
		 JavascriptExecutor executor = (JavascriptExecutor)driver;
		 
		 
		 executor.executeScript("arguments[0].setAttribute('value', '" + data +"')", element);
		 
		 System.out.println(element.getText());
		 
		 
		 DriverScript.bResult = true;
//			
		}
		
		catch(Exception e)
		{
			Log.error("unable to click"+e.getMessage());
			
			DriverScript.bResult = false;
			
		}
	}
	
	
	public static void handlestale(String object, String data)
	{
		
		try 
		
		{
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			WebDriverWait wait = new WebDriverWait(driver, 30);
			
			WebElement element = objectLocator(object);
			
			element.sendKeys(data);
			 
//		 JavascriptExecutor executor = (JavascriptExecutor)driver;
//		 
//		 
//		 executor.executeScript("arguments[0].setAttribute('value', '" + data +"')", element);
//		 
//		 System.out.println(element.getText());
		 
		 
		 DriverScript.bResult = true;
//			
		}
		
		catch(Exception e)
		{
			
WebElement element = objectLocator(object);
			
			element.sendKeys(data);
			Log.error("unable to get the element"+e.getMessage());
			
			DriverScript.bResult = false;
			
		}
	}
	
	public static void verifyrows(String object, String data)
	{
		
			
			try{
				int count = 0;
				Log.info("Get total row count "+ object);
				//count = driver.findElements(By.xpath(object)).size();
				JavascriptExecutor js = (JavascriptExecutor) driver; 
				
				
				List elements = (List) js.executeScript("return document.getElementsByTagName('tr');");
				count=elements.size();
				int expectedcount=Integer.parseInt(data);
				
				if(count==expectedcount+1)
				{
					DriverScript.bResult = true;	
					Log.info("The values are matching"+"The row count displayed is"+count);
					
				}
					
				else
				{
					
					ScreenshotCapture.takeScreenShot(driver);
					Log.error("values are not matching"+"The actual count is"+count);
					//Log.info();
					DriverScript.bResult = false;
		
					
				}
				
			}
	catch(Exception e){
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Element not present --- " + e.getMessage());
				DriverScript.bResult = false;
				DriverScript.failedException = e.getMessage();
					 
		}

	
	
}
	
	
	public static void verfiySystemTypeRows(String object, String data){
		int count = 0;
		try{
			Log.info("Get total row count "+ object);
			count = driver.findElements(By.xpath(object)).size();
			if(count==5)
			{
				Log.info("values are matching");
				DriverScript.bResult = true;
			}
			else
			{
				Log.info("Values are not matching");
				DriverScript.bResult = false;
			}
			//Log.info(count);
		}catch(Exception e){
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element not present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
				 
	}
	
	
	public static void verifyuserresults(String object, String data)
	{
		try {
			
			Thread.sleep(3000);
			
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-md-3 ng-binding'][1]")));
			Thread.sleep(1000);
	//	WebElement licenseusers = driver.findElement(By.xpath("//div[@class='col-md-3 ng-binding'][1]"));
		
		
		
		String licenseuserstext=driver.findElement(By.xpath("//div[@class='col-md-3 ng-binding'][1]")).getText();
		String[] licenseuserstextsubstring = licenseuserstext.split("/");
		String licenseusersnumber=licenseuserstextsubstring[1].trim();
		
		
		
		
		Log.info(licenseuserstext);
		Log.info(licenseuserstextsubstring[1]);
		Log.info(licenseusersnumber+"is the license users number");
		
		System.out.println(licenseusersnumber+"is the license users number");
		
		Thread.sleep(2000);
		
		driver.navigate().back();
		Thread.sleep(2000);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#divUserManagementTab")));
		WebElement Usermanagement = driver.findElement(By.cssSelector("#divUserManagementTab"));
		
		Usermanagement.click();
		Thread.sleep(6000);
		WebElement Usermanagementusers = driver.findElement(By.xpath("//*[@class='ng-binding'][2]"));
		
        String  Usermanagementuserstext =Usermanagementusers.getText();
		
		String Usermanagementusersnumber=Usermanagementuserstext.trim();
		Log.info(Usermanagementuserstext);
		Log.info(Usermanagementusersnumber+"is the user management users number");
		
		System.out.println(Usermanagementusersnumber+"is the user management users number");
		
		if(licenseusersnumber==Usermanagementusersnumber)
		{
			DriverScript.bResult = true;
			Log.info("The values are matching");
			
		}
		
		else
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("The values are not matching"+"The actual value is"+Usermanagementusersnumber);
			DriverScript.bResult = false;
			
		}
		
		
		
		}
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Element not present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		}

	
	
	
	public static void verifytheNumberofSafetyManager(String object, String data)
	{
		try {
		//WebDriverWait wait = new WebDriverWait(driver, 30);
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
		
		List<WebElement> systemtypes=driver.findElements(By.xpath("//td[3][@class='padding15Px ng-binding defaultData' and contains(text(),'SafetyManager')]"));
		
		int size=systemtypes.size();
		

		int expectedcount=Integer.parseInt(data);
		
		if(size==expectedcount)
		{
			Log.info("values are matching"+size);
			DriverScript.bResult = true;
		}
		
		
		else
		{
			Log.info("Values are not matching and the actual count is"+size);
			ScreenshotCapture.takeScreenShot(driver);
			DriverScript.bResult = false;
		}
		}
		
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Elements not present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	
	public static void verifytheNumberofSafetyManagersetup(String object, String data)
	{
		try {
		//WebDriverWait wait = new WebDriverWait(driver, 30);
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
		
		List<WebElement> systemtypes=driver.findElements(By.xpath("//td[3][@class='padding15Px ng-binding defaultData' and contains(text(),'SafetyManager')]"));
		
		int size=systemtypes.size();
		
		int actualcount = size;
		
		System.out.println(size+"is the actual number of safety managers");
		
		
		final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin04\",\r\n" +
		        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
		    System.out.println(POST_PARAMS);
		    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
		    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		    postConnection.setRequestMethod("POST");
		    postConnection.setRequestProperty("clienttype", "1");
		   postConnection.setRequestProperty("Content-Type", "application/json");
		    postConnection.setDoOutput(true);
		    OutputStream os = postConnection.getOutputStream();
		    os.write(POST_PARAMS.getBytes());
		    os.flush();
		    os.close();
		    int responseCode = postConnection.getResponseCode();
		    System.out.println("POST Response Code :  " + responseCode);
		    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		    BufferedReader in = new BufferedReader(new InputStreamReader(
		          postConnection.getInputStream()));
		      String inputLine;
		      StringBuffer response = new StringBuffer();
		      while ((inputLine = in .readLine()) != null) {
		          response.append(inputLine);
		      } in .close();
		       
		      String accesstoken =response.toString();
		      System.out.println(response.toString());
		      System.out.println(accesstoken);
		      
		      
		      String accesstokensubstring[] = accesstoken.split(":");
		      
		      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
		      
		      
		      String actualtoken=acesstokensubstring2[0];
		      
		      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
		      
		      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
		      
		      System.out.println(actualtoken1+"is the new token");
		      
		      
		      URL obj1 = new URL("https://mtsstoragewebapiic200.azurewebsites.net/api/setup?systemtype=2");
		      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
		      postConnection1.setRequestMethod("GET");
		      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
		      postConnection1.setRequestProperty("Content-Type", "application/json");
		      postConnection1.setDoOutput(true);
		      int responseCode1 = postConnection1.getResponseCode();
		      System.out.println("POST Response Code :  " + responseCode1);
		      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
		      BufferedReader in1 = new BufferedReader(new InputStreamReader(
		            postConnection1.getInputStream()));
		        String inputLine1;
		        StringBuffer response1 = new StringBuffer();
		        while ((inputLine1 = in1 .readLine()) != null) {
		            response1.append(inputLine1);
		        } in1 .close();
		        
		        
		        System.out.println(response1.toString());
		        
		        Log.info(response1.toString());
		        
		        
		        String responsesubstring01 = response1.toString();
		        
		        System.out.println(responsesubstring01);
		        
		        String responsesubstring02[] = responsesubstring01.split("total");
		    
		        System.out.println(responsesubstring02[1]);
		        
		        String responsesubstring03[]=responsesubstring02[1].split(":");
		        
		        System.out.println(responsesubstring03[1]);
		        
		        String responsesubstring04[]=responsesubstring03[1].split(",");
		
		        System.out.println(responsesubstring04[0]);
		        
		        int expectedcount = Integer.parseInt(responsesubstring04[0]);
		        
		        if (actualcount==expectedcount)
		        {
		        	DriverScript.bResult = true;
		        	Log.info("The count is matching");
		        }
		        
		        else
		        {
		        	ScreenshotCapture.takeScreenShot(driver);
					Log.error("Count is not matching and the actual count is --- "+actualcount );
					DriverScript.bResult = false;
		        }
		        
		        

		
		}
		
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Elements not present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	public static void verifytheNumberofSafetyManagerlesson(String object, String data)
	{
		try {
		//WebDriverWait wait = new WebDriverWait(driver, 30);
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
		
		List<WebElement> systemtypes=driver.findElements(By.xpath("//td[3][@class='padding15Px ng-binding defaultData' and contains(text(),'SafetyManager')]"));
		
		int size=systemtypes.size();
		
		int actualcount = size;
		
		System.out.println(size+"is the actual number of safety managers");
		
		
		final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin04\",\r\n" +
		        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
		    System.out.println(POST_PARAMS);
		    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
		    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		    postConnection.setRequestMethod("POST");
		    postConnection.setRequestProperty("clienttype", "1");
		   postConnection.setRequestProperty("Content-Type", "application/json");
		    postConnection.setDoOutput(true);
		    OutputStream os = postConnection.getOutputStream();
		    os.write(POST_PARAMS.getBytes());
		    os.flush();
		    os.close();
		    int responseCode = postConnection.getResponseCode();
		    System.out.println("POST Response Code :  " + responseCode);
		    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		    BufferedReader in = new BufferedReader(new InputStreamReader(
		          postConnection.getInputStream()));
		      String inputLine;
		      StringBuffer response = new StringBuffer();
		      while ((inputLine = in .readLine()) != null) {
		          response.append(inputLine);
		      } in .close();
		       
		      String accesstoken =response.toString();
		      System.out.println(response.toString());
		      System.out.println(accesstoken);
		      
		      
		      String accesstokensubstring[] = accesstoken.split(":");
		      
		      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
		      
		      
		      String actualtoken=acesstokensubstring2[0];
		      
		      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
		      
		      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
		      
		      System.out.println(actualtoken1+"is the new token");
		      
		      
		      URL obj1 = new URL("https://mtsstoragewebapiic200.azurewebsites.net/api/lesson?systemtype=2");
		      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
		      postConnection1.setRequestMethod("GET");
		      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
		      postConnection1.setRequestProperty("Content-Type", "application/json");
		      postConnection1.setDoOutput(true);
		      int responseCode1 = postConnection1.getResponseCode();
		      System.out.println("POST Response Code :  " + responseCode1);
		      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
		      BufferedReader in1 = new BufferedReader(new InputStreamReader(
		            postConnection1.getInputStream()));
		        String inputLine1;
		        StringBuffer response1 = new StringBuffer();
		        while ((inputLine1 = in1 .readLine()) != null) {
		            response1.append(inputLine1);
		        } in1 .close();
		        
		        
		        System.out.println(response1.toString());
		        
		        Log.info(response1.toString());
		        
		        
		        String responsesubstring01 = response1.toString();
		        
		        System.out.println(responsesubstring01);
		        
		        String responsesubstring02[] = responsesubstring01.split("total");
		    
		        System.out.println(responsesubstring02[1]);
		        
		        String responsesubstring03[]=responsesubstring02[1].split(":");
		        
		        System.out.println(responsesubstring03[1]);
		        
		        String responsesubstring04[]=responsesubstring03[1].split(",");
		
		        System.out.println(responsesubstring04[0]);
		        
		        int expectedcount = Integer.parseInt(responsesubstring04[0]);
		        
		        if (actualcount==expectedcount)
		        {
		        	DriverScript.bResult = true;
		        	Log.info("The count is matching");
		        }
		        
		        else
		        {
		        	ScreenshotCapture.takeScreenShot(driver);
					Log.error("Count is not matching and the actual count is --- "+actualcount );
					DriverScript.bResult = false;
		        }
		        
		        

		
		}
		
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Elements not present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	
	
	
	public static void verifytheNumberofExperion(String object, String data)
	{
		try {
		//WebDriverWait wait = new WebDriverWait(driver, 30);
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
		
		List<WebElement> systemtypes=driver.findElements(By.xpath("//td[3][@class='padding15Px ng-binding defaultData' and contains(text(),'Experion')]"));
		
		int size1=systemtypes.size();
		

		int expectedcount=Integer.parseInt(data);
		
		
		List<WebElement> systemtypes2=driver.findElements(By.xpath("//td[3][@class='padding15Px ng-binding' and contains(text(),'Experion')]"));
		
		int size2=systemtypes2.size();
				
		int size = size1+size2;
		
		if(size==expectedcount)
		{
			Log.info("values are matching"+size);
			DriverScript.bResult = true;
		}
		
		
		else
		{
			
			Log.info("Values are not matching and the actual count is"+size);
			ScreenshotCapture.takeScreenShot(driver);
			DriverScript.bResult = false;
		}
		}
		
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Elements not present --- " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
	}
	
	
	public static void verifythenumberofexperionlesson(String object,String data)
	{
		
		
		try {
			//WebDriverWait wait = new WebDriverWait(driver, 30);
			//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
			
			List<WebElement> systemtypes=driver.findElements(By.xpath("//td[3][@class='padding15Px ng-binding defaultData' and contains(text(),'Experion')]"));
			
			
			//td[3][@class='padding15Px ng-binding defaultData' and contains(text(),'Experion')]
			int size=systemtypes.size();
			
			int actualcount = size;
			
			System.out.println(size+"is the actual number of experion");
			
			
			final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin04\",\r\n" +
			        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
			    System.out.println(POST_PARAMS);
			    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
			    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
			    postConnection.setRequestMethod("POST");
			    postConnection.setRequestProperty("clienttype", "1");
			   postConnection.setRequestProperty("Content-Type", "application/json");
			    postConnection.setDoOutput(true);
			    OutputStream os = postConnection.getOutputStream();
			    os.write(POST_PARAMS.getBytes());
			    os.flush();
			    os.close();
			    int responseCode = postConnection.getResponseCode();
			    System.out.println("POST Response Code :  " + responseCode);
			    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
			    BufferedReader in = new BufferedReader(new InputStreamReader(
			          postConnection.getInputStream()));
			      String inputLine;
			      StringBuffer response = new StringBuffer();
			      while ((inputLine = in .readLine()) != null) {
			          response.append(inputLine);
			      } in .close();
			       
			      String accesstoken =response.toString();
			      System.out.println(response.toString());
			      System.out.println(accesstoken);
			      
			      
			      String accesstokensubstring[] = accesstoken.split(":");
			      
			      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
			      
			      
			      String actualtoken=acesstokensubstring2[0];
			      
			      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
			      
			      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
			      
			      System.out.println(actualtoken1+"is the new token");
			      
			      
			      URL obj1 = new URL("https://mtsstoragewebapiic200.azurewebsites.net/api/lesson?systemtype=1");
			      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
			      postConnection1.setRequestMethod("GET");
			      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
			      postConnection1.setRequestProperty("Content-Type", "application/json");
			      postConnection1.setDoOutput(true);
			      int responseCode1 = postConnection1.getResponseCode();
			      System.out.println("POST Response Code :  " + responseCode1);
			      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
			      BufferedReader in1 = new BufferedReader(new InputStreamReader(
			            postConnection1.getInputStream()));
			        String inputLine1;
			        StringBuffer response1 = new StringBuffer();
			        while ((inputLine1 = in1 .readLine()) != null) {
			            response1.append(inputLine1);
			        } in1 .close();
			        
			        
			        System.out.println(response1.toString());
			        
			        Log.info(response1.toString());
			        
			        
			        String responsesubstring01 = response1.toString();
			        
			        System.out.println(responsesubstring01);
			        
			        String responsesubstring02[] = responsesubstring01.split("total");
			    
			        System.out.println(responsesubstring02[1]);
			        
			        String responsesubstring03[]=responsesubstring02[1].split(":");
			        
			        System.out.println(responsesubstring03[1]);
			        
			        String responsesubstring04[]=responsesubstring03[1].split(",");
			
			        System.out.println(responsesubstring04[0]);
			        
			        int expectedcount = Integer.parseInt(responsesubstring04[0]);
			        
			        if (actualcount==expectedcount)
			        {
			        	DriverScript.bResult = true;
			        	Log.info("The count is matching");
			        }
			        
			        else
			        {
			        	ScreenshotCapture.takeScreenShot(driver);
						Log.error("Count is not matching and the actual count is --- "+actualcount );
						DriverScript.bResult = false;
			        }
			        
			        

			
			}
			
			
			catch(Exception e)
			{
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Elements not present --- " + e.getMessage());
				DriverScript.bResult = false;
				DriverScript.failedException = e.getMessage();
			}

		
	}
	
	public static void verifythenumberofexperionsetup(String object,String data)
	{
		
		
		try {
			//WebDriverWait wait = new WebDriverWait(driver, 30);
			//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(object)));
			
			List<WebElement> systemtypes=driver.findElements(By.xpath("//td[3][@class='padding15Px ng-binding defaultData' and contains(text(),'Experion')]"));
			
			
			//td[3][@class='padding15Px ng-binding defaultData' and contains(text(),'Experion')]
			int size=systemtypes.size();
			
			int actualcount = size;
			
			System.out.println(size+"is the actual number of experion");
			
			
			final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin04\",\r\n" +
			        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
			    System.out.println(POST_PARAMS);
			    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
			    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
			    postConnection.setRequestMethod("POST");
			    postConnection.setRequestProperty("clienttype", "1");
			   postConnection.setRequestProperty("Content-Type", "application/json");
			    postConnection.setDoOutput(true);
			    OutputStream os = postConnection.getOutputStream();
			    os.write(POST_PARAMS.getBytes());
			    os.flush();
			    os.close();
			    int responseCode = postConnection.getResponseCode();
			    System.out.println("POST Response Code :  " + responseCode);
			    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
			    BufferedReader in = new BufferedReader(new InputStreamReader(
			          postConnection.getInputStream()));
			      String inputLine;
			      StringBuffer response = new StringBuffer();
			      while ((inputLine = in .readLine()) != null) {
			          response.append(inputLine);
			      } in .close();
			       
			      String accesstoken =response.toString();
			      System.out.println(response.toString());
			      System.out.println(accesstoken);
			      
			      
			      String accesstokensubstring[] = accesstoken.split(":");
			      
			      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
			      
			      
			      String actualtoken=acesstokensubstring2[0];
			      
			      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
			      
			      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
			      
			      System.out.println(actualtoken1+"is the new token");
			      
			      
			      URL obj1 = new URL("https://mtsstoragewebapiic200.azurewebsites.net/api/setup?systemtype=1");
			      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
			      postConnection1.setRequestMethod("GET");
			      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
			      postConnection1.setRequestProperty("Content-Type", "application/json");
			      postConnection1.setDoOutput(true);
			      int responseCode1 = postConnection1.getResponseCode();
			      System.out.println("POST Response Code :  " + responseCode1);
			      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
			      BufferedReader in1 = new BufferedReader(new InputStreamReader(
			            postConnection1.getInputStream()));
			        String inputLine1;
			        StringBuffer response1 = new StringBuffer();
			        while ((inputLine1 = in1 .readLine()) != null) {
			            response1.append(inputLine1);
			        } in1 .close();
			        
			        
			        System.out.println(response1.toString());
			        
			        Log.info(response1.toString());
			        
			        
			        String responsesubstring01 = response1.toString();
			        
			        System.out.println(responsesubstring01);
			        
			        String responsesubstring02[] = responsesubstring01.split("total");
			    
			        System.out.println(responsesubstring02[1]);
			        
			        String responsesubstring03[]=responsesubstring02[1].split(":");
			        
			        System.out.println(responsesubstring03[1]);
			        
			        String responsesubstring04[]=responsesubstring03[1].split(",");
			
			        System.out.println(responsesubstring04[0]);
			        
			        int expectedcount = Integer.parseInt(responsesubstring04[0]);
			        
			        if (actualcount==expectedcount)
			        {
			        	DriverScript.bResult = true;
			        	Log.info("The count is matching");
			        }
			        
			        else
			        {
			        	ScreenshotCapture.takeScreenShot(driver);
						Log.error("Count is not matching and the actual count is --- "+actualcount );
						DriverScript.bResult = false;
			        }
			        
			        

			
			}
			
			
			catch(Exception e)
			{
				ScreenshotCapture.takeScreenShot(driver);
				Log.error("Elements not present --- " + e.getMessage());
				DriverScript.bResult = false;
				DriverScript.failedException = e.getMessage();
			}

		
	}
	
	public static void getPassword(String object, String data) throws InterruptedException
	{
		
		try {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		try {
			
//			WebElement mailelement = driver.findElement(By.xpath("//span[contains(text(),'Honeywell Skills In.')]/ancestor::tr[1]"));
//			
//			mailelement.click();
			List<WebElement> rows = driver.findElements(By.xpath("//span[contains(text(),'Honeywell Skills In.')]/ancestor::tr"));
			
			int rowsize= rows.size();
			
			String rowsize1= String.valueOf(rowsize);
			
			Log.info(rowsize1);
			
			int actualrows = rowsize/2;
			
			Log.info(actualrows+"is the actual rows");
			rows.get(actualrows+2).click();
//			if(rowsize==1)
//			{
//				rows.get(0).click();
//			}
//			else
//			{
//				rows.get(1).click();
//			}
//			
			//rows.get(1).click();
//		}
//		
//		catch(Exception e){
//			
//			Log.info("Elements not present");
//			
//		}
		
		Thread.sleep(4000);
	
		String text= driver.findElement(By.xpath("//label[contains(text(),'Password :')]/*")).getText();
		
		Log.info(text);
		
		System.out.println(text+"is the password");
		Thread.sleep(4000);
		
		WebElement applicationlink = driver.findElement(By.xpath("//a[contains(text(),'Click here to launch the Management Console')]"));
		
		applicationlink.click();
		
		String parentWinHandle = driver.getWindowHandle();
		
		Set<String> windowhandles= driver.getWindowHandles();
		
			
		
		
for(String s:windowhandles)
	{
	Log.info(s.toString());
	if(!s.equals(parentWinHandle))
	{
		 driver.switchTo().window(s);	
		WebDriverWait wait = new WebDriverWait(driver, 40);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='btnSignIn']")));
		 
		element.click();
		
		  
		  driver.findElement(By.xpath("//input[@id='uName']")).sendKeys(data);

		 driver.findElement(By.xpath("//input[@id='password']")).sendKeys(text);
		
	}


	}


DriverScript.bResult = true;

		}
		
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.error(e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		
		
		
	}

public static void navigateback(String object, String data)
{
	
	
	try {
		
		driver.navigate().back();
		Thread.sleep(1000);
		Log.info("Navigated to the previous page");
		DriverScript.bResult = true;
		
		
	}
	
	
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.error("Unable to navigate back " + e.getMessage());
			DriverScript.bResult = false;
			DriverScript.failedException = e.getMessage();
		}
		

}


public static void  verifytoastmessage(String object, String data)
{
	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	try {
	
	
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	//String theTextIWant = js.executeScript("return arguments[0].innerHTML;",driver.findElement(By.xpath("//span[@itemprop='telephone']")));
	
	WebDriverWait wait = new WebDriverWait(driver, 30);
	WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(object)));
	
	String text=el.getText();
	
	Log.info(text+"Actual text");
	Log.info(data+"Expected text");
	
	if(text.contains(data))
	{
		js.executeScript("arguments[0].style.border='5px solid green'", el);
		Log.info("Text is matching");
		DriverScript.bResult = true;
	}
	
	else
	{
		ScreenshotCapture.takeScreenShot(driver);
		Log.info("Text is not matching");
		DriverScript.bResult = false;
		
	}
	
	}
	
	catch(Exception e){
		ScreenshotCapture.takeScreenShot(driver);
		Log.error("Object Not Present--- " + e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
	}
	
	
}



	

//WebDriverWait wait = new WebDriverWait(driver, 20);
//
//WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='btnSignIn']")));
//
//element.click();

//driver.findElement(By.xpath("//*[@id='btnSignIn']")).click();

public static void gethiddentext(String object, String data) {
	
	
	
	
	WebElement message =driver.findElement(By.xpath("//div[@class='alert alert-danger fade in divSuccessNotify ng-binding ng-hide']"));
	
	String message1=message.getAttribute("innerHTML");
	
	String messagenew =message.getAttribute("innerText");
	String message5=message.getAttribute("value");
	
	String errormsg= message.getText();
	
	
	//WebElement message1 =driver.findElement(By.xpath("//div[@ng-show='showErrorMessage']//*[1]"));
	
	WebElement message2 =driver.findElement(By.xpath("//div[@ng-show='showErrorMessage']//*[2]"));
	
	//WebElement message3 =driver.findElement(By.xpath("//div[@ng-show='showErrorMessage']//*[3]"));
	
	//WebElement message5 =driver.findElement(By.xpath("//div[@ng-show='showErrorMessage']//*[3]"));
	
	//String messageText = ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML", message);
	JavascriptExecutor js = (JavascriptExecutor)driver;
	
	String messageText =(String) js.executeScript("return arguments[0].innerHTML", message);
	
	String messageText1 =(String) js.executeScript("return arguments[0].innerHTML", message1);
	String messageText2 =(String) js.executeScript("return arguments[0].innerHTML", message2);
	
	//String messageText3 =(String) js.executeScript("return arguments[0].innerHTML", message3);
	//String n=(String) js.executeScript("return document.getElementsByClassName('alert alert-danger fade in divSuccessNotify ng-binding ng-hide').value;");
	System.out.println(messageText);
	Log.info(messageText);
	
	System.out.println(messageText1);
	Log.info(messageText1);
	

	System.out.println(messageText2);
	Log.info(messageText2);
	
//	System.out.println(messageText3);
//	Log.info(messageText3);
	

	System.out.println(errormsg);
	Log.info(errormsg);
	
	System.out.println(messagenew+"1");
	Log.info(messagenew);
	
	
	System.out.println(message1+"2");
	Log.info(message1);
	System.out.println(message5+"3");
	Log.info(message5);
	
	
}

public static void getchild(String object, String data)
{
	
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	List<WebElement> childelements = driver.findElements(By.xpath("//div[@class='alert alert-danger fade in divSuccessNotify ng-binding ng-hide']//*"));
	
	
JavascriptExecutor js = (JavascriptExecutor)driver;
	
	String messageText =(String) js.executeScript("return arguments[0].outerHTML", childelements);
	
	Log.info(messageText);
	
	
	for(WebElement a:childelements)
	{
		String text = a.getText();
		Log.info(text);
	}
	
	for(WebElement a:childelements)
	{
		String text = a.getAttribute("innerHTML");
		Log.info(text+"innerHTML");
	}
}


public static void resizebrowser(String object, String data)
{
	
	try {
	  Dimension d = new Dimension(800,480);
	  driver.manage().window().setSize(d);
	  DriverScript.bResult = true;
	  
	}
	
	catch(Exception e)
	{
		
		Log.error("Unable to resize browser--- " + e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
		
	}
}



public static void zoomout(String object, String data)
{
	try {
		
		
		Robot robot = new Robot();
		
		for(int i=0;i<3;i++)
		{
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		 robot.keyPress(KeyEvent.VK_SUBTRACT);
		 robot.keyRelease(KeyEvent.VK_SUBTRACT);
		 robot.keyRelease(KeyEvent.VK_CONTROL);
		 
		}
	} catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


public static void waituntilclickable(String object, String data) 
{
	
	try {
   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	//String theTextIWant = js.executeScript("return arguments[0].innerHTML;",driver.findElement(By.xpath("//span[@itemprop='telephone']")));
	
	WebDriverWait wait = new WebDriverWait(driver, 30);
	WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(data)));
	js.executeScript("arguments[0].style.border='3px dotted blue'", el);
	}
	
	catch(Exception e){
		ScreenshotCapture.takeScreenShot(driver);
		Log.error("Object Not Present--- " + e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
	
	
	
}
	
}


public static void getOtp(String object, String data) throws InterruptedException
{
	//driver.navigate().to("https://accounts.google.com/ServiceLogin/signinchooser?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	try {
		WebDriverWait wait = new WebDriverWait(driver, 45);
		Robot robot = new Robot();                          
		robot.keyPress(KeyEvent.VK_CONTROL); 
		robot.keyPress(KeyEvent.VK_T); 
		robot.keyRelease(KeyEvent.VK_CONTROL); 
		robot.keyRelease(KeyEvent.VK_T);
		
		Thread.sleep(3000);
		String parentWinHandle = driver.getWindowHandle();
		Set<String> windowhandles= driver.getWindowHandles();
		
	for(String s:windowhandles)
	{
	Log.info(s.toString());
	if(!s.equals(parentWinHandle))
	{
		//driver.switchTo().window(s);
	
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.navigate().to("https://accounts.google.com/ServiceLogin/signinchooser?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
		WebElement username = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']")));
		username.click();
		username.sendKeys(object);
		WebElement Nextbtn_xpath = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Next')]")));
		Nextbtn_xpath.click();

		WebElement password_xpath = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='password']")));
		try {
		//password_xpath.click();
		password_xpath.sendKeys(data);
		}
		catch(Exception e)
		{
			Log.info(e.getMessage());
			DriverScript.bResult = false;
		}
		try {
			password_xpath.sendKeys(Keys.ENTER);
		//Nextbtn_xpath.click();
	}
	catch(Exception e)
	{
		Log.info(e.getMessage());
		DriverScript.bResult = false;
	}
		WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Honeywell Skills In.')]/ancestor::tr")));
		
		
		List<WebElement> rows = driver.findElements(By.xpath("//span[contains(text(),'Honeywell Skills In.')]/ancestor::tr"));
		
		int rowsize= rows.size();
		
		String rowsize1= String.valueOf(rowsize);
		
		Log.info(rowsize1+"is the number of elements matching the criteria");
		
		int actualsize = rowsize/2;
		
		Log.info(String.valueOf(actualsize+"is the actual size"));
		
		//int actualelement=actualsize+1;
		
		rows.get(0).click();
		
		
		Thread.sleep(4000);
		
		
		List<WebElement> OTPS = driver.findElements(By.xpath("//label[contains(text(),'OTP:')]"));
		int OTPnumber= OTPS.size();
		String text =OTPS.get(OTPnumber-1).getText();
		String text1[]=text.split(":", 0);
		String OTP = text1[1].trim();
		
		Log.info(text);
		Thread.sleep(4000);
		Log.info(text1[0]);
		Log.info(text1[1]);
		
		
		
		String parentWinHandle1 = driver.getWindowHandle();
		
		Set<String> windowhandles1= driver.getWindowHandles();
		
		
		
		
	for(String s1:windowhandles1)
	{
	Log.info(s1.toString());
	if(!s1.equals(parentWinHandle1))
	{
		 driver.switchTo().window(s1);	
	  
	 Thread.sleep(3000);
	 
	 driver.findElement(By.xpath("//input[@name='inputOTP']")).sendKeys(OTP);


	}

	}
		
	}
	}
	}
	
	catch(Exception e){
		
		Log.info("Elements not present");
		DriverScript.bResult = false;
		
	}
	
	
}


public static void scrollintoelement(String object, String data)
{
	try
	{
	WebElement element =  objectLocator(object);
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	//webElement = driver.findElement(By.xpath("bla-bla-bla"));
	 js.executeScript("arguments[0].scrollIntoView();", element);
	 DriverScript.bResult = true;
}
	
catch(Exception e){
		
		Log.info(e.getMessage());
		DriverScript.bResult = false;
		
	}
	


	//((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
}

public static void scrolltobottom(String object, String data)
{
	try
	{
	JavascriptExecutor js = ((JavascriptExecutor) driver);
	js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	DriverScript.bResult = true;
}
	
	catch(Exception e)
	{
		Log.info(e.getMessage());
		DriverScript.bResult = false;
		
	}



}

public static void pagedown(String object, String data) throws AWTException
{
	
	try
	{
	Robot robot = new Robot();
	robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	DriverScript.bResult = true;
	}
	
	catch(Exception e)
	{
		Log.info(e.getMessage());
		DriverScript.bResult = false;
		
	}

}

public static void selectlesson(String object, String data) throws InterruptedException
{
	
	try {
//	WebElement element = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/preceding-sibling::td/*"));
	WebDriverWait wait = new WebDriverWait(driver, 45);
	WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'"+data+"')]/../td[1]/input")));
	
	//WebElement element = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[1]/input"));
	
	//td[contains(text(),'L03_Faulty C300 controller Module replacement (non-redundant) with same firmware')]/../td[1]/input
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	// js.executeScript("arguments[0].scrollIntoView();", element);
	 js.executeScript("arguments[0].style.border='3px dotted blue'", element);
	 

	 js.executeScript("arguments[0].click();", element );
	 Thread.sleep(3000);
	 DriverScript.bResult = true;
	}
	
	catch(Exception e)
	{
		Log.info(e.getMessage());
		DriverScript.bResult = false;
		
	}
	 
}

public static void selectsetup(String object, String data) throws InterruptedException
{
	try {
//	WebElement element = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/preceding-sibling::td/*"));
	WebDriverWait wait = new WebDriverWait(driver, 45);
	WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'"+data+"')]/../td[1]/input")));
	
	//WebElement element = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[1]/input"));
	
	//td[contains(text(),'L03_Faulty C300 controller Module replacement (non-redundant) with same firmware')]/../td[1]/input
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	
	 js.executeScript("arguments[0].style.border='3px dotted blue'", element);
	 

	 js.executeScript("arguments[0].click();", element );
	 Thread.sleep(3000);
	 DriverScript.bResult = true;
	 
	}
	
	catch(Exception e)
	{
		Log.info(e.getMessage());
		DriverScript.bResult = false;
		
	}

	
}


public static void fileupload(String object, String data ) throws AWTException, InterruptedException
{
	
	try {
	//Store the location of the file in clipboard 
			//Clipboard
	//C:\\Users\\Mohammed\\Desktop\\IC Test data\\Test lesson001Tue Jul 16 2019 11_37_39 GMT+0530 (India Standard Time).zip
			StringSelection strSel = new StringSelection(data);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strSel, null);
			
			Robot robot = new Robot();
			//Control key in the keyboard
			//Ctrl+V 
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//			
//			Thread.sleep(3000);
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.keyRelease(KeyEvent.VK_ENTER);
			
			
			
			//Thread.sleep(1000);
		      
			  // Press Enter
			 robot.keyPress(KeyEvent.VK_ENTER);
			 
			// Release Enter
			 robot.keyRelease(KeyEvent.VK_ENTER);
			 
			  // Press CTRL+V
			 robot.keyPress(KeyEvent.VK_CONTROL);
			 robot.keyPress(KeyEvent.VK_V);
			 
			// Release CTRL+V
			 robot.keyRelease(KeyEvent.VK_CONTROL);
			 robot.keyRelease(KeyEvent.VK_V);
			// Thread.sleep(1000);
			        
			        // Press Enter 
			 robot.keyPress(KeyEvent.VK_ENTER);
			 robot.keyRelease(KeyEvent.VK_ENTER);
			
			Thread.sleep(3000);
			
			DriverScript.bResult = true;
			
	}
	
	
	catch(Exception e)
	{
		Log.info(e.getMessage());
		DriverScript.bResult = false;
		
	}
}

public static void verfiylessonuploaded (String object, String data) throws InterruptedException
{
	try {
	WebDriverWait wait = new WebDriverWait(driver, 45);
	
	//tHIS is verifying the lesson title column
	WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'"+data+"')]")));
	
	//WebElement element = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[1]/input"));
	
	//td[contains(text(),'L03_Faulty C300 controller Module replacement (non-redundant) with same firmware')]/../td[1]/input
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	 js.executeScript("arguments[0].scrollIntoView();", element);
	 Thread.sleep(2000);
	 js.executeScript("arguments[0].style.border='3px dotted blue'", element);
	 
	 WebElement systemtype= driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[3]"));
	 js.executeScript("arguments[0].style.border='3px dotted blue'", systemtype);
	 if(systemtype.getText().contains("Experion"))
	 {
		 DriverScript.bResult = true;
		 Log.info("System type is experion");
		 
	 }
	 
	 else
	 {
		 Log.info("System type is not experion and the actual type is"+ systemtype.getText());
		 DriverScript.bResult = false;
		 
	 }
	 
	 WebElement status= driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[5]"));
	 js.executeScript("arguments[0].style.border='3px dotted blue'", status);
	 
	 if(status.getText().contains("Unpublished"))
	 {
		 DriverScript.bResult = true;
		 Log.info("Status is unpublished");
		 
	 }
	 
	 else
	 {
		 Log.info("Status  is not correct and the actual status is"+ status.getText());
		 DriverScript.bResult = false;
		 
	 }
	 
	 DriverScript.bResult = true;
	}
	
	catch(Exception e)
	{
		DriverScript.bResult = false;
		
	}
	 
	 
	
}



public static void verfiysetupuploaded (String object, String data) throws InterruptedException
{
	try {
	WebDriverWait wait = new WebDriverWait(driver, 45);
	
	//tHIS is verifying the lesson title column
	WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'"+data+"')]")));
	
	//WebElement element = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[1]/input"));
	
	//td[contains(text(),'L03_Faulty C300 controller Module replacement (non-redundant) with same firmware')]/../td[1]/input
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	 js.executeScript("arguments[0].scrollIntoView();", element);
	 Thread.sleep(2000);
	 js.executeScript("arguments[0].style.border='3px dotted blue'", element);
	 
	 WebElement systemtype= driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[3]"));
	 js.executeScript("arguments[0].style.border='3px dotted blue'", systemtype);
	 if(systemtype.getText().contains("Experion"))
	 {
		 DriverScript.bResult = true;
		 Log.info("System type is experion");
		 
	 }
	 
	 else
	 {
		 Log.info("System type is not experion and the actual type is"+ systemtype.getText());
		 DriverScript.bResult = false;
		 
	 }
	 
	 WebElement status= driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[5]"));
	 js.executeScript("arguments[0].style.border='3px dotted blue'", status);
	 
	 if(status.getText().contains("Unpublished"))
	 {
		 DriverScript.bResult = true;
		 Log.info("Status is unpublished");
		 
	 }
	 
	 else
	 {
		 Log.info("Status  is not correct and the actual status is"+ status.getText());
		 DriverScript.bResult = false;
		 
	 }
	 
	 DriverScript.bResult = true;
	}
	
	catch(Exception e)
	{
		DriverScript.bResult = false;
		
	}
	 
	 
	
}


public static void offtraineraction(String object, String data)
{
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	try {
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	List<WebElement> actionnames= driver.findElements(By.xpath("//*[@ng-repeat='action in actionNames']"));
	
	//List<WebElement> traineractions= driver.findElements(By.xpath("//*[@ng-repeat='action in trainerActions']"));
	
	int i;
	for (i=0;i<actionnames.size();i++)
	{
		String actiontext= actionnames.get(i).getText();
		
		Log.info(actiontext);
		if(actiontext.contains(data))
		{
		Log.info("Text is matching");
		
		
			//*[@ng-repeat='action in trainerActions'][4]/*/*
		
		int b = i+1;
		

		
		//*[@ng-repeat='action in trainerActions'][4]/*/div[@class='toggle btn btn-primary']/* if on the class will be toggle btn btn-primary
		
		
		//if off class = toggle btn btn-default off
		try {
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			WebElement traineractions= driver.findElement(By.xpath("//*[@ng-repeat='action in trainerActions']["+b+"]/*/div[@class='toggle btn btn-primary']/*"));
			
			System.out.println(i);
			
			Highlight.highlightElement(traineractions);
			
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", traineractions);
		//	traineractions.click();
			DriverScript.bResult = true;	
			
		}
		
		catch(Exception e)
		{
			Log.info("Switch is already off");
			
			DriverScript.bResult = false;
		}
			
		}
		
		else
		{
			Log.info("There is no permission with the given text");
			System.out.println("There is no permission with the given text");
			//DriverScript.bResult = false;
			
		}
		
		
	}
	
	}
	
	
	catch(Exception e)
	{
		Log.info(e.getMessage());
		
		DriverScript.bResult = false;
	}
	
	
	
	
	
	
}


public static void ontraineraction(String object, String data)
{
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	try {
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	List<WebElement> actionnames= driver.findElements(By.xpath("//*[@ng-repeat='action in actionNames']"));	
	int i;
	for (i=0;i<actionnames.size();i++)
	{
		String actiontext= actionnames.get(i).getText();
		
		Log.info(actiontext);
		if(actiontext.contains(data))
		{
		Log.info("Text is matching");
		
		
			//*[@ng-repeat='action in trainerActions'][4]/*/*
		
		int b = i+1;
		

		
		//*[@ng-repeat='action in trainerActions'][4]/*/div[@class='toggle btn btn-primary']/* if on the class will be toggle btn btn-primary
		
		
		//if off class = toggle btn btn-default off
		try {
			WebElement traineractions= driver.findElement(By.xpath("//*[@ng-repeat='action in trainerActions']["+b+"]/*/div[@class='toggle btn btn-default off']/*"));
			
			System.out.println(i);
			
			Highlight.highlightElement(traineractions);
			
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", traineractions);
		//	traineractions.click();
			DriverScript.bResult = true;	
			
		}
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.info("Switch is already on");
			
			DriverScript.bResult = false;
		}
			
		}
		
		else
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.info("There is no permission with the given text");
			System.out.println("There is no permission with the given text");
			//DriverScript.bResult = false;
			
		}
		
		
	}
	
	}
	
	
	catch(Exception e)
	{
		ScreenshotCapture.takeScreenShot(driver);
		Log.info(e.getMessage());
		
		DriverScript.bResult = false;
	}
	
	
	
	
	
	
}






public static void POSTRequest(String object, String data) throws IOException {
	
	
	try {
	
	driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	
	WebElement totallessons = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/following-sibling::td[3]"));
	
	WebElement completelessons = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/following-sibling::td[4]"));
	
	WebElement incompletelessons = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/following-sibling::td[5]"));
	
	
	WebElement notstartedlessons = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/following-sibling::td[6]"));
	
	
	WebElement checkstatus = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/following-sibling::td[7]/*"));
	
	Highlight.highlightElement(checkstatus);
	
	
	String totallessonstext =totallessons.getText();
	
	int totallessonsnumber =Integer.parseInt(totallessonstext);
	
	String completelessonstext =completelessons.getText();
	
	int completelessonsnumber =Integer.parseInt(completelessonstext);
	
	String incompletelessonstext =incompletelessons.getText();
	
	int incompletelessonsnumber =Integer.parseInt(incompletelessonstext);
	
	String notstartedlessonstext =notstartedlessons.getText();
	
	Log.info(totallessonstext);
	Log.info(completelessonstext);
	Log.info(incompletelessonstext);
	Log.info(notstartedlessonstext);
	

    final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin01\",\r\n" +
        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
    System.out.println(POST_PARAMS);
    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
    postConnection.setRequestMethod("POST");
    postConnection.setRequestProperty("clienttype", "1");
   postConnection.setRequestProperty("Content-Type", "application/json");
    postConnection.setDoOutput(true);
    OutputStream os = postConnection.getOutputStream();
    os.write(POST_PARAMS.getBytes());
    os.flush();
    os.close();
    int responseCode = postConnection.getResponseCode();
    System.out.println("POST Response Code :  " + responseCode);
    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
    BufferedReader in = new BufferedReader(new InputStreamReader(
          postConnection.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in .readLine()) != null) {
          response.append(inputLine);
      } in .close();
       
      String accesstoken =response.toString();
      System.out.println(response.toString());
      System.out.println(accesstoken);
      
      String accesstokensubstring[] = accesstoken.split(":");
      
      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
      
      
      String actualtoken=acesstokensubstring2[0];
      
      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
      
      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
      
      System.out.println(actualtoken1+"is the new token");
      
//      for(String s:accesstokensubstring)
//      {
//    	  System.out.println(s);
//      }
   
      
      URL obj1 = new URL("https://mtsadminwebapiic200.azurewebsites.net/api/lessonstatistics");
      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
      postConnection1.setRequestMethod("GET");
      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
      postConnection1.setRequestProperty("Content-Type", "application/json");
      postConnection1.setDoOutput(true);
      int responseCode1 = postConnection1.getResponseCode();
      System.out.println("POST Response Code :  " + responseCode1);
      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
      BufferedReader in1 = new BufferedReader(new InputStreamReader(
            postConnection1.getInputStream()));
        String inputLine1;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine1 = in1 .readLine()) != null) {
            response1.append(inputLine1);
        } in1 .close();
        
        
        System.out.println(response1.toString());
        
        Log.info(response1.toString());
        
        
        String responsesubstring01 = response1.toString();
        
        String responsesubstring02[] = responsesubstring01.split(data);
      
        
        //This will contain the complete, incomplete and not started status
        System.out.println(responsesubstring02[1]+"is the string");
        
        
        String completedlesson[]=responsesubstring02[1].split("complete");
        
        System.out.println(completedlesson[1]);
        
        String completedlessonnumber[]=completedlesson[1].split(",");
        
        for(String s:completedlessonnumber)
        {
        	System.out.println(s);
        }
        
        String finalcompletedlessonnumber[]=completedlessonnumber[0].split(":");
        
        System.out.println(finalcompletedlessonnumber[1]+"is the number of completed lessons");
        
        int finalcompletedlesnumber =Integer.parseInt(finalcompletedlessonnumber[1]);
        
        if(finalcompletedlesnumber==completelessonsnumber)
        {
        	Log.info("The completed lessons value are matching and the values are"+completelessonsnumber);
        	
        	DriverScript.bResult = true;
        }
        
        else
        {
        	Log.info("The completed lessons value are not matching and the values are"+completelessonsnumber);
        	
        	DriverScript.bResult = false;
        }
        
        String incompletedlesson[]=responsesubstring02[1].split("incomplete");
        
        System.out.println(incompletedlesson[1]+"initial incomplete text");
        
        String incompletedlesson2[]=incompletedlesson[1].split(":");
        
        System.out.println(incompletedlesson2[1]+"IS THE NEXT INCOMPLETE TEXT");
        
        String incompletedlesson3[]=incompletedlesson2[1].split(",");
        
        System.out.println(incompletedlesson3[0]+"is the 3rd incomplte text");
        
        
        int finalincompletedlesnumber = Integer.parseInt(incompletedlesson3[0]);
        
        
        if(finalincompletedlesnumber==incompletelessonsnumber)
        {
        	Log.info("The incompleted lessons value are matching and the value displayed is "+incompletelessonsnumber);
        	
        	DriverScript.bResult = true;
        }
        
        else
        {
        	Log.info("The incompleted lessons value are not matching and the value displayed is"+incompletelessonsnumber);
        	
        	DriverScript.bResult = false;
        }
        
        String totallesson[]=responsesubstring02[1].split("total");
        
        System.out.println(totallesson[1]+"initial total text");
        
        
        String totallesson2[]=totallesson[1].split(":");
        
        System.out.println(totallesson2[1]+"IS THE NEXT TOTAL TEXT");
        
        
        String totallesson3[]=totallesson2[1].split(",");
        
        System.out.println(totallesson3[0]+"is the 3rd total text");
        
        int totallesson3number = Integer.parseInt(totallesson3[0]);
        
        if(totallesson3number==totallessonsnumber)
        {
        	Log.info("The total lessons value are matching and the value displayed is "+totallessonsnumber);
        	
        	DriverScript.bResult = true;
        }
        
        else
        {
        	Log.info("The total lessons value are not matching and the value displayed is"+totallessonsnumber);
        	
        	DriverScript.bResult = false;
        }
        
        
        ScreenshotCapture.takeScreenShot(driver);
        
        
        
        
	}
	
	catch(Exception e)
	{
		
		ScreenshotCapture.takeScreenShot(driver);
		Log.info(e.getMessage());
		
		DriverScript.bResult = false;
	}

}


public static void signinclick(String object, String data)
{
	
	
	try
	{		
		
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
	    WebElement Element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#btnLogin")));
	    Element.click();
		//Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//WebElement element = driver.findElement(By.cssSelector("#btnLogin"));
		//WebElement element = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));
		//JavascriptExecutor executor = (JavascriptExecutor)driver;
		//executor.executeScript("arguments[0].click();", element);
		
		//element.click();
		DriverScript.bResult=true;
	}
	
	catch(Exception e)
	{
		
		ScreenshotCapture.takeScreenShot(driver);
		Log.info("Element is not clickable");
		DriverScript.bResult=false;
	}
}



public static void verfiyduplicatelessonnotpresent (String object, String data) throws InterruptedException
{
	try {
	WebDriverWait wait = new WebDriverWait(driver, 45);
	
	//tHIS is verifying the lesson title column
	WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'"+data+"')]")));
	
	//WebElement element = driver.findElement(By.xpath("//td[contains(text(),'"+data+"')]/../td[1]/input"));
	
	//td[contains(text(),'L03_Faulty C300 controller Module replacement (non-redundant) with same firmware')]/../td[1]/input
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	 js.executeScript("arguments[0].scrollIntoView();", element);
	 Thread.sleep(2000);
	 js.executeScript("arguments[0].style.border='3px dotted blue'", element);
	 
	List <WebElement> systemtype= driver.findElements(By.xpath("//td[contains(text(),'"+data+"')]/../td[3]"));
	
	int systemtypenumber = systemtype.size();
	if (systemtypenumber==1)
	{
		System.out.println("the lesson is only one"+systemtypenumber);
		DriverScript.bResult = true;	
		
		
		
		//Log.info(systemtypenumber);
		
	}
	else
	{
		System.out.println("There are duplicate lessons"+ systemtypenumber);
		DriverScript.bResult = false;
		
	
	}
	
	// js.executeScript("arguments[0].style.border='3px dotted blue'", systemtype);

	 
	 
	}
	
	catch(Exception e)
	{
		ScreenshotCapture.takeScreenShot(driver);
		Log.info(e.getMessage());
		DriverScript.bResult = false;
		
	}
	 
	 
	
}


public static void enterspace(String object, String data)
{
	try {
	WebElement element =  objectLocator(object);
	element.sendKeys(Keys.SPACE);
	DriverScript.bResult = true;
	}
	
	catch(Exception e)
	{
	
		ScreenshotCapture.takeScreenShot(driver);
		Log.error("Unable to send enter key to the object --- " + e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
		
	}
	
}



public static void dragandropnew(String object, String data)
{
	try {
		
		
		//driver.switchTo().defaultContent();
		driver.switchTo().frame(0);
		
		//WebElement sourceElement =  objectLocator(object);
		
		//Highlight.highlightElement(sourceElement);
		
		//WebElement targetElement = driver.findElement(By.cssSelector("#divGroupUpdateContainer"));
	       
	   // Highlight.highlightElement(targetElement);
		
		
	    WebElement source = objectLocator(object);
	    WebElement destination = driver.findElement(By.cssSelector("#divGroupUpdateContainer"));
	    Highlight.highlightElement(source);
	    
	    Highlight.highlightElement(destination);
		
		//JavascriptExecutor js = (JavascriptExecutor) driver;
//		String xto=Integer.toString(source.getLocation().x);
//		String yto=Integer.toString(destination.getLocation().y);
//		((JavascriptExecutor)driver).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a=="HTMLEvents"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent("on"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
//		"simulate(arguments[0],"mousedown",0,0); simulate(arguments[0],"mousemove",arguments[1],arguments[2]); simulate(arguments[0],"mouseup",arguments[1],arguments[2]); ",
//		source,xto,yto);
		
		
		
		
//		
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    js.executeScript("function createEvent(typeOfEvent) {\n" +"var event =document.createEvent("CustomEvent");\n" +"event.initCustomEvent(typeOfEvent,true, true, null);\n" +"event.dataTransfer = {\n" +"data: {},\n" +"setData: function (key, value) {\n" +"this.data[key] = value;\n" +"},\n" +"getData: function (key) {\n" +"return this.data[key];\n" +"}\n" +"};\n" +"return event;\n" +"}\n" +"\n" +"function dispatchEvent(element, event,transferData) {\n" +"if (transferData !== undefined) {\n" +"event.dataTransfer = transferData;\n" +"}\n" +"if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n" +"} else if (element.fireEvent) {\n" +"element.fireEvent("on" + event.type, event);\n" +"}\n" +"}\n" +"\n" +"function simulateHTML5DragAndDrop(element, destination) {\n" +"var dragStartEvent =createEvent('dragstart');\n" +"dispatchEvent(element, dragStartEvent);\n" +"var dropEvent = createEvent('drop');\n" +"dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n" +"var dragEndEvent = createEvent('dragend');\n" +"dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" +"}\n" +"\n" +"var source = arguments[0];\n" +"var destination = arguments[1];\n" +"simulateHTML5DragAndDrop(source,destination);",source, destination);
//	    Thread.sleep(1500);
//		
//		
		
		
		
	
	    
//	    
//	    WebElement LocatorFrom = objectLocator(object);
//	    WebElement LocatorTo = driver.findElement(By.cssSelector("#divGroupUpdateContainer"));
//	    Highlight.highlightElement(LocatorFrom);
//	    
//	    Highlight.highlightElement(LocatorTo);
//	    
//	    String xto=Integer.toString(LocatorTo.getLocation().x);
//	    String yto=Integer.toString(LocatorTo.getLocation().y);
//	    ((JavascriptExecutor)driver).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
//	    "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
//	    LocatorFrom,xto,yto);
//		
		//sourceElement.click();
	    
	    
//	    Actions builder = new Actions(driver);
//
//	    Action dragAndDrop = builder.clickAndHold(sourceElement)
//	       .moveToElement(targetElement)
//	       .release(targetElement)
//	       .build();
//
//	    dragAndDrop.perform();
	    
	    
	    
//	    Actions builder = new Actions(driver);
//	    builder.
//	    //keyDown(Keys.CONTROL)
//	    click(sourceElement)
//	        .clickAndHold(sourceElement)
//	        .dragAndDrop(sourceElement, driver.findElement(By.cssSelector("#divGroupUpdateContainer")))
//	      .build().perform();
//	    builder.release();
	    
//	    Actions builder = new Actions(driver);
//	    
//	    
//	    
//	    int xOffset1 = sourceElement.getLocation().getX();
//	    
//	    int yOffset1 =  sourceElement.getLocation().getY();
//	    
//	    System.out.println("xOffset1--->"+xOffset1+" yOffset1--->"+yOffset1);
//	    
//	    //Secondly, get x and y offset for to object
//	    int xOffset = targetElement.getLocation().getX();
//	    
//	    int yOffset =  targetElement.getLocation().getY();
//	    
//	    System.out.println("xOffset--->"+xOffset+" yOffset--->"+yOffset);
//	    
//	    //Find the xOffset and yOffset difference to find x and y offset needed in which from object required to dragged and dropped
//	    xOffset =(xOffset-xOffset1)+10;
//	    yOffset=(yOffset-yOffset1)+20;
//	    
//	    builder.dragAndDropBy(sourceElement, xOffset,yOffset).perform();
//	    
//	   WebElement element = driver.findElement(By.xpath("//*[@id='divGroupUpdatePanel']//div[@id = 'divGroupUpdateContainer']"));
////	    
////	    Actions action = new Actions(driver);
////	    
////	    
////	    action.moveToElement(sourceElement).clickAndHold().moveToElement(element).release().build().perform();
//	 
//	    
//	    Actions builder= new Actions(driver);
//	    builder.dragAndDrop(sourceElement, element).perform();
//	    builder.build();
//	    
	    
	        //.keyUp(Keys.CONTROL);

	     //   Action selected = builder.build();

	      //  selected.perform();
	    
//	    Actions builder = new Actions(driver);
//
//	    builder.keyDown(Keys.CONTROL)
//	       .click(sourceElement)
//	       .click(targetElement)
//	       .keyUp(Keys.CONTROL);
//
//	    // Then get the action:
//	    Action selectMultiple = builder.build();
//
//	    // And execute it:
//	    selectMultiple.perform(); 
	    
	    
	    
//	    Actions act=new Actions(driver);					
//
//		//Dragged and dropped.		
//	         act.dragAndDrop(sourceElement, targetElement).build().perform();
	    		
		
		
//      Actions builder = new Actions(driver);
//       builder.clickAndHold(sourceElement);
//        Action action = builder.build();
//        action.perform();
//
//    //  driver.switchTo().frame(0);
//       
//       
//
//      builder.moveToElement(targetElement);
//      builder.release(targetElement);
//      action = builder.build();
//       action.perform();
		
//		Actions action = new Actions(driver);
//	
//	
//	
//	
//	
//	
//	Highlight.highlightElement(targetElement);
//	//element.click();
//	
//	action.clickAndHold(sourceElement)
//	
//	
//	.moveByOffset(-1, -1) // To fix issue with drag and drop in Chrome V61.0.3163.79
//    .moveToElement(targetElement, 
//    		targetElement.getLocation().getX()+targetElement.getSize().getWidth()/2, 
//    		targetElement.getLocation().getY()+targetElement.getSize().getHeight()/2)
//    .release(targetElement)
//    .build()
//    .perform();
//Actions builder = new Actions(driver);
//
//builder.keyDown(Keys.CONTROL)
//   .click(sourceElement)
//   .click(targetElement)
//   .keyUp(Keys.CONTROL);
//
//// Then get the action:
//Action selectMultiple = builder.build();
//
//// And execute it:
//selectMultiple.perform(); 
	}
	
	catch(Exception e)
	{
	
		ScreenshotCapture.takeScreenShot(driver);
		Log.error("Unable to send enter key to the object --- " + e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
		
	}
	
}


public static void draganddropusingrobot(String object, String data) throws AWTException
{
	
	
	driver.switchTo().frame(0);
	
	WebElement sourceElement =  objectLocator(object);
	
	Highlight.highlightElement(sourceElement);
	
	WebElement targetElement = driver.findElement(By.cssSelector("#divGroupUpdateContainer"));
       
    Highlight.highlightElement(targetElement);
	
	Point coordinates1 = sourceElement.getLocation();
	Point coordinates2 = targetElement.getLocation();  
	Robot robot = new Robot();           
	robot.mouseMove(coordinates1.getX(), coordinates1.getY());
	robot.mousePress(InputEvent.BUTTON1_MASK);
	robot.mouseMove(coordinates2.getX(), coordinates2.getY());
	robot.mouseRelease(InputEvent.BUTTON1_MASK);
	
	
	
}


public static void draganddropusingjavascript(String object, String data)
{
	
    driver.switchTo().frame(0);
	
	WebElement sourceElement =  objectLocator(object);
	
	Highlight.highlightElement(sourceElement);
	
	WebElement targetElement = driver.findElement(By.cssSelector("#divGroupUpdateContainer"));
       
    Highlight.highlightElement(targetElement);
    
    
    final String java_script =
    		"var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe" +
    		                "ct:'',effectAllowed:'all',files:[],items:{},types:[],setData:fun" +
    		                "ction(format,data){this.items[format]=data;this.types.append(for" +
    		                "mat);},getData:function(format){return this.items[format];},clea" +
    		                "rData:function(format){}};var emit=function(event,target){var ev" +
    		                "t=document.createEvent('Event');evt.initEvent(event,true,false);" +
    		                "evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit('" +
    		                "dragstart',src);emit('dragenter',tgt);emit('dragover',tgt);emit(" +
    		                "'drop',tgt);emit('dragend',src);";
    
    
    JavascriptExecutor js = (JavascriptExecutor) driver;

    		        js.executeScript(java_script, sourceElement, targetElement);
    		        //Thread.sleep(2000);
	
}


public static void handleframes(String object, String data)
{
	int size = driver.findElements(By.tagName("iframe")).size();
	
	
	
	List<WebElement> frames = driver.findElements(By.tagName("iframe"));
	
	for(WebElement f:frames)
	{
		String attribute = f.getAttribute("id");
		System.out.println(attribute);
	}
	
	driver.switchTo().frame(0);
	
	System.out.println("The number of frames is"+size);
}



//This is working but again it is asking to select the system type
public static void handlesystemtypeie(String object,String data)
{
	try {
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("return document.getElementById('machineTypeSelect').selectedIndex = '" + data + "';");
	
	DriverScript.bResult = true;
	}
	
	catch(Exception e)
	{
	
		ScreenshotCapture.takeScreenShot(driver);
		Log.error(e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
		
	}
	
	
}

public static void selectsystemtypeie(String object, String data)

{
	try {
	WebElement firstElement =  objectLocator(object);
	
	WebElement secondelement = driver.findElement(By.xpath("//option[contains(text(),'Safety Manager')]"));
	
	
	Actions action = new Actions(driver);
    action.keyDown(Keys.CONTROL).click(firstElement).click(secondelement).build().perform();
    
    DriverScript.bResult = true;
	}
    
    
    catch(Exception e)
	{
    	
    	ScreenshotCapture.takeScreenShot(driver);
		Log.error(e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
		
	}
}


public static void selectSMOptionFromDropDownie(String object, String data) {
	try {

		objectLocator(object).click();
		
		DriverScript.bResult = true;

	} 
	
	
	catch (Exception e)
	
	{
		
		ScreenshotCapture.takeScreenShot(driver);
		System.out.println("Exception in selectOptionFromDropDown:" + e);
		
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
	}
	
}

public static void selectexpOptionFromDropDownie(String object, String data)
{
	try {

		objectLocator(object).click();
		
		//objectLocator(object).sendKeys(Keys.ARROW_UP);
		
		DriverScript.bResult = true;

	} 
	
	catch (Exception e)
	{
		
		ScreenshotCapture.takeScreenShot(driver);
		System.out.println("Exception in selectOptionFromDropDown:" + e);
		
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
	}
	
}


public static void verfiyenabledlicensefeatures(String object, String data)
{
	try
	{
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement feature = driver.findElement(By.xpath("//*[contains(text(),'"+data+"')]"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].scrollIntoView();", feature);	
		Highlight.highlightElement(feature);
		if(feature.isDisplayed())
		{
			Log.info("feature is displayed");
			WebElement enabledfeature =driver.findElement(By.xpath("//*[contains(text(),'"+data+"')]/*/*"));
			String okenabled = enabledfeature.getAttribute("class");
			if (okenabled.contains("ok"))
			{
				System.out.println(okenabled);
				Log.info("feature is enabled");
				DriverScript.bResult = true;
			}
			
			else
			{
				Log.info("feature is not enabled");
				DriverScript.bResult = false;
			}
		}
		
		
		
		else
		{
			Log.info("feature is not displayed");
			ScreenshotCapture.takeScreenShot(driver);
			System.out.println("feature is not displayed");
			DriverScript.bResult = false;
		}	
		
	}
	
	
	catch (Exception e)
	{
		ScreenshotCapture.takeScreenShot(driver);
		System.out.println("Exception in selectOptionFromDropDown:" + e);
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
	}
	
	
}


public static void verifySMlicenseuserinfo(String object, String data)
{
	
	try {
	final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin04\",\r\n" +
	        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
	    System.out.println(POST_PARAMS);
	    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
	    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
	    postConnection.setRequestMethod("POST");
	    postConnection.setRequestProperty("clienttype", "1");
	   postConnection.setRequestProperty("Content-Type", "application/json");
	    postConnection.setDoOutput(true);
	    OutputStream os = postConnection.getOutputStream();
	    os.write(POST_PARAMS.getBytes());
	    os.flush();
	    os.close();
	    int responseCode = postConnection.getResponseCode();
	    System.out.println("POST Response Code :  " + responseCode);
	    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
	    BufferedReader in = new BufferedReader(new InputStreamReader(
	          postConnection.getInputStream()));
	      String inputLine;
	      StringBuffer response = new StringBuffer();
	      while ((inputLine = in .readLine()) != null) {
	          response.append(inputLine);
	      } in .close();
	       
	      String accesstoken =response.toString();
	      System.out.println(response.toString());
	      System.out.println(accesstoken);
	      
	      String accesstokensubstring[] = accesstoken.split(":");
	      
	      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
	      
	      
	      String actualtoken=acesstokensubstring2[0];
	      
	      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
	      
	      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
	      
	      System.out.println(actualtoken1+"is the new token");
	      
//	      for(String s:accesstokensubstring)
//	      {
//	    	  System.out.println(s);
//	      }
	   
	      
	      URL obj1 = new URL("https://mtsadminwebapiic200.azurewebsites.net/api/license/getlicenseinfo");
	      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
	      postConnection1.setRequestMethod("GET");
	      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
	      postConnection1.setRequestProperty("Content-Type", "application/json");
	      postConnection1.setDoOutput(true);
	      int responseCode1 = postConnection1.getResponseCode();
	      System.out.println("POST Response Code :  " + responseCode1);
	      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
	      BufferedReader in1 = new BufferedReader(new InputStreamReader(
	            postConnection1.getInputStream()));
	        String inputLine1;
	        StringBuffer response1 = new StringBuffer();
	        while ((inputLine1 = in1 .readLine()) != null) {
	            response1.append(inputLine1);
	        } in1 .close();
	        
	        
	        System.out.println(response1.toString());
	        
	        Log.info(response1.toString());
	        
	        
	        String responsesubstring01 = response1.toString();
	        
	        String responsesubstring02[] = responsesubstring01.split(":");
	        
	        System.out.println(responsesubstring02[1]+"is the string");
	        
	        String responsesubstring03[] = responsesubstring02[1].split(",");
	        
	        String numberofusers = responsesubstring03[0];
	        
	        int presentusersapi = Integer.parseInt(numberofusers);
	        
	        System.out.println(numberofusers+"are the number of users");
	        
	        System.out.println(presentusersapi+"present users from api call");
	        
	        
	        String responsesubstring04[] = responsesubstring01.split("numberOfUser");
	        System.out.println(responsesubstring04[1]+"are the allowed users");
	        String responsesubstring05[]=responsesubstring04[1].split(":");
	        System.out.println(responsesubstring05[1]);
	        String responsesubstring06[]=responsesubstring05[1].split("}");
	        System.out.println(responsesubstring06[0]);
	        
	        String allowedusers = responsesubstring06[0];
	        
	        int allowedusersapi = Integer.parseInt(allowedusers);
	        
	        System.out.println(allowedusers);
	        
	        System.out.println(allowedusersapi+"allowed users from api call");
	        
	        
	        //This is for UI
	        
	        WebElement numberofusersui= driver.findElement(By.xpath("//*[contains(text(),'Number Of Users:')]/.."));
	        
	        String numberofusersuitext = numberofusersui.getText();
	        
	        System.out.println(numberofusersuitext+"is the user list from UI ");
	        
	        
	        String responsesubstring07[] = numberofusersuitext.split(":");
	        
	        System.out.println(responsesubstring07[1]);
	        
	        String responsesubstring08[]=responsesubstring07[1].split("/");
	        
            System.out.println(responsesubstring08[0]);
	        
	        System.out.println(responsesubstring08[1]);
	        
           String responsesubstring09=responsesubstring08[0].trim();
	        
	       String responsesubstring10=responsesubstring08[1].trim();
	        
	        int presentuserUI =Integer.parseInt(responsesubstring09);
	        
	        int allowedusersUI=Integer.parseInt(responsesubstring10);
	        
	        System.out.println(presentuserUI);
	        
	        System.out.println(allowedusersUI);
	        
	        if (presentusersapi==presentuserUI)
	        {
	        	Log.info("The present users value are matching");
	        	System.out.println("The present users value are matching");
	        	DriverScript.bResult = true;
	        }
	        
	        else
	        {
	        	ScreenshotCapture.takeScreenShot(driver);
	        
	        	Log.info("The present users value are not matching");
	        	System.out.println("The present users value are not matching");
	        	DriverScript.bResult = false;
	        }
	        
	        if (allowedusersapi==allowedusersUI)
	        {
	        	
	        	Log.info("The allowed users value are matching");
	        	System.out.println("The allowed users value are matching");
	        	DriverScript.bResult = true;
	        	
	        }
	        
	        else
	        {
	        	ScreenshotCapture.takeScreenShot(driver);
	        
	        	Log.info("The allowed users value are not matching");
	        	System.out.println("The allowed users value are not matching");
	        	DriverScript.bResult = false;
	        }
	        
	        
	        
	  
	        
	}
	        
	        
	        
	        catch(Exception e)
	    	{
	    		
	    		ScreenshotCapture.takeScreenShot(driver);
	    		Log.info(e.getMessage());
	    		
	    		DriverScript.bResult = false;
	    	}
	
	
	
	   
	
}

public static void verifyIC300licenseuserinfo(String object, String data)
{
	
	try {
	final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin02\",\r\n" +
	        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
	    System.out.println(POST_PARAMS);
	    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
	    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
	    postConnection.setRequestMethod("POST");
	    postConnection.setRequestProperty("clienttype", "1");
	   postConnection.setRequestProperty("Content-Type", "application/json");
	    postConnection.setDoOutput(true);
	    OutputStream os = postConnection.getOutputStream();
	    os.write(POST_PARAMS.getBytes());
	    os.flush();
	    os.close();
	    int responseCode = postConnection.getResponseCode();
	    System.out.println("POST Response Code :  " + responseCode);
	    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
	    BufferedReader in = new BufferedReader(new InputStreamReader(
	          postConnection.getInputStream()));
	      String inputLine;
	      StringBuffer response = new StringBuffer();
	      while ((inputLine = in .readLine()) != null) {
	          response.append(inputLine);
	      } in .close();
	       
	      String accesstoken =response.toString();
	      System.out.println(response.toString());
	      System.out.println(accesstoken);
	      
	      String accesstokensubstring[] = accesstoken.split(":");
	      
	      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
	      
	      
	      String actualtoken=acesstokensubstring2[0];
	      
	      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
	      
	      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
	      
	      System.out.println(actualtoken1+"is the new token");
	      
//	      for(String s:accesstokensubstring)
//	      {
//	    	  System.out.println(s);
//	      }
	   
	      
	      URL obj1 = new URL("https://mtsadminwebapiic200.azurewebsites.net/api/license/getlicenseinfo");
	      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
	      postConnection1.setRequestMethod("GET");
	      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
	      postConnection1.setRequestProperty("Content-Type", "application/json");
	      postConnection1.setDoOutput(true);
	      int responseCode1 = postConnection1.getResponseCode();
	      System.out.println("POST Response Code :  " + responseCode1);
	      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
	      BufferedReader in1 = new BufferedReader(new InputStreamReader(
	            postConnection1.getInputStream()));
	        String inputLine1;
	        StringBuffer response1 = new StringBuffer();
	        while ((inputLine1 = in1 .readLine()) != null) {
	            response1.append(inputLine1);
	        } in1 .close();
	        
	        
	        System.out.println(response1.toString());
	        
	        Log.info(response1.toString());
	        
	        
	        String responsesubstring01 = response1.toString();
	        
	        String responsesubstring02[] = responsesubstring01.split(":");
	        
	        System.out.println(responsesubstring02[1]+"is the string");
	        
	        String responsesubstring03[] = responsesubstring02[1].split(",");
	        
	        String numberofusers = responsesubstring03[0];
	        
	        int presentusersapi = Integer.parseInt(numberofusers);
	        
	        System.out.println(numberofusers+"are the number of users");
	        
	        System.out.println(presentusersapi+"present users from api call");
	        
	        
	        String responsesubstring04[] = responsesubstring01.split("numberOfUser");
	        System.out.println(responsesubstring04[1]+"are the allowed users");
	        String responsesubstring05[]=responsesubstring04[1].split(":");
	        System.out.println(responsesubstring05[1]);
	        String responsesubstring06[]=responsesubstring05[1].split("}");
	        System.out.println(responsesubstring06[0]);
	        
	        String allowedusers = responsesubstring06[0];
	        
	        int allowedusersapi = Integer.parseInt(allowedusers);
	        
	        System.out.println(allowedusers);
	        
	        System.out.println(allowedusersapi+"allowed users from api call");
	        
	        
	        //This is for UI
	        
	        WebElement numberofusersui= driver.findElement(By.xpath("//*[contains(text(),'Number Of Users:')]/.."));
	        
	        String numberofusersuitext = numberofusersui.getText();
	        
	        System.out.println(numberofusersuitext+"is the user list from UI ");
	        
	        
	        String responsesubstring07[] = numberofusersuitext.split(":");
	        
	        System.out.println(responsesubstring07[1]);
	        
	        String responsesubstring08[]=responsesubstring07[1].split("/");
	        
            System.out.println(responsesubstring08[0]);
	        
	        System.out.println(responsesubstring08[1]);
	        
           String responsesubstring09=responsesubstring08[0].trim();
	        
	       String responsesubstring10=responsesubstring08[1].trim();
	        
	        int presentuserUI =Integer.parseInt(responsesubstring09);
	        
	        int allowedusersUI=Integer.parseInt(responsesubstring10);
	        
	        System.out.println(presentuserUI);
	        
	        System.out.println(allowedusersUI);
	        
	        if (presentusersapi==presentuserUI)
	        {
	        	Log.info("The present users value are matching");
	        	System.out.println("The present users value are matching");
	        	DriverScript.bResult = true;
	        }
	        
	        else
	        {
	        	ScreenshotCapture.takeScreenShot(driver);
	        
	        	Log.info("The present users value are not matching");
	        	System.out.println("The present users value are not matching");
	        	DriverScript.bResult = false;
	        }
	        
	        if (allowedusersapi==allowedusersUI)
	        {
	        	
	        	Log.info("The allowed users value are matching");
	        	System.out.println("The allowed users value are matching");
	        	DriverScript.bResult = true;
	        	
	        }
	        
	        else
	        {
	        	ScreenshotCapture.takeScreenShot(driver);
	        
	        	Log.info("The allowed users value are not matching");
	        	System.out.println("The allowed users value are not matching");
	        	DriverScript.bResult = false;
	        }
	        
	        
	        
	  
	        
	}
	        
	        
	        
	        catch(Exception e)
	    	{
	    		
	    		ScreenshotCapture.takeScreenShot(driver);
	    		Log.info(e.getMessage());
	    		
	    		DriverScript.bResult = false;
	    	}
	
	
	
	   
	
}



public static void verifySMlicensehours(String object, String data)
{
	
	
		
		
		try {
			final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin04\",\r\n" +
			        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
			    System.out.println(POST_PARAMS);
			    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
			    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
			    postConnection.setRequestMethod("POST");
			    postConnection.setRequestProperty("clienttype", "1");
			   postConnection.setRequestProperty("Content-Type", "application/json");
			    postConnection.setDoOutput(true);
			    OutputStream os = postConnection.getOutputStream();
			    os.write(POST_PARAMS.getBytes());
			    os.flush();
			    os.close();
			    int responseCode = postConnection.getResponseCode();
			    System.out.println("POST Response Code :  " + responseCode);
			    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
			    BufferedReader in = new BufferedReader(new InputStreamReader(
			          postConnection.getInputStream()));
			      String inputLine;
			      StringBuffer response = new StringBuffer();
			      while ((inputLine = in .readLine()) != null) {
			          response.append(inputLine);
			      } in .close();
			       
			      String accesstoken =response.toString();
			      System.out.println(response.toString());
			      System.out.println(accesstoken);
			      
			      String accesstokensubstring[] = accesstoken.split(":");
			      
			      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
			      
			      
			      String actualtoken=acesstokensubstring2[0];
			      
			      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
			      
			      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
			      
			      System.out.println(actualtoken1+"is the new token");
			      
//			      for(String s:accesstokensubstring)
//			      {
//			    	  System.out.println(s);
//			      }
			   
			      
			      URL obj1 = new URL("https://mtsadminwebapiic200.azurewebsites.net/api/license/getlicenseinfo");
			      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
			      postConnection1.setRequestMethod("GET");
			      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
			      postConnection1.setRequestProperty("Content-Type", "application/json");
			      postConnection1.setDoOutput(true);
			      int responseCode1 = postConnection1.getResponseCode();
			      System.out.println("POST Response Code :  " + responseCode1);
			      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
			      BufferedReader in1 = new BufferedReader(new InputStreamReader(
			            postConnection1.getInputStream()));
			        String inputLine1;
			        StringBuffer response1 = new StringBuffer();
			        while ((inputLine1 = in1 .readLine()) != null) {
			            response1.append(inputLine1);
			        } in1 .close();
			        
			        
			        System.out.println(response1.toString());
			        
			        Log.info(response1.toString());
			        
			        
			        String responsesubstring01=response1.toString();
			        
			        String responsesubstring02[] = responsesubstring01.split("minutesTotal");
			        
			        String responsesubstring03=responsesubstring02[1];
			        
			        System.out.println(responsesubstring03);
			        
			        String responsesubstring04[]=responsesubstring03.split(":");
			        
			        System.out.println(responsesubstring04[1]);
			        
			        String responsesubstring05[]=responsesubstring04[1].split(",");
			        
			        System.out.println(responsesubstring05[0]);
			        
			        int totalminutesapi = Integer.parseInt(responsesubstring05[0]);
			        
			        int totalhoursapi = (totalminutesapi/60);
			        
			        System.out.println(totalhoursapi);
			        
			        String responsesubstring06[]=responsesubstring01.split("minutesRem");
			        String responsesubstring07=responsesubstring06[1];
			        System.out.println(responsesubstring07);
			        String responsesubstring08[]=responsesubstring07.split(":");
			        System.out.println(responsesubstring08[1]);
			        String responsesubstring09[]=responsesubstring08[1].split(",");
			        
			        System.out.println(responsesubstring09[0]);
			        
                    int remainingminutesapi = Integer.parseInt(responsesubstring09[0]);
                    
                    int remaininghoursapi=remainingminutesapi/60;
                    
                    System.out.println(remaininghoursapi);
			        
			       // int remainhoursapi = (totalminutesapi/60);
                    
                    
                    
                    
                    WebElement numberofhoursui= driver.findElement(By.xpath("//*[contains(text(),'Remaining Hours:')]/.."));
        	        
        	        String numberofhoursuitext = numberofhoursui.getText();
        	        
        	        System.out.println(numberofhoursuitext+"is the user list from UI ");
        	        
        	        
        	        String responsesubstring10[] = numberofhoursuitext.split(":");
        	        
        	        System.out.println(responsesubstring10[1]);
        	        
        	        String responsesubstring11[]=responsesubstring10[1].split("/");
        	        
                    System.out.println(responsesubstring11[0]);
        	        
        	        System.out.println(responsesubstring11[1]);
        	        
                   String responsesubstring12=responsesubstring11[0].trim();
        	        
        	       String responsesubstring13=responsesubstring11[1].trim();
        	        
        	        int remaininghoursUI =Integer.parseInt(responsesubstring12);
        	        
        	        int totalhoursUI=Integer.parseInt(responsesubstring13);
        	        
        	        System.out.println(remaininghoursUI);
        	        
        	        System.out.println(totalhoursUI);
        	        
        	        if (totalhoursapi==totalhoursUI)
        	        {
        	        	Log.info("The total hours value is matching");
        	        	System.out.println("The total hours value is matching");
        	        	DriverScript.bResult = true;
        	        }
        	        
        	        else
        	        {
        	        	ScreenshotCapture.takeScreenShot(driver);
        	        
        	        	Log.info("The total hours value is not matching");
        	        	System.out.println("The total hours value is not matching");
        	        	DriverScript.bResult = false;
        	        }
        	        
        	        if (remaininghoursapi==remaininghoursUI)
        	        {
        	        	
        	        	Log.info("The remainining hours value is matching");
        	        	System.out.println("The remainining hours value is matching");
        	        	DriverScript.bResult = true;
        	        	
        	        }
        	        
        	        else
        	        {
        	        	ScreenshotCapture.takeScreenShot(driver);
   
        	        	Log.info("The remainining hours value is not matching");
        	        	System.out.println("The remainining hours value is not matching");
        	        	DriverScript.bResult = false;
        	        }
        	        
        	 
			        
			        
			
		
		
	}
	   catch(Exception e)
	{
		
		ScreenshotCapture.takeScreenShot(driver);
		Log.info(e.getMessage());
		
		DriverScript.bResult = false;
	}
	
}

public static void verifyIC300licensehours(String object, String data)
{
	
	
		
		
		try {
			final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin02\",\r\n" +
			        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
			    System.out.println(POST_PARAMS);
			    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
			    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
			    postConnection.setRequestMethod("POST");
			    postConnection.setRequestProperty("clienttype", "1");
			   postConnection.setRequestProperty("Content-Type", "application/json");
			    postConnection.setDoOutput(true);
			    OutputStream os = postConnection.getOutputStream();
			    os.write(POST_PARAMS.getBytes());
			    os.flush();
			    os.close();
			    int responseCode = postConnection.getResponseCode();
			    System.out.println("POST Response Code :  " + responseCode);
			    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
			    BufferedReader in = new BufferedReader(new InputStreamReader(
			          postConnection.getInputStream()));
			      String inputLine;
			      StringBuffer response = new StringBuffer();
			      while ((inputLine = in .readLine()) != null) {
			          response.append(inputLine);
			      } in .close();
			       
			      String accesstoken =response.toString();
			      System.out.println(response.toString());
			      System.out.println(accesstoken);
			      
			      String accesstokensubstring[] = accesstoken.split(":");
			      
			      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
			      
			      
			      String actualtoken=acesstokensubstring2[0];
			      
			      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
			      
			      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
			      
			      System.out.println(actualtoken1+"is the new token");
			      
//			      for(String s:accesstokensubstring)
//			      {
//			    	  System.out.println(s);
//			      }
			   
			      
			      URL obj1 = new URL("https://mtsadminwebapiic200.azurewebsites.net/api/license/getlicenseinfo");
			      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
			      postConnection1.setRequestMethod("GET");
			      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
			      postConnection1.setRequestProperty("Content-Type", "application/json");
			      postConnection1.setDoOutput(true);
			      int responseCode1 = postConnection1.getResponseCode();
			      System.out.println("POST Response Code :  " + responseCode1);
			      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
			      BufferedReader in1 = new BufferedReader(new InputStreamReader(
			            postConnection1.getInputStream()));
			        String inputLine1;
			        StringBuffer response1 = new StringBuffer();
			        while ((inputLine1 = in1 .readLine()) != null) {
			            response1.append(inputLine1);
			        } in1 .close();
			        
			        
			        System.out.println(response1.toString());
			        
			        Log.info(response1.toString());
			        
			        
			        String responsesubstring01=response1.toString();
			        
			        String responsesubstring02[] = responsesubstring01.split("minutesTotal");
			        
			        String responsesubstring03=responsesubstring02[1];
			        
			        System.out.println(responsesubstring03);
			        
			        String responsesubstring04[]=responsesubstring03.split(":");
			        
			        System.out.println(responsesubstring04[1]);
			        
			        String responsesubstring05[]=responsesubstring04[1].split(",");
			        
			        System.out.println(responsesubstring05[0]);
			        
			        int totalminutesapi = Integer.parseInt(responsesubstring05[0]);
			        
			        int totalhoursapi = (totalminutesapi/60);
			        
			        System.out.println(totalhoursapi);
			        
			        String responsesubstring06[]=responsesubstring01.split("minutesRem");
			        String responsesubstring07=responsesubstring06[1];
			        System.out.println(responsesubstring07);
			        String responsesubstring08[]=responsesubstring07.split(":");
			        System.out.println(responsesubstring08[1]);
			        String responsesubstring09[]=responsesubstring08[1].split(",");
			        
			        System.out.println(responsesubstring09[0]);
			        
                    int remainingminutesapi = Integer.parseInt(responsesubstring09[0]);
                    
                    int remaininghoursapi=remainingminutesapi/60;
                    
                    System.out.println(remaininghoursapi);
			        
			       // int remainhoursapi = (totalminutesapi/60);
                    
                    
                    
                    
                    WebElement numberofhoursui= driver.findElement(By.xpath("//*[contains(text(),'Remaining Hours:')]/.."));
        	        
        	        String numberofhoursuitext = numberofhoursui.getText();
        	        
        	        System.out.println(numberofhoursuitext+"is the user list from UI ");
        	        
        	        
        	        String responsesubstring10[] = numberofhoursuitext.split(":");
        	        
        	        System.out.println(responsesubstring10[1]);
        	        
        	        String responsesubstring11[]=responsesubstring10[1].split("/");
        	        
                    System.out.println(responsesubstring11[0]);
        	        
        	        System.out.println(responsesubstring11[1]);
        	        
                   String responsesubstring12=responsesubstring11[0].trim();
        	        
        	       String responsesubstring13=responsesubstring11[1].trim();
        	        
        	        int remaininghoursUI =Integer.parseInt(responsesubstring12);
        	        
        	        int totalhoursUI=Integer.parseInt(responsesubstring13);
        	        
        	        System.out.println(remaininghoursUI);
        	        
        	        System.out.println(totalhoursUI);
        	        
        	        if (totalhoursapi==totalhoursUI)
        	        {
        	        	Log.info("The total hours value is matching");
        	        	System.out.println("The total hours value is matching");
        	        	DriverScript.bResult = true;
        	        }
        	        
        	        else
        	        {
        	        	ScreenshotCapture.takeScreenShot(driver);
        	        
        	        	Log.info("The total hours value is not matching");
        	        	System.out.println("The total hours value is not matching");
        	        	DriverScript.bResult = false;
        	        }
        	        
        	        if (remaininghoursapi==remaininghoursUI)
        	        {
        	        	
        	        	Log.info("The remainining hours value is matching");
        	        	System.out.println("The remainining hours value is matching");
        	        	DriverScript.bResult = true;
        	        	
        	        }
        	        
        	        else
        	        {
        	        	ScreenshotCapture.takeScreenShot(driver);
   
        	        	Log.info("The remainining hours value is not matching");
        	        	System.out.println("The remainining hours value is not matching");
        	        	DriverScript.bResult = false;
        	        }
        	        
        	 
			        
			        
			
		
		
	}
	   catch(Exception e)
	{
		
		ScreenshotCapture.takeScreenShot(driver);
		Log.info(e.getMessage());
		
		DriverScript.bResult = false;
	}
	
}



public static void verifySMexpirydate(String object, String data)
{
	
	
	try {
		final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin04\",\r\n" +
		        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
		    System.out.println(POST_PARAMS);
		    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
		    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		    postConnection.setRequestMethod("POST");
		    postConnection.setRequestProperty("clienttype", "1");
		   postConnection.setRequestProperty("Content-Type", "application/json");
		    postConnection.setDoOutput(true);
		    OutputStream os = postConnection.getOutputStream();
		    os.write(POST_PARAMS.getBytes());
		    os.flush();
		    os.close();
		    int responseCode = postConnection.getResponseCode();
		    System.out.println("POST Response Code :  " + responseCode);
		    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		    BufferedReader in = new BufferedReader(new InputStreamReader(
		          postConnection.getInputStream()));
		      String inputLine;
		      StringBuffer response = new StringBuffer();
		      while ((inputLine = in .readLine()) != null) {
		          response.append(inputLine);
		      } in .close();
		       
		      String accesstoken =response.toString();
		      System.out.println(response.toString());
		      System.out.println(accesstoken);
		      
		      String accesstokensubstring[] = accesstoken.split(":");
		      
		      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
		      		      
		      String actualtoken=acesstokensubstring2[0];
		      
		      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
		      
		      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
		      
		      System.out.println(actualtoken1+"is the new token");
		   
		      
		      URL obj1 = new URL("https://mtsadminwebapiic200.azurewebsites.net/api/license/getlicenseinfo");
		      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
		      postConnection1.setRequestMethod("GET");
		      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
		      postConnection1.setRequestProperty("Content-Type", "application/json");
		      postConnection1.setDoOutput(true);
		      int responseCode1 = postConnection1.getResponseCode();
		      System.out.println("POST Response Code :  " + responseCode1);
		      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
		      BufferedReader in1 = new BufferedReader(new InputStreamReader(
		            postConnection1.getInputStream()));
		        String inputLine1;
		        StringBuffer response1 = new StringBuffer();
		        while ((inputLine1 = in1 .readLine()) != null) {
		            response1.append(inputLine1);
		        } in1 .close();
		        
		        
		        System.out.println(response1.toString());
		        
		        Log.info(response1.toString());
		        
		        
		        String responsesubstring01=response1.toString();
		        
		        String responsesubstring02[] = responsesubstring01.split("expiryDate");
		        
		        String responsesubstring03=responsesubstring02[1];
		        
		        System.out.println(responsesubstring03);
		        
		        String responsesubstring04[]=responsesubstring03.split(":");
		        
		        System.out.println(responsesubstring04[1]);
		        
		        String responsesubstring05[]=responsesubstring04[1].split("\"");
		        
		        System.out.println( responsesubstring05[1]);
		        
		        String responsesubstring06[]=responsesubstring05[1].split("-");
		        //year
		        System.out.println(responsesubstring06[0]);
		        
		        //month
		        
		        System.out.println(responsesubstring06[1]);
		        //date
		        
		        System.out.println(responsesubstring06[2]);
		        String responsesubstring07[]=responsesubstring06[2].split("T");
		        System.out.println(responsesubstring07[0]);
		        
		        String responsesubstring08=responsesubstring06[1].toString();
		        
		        String responsesubstring09 = responsesubstring06[0].toString();
		        
		        String responsesubstring10 =responsesubstring07[0].toString();
		        
		        int yearapi=Integer.parseInt(responsesubstring09);
		        
		        int monthapi = Integer.parseInt(responsesubstring08);
		        
		        int dateapi = Integer.parseInt(responsesubstring10);
		        
		        System.out.println(yearapi+"IS THE YEAR FROM API");
		        
		        System.out.println(monthapi+"IS THE MONTH FROM API");
		        
		        System.out.println(dateapi+"IS THE DATE FROM API");
		        
		        
		        
		        WebElement expirydate= driver.findElement(By.xpath("//*[contains(text(),'Expiry Date:')]/.."));
    	        
    	        String expirydatetext = expirydate.getText();
    	        
    	        System.out.println(expirydatetext+"is the expiry date from UI");
    	        
    	        
    	        String responsesubstring11[]=expirydatetext.split(":");
    	        
    	        String responsesubstring12=responsesubstring11[1];
    	        
    	        String responsesubstring13[] =responsesubstring12.split(",");
    	        
    	        String responsesubstring14=responsesubstring13[0];
    	        
    	        System.out.println(responsesubstring14);
    	        
    	        String responsesubstring15=responsesubstring14.trim();
    	        
    	        System.out.println(responsesubstring15);
    	        
    	        String responsesubstring16 = responsesubstring15.substring(0, 3);
    	        
    	        System.out.println(responsesubstring16);
    	        
    	        String responsesubstring17 = responsesubstring15.substring(4,6);
    	        
    	        System.out.println(responsesubstring17);
    	        
    	        
    	        Date date = new SimpleDateFormat("MMMM").parse(responsesubstring16);
    	        Calendar cal = Calendar.getInstance();
    	        cal.setTime(date);
    	        System.out.println(cal.get(Calendar.MONTH));
    	        
    	        int monthui = cal.get(Calendar.MONTH)+1;
    	        
    	        System.out.println(monthui+" is the month from UI");
    	        
    	        int dateui = Integer.parseInt(responsesubstring17);
    	        
    	        System.out.println(dateui+" is the date from UI");
    	        
    	        //nOW PERFORMING TO GET YEAR
    	        
    	        String responsesubstring18[]=expirydatetext.split(",");
    	        
    	        System.out.println(responsesubstring18[1]);
    	        
    	        String responsesubstring19=responsesubstring18[1].trim();
    	        
    	        System.out.println(responsesubstring19);
    	        
    	        String responsesubstring20=responsesubstring19.substring(0, 4);
    	        
    	        System.out.println(responsesubstring20);
    	        
    	        int yearui=Integer.parseInt(responsesubstring20);
    	        
    	        System.out.println(yearui+"is the year from UI");
    	        
    	        
    	        if (yearapi==yearui)
    	        {
    	        	
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        	Log.info("The year is matching");
    	        	System.out.println("The year is matching");
    	        	DriverScript.bResult = true;
    	        }
    	        
    	        else
    	        {
    	        	
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        	Log.info("The year is not matching");
    	        	System.out.println("The year is not matching");
    	        	DriverScript.bResult = false;
    	        }
    	        
    	        
    	        if (monthapi==monthui)
    	        {
    	        	
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        	Log.info("The month is matching");
    	        	System.out.println("The month is matching");
    	        	DriverScript.bResult = true;
    	        }
    	        
    	        else
    	        {
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        
    	        	Log.info("The month is not matching");
    	        	System.out.println("The month is not matching");
    	        	DriverScript.bResult = false;
    	        }
    	        
    	        if (dateapi==dateui)
    	        {
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        	Log.info("The date is matching");
    	        	System.out.println("The date is matching");
    	        	DriverScript.bResult = true;
    	        }
    	        
    	        else
    	        {
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        
    	        	Log.info("The date is not matching");
    	        	System.out.println("The date is not matching");
    	        	DriverScript.bResult = false;
    	        }
	}
    	        
    	        
    	        
    	        
    	        
    	        
   catch(Exception e)
		{
			
			ScreenshotCapture.takeScreenShot(driver);
			Log.info(e.getMessage());
			
			DriverScript.bResult = false;
		}
		



}



public static void verifyIC300expirydate(String object, String data)
{
	
	
	try {
		final String POST_PARAMS = "{\n" + "\"Username\":\"icautoadmin02\",\r\n" +
		        "    \"Password\":\"Honeywell123\",\r\n" + "\n}";
		    System.out.println(POST_PARAMS);
		    URL obj = new URL("https://mtswebapiic200.azurewebsites.net/api/users?clienttype=1");
		    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		    postConnection.setRequestMethod("POST");
		    postConnection.setRequestProperty("clienttype", "1");
		   postConnection.setRequestProperty("Content-Type", "application/json");
		    postConnection.setDoOutput(true);
		    OutputStream os = postConnection.getOutputStream();
		    os.write(POST_PARAMS.getBytes());
		    os.flush();
		    os.close();
		    int responseCode = postConnection.getResponseCode();
		    System.out.println("POST Response Code :  " + responseCode);
		    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		    BufferedReader in = new BufferedReader(new InputStreamReader(
		          postConnection.getInputStream()));
		      String inputLine;
		      StringBuffer response = new StringBuffer();
		      while ((inputLine = in .readLine()) != null) {
		          response.append(inputLine);
		      } in .close();
		       
		      String accesstoken =response.toString();
		      System.out.println(response.toString());
		      System.out.println(accesstoken);
		      
		      String accesstokensubstring[] = accesstoken.split(":");
		      
		      String acesstokensubstring2[]=accesstokensubstring[1].split(",");
		      		      
		      String actualtoken=acesstokensubstring2[0];
		      
		      String actualtoken1=actualtoken.substring(1, actualtoken.length()-1);
		      
		      System.out.println(acesstokensubstring2[0].toString()+"is the access token");
		      
		      System.out.println(actualtoken1+"is the new token");
		   
		      
		      URL obj1 = new URL("https://mtsadminwebapiic200.azurewebsites.net/api/license/getlicenseinfo");
		      HttpURLConnection postConnection1 = (HttpURLConnection) obj1.openConnection();
		      postConnection1.setRequestMethod("GET");
		      postConnection1.setRequestProperty("Authorization", "Bearer "+actualtoken1);
		      postConnection1.setRequestProperty("Content-Type", "application/json");
		      postConnection1.setDoOutput(true);
		      int responseCode1 = postConnection1.getResponseCode();
		      System.out.println("POST Response Code :  " + responseCode1);
		      System.out.println("POST Response Message : " + postConnection1.getResponseMessage());
		      BufferedReader in1 = new BufferedReader(new InputStreamReader(
		            postConnection1.getInputStream()));
		        String inputLine1;
		        StringBuffer response1 = new StringBuffer();
		        while ((inputLine1 = in1 .readLine()) != null) {
		            response1.append(inputLine1);
		        } in1 .close();
		        
		        
		        System.out.println(response1.toString());
		        
		        Log.info(response1.toString());
		        
		        
		        String responsesubstring01=response1.toString();
		        
		        String responsesubstring02[] = responsesubstring01.split("expiryDate");
		        
		        String responsesubstring03=responsesubstring02[1];
		        
		        System.out.println(responsesubstring03);
		        
		        String responsesubstring04[]=responsesubstring03.split(":");
		        
		        System.out.println(responsesubstring04[1]);
		        
		        String responsesubstring05[]=responsesubstring04[1].split("\"");
		        
		        System.out.println( responsesubstring05[1]);
		        
		        String responsesubstring06[]=responsesubstring05[1].split("-");
		        //year
		        System.out.println(responsesubstring06[0]);
		        
		        //month
		        
		        System.out.println(responsesubstring06[1]);
		        //date
		        
		        System.out.println(responsesubstring06[2]);
		        String responsesubstring07[]=responsesubstring06[2].split("T");
		        System.out.println(responsesubstring07[0]);
		        
		        String responsesubstring08=responsesubstring06[1].toString();
		        
		        String responsesubstring09 = responsesubstring06[0].toString();
		        
		        String responsesubstring10 =responsesubstring07[0].toString();
		        
		        int yearapi=Integer.parseInt(responsesubstring09);
		        
		        int monthapi = Integer.parseInt(responsesubstring08);
		        
		        int dateapi = Integer.parseInt(responsesubstring10);
		        
		        System.out.println(yearapi+"IS THE YEAR FROM API");
		        
		        System.out.println(monthapi+"IS THE MONTH FROM API");
		        
		        System.out.println(dateapi+"IS THE DATE FROM API");
		        
		        
		        
		        WebElement expirydate= driver.findElement(By.xpath("//*[contains(text(),'Expiry Date:')]/.."));
    	        
    	        String expirydatetext = expirydate.getText();
    	        
    	        System.out.println(expirydatetext+"is the expiry date from UI");
    	        
    	        
    	        String responsesubstring11[]=expirydatetext.split(":");
    	        
    	        String responsesubstring12=responsesubstring11[1];
    	        
    	        String responsesubstring13[] =responsesubstring12.split(",");
    	        
    	        String responsesubstring14=responsesubstring13[0];
    	        
    	        System.out.println(responsesubstring14);
    	        
    	        String responsesubstring15=responsesubstring14.trim();
    	        
    	        System.out.println(responsesubstring15);
    	        
    	        String responsesubstring16 = responsesubstring15.substring(0, 3);
    	        
    	        System.out.println(responsesubstring16);
    	        
    	        String responsesubstring17 = responsesubstring15.substring(4,6);
    	        
    	        System.out.println(responsesubstring17);
    	        
    	        
    	        Date date = new SimpleDateFormat("MMMM").parse(responsesubstring16);
    	        Calendar cal = Calendar.getInstance();
    	        cal.setTime(date);
    	        System.out.println(cal.get(Calendar.MONTH));
    	        
    	        int monthui = cal.get(Calendar.MONTH)+1;
    	        
    	        System.out.println(monthui+" is the month from UI");
    	        
    	        int dateui = Integer.parseInt(responsesubstring17);
    	        
    	        System.out.println(dateui+" is the date from UI");
    	        
    	        //nOW PERFORMING TO GET YEAR
    	        
    	        String responsesubstring18[]=expirydatetext.split(",");
    	        
    	        System.out.println(responsesubstring18[1]);
    	        
    	        String responsesubstring19=responsesubstring18[1].trim();
    	        
    	        System.out.println(responsesubstring19);
    	        
    	        String responsesubstring20=responsesubstring19.substring(0, 4);
    	        
    	        System.out.println(responsesubstring20);
    	        
    	        int yearui=Integer.parseInt(responsesubstring20);
    	        
    	        System.out.println(yearui+"is the year from UI");
    	        
    	        
    	        if (yearapi==yearui)
    	        {
    	        	
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        	Log.info("The year is matching");
    	        	System.out.println("The year is matching");
    	        	DriverScript.bResult = true;
    	        }
    	        
    	        else
    	        {
    	        	
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        	Log.info("The year is not matching");
    	        	System.out.println("The year is not matching");
    	        	DriverScript.bResult = false;
    	        }
    	        
    	        
    	        if (monthapi==monthui)
    	        {
    	        	
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        	Log.info("The month is matching");
    	        	System.out.println("The month is matching");
    	        	DriverScript.bResult = true;
    	        }
    	        
    	        else
    	        {
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        
    	        	Log.info("The month is not matching");
    	        	System.out.println("The month is not matching");
    	        	DriverScript.bResult = false;
    	        }
    	        
    	        if (dateapi==dateui)
    	        {
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        	Log.info("The date is matching");
    	        	System.out.println("The date is matching");
    	        	DriverScript.bResult = true;
    	        }
    	        
    	        else
    	        {
    	        	ScreenshotCapture.takeScreenShot(driver);
    	        
    	        	Log.info("The date is not matching");
    	        	System.out.println("The date is not matching");
    	        	DriverScript.bResult = false;
    	        }
	}
    	        
    	        
    	        
    	        
    	        
    	        
   catch(Exception e)
		{
			
			ScreenshotCapture.takeScreenShot(driver);
			Log.info(e.getMessage());
			
			DriverScript.bResult = false;
		}
		



}


public static void offtraineeaction(String object, String data)
{
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	try {
	List<WebElement> actionnames= driver.findElements(By.xpath("//*[@ng-repeat='action in actionNames']"));	
	int i;
	for (i=0;i<actionnames.size();i++)
	{
		String actiontext= actionnames.get(i).getText();
		
		Log.info(actiontext);
		if(actiontext.contains(data))
		{
		Log.info("Text is matching");
		
		
			//*[@ng-repeat='action in trainerActions'][4]/*/*
		
		int b = i+1;
		

		
		//*[@ng-repeat='action in trainerActions'][4]/*/div[@class='toggle btn btn-primary']/* if on the class will be toggle btn btn-primary
		
		
		//if off class = toggle btn btn-default off
		try {
			WebElement traineractions= driver.findElement(By.xpath("//*[@ng-repeat='action in traineeActions']["+b+"]/*/div[@class='toggle btn btn-primary']/*"));
			
			System.out.println(i);
			
			Highlight.highlightElement(traineractions);
			
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", traineractions);
		//	traineractions.click();
			DriverScript.bResult = true;	
			
		}
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.info("Switch is already off");
			
			DriverScript.bResult = false;
		}
			
		}
		
		else
		{
			Log.info("There is no permission with the given text");
			System.out.println("There is no permission with the given text");
			//DriverScript.bResult = false;
			
		}
		
		
	}
	
	}
	
	
	catch(Exception e)
	{
		
		ScreenshotCapture.takeScreenShot(driver);
		Log.info(e.getMessage());
		
		DriverScript.bResult = false;
	}
	
	
	
	
	
	
}

public static void ontraineeaction(String object, String data)
{
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	try {
	List<WebElement> actionnames= driver.findElements(By.xpath("//*[@ng-repeat='action in actionNames']"));	
	int i;
	for (i=0;i<actionnames.size();i++)
	{
		String actiontext= actionnames.get(i).getText();
		
		Log.info(actiontext);
		if(actiontext.contains(data))
		{
		Log.info("Text is matching");
		
		
			//*[@ng-repeat='action in trainerActions'][4]/*/*
		
		int b = i+1;
		

		
		//*[@ng-repeat='action in trainerActions'][4]/*/div[@class='toggle btn btn-primary']/* if on the class will be toggle btn btn-primary
		
		
		//if off class = toggle btn btn-default off
		try {
			WebElement traineractions= driver.findElement(By.xpath("//*[@ng-repeat='action in traineeActions']["+b+"]/*/div[@class='toggle btn btn-default off']/*"));
			
			System.out.println(i);
			
			Highlight.highlightElement(traineractions);
			
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", traineractions);
		//	traineractions.click();
			DriverScript.bResult = true;	
			
		}
		
		catch(Exception e)
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.info("Switch is already on");
			
			DriverScript.bResult = false;
		}
			
		}
		
		else
		{
			ScreenshotCapture.takeScreenShot(driver);
			Log.info("There is no permission with the given text");
			System.out.println("There is no permission with the given text");
			//DriverScript.bResult = false;
			
		}
		
		
	}
	
	}
	
	
	catch(Exception e)
	{
		ScreenshotCapture.takeScreenShot(driver);
		Log.info(e.getMessage());
		
		DriverScript.bResult = false;
	}
	
	
	
	
	
	
}


public static void verifyElementNotDisabled(String object, String data){
	try {
		
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		Log.info("Verify Element Disabled " + object);
		String eenabled = objectLocator(object).getAttribute("class");
		System.out.println(eenabled);
		if (eenabled.contains("disable")) {
			
			ScreenshotCapture.takeScreenShot(driver);
			Highlight.highlightElement(objectLocator(object));
			Log.error("Element is disabled");
			System.out.println("Element is disabled");
			
			DriverScript.failedException = "Element is disabled";
			DriverScript.bResult = false;
			
		} else {
			ScreenshotCapture.takeScreenShot(driver);
			Highlight.highlightElement(objectLocator(object));
			Log.info("Element is enabled");
			System.out.println("Element is enabled");
			DriverScript.bResult = true;
			
		}
	} catch (Exception e) {
		ScreenshotCapture.takeScreenShot(driver);
		Log.error("Not able to locate element --- " + e.getMessage());
		DriverScript.bResult = false;
		DriverScript.failedException = e.getMessage();
	}
}


public static void verifyloginpageloadtime(String object,String data)
{
	
	
	long start = System.currentTimeMillis();
	
	//System.out.println(start+"is the start time");

	driver.get(data);

	long finish = System.currentTimeMillis();
	
	//System.out.println(finish+"is the end time");
	long totalTime = finish - start; 
	System.out.println("Total Time for page load - "+totalTime); 
	Log.info("Total Time for page load - "+totalTime);
}

public static void verifyelementloadtime(String object,String data)
{
	
	
	
    
    objectLocator(object).click();
    
    
    
    long start = System.currentTimeMillis();
    
   // System.out.println(start+"is the start time");
    
    WebDriverWait wait = new WebDriverWait(driver, 30);
    
   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(data)));
    
   // waituntilclickable(object, data);
    
//    driver.findElement(By.xpath(data));
//    Highlight.highlightElement(driver.findElement(By.xpath(data)));
    long finish = System.currentTimeMillis();
    
    System.out.println(finish+"is the end time");
	long totalTime = finish - start; 
	System.out.println("Total Time for page load - "+totalTime); 
	Log.info("Total Time for page load - "+totalTime);

   // pageLoad.stop();
    
//    long pageLoadTime_ms = pageLoad.getTime();
//    long pageLoadTime_Seconds = pageLoadTime_ms / 1000;
//    System.out.println("Total Page Load Time: " + pageLoadTime_ms + " milliseconds");
//    System.out.println("Total Page Load Time: " + pageLoadTime_Seconds + " seconds");
	
	
	
	
}







}






