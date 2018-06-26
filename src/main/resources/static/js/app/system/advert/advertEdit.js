function updateAdvert() {
    var selected = $("#advertTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的广告！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个广告！');
        return;
    }
    var advertId = selected[0].id;
    $.post(ctx + "advert/getAdvert", { "id": advertId }, function(r) {
        if (r.code == 0) {
            var $form = $('#advert-add');
            $form.modal();
            var advert = r.msg;
            $("#advert-add-modal-title").html('修改广告');
            $form.find("input[name='id']").val(advert.id);
            $form.find("input[name='title']").val(advert.title);
            $form.find("input[name='url']").val(advert.url);
           
            $('.img-div').remove();
            
            var is=advert.imgs.split('$lvmq$');
            for(var i=0;i<is.length;i++){
            	$("#imgs_up").before('<div class="img-div" style="margin-top:10px;">'
			       		+'<img style="width:250px;height:200px" src="'+is[i]+'" >'
			       		+'<input type="hidden" name="_imgs" value="'+is[i]+'">'
			       		+'<button  type="button"   class="btn btn-secondary remove-img">移除</button>'
			       	+'</div>');
            }
            
            $("#advert-add-button").attr("name", "update");
            
        } else {
            $MB.n_danger(r.msg);
        }
    });
}