package com.demo.entities;
// Generated Feb 1, 2024, 10:59:46 AM by Hibernate Tools 4.3.6.Final

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Transaction generated by hbm2java
 */
@Entity
@Table(name = "transaction", catalog = "pento_sm4")
public class Transaction implements java.io.Serializable {

	private Integer id;
	private int orderId;
	private String totalAmount;
	private String paymentMethod;
	private Date created;

	public Transaction() {
	}

	public Transaction(int orderId, String totalAmount, String paymentMethod, Date created) {
		this.orderId = orderId;
		this.totalAmount = totalAmount;
		this.paymentMethod = paymentMethod;
		this.created = created;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "order_id", nullable = false)
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Column(name = "total_amount", nullable = false, length = 250)
	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "payment_method", nullable = false, length = 250)
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created", nullable = false, length = 10)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}