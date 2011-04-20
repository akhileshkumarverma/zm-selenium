/**
 * 
 */
package com.zimbra.qa.selenium.projects.admin.ui;

import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;


/**
 * @author Matt Rhoades
 *
 */
public class PageManageDistributionLists extends AbsTab {

	public static class Locators {

		// ** OverviewTreePanel -> Addresses -> Aliases
		public static final String zti_DISTRIBUTION_LIST = "zti__AppAdmin__ADDRESS__DL_textCell";

		// ** "Manage Aliases" Tab Title
		public static final String ztab_MANAGE_DISTRIBUTION_LIST_ICON = "css=tr#ztab__MAIN_TAB_row div.ImgDistributionList";
		public static final String zb_NEW = "xpath=//*[@id='zb__ACLV__NEW_MENU_title']";		// New Button
		public static final String zdd_NEW_MENU="css=td#zb__ACLV__NEW_MENU_dropdown div.ImgSelectPullDownArrow";

		// NEW Menu
		// TODO: define these locators
		public static final String zmi_ALAIS = "zmi__ACLV__NEW_title";


	}

	public PageManageDistributionLists(AbsApplication application) {
		super(application);
	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsTab#isActive()
	 */
	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the Admin Console is loaded in the browser
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Admin Console application is not active!");


		boolean present = sIsElementPresent(Locators.ztab_MANAGE_DISTRIBUTION_LIST_ICON);
		if ( !present ) {
			return (false);
		}

		boolean visible = zIsVisiblePerPosition(Locators.ztab_MANAGE_DISTRIBUTION_LIST_ICON, 0, 0);
		if ( !visible ) {
			logger.debug("isActive() visible = "+ visible);
			return (false);
		}

		return (true);

	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsTab#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsTab#navigateTo()
	 */
	@Override
	public void zNavigateTo() throws HarnessException {

		if ( zIsActive() ) {
			// This page is already active.
			return;
		}

		// Click on Addresses -> Accounts
		zClick(Locators.zti_DISTRIBUTION_LIST);

		zWaitForActive();

	}

	@Override
	public AbsPage zListItem(Action action, String item)
	throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item)
	throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption ,String item)
	throws HarnessException {
		// TODO Auto-generated method stub
		return null;	
	}

	
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {

		logger.info(myPageName() + " zToolbarPressButton("+ button +")");

		tracer.trace("Press the "+ button +" button");

		if ( button == null )
			throw new HarnessException("Button cannot be null!");


		// Default behavior variables
		//
		String locator = null;			// If set, this will be clicked
		AbsPage page = null;	// If set, this page will be returned

		// Based on the button specified, take the appropriate action(s)
		//

		if ( button == Button.B_NEW ) {

			// New button
			locator = Locators.zb_NEW;

			 
			// Create the page
			page = new PageNewDistributionList(MyApplication);

			// FALL THROUGH

		} else {
			throw new HarnessException("no logic defined for button "+ button);
		}

		if ( locator == null ) {
			throw new HarnessException("locator was null for button "+ button);
		}

		// Default behavior, process the locator by clicking on it
		//
		this.zClick(locator);

		// If page was specified, make sure it is active
		if ( page != null ) {
			//page.zWaitForActive();
		}

		return (page);

	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		return null;

	}

}
