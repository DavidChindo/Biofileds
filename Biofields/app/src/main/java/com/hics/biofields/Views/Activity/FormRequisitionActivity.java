package com.hics.biofields.Views.Activity;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hics.biofields.BioApp;
import com.hics.biofields.Library.Connection;
import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.LogicUtils;
import com.hics.biofields.Library.Prefs;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Library.Validators;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Network.FilesResponse;
import com.hics.biofields.Network.Requests.FileRequest;
import com.hics.biofields.Network.Requests.RequisitionItem.BudgeItemRequest;
import com.hics.biofields.Network.Requests.RequisitionRequest;
import com.hics.biofields.Network.Responses.Catalogs.BudgetlistResponse;
import com.hics.biofields.Network.Responses.Catalogs.CompanyCatResponse;
import com.hics.biofields.Network.Responses.Catalogs.CostcenterResponse;
import com.hics.biofields.Network.Responses.Catalogs.PaymentType;
import com.hics.biofields.Network.Responses.Catalogs.SiteResponse;
import com.hics.biofields.Network.Responses.Catalogs.VendorResponse;
import com.hics.biofields.Network.Responses.RequisitionResponse;
import com.hics.biofields.Presenters.Events.BudgeItemEvent;
import com.hics.biofields.Presenters.Events.CloseFormRequisitionEvent;
import com.hics.biofields.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormRequisitionActivity extends AppCompatActivity {

    @BindView(R.id.act_form_company)MaterialSpinner spCompany;
    @BindView(R.id.act_form_center)MaterialSpinner spCenter;
    @BindView(R.id.act_form_itembudge)MaterialSpinner spItemBudge;
    @BindView(R.id.act_form_provider)EditText providerEdt;
    @BindView(R.id.act_form_provider_list)ListView providersLv;
    @BindView(R.id.act_form_description)EditText descriptionEdt;
    @BindView(R.id.act_form_site)MaterialSpinner spSite;
    @BindView(R.id.act_form_comments)EditText commentsEdt;
    @BindView(R.id.act_form_poa)RadioGroup poaRg;
    @BindView(R.id.act_form_includereplace)RadioGroup includeRg;
    @BindView(R.id.act_form_delete)RadioGroup deleteRg;
    @BindView(R.id.act_form_indispensable)RadioGroup indispensableRg;
    @BindView(R.id.act_form_payment)MaterialSpinner spPayment;
    @BindView(R.id.act_form_billed_rg)RadioGroup billedRg;
    @BindView(R.id.act_form_urgent)RadioGroup urgenteRg;
    @BindView(R.id.act_form_files_ln)LinearLayout filesLn;
    @BindView(R.id.act_form_budgeitem_lv)ListView budgeitemsLv;
    @BindView(R.id.act_form_add_files)Button addFilesBtn;
    @BindView(R.id.card_info)CardView infoCard;
    @BindView(R.id.billedTextView)TextView billedTxt;
    @BindView(R.id.urgentTextView)TextView urgentTxt;
    @BindView(R.id.info_budget_title)TextView infoBudgetTxt;
    @BindView(R.id.act_form_poa_not)RadioButton poaNot;
    @BindView(R.id.act_form_poa_yes)RadioButton poayes;
    @BindView(R.id.act_form_includereplace_not)RadioButton includeNot;
    @BindView(R.id.act_form_includereplace_yes)RadioButton includeYes;
    @BindView(R.id.act_form_delet_not)RadioButton deleteNot;
    @BindView(R.id.act_form_delet_yes)RadioButton deleteYes;
    @BindView(R.id.act_form_indispensable_not)RadioButton indispensableNot;
    @BindView(R.id.act_form_indispensable_yes)RadioButton indispensableYes;

    public boolean searching = true;
    public static String idCompanyGlobal = "";
    public static ArrayList<BudgeItemRequest> budgeItemRequests = new ArrayList<BudgeItemRequest>();
    public ArrayList<String> files = new ArrayList<String>();
    private static final int READ_REQUEST_CODE = 42;
    public static final String TAG = FormRequisitionActivity.class.getSimpleName();
    public static final String folderOrigin = Environment.getExternalStorageDirectory() + "/" + Statics.NAME_FOLDER + "/";
    ProgressDialog mProgressDialog;
    ArrayAdapter<BudgeItemRequest> budgeAdapter;
    public boolean isBiofieldsCompany = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_requisition);
        ButterKnife.bind(this);
        initFields();
    }

    private void initFields(){
        Prefs prefs = Prefs.with(this);
        isBiofieldsCompany = prefs.getBoolean(Statics.IS_BIOFIELDS_PREFS);
        final Realm realm = Realm.getDefaultInstance();
        spCompany.setAdapter(new ArrayAdapter<CompanyCatResponse>(this,android.R.layout.simple_spinner_dropdown_item, RealmManager.list(realm,CompanyCatResponse.class)));
        spCenter.setAdapter(new ArrayAdapter<CostcenterResponse>(this,android.R.layout.simple_spinner_dropdown_item, RealmManager.list(realm,CostcenterResponse.class)));
        spItemBudge.setAdapter(new ArrayAdapter<BudgetlistResponse>(this,android.R.layout.simple_spinner_dropdown_item, RealmManager.list(realm,BudgetlistResponse.class)));
        spSite.setAdapter(new ArrayAdapter<SiteResponse>(this,android.R.layout.simple_spinner_dropdown_item, RealmManager.list(realm,SiteResponse.class)));
        spPayment.setAdapter(new ArrayAdapter<PaymentType>(this,android.R.layout.simple_spinner_dropdown_item, PaymentType.paymentsType()));
        //realm.close();
        hideFields();
        registerForContextMenu(budgeitemsLv);
        if (isBiofieldsCompany) {
            providerEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (searching) {
                        String word = providerEdt.getText().toString().toLowerCase().trim();
                        RealmList<VendorResponse> listValues = new RealmList<>();
                        if (word != null && !word.isEmpty() && word.length() > 2) {
                            listValues.clear();
                            listValues = RealmManager.findByProvider(VendorResponse.class, "name", word);
                            if (listValues != null && listValues.size() > 0) {
                                providersLv.setVisibility(View.VISIBLE);
                                providersLv.setAdapter(new ArrayAdapter<VendorResponse>(FormRequisitionActivity.this, android.R.layout.simple_list_item_1, listValues));
                                DesignUtils.setListViewHeightBasedOnChildrenAdapter(providersLv);
                                providersLv.setPadding(0, 0, 0, 0);
                            } else {
                                listValues.clear();
                                listValues = RealmManager.findByProviderNotRegister(VendorResponse.class, "name", "Proveedor no registrado");
                                providersLv.setAdapter(null);
                                providersLv.setAdapter(new ArrayAdapter<VendorResponse>(FormRequisitionActivity.this,
                                        android.R.layout.simple_list_item_1, listValues));
                                providersLv.setVisibility(View.VISIBLE);
                                //DesignUtils.warningMessage(FormRequisitionActivity.this, "", "No existen resultados");
                            }
                        } else {
                            listValues.clear();
                            providersLv.setVisibility(View.GONE);
                            providersLv.setAdapter(null);
                            providersLv.setAdapter(new ArrayAdapter<VendorResponse>(FormRequisitionActivity.this,
                                    android.R.layout.simple_list_item_1, listValues));
                        }
                    }

                }
            });
        }
    }

    private void hideFields(){
        if(!isBiofieldsCompany){
            billedTxt.setVisibility(View.GONE);
            urgentTxt.setVisibility(View.GONE);
            urgenteRg.setVisibility(View.GONE);
            billedRg.setVisibility(View.GONE);
            spSite.setVisibility(View.GONE);
            infoCard.setVisibility(View.GONE);
            infoBudgetTxt.setVisibility(View.GONE);
        }
    }



    public Boolean isSizeValid(File file) {
        final long MiB = 1024 * 1024;
        final long KiB = 1024;
        final long maxSize = (MiB * 5) / MiB;

        if (!file.isFile()) {
            throw new IllegalArgumentException("Expected a file");
        }
        final double length = file.length();

        if (length > MiB) {
            Log.d("FORMVALIDATION","SIZE "+((length / MiB) > maxSize));
            return (length / MiB) < maxSize;
        }
        if (length > KiB) {
            return true;
        }
        return true;
    }

    @OnClick(R.id.act_form_sent_requisition)
    void onSentRequisitionClick(){
        String path = zip(files,"2222");
        File file = new File(path);
        if (isSizeValid(file)) {
            if (Connection.isConnected(this)) {
                boolean validate = isBiofieldsCompany ? validateForm() : validateFormNotBiofields();
                if (validate) {
                    Gson gson = new Gson();
                    String json = gson.toJson(createRequisition());
                    Log.d(TAG, "JSON " + json);
                    Call<RequisitionResponse> call = BioApp.getHicsService().createRequisition("Bearer " + RealmManager.token(), createRequisition());
                    mProgressDialog = ProgressDialog.show(this, null, "Enviando...");
                    mProgressDialog.setCancelable(false);
                    call.enqueue(new Callback<RequisitionResponse>() {
                        @Override
                        public void onResponse(Call<RequisitionResponse> call, Response<RequisitionResponse> response) {
                            mProgressDialog.dismiss();
                            if (response.code() == Statics.code_OK_Get) {
                                //DesignUtils.successMessage(FormRequisitionActivity.this,"Crear Requisición",response.body().getMessage() +" con el número " +response.body().getReqNumber());
                                uploadFiles(response.body().getReqNumber());
                            } else {
                                DesignUtils.errorMessage(FormRequisitionActivity.this, "Crear Requisición", "por el momento no es posible crear la requisición");
                            }
                        }

                        @Override
                        public void onFailure(Call<RequisitionResponse> call, Throwable t) {
                            mProgressDialog.dismiss();
                            DesignUtils.errorMessage(FormRequisitionActivity.this, "Crear Requisición", t.getLocalizedMessage());
                        }
                    });
                }
            } else {
                DesignUtils.errorMessage(this, "Error de Red", "No hay conexión a internet");
            }
        }else{
            DesignUtils.errorMessage(this, "", "Ha superado el peso máximo de archivos, no debe ser mayor a 5 MB. Verifique el tamaño de sus archivos");
        }
    }

    @OnItemSelected(R.id.act_form_company)
    void onCompanySelected(int position){
        if (position >= 0) {
            String idCompany = ((CompanyCatResponse) spCompany.getItemAtPosition(position)).getCompanyId();
            FormRequisitionActivity.idCompanyGlobal = idCompany;
            spItemBudge.setVisibility(View.VISIBLE);
            spItemBudge.setAdapter(new ArrayAdapter<BudgetlistResponse>(this, android.R.layout.simple_spinner_dropdown_item, RealmManager.findById(BudgetlistResponse.class, "rubroEmpresaId", idCompany)));
        }else{
            spItemBudge.setVisibility(View.GONE);
        }
    }

    @OnItemClick(R.id.act_form_provider_list)
    void onProviderClick(int position){
        DesignUtils.hideKeyboard(this);
        searching = false;
        providerEdt.setText(((VendorResponse) providersLv.getItemAtPosition(position)).getName());
        providersLv.setVisibility(View.GONE);
        searching = true;
    }

    @OnClick(R.id.act_form_add_budge)
    void onOpenBudgeFomClick(){
        if (spCompany.getSelectedItemPosition() > 0) {
            startActivity(new Intent(this, BudgeItemForm.class));
        }else{
            DesignUtils.errorMessage(this,"Crear Partida de Requisición","Debe seleccionar una compañia primero");
        }
    }

    @OnClick(R.id.act_form_add_files)
    void onAddFiles(){
        if (files.size()< 3) {
            performFileSearch();
        }else{
            DesignUtils.errorMessage(this,"Archivos","Sólo se permite subir máximo 3 archivos");
        }
    }

    @OnCheckedChanged(R.id.act_form_poa_not)
    void onCheckedChangedPoa(CompoundButton compoundButton, boolean checked){
        includeNot.setEnabled(checked);
        includeYes.setEnabled(checked);
        if (!checked){
            includeRg.check(-1);
        }
    }

    @OnCheckedChanged(R.id.act_form_includereplace_not)
    void onCheckedChangedInclude(CompoundButton compoundButton, boolean checked){
       deleteNot.setEnabled(checked);
        deleteYes.setEnabled(checked);
        if (!checked){
            deleteRg.check(-1);
        }
    }

    @OnCheckedChanged(R.id.act_form_delet_not)
    void onCheckedChangedDelete(CompoundButton compoundButton, boolean checked){
        indispensableNot.setEnabled(checked);
        indispensableYes.setEnabled(checked);
        if (!checked){
            indispensableRg.check(-1);
        }
    }



    private void addItems(final String file,String name){
        final View child = getLayoutInflater().inflate(R.layout.item_files, null);
        TextView budgeItem =  (TextView)child.findViewById(R.id.item_file_form_name);
        Button deletbTN = (Button)child.findViewById(R.id.item_file_btn);
        child.setTag(file);
        deletbTN.setTag(file);
        deletbTN.setTag(file);
        if (name!= null && !name.isEmpty()){
            budgeItem.setText(name);
        }
        deletbTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (files.size() > 0){
                    final AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(FormRequisitionActivity.this);
                    builder.setTitle("Archivos de Soporte")
                            .setMessage("¿Desea borrar el archivo?")
                            .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    int index = getIndexArray(file);
                                    files.remove(index);
                                    filesLn.removeView(child);

                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });


        filesLn.addView(child);
        files.add(file);
    }

    private int getIndexArray(String file){
        int index = -1;
        if (!file.isEmpty()){
            for (int i = 0; i <files.size(); i++){
                if (files.get(i).equals(files)){
                    return index;
                }
                index+=1;
            }
        }
        return index;
    }

    public void performFileSearch() {
        Intent intent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }else{
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.form_pick_files)), READ_REQUEST_CODE);

    }

    private boolean validateForm(){
        if (!Validators.validateSpiner(spCompany,this,"Empresa")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Empresa"));
            return false;
        }else if(!Validators.validateSpiner(spCenter,this,"Centro del Costo")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Centro del Costo"));
            return false;
        }else if (!Validators.validateSpiner(spItemBudge,this,"Partida de presupuesto")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Partida de presupuesto"));
            return false;
        }else  if(!Validators.validateEdt(providerEdt,this,"Proveedor")){
            return false;
        }else if(!Validators.validateEdt(descriptionEdt,this,"Descripción del requerimiento")){
            return false;
        }else if(!Validators.validateSpiner(spSite,this,"Sitio de entrega")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Sitio de entrega"));
            return false;
        }else if(!Validators.validateSpiner(spPayment,this,"Moneda de pago")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Moneda de pago"));
            return false;
        }else if(!Validators.validateRadioGroup(billedRg,this,"¿El proveedor ya le ha entregado factura?")){
            return false;
        }else if(!Validators.validateRadioGroup(urgenteRg,this,"¿Pago urgente(Próximo Miércoles)?")){
            return false;
        }else if(!Validators.validateRadioGroup(poaRg,this,"POA")) {
            return false;
        }else if(!validateBudgetInfo()){
            return false;
        /*}else if(!Validators.validateRadioGroup(includeRg,this,"¿Puede incluirse / reemplazar otra partida?")){
            return false;
        }else if(!Validators.validateRadioGroup(deleteRg,this,"¿Se puede eliminar otra partida?")){
            return false;
        }else if(!Validators.validateRadioGroup(indispensableRg,this,"¿Es indispensable para la operación?")){
            return false;*/
        }else if(!Validators.validateArrayListString(files,this,"Archivos de Soporte")){
            return false;
        }else if(!Validators.validateArrayList(FormRequisitionActivity.budgeItemRequests,this,"Partidas de Requisición")){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validateFormNotBiofields(){
        if (!Validators.validateSpiner(spCompany,this,"Empresa")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Empresa"));
            return false;
        }else if(!Validators.validateSpiner(spCenter,this,"Centro del Costo")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Centro del Costo"));
            return false;
        }else if (!Validators.validateSpiner(spItemBudge,this,"Partida de presupuesto")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Partida de presupuesto"));
            return false;
        }else  if(!Validators.validateEdt(providerEdt,this,"Proveedor")){
            return false;
        }else if(!Validators.validateEdt(descriptionEdt,this,"Descripción del requerimiento")){
            return false;
        }else if(!Validators.validateSpiner(spPayment,this,"Moneda de pago")){
            DesignUtils.errorMessage(this,"Campo Obligatorio", getString(R.string.validate_field, "Moneda de pago"));
            return false;
        }else if(!Validators.validateArrayListString(files,this,"Archivos de Soporte")){
            return false;
        }else if(!Validators.validateArrayList(FormRequisitionActivity.budgeItemRequests,this,"Partidas de Requisición")){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validateBudgetInfo(){
        if (poaRg.getCheckedRadioButtonId() == R.id.act_form_poa_not){
            if(!Validators.validateRadioGroup(includeRg,this,"¿Puede incluirse / reemplazar otra partida?")){
                return false;
            }else if (includeRg.getCheckedRadioButtonId() == R.id.act_form_includereplace_not){
                if(!Validators.validateRadioGroup(deleteRg,this,"¿Se puede eliminar otra partida?")){
                    return false;
                }else if (deleteRg.getCheckedRadioButtonId() == R.id.act_form_delet_not){
                    if(!Validators.validateRadioGroup(indispensableRg,this,"¿Es indispensable para la operación?")){
                        return false;
                    }else{
                        return true;
                    }
                }else {
                    return true;
                }
            }else if (deleteRg.getCheckedRadioButtonId() == R.id.act_form_delet_not){
                if(!Validators.validateRadioGroup(indispensableRg,this,"¿Es indispensable para la operación?")){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else if (includeRg.getCheckedRadioButtonId() == R.id.act_form_includereplace_not){
            if(!Validators.validateRadioGroup(deleteRg,this,"¿Se puede eliminar otra partida?")){
                return false;
            }else if (deleteRg.getCheckedRadioButtonId() == R.id.act_form_delet_not){
                if(!Validators.validateRadioGroup(indispensableRg,this,"¿Es indispensable para la operación?")){
                    return false;
                }else {
                    return true;
                }
            }else{
                return true;
            }
        }else if (deleteRg.getCheckedRadioButtonId() == R.id.act_form_delet_not){
            if(!Validators.validateRadioGroup(indispensableRg,this,"¿Es indispensable para la operación?")){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    private RequisitionRequest createRequisition(){

        int reqCompanyId = Integer.parseInt(((CompanyCatResponse)spCompany.getSelectedItem()).getCompanyId());
        int reqCostCenterId = Integer.parseInt(((CostcenterResponse)spCenter.getSelectedItem()).getCostCenterId());
        int reqRubroId = Integer.parseInt(((BudgetlistResponse)spItemBudge.getSelectedItem()).getRubroId());
        String reqVendedor = providerEdt.getText().toString().trim();
        String reqDesc = descriptionEdt.getText().toString().trim();

        int reqSite = isBiofieldsCompany ? Integer.parseInt(((SiteResponse)spSite.getSelectedItem()).getSiteId()) : -1;

        String reqNotes = commentsEdt.getText().toString().trim();
        int reqMonedaId = Integer.parseInt(((PaymentType)spPayment.getSelectedItem()).getId());
        boolean reqFacturado = isBiofieldsCompany ?  billedRg.getCheckedRadioButtonId() == R.id.act_form_billed_yes : Boolean.parseBoolean(null);
        int reqUrgente = isBiofieldsCompany ?  urgenteRg.getCheckedRadioButtonId() == R.id.act_form_urgent_yes ? 1 : 0 : -1;
        boolean reqPOAa = isBiofieldsCompany ? poaRg.getCheckedRadioButtonId() == R.id.act_form_poa_yes : Boolean.parseBoolean(null);
        boolean reqIncluirPOAb = isBiofieldsCompany ?  reqPOAa ? includeRg.getCheckedRadioButtonId() == R.id.act_form_includereplace_yes :
                Boolean.parseBoolean(null) : Boolean.parseBoolean(null);
        boolean reqDeletePOAc =  isBiofieldsCompany ? reqPOAa ? deleteRg.getCheckedRadioButtonId() == R.id.act_form_delet_yes :
                Boolean.parseBoolean(null) :  Boolean.parseBoolean(null);
        boolean reqOperaciond = isBiofieldsCompany ? reqPOAa ? indispensableRg.getCheckedRadioButtonId() == R.id.act_form_indispensable_yes :
                Boolean.parseBoolean(null) :  Boolean.parseBoolean(null);
        ArrayList<BudgeItemRequest> reqitem = FormRequisitionActivity.budgeItemRequests;

        RequisitionRequest requisitionRequest = new RequisitionRequest(reqCompanyId,reqCostCenterId,reqRubroId,reqVendedor,reqDesc,reqSite,reqNotes,reqMonedaId,reqFacturado,reqUrgente,
                reqPOAa,reqIncluirPOAb,reqDeletePOAc,reqOperaciond,reqitem);

        return requisitionRequest;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        String path;
        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
          if (data.getClipData() != null && data.getClipData().getItemCount() > 1) {
              int max = data.getClipData().getItemCount() > 3 ? 3 : data.getClipData().getItemCount();
              for (int i =0; i < max ; i++) {
                  Uri pathUri = data.getClipData().getItemAt(i).getUri();
                  String wholeID = DocumentsContract.getDocumentId(pathUri);
                  String id = wholeID.split(":").length > 1 ? wholeID.split(":")[1] : wholeID.split(":")[0];
                  String pathDest = isDownloadsDocument(pathUri) ? folderOrigin : folderOrigin + id;
                  try {
                      path = inputToFile(pathUri, pathDest);
                      String[] paths = path.split("/");
                      String name = paths[paths.length - 1];
                      addItems(pathDest, name);
                  } catch (IOException e) {
                      e.printStackTrace();
                      if (e.getLocalizedMessage().contains("such")) {
                          DesignUtils.errorMessage(this, "Selección de archivo", "El archivo seleccionado no existe o se ha movido de su ubicación");
                      } else {
                          DesignUtils.errorMessage(this, "Selección de archivo", e.getLocalizedMessage());
                      }
                  }
              }
          }else {
              if (data != null) {
                  uri = data.getData();
                  String wholeID = DocumentsContract.getDocumentId(uri);
                  String id = wholeID.split(":").length > 1 ? wholeID.split(":")[1] : wholeID.split(":")[0];
                  String pathDest = isDownloadsDocument(uri) ? folderOrigin : folderOrigin + id;
                  try {
                      path = inputToFile(uri, pathDest);
                      String[] paths = path.split("/");
                      String name = paths[paths.length - 1];
                      addItems(path, name);
                  } catch (IOException e) {
                      e.printStackTrace();
                      if (e.getLocalizedMessage().contains("such")) {
                          DesignUtils.errorMessage(this, "Selección de archivo", "El archivo seleccionado no existe o se ha movido de su ubicación");
                      } else {
                          DesignUtils.errorMessage(this, "Selección de archivo", e.getLocalizedMessage());
                      }
                  }
              }
          }
        }
    }

    @Subscribe(sticky = true)
    public void onAddBudgeItem(BudgeItemEvent event){
        EventBus.getDefault().removeStickyEvent(event);
        budgeItemRequests.add(event.budgeItemRequest);
        budgeAdapter = new ArrayAdapter<BudgeItemRequest>(this,android.R.layout.simple_list_item_1,budgeItemRequests);
        budgeitemsLv.setAdapter(budgeAdapter);
        DesignUtils.setListViewHeightBasedOnChildrenAdapter(budgeitemsLv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.list_requisitions, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.add:
                return true;
            case R.id.delete:
                //budgeAdapter.remove(budgeAdapter.getItem(info.position));
                if (info.position >= 0 ){
                    final AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(FormRequisitionActivity.this);
                    builder.setTitle("Partidas de Requisición")
                            .setMessage("¿Desea borrar la partida de requisición?")
                            .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    budgeItemRequests.remove(info.position);
                                    DesignUtils.setListViewHeightBasedOnChildrenAdapter(budgeitemsLv);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
            }
                return true;
            case R.id.cancel:
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private String copy(String src, String dst) throws IOException {
        File fOrigin = new File(src);
        File fDest = new File(dst);
        FileInputStream inStream = new FileInputStream(fOrigin);
        FileOutputStream outStream = new FileOutputStream(fDest);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
        return  dst;
    }

    private String inputToFile(Uri uri,String ruta) throws IOException {
        if (isDownloadsDocument(uri)){
            String pathDownload = "";
            final String id = DocumentsContract.getDocumentId(uri);
            uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            String selection = "_id=?";
            String[] selectionArgs = new String[]{id};

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {
                        MediaStore.Images.Media.DATA
                };
                Cursor cursor = null;
                try {
                    cursor = getContentResolver()
                            .query(uri, projection, selection, selectionArgs, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                         pathDownload = cursor.getString(column_index);
                    }
                    if (!pathDownload.isEmpty()){
                        String[] paths =  pathDownload.split("/");
                        String name = paths[paths.length - 1];
                        ruta += name;
                        return copy(pathDownload,ruta);
                    }else{
                        return "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }else{
                return "";
            }
        }else{
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ruta = ruta.contains("/Download") ? ruta.replace("/Download","") : ruta;
            File f = new File(uri.toString().contains("image")? ruta + ".jpg" : ruta);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            OutputStream outStream = new FileOutputStream(f);
            outStream.write(buffer);

            return f.getAbsolutePath();
        }
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private String zip(ArrayList<String> filesm, String numRequisition){
        return  LogicUtils.compressZip(this,numRequisition,filesm);
    }

    private void uploadFiles(final String numRequisition){
        String path = zip(files,numRequisition);
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("zip/*"), file );
        MultipartBody.Part body = MultipartBody.Part.createFormData("zipPackage",file.getPath(),requestFile);

        Call<FilesResponse> call = BioApp.getHicsService().uploadFile("Bearer "+RealmManager.token(), body,Integer.parseInt(numRequisition));
        mProgressDialog = ProgressDialog.show(this, null, "Subiendo...");
        mProgressDialog.setCancelable(false);
        call.enqueue(new Callback<FilesResponse>() {
            @Override
            public void onResponse(Call<FilesResponse> call, Response<FilesResponse> response) {
                mProgressDialog.dismiss();
                if (response.code() == Statics.code_OK_Get){
                    //DesignUtils.successMessage(FormRequisitionActivity.this,"Crear Requisición",response.body().getMessage());
                    budgeItemRequests.clear();
                    deleteFiles();
                    showDialog("Nueva Requisición","Se ha creado tú requisición "+numRequisition + " y " + response.body().getMessage());
                }else{
                    DesignUtils.errorMessage(FormRequisitionActivity.this,"Crear Requisición","Por el momento no es posible subir los archivos");
                }
            }
            @Override
            public void onFailure(Call<FilesResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                t.printStackTrace();
                DesignUtils.errorMessage(FormRequisitionActivity.this,"Crear Requisición",t.getLocalizedMessage());
            }
        });
    }

    private void showDialog(String title, String msg){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        EventBus.getDefault().postSticky(new CloseFormRequisitionEvent(true));
                    }
                })
                .show();
    }

    private void deleteFiles(){
        if (!files.isEmpty()){
            for (String f: files) {
                File file = new  File(f);
                if (file.exists()){
                    Log.d(TAG,"FILE BORRADO "+file.delete());
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        budgeItemRequests.clear();
        idCompanyGlobal = "";
        EventBus.getDefault().postSticky(new CloseFormRequisitionEvent(true));
    }
}
