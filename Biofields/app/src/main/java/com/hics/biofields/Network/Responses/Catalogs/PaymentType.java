package com.hics.biofields.Network.Responses.Catalogs;

import java.util.ArrayList;

/**
 * Created by david.barrera on 9/4/17.
 */

public class PaymentType {

    private String desc;
    private String id;

    public PaymentType(String id,String desc) {
        this.desc = desc;
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.desc;
    }

    public static ArrayList<PaymentType> paymentsType(){
        ArrayList<PaymentType> payments = new ArrayList<PaymentType>();
        payments.add(new PaymentType("2","MXN - Pesos"));
        payments.add(new PaymentType("3","USD - Dólares"));
        payments.add(new PaymentType("4","EUR - Euros"));

        return payments;
    }
}
