package com.github.uiautomatorstub;

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
}
