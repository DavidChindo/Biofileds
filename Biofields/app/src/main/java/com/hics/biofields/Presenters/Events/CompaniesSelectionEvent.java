package com.hics.biofields.Presenters.Events;

import android.widget.ArrayAdapter;

import com.hics.biofields.Network.Responses.CompanyResponse;

import java.util.ArrayList;

/**
 * Created by david.barrera on 9/1/17.
 */

public class CompaniesSelectionEvent {

    public ArrayList<CompanyResponse> companies;

    public CompaniesSelectionEvent(ArrayList<CompanyResponse> companies) {
        this.companies = companies;
    }
}
