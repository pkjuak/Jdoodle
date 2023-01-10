
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.licensing.base.LicenseKey;

import java.io.FileInputStream;
import java.io.IOException;

public class TextWrapTest {
    public static void main(String[] args) throws IOException{
        LicenseKey.loadLicenseFile(new FileInputStream("license/license.json"));
        String fileName = "target/testTextWrapping_New.pdf";
        testTextWrapping(fileName);
    }

    public static void testTextWrapping(String fileName) throws IOException {
        
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
        pdfDoc.setDefaultPageSize(PageSize.LETTER);
      
        Document document = new Document(pdfDoc);
        document.setLeftMargin(68.04F);
        document.setRightMargin(68.04F);
        
        PdfFont font = PdfFontFactory.createFont("Fonts/arial.ttf");

        String text1 = "Please note that the corporation does not have IPFCF coverage " +
                "for any period of time during which primary ";

        String text2 =  "professional liability insurance " +
                "coverage is not in place, and that the statutory limits of liability do not " +
                "apply for any such ";

        String text3 = "period.";

        Text text = new Text(text1);
        text.setFont(font);
        text.setFontSize(9);
        Paragraph paragraph = new Paragraph();
        paragraph.add(text);

        text = new Text(text2);
        text.setFont(font);
        text.setFontSize(9);
        paragraph.add(text);

        text = new Text(text3);
        text.setFont(font);
        text.setFontSize(9);
        paragraph.add(text);

        paragraph.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        document.add(paragraph);
        document.close();
    }
}
