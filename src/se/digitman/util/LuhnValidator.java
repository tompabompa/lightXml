package se.digitman.util;

/**
 * Created 2011-jan-25
 * Validator for Luhn numbers.
 * @author hl
 */
public class LuhnValidator {

    private final String luhnNumber;

    /**
     * @param luhnNumber A String representation of the Luhn 
     * number to validate
     */
    public LuhnValidator(String luhnNumber) {
        this.luhnNumber = luhnNumber.replaceAll("\\D", "");
    }

    /**
     * Returns true if the string entered is a correct
     * number according to the Luhn-algorithm.
     * Works for:
     * Personal identity number,
     * Corporate identity number,
     * Plusgiro,
     * Bankgiro et al
     */
    public boolean isValid() {
        int a = 1;
        int sum = 0;
        int term;
        for (int i = luhnNumber.length() - 1; i >= 0; i--) {
            term = Character.digit(luhnNumber.charAt(i), 10) * a;
            if (term > 9) {
                term -= 9;
            }
            sum += term;
            a = 3 - a;
        }
        return sum % 10 == 0;
    }
}
