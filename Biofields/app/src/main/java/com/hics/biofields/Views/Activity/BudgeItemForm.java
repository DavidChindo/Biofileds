package com.hics.biofields.Views.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.Validators;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Network.Requests.RequisitionItem.BudgeItemRequest;
import com.hics.biofields.Network.Responses.Catalogs.ItemResponse;
import com.hics.biofields.Network.Responses.Catalogs.UoMResponse;
import com.hics.biofields.Network.Responses.Catalogs.VendorResponse;
import com.hics.biofields.Presenters.Events.BudgeItemEvent;
import com.hics.biofields.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.realm.Realm;
import io.realm.RealmList;

public class BudgeItemForm extends AppCompatActivity {

    @BindView(R.id.act_budge_notes)EditText notesEdt;
    @BindView(R.id.act_budge_productservice)RadioGroup productservicerg;
    @BindView(R.id.act_budge_description)EditText descriptionEdt;
    @BindView(R.id.act_budge_description_list)ListView lvDescriptions;
    @BindView(R.id.act_budge_uom)MaterialSpinner spUOM;
    @BindView(R.id.act_budge_price)EditText priceEdt;
    @BindView(R.id.act_budge_qty)EditText qtyEdt;
    @BindView(R.id.act_budge_total)TextView totalTxt;

    public boolean searching = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budge_item_form);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);
        initFields();
    }

    private void initFields(){
        Realm realm = Realm.getDefaultInstance();
    spUOM.setAdapter(new ArrayAdapter<UoMResponse>(this,android.R.layout.simple_spinner_dropdown_item, RealmManager.list(realm,UoMResponse.class)));

        descriptionEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searching) {
                    String word = descriptionEdt.getText().toString().toLowerCase().trim();
                    RealmList<ItemResponse> listValues = new RealmList<>();
                    if (word != null && !word.isEmpty() && word.length() > 2) {
                        listValues.clear();
                        listValues = RealmManager.findByDescription(ItemResponse.class,"companyId",FormRequisitionActivity.idCompanyGlobal,"itemDesc",word);
                        if (listValues != null && listValues.size() > 0) {
                            lvDescriptions.setVisibility(View.VISIBLE);
                            lvDescriptions.setAdapter(new ArrayAdapter<ItemResponse>(BudgeItemForm.this,android.R.layout.simple_list_item_1,listValues));
                            DesignUtils.setListViewHeightBasedOnChildrenAdapter(lvDescriptions);
                            lvDescriptions.setPadding(0,0,0,0);
                        } else {
                            listValues.clear();
                            lvDescriptions.setAdapter(null);
                            lvDescriptions.setAdapter(new ArrayAdapter<ItemResponse>(BudgeItemForm.this,
                                    android.R.layout.simple_list_item_1, listValues));
                            lvDescriptions.setVisibility(View.GONE);
                            DesignUtils.warningMessage(BudgeItemForm.this,"","No existen resultados");
                        }
                    }else{
                        listValues.clear();
                        lvDescriptions.setVisibility(View.GONE);
                        lvDescriptions.setAdapter(null);
                        lvDescriptions.setAdapter(new ArrayAdapter<ItemResponse>(BudgeItemForm.this,
                                android.R.layout.simple_list_item_1, listValues));
                    }
                }

            }
        });


        qtyEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!priceEdt.getText().toString().trim().isEmpty() && !qtyEdt.getText().toString().trim().isEmpty()){
                    int price = Integer.parseInt(priceEdt.getText().toString().trim());
                    int qty = Integer.parseInt(s.toString());
                    int total = price * qty;
                    totalTxt.setText(getResources().getString(R.string.act_budge_total,total));
                    totalTxt.setVisibility(View.VISIBLE);
                }
            }
        });

        priceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!priceEdt.getText().toString().trim().isEmpty() && !qtyEdt.getText().toString().trim().isEmpty()){
                    int price = Integer.parseInt(qtyEdt.getText().toString().trim());
                    int qty = Integer.parseInt(s.toString());
                    int total = price * qty;
                    totalTxt.setText(getResources().getString(R.string.act_budge_total,total));
                    totalTxt.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean validateForm(){
        if (!Validators.validateEdt(notesEdt,this,"Notas")){
            return false;
        }else if(!Validators.validateRadioGroup(productservicerg,this,"Producto / Servicio")){
            return false;
        }else if (!Validators.validateEdt(descriptionEdt,this,"Descripcion (Producto / Servicio)")){
            return false;
        }else  if(!Validators.validateSpiner(spUOM,this,"Unidad de medida")){
            return false;
        }else if(!Validators.validateEdt(priceEdt,this,"Precio unitario")){
            return false;
        }else if(!Validators.validateEdt(qtyEdt,this,"Cantidad")){
            return false;
        }else {
            return true;
        }
    }

    private BudgeItemRequest budgeItemRequest(){
        String productService = productservicerg.getCheckedRadioButtonId() == R.id.act_budge_productservice_yes ? "Producto" : "Servicio";
        BudgeItemRequest budgeItemRequest = new BudgeItemRequest(idAutonumeric(FormRequisitionActivity.budgeItemRequests),
                notesEdt.getText().toString().trim(),productService,
                    descriptionEdt.getText().toString().trim(),((UoMResponse)spUOM.getSelectedItem()).toString(),
                        priceEdt.getText().toString().trim(),qtyEdt.getText().toString().trim(),totalTxt.getText().toString());

        return budgeItemRequest;

    }

    private int idAutonumeric(ArrayList<BudgeItemRequest> budgeItemRequest){
        return budgeItemRequest.isEmpty() ? 1 : budgeItemRequest.size() + 1;
    }

    @OnItemClick(R.id.act_budge_description_list)
    void onProviderClick(int position){
        DesignUtils.hideKeyboard(this);
        searching = false;
        descriptionEdt.setText(((ItemResponse) lvDescriptions.getItemAtPosition(position)).getItemDesc());
        lvDescriptions.setVisibility(View.GONE);
        searching = true;
    }

    @OnClick(R.id.act_budge_cancel)
    void onCancelClick(){
        finish();
    }

    @OnClick(R.id.act_budge_accept)
    void onAcceptClick(){
        if (validateForm()) {
            EventBus.getDefault().postSticky(new BudgeItemEvent(budgeItemRequest()));
            finish();
        }
    }

}
