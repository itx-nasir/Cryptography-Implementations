package ModesOfAES;

public class CFB extends AESBlockCipher {
    @Override
    public String encrypt(String plainText, String key) {
        try {
            validateInput(plainText, key);
            String paddedKey = padKey(key);
            generateIV();
            
            // Initialize
            String[][] ivMatrix = Utility.StringTo2DArray(iv);
            String[][] keyMatrix = Utility.StringTo2DArray(Utility.TexttoHEX(paddedKey));
            generateRoundKeys(keyMatrix);
            
            String[][] feedback = ivMatrix;
            for (String block : splitIntoBlocks(plainText)) {
                String[][] encryptedFeedback = encryptBlock(feedback, roundKeys[0]);
                String[][] plainTextBlock = Utility.StringTo2DArray(block);
                String[][] cipherBlock = AddRoundKey(plainTextBlock, encryptedFeedback);
                
                cipherText += Utility.HextoText(cipherBlock);
                feedback = cipherBlock;
            }
            
            return cipherText;
        } catch (Exception e) {
            throw new AESException("CFB encryption failed", e);
        }
    }

    @Override
    public String decrypt() {
        try {
            String[][] feedback = Utility.StringTo2DArray(iv);
            
            for (String block : splitIntoBlocks(cipherText)) {
                String[][] encryptedFeedback = encryptBlock(feedback, roundKeys[0]);
                String[][] cipherBlock = Utility.StringTo2DArray(block);
                String[][] plainTextBlock = AddRoundKey(cipherBlock, encryptedFeedback);
                
                decrypted += Utility.HextoText(plainTextBlock);
                feedback = cipherBlock;
            }
            
            return getPlainText();
        } catch (Exception e) {
            throw new AESException("CFB decryption failed", e);
        }
    }
}
