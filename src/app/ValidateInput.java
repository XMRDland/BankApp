package app;

import java.util.Scanner;

/**
 * Этот класс - обновлённая версия консольного ввода.
 * Главная задача данного класса решить проблему ошибок ввода пользователем.
 */
public class ValidateInput extends ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String askStr(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    @Override
    public int askInt(String question) {
        try {
            return Integer.parseInt(askStr(question));
        } catch (NumberFormatException e) {}
        return -1;
    }

    @Override
    public long askLong(String question) {
        try {
            return Long.parseLong(askStr(question));
        } catch (NumberFormatException e) {}
        return -1;
    }
}
