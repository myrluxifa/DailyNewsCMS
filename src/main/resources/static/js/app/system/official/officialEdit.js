
	var validator;
	var $officialForm = $("#official-add-form");
	
	
	var E = window.wangEditor;
    var editor = new E('#editor');
	var $text1 = $('#details');
    // 或者 var editor = new E( document.getElementById('editor') )
	editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
	editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text1.val(html);
    }
	editor.create();
	E.fullscreen.init('#editor');
	$text1.val(editor.txt.html());
	

	$(function() {
		
	    validateRule();
	    

	    $("#official-edit .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $officialForm.validate();
	        var flag = validator.form();
	        if (flag) {
	            
	            if (name == "update") {
	                $.post(ctx + "official/update", $officialForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("officialTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#official-edit .btn-close").click(function() {
	    	editor.txt.html('');
	        closeModal();
	    });

	});

	function closeModal() {
		$MB.closeAndRestModal("official-edit");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $officialForm.validate({
	        rules: {
	            details: {
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
	            
	            details: icon + "请填写参数"
	        }
	    });
	}


	

function updateofficial() {
    var selected = $("#officialTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的参数！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个参数！');
        return;
    }
    var officialId = selected[0].id;
    $.post(ctx + "official/getofficial", { "id": officialId }, function(r) {
        if (r.code == 0) {
            var $form = $('#official-edit');
            $form.modal();
            var official = r.msg;
            $("#official-add-modal-title").html('修改参数');
            $form.find("input[name='id']").val(official.id);
            $form.find("input[name='name']").val(official.name);
            $form.find("input[name='type']").val(official.type);
            $form.find("textarea[name='details']").val(official.details);
            
            editor.txt.html(official.details);
            $("#official-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}