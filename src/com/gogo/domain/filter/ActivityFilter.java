package com.gogo.domain.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(value={"ownUser","roles"})
public interface ActivityFilter {

}
