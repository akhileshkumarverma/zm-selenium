/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013, 2014, 2015, 2016 Synacor, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.qa.selenium.projects.ajax.ui.tasks;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.projects.ajax.ui.*;


/**
 * The <code>FormMailNew<code> object defines a compose new message view
 * in the Zimbra Ajax client.
 * <p>
 * This class can be used to compose a new message.
 * <p>
 *
 * @author Matt Rhoades
 * @see http://wiki.zimbra.com/wiki/Testing:_Selenium:_ZimbraSelenium_Overview#Mail_Page
 */
public class FormTaskNew extends AbsForm {

	/**
	 * Defines Selenium locators for various objects in {@link FormTaskNew}
	 */
	public static class Locators {

		public static final String zSendIconBtn			= "css=[id^=zb__COMPOSE][id$=__SEND_title]";
		public static final String zCancelIconBtn		= "css=[id^=zb__COMPOSE][id$=__CANCEL_title]";
		public static final String zSaveDraftIconBtn	= "css=[id^=zb__COMPOSE][id$=__SAVE_DRAFT_title]";
		public static final String zSpellCheckIconBtn	= "css=[id^=zb__COMPOSE][id$=__SPELL_CHECK_title]";

		public static final String zToField				= "css=[id^=zv__COMPOSE][id$=_to_control]";
		public static final String zCcField				= "css=[id^=zv__COMPOSE][id$=_cc_control]";
		public static final String zBccField			= "css=[id^=zv__COMPOSE][id$=_bcc_control]";
		public static final String zSubjectField		= "css=div[id^=zv__COMPOSE] input[id$=_subject_control]";

		public static final String zBodyFrameHTML		= "//div[contains(id,'zv__COMPOSE')]//iframe";


		public static final String zPriorityPulldown	= "css=[id^=zv__COMPOSE][id$=___priority_dropdown]";
		public static final String zPriorityOptionHigh	= "css=[id^=zv__COMPOSE][id$=___priority_dropdown]";
		public static final String zPriorityOptionNormal	= "css=[id^=zv__COMPOSE][id$=___priority_dropdown]";
		public static final String zPriorityOptionLow	= "css=[id^=zv__COMPOSE][id$=___priority_dropdown]";

		public static final String zFrame = "css=iframe[id^='iframe_DWT']";
		public static final String zSaveAndCloseIconBtn = "//*[@id='DWT9_left_icon']";
		public static final String zBodyField = "css=body";
		public static final String zNameField = "css=[id^=DWT4] [input$=]";
		public static final String zEditNameField = "css=[class=DwtInputField] [input$=]";
		//public static final String zSaveTask = "css=div[id^='ztb__TKE']  tr[id^='ztb__TKE'] td[id$='__SAVE_title']";
		public static final String zSaveTask = "css=div[id^='ztb__TKE']  tr[id^='ztb__TKE'] td[id$='_title']:contains('Save')";
		//public static final String zTasksubjField = "//td[contains(@id,'zv__TKE1_subject')]/div/input";
		public static final String zTasksubjField = "css=td[id$='_subject'] div input";
		public static final String zTaskBodyField = "css=div[class='ZmTaskEditView'] div[id$='_notes'] textarea[id$='_body']";
		public static final String zTasksubjFieldDesktop = "//td[contains(@id,'_subject')]/div/input";
		//public static final String zCancelTask = "zb__TKE-1__CANCEL_left_icon";
		public static final String zCancelTask = "css=div[id^='ztb__TKE']  tr[id^='ztb__TKE'] td[id$='_title']:contains('Cancel')";
	//	public static final String zTaskOptionDropDown = "css=div[id^='ztb__TKE'] div[id$='__COMPOSE_OPTIONS'] td[id$='__COMPOSE_OPTIONS_dropdown']>div";
		public static final String zTaskFormatAsHtml="css=div[id$='_FORMAT_HTML']";
		public static final String zTaskFormatAsText="css=div[id$='_FORMAT_TEXT']";
		public static final String zCloseButton="css=div[id^='ztb__TKE']  tr[id^='ztb__TKE'] td[id$='_title']:contains('Close')";
	}

	public static class Field {

		public static final Field Subject = new Field("Subject");
		public static final Field Location = new Field("Location");
		public static final Field Body = new Field("Body");
		public static final Field HtmlBody = new Field("HtmlBody");
		public static final Field DueDate = new Field("DueDate");

		private String field;
		private Field(String name) {
			field = name;
		}

		@Override
		public String toString() {
			return (field);
		}

	}


	/**
	 * Protected constuctor for this object.  Only classes within
	 * this package should create DisplayMail objects.
	 *
	 * @param application
	 */
	public FormTaskNew(AbsApplication application) {
		super(application);

		logger.info("new " + FormTaskNew.class.getCanonicalName());

	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}


