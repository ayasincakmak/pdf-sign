package gr.hcg.sign;


import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.print.Doc;

public class AddEmbeddedFiles {

    public static void main(String[] args) throws Exception{
        PDDocument doc = null;
        try {
            
            //PDPage page = new PDPage();
            //doc.addPage(page);
             File aFile=  new File("D:\\DateConversions.pdf");
             doc = PDDocument.load(new File("D:\\DateConversions.pdf"));
            
            //PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            //contentStream.beginText();
            //contentStream.setFont(PDType1Font.HELVETICA, 12);
            //contentStream.newLineAtOffset(100, 700);
            //contentStream.showText("Go to Document -> File Attachments to View Embedded Files");
            //contentStream.endText();
           // contentStream.close();

            doc.getPages().get(0).getContents();
            // embedded files are stored in a named tree
            PDEmbeddedFilesNameTreeNode efTree = new PDEmbeddedFilesNameTreeNode();

            // first create the file specification, which holds the embedded file
            PDComplexFileSpecification fs = new PDComplexFileSpecification();
            fs.setFile("UBL2.xml");
            fs.setFileDescription("invoice-ubl");
            
            // create a dummy file stream, this would probably normally be a FileInputStream
            byte[] data = "This is the contents of the embedded file".getBytes("ISO-8859-1");
            ByteArrayInputStream fakeFile = new ByteArrayInputStream(data);
            
            // now lets some of the optional parameters
            PDEmbeddedFile ef = new PDEmbeddedFile(doc, fakeFile);
            ef.setCreationDate(new GregorianCalendar());
            fs.setEmbeddedFile(ef);

            // create a new tree node and add the embedded file 
            PDEmbeddedFilesNameTreeNode treeNode = new PDEmbeddedFilesNameTreeNode();
            treeNode.setNames(Collections.singletonMap("attachment",  fs));

            // add the new node as kid to the root node
            List<PDEmbeddedFilesNameTreeNode> kids = new ArrayList<PDEmbeddedFilesNameTreeNode>();
            kids.add(treeNode);
            efTree.setKids(kids);

            PDDocument outDoc = new PDDocument();
            outDoc.setDocumentInformation(doc.getDocumentInformation());
            for (PDPage srcPage : doc.getPages()) {
                new PDPageContentStream(outDoc, srcPage,
                        PDPageContentStream.AppendMode.APPEND, true).close();
                outDoc.addPage(srcPage);
            }
           // outDoc.save(os);
            //outDoc.close();

            
            // add the tree to the document catalog
            PDDocumentNameDictionary names = new PDDocumentNameDictionary(doc.getDocumentCatalog());
            names.setEmbeddedFiles(efTree);
            doc.getDocumentCatalog().setNames(names);

            outDoc.save(new File("D:/embedded-file.pdf"));
            outDoc.close();
        } catch (IOException e){
            System.err.println("Exception while trying to create pdf document - " + e);
        }

        finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

}