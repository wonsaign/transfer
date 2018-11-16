package com.wangxiao.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wangxiao.entity.RecordContent;
@JsonInclude(value = JsonInclude.Include.NON_NULL) // null属性,不显示
public class MsgContent {
	
	public static final String PACKAGE_NAME = "com.wangxiao.api.polymorphic.";
	
	//@JSONField(name = "Text") // JSON.toJSONString的时候生效
	//@JsonProperty(value = "ErrorCode")
	@JsonProperty(value="Text")
	private String text;// 内容
	@JsonProperty(value="Desc")
	private String desc;// 描述
	@JsonProperty(value="Latitude")
	private Number latitude;// 纬度
	@JsonProperty(value="Longitude")
	private Number longitude;// 经度
	@JsonProperty(value="Index")
	private Number index;//表情
	@JsonProperty(value="Data")
	private String data;// 数据
	@JsonProperty(value="Ext")
	private String ext;// 扩展字段
	@JsonProperty(value="Sound")
	private String sound;// 自定义铃声
	@JsonProperty(value="UUID")
	private String UUID;
	@JsonProperty(value="Size")
	private Number size;// 大小
	@JsonProperty(value="Second")
	private Number second;// 时长
	@JsonProperty(value="ImageFormat")
	private Number imageFormat;// 图片格式
	@JsonProperty(value="ImageInfoArray")
	private List<ImageInfoArray> imageInfoArray;// 原图下载地址
	@JsonProperty(value="FileSize")
	private Number fileSize;// 文件大小
	@JsonProperty(value="FileName")
	private String fileName;// 文件名称
	
	public void setFields(MsgContent mc,RecordContent rc) {}
	public void doFilterWords(MsgContent mc) {}
	
	public MsgContent getPolyContent(String msgType) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String clazzName = PACKAGE_NAME + msgType; 
		Class<?> clazz = Class.forName(clazzName);
		// this is init , must be null
		return (MsgContent)clazz.newInstance();
	}
	
	//@JSONField(name="Text")
	public String getText() {
		return text;
	}
	//@JSONField(name="Text")
	public void setText(String text) {
		this.text = text;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Number getLatitude() {
		return latitude;
	}
	public void setLatitude(Number latitude) {
		this.latitude = latitude;
	}
	public Number getLongitude() {
		return longitude;
	}
	public void setLongitude(Number longitude) {
		this.longitude = longitude;
	}
	public Number getIndex() {
		return index;
	}
	public void setIndex(Number index) {
		this.index = index;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public Number getSize() {
		return size;
	}
	public void setSize(Number size) {
		this.size = size;
	}
	public Number getSecond() {
		return second;
	}
	public void setSecond(Number second) {
		this.second = second;
	}
	public Number getImageFormat() {
		return imageFormat;
	}
	public void setImageFormat(Number imageFormat) {
		this.imageFormat = imageFormat;
	}
	
	public List<ImageInfoArray> getImageInfoArray() {
		return imageInfoArray;
	}

	public void setImageInfoArray(List<ImageInfoArray> imageInfoArray) {
		this.imageInfoArray = imageInfoArray;
	}

	public Number getFileSize() {
		return fileSize;
	}
	public void setFileSize(Number fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
