$(function() {
    var settings = {
        url: ctx + "withdraw/list",
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
                field: 'userName',
                title: '用户'
            },
            {
            	field: 'fee',
            	title: '提现金额（元）'
            },
            {
            	field: 'state',
            	title: '状态',
            	formatter: function(value, row, index) {
                    if (value == '0') return '<span class="badge badge-warning">待审批</span>';
                    else if (value == '1') return '<span class="badge badge-success">审批通过</span>';
                    else return '<span class="badge badge-default">审批不通过</span>';
                }
            },
            {
            	field: 'captcha',
            	title: '验证码'
            },
            {
            	field: 'createTime',
            	title: '申请时间'
            },
            {
            	field: 'auditingUser',
            	title: '审批人'
            },
            {
            	field: 'auditingTime',
            	title: '审批时间'
            },
            {
            	field: 'remark',
            	title: '备注'
            }
            

        ]
    }
    $MB.initTable('table', settings);
});


function pass() {
    var selected = $("#table").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请选择想要操作的申请！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i != (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定审批通过选中的申请？",
        confirmButtonText: "审批通过"
    }, function() {
        $.post(ctx + 'withdraw/pass', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                $MB.refreshTable('table');
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function reject() {
    var selected = $("#table").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请选择想要操作的申请！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i != (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "确定拒绝选中的申请？",
        confirmButtonText: "审批不通过"
    }, function() {
        $.post(ctx + 'withdraw/reject', { "ids": ids }, function(r) {
            if (r.code == 0) {
                $MB.n_success(r.msg);
                $MB.refreshTable('table');
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}