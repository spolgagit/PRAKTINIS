package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class testLogout {
	
	WebDriver driver;
	WebDriverWait wait;
	WebElement logoutForm;
	WebElement messageSpan;
	String message;
	String[] testLoginData = { "user", "user" }; 
	
	@BeforeTest
	public void setup() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	@AfterTest
	public void close() {
		driver.quit();
	}	
	
	@Ignore
	private void logout(String url, String username, String password) {
		
		System.out.println("\nTESTO PRADZIA:");	
		
		driver.navigate().to("http://localhost:8080/prisijungti?logout");
		driver.manage().window().maximize();
	
		//driver.findElement(By.name("username")).sendKeys("admin");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys(username);
		//driver.findElement(By.name("password")).sendKeys("admin");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(password);
		//driver.findElement(By.cssSelector("button[type=submit]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=submit]"))).click();
		System.out.println("- Pasiruošimas testui baigtas.");		
		
		driver.navigate().to(url);
		System.out.println("- Atidarytas ["+url+"] puslapis.");

		logoutForm = driver.findElement(By.cssSelector("#logoutForm + a"));
		System.out.println("- Vartotojas prisijunges.");
			
		logoutForm.click();
		System.out.println("- Atsijungimo nuoroda paspausta.");

		messageSpan = driver.findElement(By.cssSelector(".form-signin span"));
		String  mess = messageSpan.getText();
		System.out.println("- Gautas pranesimas:" + mess);
		
		message="Sėkmingai atsijungėte";
		Assert.assertEquals(mess, message, "[!] Pranesimai nesutampa.");
		System.out.println("- Pranesimai sutampa. Vartotojas atsijunge. ");		
		
		System.out.println("TESTO PABAIGA.\n");
	}
	
	
	
  @Test
  public void LogoutFromMain() {
	  logout("http://localhost:8080/", testLoginData[0], testLoginData[1]);
  }
  
  @Test
  public void LogoutFromList() {
	  logout("http://localhost:8080/skaiciai", testLoginData[0], testLoginData[1]);
  }
  
  @Test
  public void LogoutFromEdit() {
	  logout("http://localhost:8080/atnaujinti?id=29", testLoginData[0], testLoginData[1]);
  }
  
  @Test
  public void LogoutFromView() {
	  logout("http://localhost:8080/rodyti?id=19", testLoginData[0], testLoginData[1]);
  }
		  
}