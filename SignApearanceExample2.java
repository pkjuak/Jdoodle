package iText.example;

import com.itextpdf.forms.form.element.SignatureFieldAppearance;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Enumeration;

public class SignApearanceExample2 {
    private static final String CERT_PATH = "YOUR_CERTIFICATE_PATH";
    private static String SRC = "src/main/resources/";
    private static String DEST = "target/";
    public static void main(String[] args) throws Exception {
       signWithImageAppearance(SRC + "SignedResult.pdf", DEST + "result_Signed_IMG_Appearance.pdf");
    }

    private static void signWithImageAppearance(String src, String dest) throws Exception {

        PdfSigner pdfSigner = new PdfSigner(new PdfReader(src), new FileOutputStream(dest), new StampingProperties());
        pdfSigner.setCertificationLevel(PdfSigner.CERTIFIED_NO_CHANGES_ALLOWED);

        //Set the name indicating the field to be signed.
        //The field can already be present in the document but most not be signed.
        pdfSigner.setFieldName("signature");
        ImageData clientSignatureImage = ImageDataFactory.create(SRC + "image.png");

        SignatureFieldAppearance signatureAppearance = new SignatureFieldAppearance(pdfSigner.getFieldName())
                .setContent(clientSignatureImage);
        pdfSigner.setPageNumber(1)
                .setPageRect(new Rectangle(100, 100, 25, 15))
                .setSignatureAppearance(signatureAppearance);

        char[] password = "password".toCharArray();
        IExternalSignature pks = getPrivateKeySignature(CERT_PATH, password);
        Certificate[] chain = getCertificateChain(CERT_PATH, password);
        OCSPVerifier ocspVerifier = new OCSPVerifier(null, null);
        OcspClientBouncyCastle ocspClient = new OcspClientBouncyCastle(ocspVerifier);
        java.util.List<ICrlClient> crlClients = Arrays.asList(new CrlClientOnline());

        // Sign the document using the detached mode, CMS or CAdES equivalent.
        pdfSigner.signDetached(new BouncyCastleDigest(), pks, chain, crlClients, ocspClient, null,
                0, PdfSigner.CryptoStandard.CMS);
    }
    private static PrivateKeySignature getPrivateKeySignature(String certificatePath, char[] password) throws Exception {
        PrivateKey pk = null;

        KeyStore p12 = KeyStore.getInstance("pkcs12");
        p12.load(new FileInputStream(certificatePath), password);

        Enumeration<String> aliases = p12.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            if (p12.isKeyEntry(alias)) {
                pk = (PrivateKey) p12.getKey(alias, password);
                break;
            }
        }
        Security.addProvider(new BouncyCastleProvider());
        return new PrivateKeySignature(pk, DigestAlgorithms.SHA512, BouncyCastleProvider.PROVIDER_NAME);
    }

    private static Certificate[] getCertificateChain(String certificatePath, char[] password) throws Exception {
        Certificate[] certChain = null;

        KeyStore p12 = KeyStore.getInstance("pkcs12");
        p12.load(new FileInputStream(certificatePath), password);

        Enumeration<String> aliases = p12.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            if (p12.isKeyEntry(alias)) {
                certChain = p12.getCertificateChain(alias);
                break;
            }
        }
        return certChain;
    }

}
