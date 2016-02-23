/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013, 2014 Zimbra, Inc.
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

package com.zimbra.qa.selenium.projects.ajax.tests.mail.conversation.conversations;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.RecipientItem;
import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;

public class CheckToFieldInConversationViewInSentFolder extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public CheckToFieldInConversationViewInSentFolder() {
		logger.info("New "+ CheckToFieldInConversationViewInSentFolder.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {{
			put("zimbraPrefGroupMailBy", "conversation");
		}};
	}

	
	@Bugs(ids = "67986,64067,47288,16213")
	@Test(	description = "To field should not display blank in a conversation in Sent folder",
			groups = { "functional" })
	
	public void CheckToFieldInConversationViewInSentFolder_01() throws HarnessException {

		//-- DATA
		
		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
				
		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ ZimbraAccount.AccountB().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>"+ "body" + ZimbraSeleniumProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");
		
		//-- GUI
		
		// Login
		app.zPageLogin.zLogin(ZimbraAccount.AccountA());
		
		// Refresh current view
		app.zPageMail.zVerifyMailExists(subject);
		
		// Click in sent
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Sent));
		
		//Click on subject
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// From the test account, check the sent folder 
		MailItem sent = MailItem.importFromSOAP(ZimbraAccount.AccountA(), "in:sent subject:("+ subject +")");
		ZAssert.assertNotNull(sent, "Verify the sent message in the sent folder");
		
		// To: should contain the original sender (AccountB)
		ZAssert.assertEquals(sent.dToRecipients.size(), 1, "Verify 1 'To'");
		boolean found1 = false;
		for (RecipientItem r : sent.dToRecipients) {
			logger.info("Looking for: "+ ZimbraAccount.AccountB().EmailAddress +" found "+ r.dEmailAddress);
			if ( r.dEmailAddress.equals(ZimbraAccount.AccountB().EmailAddress) ) {
				found1 = true;
				break;
			}
		}
		ZAssert.assertTrue(found1, "Verify the correct 'To' address was found");
		
	}

}