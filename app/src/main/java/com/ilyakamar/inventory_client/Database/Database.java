package com.ilyakamar.inventory_client.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.ilyakamar.inventory_client.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {// START

    private final static String DB_NAME =  "EatItDB.db";
    private final static int DB_VER =  1;




    public Database(Context context) {// cons Database
        super(context, DB_NAME, null, DB_VER);

    }// end cons Database



    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"ProductName","ProductId","Quantity","Price","Disscount"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                result.add(new Order(
                        c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Disscount"))
                        ));
            }while (c.moveToNext());
        }
        return result;
    }


    public void addToCart(Order order){// addToCart()


        SQLiteDatabase db = getReadableDatabase();
        String query = String .format(
                "INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Disscount) VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());

        db.execSQL(query);

    }// end addToCart


    public void cleanCart(){// cleanCart()   ///// Order order


        SQLiteDatabase db = getReadableDatabase();
        String query = String .format("DELETE FROM OrderDetail");



        db.execSQL(query);

    }// end cleanCart

}// END
