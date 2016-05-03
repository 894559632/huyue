/**
 * 跟触屏版用户模块相关的命名空间
 */
base.user = {};
base.user.visit = {};
// 清空用户收藏的方法
base.user.visit.clear = function() {
	$.ajax({
		url : "/touch/user/"
		type : "post"
	})
}