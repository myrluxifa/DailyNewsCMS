function updateinviteImgBanner() {
    var selected = $("#inviteImgBannerTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的banner！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个banner！');
        return;
    }
    var inviteImgBannerId = selected[0].id;
    $.post(ctx + "inviteImgBanner/getInviteImgBanner", { "id": inviteImgBannerId }, function(r) {
        if (r.code == 0) {
            var $form = $('#inviteImgBanner-add');
            $form.modal();
            var inviteImgBanner = r.msg;
            $("#inviteImgBanner-add-modal-title").html('修改banner');
            $form.find("input[name='id']").val(inviteImgBanner.id);
            $form.find("input[name='sort']").val(inviteImgBanner.sort);
            $form.find("input[name='url']").val(inviteImgBanner.url);
            $('#add-img').attr("src",inviteImgBanner.imgUrl);
			$("input[name='imgUrl']").val(inviteImgBanner.imgUrl);
            
            $("#inviteImgBanner-add-button").attr("name", "update");
            
        } else {
            $MB.n_danger(r.msg);
        }
    });
}