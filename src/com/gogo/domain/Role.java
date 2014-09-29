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
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

/**
 * 活动角色
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_role")
public class Role {

	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="role_id",length=32)
	private String roleId;
	
	//角色编码
	@Column(name="role_code",length=10)
	private String roleCode;
	
	//角色名称
	@Column(name="role_name",length=10)
	private String roleName;

	//角色所在活动
	@ManyToOne
	@JoinColumn(name="belongact")
	@OrderBy("actSignTime ASC")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Activity belongAct;
	
	//角色所有人员
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
