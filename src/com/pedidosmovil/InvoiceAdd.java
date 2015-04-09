package com.pedidosmovil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class InvoiceAdd extends Activity implements InvoiceProductArrayAdapterCallback
{
	private Calculator calculator;
	private int customerID;
	private int companyID;
	private Intent intent;
	private DBAdapter db;
	private ArrayList<ProductModel> products;
	private ArrayList<Integer> quantities;
	private ArrayList<Float> totals;
	private InvoiceModel invoice;
	private InvoiceDetailModel invoiceDetail;
	private String observations;
	private Float discount;
	
	public static void main(String[] args) {
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoice_add);
		
		discount = 0.0f;
		observations = "";
		
		//Get products from selected company
		intent = getIntent();
		customerID = intent.getIntExtra("customerID", 0);
		companyID = intent.getIntExtra("companyID", 0);
		
		db = new DBAdapter(this);
		db.open();
		products = db.fetchAllProducts(companyID);
		
		//Initialize quantities & totals to be used on ListView
		//and avoid losing their values when scrolling.
		quantities = new ArrayList<Integer>(products.size());
		totals = new ArrayList<Float>(products.size());
		for(int i=0; i<products.size(); i++)
		{
			quantities.add(i,0);
			totals.add(i, 0.0f);
		}

		ListView listView = (ListView)findViewById(R.id.listView1);        
        InvoiceProductArrayAdapter adapter = new InvoiceProductArrayAdapter(this.getApplicationContext(), products);
        adapter.setCallback(this);
        adapter.setQuantities(quantities);
        adapter.setTotals(totals);
        listView.setAdapter(adapter);
        
        Button bSave = (Button)findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveInvoice();
			}
		});
	}
	
	@Override
	public void OpenDialog(int position) 
	{
		calculator = new Calculator(InvoiceAdd.this);
		calculator.setPosition(position);
		calculator.setParentView((ListView)findViewById(R.id.listView1));
		calculator.setQuantities(quantities);
		calculator.setTotals(totals);
		calculator.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Intent myIntent;
		
		switch(item.getItemId()) {
		case R.id.action_options:
			myIntent = new Intent(getApplicationContext(), Options.class);
			myIntent.putExtra("discount", discount);
			myIntent.putExtra("observation", observations);
			startActivityForResult(myIntent, R.integer.REQUEST_OPTIONS);
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{		
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode) {
		case R.integer.REQUEST_OPTIONS:
			if(resultCode == RESULT_OK) {
				discount = data.getFloatExtra("discount", 0.0f);
				observations = data.getStringExtra("observations");
			}
			break;
		case R.integer.REQUEST_SIGNATURE:
			if(resultCode == RESULT_OK) {
				//TODO
			}
			break;
		}
	}
	
	public void saveInvoice()
	{
		int folio = 1;
		float subtotal = 0.0f;
		float total = 0.0f;
		invoice = new InvoiceModel();
		invoiceDetail = new InvoiceDetailModel();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		
		ArrayList<InvoiceModel> invoices = db.fetchAllInvoices();
		if(invoices.size()>0)
			folio = invoices.get(invoices.size()-1).getFolio()+1;
		
		//Create new invoice here
		invoice.setComments("");
		invoice.setCompanyID(companyID);
		invoice.setCustomerID(customerID);
		invoice.setDate(today);
		invoice.setDiscount(discount);
		invoice.setComments(observations);
		invoice.setFolio(folio);
		invoice.setSubtotal(0.0f);
		invoice.setTotal(0.0f);
		int invoice_id = db.insertRecord(invoice);
		invoice.setID(invoice_id);

		for(int i=0; i<products.size(); i++)
		{
			if(quantities.get(i) > 0)
			{
				subtotal+=totals.get(i);
				//Insert detail here
				invoiceDetail.setInvoiceID(invoice.getID());
				invoiceDetail.setProductID(products.get(i).getID());
				invoiceDetail.setQuantity(quantities.get(i));
				invoiceDetail.setTotal(totals.get(i));
				db.insertRecord(invoiceDetail);				
			}
		}
		
		//set invoice totals
		total = subtotal - (subtotal * (discount/100.0f));
		invoice.setSubtotal(subtotal);
		invoice.setTotal(total);
		db.updateRecord(invoice);
		
		Toast.makeText(getApplicationContext(), R.string.item_saved, Toast.LENGTH_SHORT).show();
		finish();
	}
}
