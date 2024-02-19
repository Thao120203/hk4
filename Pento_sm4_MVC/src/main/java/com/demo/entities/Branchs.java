package com.demo.entities;
// Generated Feb 1, 2024, 10:59:46 AM by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;

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

/**
 * Branchs generated by hbm2java
 */
@Entity
@Table(name = "branchs", catalog = "pento_sm4")
public class Branchs implements java.io.Serializable {

	private Integer id;
	private String name;
	private String address;
	private String phoneNumber;
	private String openingHours;
	private String clossingHours;
	private String status;
	private Set<Tables> tableses = new HashSet<Tables>(0);

	public Branchs() {
	}

	public Branchs(String name, String address, String phoneNumber, String openingHours, String clossingHours,
			String status) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.openingHours = openingHours;
		this.clossingHours = clossingHours;
		this.status = status;
	}

	public Branchs(String name, String address, String phoneNumber, String openingHours, String clossingHours,
			String status, Set<Tables> tableses) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.openingHours = openingHours;
		this.clossingHours = clossingHours;
		this.status = status;
		this.tableses = tableses;
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

	@Column(name = "name", nullable = false, length = 250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", nullable = false, length = 250)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone_number", nullable = false, length = 250)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "opening_hours", nullable = false, length = 250)
	public String getOpeningHours() {
		return this.openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	@Column(name = "clossing_hours", nullable = false, length = 250)
	public String getClossingHours() {
		return this.clossingHours;
	}

	public void setClossingHours(String clossingHours) {
		this.clossingHours = clossingHours;
	}

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "branchs")
	public Set<Tables> getTableses() {
		return this.tableses;
	}

	public void setTableses(Set<Tables> tableses) {
		this.tableses = tableses;
	}

}
