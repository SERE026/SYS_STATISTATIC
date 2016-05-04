package com.ycm.dto;

/**
 * IP 相关信息
 * @author sere
 *
 */
public class IPAddressInfo {

	/*#：
	 * {"code":0,"data":{"country":"\u4e2d\u56fd","country_id":"CN","area":"\u534e\u5317","area_id":"100000",
	 * "region":"\u5317\u4eac\u5e02","region_id":"110000","city":"\u5317\u4eac\u5e02","city_id":"110100","county":"","county_id":"-1",
	 "isp":"\u7535\u4fe1","isp_id":"100017","ip":"124.126.206.87"}}
	 */
	/**
	 * 所处国家
	 */
	private String country;
	
	/**
	 * 国家编号
	 */
	private String country_id;
	
	/**
	 * 区域
	 */
	private String area;
	
	/**
	 * 区域编号
	 */
	private String area_id;
	
	/**
	 * 省 或者直辖市信息
	 */
	private String region;
	
	/**
	 * 省或者直辖市 编号
	 */
	private String region_id;
	
	/**
	 * 市级信息
	 */
	private String city;
	
	/**
	 * 市级信息 编号
	 */
	private String city_id;
	
	/**
	 * 乡 、县级信息
	 */
	private String county;
	
	/**
	 * 乡 、县级信息编号
	 */
	private String county_id;
	
	/**
	 * 网络运营商
	 */
	private String isp;
	
	/**
	 * 网络运营商 信息编号
	 */
	private String isp_id;
	
	/**
	 * IP
	 */
	private String ip;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry_id() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getIsp_id() {
		return isp_id;
	}

	public void setIsp_id(String isp_id) {
		this.isp_id = isp_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIPAddress(){
		return this.region+" "+ this.city;
	}
	
}
