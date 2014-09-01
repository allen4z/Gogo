package com.gogo.domain.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(value={"ownActivities","joinActivities"})
public interface UserFilter {

}
