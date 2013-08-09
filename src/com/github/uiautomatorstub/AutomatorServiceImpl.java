package com.github.uiautomatorstub;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class AutomatorServiceImpl implements AutomatorService {

	private UiAutomatorTestCase tc = null;

	public AutomatorServiceImpl(UiAutomatorTestCase tc) {
		this.tc = tc;
	}

	@Override
	public String ping() {
		return "pong";
	}

	@Override
	public boolean pressKey(String key) {
		boolean result = false;
		key = key.toLowerCase();
		if ("home".equals(key))
			result = tc.getUiDevice().pressHome();
		else if ("back".equals(key))
			result = tc.getUiDevice().pressBack();
		else if ("left".equals(key))
			result = tc.getUiDevice().pressDPadLeft();
		else if ("right".equals(key))
			result = tc.getUiDevice().pressDPadRight();
		else if ("up".equals(key))
			result = tc.getUiDevice().pressDPadUp();
		else if ("down".equals(key))
			result = tc.getUiDevice().pressDPadDown();
		else if ("center".equals(key))
			result = tc.getUiDevice().pressDPadCenter();
		else if ("menu".equals(key))
			result = tc.getUiDevice().pressMenu();
		else if ("search".equals(key))
			result = tc.getUiDevice().pressSearch();
		else if ("enter".equals(key))
			result = tc.getUiDevice().pressEnter();
		else if ("delete".equals(key) || "del".equals(key))
			result = tc.getUiDevice().pressDelete();
		else if ("recent".equals(key))
			try {
				result = tc.getUiDevice().pressRecentApps();
			} catch (RemoteException e) {
				Log.d(e.getMessage());
				result = false;
			}
		else
			result = false;
		
		return result;
	}

	@Override
	public boolean click(Selector selector) {
		Log.d("text=" + selector.getText() + ", className="
				+ selector.getClassName() + ", description="
				+ selector.getDescription());
		UiObject item = new UiObject(getUiSelector(selector));
		try {
			item.click();
		} catch (UiObjectNotFoundException e) {
			Log.d(e.getMessage());
			return false;
		}
		return true;
	}

	private UiSelector getUiSelector(Selector selector) {
		UiSelector s = new UiSelector();
		if (selector.getText() != null && selector.getText() != "")
			s = s.text(selector.getText());
		if (selector.getClassName() != null && selector.getClassName() != "")
			s = s.className(selector.getClassName());
		if (selector.getDescription() != null
				&& selector.getDescription() != "")
			s = s.description(selector.getDescription());

		return s;
	}

}
