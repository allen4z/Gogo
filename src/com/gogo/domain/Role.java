package com.gogo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_role")
public class Role {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="role_id",length=32)
	private String roleId;
	
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



	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
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
