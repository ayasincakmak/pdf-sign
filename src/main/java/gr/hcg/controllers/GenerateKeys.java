package gr.hcg.controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;

public class GenerateKeys {

    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private KeyStore store;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
    }

    public void createKeys() {
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public static void main(String[] args) throws KeyStoreException, CertificateException {
        GenerateKeys gk;
        try {
            gk = new GenerateKeys(1024);
            KeyStore ks = KeyStore.getInstance("pkcs12");
            gk.createKeys();
            ks.getProvider();
            char[] pwdArray = "password".toCharArray();
            ks.load(null,pwdArray);
            gk.writeToFile("D:/temp/KeyPair/publicKey", gk.getPublicKey().getEncoded());
            gk.writeToFile("D:/temp/KeyPair/privateKey", gk.getPrivateKey().getEncoded());
       //     gk.writeToFile("D:/temp/KeyPair/keystore.p12",ks.ge);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}