public void convert(String svg, String output, PageSize size) throws IOException {
   
   try (PdfDocument doc = new PdfDocument(new PdfWriter(output, new WriterProperties().setCompressionLevel(0)))) {
       doc.addNewPage(size);
       ISvgConverterProperties properties = new SvgConverterProperties().setBaseUri(svg);
       SvgConverter.drawOnDocument(new FileInputStream(svg), doc, 1, properties);
   }
}
