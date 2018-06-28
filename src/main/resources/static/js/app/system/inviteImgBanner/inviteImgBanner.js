$(function() {
    var settings = {
        url: ctx + "inviteImgBanner/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                title: $(".inviteImgBanner-table-form").find("input[name='title']").val().trim()
            };
        },
        columns: [{
                checkbox: true
            }, {
                field: 'id',
                visible: false
            },
            {
                field: 'url',
                title: '标题'
            },
            {
                field: 'imgUrl',
                title: '图片',
                formatter:function(value,row,index){
                	return '<img  src="'+value+'" class="img-rounded" style="width:250px;height:200px">';
                }
            },
            {
            	field: 'sort',
            	title: '顺序'
            }
            

        ]
    }
    $MB.initTable('inviteImgBannerTable', settings);
});


function search() {
    $MB.refreshTable('inviteImgBannerTable');
}

function deleteinviteImgBanner() {
    var selected = $("#inviteImgBannerTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的banner！');
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
        text: "确定删除选中的banner？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'inviteImgBanner/delete', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

