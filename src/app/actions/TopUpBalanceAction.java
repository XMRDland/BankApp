package app.actions;

import app.*;
import java.util.logging.Logger;

/**
 * Класс для реализации действия "Пополнить баланс", используется в StartUI.
 */
public class TopUpBalanceAction implements UserAction {

    @Override
    public String getTitle() {
        return "Пополнить баланс";
    }

    @Override
    public boolean execute(BankService bankService, Input input, String requisite, Logger logger) {
        long amount = 0L;
        boolean isCorrect = false;
        while (!isCorrect) {
            amount = input.askLong("Введите сумму: ");
            if (amount < 0) {
                System.out.println("Неверные данные.");
            } else {
                isCorrect = true;
            }
        }
        bankService.topUpBalance(requisite, amount, logger);
        System.out.println("Успешно! Текущий баланс: " + bankService.balance(requisite, logger)/100);
        return true;
    }
}
