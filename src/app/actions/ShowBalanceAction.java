package app.actions;

import app.*;
import java.util.logging.Logger;

/**
 * Класс для реализации действия "Показать баланс", используется в StartUI.
 */
public class ShowBalanceAction implements UserAction {

    @Override
    public String getTitle() {
        return "Показать баланс";
    }

    @Override
    public boolean execute(BankService bankService, Input input, String requisite, Logger logger) {
        System.out.println("Ваш баланс: " + bankService.balance(requisite, logger)/100);
        return true;
    }
}
