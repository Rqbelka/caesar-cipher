package com.javarush.caesar.core;

import com.javarush.caesar.exception.CaesarException;
import com.javarush.caesar.model.ProcessingResult;
import com.javarush.caesar.service.ValidationService;

public class CaesarCoderDecoder {
    private final ValidationService validationService;
    public static final char[] ALPHABET = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц',
            'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', ' ', '.', ',', '«', '»', '"',
            '\'', ':', '!', '?', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    public CaesarCoderDecoder() {
        this.validationService = new ValidationService();
    }

    public ProcessingResult encodeText(int shift, String text) throws CaesarException {
        validationService.validateTextForEncoding(text);
        validationService.validateShifter(shift);

        String upperText = text.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < upperText.length(); i++) {
            for (int j = 0; j < ALPHABET.length; j++) {
                if (upperText.charAt(i) == ALPHABET[j]) {
                    result.append(ALPHABET[(j + shift) % ALPHABET.length]);
                }
            }
        }
        String encoded = result.toString();
        return new ProcessingResult(true, "Текст успешно закодирован", text, encoded);
    }

    public ProcessingResult decodeText(int shift, String caesarCode) throws CaesarException {
        validationService.validateTextForEncoding(caesarCode);
        validationService.validateShifter(shift);

        String upperText = caesarCode.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < upperText.length(); i++) {
            for (int j = 0; j < ALPHABET.length; j++) {
                if (upperText.charAt(i) == ALPHABET[j]) {
                    result.append(ALPHABET[(j - shift + ALPHABET.length) % ALPHABET.length]);
                }
            }
        }
        String decoded = result.toString();
        return new ProcessingResult(true, "Текст успешно декодирован", caesarCode, decoded);
    }

    public ProcessingResult bruteForce(String caesarCode) throws CaesarException {
        validationService.validateTextForEncoding(caesarCode);

        String upperText = caesarCode.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (int key = 1; key < ALPHABET.length; key++) {
            result.append("Ключ: " + key + " - ");
            for (int i = 0; i < upperText.length(); i++) {
                for (int j = 0; j < ALPHABET.length; j++) {
                    if (upperText.charAt(i) == ALPHABET[j]) {
                        result.append(ALPHABET[(j - key + ALPHABET.length) % ALPHABET.length]);
                    }
                }
            }
            result.append(";");
            result.append("\n");
        }

        String decoded = result.toString();
        return new ProcessingResult(true, "Текст успешно декодирован", caesarCode, decoded);
    }
}



