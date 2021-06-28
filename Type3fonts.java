public void manipulatePdf(String dest) throws IOException {
   //Load the license file to use cleanup features
   LicenseKey.loadLicenseFile("licese.xml");
   PdfDocument pdfDocument = new PdfDocument(new PdfReader(SRC), new PdfWriter(dest));
   PdfAcroForm pdfAcroForm = PdfAcroForm.getAcroForm(pdfDocument, false);

   if (pdfAcroForm != null) {
       pdfAcroForm.flattenFields();
   }
   DeviceRgb color = (DeviceRgb) ColorConstants.BLACK;

   List<PdfCleanUpLocation> cleanUpLocations = new ArrayList<PdfCleanUpLocation>();
   cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(50, 50, 500, 800), color));
   PdfCleanUpTool cleaner = new PdfCleanUpTool(pdfDocument, cleanUpLocations);

   cleaner.cleanUp();

   pdfDocument.close();
}

