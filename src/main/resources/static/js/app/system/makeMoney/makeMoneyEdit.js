function updateMakeMoney() {
    var selected = $("#makeMoneyTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的任务！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个任务！');
        return;
    }
    var makeMoneyId = selected[0].id;
    $.post(ctx + "makeMoney/getMakeMoney", { "id": makeMoneyId }, function(r) {
        if (r.code == 0) {
            var $form = $('#makeMoney-add');
            $form.modal();
            var makeMoney = r.msg;
            $("#makeMoney-add-modal-title").html('修改任务');
            $form.find("input[name='id']").val(makeMoney.id);
            $form.find("input[name='title']").val(makeMoney.title);
            $form.find("img[name='img_show']").attr("src",makeMoney.logo);
            $form.find("input[name='logo']").val(makeMoney.logo);
            $form.find("input[name='logoOld']").val(makeMoney.logo);
            $form.find("input[name='url']").val(makeMoney.url);
            $form.find("input[name='exposition']").val(makeMoney.exposition);
            $form.find("input[name='cash']").val(makeMoney.cash);
            $form.find("input[name='lineOne']").val(makeMoney.lineOne);
            $form.find("input[name='lineTwo']").val(makeMoney.lineTwo);
            $form.find("input[name='lineThree']").val(makeMoney.lineThree);
            $form.find("input[name='lineFour']").val(makeMoney.lineFour);
            $form.find("input[name='participantsNum']").val(makeMoney.participantsNum);
            $form.find("input[name='rewardsType']").val(makeMoney.rewardsType);
            $form.find("input[name='cycle']").val(makeMoney.cycle);
            $form.find("input[name='timeLimit']").val(makeMoney.timeLimit);
            $form.find("input[name='introduce']").val(makeMoney.introduce);
            editor.txt.html(makeMoney.introduce);
            
            $('.img-div').remove();
            
            var is=makeMoney.imgs.split('$lvmq$');
            for(var i=0;i<is.length;i++){
            	$("#imgs_up").before('<div class="img-div" style="margin-top:10px;">'
			       		+'<img style="width:250px;height:200px" src="'+is[i]+'" >'
			       		+'<input type="hidden" name="_imgs" value="'+is[i]+'">'
			       		+'<button  type="button"   class="btn btn-secondary remove-img">移除</button>'
			       	+'</div>');
            }
            
            $("#makeMoney-add-button").attr("name", "update");
            
        } else {
            $MB.n_danger(r.msg);
        }
    });
}