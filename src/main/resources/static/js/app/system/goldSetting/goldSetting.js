$(function() {
    var settings = {
        url: ctx + "goldSetting/list",
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
            	field: 'gold',
            	title: '金币'
            }
        ]
    }
    $MB.initTable('goldSettingTable', settings);
});


function search() {
    $MB.refreshTable('goldSettingTable');
}




