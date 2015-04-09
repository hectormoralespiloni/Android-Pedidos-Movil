package com.pedidosmovil;

import jxl.*;
import jxl.read.biff.BiffException;
import java.io.File;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ImportExcel extends AsyncTask<Integer, Integer, Integer>
{
	public static final int TYPE_CUSTOMER = 0;
	public static final int TYPE_COMPANY = 1;
	public static final int TYPE_PRODUCT = 2;
	
	private int COL_PROD_CODE = 0;
	private int COL_PROD_NAME = 1;
	private int COL_PROD_TAX = 2;
	private int COL_PROD_PRICE = 3;
	
	private int COL_NAME = 0;
	private int COL_ADDR = 1;
	private int COL_CITY = 2;
	private int COL_STATE = 3;
	private int COL_ZIP = 4;
	private int COL_RFC = 5;
	private int COL_PHONE = 6;
	private int COL_EMAIL = 7;
	
	private DBAdapter db;
	private Context context;
	private int companyID;
	private String filePath;
	private int type;
	private ProgressDialog progressDialog;
	private OnTaskCompleted taskCompletedListener;
	private OnProgressUpdate progressUpdateListener;
	
	public static void main(String[] args) {
	}
	
	public ImportExcel(Context context) {
		this.companyID = 0;
		this.context = context;
	}
	
	public void setOnTaskCompletedListener(OnTaskCompleted listener) {
		this.taskCompletedListener = listener;
	}
	
	public void setOnProgressUpdateListener(OnProgressUpdate listener) {
		this.progressUpdateListener = listener;
	}
	
	public void setProgressDialog(ProgressDialog dialog) {
		this.progressDialog = dialog;
	}
	
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	@Override
	protected Integer doInBackground(Integer... params) 
	{	
		int retValue = 0;
		db = new DBAdapter(context);
		db.open();
		
		try 
		{
			switch(type) {
			case TYPE_COMPANY:				
				db.deleteTable(DBOpenHelper.TABLE_COMPANY);
				break;
			case TYPE_CUSTOMER:
				db.deleteTable(DBOpenHelper.TABLE_CUSTOMER);
				break;
			case TYPE_PRODUCT:
				db.deleteTable(DBOpenHelper.TABLE_PRODUCT);
				break;
			}
			
			Workbook workbook = Workbook.getWorkbook(new File(filePath));
			Sheet sheet = workbook.getSheet(0);
			progressDialog.setMax(sheet.getRows());

			for(int i=0; i<sheet.getRows(); i++)
			{						
				switch(type) 
				{
				case TYPE_COMPANY: {
					String name = sheet.getCell(COL_NAME,i).getContents();
					String addr = sheet.getCell(COL_ADDR,i).getContents();
					String city = sheet.getCell(COL_CITY,i).getContents();
					String state = sheet.getCell(COL_STATE,i).getContents();
					String phone = sheet.getCell(COL_PHONE,1).getContents();
					String zip = sheet.getCell(COL_ZIP,i).getContents();
					String rfc = sheet.getCell(COL_RFC,i).getContents();
					String email = sheet.getCell(COL_EMAIL,i).getContents();
					CompanyModel company = new CompanyModel();
					company.setName(name);
					company.setAddress(addr);
					company.setCity(city);
					company.setState(state);
					company.setZIP(zip);
					company.setRFC(rfc);
					company.setEmail(email);
					company.setPhone(phone);
					db.insertRecord(company);
					break;
					}
				case TYPE_CUSTOMER: {
					String name = sheet.getCell(COL_NAME,i).getContents();
					String addr = sheet.getCell(COL_ADDR,i).getContents();
					String city = sheet.getCell(COL_CITY,i).getContents();
					String state = sheet.getCell(COL_STATE,i).getContents();
					String phone = sheet.getCell(COL_PHONE,i).getContents();
					String zip = sheet.getCell(COL_ZIP,i).getContents();
					String rfc = sheet.getCell(COL_RFC,i).getContents();
					String email = sheet.getCell(COL_EMAIL,i).getContents();
					CustomerModel customer = new CustomerModel();
					customer.setName(name);
					customer.setAddress(addr);
					customer.setCity(city);
					customer.setState(state);
					customer.setZIP(zip);
					customer.setRFC(rfc);
					customer.setEmail(email);
					customer.setPhone(phone);
					db.insertRecord(customer);
					break;
					}
				case TYPE_PRODUCT: {
					String code = sheet.getCell(COL_PROD_CODE,i).getContents();
					String name = sheet.getCell(COL_PROD_NAME,i).getContents();
					Float tax = Float.valueOf(sheet.getCell(COL_PROD_TAX,i).getContents().replace(',','.'));
					Float price = Float.valueOf(sheet.getCell(COL_PROD_PRICE,i).getContents().replace(',','.'));
					ProductModel product = new ProductModel();
					product.setCode(code);
					product.setName(name);
					product.setPrice(price);
					product.setTax(tax);
					product.setCompanyID(companyID);					
					db.insertRecord(product);
					break;
					}
				}
				Thread.sleep(10);
				publishProgress(i);
			}

		} catch (BiffException e) {				
			e.printStackTrace();
			retValue = -1;
		} catch (Exception e) {			
			e.printStackTrace();
			retValue = -1;
		}

		db.close();
		return retValue;
	}
	
	@Override
	protected void onPostExecute(Integer result) 
	{
		taskCompletedListener.onTaskCompleted(result);
		/*
		progressDialog.dismiss();
		
		if(result == 0)
			Toast.makeText(context, "¡Datos importados con éxito!", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(context, "Ha ocurrido un error al importar datos", Toast.LENGTH_SHORT).show();
		*/
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) 
	{
		progressUpdateListener.onProgressUpdate(values[0]);
		//progressDialog.setProgress(values[0]);
	}
}
