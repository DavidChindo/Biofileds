package com.hics.biofields.Views.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.Prefs;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Library.Validators;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Network.Requests.RequisitionItem.BudgeItemRequest;
import com.hics.biofields.Network.Responses.Catalogs.ExpenseResponse;
import com.hics.biofields.Network.Responses.Catalogs.ItemResponse;
import com.hics.biofields.Network.Responses.Catalogs.UoMResponse;
import com.hics.biofields.Network.Responses.Catalogs.VendorResponse;
import com.hics.biofields.Presenters.Events.BudgeItemEvent;
import com.hics.biofields.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
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
    @BindView(R.id.act_budge_item_ln)LinearLayout productserviceLn;

    public boolean searching = true;
    public boolean searchItem = true;
    public boolean isBiofieldsCompany = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budge_item_form);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);
        initFields();
    }

    private void initFields(){
        Prefs prefs = Prefs.with(BudgeItemForm.this);
        isBiofieldsCompany = prefs.getBoolean(Statics.IS_BIOFIELDS_PREFS);
        productserviceLn.setVisibility(isBiofieldsCompany ? View.VISIBLE : View.GONE);
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
                    if (searchItem) {
                        RealmList<ItemResponse> listValues = new RealmList<>();
                        if (word != null && !word.isEmpty() && word.length() > 2) {
                            listValues.clear();
                            listValues = RealmManager.findByDescription(ItemResponse.class, "companyId", FormRequisitionActivity.idCompanyGlobal, "itemDesc", word);
                            if (listValues != null && listValues.size() > 0) {
                                lvDescriptions.setVisibility(View.VISIBLE);
                                lvDescriptions.setAdapter(new ArrayAdapter<ItemResponse>(BudgeItemForm.this, android.R.layout.simple_list_item_1, listValues));
                                DesignUtils.setListViewHeightBasedOnChildrenAdapter(lvDescriptions);
                                lvDescriptions.setPadding(0, 0, 0, 0);
                            } else {
                                listValues.clear();
                                lvDescriptions.setAdapter(null);
                                lvDescriptions.setAdapter(new ArrayAdapter<ItemResponse>(BudgeItemForm.this,
                                        android.R.layout.simple_list_item_1, listValues));
                                lvDescriptions.setVisibility(View.GONE);
                                DesignUtils.warningMessage(BudgeItemForm.this, "", "No existen resultados");
                            }
                        } else {
                            listValues.clear();
                            lvDescriptions.setVisibility(View.GONE);
                            lvDescriptions.setAdapter(null);
                            lvDescriptions.setAdapter(new ArrayAdapter<ItemResponse>(BudgeItemForm.this,
                                    android.R.layout.simple_list_item_1, listValues));
                        }
                    }else{
                        RealmList<ExpenseResponse> listValues = new RealmList<>();
                        if (word != null && !word.isEmpty() && word.length() > 2) {
                            listValues.clear();
                            listValues = RealmManager.findByProvider(ExpenseResponse.class,"expcatDesc",word);
                            if (listValues != null && listValues.size() > 0) {
                                lvDescriptions.setVisibility(View.VISIBLE);
                                lvDescriptions.setAdapter(new ArrayAdapter<ExpenseResponse>(BudgeItemForm.this, android.R.layout.simple_list_item_1, listValues));
                                DesignUtils.setListViewHeightBasedOnChildrenAdapter(lvDescriptions);
                                lvDescriptions.setPadding(0, 0, 0, 0);
                            } else {
                                listValues.clear();
                                lvDescriptions.setAdapter(null);
                                lvDescriptions.setAdapter(new ArrayAdapter<ExpenseResponse>(BudgeItemForm.this,
                                        android.R.layout.simple_list_item_1, listValues));
                                lvDescriptions.setVisibility(View.GONE);
                                DesignUtils.warningMessage(BudgeItemForm.this, "", "No existen resultados");
                            }
                        } else {
                            listValues.clear();
                            lvDescriptions.setVisibility(View.GONE);
                            lvDescriptions.setAdapter(null);
                            lvDescriptions.setAdapter(new ArrayAdapter<ExpenseResponse>(BudgeItemForm.this,
                                    android.R.layout.simple_list_item_1, listValues));
                        }
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
                    double price = Double.parseDouble(priceEdt.getText().toString().trim());
                    double qty = Double.parseDouble(s.toString());
                    double total = price * qty;
                    totalTxt.setText(getResources().getString(R.string.act_budge_total, String.valueOf(total)));
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
                    double price = Double.parseDouble(qtyEdt.getText().toString().trim());
                    double qty = Double.parseDouble(s.toString());
                    double total = price * qty;
                    totalTxt.setText(getResources().getString(R.string.act_budge_total,String.valueOf(total)));
                    totalTxt.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean validateForm(){
        if (!Validators.validateEdt(notesEdt,this,"Notas")){
            return false;
        }else if(isBiofieldsCompany){
                if (!Validators.validateRadioGroup(productservicerg,this,"Producto / Servicio")){
                return false;
            }else{
                    return true;
                }
        }else if(isBiofieldsCompany){
                if(!Validators.validateEdt(descriptionEdt,this,"Descripcion (Producto / Servicio)")){
                   return false;
                }else{
                    return true;
                }
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
        boolean productService = productservicerg.getCheckedRadioButtonId() == R.id.act_budge_productservice_yes ? true : false;
        BudgeItemRequest budgeItemRequest = new BudgeItemRequest(idAutonumeric(FormRequisitionActivity.budgeItemRequests),
                notesEdt.getText().toString().trim(),isBiofieldsCompany ? productService  ? descriptionEdt.getText().toString().trim() : "-1" : null,
                isBiofieldsCompany ? !productService  ? descriptionEdt.getText().toString().trim() : "-1" : null,((UoMResponse)spUOM.getSelectedItem()).toString(),
                Double.parseDouble(priceEdt.getText().toString().trim()),Double.parseDouble(qtyEdt.getText().toString().trim()),
                totalTxt.getText().toString());

        return budgeItemRequest;

    }

    private int idAutonumeric(ArrayList<BudgeItemRequest> budgeItemRequest){
        return budgeItemRequest.isEmpty() ? 1 : budgeItemRequest.size() + 1;
    }

    @OnItemClick(R.id.act_budge_description_list)
    void onProviderClick(int position){
        DesignUtils.hideKeyboard(this);
        searching = false;
        boolean productService = productservicerg.getCheckedRadioButtonId() == R.id.act_budge_productservice_yes ? true : false;
        if (productService) {
            descriptionEdt.setText(((ItemResponse) lvDescriptions.getItemAtPosition(position)).getItemDesc());
        }else{
            descriptionEdt.setText(((ExpenseResponse) lvDescriptions.getItemAtPosition(position)).getExpcatDesc());
        }
        lvDescriptions.setVisibility(View.GONE);
        searching = true;
    }

    @OnCheckedChanged({R.id.act_budge_productservice_yes, R.id.act_budge_productservice_not})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            descriptionEdt.setEnabled(true);
            switch (button.getId()) {
                case R.id.act_budge_productservice_yes:
                    descriptionEdt.setText("");
                        descriptionEdt.setHint("Ingrese producto");
                        searchItem = true;
                    break;
                case R.id.act_budge_productservice_not:
                    descriptionEdt.setText("");
                    descriptionEdt.setHint(R.string.act_budge_description);
                    searchItem = false;
                    break;
            }
        }
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
