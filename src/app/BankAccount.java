package app;

import java.util.Objects;

/**
 * Модель данных для работы с пользователем и его счётом (не больше одного).
 */

public class BankAccount {

    private String username;
    private String password;
    private long balance;
    private final String requisite;

    public BankAccount(String username, String password, String requisite) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.requisite = requisite;
    }

    /**
    * Геттеры/сеттеры
    */

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getRequisite() {
        return requisite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankAccount that = (BankAccount) o;
        if (!Objects.equals(username, that.username)) {
            return false;
        }
        return Objects.equals(requisite, that.requisite);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (requisite != null ? requisite.hashCode() : 0);
        return result;
    }
}
