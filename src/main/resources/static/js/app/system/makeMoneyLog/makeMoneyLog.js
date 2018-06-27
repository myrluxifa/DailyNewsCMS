$(function() {
    var settings = {
        url: ctx + "makeMoneyLog/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                status: $(".makeMoneyLog-table-form").find("select[name='status']").val(),
                title: $(".makeMoneyLog-table-form").find("input[name='title']").val()
            };
        },
        columns: [{
                checkbox: true
            }, {
                field: 'id',
                visible: false
            }, {
                field: 'userName',
                title: '手机号'
            },
            {
            	field: 'title',
            	title: '标题'
            },
            {
            	field: 'createTime',
            	title: '报名时间'
            }
            ,
            {
            	field: 'endTime',
            	title: '截至时间'
            }
            ,
            {
            	field: 'status',
            	title: '状态',
            	formatter: function(value, row, index) {
                    if (value == '1') return '待上传';
                    else if (value == '2') return '待审批';
                    else if (value=='3') return '审批通过';
                    else if (value=='4') return '审批不通过';
                    else if (value=='5') return '取消';
                    else if (value=='6') return '过期';
                }
            }

        ]
    }
    $MB.initTable('makeMoneyLogTable', settings);
});


function search() {
    $MB.refreshTable('makeMoneyLogTable');
}




