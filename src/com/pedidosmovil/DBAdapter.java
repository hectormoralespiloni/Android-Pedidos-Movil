package com.pedidosmovil;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter 
{
	private Context context;
	private SQLiteDatabase database;
	private DBOpenHelper dbHelper;
	
	public DBAdapter(Context context) {
		this.context = context;
	}
	
	public DBAdapter open() throws SQLException 
	{
		dbHelper = new DBOpenHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public Cursor getCursor(String tableName, String[] fields, String whereFieldName, CharSequence constraint)
	{
		String query;
		String selection = "*";
		if(fields != null) 
		{
			int i=0;
			selection = "";
			for(i=0; i<fields.length-1; i++) {
				selection += fields[i];
				selection += ",";
			}
			selection += fields[i];
		}
		
		if(constraint != null) {
			query = "SELECT " + selection + " FROM " + tableName + " WHERE " + whereFieldName + " LIKE '%" + constraint.toString() + "%'";
		}
		else {
			query = "SELECT " + selection + " FROM " + tableName;
		}		
		Cursor cursor = database.rawQuery(query,  null);
		return cursor;
	}
	
	public void deleteTable(String tableName)
	{
		database.delete(tableName, null, null);
	}
	
	//-------------------------------------------------------------------------
	// CUSTOMER TABLE
	//-------------------------------------------------------------------------
	public int insertRecord(CustomerModel customer) 
	{
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.CUSTOMER_COL_NAME, customer.getName());
		values.put(DBOpenHelper.CUSTOMER_COL_ADDRESS, customer.getAddress());
		values.put(DBOpenHelper.CUSTOMER_COL_STATE, customer.getState());
		values.put(DBOpenHelper.CUSTOMER_COL_CITY, customer.getCity());
		values.put(DBOpenHelper.CUSTOMER_COL_ZIP, customer.getZIP());
		values.put(DBOpenHelper.CUSTOMER_COL_RFC, customer.getRFC());
		values.put(DBOpenHelper.CUSTOMER_COL_PHONE, customer.getPhone());
		values.put(DBOpenHelper.CUSTOMER_COL_EMAIL, customer.getEmail());
		
		return (int)database.insert(DBOpenHelper.TABLE_CUSTOMER, null, values);
	}
	
	public boolean updateRecord(CustomerModel customer)
	{
		String[] id = { String.valueOf(customer.getID()) };		
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.CUSTOMER_COL_NAME, customer.getName());
		values.put(DBOpenHelper.CUSTOMER_COL_ADDRESS, customer.getAddress());
		values.put(DBOpenHelper.CUSTOMER_COL_STATE, customer.getState());
		values.put(DBOpenHelper.CUSTOMER_COL_CITY, customer.getCity());
		values.put(DBOpenHelper.CUSTOMER_COL_ZIP, customer.getZIP());
		values.put(DBOpenHelper.CUSTOMER_COL_RFC, customer.getRFC());
		values.put(DBOpenHelper.CUSTOMER_COL_PHONE, customer.getPhone());
		values.put(DBOpenHelper.CUSTOMER_COL_EMAIL, customer.getEmail());
		
		return database.update(DBOpenHelper.TABLE_CUSTOMER, values, DBOpenHelper.CUSTOMER_KEY_ID + "=?", id) > 0;
	}

	public boolean deleteRecord(CustomerModel customer) 
	{
		String[] id = { String.valueOf(customer.getID()) };
		
		return database.delete(DBOpenHelper.TABLE_CUSTOMER, DBOpenHelper.CUSTOMER_KEY_ID + "=?", id) > 0;
	}
	
	public ArrayList<CustomerModel> fetchAllCustomers() 
	{
		ArrayList<CustomerModel> records = new ArrayList<CustomerModel>();
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_CUSTOMER;
		
		Cursor cursor = database.rawQuery(query,  null);
		if(cursor.moveToFirst())
		{
			do {			
				CustomerModel cust = new CustomerModel();
				cust.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_KEY_ID))));
				cust.setName(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_NAME))));
				cust.setAddress(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_ADDRESS))));
				cust.setState(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_STATE))));
				cust.setCity(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_CITY))));
				cust.setZIP(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_ZIP))));
				cust.setRFC(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_RFC))));
				cust.setPhone(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_PHONE))));
				cust.setEmail(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_EMAIL))));
				records.add(cust);
			} while(cursor.moveToNext());
		}
		
		cursor.close();
		return records;
	}	

	public CustomerModel fetchCustomer(int id) 
	{
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_CUSTOMER + " WHERE " + DBOpenHelper.CUSTOMER_KEY_ID + "=" + id;		
		Cursor cursor = database.rawQuery(query,  null);
		
		if (cursor != null) 
		{
			cursor.moveToFirst();
			
			CustomerModel cust = new CustomerModel();
			cust.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_KEY_ID))));
			cust.setName(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_NAME))));
			cust.setAddress(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_ADDRESS))));
			cust.setState(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_STATE))));
			cust.setCity(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_CITY))));
			cust.setZIP(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_ZIP))));
			cust.setRFC(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_RFC))));
			cust.setPhone(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_PHONE))));
			cust.setEmail(cursor.getString((cursor.getColumnIndex(DBOpenHelper.CUSTOMER_COL_EMAIL))));		
			
			return cust;
		}
		
		return null;
	}

	//-------------------------------------------------------------------------
	// COMPANY TABLE
	//-------------------------------------------------------------------------
	public int insertRecord(CompanyModel company) 
	{
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.COMPANY_COL_NAME, company.getName());
		values.put(DBOpenHelper.COMPANY_COL_ADDRESS, company.getAddress());
		values.put(DBOpenHelper.COMPANY_COL_STATE, company.getState());
		values.put(DBOpenHelper.COMPANY_COL_CITY, company.getCity());
		values.put(DBOpenHelper.COMPANY_COL_ZIP, company.getZIP());
		values.put(DBOpenHelper.COMPANY_COL_RFC, company.getRFC());
		values.put(DBOpenHelper.COMPANY_COL_EMAIL, company.getEmail());
		values.put(DBOpenHelper.COMPANY_COL_PHONE, company.getPhone());
		
		return (int)database.insert(DBOpenHelper.TABLE_COMPANY, null, values);
	}
	
	public boolean updateRecord(CompanyModel company)
	{
		String[] id = { String.valueOf(company.getID()) };		
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.COMPANY_COL_NAME, company.getName());
		values.put(DBOpenHelper.COMPANY_COL_ADDRESS, company.getAddress());
		values.put(DBOpenHelper.COMPANY_COL_STATE, company.getState());
		values.put(DBOpenHelper.COMPANY_COL_CITY, company.getCity());
		values.put(DBOpenHelper.COMPANY_COL_ZIP, company.getZIP());
		values.put(DBOpenHelper.COMPANY_COL_RFC, company.getRFC());
		values.put(DBOpenHelper.COMPANY_COL_EMAIL, company.getEmail());
		values.put(DBOpenHelper.COMPANY_COL_PHONE, company.getPhone());
		
		return database.update(DBOpenHelper.TABLE_COMPANY, values, DBOpenHelper.COMPANY_KEY_ID + "=?", id) > 0;
	}

	public boolean deleteRecord(CompanyModel company) 
	{
		String[] id = { String.valueOf(company.getID()) };
		
		return database.delete(DBOpenHelper.TABLE_COMPANY, DBOpenHelper.COMPANY_KEY_ID + "=?", id) > 0;
	}
	
	public ArrayList<CompanyModel> fetchAllCompanies() 
	{
		ArrayList<CompanyModel> records = new ArrayList<CompanyModel>();
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_COMPANY;
		
		Cursor cursor = database.rawQuery(query,  null);
		if(cursor.moveToFirst())
		{
			do {			
				CompanyModel model = new CompanyModel();
				model.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.COMPANY_KEY_ID))));
				model.setName(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_NAME))));
				model.setAddress(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_ADDRESS))));
				model.setState(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_STATE))));
				model.setCity(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_CITY))));
				model.setZIP(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_ZIP))));
				model.setRFC(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_RFC))));
				model.setEmail(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_EMAIL))));
				model.setPhone(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_PHONE))));
				records.add(model);
			} while(cursor.moveToNext());
		}
		
		cursor.close();
		return records;
	}	

	public CompanyModel fetchCompany(int id) 
	{
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_COMPANY + " WHERE " + DBOpenHelper.COMPANY_KEY_ID + "=" + id;
		Cursor cursor = database.rawQuery(query,  null);
		
		if (cursor != null) 
		{
			cursor.moveToFirst();
			
			CompanyModel model = new CompanyModel();
			model.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.COMPANY_KEY_ID))));
			model.setName(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_NAME))));
			model.setAddress(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_ADDRESS))));
			model.setState(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_STATE))));
			model.setCity(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_CITY))));
			model.setZIP(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_ZIP))));
			model.setRFC(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_RFC))));
			model.setEmail(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_EMAIL))));
			model.setPhone(cursor.getString((cursor.getColumnIndex(DBOpenHelper.COMPANY_COL_PHONE))));			
			
			return model;
		}
		
		return null;
	}

	//-------------------------------------------------------------------------
	// PRODUCT TABLE
	//-------------------------------------------------------------------------
	public int insertRecord(ProductModel product) 
	{
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.PRODUCT_COL_NAME, product.getName());
		values.put(DBOpenHelper.PRODUCT_COL_CODE, product.getCode());
		values.put(DBOpenHelper.PRODUCT_COL_PRICE, product.getPrice());
		values.put(DBOpenHelper.PRODUCT_COL_TAX, product.getTax());
		values.put(DBOpenHelper.PRODUCT_COL_COMPANY_ID, product.getCompanyID());
		
		return (int)database.insert(DBOpenHelper.TABLE_PRODUCT, null, values);
	}
	
	public boolean updateRecord(ProductModel product)
	{
		String[] id = { String.valueOf(product.getID()) };		
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.PRODUCT_COL_NAME, product.getName());
		values.put(DBOpenHelper.PRODUCT_COL_CODE, product.getCode());
		values.put(DBOpenHelper.PRODUCT_COL_PRICE, product.getPrice());
		values.put(DBOpenHelper.PRODUCT_COL_TAX, product.getTax());
		values.put(DBOpenHelper.PRODUCT_COL_COMPANY_ID, product.getCompanyID());
		
		return database.update(DBOpenHelper.TABLE_PRODUCT, values, DBOpenHelper.PRODUCT_KEY_ID + "=?", id) > 0;
	}

	public boolean deleteRecord(ProductModel product) 
	{
		String[] id = { String.valueOf(product.getID()) };
		
		return database.delete(DBOpenHelper.TABLE_PRODUCT, DBOpenHelper.PRODUCT_KEY_ID + "=?", id) > 0;
	}
	
	public ArrayList<ProductModel> fetchAllProducts(int companyID) 
	{
		ArrayList<ProductModel> records = new ArrayList<ProductModel>();
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_PRODUCT + " WHERE " + 
				DBOpenHelper.PRODUCT_COL_COMPANY_ID + "=" + companyID;
		
		Cursor cursor = database.rawQuery(query,  null);
		if(cursor.moveToFirst())
		{
			do {			
				ProductModel model = new ProductModel();
				model.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.PRODUCT_KEY_ID))));
				model.setName(cursor.getString((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_NAME))));
				model.setCode(cursor.getString((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_CODE))));
				model.setPrice(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_PRICE))));
				model.setTax(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_TAX))));
				model.setCompanyID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_COMPANY_ID))));
				records.add(model);
			} while(cursor.moveToNext());
		}
		
		cursor.close();
		return records;
	}	

	public ProductModel fetchProduct(int id) 
	{
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_PRODUCT + " WHERE " + DBOpenHelper.PRODUCT_KEY_ID + "=" + id;
		Cursor cursor = database.rawQuery(query,  null);
		
		if (cursor != null) 
		{
			cursor.moveToFirst();
			
			ProductModel model = new ProductModel();
			model.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.PRODUCT_KEY_ID))));
			model.setName(cursor.getString((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_NAME))));
			model.setCode(cursor.getString((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_CODE))));
			model.setPrice(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_PRICE))));
			model.setTax(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_TAX))));
			model.setCompanyID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.PRODUCT_COL_COMPANY_ID))));			
			
			return model;
		}
		
		return null;
	}

	//-------------------------------------------------------------------------
	// INVOICE TABLE
	//-------------------------------------------------------------------------
	public int insertRecord(InvoiceModel invoice) 
	{
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.INVOICE_COL_COMMENTS, invoice.getComments());
		values.put(DBOpenHelper.INVOICE_COL_COMPANY_ID, invoice.getCompanyID());
		values.put(DBOpenHelper.INVOICE_COL_CUSTOMER_ID, invoice.getCustomerID());
		values.put(DBOpenHelper.INVOICE_COL_DATE, invoice.getDate());
		values.put(DBOpenHelper.INVOICE_COL_DISCOUNT,  invoice.getDiscount());
		values.put(DBOpenHelper.INVOICE_COL_FOLIO, invoice.getFolio());
		values.put(DBOpenHelper.INVOICE_COL_SUBTOTAL,  invoice.getSubtotal());
		values.put(DBOpenHelper.INVOICE_COL_TOTAL,  invoice.getTotal());
		values.put(DBOpenHelper.INVOICE_COL_SIGNATURE_ID, invoice.getSignatureID());
		
		return (int)database.insert(DBOpenHelper.TABLE_INVOICE, null, values);
	}
	
	public boolean updateRecord(InvoiceModel invoice)
	{
		String[] id = { String.valueOf(invoice.getID()) };		
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.INVOICE_COL_COMMENTS, invoice.getComments());
		values.put(DBOpenHelper.INVOICE_COL_COMPANY_ID, invoice.getCompanyID());
		values.put(DBOpenHelper.INVOICE_COL_CUSTOMER_ID, invoice.getCustomerID());
		values.put(DBOpenHelper.INVOICE_COL_DATE, invoice.getDate());
		values.put(DBOpenHelper.INVOICE_COL_DISCOUNT,  invoice.getDiscount());
		values.put(DBOpenHelper.INVOICE_COL_FOLIO, invoice.getFolio());
		values.put(DBOpenHelper.INVOICE_COL_SUBTOTAL,  invoice.getSubtotal());
		values.put(DBOpenHelper.INVOICE_COL_TOTAL,  invoice.getTotal());
		values.put(DBOpenHelper.INVOICE_COL_SIGNATURE_ID, invoice.getSignatureID());
		
		return database.update(DBOpenHelper.TABLE_INVOICE, values, DBOpenHelper.INVOICE_KEY_ID + "=?", id) > 0;
	}

	public boolean deleteRecord(InvoiceModel invoice) 
	{
		String[] id = { String.valueOf(invoice.getID()) };
		
		return database.delete(DBOpenHelper.TABLE_INVOICE, DBOpenHelper.INVOICE_KEY_ID + "=?", id) > 0;
	}
	
	public ArrayList<InvoiceModel> fetchAllInvoices() 
	{
		ArrayList<InvoiceModel> records = new ArrayList<InvoiceModel>();
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_INVOICE;
		
		Cursor cursor = database.rawQuery(query,  null);
		if(cursor.moveToFirst())
		{
			do {			
				InvoiceModel model = new InvoiceModel();
				model.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_KEY_ID))));
				model.setComments(cursor.getString((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_COMMENTS))));
				model.setCompanyID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_COMPANY_ID))));
				model.setCustomerID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_CUSTOMER_ID))));
				model.setDate(cursor.getString((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_DATE))));
				model.setDiscount(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_DISCOUNT))));
				model.setFolio(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_FOLIO))));
				model.setSubtotal(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_SUBTOTAL))));
				model.setTotal(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_TOTAL))));
				model.setSignatureID(cursor.getString((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_SIGNATURE_ID))));
				records.add(model);
			} while(cursor.moveToNext());
		}
		
		cursor.close();
		return records;
	}	

	public InvoiceModel fetchInvoice(int id) 
	{
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_INVOICE + " WHERE " + DBOpenHelper.INVOICE_KEY_ID + "=" + id;
		Cursor cursor = database.rawQuery(query,  null);
		
		if (cursor != null) 
		{
			cursor.moveToFirst();
			
			InvoiceModel model = new InvoiceModel();
			model.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_KEY_ID))));
			model.setComments(cursor.getString((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_COMMENTS))));
			model.setCompanyID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_COMPANY_ID))));
			model.setCustomerID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_CUSTOMER_ID))));
			model.setDate(cursor.getString((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_DATE))));
			model.setDiscount(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_DISCOUNT))));
			model.setFolio(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_FOLIO))));
			model.setSubtotal(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_SUBTOTAL))));
			model.setTotal(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_TOTAL))));			
			model.setSignatureID(cursor.getString((cursor.getColumnIndex(DBOpenHelper.INVOICE_COL_SIGNATURE_ID))));
			
			return model;
		}
		
		return null;
	}

	//-------------------------------------------------------------------------
	// INVOICE_DETAIL TABLE
	//-------------------------------------------------------------------------
	public int insertRecord(InvoiceDetailModel invoice) 
	{
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.INVOICE_DETAIL_COL_INVOICE_ID, invoice.getInvoiceID());
		values.put(DBOpenHelper.INVOICE_DETAIL_COL_PRODUCT_ID, invoice.getProductID());
		values.put(DBOpenHelper.INVOICE_DETAIL_COL_QUANTITY, invoice.getQuantity());
		values.put(DBOpenHelper.INVOICE_DETAIL_COL_TOTAL, invoice.getTotal());
		
		return (int)database.insert(DBOpenHelper.TABLE_INVOICE_DETAIL, null, values);
	}
	
	public boolean updateRecord(InvoiceDetailModel invoice)
	{
		String[] id = { String.valueOf(invoice.getID()) };		
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.INVOICE_DETAIL_COL_INVOICE_ID, invoice.getInvoiceID());
		values.put(DBOpenHelper.INVOICE_DETAIL_COL_PRODUCT_ID, invoice.getProductID());
		values.put(DBOpenHelper.INVOICE_DETAIL_COL_QUANTITY, invoice.getQuantity());
		values.put(DBOpenHelper.INVOICE_DETAIL_COL_TOTAL, invoice.getTotal());
		
		return database.update(DBOpenHelper.TABLE_INVOICE_DETAIL, values, DBOpenHelper.INVOICE_DETAIL_KEY_ID + "=?", id) > 0;
	}

	public boolean deleteRecord(InvoiceDetailModel invoice) 
	{
		String[] id = { String.valueOf(invoice.getID()) };
		
		return database.delete(DBOpenHelper.TABLE_INVOICE_DETAIL, DBOpenHelper.INVOICE_DETAIL_KEY_ID + "=?", id) > 0;
	}
	
	public ArrayList<InvoiceDetailModel> fetchAllInvoiceDetails(int invoiceID) 
	{
		ArrayList<InvoiceDetailModel> records = new ArrayList<InvoiceDetailModel>();
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_INVOICE_DETAIL + " WHERE " + DBOpenHelper.INVOICE_DETAIL_COL_INVOICE_ID + "=" + invoiceID;
		
		Cursor cursor = database.rawQuery(query,  null);
		if(cursor.moveToFirst())
		{
			do {			
				InvoiceDetailModel model = new InvoiceDetailModel();
				model.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_KEY_ID))));
				model.setInvoiceID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_COL_INVOICE_ID))));
				model.setProductID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_COL_PRODUCT_ID))));
				model.setQuantity(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_COL_QUANTITY))));
				model.setTotal(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_COL_TOTAL))));
				records.add(model);
			} while(cursor.moveToNext());
		}
		
		cursor.close();
		return records;
	}	

	public InvoiceDetailModel fetchInvoiceDetail(int id) 
	{
		String query = "SELECT * FROM " + DBOpenHelper.TABLE_INVOICE_DETAIL + " WHERE " + DBOpenHelper.INVOICE_DETAIL_KEY_ID + "=" + id;
		Cursor cursor = database.rawQuery(query,  null);
		
		if (cursor != null) 
		{
			cursor.moveToFirst();
			
			InvoiceDetailModel model = new InvoiceDetailModel();
			model.setID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_KEY_ID))));
			model.setInvoiceID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_COL_INVOICE_ID))));
			model.setProductID(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_COL_PRODUCT_ID))));
			model.setQuantity(cursor.getInt((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_COL_QUANTITY))));
			model.setTotal(cursor.getFloat((cursor.getColumnIndex(DBOpenHelper.INVOICE_DETAIL_COL_TOTAL))));			
			
			return model;
		}
		
		return null;
	}
}
