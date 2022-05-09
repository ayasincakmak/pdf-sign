package gr.hcg.sign;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PDShrink {
    public static void shrinkFirstpage(PDDocument pdf) throws IOException {
        PDPageTree tree = pdf.getDocumentCatalog().getPages();
        PDPage page = tree.get(0);

        PDPageContentStream contentStream = new PDPageContentStream(pdf, page, PDPageContentStream.AppendMode.APPEND, false);

        contentStream.transform(new Matrix(0.9f, 0, 0, 0.9f, 25,75));
        contentStream.close();

    }
    
    

    public static void shrinkAllPages(PDDocument pdf) throws IOException {
        PDPageTree tree = pdf.getDocumentCatalog().getPages();

        Iterator<PDPage> iterator = tree.iterator();
        while (iterator.hasNext()) {
            PDPage page = iterator.next();
            PDPageContentStream contentStream = new PDPageContentStream(pdf, page, PDPageContentStream.AppendMode.PREPEND, false);
            contentStream.transform(new Matrix(0.9f, 0, 0, 0.9f, 25, 75));
            contentStream.close();
        }
    }


    public static void main(String[] args) throws FileNotFoundException, IOException {
        try (PDDocument pdf = PDDocument.load(new File("D:\\4LT2022000000004_signed_noitex.pdf"))) {
            shrinkAllPages(pdf);
            pdf.save("D:\\foo2.pdf");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        
        File toCompress = new File("D:\\4LT2022000000004_signed_noitex.pdf");
        File compressedOutputFile = new File("D:\\compressedFile2.pdf");
         try {
             
             PDDocument doc = new PDDocument();
             doc.load(toCompress);
             
            // PDDocument doc = new PDDocument();
             InputStream in = new FileInputStream(toCompress);
             OutputStream out = new FileOutputStream(compressedOutputFile);

             PDPage page = new PDPage();
             doc.addPage(page);

             PDStream stream = new PDStream(doc, in,    COSName.FLATE_DECODE);

             doc.save(out);
             doc.close();

         } catch (Exception e) {

         }
     
    }
}