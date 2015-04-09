package com.pedidosmovil;

public class InvoiceModel extends DatabaseModel 
{
	private int folio;
	private int company_id;
	private int customer_id;
	private String date;
	private String comments;
	private float subtotal;
	private float total;
	private float discount;
	private String signature_id;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public int getFolio() {
		return this.folio;
	}
	
	public int getCompanyID() {
		return this.company_id;
	}
	
	public int getCustomerID() {
		return this.customer_id;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getComments() {
		return this.comments;
	}
	
	public float getSubtotal() {
		return this.subtotal;
	}
	
	public float getTotal() {
		return this.total;
	}
	
	public float getDiscount() {
		return this.discount;
	}
	
	public String getSignatureID() {
		return this.signature_id;
	}
	
	public void setFolio(int folio) {
		this.folio = folio;
	}
	
	public void setCompanyID(int company_id) {
		this.company_id = company_id;
	}
	
	public void setCustomerID(int customer_id) {
		this.customer_id = customer_id;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	
	public void setSignatureID(String signature_id) {
		this.signature_id = signature_id;
	}
}
