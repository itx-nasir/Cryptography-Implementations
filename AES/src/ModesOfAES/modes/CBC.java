package ModesOfAES;
/*
 * the purpose of the class is to implement 
 * functionality of mode of AES named CIPHER BLOCK CHAINING MODE
 */

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Cipher Block Chaining (CBC) mode implementation of AES.
 * Each block is XORed with the previous ciphertext block before encryption.
 */
public class CBC extends AESBlockCipher /* why extends because it uses the same functionality   of AES
                              */
{                            
	@Override
	public String encrypt(String plainText, String key) {
		try {
			validateInput(plainText, key);
			String paddedKey = padKey(key);
			generateIV();
			
			// Initialize
			String[][] ivMatrix = Utility.StringTo2DArray(iv);
			String[][] keyMatrix = Utility.StringTo2DArray(Utility.TexttoHEX(paddedKey));
			
			// Generate round keys
			generateRoundKeys(keyMatrix);
			
			// Process blocks
			String[][] previousBlock = ivMatrix;
			for (String block : splitIntoBlocks(plainText)) {
				String[][] blockMatrix = Utility.StringTo2DArray(block);
				String[][] xoredBlock = AddRoundKey(blockMatrix, previousBlock);
				String[][] encryptedBlock = encryptBlock(xoredBlock, roundKeys[0]);
				cipherText += Utility.HextoText(encryptedBlock);
				previousBlock = encryptedBlock;
			}
			
			return cipherText;
		} catch (Exception e) {
			throw new AESException("CBC encryption failed", e);
		}
	}

	private String processBlocks(String plainText, String[][] previousBlock) {
		StringBuilder result = new StringBuilder();
		String[] blocks = splitIntoBlocks(plainText);
		
		for (String block : blocks) {
			String[][] blockMatrix = prepareBlock(block);
			String[][] encrypted = encryptBlock(blockMatrix, previousBlock);
			result.append(matrixToHex(encrypted));
			previousBlock = encrypted;
		}
		
		return result.toString();
	}

	@Override
	public String Decryption() /* we have inverse mix column for decryption*/
	{
		   String [][]Mix= {{"0E","0B","0D","09"},
		   					{"09","0E","0B","0D"},
		   					{"0D","09","0E","0B"},
		   					{"0B","0D","09","0E"}
  						   };
		try       /* inverse mix column standard matrix need for decryption*/
		{
			SplitIntoBlocks(CipherText);
			String [][]iv;
			iv=Utility.StringTo2DArray(IV);
			iv=Utility.TransposeMatrix(iv);
			String [][]Resultant;
				
			for(int m=0;m<PT_Blocks.length;m++)
				{
					String [][]PT;
					PT=Utility.StringTo2DArray(PT_Blocks[m]);
					PT=Utility.TransposeMatrix(PT);
					Resultant=PT;
					for(int i=10;i>=1;i--)
					{	Resultant=AddRoundKey(RoundKeys[i],Resultant);	
						if(i!=10)
						{
							Resultant=MixColumn(Mix,Resultant); // mix column
							
						}
						for(int k=0;k<4;k++)
							{
								RightRotate(Resultant[k],k);
							}
						SubstituteByte(Resultant,Inverse_S_Box); // substitute byte
					}
					Resultant=AddRoundKey(RoundKeys[0],Resultant);
					Resultant=AddRoundKey(iv,Resultant);
					Decrypted+=Utility.HextoText(Resultant); // receive the messase in textual form
					iv=Utility.StringTo2DArray(PT_Blocks[m]);
					iv=Utility.TransposeMatrix(iv);
					
				}
			}
		   catch(Exception e)
			{
				System.out.print(e);
			}
			return null;
	}
}