	@Override
	public void zSubmit() throws HarnessException {
		logger.info("FormTaskNew.zSubmit()");

		zToolbarPressButton(Button.B_SAVE);
	}

	/**
	 * Press the toolbar button
	 * @param button
	 * @return
	 * @throws HarnessException
	 */
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton("+ button +")");

		tracer.trace("Click "+ button);

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		// Fallthrough objects
		AbsPage page = null;
		String locator = null;

		if (button == Button.B_SAVE ) {

			locator = Locators.zSaveTask;
			page = null;

		} else if (button == Button.B_CANCEL) {

			locator = Locators.zCancelTask;
			page = new DialogWarning(
					DialogWarning.DialogWarningID.SaveTaskChangeMessage,
					this.MyApplication,
					((AppAjaxClient)this.MyApplication).zPageTasks);

		} else {
			throw new HarnessException("no logic defined for button " + button);
		}


		// Make sure a locator was set
		if ( locator == null )
			throw new HarnessException("locator was null for button "+ button);

		// Click it
		this.zClickAt(locator,"0,0");
		// this.sClickAt(locator, "0,0");
		
		SleepUtil.sleepMedium();

		// if the app is busy, wait for it to become active again
		this.zWaitForBusyOverlay();

		if ( page != null ) {

			// Make sure the page becomes active
			page.zWaitForActive();

		}

