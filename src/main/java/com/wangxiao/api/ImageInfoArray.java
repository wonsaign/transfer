package com.wangxiao.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = JsonInclude.Include.NON_NULL) // null属性,不显示
public class ImageInfoArray {
	
	@JsonProperty(value="Type")
	private Number type;// 类型
	@JsonProperty(value="Width")
	private Number width;// 宽
	@JsonProperty(value="Height")
	private Number Height;// 高
	@JsonProperty(value="URL")
	private String URL;// 
	@JsonProperty(value="SIZE")
	private Number size;
	
	public Number getType() {
		return type;
	}
	public void setType(Number type) {
		this.type = type;
	}
	public Number getWidth() {
		return width;
	}
	public void setWidth(Number width) {
		this.width = width;
	}
	public Number getHeight() {
		return Height;
	}
	public void setHeight(Number height) {
		Height = height;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public Number getSize() {
		return size;
	}
	public void setSize(Number size) {
		this.size = size;
	}
}
