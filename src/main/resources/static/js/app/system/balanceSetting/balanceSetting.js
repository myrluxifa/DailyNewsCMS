$(function() {
    var settings = {
        url: ctx + "balanceSetting/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1
            };
        },
        columns: [{
                checkbox: true
            }, {
                field: 'id',
                visible: false
            }, {
                field: 'explain',
                title: '说明'
            },
            {
            	field: 'money',
            	title: '现金'
            }
        ]
    }
    $MB.initTable('balanceSettingTable', settings);
});


function search() {
    $MB.refreshTable('balanceSettingTable');
}




