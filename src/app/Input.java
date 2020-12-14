package app;

/**
 * Интерфейс для ввода.
 */
public interface Input {

    /**
     * Спросить и получить ответ строкой.
     */
    String askStr(String question);

    /**
     * Спросить и получить ответ интом.
     */
    int askInt(String question);

    /**
     * Спросить и получить ответ типом long.
     */
    long askLong(String question);
}
