$(function() {
    var settings = {
        url: ctx + "news/list",
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
            	field: 'catValue',
            	title: '分类'
            },
            {
            	field: 'publishDate',
            	title: '发布时间'
            }
            

        ]
    }
    $MB.initTable('newsTable', settings);
});


function deleteNews() {
    var selected = $("#newsTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的新闻！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i != (selected_length - 1)) ids += ",";
        if (userName == selected[i].username) contain = true;
    }
    if (contain) {
        $MB.n_warning('勾选新闻中包含当前登录用户，无法删除！');
        return;
    }

    $MB.confirm({
        text: "确定删除选中的新闻？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'news/delete', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function jPushNews() {
    var selected = $("#newsTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要推送的新闻！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i != (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定推送选中的新闻？",
        confirmButtonText: "确定"
    }, function() {
        $.post(ctx + 'news/jpush', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}