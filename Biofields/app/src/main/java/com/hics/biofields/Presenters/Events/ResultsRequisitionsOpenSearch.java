package com.hics.biofields.Presenters.Events;

import com.hics.biofields.Network.Responses.RequisitionItemResponse;

import java.util.ArrayList;

/**
 * Created by david.barrera on 9/20/17.
 */

public class ResultsRequisitionsOpenSearch {

    public ArrayList<RequisitionItemResponse> requisitions;

    public ResultsRequisitionsOpenSearch(ArrayList<RequisitionItemResponse> requisitions) {
        this.requisitions = requisitions;
    }


}

