package com.pedidosmovil;

public class InvoiceDetailModel extends DatabaseModel
{
	private int invoice_id;
	private int product_id;
	private int quantity;
	private float total;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public int getInvoiceID() {
		return this.invoice_id;
	}
	
	public int getProductID() {
		return this.product_id;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public float getTotal() {
		return this.total;
	}
	
	public void setInvoiceID(int invoice_id) {
		this.invoice_id = invoice_id;
	}

	public void setProductID(int product_id) {
		this.product_id = product_id;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTotal(float total) {
		this.total = total;
	}
}
