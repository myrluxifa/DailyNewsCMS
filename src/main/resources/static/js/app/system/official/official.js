$(function() {
    var settings = {
        url: ctx + "official/list",
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
                field: 'name',
                title: '文案位置'
            }
        ]
    }
    $MB.initTable('officialTable', settings);
});


function search() {
    $MB.refreshTable('officialTable');
}




