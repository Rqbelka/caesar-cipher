package com.javarush.caesar.service;

import com.javarush.caesar.core.CaesarCoderDecoder;
import com.javarush.caesar.exception.CaesarException;

public class ValidationService {
    public void validateTextForEncoding(String text) throws CaesarException {
        if (text == null || text.trim().isEmpty()) {
            throw new CaesarException("Текст для кодирования не может быть пустым");
        }
    }

    public void validateShifter(int shiftNumber) throws CaesarException {
        if (shiftNumber <= 0 || shiftNumber > CaesarCoderDecoder.ALPHABET.length) {
            throw new CaesarException("Номер сдвига не может быть меньше \"0\" или больше длины словаря. Выберите \"4. Справка по алфавиту Цезаря\" и проверьте длину словаря! ");
        }
    }
}
