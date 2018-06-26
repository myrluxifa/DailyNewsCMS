
	var validator;
	var $goldSettingForm = $("#goldSetting-add-form");
	
	
	

	$(function() {
		
		
	    validateRule();
	    

	    $("#goldSetting-edit .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $goldSettingForm.validate();
	        var flag = validator.form();
	        if (flag) {
	            
	            if (name == "update") {
	                $.post(ctx + "goldSetting/update", $goldSettingForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("goldSettingTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#goldSetting-add .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		validator.resetForm();
		$MB.closeAndRestModal("goldSetting-edit");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $goldSettingForm.validate({
	        rules: {
	            gold: {
	                required: true
	            }
	        },
	        errorPlacement: function(error, element) {
	            if (element.is(":checkbox") || element.is(":radio")) {
	                error.appendTo(element.parent().parent());
	            } else {
	                error.insertAfter(element);
	            }
	        },
	        messages: {
	            
	            gold: icon + "请填写参数"
	        }
	    });
	}


	

function updateGoldSetting() {
    var selected = $("#goldSettingTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的参数！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个参数！');
        return;
    }
    var goldSettingId = selected[0].id;
    $.post(ctx + "goldSetting/getGoldSetting", { "id": goldSettingId }, function(r) {
        if (r.code == 0) {
            var $form = $('#goldSetting-edit');
            $form.modal();
            var goldSetting = r.msg;
            $("#goldSetting-add-modal-title").html('修改参数');
            $form.find("input[name='id']").val(goldSetting.id);
            $form.find("input[name='explain']").val(goldSetting.explain);
            $form.find("input[name='gold']").val(goldSetting.gold);
            $("#goldSetting-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}