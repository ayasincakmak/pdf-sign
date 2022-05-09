//package gr.hcg.sign;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.KeyStore;
//
//import sun.misc.BASE64Decoder;
//
//public class Test {
//
//    String p12KeyStore = "MIIGEgIBAzCCBcwGCSqGSIb3DQEHAaCCBb0EggW5MIIFtTCCAyIGCSqGSIb3DQEHAaCCAxMEggMP\n"
//            + "MIIDCzCCAwcGCyqGSIb3DQEMCgECoIICsjCCAq4wKAYKKoZIhvcNAQwBAzAaBBSzUaBB+6XnGBJ8\n"
//            + "647PxKRFxcVm2QICBAAEggKAj9gPGq42/xlqTGvvMGQCDmYFS3cKkfFN6MUTRTg12TxWM1VjdGPX\n"
//            + "xdAj6loc7z2oNo2ith/lNHGJVgiRMWc8JMy2LwYamzXKEN4hNIRBu4NYycGNT4+3UciTSRa3TRN8\n"
//            + "pYXiVrwVooZBD+17oZKVjQCfbefncfOcEKZxV9JFSAvrI5WRREjHH5XicIl7WeLt6YomL9z+ocQf\n"
//            + "JQoTxS3g/vJYHt5OwWWdhlvA3g/KdFpWZGnvfdQvb+oarnlV8+I+5+ymT8/Eb/tUskUiYlVqPuXq\n"
//            + "cKbwS1Lrs+9/ClLhv7Bj8zwtiq+SUkSAOZFtrkSaTPdz7X1R31NiIQcs/fdlOZxLloVJOPEgkoiJ\n"
//            + "sbbBmvDlv5JKnWd5r/9EE0yKPgOnLQPscRYIpKKX+bvvo9lEZrTSLjjNfgsKOADb7mHMg/kqvnxx\n"
//            + "1MoCQt5AN2l+4KKULqTyINZZjtbZZsAfawuKKfo9wTUP689lEQJTjoQUbLyB4A0sElPsHlkxEcgq\n"
//            + "XCgDfgxYrxPzQhoCWQDMRVjUny60hwn+wUy3kGG9AlzPEFBtfKOtoDwzGBmG+vgB59UGFq/TH7Ua\n"
//            + "Q/UcjC8D4VOF1firreTxP2J173qu0QGriFzEpX6XCGDy2idtXXSZJh6kaROTMU582nA9RYuVk0R1\n"
//            + "GJ6iYTSJYzrP/IWtsbVcTqYfX40jwinZ9msuz+QbyklgdQKLzcruZmT/aQo/C13uQTn9NseqyBzl\n"
//            + "C/lwZXYjqKbl1WiXYYeCTCvUvrXmr2RYQmT2ACoN4RvG5yj7K4VrJIklX2J64ADi5pdGuB4r61JA\n"
//            + "TNFG8rBOeUISW1MGi5z0cLnkZjfLyszcTjb6ofdUcDFCMB0GCSqGSIb3DQEJFDEQHg4AdABlAHMA\n"
//            + "dABrAGUAeTAhBgkqhkiG9w0BCRUxFAQSVGltZSAxMjc2MTc2MDk4OTg0MIICiwYJKoZIhvcNAQcG\n"
//            + "oIICfDCCAngCAQAwggJxBgkqhkiG9w0BBwEwKAYKKoZIhvcNAQwBBjAaBBRI657n4ln3ySMdZl0K\n"
//            + "yiggS0zR+wICBACAggI4o4LNjqw7bRqZB1Yuf5edReVd3zkViIVV6WXj2G/s3UtSV3hMFaR01FJ6\n"
//            + "DbEmwFX5GLKfe1PY9la5ygiGtSFdLFP+ybYrBHCYJRityN3UU6SM5j6v7XTAu2C2pEplRU4s9Oyg\n"
//            + "1Nm1cgdRdXUmb8+dyioIq1tiH/YP8w4u8bZGTLPuVzKW9NKAUbDK8CzYmDFs1xX9s1D7iUVyoZP8\n"
//            + "zCxMmiqM5JBGonpAkuoVHbRqOrjFjuuNMX6QeL/2eGQAMH3x8DpBDtj5s/EsGfnHC8j3P0zAM8B9\n"
//            + "0V+qRQIlLkjQvPGFvSuQnaHIPSyeZZ1ZanJ8GcPKocWnPnCAr4fCwYHxhUVyGFDMVLb127TPGk09\n"
//            + "aYc8KDLMH4h6/wu1ALy5+n3DkuXrq6N3hV9VrGDGYHBsLOrCNJEbUUMJQ95wxG3coYWTo41WQbpf\n"
//            + "/d/8dKB9vZr5aq0JtqEmOUQQ/vdKmlJuEUvU2CO7hiJXLQr2nLIRzbq4iNlnqFXnQTcIq0MN/PD+\n"
//            + "JpBD8rSMW8zEvERsxpatU9hg9q4/w3BmYcqybgUMV7+sHFB8wf+7gwoJnJc+BPFI5nQj6+Ym9YDn\n"
//            + "xLUsILEdp3qVXpHaMH8tx0YdWqlrshfxicLcxSkedbChSuZdZtILvm95UU8FugEEXuwTwB+ubeOk\n"
//            + "L8FoNL+r7pAJt/ZXWIh7DrNFse6GvX83IrntxipoLZn4wF7myuQEMoHnuGau1cTmX5mdIN35qhsx\n"
//            + "1ABrKb0xSMPvOnbsTDA9MCEwCQYFKw4DAhoFAAQUS/JTpKWvqt7A4qI1mLj53TB8+x8EFO8n6+hq\n"
//            + "xu5CkKPQGKVyhXQkDAUIAgIEAA==";
//
//    private KeyStore store = null;
//
//    private Test() throws Exception {
//
//        store = KeyStore.getInstance("PKCS12", "SunJSSE");
//        store.load(null, null);
//
//    }
//
//    private void run() throws Exception {
//        InputStream is;
//        String pwd = null;
//
//        byte [] p12 = new BASE64Decoder().decodeBuffer(p12KeyStore);
//        ByteArrayInputStream storeStream = new ByteArrayInputStream(p12);
//        is = storeStream;
//        is.mark(0);
//        
//        //incorrect PIN
//        pwd = "100000";
//        try {
//            store.load(is, pwd.toCharArray());
//            System.out.println("PIN '" + pwd + "' OK");
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error message for PIN '" + pwd + "': " + e.getMessage());
//        }
//        finally {
//            is.reset();
//        }
//        System.out.println();
//
//        //incorrect PIN
//        pwd = "100872";
//        try {
//            store.load(is, pwd.toCharArray());
//            System.out.println("PIN '" + pwd + "' OK");
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error message for PIN '" + pwd + "': " + e.getMessage());
//        }
//        finally {
//            is.reset();
//        }
//        System.out.println();
//
//        //correct PIN
//        pwd = "123456";
//        try {
//            store.load(is, pwd.toCharArray());
//            System.out.println("PIN '" + pwd + "' OK");
//        }
//        catch (IOException e) {
//            System.out.println("Error message for PIN '" + pwd + "': " + e.getMessage());
//        }
//        finally {
//            is.close();
//        }
//    }
//    
//    public static void main(String [] args) {
//        try {
//            (new Test()).run();
//        }
//        catch (Throwable e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//        System.exit(0);
//    }
//}