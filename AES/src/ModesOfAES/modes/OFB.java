package ModesOfAES;

public class OFB extends AESBlockCipher
{
	@Override
	public String encrypt(String plainText, String key)
	{
		try
		{
			validateInput(plainText, key);
			String paddedKey = padKey(key);
			generateIV();
			
			String[][] keyMatrix = Utility.StringTo2DArray(Utility.TexttoHEX(paddedKey));
			generateRoundKeys(keyMatrix);
			
			String[][] feedback = Utility.StringTo2DArray(iv);
			
			for (String block : splitIntoBlocks(plainText))
			{
				feedback = encryptBlock(feedback, roundKeys[0]);
				String[][] plainTextBlock = Utility.StringTo2DArray(block);
				String[][] cipherBlock = AddRoundKey(plainTextBlock, feedback);
				cipherText += Utility.HextoText(cipherBlock);
			}
			
			return cipherText;
		}
		catch (Exception e)
		{
			throw new AESException("OFB encryption failed", e);
		}
	}

	@Override
	public String decrypt()
	{
		// OFB mode encryption and decryption are identical
		return encrypt(cipherText, getKeyFromRoundKeys());
	}
}
