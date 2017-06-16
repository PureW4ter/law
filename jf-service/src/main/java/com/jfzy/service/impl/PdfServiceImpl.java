package com.jfzy.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfzy.service.PdfService;
import com.jfzy.service.exception.JfApplicationRuntimeException;

public class PdfServiceImpl implements PdfService {

	private byte[] createPdf() {
		Document doc = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(doc, baos);
			doc.open();
			PdfPTable table = new PdfPTable(8);
			for (int aw = 0; aw < 16; aw++) {
				table.addCell("hi");
			}
			doc.add(table);
			doc.close();
			return baos.toByteArray();
		} catch (DocumentException e) {
			throw new JfApplicationRuntimeException("创建PDF文件失败", e);
		}

	}

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("/tmp/a.pdf");
		fos.write(new PdfServiceImpl().createPdf());
		fos.close();
	}
}
