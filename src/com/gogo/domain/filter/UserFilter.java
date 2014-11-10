package com.gogo.domain.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(value={"name","password"})
public interface UserFilter {

}
