/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012, 2013, 2014 Zimbra, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.qa.selenium.projects.ajax.tests.preferences.attributes;

import org.testng.annotations.*;

import com.zimbra.qa.selenium.framework.core.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.*;
import com.zimbra.qa.selenium.projects.ajax.ui.calendar.DialogFindAttendees;
import com.zimbra.qa.selenium.projects.ajax.ui.calendar.FormApptNew;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.*;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormAddressPicker.Field;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormAddressPicker.Locators;


public class ZimbraFeatureContactsDetailedSearchEnabled extends PrefGroupMailByMessageTest {

	public ZimbraFeatureContactsDetailedSearchEnabled() {
		logger.info("New "+ ZimbraFeatureContactsDetailedSearchEnabled.class.getCanonicalName());

		super.startingAccountPreferences.put("ZimbraFeatureContactsDetailedSearchEnabled", "TRUE");		
	}

	@Bugs(ids = "70708")
	@Test(	description = "Filter addresses using department name after selecting \"To:\" while composing mail",
	groups = { "functional" })
	public void ZimbraFeatureContactsDetailedSearchEnabled_01() throws HarnessException {

		String department=ZimbraSeleniumProperties.getUniqueString();

		//-- Data

		// Add departments to three accounts	
		ZimbraAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zimbraAdmin'>"
						+		"<id>"+ZimbraAccount.Account5().ZimbraId +"</id>"
						+		"<a n='ou'>"+department+"</a>"
						+	"</ModifyAccountRequest>");

		ZimbraAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zimbraAdmin'>"
						+		"<id>"+ZimbraAccount.Account6().ZimbraId +"</id>"
						+		"<a n='ou'>"+department+"</a>"
						+	"</ModifyAccountRequest>");

		ZimbraAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zimbraAdmin'>"
						+		"<id>"+ ZimbraAccount.Account7().ZimbraId +"</id>"
						+		"<a n='ou'>HR</a>"
						+	"</ModifyAccountRequest>");


		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		//Click on To button
		FormAddressPicker selectAddress =(FormAddressPicker)mailform.zToolbarPressButton(Button.B_TO);

		//Enter department to search
		selectAddress.zFillField(Field.Department,department );
		selectAddress.zToolbarPressButton(Button.B_SEARCH);

		//Check that correct addresses are filtered out		
		ZAssert.assertTrue(selectAddress.sIsElementPresent(Locators.SearchResultArea+":contains('" + ZimbraAccount.Account5().EmailAddress + "')"),"Verify that correct addresses are filtered out");
		ZAssert.assertTrue(selectAddress.sIsElementPresent(Locators.SearchResultArea+":contains('" + ZimbraAccount.Account6().EmailAddress + "')"),"Verify that correct addresses are filtered out");
		
		//Check that department names are also displayed
		ZAssert.assertTrue(selectAddress.sIsElementPresent(Locators.SearchResultArea+":contains('" + department + "')"),"Verify that search result containing the department name is displayed ");

		//Selecting the first addresses from the search result and moving to recipient box		
		selectAddress.zClick(Locators.ContactPickerFirstContact);
		selectAddress.zToolbarPressButton(Button.B_TO);

		//Verify that  the selected address has been moved to recipient box and department name is displayed in recipient box as well
		ZAssert.assertTrue(selectAddress.sIsElementPresent(Locators.SearchResultArea+":contains('" + department + "'):contains('To:')"), "Verify that department is displayed in recipient box as well");
	    this.app.zPageLogin.zNavigateTo();
	    this.startingPage.zNavigateTo();

	}		

	@Bugs(ids = "70708")
	@Test(	description = "Filter addresses using department name after selecting \"To:\" while composing appointments",
	groups = { "functional" })
	public void ZimbraFeatureContactsDetailedSearchEnabled_02() throws HarnessException {

		String department=ZimbraSeleniumProperties.getUniqueString();
		
		// Add departments to three accounts	
		ZimbraAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zimbraAdmin'>"
						+		"<id>"+ZimbraAccount.Account1().ZimbraId +"</id>"
						+		"<a n='ou'>"+department+"</a>"
						+	"</ModifyAccountRequest>");

		ZimbraAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zimbraAdmin'>"
						+		"<id>"+ZimbraAccount.Account2().ZimbraId +"</id>"
						+		"<a n='ou'>"+department+"</a>"
						+	"</ModifyAccountRequest>");

		ZimbraAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zimbraAdmin'>"
						+		"<id>"+ ZimbraAccount.Account3().ZimbraId +"</id>"
						+		"<a n='ou'>ProductManagement</a>"
						+	"</ModifyAccountRequest>");


		app.zPageCalendar.zNavigateTo();
		// Open the new mail form
		FormApptNew apptForm = (FormApptNew) app.zPageCalendar.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(apptForm, "Verify the new form opened");
		
        apptForm.zToolbarPressButton(Button.B_TO);
        DialogFindAttendees dialogFindAttendees = (DialogFindAttendees) new DialogFindAttendees(app, app.zPageCalendar);
        
        //Enter department to search
        dialogFindAttendees.zFillField(com.zimbra.qa.selenium.projects.ajax.ui.calendar.DialogFindAttendees.Field.Department, department);
		dialogFindAttendees.zClickButton(Button.B_SEARCH);
        //Click on To button

		//Check that correct addresses are filtered out		
		ZAssert.assertTrue(dialogFindAttendees.sIsElementPresent(Locators.SearchResultArea+":contains('" + ZimbraAccount.Account1().EmailAddress + "')"),"Verify that correct addresses are filtered out");
		ZAssert.assertTrue(dialogFindAttendees.sIsElementPresent(Locators.SearchResultArea+":contains('" + ZimbraAccount.Account2().EmailAddress + "')"),"Verify that correct addresses are filtered out");
		
		//Check that department names are also displayed
		ZAssert.assertTrue(dialogFindAttendees.sIsElementPresent(Locators.SearchResultArea+":contains('" + department + "')"),"Verify that search result containing the department name is displayed ");

		//Selecting the first addresses from the search result and moving to recipient box		
        dialogFindAttendees.zClickButton(Button.B_SELECT_FIRST_CONTACT);
        dialogFindAttendees.zClickButton(Button.B_CHOOSE_CONTACT_FROM_PICKER);

		//Verify that  the selected address has been moved to recipient box and department name is displayed in recipient box as well
		ZAssert.assertTrue(dialogFindAttendees.sIsElementPresent("css=div[id='ZmContactPicker'] div[class='DwtChooserListView'] div[id^='zli__DWT'] td:contains('" + department + "')"), "Verify that department is visible in recipient box as well");
	}		
}

