package com.github.uiautomatorstub;

import com.android.uiautomator.core.UiSelector;

public class Selector {
	private String _text;
	private String _className;
	private String _description;
	public String getText() {
		return _text;
	}
	public void setText(String text) {
		this._text = text;
	}
	public String getClassName() {
		return _className;
	}
	public void setClassName(String className) {
		this._className = className;
	}
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		this._description = description;
	}

	public UiSelector toUiSelector() {
		UiSelector s = new UiSelector();
		if (getText() != null && getText() != "")
			s = s.text(getText());
		if (getClassName() != null && getClassName() != "")
			s = s.className(getClassName());
		if (getDescription() != null && getDescription() != "")
			s = s.description(getDescription());

		return s;
	} 
}
