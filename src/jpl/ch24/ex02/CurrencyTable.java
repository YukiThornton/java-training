package jpl.ch24.ex02;

import java.util.Currency;
import java.util.Locale;

public class CurrencyTable {

    private static final int TABLE_SIZE = 6;
    private Locale[] locales;
    private String[] currencyCodes;
    private Currency[] currencies = new Currency[TABLE_SIZE];
    private String[][] currencyStrs = new String[TABLE_SIZE][TABLE_SIZE];

    public static void main(String[] args) {
        Locale[] locales = {Locale.CANADA, Locale.CANADA_FRENCH, Locale.JAPAN, Locale.US, Locale.TAIWAN, Locale.GERMAN};
        String[] currencyCodes = {"AED", "USD", "JPY", "AMD", "ANG", "AOA"};
        CurrencyTable table = CurrencyTable.create(locales, currencyCodes);
        table.show();
    }

    private CurrencyTable() {
        super();
    }

    public static CurrencyTable create(Locale[] locales, String[] currencyCodes) {
        if (locales == null || locales.length != TABLE_SIZE || 
                currencyCodes == null || currencyCodes.length != TABLE_SIZE) {
          throw new IllegalArgumentException("Both locales and currencyCodes should have 6 elements.");
        }
        CurrencyTable table = new CurrencyTable();
        table.locales = locales;
        table.currencyCodes = currencyCodes;
        table.doCreate();
        return table;
    }
    
    public void show() {
        int rowWidth = getMaximumLocaleDisplayName();
        System.out.print("    |");
        for (Locale locale : locales) {
            String name = locale.getDisplayName();
            System.out.print(name);
            completeCell(name, rowWidth);
        }
        System.out.println();
        for (int i = 0; i < currencyStrs.length; i++) {
            System.out.print(" " + currencies[i].getCurrencyCode() + " |");
            for (int j = 0; j < currencyStrs[i].length; j++) {
                String currencyStr = currencyStrs[i][j];
                System.out.print(currencyStr);
                completeCell(currencyStr, rowWidth);
            }
            System.out.println();
        }
    }
    
    private void doCreate() {
        createCurrencies();
        for (int i = 0; i < currencies.length; i++) {
            for (int j = 0; j < locales.length; j++) {
                currencyStrs[i][j] = currencies[i].getSymbol(locales[j]);
            }
        }
    }
    
    private void createCurrencies() {
        for (int i = 0; i < currencyCodes.length; i++) {
            currencies[i] = Currency.getInstance(currencyCodes[i]);
        }
    }
    
    private int getMaximumLocaleDisplayName() {
        int result = 0;
        for (Locale locale : locales) {
            int length = locale.getDisplayName().length();
            if (length > result) {
                result = length;
            }
        }
        return result;
    }
    
    private void completeCell(String element, int rowWidth) {
        if (rowWidth > element.length()) {
            for (int i = 0; i < rowWidth - element.length(); i++) {
                System.out.print(" ");
            }
        }
        System.out.print("|");
    }
}
