package app;

import java.util.Scanner;

/**
 * Имплементация интерфейса Input, которая игнорирует проблемы неправильного ввода пользователем.
 */
public class ConsoleInput implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String askStr(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    @Override
    public int askInt(String question) {
        return Integer.parseInt(askStr(question));
    }

    @Override
    public long askLong(String question) {
        return Long.parseLong(askStr(question));
    }
}
