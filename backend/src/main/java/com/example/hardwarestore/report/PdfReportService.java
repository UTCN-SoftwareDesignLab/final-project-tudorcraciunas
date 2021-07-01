package com.example.hardwarestore.report;

import com.example.hardwarestore.item.model.DTO.ItemDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.example.hardwarestore.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {


    @Override
    public ReportType getType() {
        return PDF;
    }

    @Override
    public ByteArrayOutputStream export(List<ItemDTO> itemDTOS) {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();

        document.addPage(page);

        PDPageContentStream pageContentStream;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            pageContentStream = new PDPageContentStream(document, page);
            pageContentStream.beginText();
            pageContentStream.setFont(PDType1Font.HELVETICA, 11);
            pageContentStream.setLeading(12.5f);
            pageContentStream.newLineAtOffset(50, 700);
            pageContentStream.showText("Out of Stock:");
            pageContentStream.newLine();
            pageContentStream.newLine();

            for (ItemDTO itemDTO : itemDTOS) {
                pageContentStream.showText(itemDTO.toString());
                pageContentStream.newLine();
                pageContentStream.newLine();
            }

            pageContentStream.endText();
            pageContentStream.close();

            document.save(output);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;

    }
}
