package ModesOfAES;

abstract class AESBlockCipher extends AES {
    protected String[][] encryptBlock(String[][] block, String[][] key) {
        String[][] result = block;
        
        // Initial round
        result = AddRoundKey(result, roundKeys[0]);
        
        // Main rounds
        for (int round = 1; round <= NUM_ROUNDS; round++) {
            SubstituteByte(result, S_Box);
            shiftRows(result);
            if (round != NUM_ROUNDS) {
                result = MixColumn(MIX_COLUMNS_MATRIX, result);
            }
            result = AddRoundKey(result, roundKeys[round]);
        }
        
        return result;
    }

    protected String[][] decryptBlock(String[][] block, String[][] key) {
        String[][] result = block;
        
        // Reverse final round
        result = AddRoundKey(result, roundKeys[NUM_ROUNDS]);
        
        // Reverse main rounds
        for (int round = NUM_ROUNDS - 1; round >= 0; round--) {
            SubstituteByte(result, Inverse_S_Box);
            inverseShiftRows(result);
            if (round != 0) {
                result = inverseMixColumn(result);
            }
            result = AddRoundKey(result, roundKeys[round]);
        }
        
        return result;
    }
} 