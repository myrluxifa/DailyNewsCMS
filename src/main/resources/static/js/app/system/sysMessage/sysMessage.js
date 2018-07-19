$(function() {
    var settings = {
        url: ctx + "sysMessage/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                title: $(".sysMessage-table-form").find("input[name='title']").val().trim(),
                flag: $(".sysMessage-table-form").find("select[name='flag']").val()
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
            }, {
                field: 'flag',
                title: '状态',
                formatter: function(value, row, index) {
                    if (value == '0') return '待发布';
                    else if (value == '2') return '已发布';
                }
            }
        ]
    }
    $MB.initTable('sysMessageTable', settings);
});


function search() {
    $MB.refreshTable('sysMessageTable');
}


function refresh() {
    $MB.refreshTable('sysMessageTable');
}


function deleteSysMessage() {
    var selected = $("#sysMessageTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的消息！');
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
        text: "确定删除选中的消息？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'sysMessage/delete', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}



function pushSysMessage() {
    var selected = $("#sysMessageTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要发布的消息！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i != (selected_length - 1)) ids += ",";
        if (2 == selected[i].flag) contain = true;
    }
    if (contain) {
        $MB.n_warning('勾选的系统消息中包含已发布的消息,请确认清除之后重新发布！');
        return;
    }

    $MB.confirm({
        text: "确定发布选中的系统消息？",
        confirmButtonText: "确定发布"
    }, function() {
        $.post(ctx + 'sysMessage/push', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}


