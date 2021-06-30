public void signOffice(Certificate[] chain, PrivateKey pk, String digestAlgorithm,
                     String provider, PdfSigner.CryptoStandard signatureType, String reason, String location)
            throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        
        //Convert .docx document to PDF 
        OfficeConverter.convertOfficeDocumentToPdf(new FileInputStream("Alice in Wonderland Excerpt.docx"), baos);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        PdfReader reader = new PdfReader(bais);
        PdfSigner signer = new PdfSigner(reader, new FileOutputStream("Alice in Wonderland Excerpt.pdf"), new StampingProperties());

        // Create the signature appearance
        Rectangle rect = new Rectangle(36, 648, 200, 100);
        PdfSignatureAppearance appearance = signer.getSignatureAppearance();
        appearance
                .setReason(reason)
                .setLocation(location)
                .setReuseAppearance(false)
                .setPageRect(rect)
                .setPageNumber(1);
        signer.setFieldName("sig");

        IExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
        IExternalDigest digest = new BouncyCastleDigest();

        // Sign the document.
        signer.signDetached(digest, pks, chain, null, null, null, 0, signatureType);
    }
