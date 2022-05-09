//package gr.hcg.check;
//
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Rectangle;
//import com.lowagie.text.pdf.PdfName;
//import com.lowagie.text.pdf.PdfPKCS7;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfSigGenericPKCS;
//import com.lowagie.text.pdf.PdfSignatureAppearance;
//import com.lowagie.text.pdf.PdfStamper;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.security.GeneralSecurityException;
//import java.security.KeyFactory;
//import java.security.MessageDigest;
//import java.security.PrivateKey;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.util.Base64;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//
//import org.apache.pdfbox.util.Hex;
//
//public class Deneme2 {
//    static String thisHash;
//
////    static class MyExternalSignatureContainer implements ExternalSignatureContainer {
////        protected byte[] sig;
////        public MyExternalSignatureContainer(byte[] sig) {
////            this.sig = sig;
////        }
////        public byte[] sign(InputStream is) {
////            return sig;
////        }
////
////        @Override
////        public void modifySigningDictionary(PdfDictionary signDic) {
////        }
////    }
////
////    static class EmptyContainer implements ExternalSignatureContainer {
////        public EmptyContainer() {
////        }
////        public byte[] sign(InputStream is) {
////            ExternalDigest digest = hashAlgorithm1 -> DigestAlgorithms.getMessageDigest(hashAlgorithm1, null);
////            try {
////                byte[] hash = DigestAlgorithms.digest(is, digest.getMessageDigest("SHA256"));
////
////                thisHash = Hex.encodeHexString(hash);
////
////                return new byte[0];
////            } catch (IOException | GeneralSecurityException e) {
////                throw new RuntimeException(e);
////            }
////        }
////
////        @Override
////        public void modifySigningDictionary(PdfDictionary pdfDictionary) {
////            pdfDictionary.put(PdfName.FILTER, PdfName.ADOBE_PPKMS);
////            pdfDictionary.put(PdfName.SUBFILTER, PdfName.ADBE_PKCS7_DETACHED);
////        }
////    }
//    
//    static class Test1 extends PdfSigGenericPKCS {
//
//        public Test1(com.lowagie.text.pdf.PdfName filter, com.lowagie.text.pdf.PdfName subFilter) {
//            super(filter,subFilter);
//            // TODO Auto-generated constructor stub
//            super.hashAlgorithm="SHA-256";
//        }
//        
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
//      //  appearance.setCertificate(chain[0]);
//        appearance.setReason("Nice");
//        appearance.setLocation("Delhi");
//        appearance.setSignDate(cal);
//
//        //ExternalSignatureContainer external = new EmptyContainer();
//      //  MakeSignature.signExternalContainer(appearance, external, 8192);
//
//        os.close();
//        reader.close();
//
//        
//        
//        return thisHash;
//    }
//
//    public static Certificate getCert() throws CertificateException, IOException {
//        
//        //String certB64 = "MIIHFDCCBfygAwIBAgIIK2o4sL7KHQgwDQYJKoZIhvcNAQELBQAwSTELMAkGA1UEBhMCVVMxEzARBgNVBAoTCkdvb2dsZSBJbmMxJTAjBgNVBAMTHEdvb2dsZSBJbnRlcm5ldCBBdXRob3JpdHkgRzIwHhcNMTYxMjE1MTQwNDE1WhcNMTcwMzA5MTMzNTAwWjBmMQswCQYDVQQGEwJVUzETMBEGA1UECAwKQ2FsaWZvcm5pYTEWMBQGA1UEBwwNTW91bnRhaW4gVmlldzETMBEGA1UECgwKR29vZ2xlIEluYzEVMBMGA1UEAwwMKi5nb29nbGUuY29tMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEG1y99TYpFSSiawnjJKYI8hyEzJ4M+IELfLjmSsYI7fW/V8AT61quCswtBMikJYqzYBZrV2Reu5sHlLr6936cR6OCBKwwggSoMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjCCA2sGA1UdEQSCA2IwggNeggwqLmdvb2dsZS5jb22CDSouYW5kcm9pZC5jb22CFiouYXBwZW5naW5lLmdvb2dsZS5jb22CEiouY2xvdWQuZ29vZ2xlLmNvbYIWKi5nb29nbGUtYW5hbHl0aWNzLmNvbYILKi5nb29nbGUuY2GCCyouZ29vZ2xlLmNsgg4qLmdvb2dsZS5jby5pboIOKi5nb29nbGUuY28uanCCDiouZ29vZ2xlLmNvLnVrgg8qLmdvb2dsZS5jb20uYXKCDyouZ29vZ2xlLmNvbS5hdYIPKi5nb29nbGUuY29tLmJygg8qLmdvb2dsZS5jb20uY2+CDyouZ29vZ2xlLmNvbS5teIIPKi5nb29nbGUuY29tLnRygg8qLmdvb2dsZS5jb20udm6CCyouZ29vZ2xlLmRlggsqLmdvb2dsZS5lc4ILKi5nb29nbGUuZnKCCyouZ29vZ2xlLmh1ggsqLmdvb2dsZS5pdIILKi5nb29nbGUubmyCCyouZ29vZ2xlLnBsggsqLmdvb2dsZS5wdIISKi5nb29nbGVhZGFwaXMuY29tgg8qLmdvb2dsZWFwaXMuY26CFCouZ29vZ2xlY29tbWVyY2UuY29tghEqLmdvb2dsZXZpZGVvLmNvbYIMKi5nc3RhdGljLmNugg0qLmdzdGF0aWMuY29tggoqLmd2dDEuY29tggoqLmd2dDIuY29tghQqLm1ldHJpYy5nc3RhdGljLmNvbYIMKi51cmNoaW4uY29tghAqLnVybC5nb29nbGUuY29tghYqLnlvdXR1YmUtbm9jb29raWUuY29tgg0qLnlvdXR1YmUuY29tghYqLnlvdXR1YmVlZHVjYXRpb24uY29tggsqLnl0aW1nLmNvbYIaYW5kcm9pZC5jbGllbnRzLmdvb2dsZS5jb22CC2FuZHJvaWQuY29tghtkZXZlbG9wZXIuYW5kcm9pZC5nb29nbGUuY26CBGcuY2+CBmdvby5nbIIUZ29vZ2xlLWFuYWx5dGljcy5jb22CCmdvb2dsZS5jb22CEmdvb2dsZWNvbW1lcmNlLmNvbYIKdXJjaGluLmNvbYIKd3d3Lmdvby5nbIIIeW91dHUuYmWCC3lvdXR1YmUuY29tghR5b3V0dWJlZWR1Y2F0aW9uLmNvbTALBgNVHQ8EBAMCB4AwaAYIKwYBBQUHAQEEXDBaMCsGCCsGAQUFBzAChh9odHRwOi8vcGtpLmdvb2dsZS5jb20vR0lBRzIuY3J0MCsGCCsGAQUFBzABhh9odHRwOi8vY2xpZW50czEuZ29vZ2xlLmNvbS9vY3NwMB0GA1UdDgQWBBThPf/3oDfxFM/hdOi5kLv8qrZbsjAMBgNVHRMBAf8EAjAAMB8GA1UdIwQYMBaAFErdBhYbvPZotXb1gba7Yhq6WoEvMCEGA1UdIAQaMBgwDAYKKwYBBAHWeQIFATAIBgZngQwBAgIwMAYDVR0fBCkwJzAloCOgIYYfaHR0cDovL3BraS5nb29nbGUuY29tL0dJQUcyLmNybDANBgkqhkiG9w0BAQsFAAOCAQEAWZQy0Kvn9cPnIh7Z4kfUCXX/dhdvjLJYFAn3b3d5DVs1BLYuukfIjilVdAeTUHZH7TLn/uVejg3yS0ssRg1ds1iv2O9DJbnl5FHcjNAvwfN533FulWP41OC6B6dC6BGGTXTvQobDup7/EKg1GWX9ksBtTfKLH5wrjhN955Itnd25Sjw2bSjLaWEtTrjINXmnBoc2+qHFzF/fNxK1KbmkBboUIGoaGsThe3AF0Ye+XAeaZH08+GdrorknlHDQLLtHIcJ3C6PrQ/kTpwWd/TVXW42BN+N7xZiGJbvKOg0S0rk2hzhgX4QoUKZHMqqh1sS6ypkfnWx75nh325y4Tenk+A==";
//        //byte encodedCert[] = Base64.getDecoder().decode(certB64);
//        
//        String cert = "D://dev//tbas//config//root.cer"; // the cert we get from client
//        ByteArrayInputStream userCertificate = new ByteArrayInputStream(Base64.getDecoder().decode(cert));
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
////    private static ExternalDigest getDigest() {
////       return new ExternalDigest() {
////            public MessageDigest getMessageDigest(String hashAlgorithm)
////                    throws GeneralSecurityException {
////                return DigestAlgorithms.getMessageDigest(hashAlgorithm, null);
////            }
////        };
////    }
//
//    public static void createSignature(String src, String dest, String fieldname, byte[] signature) throws IOException, DocumentException, GeneralSecurityException {
//        PdfReader reader = new PdfReader(src);
//        FileOutputStream os = new FileOutputStream(dest);
//        //ExternalSignatureContainer external = new MyExternalSignatureContainer(signature);
//        //MakeSignature.signDeferred(reader, fieldname, os, external);
//
//        reader.close();
//        os.close();
//    }
//
//    
//    public static PrivateKey getPrivate(String filename) throws Exception {
//        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        return kf.generatePrivate(spec);
//    }
//
//    
//    public static void main(String[] args) throws Exception {
//        Certificate cert = getCert();
//        Certificate[] chain = {cert};
//
//        String src = "D:/temp/sample.pdf";
//        String between = "D:/temp/sample_out_between.pdf";
//        String dest = "D:/temp/sample_out.pdf";
//        String fieldName = "sign";
//        String hash = emptySignature(src, between, fieldName, chain);
//
//        String signature = "184d48576ab3c1c822da3982319010af94b628fb92131eec";  // signed hash signature we get from client
//        byte[] signatureBytes = Hex.decodeBase64(signature);
////
//        PdfPKCS7 sgn = new PdfPKCS7(null, null);
//        sgn.setExternalDigest(signatureBytes, null, "RSA");
////
////       byte[] data = sgn.getEncodedPKCS7(Hex.decodeHex(hash.toCharArray()),null, null, null, MakeSignature.CryptoStandard.CMS);
////
////        createSignature(between, dest, fieldName, data);
////        
//        
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        PKCS8EncodedKeySpec keysp = new PKCS8EncodedKeySpec("TEST".getBytes());
//        PrivateKey clientPrivateKey =getPrivate("D:/temp/KeyPair/PrivateKey");
//        
//        
//        PdfReader reader = new PdfReader(src);
//        FileOutputStream os = new FileOutputStream(dest);
//        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
////        PdfStamper  stp = PdfStamper.createSignature(reader, os, '\0', null, true);
//        PdfSignatureAppearance sap = stamper.getSignatureAppearance();
//        
//        //Test1.g
//        
//        //MakeSignature.signDetached(sap, getDigest(), null, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
//
//        Test1 t1= new Test1(new com.lowagie.text.pdf.PdfName("Adobe.PPKLite"), new com.lowagie.text.pdf.PdfName("adbe.pkcs7.detached"));
//        
//        t1.setSignInfo(clientPrivateKey, chain, null);
//        
//        //pkcs = new PdfPKCS7(privKey, certChain, crlList, hashAlgorithm, provider, PdfName.ADBE_PKCS7_SHA1.equals(get(PdfName.SUBFILTER)));
//        //pkcs.setExternalDigest(externalDigest, externalRSAdata, digestEncryptionAlgorithm);
//        
//    }
//}