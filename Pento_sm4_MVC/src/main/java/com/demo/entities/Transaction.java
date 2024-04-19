package com.demo.entities;
// Generated Mar 15, 2024, 1:31:57 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import jakarta.persistence.*;

/**
 * Transaction generated by hbm2java
 */
@Entity
@Table(name = "transaction")
public class Transaction implements java.io.Serializable {

	private Integer id;
	private Orders orders ;
	private Promotions promotions;
	private String totalAmount;
	private String paymentMethod;
	private Date created;

	public Transaction() {
	}

	public Transaction(Orders orders, String totalAmount, String paymentMethod, Date created) {
		this.orders = orders;
		this.totalAmount = totalAmount;
		this.paymentMethod = paymentMethod;
		this.created = created;
	}

	public Transaction(Orders orders, Promotions promotions, String totalAmount, String paymentMethod,
			Date created) {
		this.orders = orders;
		this.promotions = promotions;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "promotion_id")
	public Promotions getPromotions() {
		return this.promotions;
	}

	

	public void setPromotions(Promotions promotions) {
		this.promotions = promotions;
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
