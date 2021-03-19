package com.business.entity;

/**
 * 首次登录对象
 */
public class LoginInfo {
	private String encryptedData;
	private String iv;
    private String code;
    private String avatarUrl;
    private int gender;
    private String nickName;
    private String language;
    private String city;
    private String province;
    private String country;
	private int promoterId;
	private Long companyId;	//商户id

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public int getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(int promoterId) {
		this.promoterId = promoterId;
	}
}
