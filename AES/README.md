# Advanced Encryption Standard (AES) Implementation

A Java implementation of AES-128 with multiple modes of operation, providing secure symmetric encryption capabilities.

## Features

- **AES-128 Core Implementation**
  - 128-bit block size and key length
  - 10 rounds of encryption/decryption
  - Standard S-Box transformations
  - Secure key expansion algorithm

- **Supported Modes of Operation**
  - Electronic Codebook (ECB)
  - Cipher Block Chaining (CBC)
  - Cipher Feedback (CFB)
  - Output Feedback (OFB)
  - Counter Mode (CTR)

## Usage Examples

### Basic Example
```java
// Using CBC Mode (recommended)
AES cipher = new CBC();
String plaintext = "Hello, World!";
String key = "MySecretKey12345";

// Encryption
String ciphertext = cipher.encrypt(plaintext, key);
String iv = cipher.getIV(); // Save IV for decryption

// Decryption
String decrypted = cipher.decrypt();
```AES/README.md

### Mode-Specific Examples

```java
// CBC Mode (Recommended for most uses)
CBC cbcCipher = new CBC();
String encrypted = cbcCipher.encrypt(plaintext, key);

// CTR Mode (For parallel processing)
CTR ctrCipher = new CTR();
String encrypted = ctrCipher.encrypt(plaintext, key);

// CFB Mode (For stream cipher needs)
CFB cfbCipher = new CFB();
String encrypted = cfbCipher.encrypt(plaintext, key);
```

## Security Considerations

### Mode Selection
- CBC: Best for general-purpose encryption
- CTR: Ideal for parallel processing
- CFB/OFB: Good for stream encryption
- ECB: Not recommended for sensitive data

### Key Management
- Use cryptographically secure random keys
- Never reuse keys
- Implement proper key rotation
- Store keys securely

### IV Handling
- Use unique IVs for each encryption
- Store IV with ciphertext
- Never reuse IV-key combinations

## Implementation Details

### Block Structure
- Block Size: 128 bits (16 bytes)
- Key Size: 128 bits (16 bytes)
- Number of Rounds: 10

### Process Flow
1. Key Expansion
   - Generates round keys for all 10 rounds
2. Initial Round
   - AddRoundKey
3. Main Rounds (9x)
   - SubBytes
   - ShiftRows
   - MixColumns
   - AddRoundKey
4. Final Round
   - SubBytes
   - ShiftRows
   - AddRoundKey

## Project Structure

AES/
├── src/
│   └── ModesOfAES/
│       ├── core/
│       │   ├── AES.java           # Base implementation
│       │   ├── AESOperation.java  # Interface
│       │   └── AESBlockCipher.java# Block operations
│       ├── modes/
│       │   ├── CBC.java          # CBC implementation
│       │   ├── CFB.java          # CFB implementation
│       │   ├── CTR.java          # CTR implementation
│       │   ├── ECB.java          # ECB implementation
│       │   └── OFB.java          # OFB implementation
│       └── utils/
│           └── Utility.java       # Helper functions
└── test/
    └── ModesOfAES/
        └── AESTest.java          # Unit tests

## Known Limitations

1. Only supports 128-bit keys
2. No authenticated encryption
3. Manual IV management required
4. Not FIPS certified
5. Academic/learning implementation

## Error Handling

Common exceptions:
- `AESException`: General encryption/decryption errors
- `IllegalArgumentException`: Invalid input parameters
- `SecurityException`: Security-related failures

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- NIST for AES specifications
- Cryptography community
- All contributors and reviewers
