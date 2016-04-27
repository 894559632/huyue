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
		return document.getElementByTagName(tagName);
	},
	getC : function(className) {
		return document.getElementByClassName(className);
	}
};