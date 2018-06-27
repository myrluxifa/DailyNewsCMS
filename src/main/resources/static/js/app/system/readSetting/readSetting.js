$(function() {
    var settings = {
        url: ctx + "readSetting/list",
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
            	field: 'dailyCnt',
            	title: '每日奖励次数'
            },
            {
            	field: 'gold',
            	title: '每日奖励金额'
            },
            {
            	field: 'horCnt',
            	title: '每小时红包次数'
            },
            {
            	field: 'horMoney',
            	title: '红包金额'
            }
        ]
    }
    $MB.initTable('readSettingTable', settings);
});


function search() {
    $MB.refreshTable('readSettingTable');
}




