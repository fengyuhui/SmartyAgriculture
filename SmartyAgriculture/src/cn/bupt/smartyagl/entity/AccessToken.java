package cn.bupt.smartyagl.entity;

import java.util.List;

public class AccessToken {
	private String access_token	;
	private String expires_in ;
	private int errcode;
	private String errmsg;
	private String openid;
	private int sex;
	private String nickname;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private List<String> privilege;
	private String language;
	private String unionid;
	public String getUnionid() {
        return unionid;
    }
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
    public String getRefresh_token() {
        return refresh_token;
    }
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    private String refresh_token;
	private String scope;
	
	
	
	public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getHeadimgurl() {
        return headimgurl;
    }
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    public  List<String> getPrivilege() {
        return privilege;
    }
    public void setPrivilege( List<String> privilege) {
        this.privilege = privilege;
    }
    public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
}