		return (page);

	}

	/**
	 * Press the toolbar pulldown and the menu option
	 * @param pulldown
	 * @param option
	 * @return
	 * @throws HarnessException
	 */
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressPulldown("+ pulldown +", "+ option +")");

		tracer.trace("Click pulldown "+ pulldown +" then "+ option);

		if ( pulldown == null )
			throw new HarnessException("Pulldown cannot be null!");

		if ( option == null )
			throw new HarnessException("Option cannot be null!");

		// Default behavior variables
		//
		String pulldownLocator = null;	// If set, this will be expanded
		String optionLocator = null;	// If set, this will be clicked
		AbsPage page = null;	// If set, this page will be returned

		// Based on the button specified, take the appropriate action(s)
		//

		if ( pulldown == Button.B_PRIORITY ) {

			logger.info(ZimbraDOM.showIDs());

			String divID = ZimbraDOM.getID(
					ZimbraDOM.APP.APP_TASKS,
					ZimbraDOM.COMPONENT_NAME.OP_VIEW_TASKEDIT,
					ZimbraDOM.COMPONENT_TYPE.WIDGET_VIEW);

			if ( option == Button.O_PRIORITY_HIGH ) {

				// I18N: https://bugzilla.zimbra.com/show_bug.cgi?id=83574
				pulldownLocator = "css=div#"+ divID +" td[id$='_priority'] div.ImgSelectPullDownArrow";
				optionLocator = "css=div[id^='EditTaskPriorityMenu_high']";
				page = this;

			} else if ( option == Button.O_PRIORITY_NORMAL ) {

				// I18N: https://bugzilla.zimbra.com/show_bug.cgi?id=83574
				pulldownLocator = "css=div#"+ divID +" td[id$='_priority'] div.ImgSelectPullDownArrow";
				optionLocator = "css=div[id^='EditTaskPriorityMenu_normal']";
				page = this;

			} else if ( option == Button.O_PRIORITY_LOW ) {

				// I18N: https://bugzilla.zimbra.com/show_bug.cgi?id=83574
				pulldownLocator = "css=div#"+ divID +" td[id$='_priority'] div.ImgSelectPullDownArrow";
				optionLocator = "css=div[id^='EditTaskPriorityMenu_low']";
				page = this;

			} else {
				throw new HarnessException("unsupported priority option "+ option);
			}

		}else if(pulldown==Button.B_OPTIONS){
			if(option==Button.O_OPTION_FORMAT_AS_HTML){

				String zTaskOptionDropDown = ZimbraDOM.getID(
						ZimbraDOM.APP.APP_TASKS,
						ZimbraDOM.COMPONENT_NAME.OP_COMPOSE_OPTIONS,
						ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON);

				pulldownLocator = "css=div#" + zTaskOptionDropDown + " td[id$='_dropdown']";
				//pulldownLocator=Locators.zTaskOptionDropDown;

				optionLocator=Locators.zTaskFormatAsHtml;
				page=this;

			}else if(option==Button.O_OPTION_FORMAT_AS_TEXT){
				//pulldownLocator=Locators.zTaskOptionDropDown;

				String zTaskOptionDropDown = ZimbraDOM.getID(
						ZimbraDOM.APP.APP_TASKS,
						ZimbraDOM.COMPONENT_NAME.OP_COMPOSE_OPTIONS,
						ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON);

				pulldownLocator = "css=div#" + zTaskOptionDropDown + " td[id$='_dropdown']";
				optionLocator=Locators.zTaskFormatAsText;

				page = new DialogWarning(
						DialogWarning.DialogWarningID.SwitchingToTextWillDiscardHtmlFormatting,
						this.MyApplication,
						((AppAjaxClient)this.MyApplication).zPageTasks);

				//Intentionally adding this code.
				SleepUtil.sleepMedium();
				this.zClickAt(pulldownLocator,"");
				SleepUtil.sleepMedium();
				this.zClickAt(optionLocator,"");
				SleepUtil.sleepMedium();
				return (page);

			}


		}else {

			throw new HarnessException("no logic defined for pulldown "+ pulldown);
		}

		// Default behavior
		if ( pulldownLocator != null ) {

			// Make sure the locator exists
			if ( !this.sIsElementPresent(pulldownLocator) ) {
				throw new HarnessException("Button "+ pulldown +" option "+ option +" pulldownLocator "+ pulldownLocator +" not present!");
			}

			SleepUtil.sleepMedium();
			this.zClickAt(pulldownLocator,"");
			SleepUtil.sleepMedium();


			this.zWaitForBusyOverlay();

			if ( optionLocator != null ) {

				// Make sure the locator exists
				if ( !this.sIsElementPresent(optionLocator) ) {
					throw new HarnessException("Button "+ pulldown +" option "+ option +" optionLocator "+ optionLocator +" not present!");
				}

				this.zClickAt(optionLocator,"");

				this.zWaitForBusyOverlay();

			}

			// If we click on pulldown/option and the page is specified, then
			// wait for the page to go active
			if ( page != null ) {
				page.zWaitForActive();
			}

		}

		// Return the specified page, or null if not set
		return (page);
	}

	/**
	 * Fill in the form field with the specified text
	 * @param field
	 * @param value
	 * @throws HarnessException
	 */
	public void zFillField(Field field, String value) throws HarnessException {
		tracer.trace("Set "+ field +" to "+ value);

		String locator = null;

		if (field == Field.Subject) {
		     locator = Locators.zTasksubjField;
		     
		} else if (field == Field.Body) {

			locator = "css=div[class='ZmTaskEditView'] div[id$='_notes'] textarea[id$='_body']";
			this.sFocus(locator);
			this.sClickAt(locator, "");
			this.zKeyboard.zTypeCharacters(value);
			return;

		} else if (field == Field.HtmlBody) {
						
			int frames = this.sGetCssCount("css=iframe");
			logger.info("Body: # of frames: " + frames);
				
			if (this.zIsVisiblePerPosition("css=textarea[class='ZmHtmlEditorTextArea']", 10, 10)) {

				locator = "css=textarea[class='ZmHtmlEditorTextArea']";
				this.sFocus(locator);
				this.sClickAt(locator, "");
				this.zWaitForBusyOverlay();
				this.sType(locator, value);

				return;
			}

			if (frames >= 1) {

				try {

					if (this.sIsElementPresent("css=iframe[id$='ZmHtmlEditor1_body_ifr']")) {
						
						sSelectFrame("css=div[class='ZmTaskEditView'] div[id$='_notes'] iframe[id$='_body_ifr']");

						locator = "css=body[id='tinymce']";
						this.sClickAt(locator, "");
						this.sFocus(locator);
						this.sType(locator, value);
						
					} else {
						throw new HarnessException("Unable to locate compose body");
					}

				} finally {
					
					this.sSelectFrame("relative=top");

				}

				this.zWaitForBusyOverlay();
				return;

			} else {
				throw new HarnessException("Compose //iframe count was " + frames);
			}


		} else if (field == Field.DueDate) {
			locator = "css=input[id$='_endDateField']";
			this.sFocus(locator);
			this.zClickAt(locator,"0,0");
			sType(locator, value);
			return;
			
		} else {
			throw new HarnessException("not implemented for field " + field);
		}

		// Make sure the button exists
		if ( !this.sIsElementPresent(locator) )
			throw new HarnessException("Field is not present field="+ field +" locator="+ locator);

		// Enter text
		if (field != Field.Subject) {
			this.sFocus(locator);
		}

		this.sClickAt(locator, "");
		sType(locator, value);

		this.zWaitForBusyOverlay();

	}


	@Override
	public void zFill(IItem item) throws HarnessException {
		
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		logger.info(myPageName() + " zIsActive()");

		//String locator = "css=td[id='zv__TKE1_subject']";css=td[id$='_subject']
		String locator = "css=td[id$='_subject']";
		if ( !this.sIsElementPresent(locator) ) {
			return (false);
		}

		logger.info(myPageName() + " zIsActive() = true");
		return (true);
	}

}
