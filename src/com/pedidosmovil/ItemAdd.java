package com.pedidosmovil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemAdd extends Activity 
{
	private EditText name;
	private EditText address;
	private EditText city;
	private EditText state;
	private EditText ZIP;
	private EditText RFC;
	private EditText phone;
	private EditText email;
	private EditText code;
	private EditText price;
	private EditText tax;
	private int companyID;
	private int DataType;
	private int itemID;
	private DBAdapter db;
	
	public static void main(String[] args) {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		
		Intent myIntent = getIntent();
		DataType = myIntent.getIntExtra("DataType", 0);
		companyID = myIntent.getIntExtra("companyID", 0);
		itemID = myIntent.getIntExtra("id", 0);
		
		switch(DataType)
		{
			case R.integer.company_type:
				setContentView(R.layout.company_add);				
				name = (EditText)findViewById(R.id.editName);
				address = (EditText)findViewById(R.id.editAddress);
				city = (EditText)findViewById(R.id.editCity);
				state = (EditText)findViewById(R.id.editState);
				ZIP = (EditText)findViewById(R.id.editZIP);
				RFC = (EditText)findViewById(R.id.editRFC);
				phone = (EditText)findViewById(R.id.editPhone);
				email = (EditText)findViewById(R.id.editEmail);
				if(itemID > 0)
				{
					db = new DBAdapter(this);
					db.open();
					CompanyModel company = db.fetchCompany(itemID);
					name.setText(company.getName());
					address.setText(company.getAddress());
					city.setText(company.getCity());
					state.setText(company.getState());
					ZIP.setText(company.getZIP());
					RFC.setText(company.getRFC());
					phone.setText(company.getPhone());
					email.setText(company.getEmail());
					db.close();
				}
				break;
			case R.integer.customer_type:
				setContentView(R.layout.customer_add);
				name = (EditText)findViewById(R.id.editName);
				address = (EditText)findViewById(R.id.editAddress);
				city = (EditText)findViewById(R.id.editCity);
				state = (EditText)findViewById(R.id.editState);
				ZIP = (EditText)findViewById(R.id.editZIP);
				RFC = (EditText)findViewById(R.id.editRFC);
				phone = (EditText)findViewById(R.id.editPhone);
				email = (EditText)findViewById(R.id.editEmail);
				if(itemID > 0)
				{
					db = new DBAdapter(this);
					db.open();
					CustomerModel customer = db.fetchCustomer(itemID);
					name.setText(customer.getName());
					address.setText(customer.getAddress());
					city.setText(customer.getCity());
					state.setText(customer.getState());
					ZIP.setText(customer.getZIP());
					RFC.setText(customer.getRFC());
					phone.setText(customer.getPhone());
					email.setText(customer.getEmail());
					db.close();
				}				
				break;
			case R.integer.product_type:
				setContentView(R.layout.product_add);
				name = (EditText)findViewById(R.id.editName);
				code = (EditText)findViewById(R.id.editCode);
				price = (EditText)findViewById(R.id.editPrice);
				tax = (EditText)findViewById(R.id.editTax);				
				if(itemID > 0)
				{
					db = new DBAdapter(this);
					db.open();
					ProductModel product = db.fetchProduct(itemID);
					name.setText(product.getName());
					code.setText(product.getCode());
					price.setText(Float.valueOf(product.getPrice()).toString());
					tax.setText(Float.valueOf(product.getTax()).toString());
					db.close();
				}
				break;
			case R.integer.invoice_type:
				setContentView(R.layout.invoice_add);
				break;
			default:
				break;
		}
		
		Button bSave = (Button)findViewById(R.id.ButtonSave);
		//Invoice does not have save button...
		if(bSave!=null)
		{
			bSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					saveData();
					Toast.makeText(getApplicationContext(), R.string.item_saved, Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
	}
	
	private void saveData()
	{
		db = new DBAdapter(this);
		db.open();
		
		switch(DataType) {
		case R.integer.customer_type:
			//create new company model object
			CustomerModel cust = new CustomerModel();
			cust.setName(name.getText().toString());
			cust.setAddress(address.getText().toString());
			cust.setCity(city.getText().toString());
			cust.setState(state.getText().toString());
			cust.setZIP(ZIP.getText().toString());
			cust.setRFC(RFC.getText().toString());
			cust.setPhone(phone.getText().toString());
			cust.setEmail(email.getText().toString());
			if(itemID > 0) {
				cust.setID(itemID);
				db.updateRecord(cust);
			}
			else
				db.insertRecord(cust);
			break;
		case R.integer.company_type:
			//create new company model object
			CompanyModel comp = new CompanyModel();
			comp.setName(name.getText().toString());
			comp.setAddress(address.getText().toString());
			comp.setCity(city.getText().toString());
			comp.setState(state.getText().toString());
			comp.setZIP(ZIP.getText().toString());
			comp.setRFC(RFC.getText().toString());
			comp.setPhone(phone.getText().toString());
			comp.setEmail(email.getText().toString());
			if(itemID > 0) {
				comp.setID(itemID);
				db.updateRecord(comp);
			}
			else
				db.insertRecord(comp);
			break;
		case R.integer.product_type:
			//create new product model object
			ProductModel prod = new ProductModel();
			prod.setName(name.getText().toString());
			prod.setCode(code.getText().toString());			
			try {
				prod.setPrice(Float.valueOf(price.getText().toString()));
			}catch(Exception e){
				prod.setPrice(0.0f);
			}
			try {
				prod.setTax(Float.valueOf(tax.getText().toString()));
			}catch(Exception e){
				prod.setTax(0.0f);
			}

			prod.setCompanyID(companyID);
			if(itemID > 0) {
				prod.setID(itemID);
				db.updateRecord(prod);
			}
			else
				db.insertRecord(prod);			
			break;
		}
		
		db.close();
	}
}
