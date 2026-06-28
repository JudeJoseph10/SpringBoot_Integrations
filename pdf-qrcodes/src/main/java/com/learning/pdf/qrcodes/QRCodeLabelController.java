package com.learning.pdf.qrcodes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idautomation.qrcode.QRCode;
import com.idautomation.qrcode.encoder.barCodeEncoder;

@RestController
@RequestMapping("/api/labels")
public class QRCodeLabelController {

	@PostMapping(value = "/generate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> generateLabels(@RequestBody List<String> productIDs) {

		try {
			XWPFDocument document = new XWPFDocument();
			try {
				XWPFTable table = document.createTable();
				table.removeRow(0);
				int columnsPerRow = 2;
				int currentColumn = 0;
				XWPFTableRow row = null;

				for (String productID : productIDs) {
					if (productID != null & !productID.isEmpty()) {

						String qrImageFile = productID + "-qr.jpeg";
						QRCode qr = new QRCode();
						qr.setDataToEncode(productID);
						new barCodeEncoder(qr, "JPEG", qrImageFile);

						if (currentColumn == 0) {
							row = table.createRow();
						}

						XWPFTableCell cell = row.getCell(currentColumn);

						if (cell == null) {
							cell = row.addNewTableCell();
						}

						XWPFParagraph cellParagraph = cell.addParagraph();
						XWPFRun textRun = cellParagraph.createRun();
						textRun.setText("Label for: " + productID);
						textRun.addBreak();

						try (FileInputStream imageStream = new FileInputStream(qrImageFile)) {
							XWPFRun imageRun = cellParagraph.createRun();
							imageRun.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_JPEG, qrImageFile,
									Units.toEMU(70), Units.toEMU(70));
						}
						currentColumn++;
						if (currentColumn >= columnsPerRow) {
							currentColumn = 0;
						}
					}
				}
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				document.write(outStream);

				for (String productID : productIDs) {
					new File(productID + "-qr.jpeg").delete();
				}

				ByteArrayInputStream inputStream = new ByteArrayInputStream(outStream.toByteArray());
				InputStreamResource resource = new InputStreamResource(inputStream);
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=LabelsWithQR.docx")
						.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			} finally {
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
