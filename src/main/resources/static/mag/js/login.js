manage.login = {
	clear : function() {
		if ($("#loginForm")) {
			$("#loginForm").form("clear");
		}
	},
	submit : function() {
		if ($("#loginForm")) {
			$("#loginForm").submit();
		}
	}
}
