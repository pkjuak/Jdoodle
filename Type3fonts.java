import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.licensekey.LicenseKey;
import com.itextpdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.pdfcleanup.PdfCleanUpTool;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CleanupTest {
    
    public static void main(String[] args) throws IOException {
       
        //Load the license file to use cleanup features
        LicenseKey.loadLicenseFile("license.xml");
        
        StampingProperties properties = new StampingProperties();
        properties.useAppendMode();

        PdfDocument pdfDocument = new PdfDocument(new PdfReader("base.pdf"), new PdfWriter("cleanupResult.pdf"), properties);
        PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(pdfDocument, false);

        if (pdfAcroForm != null) {
            pdfAcroForm.flattenFields();
        }
       
        DeviceRgb color = (DeviceRgb) ColorConstants.BLACK;

        List<PdfCleanUpLocation> cleanUpLocations = new ArrayList<PdfCleanUpLocation>();
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(10, 300, 600, 400), color));
        PdfCleanUpTool cleaner = new PdfCleanUpTool(pdfDocument, cleanUpLocations);

        cleaner.cleanUp();

        pdfDocument.close();
    }
}

