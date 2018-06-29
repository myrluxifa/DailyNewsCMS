$(function() {
    var settings = {
        url: ctx + "userLogin/list",
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
                title: '手机号'
            },
            {
            	field: 'gold',
            	title: '金币'
            },
            {
            	field: 'inviteCode',
            	title: '邀请人码'
            },
            {
            	field: 'myInviteCode',
            	title: '自己的邀请码'
            }
            ,
            {
            	field: 'balance',
            	title: '余额'
            }
            

        ]
    }
    $MB.initTable('userLoginTable', settings);
});




