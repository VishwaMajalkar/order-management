package com.vish.example.order.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * This Class is base class for all beans
 * Created by Vishwanath on 16/03/2019.
 */
@Data
public class BaseBean implements Serializable {

    private static final long serialVersionUID = 4738762788882L;

    private String createdBy;
    private Date createdDt;
    private String modifiedBy;
    private Date modifiedDt;
}
