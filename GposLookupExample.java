import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import com.itextpdf.layout.properties.BaseDirection;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.licensing.base.LicenseKey;
import java.io.FileInputStream;
import java.io.IOException;

public class GposLookupExample {
    private static String TEXT = "ဗမာမှာ လူများစုဗမာမျိုး နွယ်စု၏ ခေါ် ရာတွင် တရားဝင်ခေါ် သော အသုံး ဖြ စ်သည်။ အင်္ဂလိပ်လက်အော က်";
    private static String FONT = "src/main/resources/font/NotoSansMyanmar-Regular.ttf";
    private static String DEST = "target/SUP35025/Calligraph_OLD.pdf";

    public static void main(String[] args) throws IOException {
        manipulatePdf(DEST);
    }

    public static void manipulatePdf(String dest) throws IOException {
        LicenseKey.loadLicenseFile(new FileInputStream("License/license.json"));

        Document document = new Document(new PdfDocument(new PdfWriter(dest)));
        PdfFont font = PdfFontFactory.createFont(FONT,
                                                 PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        Paragraph paragraph = new Paragraph(TEXT)
                .setBaseDirection(BaseDirection.RIGHT_TO_LEFT)
                .setTextAlignment(TextAlignment.RIGHT);
        font.setSubset(false);

        paragraph.setFont(font);
        document.add(paragraph);

        document.close();
    }
}
