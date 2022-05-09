package gr.hcg.sign;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.geom.Rectangle2D;
import java.io.*;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.KeyGenerator;

@Component
public class Signer {

    @Value("${signer.keystore.pin}")
    public String keystorePin;

    @Value("${signer.keystore.name}")
    public String keystoreName;

    @Value("${signer.image.name}")
    public String imageName;

    @Value("${signer.tsaurl}")
    public String tsaUrl;

    public static byte[] readBytes(InputStream is ) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();

    }

    public static void setIfNotNull(CreateVisibleSignatureMem signing, String signName, String signLocation, String signReason, String visibleLine1, String visibleLine2, String uuid, String qrcode) {

        if(signName!=null) {
            signing.signatureName = signName;
        }
        if(signLocation!=null) {
            signing.signatureLocation = signLocation;
        }
        if(signReason!=null) {
            signing.signatureReason = signReason;
        }
        if(visibleLine1!=null) {
            signing.visibleLine1 = visibleLine1;
        }
        if(visibleLine2!=null) {
            signing.visibleLine2 = visibleLine2;
        }
        if(uuid!=null) {
            signing.uuid = uuid;
        }
//        if(qrcode!=null) {
//            signing.qrcode = qrcode;
//        }
    }
    
    public static byte[] fromHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


    public Calendar sign(InputStream is, OutputStream os, String signName, String signLocation, String signReason, String visibleLine1, String visibleLine2, String uuid, String qrcode) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {

        
//        KeyStore keyStore = KeyStore.getInstance("PKCS12");
//        keyStore.load(null, null);
//         
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(128);
//        Key key = keyGen.generateKey();
//        keyStore.setKeyEntry("secret", key, "password".toCharArray(), null);
//         
//        keyStore.store(new FileOutputStream("C:\\\\Users\\\\TCACAKMAK\\\\Desktop\\\\itex_calismasi\\output.p12"), "password".toCharArray());
//        
        testKeyStore();
        InputStream ksInputStream = new FileInputStream("C:\\Users\\TCACAKMAK\\Desktop\\itex_calismasi\\outstore.pkcs12");
        keystorePin="secret";
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        char[] pin = keystorePin.toCharArray();
        keystore.load(ksInputStream, pin);

        CreateVisibleSignatureMem signing = new CreateVisibleSignatureMem(keystore, pin.clone());
        setIfNotNull(signing, signName, signLocation, signReason, visibleLine1, visibleLine2, uuid, qrcode);

        InputStream imageResource = new FileInputStream(imageName);
       signing.setImageBytes(readBytes(imageResource));

        return signing.signPDF(is, os, tsaUrl, "Signature1");

    }

    
    public static void testKeyStore() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            
            PrivateKey privateKey = keyPair.getPrivate();
            Certificate[] outChain = { createCertificate("CN=CA", publicKey, privateKey) };

            KeyStore outStore = KeyStore.getInstance("PKCS12");
            outStore.getProvider();
            outStore.load(null, "secret".toCharArray());
            outStore.setKeyEntry("mykey", privateKey, "secret".toCharArray(), outChain);            
            OutputStream outputStream = new FileOutputStream("D:/outstore.pkcs12");
            outStore.store(outputStream, "secret".toCharArray());
            outputStream.flush();
            outputStream.close();

            KeyStore inStore = KeyStore.getInstance("PKCS12");      
            inStore.load(new FileInputStream("D:/outstore.pkcs12"), "secret".toCharArray());
            Key key = outStore.getKey("myKey", "secret".toCharArray());
           // assertEquals(privateKey, key);

            Certificate[] inChain = outStore.getCertificateChain("mykey");
           // assertNotNull(inChain);
            //assertEquals(outChain.length, inChain.length);
        } catch (Exception e) {
            e.printStackTrace();
            //fail(e.getMessage());
        }
    }

    private static X509Certificate createCertificate(String dn, PublicKey publicKey, PrivateKey privateKey) throws Exception {
        
        X509V3CertificateGenerator generator = new X509V3CertificateGenerator();
        generator.setSerialNumber(new BigInteger("1"));
        generator.setIssuerDN(new X509Name(dn));
        generator.setSubjectDN(new X509Name(dn));
        generator.setNotBefore(Calendar.getInstance().getTime());
        
        Calendar ten_year_later = Calendar.getInstance();
        ten_year_later.setTime(new Date()); 
        ten_year_later.add(Calendar.YEAR, 1);
        
        generator.setNotAfter(ten_year_later.getTime());
        generator.setPublicKey(publicKey);
        generator.setSignatureAlgorithm("SHA256WithRSAEncryption");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
      //  generator.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(isCA));
        generator.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(160));
        generator.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth)); 
//        if (generalNames != null) {
//            generator.addExtension(X509Extensions.SubjectAlternativeName, false, generalNames);
//        }
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        return generator.generateX509Certificate(privateKey, "BC");
        
//        X509V3CertificateGenerator certGenerator = new X509V3CertificateGenerator();
//        certGenerator.setSerialNumber(new BigInteger("1"));
//        certGenerator.setIssuerDN(new X509Name(dn));
//        certGenerator.setSubjectDN(new X509Name(dn));
//        certGenerator.setNotBefore(Calendar.getInstance().getTime());
//        certGenerator.setNotAfter(Calendar.getInstance().getTime());
//        certGenerator.setPublicKey(publicKey);
//        certGenerator.setSignatureAlgorithm("SHA1withRSA");
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        X509Certificate certificate = (X509Certificate)certGenerator.generate(privateKey,"BC");
//        return certificate;
    }
    
}
