package app;

import app.actions.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Класс, который запускает общение с пользователем.
 */
public class StartUI {
    /**
     * Инициализация меню
     */
    public void init(BankService bankService, UserAction[] actions, Input input, Logger logger) {
        String requisite = authorization(bankService, input, logger);
        showMenu(actions);
        boolean run = true;
        while (run) {
            int select = input.askInt("Выберите пункт меню: ");
            // Здесь такой if, который не даст выйти в ArrayIndexOutOfBoundsException.
            if (select >= 0 && select <= actions.length - 1) {
                // Мы по индексу массива вызываем метод execute нашего Action-объекта.
                run = actions[select].execute(bankService, input, requisite, logger);
            } else {
                System.out.println("Такого пункта нет...");
            }
        }
    }

    /**
     * Метод должен работать пока пользователь не авторизуется.
     *
     * @param bankService BankService объект.
     * @param input Input объект.
     * @return возвращает реквизиты аккаунта, под которым авторизовался пользователь.
     *         Получайте их вызывом метода getRequisiteIfPresent, класса BankService.
     */
    private String authorization(BankService bankService, Input input, Logger logger) {
        String rsl = null;
        boolean authComplete = false;
        while (!authComplete) {
            /*
             * Запрашиваете у пользователя логин, пароль пока он не пройдёт авторизацию.
             * Авторизация пройдена при условии, что в BankService есть пользователь с
             * данным логином и паролем.
             */
            String login = input.askStr("Введите логин: ");
            String password = input.askStr("Введите пароль: ");
            if (bankService.getRequisiteIfPresent(login, password, logger).isPresent()) {
                authComplete = true;
                rsl = bankService.getRequisiteIfPresent(login, password, logger).get();
            } else {
                System.out.println("Неверные данные.");
            }
        }
        return rsl;
    }

    /**
     * Печатается меню пользователя (только печатается, общения с пользователем нет).
     */
    private void showMenu(UserAction[] actions) {
        System.out.println("Меню:");
        for (int index = 0; index < actions.length; index++) {
            System.out.println(index + ". " + actions[index].getTitle());
        }
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(BankService.class.getName());
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("LogFile.log");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e){
            logger.log(Level.SEVERE, "Ошибка создания файла.", e);
        }

        BankService bankService = new BankService();
        bankService.addAccount(new BankAccount("Alice", "3475", "123"));
        bankService.addAccount(new BankAccount("Olga", "2566", "456"));
        bankService.addAccount(new BankAccount("Roman", "1657", "789"));
        bankService.addAccount(new BankAccount("Elza", "0748", "098"));;

        // В массиве хранятся объекты, которые представляют действия пользователя.
        UserAction[] actions = {
                new ShowBalanceAction(),
                new TopUpBalanceAction(),
                new TransferToAction(),
                new Exit()
        };

        Input input = new ValidateInput();
        // Запускаем наш UI передавая аргументами банковский сервис, экшены и Input.
        new StartUI().init(bankService, actions, input, logger);
    }
}
