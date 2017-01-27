package com.crawler.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WrappedWebDriver extends FirefoxDriver{
	
	private static String dummy = System.setProperty("webdriver.gecko.driver", "./driver/geckodriver");
	
	private static void setDriver(WrappedWebDriver self){
		try {
			self.getCurrentUrl();
		} catch (Exception e) {
			try {
				self.close();
			} catch (Exception ex) {
				System.err.println("hung firefox driver");
				ex.printStackTrace();
			}
			self = new WrappedWebDriver();
		}
	}
	
	public void getAndWait(String url, String cssSelector){
		setDriver(this);
		this.get(url);
		wait(30, cssSelector);
	}
	
	public void close(){
		setDriver(this);
		this.close();
	}
	
	public void clickAndWait(String cssSelector, String waitSelector){
		setDriver(this);
		WebElement link = this.findElement(By.cssSelector(cssSelector));
		link.click();
		wait(30, waitSelector);
	}
	public List<WebElement> getElements(String cssSelector){
		return this.findElements(By.cssSelector(cssSelector));
	}
	
	public WebElement getElement(String cssSelector){
		return this.findElement(By.cssSelector(cssSelector));
	}
	
	public String getText(String cssSelector){
		setDriver(this);
		WebElement element = this.findElement(By.cssSelector(cssSelector));
		return element.getText();
	}
	
	public String getAttribute(String cssSelector, String attribute){
		setDriver(this);
		WebElement element = this.findElement(By.cssSelector(cssSelector));
		return element.getAttribute(attribute);
	}
		
	public void inputForm(String cssSelector, String inputText){
		setDriver(this);
		WebElement input = this.findElement(By.cssSelector(cssSelector));
		input.clear();
		input.sendKeys(inputText);
	}
	
	public void wait(int timeout, String cssSelector){
		setDriver(this);
		WebDriverWait wait = new WebDriverWait(this, timeout);
		if (cssSelector == null ){
			cssSelector = "body";
		}
		System.out.println("waiting...:"+cssSelector);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.close();
		super.finalize();
	}
}
