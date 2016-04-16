package cn.tjuscs.selenium;

import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Selenium {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String id,em;


	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://www.ncfxy.com";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


		String filePath = "I:\\selenium\\info.csv";

		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(filePath));
			String lineText = null;

			List<String> listLines = new ArrayList<String>();

			while ((lineText = lineReader.readLine()) != null) {
				listLines.add(lineText);
			}

			String[] arr = new String[listLines.size()];
			arr = listLines.toArray(arr);

			for(int i = 0;i < listLines.size();i++){			
				String[] split_s    = arr[i].split(",");

				id = split_s[0];
				em = split_s[1];
				testSelenium();
			}
			
			lineReader.close();
		} catch (IOException ex) {
			System.err.println(ex);
		}	

	}

	@Test
	public void testSelenium() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys(id);
		driver.findElement(By.id("pwd")).clear();
		char[] charArr = id.toCharArray();
		StringBuilder sb = new StringBuilder();
		sb.append(charArr[4]);
		sb.append(charArr[5]);
		sb.append(charArr[6]);
		sb.append(charArr[7]);
		sb.append(charArr[8]);
		sb.append(charArr[9]);
		String pw = sb.toString();
		driver.findElement(By.id("pwd")).sendKeys(pw);
		driver.findElement(By.id("submit")).click();
		String email = driver.findElement(By.xpath(".//*[@id='table-main']/tr[1]/td[2]")).getText();
		String name = driver.findElement(By.xpath(".//*[@id='table-main']/tr[2]/td[2]")).getText();
		Assert.assertTrue(em.equals(email));
		Assert.assertTrue(id.equals(name));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
