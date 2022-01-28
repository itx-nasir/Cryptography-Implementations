package ModesOfAES;

public interface AESOperation {
    String encrypt(String plainText, String key);
    String decrypt();
    String getIV();
    String getCipherText();
    String getPlainText();
} 