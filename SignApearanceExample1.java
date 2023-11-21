package iText.example;

import com.itextpdf.forms.form.element.SignatureFieldAppearance;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.RoundDotsBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.itextpdf.layout.renderer.FlexContainerRenderer;

import java.io.IOException;
import java.io.InterruptedIOException;

public class SignApearanceExample1 {
    private static String SRC = "src/main/resources/";
    private static String DEST = "target/";

    public static void main(String[] args) throws Exception {
        new SignApearanceExample1().sigFieldWithDivAppearance(DEST + "signedResult.pdf");
    }

    public void sigFieldWithDivAppearance(String dest) throws IOException, InterruptedIOException {
        try (Document document = new Document(new PdfDocument(new PdfWriter(dest)))) {
            Div div = new Div();
            div.add(new Paragraph("Paragraph inside div with red dotted border and pink background")
                    .setBorder(new DashedBorder(ColorConstants.RED, 1)).setBackgroundColor(ColorConstants.PINK));
            Div flexContainer = new Div();
            flexContainer.setProperty(Property.FLEX_WRAP, FlexWrapPropertyValue.WRAP);
            flexContainer.setProperty(Property.FLEX_DIRECTION, FlexDirectionPropertyValue.ROW_REVERSE);
            flexContainer.setProperty(Property.ALIGN_ITEMS, AlignmentPropertyValue.CENTER);
            flexContainer.add(new Image(ImageDataFactory.create(SRC + "image.png")).scale(0.1f, 0.3f)
                    .setPadding(10)).add(new List()
                    .add(new ListItem("Flex container with").setListSymbol(ListNumberingType.ZAPF_DINGBATS_1))
                    .add(new ListItem(" image and list,").setListSymbol(ListNumberingType.ZAPF_DINGBATS_2))
                    .add(new ListItem(" wrap row-reverse,").setListSymbol(ListNumberingType.ZAPF_DINGBATS_3))
                    .add(new ListItem(" green dots border,").setListSymbol(ListNumberingType.ZAPF_DINGBATS_4))
                    .setPadding(10)).setBorder(new RoundDotsBorder(ColorConstants.GREEN, 10));
            flexContainer.setNextRenderer(new FlexContainerRenderer(flexContainer));
            div.add(flexContainer);

            SignatureFieldAppearance appearance = new SignatureFieldAppearance("form SigField");
            appearance.setContent(div)
                    .setFontColor(ColorConstants.WHITE).setFontSize(10)
                    .setBackgroundColor(ColorConstants.DARK_GRAY)
                    .setBorder(new SolidBorder(ColorConstants.MAGENTA, 2))
                    .setInteractive(true);
            document.add(appearance);
        }
    }
}
