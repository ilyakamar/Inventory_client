package com.ilyakamar.inventory_client.Common;

import com.ilyakamar.inventory_client.Model.User;

public class Common {


    public final static String CATEGORY_ID = "CategoryId";
    public static User currentUser;

    public static String  convertCodeToStatus(String status) {

        if (status.equals("0"))
            return "ההזמנה התקבלה";// Placed
        else if (status.equals("1"))
            return "ההזמנה בטיפול";// On my way
        else
            return "המשלוח בדרך"; // Shipped

    }// end convertCodeToStatus
}
