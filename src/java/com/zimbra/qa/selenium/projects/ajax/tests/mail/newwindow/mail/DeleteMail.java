/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2013, 2014 Zimbra, Inc.
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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.newwindow.mail;

import org.apache.log4j.*;
import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.SeparateWindowDisplayMail;


public class DeleteMail extends PrefGroupMailByMessageTest {
	protected static Logger logger = LogManager.getLogger(DeleteMail.class);

	public DeleteMail() throws HarnessException {
		logger.info("New "+ DeleteMail.class.getCanonicalName());
		


	}
	
	@Bugs(	ids = "103950")
	@Test(	description = "Delete a message from a separate window",
			groups = { "functional" })
	public void DeleteMail_01() throws HarnessException {
		
		final String subject = "subject13150210210153";

		ZimbraAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>"+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>body" + ZimbraSeleniumProperties.getUniqueString() +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");
		
		
		// Refresh current view
		app.zPageMail.zVerifyMailExists(subject);

		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		SeparateWindowDisplayMail window = null;
		
		try {
			
			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);
			
			window.zSetWindowTitle(subject);
			window.zWaitForActive();		// Make sure the window is there
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			window.zToolbarPressButton(Button.B_DELETE);
			
			// It may take some time for the window to close, so
			// wait for up to 15 seconds for the window to close
			for (int i = 0; i < 15; i++) {
				if ( !window.zIsActive() )
					break;
				SleepUtil.sleep(1000);
			}
			
			ZAssert.assertFalse(window.zIsActive(), "Verify the window is closed");
			
			window = null;

			FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Trash);
			MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +") is:anywhere");
			ZAssert.assertEquals(mail.dFolderId, trash.getId(), "Verify the message is not in the trash folder");
			
		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}
		
		
	}




}
