package com.demo.entities;
// Generated Feb 23, 2024, 1:06:06 PM by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

/**
 * Times generated by hbm2java
 */
@Entity
@Table(name = "times")
public class Times implements java.io.Serializable {

	private Integer id;
	private Days days;
	private Hours hours;
	private Months months;
	private Set<Orders> orderses = new HashSet<Orders>(0);

	public Times() {
	}

	public Times(Days days, Hours hours, Months months) {
		this.days = days;
		this.hours = hours;
		this.months = months;
	}

	public Times(Days days, Hours hours, Months months, Set<Orders> orderses) {
		this.days = days;
		this.hours = hours;
		this.months = months;
		this.orderses = orderses;
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
	@JoinColumn(name = "day_id", nullable = false)
	public Days getDays() {
		return this.days;
	}

	public void setDays(Days days) {
		this.days = days;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hour_id", nullable = false)
	public Hours getHours() {
		return this.hours;
	}

	public void setHours(Hours hours) {
		this.hours = hours;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "month_id", nullable = false)
	public Months getMonths() {
		return this.months;
	}

	public void setMonths(Months months) {
		this.months = months;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "times")
	public Set<Orders> getOrderses() {
		return this.orderses;
	}

	public void setOrderses(Set<Orders> orderses) {
		this.orderses = orderses;
	}

}
