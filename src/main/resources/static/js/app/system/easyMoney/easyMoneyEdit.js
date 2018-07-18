function updateEasyMoney() {
    var selected = $("#easyMoneyTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的任务！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个任务！');
        return;
    }
    var easyMoneyId = selected[0].id;
    $.post(ctx + "easyMoney/getEasyMoney", { "id": easyMoneyId }, function(r) {
        if (r.code == 0) {
            var $form = $('#easyMoney-add');
            $form.modal();
            var easyMoney = r.msg;
            $("#easyMoney-add-modal-title").html('修改任务');
            $form.find("input[name='id']").val(easyMoney.id);
            $form.find("input[name='title']").val(easyMoney.title);
            $form.find("img[name='img_show']").attr("src",easyMoney.img);
            $form.find("input[name='img']").val(easyMoney.img);
            $form.find("input[name='imgOld']").val(easyMoney.img);
            $form.find("input[name='textare']").val(easyMoney.textare);
            editor.txt.html(easyMoney.textare);
            $("#easyMoney-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}