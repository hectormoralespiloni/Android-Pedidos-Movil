package com.pedidosmovil;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout.Alignment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class ItemDetail extends Activity 
{
	private DBAdapter db;
	private TextView textName;
	private TextView textAddress;
	private TextView textCity;
	private TextView textState;
	private TextView textZIP;
	private TextView textRFC;
	private TextView textPhone;
	private TextView textEmail;
	private TextView textCode;
	private TextView textPrice;
	private TextView textTax;
	private TextView textDate;
	private TextView textFolio;
	private TextView textObservations;
	private TextView textDiscount;
	private TextView textTotal;
	private CustomerModel customer;
	private ProductModel product;
	private CompanyModel company;
	private InvoiceModel invoice;
	private ArrayList<InvoiceDetailModel> invDetail;
	private int dataType;
	
	public static void main(String[] args) {		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		onCreate(null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		
		Intent myIntent = getIntent();
		//final int DataType = myIntent.getIntExtra("DataType", 0);
		dataType = myIntent.getIntExtra("DataType", 0);
		final int id = myIntent.getIntExtra("id", 0);
		
		db = new DBAdapter(this);
		db.open();
		
		switch(dataType)
		{
			case R.integer.company_type:
				//Get views
				setContentView(R.layout.company_detail);
				textName = (TextView)findViewById(R.id.textName);
				textAddress = (TextView)findViewById(R.id.textAddress);
				textCity = (TextView)findViewById(R.id.textCity);
				textState = (TextView)findViewById(R.id.textState);
				textZIP = (TextView)findViewById(R.id.textZIP);
				textRFC = (TextView)findViewById(R.id.textRFC);
				textPhone = (TextView)findViewById(R.id.textPhone);
				textEmail = (TextView)findViewById(R.id.textEmail);
				//Get data
				company = db.fetchCompany(id);
				//Set data
				textName.setText(company.getName());
				textAddress.setText(company.getAddress());
				textCity.setText(company.getCity());
				textState.setText(company.getState());
				textZIP.setText(company.getZIP());
				textRFC.setText(company.getRFC());
				textPhone.setText(company.getPhone());
				textEmail.setText(company.getEmail());
				break;			
			case R.integer.customer_type:
				//Get views
				setContentView(R.layout.customer_detail);
				textName = (TextView)findViewById(R.id.textName);
				textAddress = (TextView)findViewById(R.id.textAddress);
				textCity = (TextView)findViewById(R.id.textCity);
				textState = (TextView)findViewById(R.id.textState);
				textZIP = (TextView)findViewById(R.id.textZIP);
				textRFC = (TextView)findViewById(R.id.textRFC);
				textPhone = (TextView)findViewById(R.id.textPhone);
				textEmail = (TextView)findViewById(R.id.textEmail);
				//Get data
				customer = db.fetchCustomer(id);
				//Set data
				textName.setText(customer.getName());
				textAddress.setText(customer.getAddress());
				textCity.setText(customer.getCity());
				textState.setText(customer.getState());
				textZIP.setText(customer.getZIP());
				textRFC.setText(customer.getRFC());
				textPhone.setText(customer.getPhone());	
				textEmail.setText(customer.getEmail());
				break;			
			case R.integer.product_type:
				//Get views
				setContentView(R.layout.product_detail);
				textName = (TextView)findViewById(R.id.textName);
				textCode = (TextView)findViewById(R.id.textCode);
				textPrice = (TextView)findViewById(R.id.textPrice);
				textTax = (TextView)findViewById(R.id.textTax);	
				//Get data
				product = db.fetchProduct(id);
				//Set data
				textName.setText(product.getName());
				textCode.setText(product.getCode());
				textPrice.setText("Precio: $" + Float.valueOf(product.getPrice()).toString());
				textTax.setText("Impuesto: " + Float.valueOf(product.getTax()).toString() + "%");
				break;
			case R.integer.invoice_type:
				//Get views
				setContentView(R.layout.invoice_detail);
				textName = (TextView)findViewById(R.id.textName);
				textAddress = (TextView)findViewById(R.id.textAddress);
				textCity = (TextView)findViewById(R.id.textCity);
				textState = (TextView)findViewById(R.id.textState);
				textZIP = (TextView)findViewById(R.id.textZIP);
				textRFC = (TextView)findViewById(R.id.textRFC);
				textPhone = (TextView)findViewById(R.id.textPhone);
				textDate = (TextView)findViewById(R.id.textDate);
				textFolio = (TextView)findViewById(R.id.textFolio);
				textObservations = (TextView)findViewById(R.id.textObservations);
				textDiscount = (TextView)findViewById(R.id.textDiscount);
				textTotal = (TextView)findViewById(R.id.textTotal);
				//Get data
				invoice = db.fetchInvoice(id);
				invDetail = db.fetchAllInvoiceDetails(id);
				customer = db.fetchCustomer(invoice.getCustomerID());
				company = db.fetchCompany(invoice.getCompanyID());
				//Set data
				textName.setText(customer.getName());
				textAddress.setText(customer.getAddress());
				textCity.setText(customer.getCity());
				textState.setText(customer.getState());
				textZIP.setText(customer.getZIP());
				textRFC.setText(customer.getRFC());
				textPhone.setText(customer.getPhone());
				textDate.setText("Fecha: " + invoice.getDate());
				textFolio.setText("Folio: " + invoice.getFolio());
				textObservations.setText(invoice.getComments());
				textDiscount.setText("Descuento: " + invoice.getDiscount() + "%");
				textTotal.setText("Total: " + String.format("%.2f", invoice.getTotal()));
				//Get table layout and add rows dynamically 
				TableLayout tableDetails = (TableLayout)findViewById(R.id.TableDetails);
				for(int i=0; i<invDetail.size(); i++)
				{
					ProductModel product = db.fetchProduct(invDetail.get(i).getProductID());
					TableRow row = new TableRow(this);					
					TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT);
					lp.setMargins(5, 0, 5, 0);
					//odd rows get light blue background
					if(i%2!=0) row.setBackgroundColor(Color.rgb(173, 216, 230)); 
					
					TextView prodCode = new TextView(this);
					TextView prodName = new TextView(this);
					TextView prodPrice = new TextView(this);
					TextView prodQty = new TextView(this);
					TextView prodTotal = new TextView(this);
					
					prodCode.setText(product.getCode());
					prodName.setText(product.getName());
					prodPrice.setText(String.valueOf(product.getPrice()));
					prodQty.setText(String.valueOf(invDetail.get(i).getQuantity()));
					prodTotal.setText(String.valueOf(invDetail.get(i).getTotal()));	
					
					prodCode.setLayoutParams(lp);
					prodCode.setGravity(Gravity.CENTER);
					prodName.setLayoutParams(lp);
					prodName.setGravity(Gravity.CENTER);
					prodPrice.setLayoutParams(lp);
					prodPrice.setGravity(Gravity.CENTER);
					prodQty.setLayoutParams(lp);
					prodQty.setGravity(Gravity.CENTER);
					prodTotal.setLayoutParams(lp);
					prodTotal.setGravity(Gravity.RIGHT);
					
					row.addView(prodCode);
					row.addView(prodName);
					row.addView(prodPrice);
					row.addView(prodQty);
					row.addView(prodTotal);
					tableDetails.addView(row);
				}
				break;
			default:
				break;
		}		
		db.close();
		
		Button bEdit = (Button)findViewById(R.id.ButtonEdit);
		//Invoice does not have edit button...
		if(bEdit!=null)
		{
			bEdit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					//Get data for edit here
					Intent myIntent = new Intent(getApplicationContext(), ItemAdd.class);
					myIntent.putExtra("DataType", dataType);
					myIntent.putExtra("id", id);
					startActivity(myIntent);
				}
			});
		}		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		if(dataType == R.integer.invoice_type) {
			getMenuInflater().inflate(R.menu.print, menu);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Intent shareIntent;
		ExportPDF pdf;
		
		switch(item.getItemId()) {
		case R.id.action_signature:
			shareIntent = new Intent(getApplicationContext(), Signature.class);
			startActivityForResult(shareIntent, R.integer.REQUEST_SIGNATURE);
			break;
			
		case R.id.action_print: 
			pdf = new ExportPDF(getApplicationContext());
			pdf.Export(invoice.getID());

			File file = new File(getExternalFilesDir(null)+"/pedido.pdf");
			shareIntent = new Intent(Intent.ACTION_VIEW);
			shareIntent.setDataAndType(Uri.fromFile(file), "application/pdf");						
			shareIntent.setPackage("com.dynamixsoftware.printershare");
			
			try {
			    startActivity(shareIntent);
			} catch (ActivityNotFoundException e) {				
				Toast.makeText(this, "No es posible imprimir, por favor instale PrinterShare(R)", Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.action_email: 
			pdf = new ExportPDF(getApplicationContext());
			pdf.Export(invoice.getID());
			
			Uri uri = Uri.parse("file://" + getExternalFilesDir(null)+"/pedido.pdf");			

			shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("message/rfc822");
			shareIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { customer.getEmail() });
			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Pedido "+company.getName()+" "+invoice.getDate());
			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Pedido adjunto");
			shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, uri);
			try {
				startActivity(Intent.createChooser(shareIntent,"Enviar email"));
			} catch(ActivityNotFoundException e) {
				Toast.makeText(this, "No es posible enviar e-mail. No existe una aplicación instalada", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (resultCode == RESULT_OK) 
		{
			if(requestCode == R.integer.REQUEST_SIGNATURE)
			{
				Bundle bundle = data.getExtras();
				String ID = bundle.getString("ID");
				db.open();
				invoice.setSignatureID(ID);
				db.updateRecord(invoice);
				db.close();
				Toast.makeText(this, "Firma guardada con éxito!",Toast.LENGTH_SHORT).show();
			}
		}
	}
}
