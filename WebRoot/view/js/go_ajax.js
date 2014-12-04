var tokenId="2c9ba38149e639ce0149e63aa80b0000";

/**
 * 封装发送方法
 * 
 * @param params
 *            参数信息
 * @param actionInfo
 *            action信息
 * @param success
 *            成功方法
 * @param failed
 *            失败方法
 */

function postWithOutToken(params, actionInfo, success, failed) {
	var basePath = getBasePathInfo();

	var params4json = JSON.stringify(params);
	var options = {
		type : 'POST',
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		data : params4json,
		success : success,
		error : failed
	};
	options.url = basePath + '/' + actionInfo;
	$.ajax(options);
}
function send4Json(params, actionInfo, success, failed) {
	var basePath = getBasePathInfo();
	//var token = {'access_token':'2c9ba38149e639ce0149e63aa80b0000'};
	//for(var t in token){
	//	params[t] = token[t];
    //}
	
	var params4json = JSON.stringify(params);
	var options = {
		type : 'POST',
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		data : params4json,
		success : success,
		error : failed
	};
	var url = '&access_token=2c9ba38149e639ce0149e63aa80b0000';
	options.url = basePath + '/' + actionInfo+url;
	$.ajax(options);
}

function sendJsonGET(params, actionInfo, success, failed) {
	var basePath = getBasePathInfo();
	var url = basePath + '/' + actionInfo;
	var isFirst = true;
	for ( var key in params) {
		if (isFirst == true) {
			url += '?';
		} else {
			url += '&';
		}
		url += key + '=' + params[key];
	}
	url += '&access_token=2c9ba38149e639ce0149e63aa80b0000';

	var options = {
		type : 'GET',
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		success : success,
		error : failed,
		url : url
	};
	alert(url);
	$.ajax(options);
}

/**
 * 获得路径信息
 * 
 * @returns {String}
 */
function getBasePathInfo() {
	var location = (window.location + '').split('/');
	var basePath = location[0] + '//' + location[2] + '/' + location[3];
	return basePath;
}

function getReturnUrl(urlInfo) {
	return "view/jsp/" + urlInfo + ".jsp";
}
