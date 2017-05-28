package com.kineticskunk.driverutilities;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Cookies {
	
	private WebDriver wd;
	
	public Cookies(WebDriver wd) {
		this.wd = wd;
	}
	
	public void clearCookies() {
		this.wd.manage().deleteAllCookies();
	}
	
	public void getCookies() throws Exception {
		
		this.wd.navigate().to("https://yeboyethushares.uat.tradedesk.co.za/VTBBEE/Index");
		
		for(String winHandle : this.wd.getWindowHandles()){
			System.out.println(this.wd.switchTo().window(winHandle).getCurrentUrl());
        }
		
		
		
		WebElement we = this.wd.findElement(By.id("forWATCHLIST"));
		List<WebElement> lwe = we.findElements(By.tagName("div"));
		for (int i = 0; i <= lwe.size() - 1; i++) {
			System.out.println(lwe.get(i).getText());
		}
		
		Set<Cookie> cookies = this.wd.manage().getCookies();
		Iterator<Cookie> itr = cookies.iterator();

	    while (itr.hasNext()){
	    Cookie c = itr.next();
	    System.out.println("Cookie Name: " + c.getName() + " --- " + "Cookie Domain: " + c.getDomain() + " --- " + "Cookie Value: " + c.getValue());
	    }
		
		
	}

}
