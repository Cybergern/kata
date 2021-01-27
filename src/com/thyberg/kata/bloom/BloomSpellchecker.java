package com.thyberg.kata.bloom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BloomSpellchecker {

    public static final int HASH_COUNT = 10;
    public static final int HASH_SIZE = 10;
    public static final int BITSET_SIZE = 10000000;
    private final List<byte[]> hashes = new ArrayList<>();
    private static final String FILE_NAME = "data/wordlist.txt";

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        List<String> lines = readFileToLines(FILE_NAME);
        BloomSpellchecker spellchecker = new BloomSpellchecker();
        BitSet bits = spellchecker.makeInitialBitset(lines);
        List<String> correct_words = new ArrayList<>();
        while (correct_words.size() < 10) {
            String generatedString = getRandomString();
            if(spellchecker.checkWord(generatedString, bits)) {
                correct_words.add(generatedString);
            }
        }
        System.out.println(correct_words);
    }

    private boolean checkWord(String word, BitSet bits) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        for (byte[] hash : hashes) {
            if (!bits.get(this.getBitPosition(md, word, hash))) {
                return false;
            }
        }
        return true;
    }

    private static String getRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        int targetStringLength = 5;

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static List<String> readFileToLines(String file_name) {
        List<String> result = Collections.emptyList();
        try (Stream<String> lines = Files.lines(Paths.get(file_name), StandardCharsets.ISO_8859_1)) {
            result = lines.map(String::trim).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public BitSet makeInitialBitset(List<String> words) throws NoSuchAlgorithmException, IOException {
        for (int i=0; i < HASH_COUNT; i++) {
            byte[] random_hash = new byte[HASH_SIZE];
            new Random().nextBytes(random_hash);
            this.hashes.add(random_hash);
        }
        BitSet bits = new BitSet(BITSET_SIZE);
        MessageDigest md = MessageDigest.getInstance("MD5");
        for (String word : words) {
            for (byte[] hash : hashes) {
                bits.set(getBitPosition(md, word, hash));
            }
        }
        return bits;
    }

    private int getBitPosition(MessageDigest md, String word, byte[] hash) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(word.getBytes(StandardCharsets.ISO_8859_1));
        output.write(hash);
        md.update(output.toByteArray());
        return Math.abs(Arrays.hashCode(md.digest()) % BITSET_SIZE);
    }
}
