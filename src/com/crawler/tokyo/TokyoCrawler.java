package com.crawler.tokyo;

import com.crawler.CourtType;
import com.crawler.Crawler;
import com.crawler.entity.Court;
import com.crawler.entity.Facility;
import com.crawler.entity.Frame;
import com.crawler.utils.DateConverter;
import com.crawler.utils.WrappedWebDriver;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class TokyoCrawler implements Crawler {

	private Map<String, Facility> facilities = new HashMap<>();

	public boolean crawle() {
		// Driverを生成
		WrappedWebDriver driver = new WrappedWebDriver();

		// スタートページヘ遷移
		goToTop(driver);

		// 日付を取得してループ開始
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		int nowMonth = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.DATE, 1);
		for (int i = 0; i < 65; i++) {
			// 次の日へ
			calendar.add(Calendar.DATE, 1);

			// 月が変わったらページ遷移
			int chekingMonth = calendar.get(Calendar.MONTH);
			if (chekingMonth != nowMonth) {
				goToNextMonth(driver);
			}

			// 次の日付へ遷移
			if (!goToTheDate(calendar.get(Calendar.DATE), driver)) {
				// クリックできないためスキップ
				continue;
			}

			// 施設情報を全て取得
			do {
				getFacilityStatus(driver, calendar);
			} while (goToNextPage(driver));
		}

		driver.close();
		return false;
	}

	/**
	 * 検索トップまで辿りつく
	 * 
	 * @param driver
	 */
	private void goToTop(WrappedWebDriver driver) {
		driver.getAndWait("https://yoyaku.sports.metro.tokyo.jp/user/view/user/homeIndex.html", "#purposeSearch");
		driver.clickAndWait("#purposeSearch", "#srchBtn");
		List<WebElement> checkboxs = driver.getElements("span#label");
		for (WebElement checkbox : checkboxs) {
			if (checkbox.getText().equals("テニス（ハード）") || checkbox.getText().equals("テニス（人工芝）")) {
				checkbox.click();
			}
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.clickAndWait("#srchBtn", null);
	}

	/**
	 * 次の月へ遷移
	 * 
	 * @param driver
	 */
	private void goToNextMonth(WrappedWebDriver driver) {
		driver.clickAndWait("td.calender > div > a", null);
	}

	/**
	 * 次の日へ遷移
	 * 
	 * @param date
	 * @param driver
	 */
	private boolean goToTheDate(Integer date, WrappedWebDriver driver) {
		List<WebElement> elements = driver.getElements("a.calclick");
		for (WebElement element : elements) {
			if (element.getText().equals(date.toString())) {
				element.click();
				driver.wait(30, null);
				return true;
			}
		}
		return false;
	}

	/**
	 * 次のページ番号へ進む
	 * 
	 * @param pageNumber
	 * @param driver
	 */
	private boolean goToNextPage(WrappedWebDriver driver) {
		try {
			WebElement goToNext = driver.getElement("a#goNextPager");
			goToNext.click();
			driver.wait(30, null);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	/**
	 * 各施設の空き情報を取得する
	 * @param driver
	 * @return
	 */
	private boolean getFacilityStatus(WrappedWebDriver driver, Calendar theDate) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {	
			List<WebElement> facilityTables = driver.getElements(".tablebg2");
			for (int i = 2; i < facilityTables.size(); i++) {
				WebElement facilityTable = facilityTables.get(i);
				Facility facility = null;
				Court court = null;
				
				// header
				String name = facilityTable.findElement(By.cssSelector("span#bnamem")).getText();
				facility = facilities.get(name);
				if (facility == null) {
					facility = new Facility();
					facility.setName(name);
				}
				String courtName = facilityTable.findElement(By.cssSelector("span#ppsname")).getText();
				court = facility.getCourts().get(courtName);
				if (court == null) {
					court = new Court();
					court.setName(courtName);
					CourtType type = CourtType.fromLabel(courtName.split("（")[1].split("）")[0]);
					court.setCourtType(type);
					facility.getCourts().put(courtName, court);
				}
				
				// time
				List<WebElement> timeTables = facilityTable.findElements(By.cssSelector("span#tzoneStimeLabel"));
				for (WebElement time : timeTables) {
					String[] dateStrings = time.getText().split(":");
					Frame frame = new Frame();
					
					theDate.set(Calendar.HOUR, Integer.parseInt(dateStrings[0]) );
					theDate.set(Calendar.MINUTE, Integer.parseInt(dateStrings[1]));
					Date start = theDate.getTime();
					frame.setStart(start);
					
					theDate.set(Calendar.HOUR, Integer.parseInt(dateStrings[0]) + 2);
					Date end = theDate.getTime();
					frame.setEnd(end);
	
					court.getFrames().add(frame);
				}
				
				// 空き面数
				List<WebElement> emptyTables = facilityTable.findElements(By.cssSelector("#emptyFieldCnt"));
				for (int k = 0; k < court.getFrames().size(); k++) {
					Integer size = Integer.parseInt(emptyTables.get(k).getText());
					court.getFrames().get(k).setSize(size);
				}

				facility.getCourts().put(court.getName(), court);
				facilities.put(facility.getName(), facility);
				System.out.println(facility);
			}
		} catch (NoSuchElementException se ){
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
