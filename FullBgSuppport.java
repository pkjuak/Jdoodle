public void generatePdf(String src, String dest) throws IOException {
    //A converter properties with a base uri set
    // to point to the directory where images can be retrieved
    ConverterProperties converterProperties = new ConverterProperties().setBaseUri(RESOURCES_DIR);
    PdfDocument pdfDocument = new PdfDocument(new PdfWriter(dest));
    try (FileInputStream fileInputStream = new FileInputStream(src)) {
    HtmlConverter.convertToPdf(fileInputStream, pdfDocument,  converterProperties);
    }
}
