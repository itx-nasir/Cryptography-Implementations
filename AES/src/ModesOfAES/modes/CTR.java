package ModesOfAES;

import java.math.BigInteger;

public class CTR extends AESBlockCipher
{
	
	
	
	public String IncrementCounter(String counter)
	{	 
		  BigInteger one, UpdatedCounter;
	      one = new BigInteger("1");
	      UpdatedCounter = new BigInteger(counter, 16);
	      UpdatedCounter=UpdatedCounter.add(one);
	      return(""+UpdatedCounter);
	}
	
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
			
			BigInteger counter = new BigInteger(iv, 16);
			
			for (String block : splitIntoBlocks(plainText))
			{
				String[][] counterBlock = Utility.StringTo2DArray(counter.toString(16));
				String[][] encryptedCounter = encryptBlock(counterBlock, roundKeys[0]);
				
				String[][] plainTextBlock = Utility.StringTo2DArray(block);
				String[][] cipherBlock = AddRoundKey(plainTextBlock, encryptedCounter);
				
				cipherText += Utility.HextoText(cipherBlock);
				counter = counter.add(BigInteger.ONE);
			}
			
			return cipherText;
		}
		catch(Exception e)
		{
			throw new AESException("CTR encryption failed", e);
		}
	}

	@Override
	public String decrypt()
	{
		// CTR mode encryption and decryption are identical
		return encrypt(cipherText, getKeyFromRoundKeys());
	}
}
