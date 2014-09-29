package com.gogo.domain.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(value={"belongAct","belongUser"})
public interface RoleFilter {

}
