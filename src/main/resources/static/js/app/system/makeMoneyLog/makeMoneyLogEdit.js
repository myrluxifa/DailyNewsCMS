function updatemakeMoneyLog() {
    var selected = $("#makeMoneyLogTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要审批得条目！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能审批一条！');
        return;
    }
    if (!(selected[0].status==2)){
    	$MB.n_warning('只有[待审批]状态可以进行审批');
    	return;
    }
    var makeMoneyLogId = selected[0].id;
    $.post(ctx + "makeMoneyLog/getMakeMoneyLog", { "id": makeMoneyLogId }, function(r) {
        if (r.code == 0) {
            var $form = $('#makeMoneyLog-add');
            $form.modal();
            var makeMoneyLog = r.msg;
            $("#makeMoneyLog-add-modal-title").html('审批');
            $form.find("input[name='id']").val(makeMoneyLog.id);
            $form.find("input[name='title']").val(makeMoneyLog.title);
            $form.find("input[name='userName']").val(makeMoneyLog.userName);
            
            $('.img-div').remove();
            
            var is=makeMoneyLog.imgs.split('$lvmq$');
            for(var i=0;i<is.length;i++){
            	$("#imgs_up").before('<div class="img-div" style="margin-top:10px;">'
			       		+'<img style="width:250px;height:200px" src="'+is[i]+'" >'
			       		+'<input type="hidden" name="_imgs" value="'+is[i]+'">'
			       	+'</div>');
            }
            
            $("#makeMoneyLog-add-button").attr("name", "update");
            
        } else {
            $MB.n_danger(r.msg);
        }
    });
}