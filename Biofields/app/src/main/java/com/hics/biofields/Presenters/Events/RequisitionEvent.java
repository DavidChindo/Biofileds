package com.hics.biofields.Presenters.Events;

import com.hics.biofields.Network.Responses.RequisitionItemResponse;

/**
 * Created by david.barrera on 8/31/17.
 */

public class RequisitionEvent {

    public RequisitionItemResponse requisition;

    public RequisitionEvent(RequisitionItemResponse requisition) {
        this.requisition = requisition;
    }
}
