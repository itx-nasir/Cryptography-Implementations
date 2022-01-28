package ModesOfAES;
/*
 * Purpose of this class is to implement mode named
 * Electronic 
 */

public class ECB extends AESBlockCipher
{
/*
	public ECB()
	{
		RoundKeys=new String [11][4][4];
	}*/
	

	@Override
	public String encrypt(String plainText, String key)
	{
		try {
			validateInput(plainText, key);
			String paddedKey = padKey(key);
			
			String[][] keyMatrix = Utility.StringTo2DArray(Utility.TexttoHEX(paddedKey));
			generateRoundKeys(keyMatrix);
			
			for (String block : splitIntoBlocks(plainText)) {
				String[][] plainTextBlock = Utility.StringTo2DArray(block);
				String[][] cipherBlock = encryptBlock(plainTextBlock, roundKeys[0]);
				cipherText += Utility.HextoText(cipherBlock);
			}
			
			return cipherText;
		} catch (Exception e) {
			throw new AESException("ECB encryption failed", e);
		}
	}

	@Override
	public String decrypt()
	{
		try {
			for (String block : splitIntoBlocks(cipherText)) {
				String[][] cipherBlock = Utility.StringTo2DArray(block);
				String[][] plainTextBlock = decryptBlock(cipherBlock, roundKeys[0]);
				decrypted += Utility.HextoText(plainTextBlock);
			}
			
			return getPlainText();
		} catch (Exception e) {
			throw new AESException("ECB decryption failed", e);
		}
	}
}
