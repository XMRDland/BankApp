package app.actions;

import app.*;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Класс для реализации действия "Перевести средства", используется в StartUI.
 */
public class TransferToAction implements UserAction {

    @Override
    public String getTitle() {
        return "Перевести средства";
    }

    /**
     * Перевод средств.
     */
    @Override
    public boolean execute(BankService bankService, Input input, String requisite, Logger logger) {
        String username = bankService.getNameByRequisite(requisite, logger).get();
        //валидация получателя
        boolean isDestCorrect = false;
        Optional<String> destOptional = Optional.of("");
        while (!isDestCorrect) {
            destOptional = bankService.getRequisiteByName(input.askStr("Введите имя получателя: "), logger);
            if (destOptional.isPresent()) {
                isDestCorrect = true;
            } else {
                System.out.println("Пользователь с таким именем не найден.");
            }
        }
        String destRequisite = destOptional.get();
        boolean isAmountCorrect = false;
        long amount = -1;
        while (!isAmountCorrect) {
            amount = input.askLong("Введите сумму перевода: ");
            if (amount >= 0) {
                if (bankService.balance(requisite, logger) >= amount*100) {
                    isAmountCorrect = true;
                } else {
                    System.out.println("На Вашем счете недостаточно средств.");
                }
            } else {
                System.out.println("Неверные данные.");
            }
        }
        String password = input.askStr("Повторите пароль для подтверждения перевода: ");
        if (bankService.getRequisiteIfPresent(username, password, logger).isPresent()) {

            bankService.transferMoney(username, password, requisite, destRequisite, amount, logger);
            System.out.println("Успешно! Остаток средств на счете:" + bankService.balance(requisite, logger)/100);
        } else {
            System.out.println("Неверный пароль. Операция отклонена.");
        } return true;
    }
}
