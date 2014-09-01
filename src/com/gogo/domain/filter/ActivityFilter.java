package com.gogo.domain.filter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(value={"ownUser","joinUser"})
public interface ActivityFilter {

}
