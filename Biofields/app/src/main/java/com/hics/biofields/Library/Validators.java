package com.hics.biofields.Library;

import android.app.Activity;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.hics.biofields.Network.Requests.RequisitionItem.BudgeItemRequest;
import com.hics.biofields.Network.Responses.BudgeItemResponse;
import com.hics.biofields.R;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by david.barrera on 9/5/17.
 */

public class Validators {

    public static boolean validateEdt(EditText edt, Activity activity,String question){
        if (edt.getText().toString().trim().isEmpty()){
            edt.requestFocus();
            DesignUtils.errorMessage(activity,"Campo Obligatorio", activity.getString(R.string.validate_field, question));
            return false;
        }
        return true;
    }

    public static boolean validateSpiner(MaterialSpinner sp,Activity activity,String question){
        if (sp.getSelectedItemPosition() < 1){
            /*sp.requestFocus();
            sp.setFocusable(true);*/
            sp.setFocusableInTouchMode(true);
            sp.setError(activity.getString(R.string.validate_field, question));
            return false;
        }
        sp.setError(null);
        return  true;
    }

    public static boolean validateRadioGroup(RadioGroup radioGroup, Activity activity,String question){
        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            radioGroup.requestFocus();
            radioGroup.setFocusable(true);
            radioGroup.setFocusableInTouchMode(true);
            DesignUtils.errorMessage(activity,"Campo Obligatorio", activity.getString(R.string.validate_field, question));
            return false;
        }
        return  true;
    }

    public static boolean validateArrayList(ArrayList<BudgeItemRequest> arrayListl, Activity activity, String question){
        if (arrayListl.isEmpty() && arrayListl.size() <= 0 ){
            DesignUtils.errorMessage(activity,"Campo Obligatorio", activity.getString(R.string.validate_field, question));
            return false;
        }
        return true;
    }

    public static boolean validateArrayListString(ArrayList<String> arrayListl, Activity activity, String question){
        if (arrayListl.isEmpty() && arrayListl.size() <= 0 ){
            DesignUtils.errorMessage(activity,"Campo Obligatorio", activity.getString(R.string.validate_field, question));
            return false;
        }
        return true;
    }

}
