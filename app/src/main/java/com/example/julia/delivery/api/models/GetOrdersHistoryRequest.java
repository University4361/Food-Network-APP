package com.example.julia.delivery.api.models;

import java.util.Date;

/**
 * Created by Lavrov on 20.05.2018.
 */

public class GetOrdersHistoryRequest extends BaseRequest {
    private Date fromDate;

    private Date toDate;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
