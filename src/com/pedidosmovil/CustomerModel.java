package com.pedidosmovil;

public class CustomerModel extends DatabaseModel
{
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String rfc;
	private String phone;
	private String email;
	
	public CustomerModel() {
		//Empty constructor
		super();
	}	
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public String getState() {
		return this.state;
	}

	public String getCity() {
		return this.city;
	}

	public String getZIP() {
		return this.zip;
	}

	public String getRFC() {
		return this.rfc;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getEmail() {
		return this.email;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setZIP(String zip) {
		this.zip = zip;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setRFC(String rfc) {
		this.rfc = rfc;
	}

}
