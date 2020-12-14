package app;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BankService - класс, который нарушает принцип единственной ответственности. Здесь он сразу
 * и хранит аккаунты, и реализует логику проверки баланса и переводов.
 */
public class BankService {

    private Map<String, BankAccount> accounts = new HashMap<>();

    public void addAccount(BankAccount account) {
        boolean isFindAcc = false;
        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
            BankAccount acc = entry.getValue();
            if (acc.getUsername().equals(account.getUsername())) {
                isFindAcc = true;
                break;
            }
        }
        if (!isFindAcc) {
            accounts.putIfAbsent(account.getRequisite(), account);
        }
    }

    /**
     * Метод проверяет, что в Map есть аккаунт, если есть - вернёт реквизиты.
     */
    public Optional<String> getRequisiteIfPresent(String username, String password, Logger logger) {
        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
            BankAccount account = entry.getValue();
            if (account.getPassword().equals(password) & account.getUsername().equals(username)) {
                return Optional.of(entry.getKey());
            }
        }
        //логирование неудачной попытки авторизации
        logger.log(Level.WARNING, ("Неудачная попытка авторизации."));
        return Optional.empty();
    }

    /**
     * Метод получения реквизита по имени
     */
    public Optional<String> getRequisiteByName(String username, Logger logger) {
        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
            BankAccount account = entry.getValue();
            if (account.getUsername().equals(username)) {
                return Optional.of(entry.getKey());
            }
        }
        //логирование ненайденного реквизита
        logger.log(Level.WARNING, ("Реквизит не найден."));
        return Optional.empty();
    }

    /**
     * Метод получения имени по реквизиту
     */
    public Optional<String> getNameByRequisite(String requisite, Logger logger) {
        BankAccount account = accounts.get(requisite);
        if (account != null) {
            return Optional.of(account.getUsername());
        }
        //логирование ненайденного пользователя
        logger.log(Level.WARNING, ("Пользователь не найден."));
        return Optional.empty();
    }

    /**
     * Метод для кол-ва средств на передаваемых реквизитах. На этом методе вам нужно выкидывать исключение,
     * если передаваемые реквизиты не валидны, это единственный способ сообщить о проблеме.
     */
    public long balance(String requisite, Logger logger) {
        BankAccount account = accounts.get(requisite);
        if (account != null) {
            logger.log(Level.FINE, ("Успешно."));
            return account.getBalance();

        } else {
            logger.log(Level.WARNING, ("Реквизит не найден."));
            return 0;
        }
    }

    /**
     * Метод для пополнения баланс.
     */
    public boolean topUpBalance(String requisite, long amount, Logger logger) {
        BankAccount account = accounts.get(requisite);
        account.setBalance(account.getBalance() + amount * 100);
        logger.log(Level.FINE, ("На счет " + requisite + " зачислено: " + amount));
        return true;
    }

    /**
     * Метод переводит средства с одного счёта на другой, если все условия соблюдены.
     */
    public boolean transferMoney(String username, String password, String srcRequisite,
                                 String destRequisite, long amount, Logger logger) {
        boolean rsl = false;
        if (getRequisiteIfPresent(username, password, logger).isPresent() &
                getRequisiteIfPresent(username, password, logger).get().equals(srcRequisite) &
                accounts.get(destRequisite) != null &
                accounts.get(srcRequisite) != null &
                accounts.get(srcRequisite).getBalance() >= amount * 100 &
                amount > 0) {
            BankAccount srcAccount = accounts.get(srcRequisite);
            BankAccount destAccount = accounts.get(destRequisite);
            srcAccount.setBalance(srcAccount.getBalance() - amount * 100);
            //логирование списания
            logger.log(Level.FINE, ("Со счета пользователя " + username + "списано: "+ amount));
            destAccount.setBalance(destAccount.getBalance() + amount * 100);
            //логирование начисления
            logger.log(Level.FINE, ("На счет " + getNameByRequisite(destRequisite, logger) + "начислено: "+ amount));
            rsl = true;
        }
        return rsl;
    }
}
