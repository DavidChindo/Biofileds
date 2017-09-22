package com.hics.biofields.Presenters.Events;

import com.hics.biofields.Network.Responses.RequisitionItemResponse;

/**
 * Created by david.barrera on 8/31/17.
 */

public class RequisitionEvent {

    public int reqNumber;
    public boolean showBtn;

    public RequisitionEvent(int reqNumber,boolean showBtn) {
        this.reqNumber = reqNumber;
        this.showBtn = showBtn;
    }
}
