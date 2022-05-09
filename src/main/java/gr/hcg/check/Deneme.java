//package com.ttech.tbas.common.util.security;
//
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.*;
//import com.itextpdf.text.pdf.security.*;
//import com.ttech.tbas.common.util.SecurityUtil;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.codec.binary.Hex;
//
//import java.io.*;
//import java.security.GeneralSecurityException;
//import java.security.MessageDigest;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//
//public class Deneme {
//    static String thisHash;
//
//    static class MyExternalSignatureContainer implements ExternalSignatureContainer {
//        protected byte[] sig;
//        public MyExternalSignatureContainer(byte[] sig) {
//            this.sig = sig;
//        }
//        public byte[] sign(InputStream is) {
//            return sig;
//        }
//
//        @Override
//        public void modifySigningDictionary(PdfDictionary signDic) {
//        }
//    }
//
//    static class EmptyContainer implements ExternalSignatureContainer {
//        public EmptyContainer() {
//        }
//        public byte[] sign(InputStream is) {
//            ExternalDigest digest = hashAlgorithm1 -> DigestAlgorithms.getMessageDigest(hashAlgorithm1, null);
//            try {
//                byte[] hash = DigestAlgorithms.digest(is, digest.getMessageDigest("SHA256"));
//
//                thisHash = Hex.encodeHexString(hash);
//
//                return new byte[0];
//            } catch (IOException | GeneralSecurityException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        @Override
//        public void modifySigningDictionary(PdfDictionary pdfDictionary) {
//            pdfDictionary.put(PdfName.FILTER, PdfName.ADOBE_PPKMS);
//            pdfDictionary.put(PdfName.SUBFILTER, PdfName.ADBE_PKCS7_DETACHED);
//        }
//    }
//
//    public static String emptySignature(String src, String dest, String fieldname, Certificate[] chain) throws IOException, DocumentException, GeneralSecurityException {
//        PdfReader reader = new PdfReader(src);
//        FileOutputStream os = new FileOutputStream(dest);
//        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
//
//        Calendar cal = GregorianCalendar.getInstance();
//        cal.add(Calendar.MINUTE, 10);
//
//        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//        appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 1, fieldname);
//        appearance.setCertificate(chain[0]);
//        appearance.setReason("Nice");
//        appearance.setLocation("Delhi");
//        appearance.setSignDate(cal);
//
//        ExternalSignatureContainer external = new EmptyContainer();
//        MakeSignature.signExternalContainer(appearance, external, 8192);
//
//        os.close();
//        reader.close();
//
//        return thisHash;
//    }
//
//    public static Certificate getCert() throws CertificateException, IOException {
//        String cert = "D://dev//tbas//config//root.cer"; // the cert we get from client
//        ByteArrayInputStream userCertificate = new ByteArrayInputStream(Base64.decodeBase64(cert));
//        
//       // CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        
//        File certFile = new File(cert);
//        if (certFile.exists()) {
//            FileInputStream in = new FileInputStream(cert);
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            try {
//                X509Certificate certificate = (X509Certificate) cf.generateCertificate(in);
//                return certificate;
//            } finally {
//                in.close();
//            }
//        } else {
//            return null;
//        }
//
//        
//        
//        //return cf.generateCertificate(userCertificate);
//    }
//
//    private static ExternalDigest getDigest() {
//       return new ExternalDigest() {
//            public MessageDigest getMessageDigest(String hashAlgorithm)
//                    throws GeneralSecurityException {
//                return DigestAlgorithms.getMessageDigest(hashAlgorithm, null);
//            }
//        };
//    }
//
//    public static void createSignature(String src, String dest, String fieldname, byte[] signature) throws IOException, DocumentException, GeneralSecurityException {
//        PdfReader reader = new PdfReader(src);
//        FileOutputStream os = new FileOutputStream(dest);
//        ExternalSignatureContainer external = new MyExternalSignatureContainer(signature);
//        MakeSignature.signDeferred(reader, fieldname, os, external);
//
//        reader.close();
//        os.close();
//    }
//
//    public static void main(String[] args) throws Exception {
//        Certificate cert = getCert();
//        Certificate[] chain = {cert};
//
//        String src = "D:/temp/sample.pdf";
//        String between = "D:/temp/sample_out_between.pdf";
//        String dest = "D:/temp/sample_out.pdf";
//        String fieldName = "sign";
//
//        String hash = emptySignature(src, between, fieldName, chain);
//
//        String signature = "184d48576ab3c1c822da3982319010af94b628fb92131eec";  // signed hash signature we get from client
//        byte[] signatureBytes = Hex.decodeHex(signature.toCharArray());
//
//        PdfPKCS7 sgn = new PdfPKCS7(null, chain, "SHA256", null, getDigest(), false);
//        sgn.setExternalDigest(signatureBytes, null, "RSA");
//
//       byte[] data = sgn.getEncodedPKCS7(Hex.decodeHex(hash.toCharArray()),null, null, null, MakeSignature.CryptoStandard.CMS);
//
//        createSignature(between, dest, fieldName, data);
//        
//        
//        
//    }
//}