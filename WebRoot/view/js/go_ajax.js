/**
 * 封装发送方法
 * @param params 参数信息
 * @param actionInfo action信息
 * @param success 成功方法
 * @param failed 失败方法
 */
function send4Json(params,actionInfo,success,failed){
	var basePath = getBasePathInfo();
	var params4json = JSON.stringify(params);
	var options = {
			type: 'POST', 
			dataType: 'json',
	        contentType:'application/json;charset=UTF-8', 
			data:params4json,
			success: success,
	        error:failed
		};
	options.url = basePath+'/'+actionInfo;
	$.ajax(options);
}

/**
 * 获得路径信息
 * @returns {String}
 */
function getBasePathInfo(){
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
	return basePath;
}

function getReturnUrl(urlInfo){
	return "view/jsp/"+urlInfo+".jsp";
}


