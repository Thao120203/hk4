package com.demo.entities;
// Generated Feb 23, 2024, 1:06:06 PM by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

/**
 * Hours generated by hbm2java
 */
@Entity
@Table(name = "hours")
public class Hours implements java.io.Serializable {

	private Integer id;
	private String name;
	private Set<Times> timeses = new HashSet<Times>(0);

	public Hours() {
	}

	public Hours(String name) {
		this.name = name;
	}

	public Hours(String name, Set<Times> timeses) {
		this.name = name;
		this.timeses = timeses;
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

	@Column(name = "name", nullable = false, length = 11)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hours")
	public Set<Times> getTimeses() {
		return this.timeses;
	}

	public void setTimeses(Set<Times> timeses) {
		this.timeses = timeses;
	}

}
