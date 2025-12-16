package com.javarush.caesar;

import com.javarush.caesar.core.CaesarCoderDecoder;
import com.javarush.caesar.exception.CaesarException;
import com.javarush.caesar.model.ProcessingResult;
import com.javarush.caesar.service.FileService;

import java.util.Scanner;

public class CaesarApp {
    private final CaesarCoderDecoder caesarCoder;
    private final FileService fileService;
    private final Scanner scanner;

    public CaesarApp() {
        this.caesarCoder = new CaesarCoderDecoder();
        this.fileService = new FileService();
        this.scanner = new Scanner(System.in);
    }


    public static void main(String[] args) {
        CaesarApp app = new CaesarApp();
        app.run();
    }

    public void run() {
        printWelcomeMessage();
        while (true) {
            showMainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    processEncodeFile();
                    break;
                case "2":
                    processDecodeFile();
                    break;
                case "3":
                    processBruteForce();
                    break;
                case "4":
                    showAlphabetInfo();
                    break;
                case "0":
                    System.out.println("\nДо свидания!");
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор! Попробуйте снова.");
            }
        }
    }

    private void printWelcomeMessage() {
        System.out.println("         КОД ЦЕЗАРЯ");
        System.out.println("Профессиональный кодер-декодер");
        System.out.println("=".repeat(30));

    }

    private void showMainMenu() {
        System.out.println("ГЛАВНОЕ МЕНЮ: ");
        System.out.println("1. Закодировать текст");
        System.out.println("2. Декодировать текст");
        System.out.println("3. Декодировать методом Brute Force");
        System.out.println("4. Справка по алфавиту");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void processEncodeFile() {
        System.out.println("Кодирование файла:");
        try {
            String inputFile = getInputFilePath();
            String outputFile = getOutputFilePath();

            System.out.print("Введите номер сдвига:");
            int shiftNumber = Integer.parseInt(scanner.nextLine());

            String context = fileService.readFile(inputFile);
            ProcessingResult result = caesarCoder.encodeText(shiftNumber, context);
            fileService.writeFile(result.getOutput(), outputFile);

            displaySuccessResult(result, inputFile, outputFile);

        } catch (CaesarException e) {
            displayError(e.getMessage());
        }
    }

    private void processDecodeFile() {
        System.out.println("\n ДЕКОДИРОВАНИЕ ФАЙЛА");

        try {
            String inputFile = getInputFilePath();
            String outputFile = getOutputFilePath();

            System.out.print("Введите номер сдвига:");
            int shiftNumber = Integer.parseInt(scanner.nextLine());

            String content = fileService.readFile(inputFile);
            ProcessingResult result = caesarCoder.decodeText(shiftNumber, content);
            fileService.writeFile(result.getOutput(), outputFile);

            displaySuccessResult(result, inputFile, outputFile);

        } catch (CaesarException e) {
            displayError(e.getMessage());
        }
    }
    private void processBruteForce() {
        System.out.println("\n ДЕКОДИРОВАНИЕ ФАЙЛА МЕТОДОМ BRUTE FORCE");

        try {
            String inputFile = getInputFilePath();
            String outputFile = getOutputFilePath();

            String content = fileService.readFile(inputFile);
            ProcessingResult result = caesarCoder.bruteForce(content);
            fileService.writeFile(result.getOutput(), outputFile);

            displaySuccessResult(result, inputFile, outputFile);

        } catch (CaesarException e) {
            displayError(e.getMessage());
        }
    }

    private String getInputFilePath() {
        System.out.print("Введите путь к исходному файлу (с кодом Цезаря) и его имя: ");
        return scanner.nextLine().trim();
    }

    private String getOutputFilePath() {
        System.out.print("Введите путь для результата и имя файла в который запишем результат: ");
        return scanner.nextLine().trim();
    }

    private void displaySuccessResult(ProcessingResult result, String inputFile, String outputFile) {
        System.out.println("\n " + result.getMessage());
        System.out.println("Исходный файл: " + inputFile);
        System.out.println("Результат записан в: " + outputFile);

        System.out.println("\n Превью:");
        System.out.println("Вы ввели: " + getPreview(result.getInput()));
        System.out.println("Результат: " + getPreview(result.getOutput()));
    }
    public String getPreview(String text) {
        if (text.length() <= 100) {
            return text;
        }
        return text.substring(0, 97) + "...";
    }

    private void displayError(String message) {
        System.out.println("\n Ошибка: " + message + "\n");
    }

    private void showAlphabetInfo() {
        System.out.println("\n    АЛФАВИТ");
        System.out.println("────────────────");
        System.out.println("\nДЛИНА СЛОВАРЯ:" + CaesarCoderDecoder.ALPHABET.length + " БУКВ, ЦИФР, СИМВОЛОВ И ЗНАКОВ ПРЕПИНАНИЯ");

        System.out.println("\nРУССКИЕ БУКВЫ:");
        int count = 0;
        for (char entry : CaesarCoderDecoder.ALPHABET) {
            if (entry >= 'А' && entry <= 'Я') {
                System.out.printf("%c, ", entry);
                count++;
            }
        }
        System.out.println("\nВ словаре всего " + count + " русских букв");

        System.out.println("\nЦИФРЫ:");
        count = 0;
        for (char entry : CaesarCoderDecoder.ALPHABET) {
            if (entry >= '0' && entry <= '9') {
                System.out.printf("%c, ", entry);
                count++;
            }
        }
        System.out.println("\nВ словаре всего " + count + " цифр");

        System.out.println("\nЗНАКИ ПРЕПИНАНИЯ И СИМВОЛЫ:");
        count = 0;
        for (char entry : CaesarCoderDecoder.ALPHABET) {
            if (!(entry >= 'А' && entry <= 'Я') && !(entry >= '0' && entry <= '9')) {
                System.out.printf("%c, ", entry);
                count++;
            }
        }
        System.out.println("\nВ словаре всего " + count + " знаков препинания и символов, включая пробел");

        System.out.println("\nПример кодирования со сдвигом в 1 букву:");
        System.out.println("  'ПРИВЕТ' → РСЙГЖУ");
        System.out.println("\nПравила:");
        System.out.println("• Поддерживаются русские буквы, цифры, базовые знаки препинания");
    }
}
