package org.kuxa.trackipaltest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    @JsonProperty("id")
    private Long shopifyOrderId;

    @JsonProperty("order_number")
    private String orderNumber;

    @JsonProperty("created_at")
    private Date orderDate;

    @JsonProperty("total_price")
    private Double total;

    @JsonProperty("financial_status")
    private String financialStatus;

    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;

    public boolean isFulfilled() {
        return "fulfilled".equalsIgnoreCase(fulfillmentStatus);
    }

    public void setFulfilled(boolean isFulfilled) {
        this.fulfillmentStatus = isFulfilled ? "fulfilled" : "unfulfilled";
    }

    private String trackingCode;

    // Custom constructor if needed for specific initialization
    public Order(Long shopifyOrderId, String orderNumber, Date orderDate, Double total,
                 String financialStatus, String fulfillmentStatus) {
        this.shopifyOrderId = shopifyOrderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.total = total;
        this.financialStatus = financialStatus;
        this.fulfillmentStatus = fulfillmentStatus;
    }
}
