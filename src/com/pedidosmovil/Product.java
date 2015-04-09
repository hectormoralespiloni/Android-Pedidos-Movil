package com.pedidosmovil;

import android.os.Bundle;

public class Product extends ItemList 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.setDataType(R.integer.product_type);
		super.onCreate(savedInstanceState);
	}
}
