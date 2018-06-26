$(function() {
    var settings = {
        url: ctx + "advert/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                title: $(".advert-table-form").find("input[name='title']").val().trim()
            };
        },
        columns: [{
                checkbox: true
            }, {
                field: 'id',
                visible: false
            },
            {
                field: 'title',
                title: '标题'
            },
            {
            	field: 'url',
            	title: '内容'
            }
            

        ]
    }
    $MB.initTable('advertTable', settings);
});


function search() {
    $MB.refreshTable('advertTable');
}

function deleteAdvert() {
    var selected = $("#advertTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的广告！');
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
        text: "确定删除选中的广告？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'advert/delete', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

