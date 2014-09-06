package com.gogo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private int roleId;
	
	@Column(name="role_code",length=10)
	private String roleCode;
	
	@Column(name="role_name",length=10)
	private String roleName;
	
	@ManyToOne
	@JoinColumn(name="belongact")
	@OrderBy("actSignTime ASC")
	private Activity belongAct;
	
	@OneToMany(mappedBy="role",cascade=CascadeType.ALL)
	private Set<UserAndRole> belongUser;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Activity getBelongAct() {
		return belongAct;
	}

	public void setBelongAct(Activity belongAct) {
		this.belongAct = belongAct;
	}

	public Set<UserAndRole> getBelongUser() {
		return belongUser;
	}

	public void setBelongUser(Set<UserAndRole> belongUser) {
		this.belongUser = belongUser;
	}


	
}
