package org.kuxa.trackipaltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    private Long shopifyOrderId;

    @JsonProperty("order_number")
    private Long orderNumber;

    @JsonProperty("created_at")
    private Date orderDate;

    @JsonProperty("total_price")
    private Double total;

    @JsonProperty("financial_status")
    private String financialStatus;

    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;

    @Transient
    private boolean isFulfilled;

    @Transient
    private String trackingCode;

    // No-argument constructor
    public Order() {
        // no-arg constructor
    }

    public Order(Long id, Long shopifyOrderId, Long orderNumber, Date orderDate, Double total,
                 String financialStatus, String fulfillmentStatus, boolean isFulfilled, String trackingCode) {
        this.id = id;
        this.shopifyOrderId = shopifyOrderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.total = total;
        this.financialStatus = financialStatus;
        this.fulfillmentStatus = fulfillmentStatus;
        this.isFulfilled = isFulfilled;
        this.trackingCode = trackingCode;
    }
}
