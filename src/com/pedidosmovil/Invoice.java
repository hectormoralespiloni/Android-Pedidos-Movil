package com.pedidosmovil;

import com.pedidosmovil.R;
import android.os.Bundle;

public class Invoice extends ItemList
{
	public static void main(String[] args) {
		//TODO
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.setDataType(R.integer.invoice_type);
		super.onCreate(savedInstanceState);
	}
}
