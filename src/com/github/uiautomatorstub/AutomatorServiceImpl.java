package com.github.uiautomatorstub;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class AutomatorServiceImpl implements AutomatorService {

	public AutomatorServiceImpl() {
	}

	@Override
	public String ping() {
		return "pong";
	}

	@Override
	public boolean wakeUp() {
		try {
			UiDevice.getInstance().wakeUp();
		} catch (RemoteException e) {
			Log.d(e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public String packageName() {
		return UiDevice.getInstance().getCurrentPackageName();
	}

	@Override
	public boolean pressKey(String key) {
		boolean result = false;
		key = key.toLowerCase();
		if ("home".equals(key))
			result = UiDevice.getInstance().pressHome();
		else if ("back".equals(key))
			result = UiDevice.getInstance().pressBack();
		else if ("left".equals(key))
			result = UiDevice.getInstance().pressDPadLeft();
		else if ("right".equals(key))
			result = UiDevice.getInstance().pressDPadRight();
		else if ("up".equals(key))
			result = UiDevice.getInstance().pressDPadUp();
		else if ("down".equals(key))
			result = UiDevice.getInstance().pressDPadDown();
		else if ("center".equals(key))
			result = UiDevice.getInstance().pressDPadCenter();
		else if ("menu".equals(key))
			result = UiDevice.getInstance().pressMenu();
		else if ("search".equals(key))
			result = UiDevice.getInstance().pressSearch();
		else if ("enter".equals(key))
			result = UiDevice.getInstance().pressEnter();
		else if ("delete".equals(key) || "del".equals(key))
			result = UiDevice.getInstance().pressDelete();
		else if ("recent".equals(key))
			try {
				result = UiDevice.getInstance().pressRecentApps();
			} catch (RemoteException e) {
				Log.d(e.getMessage());
				result = false;
			}
		else
			result = false;

		return result;
	}

	@Override
	public boolean swipe(Selector selector, String dir) {
		dir = dir.toLowerCase();
		UiObject item = new UiObject(selector.toUiSelector());
		boolean result = false;
		try {
			if ("u".equals(dir) || "up".equals(dir))
				result = item.swipeUp(10);
			else if ("d".equals(dir) || "down".equals(dir))
				result = item.swipeDown(10);
			else if ("l".equals(dir) || "left".equals(dir))
				result = item.swipeLeft(10);
			else if ("r".equals(dir) || "right".equals(dir))
				result = item.swipeRight(10);
		} catch (UiObjectNotFoundException e) {
			result = false;
		}
		return result;
	} 

	@Override
	public boolean click(Selector selector) {
		UiObject item = new UiObject(selector.toUiSelector());
		boolean result;
		try {
			result = item.click();
		} catch (UiObjectNotFoundException e) {
			result = false;
		}
		return result;
	}

	@Override
	public boolean click(int x, int y) {
		return UiDevice.getInstance().click(x, y);
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
