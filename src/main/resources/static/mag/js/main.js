manage.main = {
	tab : {
		addTab : function(text) {
			if ($("#main_tabs").tabs("exists", text)) {
				$("#main_tabs").tabs("select", text);
			} else {
				$("#main_tabs").tabs("add", {
					title : text,
					closable : true
				});

				// 获取当前被选中的选项卡
				var currentTabPanel = $("#main_tabs").tabs("getSelected");

				// 动态创建一个table用于形成easy-ui表格
				var dynamicTable = $("<table></table>");

				currentTabPanel.html(dynamicTable);

				// 动态获取表头
				var columns = manage.main.grid.getGridMapper(text);
				console.log(columns);

				// 将普通表格渲染为easy-ui表格
				dynamicTable.datagrid({
					url : "/Verwalter/ad/type/get",
					rownumbers : true,
					fitColumns : true,
					columns : columns
				});
			}
		}
	},
	grid : {
		// 列属性与标题对应关系
		getGridMapper : function(title) {
			var columns;
			switch (title) {
			case "广告位管理":
				columns = [ [
						{
							field : "title",
							title : "名称",
							width : 100,
							sortable : true
						},
						{
							field : "createTime",
							title : "创建时间",
							formatter : function(val, row) {
								if (val) {
									var d = new Date(val);
									var date = (d.getFullYear()) + "-"
											+ (d.getMonth() + 1) + "-"
											+ (d.getDate()) + " "
											+ (d.getHours()) + ":"
											+ (d.getMinutes()) + ":"
											+ (d.getSeconds());
									return date;
								}
							},
							width : 100,
							sortable : true
						},
						{
							field : "sortId",
							title : "排序号",
							width : 100,
							sortable : true
						},
						{
							field : "operate",
							title : "操作",
							width : 100,
						} ] ];
				break;
			}
			return columns;
		}
	}
}