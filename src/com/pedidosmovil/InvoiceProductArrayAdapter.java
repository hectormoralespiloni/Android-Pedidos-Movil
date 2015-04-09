package com.pedidosmovil;

import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;

public class InvoiceProductArrayAdapter extends ArrayAdapter<ProductModel> {
	private Context context;
	private ArrayList<ProductModel> values;
	private ArrayList<Integer> quantities;
	private ArrayList<Float> totals;
	private InvoiceProductArrayAdapterCallback callback;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public InvoiceProductArrayAdapter(Context context, ArrayList<ProductModel> values) {
		super(context, R.layout.invoice_product, values);
		this.context = context;
		this.values = values;
	}
	
	public void setCallback(InvoiceProductArrayAdapterCallback callback) {
		this.callback = callback;
	}
	
	public void setQuantities(ArrayList<Integer> quantities) {
		this.quantities = quantities;
	}
	
	public void setTotals(ArrayList<Float> totals) {
		this.totals = totals;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		Log.w("getView", "Calling get view - pos " + String.valueOf(position));
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.invoice_product, parent, false);
		//get view items
		TextView productName = (TextView)view.findViewById(R.id.TextProductName);
		TextView productCode = (TextView)view.findViewById(R.id.TextProductCode);		
		TextView productPrice = (TextView)view.findViewById(R.id.TextPrice);
		TextView productTax = (TextView)view.findViewById(R.id.TextTax);
		TextView productQty = (TextView)view.findViewById(R.id.TextQty);
		TextView productTotal = (TextView)view.findViewById(R.id.TextTotal);
		final Button bCalc = (Button)view.findViewById(R.id.ButtonCalc);
		
		//set their values		
		productName.setText(values.get(position).getName());
		productCode.setText(values.get(position).getCode());
		productPrice.setText(String.valueOf(values.get(position).getPrice()));
		productTax.setText(String.valueOf(values.get(position).getTax()) + "%");
		
		productQty.setText(String.valueOf(quantities.get(position)));
		productTotal.setText(String.format("%.2f", totals.get(position)));
		
		bCalc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(callback != null) {
					callback.OpenDialog(position);
				}
			}
		});
		
		return view;
	}
	
	@Override
	public int getCount() 
	{
		return values != null ? values.size() : 0;
	}
}
