package com.pedidosmovil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import android.content.Context;
import android.content.ContextWrapper;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportPDF 
{
	private final Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private final Font normalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private final Font smallFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private final String FILE = "pedido.pdf";
	private Document document;
	private Context context;
	private DBAdapter db;
	private InvoiceModel invoice;
	private ArrayList<InvoiceDetailModel> details;
	private CustomerModel customer;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public ExportPDF(Context context)
	{
		this.context = context;
	    try {
	    	String path = this.context.getExternalFilesDir(null).getAbsolutePath()+"/"+FILE;	    	
	        document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(path));			
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void Export(int invoiceID)
	{
		try {
	        //get data from DB	        
			db = new DBAdapter(context);
			db.open();
			invoice = db.fetchInvoice(invoiceID);
			details = db.fetchAllInvoiceDetails(invoiceID);
			customer = db.fetchCustomer(invoice.getCustomerID());
			db.close();
			
			document.open();
			addCustomer(document);
			addInvoiceData(document);	
			addSignature(document);
			document.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addCustomer(Document document) throws DocumentException
	{
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Element.ALIGN_LEFT);
		paragraph.add(new Paragraph("Datos del Cliente:", normalFontBold));
		paragraph.add(new Paragraph(customer.getRFC(), normalFont));
		paragraph.add(new Paragraph(customer.getName(), normalFont));
		paragraph.add(new Paragraph(customer.getAddress(), normalFont));
		paragraph.add(new Paragraph(customer.getCity()+", "+customer.getState()+", "+customer.getZIP(), normalFont));
		paragraph.add(new Paragraph("Tel. "+customer.getPhone(), normalFont));
		paragraph.add(new Paragraph(" "));		
		document.add(paragraph);
	}
	
	private void addInvoiceData(Document document) throws DocumentException
	{
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		paragraph.add(new Paragraph("Datos del pedido:", normalFontBold));
		paragraph.add(new Paragraph("Folio: "+invoice.getFolio(), normalFont));
		paragraph.add(new Paragraph("Fecha: "+invoice.getDate(), normalFont));
		paragraph.add(new Paragraph("Subtotal: $"+invoice.getSubtotal(), normalFont));
		paragraph.add(new Paragraph("Descuento: "+invoice.getDiscount()+"%", normalFont));
		paragraph.add(new Paragraph("Total: $"+invoice.getTotal(), normalFont));		
		paragraph.add(new Paragraph("Observaciones: "+invoice.getComments(), normalFont));
		paragraph.add(new Paragraph(" "));
		paragraph.add(new Paragraph("Detalles: ", normalFontBold));
		paragraph.add(new Paragraph(" "));
		document.add(paragraph);
		addTable(document);
	}
	
	private void addTable(Document document) throws DocumentException
	{
		PdfPTable table = new PdfPTable(5);

	    PdfPCell c1 = new PdfPCell(new Phrase("Código", smallFont));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GRAY);
	    table.addCell(c1);

	    c1 = new PdfPCell(new Phrase("Nombre",smallFont));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GRAY);
	    table.addCell(c1);

	    c1 = new PdfPCell(new Phrase("Cantidad", smallFont));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GRAY);
	    table.addCell(c1);
	    
	    c1 = new PdfPCell(new Phrase("Precio", smallFont));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GRAY);
	    table.addCell(c1);	    
	    
	    c1 = new PdfPCell(new Phrase("Total", smallFont));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    c1.setBackgroundColor(BaseColor.GRAY);
	    table.addCell(c1);
	    table.setHeaderRows(1);

	    db.open();
	    for(int i=0; i<details.size(); i++)
	    {
	    	//get the data
	    	int qty = details.get(i).getQuantity();
	    	float total = details.get(i).getTotal();
	    	int productID = details.get(i).getProductID();
	    	ProductModel product = db.fetchProduct(productID);
	    	
	    	//fill the table
	    	table.addCell(new Phrase(product.getCode(), smallFont));
	    	table.addCell(new Phrase(product.getName(), smallFont));
	    	c1 = new PdfPCell(new Phrase(String.valueOf(qty), smallFont));
	    	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	table.addCell(c1);
	    	c1 = new PdfPCell(new Phrase(String.valueOf(product.getPrice()), smallFont));
	    	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	table.addCell(c1);
	    	c1 = new PdfPCell(new Phrase(String.valueOf(total), smallFont));
	    	c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    	table.addCell(c1);
	    }
	    db.close();
	    document.add(table);
	}
	
	private void addSignature(Document document)
	{
		ContextWrapper cw = new ContextWrapper(context);
		String directory = cw.getDir("signatures", Context.MODE_PRIVATE).getAbsolutePath();
		Image img = null;
        try {
			//img = Image.getInstance(directory+"/20140301_51423_0.0635977030548045.png");
			img = Image.getInstance(directory+"/"+invoice.getSignatureID());
	        if (img.getScaledWidth() > 300 || img.getScaledHeight() > 300) {
	        	img.scaleToFit(300, 300);
	        }
	        document.add(new Paragraph(" "));
	        document.add(new Paragraph("Firma de conformidad: ", normalFontBold));
			document.add(img);
		} catch(Exception e) {			
			e.printStackTrace();
		}       
	}
}
