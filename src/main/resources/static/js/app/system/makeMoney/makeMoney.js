$(function() {
    var settings = {
        url: ctx + "makeMoney/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
            };
        },
        columns: [{
                checkbox: true
            }, {
                field: 'id',
                visible: false
            }, {
                field: 'title',
                title: '标题'
            },
            {
            	field: 'exposition',
            	title: '说明'
            },
            {
            	field: 'type',
            	title: '分类'
            }
            

        ]
    }
    $MB.initTable('makeMoneyTable', settings);
});


function deleteMk() {
    var selected = $("#makeMoneyTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的任务！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i != (selected_length - 1)) ids += ",";
        if (userName == selected[i].username) contain = true;
    }
    if (contain) {
        $MB.n_warning('勾选任务中包含当前登录用户，无法删除！');
        return;
    }

    $MB.confirm({
        text: "确定删除选中的任务？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'makeMoney/delete', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

