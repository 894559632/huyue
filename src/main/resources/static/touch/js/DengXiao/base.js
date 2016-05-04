/**
 * 基础命名空间，包含简写的dom节点获取方法
 * 
 * @author DengXiao
 */
var base = {
	getE : function(id) {
		return document.getElementById(id);
	},
	getT : function(tagName) {
		return document.getElementsByTagName(tagName);
	},
	getC : function(className) {
		return document.getElementsByClassName(className);
	},
	contains : function(array, obj) {
		var isHave = false;
		for (var i = 0; i < array.length; i++) {
			if (array[i] == obj) {
				isHave = true;
				break;
			}
		}
		return isHave;
	}
};