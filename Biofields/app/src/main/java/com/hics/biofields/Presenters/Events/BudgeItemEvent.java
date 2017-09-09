package com.hics.biofields.Presenters.Events;

import com.hics.biofields.Network.Requests.RequisitionItem.BudgeItemRequest;

/**
 * Created by david.barrera on 9/5/17.
 */

public class BudgeItemEvent {

    public BudgeItemRequest budgeItemRequest;

    public BudgeItemEvent(BudgeItemRequest budgeItemRequest) {
        this.budgeItemRequest = budgeItemRequest;
    }
}
