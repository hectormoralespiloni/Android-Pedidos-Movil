package com.pedidosmovil;

public class ProductModel extends DatabaseModel
{
	private String code;
	private String name;
	private float price; 
	private float tax;
	private int company_id;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public ProductModel() {
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
	
	public String getCode() { 
		return this.code;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public float getTax() {
		return this.tax;
	}
	
	public int getCompanyID() {
		return this.company_id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}
	
	public void setCompanyID(int company_id) {
		this.company_id = company_id;
	}
}

	