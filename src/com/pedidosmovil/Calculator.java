package com.pedidosmovil;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Calculator extends Dialog
{
	private int position;
	private ListView parentView;
	private ArrayList<Integer> quantities;
	private ArrayList<Float> totals;

	public static void main(String[] args) {		
	}
	
	public Calculator(Context context) {
		super(context);		
	}		

	public void setPosition(int position){
		this.position = position;
	}
	
	public void setParentView(ListView listView){
		this.parentView = listView;
	}
	
	public void setQuantities(ArrayList<Integer> quantities) {
		this.quantities = quantities;
	}
	
	public void setTotals(ArrayList<Float> totals) {
		this.totals = totals;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.calculator);
		setTitle("Calculadora");
		
		final EditText qty = (EditText)findViewById(R.id.editTextCalc);
		Button b0 = (Button)findViewById(R.id.button0);
		Button b1 = (Button)findViewById(R.id.button1);
		Button b2 = (Button)findViewById(R.id.button2);
		Button b3 = (Button)findViewById(R.id.button3);
		Button b4 = (Button)findViewById(R.id.button4);
		Button b5 = (Button)findViewById(R.id.button5);
		Button b6 = (Button)findViewById(R.id.button6);
		Button b7 = (Button)findViewById(R.id.button7);
		Button b8 = (Button)findViewById(R.id.button8);
		Button b9 = (Button)findViewById(R.id.button9);
		Button bCE = (Button)findViewById(R.id.buttonCE);
		Button bAdd = (Button)findViewById(R.id.buttonAdd);
		Button bDel = (Button)findViewById(R.id.buttonDel);
		Button bOK = (Button)findViewById(R.id.ButtonCalcOK);
		Button bCancel = (Button)findViewById(R.id.ButtonCalcCancel);
		
		b0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("0");
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("1");
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("2");
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("3");
			}
		});
		b4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("4");
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("5");
			}
		});
		b6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("6");
			}
		});
		b7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("7");
			}
		});
		b8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("8");
			}
		});
		b9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.append("9");
			}
		});	
		bCE.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				qty.setText("");
			}
		});	
		bAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int sum = Integer.valueOf(qty.getText().toString());
				sum++;
				qty.setText(Integer.toString(sum));
			}
		});
		bDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int sum = Integer.valueOf(qty.getText().toString());
				sum--;
				qty.setText(Integer.toString(sum));
			}
		});		
		
		bOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int start = parentView.getFirstVisiblePosition();
				View view = parentView.getChildAt(position-start);
				TextView tvQty = (TextView)view.findViewById(R.id.TextQty);
				TextView tvPrice = (TextView)view.findViewById(R.id.TextPrice);
				TextView tvTotal = (TextView)view.findViewById(R.id.TextTotal);
				TextView tvTax = (TextView)view.findViewById(R.id.TextTax);
				
				//Do calculations here
				int qtyval = Integer.valueOf(qty.getText().toString());
				tvQty.setText(Integer.toString(qtyval));				
				float priceval = Float.valueOf(tvPrice.getText().toString());
				//get tax, remove trailing '%'
				String taxStr = tvTax.getText().subSequence(0, tvTax.length()-1).toString();
				float pricetax = 1+(Float.valueOf(taxStr)/100);
				float totalval = qtyval * priceval * pricetax;
				tvTotal.setText(String.format("%.2f", totalval));
				
				//Save chosen quantity & calculated total
				quantities.add(position, qtyval);
				totals.add(position, totalval);
				
				dismiss();
			}
		});
		
		bCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();	
			}
		});
	}
}
