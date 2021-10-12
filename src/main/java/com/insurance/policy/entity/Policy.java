package com.insurance.policy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "policy")
public class Policy {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "policy_id", nullable = false, unique = true, length = 30)
	private int policyId;
	@Column(name = "policy_name", nullable = false, length = 40)
	private String policyName;
	@Column(name = "policy_description", length = 100)
	private String description;
	@Column(name = "duration", nullable = false, length = 6)
	private int duration;
	@Column(name = "price", nullable = false, length = 9)
	private int price;
	

	
	

	public Policy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Policy(int policyId, String policyName, String description, int duration, int price) {
		super();
		this.policyId = policyId;
		this.policyName = policyName;
		this.description = description;
		this.duration = duration;
		this.price = price;
	}

	public  int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Policy [policyId=" + policyId + ", policyName=" + policyName + ", description=" + description
				+ ", duration=" + duration + ", price=" + price +  "]";
	}

}
