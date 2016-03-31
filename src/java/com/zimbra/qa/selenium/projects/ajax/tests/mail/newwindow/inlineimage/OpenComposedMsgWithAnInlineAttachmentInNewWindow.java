package com.zimbra.qa.selenium.projects.ajax.tests.mail.newwindow.inlineimage;

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

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.SeparateWindowFormMailNew;

public class OpenComposedMsgWithAnInlineAttachmentInNewWindow extends
PrefGroupMailByMessageTest {

	public OpenComposedMsgWithAnInlineAttachmentInNewWindow() {
		logger.info("New "+ OpenComposedMsgWithAnInlineAttachmentInNewWindow.class.getCanonicalName());
		super.startingAccountPreferences.put("zimbraPrefComposeFormat", "html");
	}

	@Test(description = "Verify inline attachment in Normal Compose window as well as in New compose window", groups = { "windows","functional" })
	public void OpenComposedMsgWithAnAttachmentInNewWindow_01() throws HarnessException {

		// Create file item

		final String fileName = "samplejpg.jpg";
		final String filePath = ZimbraSeleniumProperties.getBaseDirectory() + "\\data\\public\\other\\" + fileName;

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		// Fill out the form with the data
		//mailform.zFill(mail);

		// Upload the file
		app.zPageMail.zPressButton(Button.O_ATTACH_DROPDOWN);
		app.zPageMail.zPressButton(Button.B_ATTACH_INLINE);
		zUploadInlineImageAttachment(filePath);

		//Verify inline image in compose window
		ZAssert.assertTrue(app.zPageMail.zVerifyInlineImageAttachmentExistsInComposeWindow(), "Verify inline image is present in compose window");

		SeparateWindowFormMailNew window = null;

		try {

			window = (SeparateWindowFormMailNew) app.zPageMail.zToolbarPressButton(Button.B_DETACH_COMPOSE);

			window.zSetWindowTitle("Compose");
			window.waitForComposeWindow();
			ZAssert.assertTrue(window.zIsActive(),"Verify the window is active");

			// Select the window
			window.sSelectWindow("Zimbra: Compose");

			// Verify Inline Attachment should not disappeared  New compose window
			ZAssert.assertTrue(app.zPageMail.zVerifyInlineImageAttachmentExistsInComposeWindow(),"Verify inline image is present in New compose window");


		} finally {

			// Make sure to close the window
			if (window != null) {
				window.zCloseWindow();
				window = null;
			}
		}
	}
}