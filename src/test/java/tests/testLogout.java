package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class testLogout {
	
	WebDriver driver;
	WebDriverWait wait;
	WebElement logoutForm;
	WebElement messageSpan;
	String message;
	String[] testLoginData = { "user", "user" }; 
	
	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	@AfterMethod
	public void close() {
		driver.quit();
	}	
	
	@Ignore
	private void logout(String url, String username, String password) { //Pagrindinis metodas.
		
		System.out.println("\nTESTO PRADZIA:");	
		
		driver.navigate().to("http://localhost:8080/prisijungti?logout"); //atidaromas atsijungimo langas
		driver.manage().window().maximize();//padidinamas
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys(username);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(password);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=submit]"))).click();
		System.out.println("- Pasiruošimas testui baigtas.");		
		
		driver.navigate().to(url); //atidaromas langas
		System.out.println("- Atidarytas ["+url+"] puslapis.");

		logoutForm = driver.findElement(By.cssSelector("#logoutForm + a")); //tikrinama forma
		System.out.println("- Vartotojas prisijunges.");
			
		logoutForm.click(); //paspaudziaa
		System.out.println("- Atsijungimo nuoroda paspausta.");

		messageSpan = driver.findElement(By.cssSelector(".form-signin span")); //tikrinamas pranesimas
		String  mess = messageSpan.getText();
		System.out.println("- Gautas pranesimas:" + mess);
		
		message="Sėkmingai atsijungėte";
		Assert.assertEquals(mess, message, "[!] Pranesimai nesutampa.");
		System.out.println("- Pranesimai sutampa. Vartotojas atsijunge. ");		
		
		System.out.println("TESTO PABAIGA.\n");
	}
	
	
	
  @Test
  public void LogoutFromMain() { //Atsijungiama is skaiciuokles lango
	  logout("http://localhost:8080/", testLoginData[0], testLoginData[1]);
  }
  
  @Test
  public void LogoutFromList() { //Atsijungiama is operaciju saraso lango
	  logout("http://localhost:8080/skaiciai", testLoginData[0], testLoginData[1]);
  }
  
  @Test
  public void LogoutFromEdit() { //Atsijungiama is redagavimo saraso
	  logout("http://localhost:8080/atnaujinti?id=29", testLoginData[0], testLoginData[1]);
  }
  
  @Test
  public void LogoutFromView() { //Atsijungiama is perziuru saraso
	  logout("http://localhost:8080/rodyti?id=19", testLoginData[0], testLoginData[1]);
  }
		  
}