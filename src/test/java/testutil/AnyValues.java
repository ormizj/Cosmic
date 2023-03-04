package testutil;

import database.DaoException;

public class AnyValues {

    public static String string() {
        return "string";
    }

    public static int integer() {
        return 17;
    }

    public static DaoException daoException() {
        return new DaoException(string(), new RuntimeException());
    }
}
