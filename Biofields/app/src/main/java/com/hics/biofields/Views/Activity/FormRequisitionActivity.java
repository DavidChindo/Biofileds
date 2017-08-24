package com.hics.biofields.Views.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hics.biofields.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

public class FormRequisitionActivity extends AppCompatActivity {

    @BindView(R.id.act_form_company)MaterialSpinner spCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_requisition);
        ButterKnife.bind(this);
        ArrayList<String> cadenas = new ArrayList<String>();
        cadenas.add("ichi");
        cadenas.add("ni");
        cadenas.add("san");
        cadenas.add("yon");
        cadenas.add("gon");
        cadenas.add("roku");
        cadenas.add("sitchi");
        cadenas.add("hatchi");
        cadenas.add("kyu");
        cadenas.add("yuu");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,cadenas);
        spCompany.setAdapter(adapter);
    }

}
