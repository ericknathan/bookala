package com.ericknathan.symbian.bookala.helpers;

public class LoginHelper {
    private static Integer id_user;

    public static int getUserId() {
        return id_user;
    }

    public static void setUserId(int user_id) {
        LoginHelper.id_user = user_id;
    }

    public static void setUserId() {
        LoginHelper.id_user = null;
    }
}
