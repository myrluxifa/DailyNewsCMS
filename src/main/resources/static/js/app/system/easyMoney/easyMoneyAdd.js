
	var validator;
	var $easyMoneyAddForm = $("#easyMoney-add-form");
	
	
	var E = window.wangEditor;
    var editor = new E('#editor');
	var $text1 = $('#textare');
    // 或者 var editor = new E( document.getElementById('editor') )
	//editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
	editor.customConfig.customUploadImg = function (files, insert) {
	    // files 是 input 中选中的文件列表
	    // insert 是获取图片 url 后，插入到编辑器的方法

	    // 上传代码返回结果之后，将图片插入到编辑器中
		var fm = new FormData();
		fm.append('file', files[0]);
		$.ajax({
			url:'makeMoney/uploadImg',
			type:'POST',
			data:fm,
			contentType:false,
			processData:false,
			success: function(result){
				insert(result);
			}
		});
	}
	editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text1.val(html);
    }
	editor.create();
	E.fullscreen.init('#editor');
	$text1.val(editor.txt.html());
	
	

	$(function() {
		
        
	    validateRule();
	    

	    $("#easyMoney-add .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $easyMoneyAddForm.validate();
	        var flag = validator.form();
	        if (flag) {
	            if (name == "save") {
	                $.post(ctx + "easyMoney/add", $easyMoneyAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("easyMoneyTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	            if (name == "update") {
	                $.post(ctx + "easyMoney/update", $easyMoneyAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("easyMoneyTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#easyMoney-add .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		$('.img-div').remove();
		validator.resetForm();
		$MB.closeAndRestModal("easyMoney-add");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $easyMoneyAddForm.validate({
	        rules: {
	            title: {
	                required: true,
	                minlength: 10,
	                maxlength: 30,
	               
	            },
	            textare: {
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
	            title: {
	                required: icon + "请输入标题",
	                minlength: icon + "标题长度10到30个字符"
	            },
	            textare: icon + "请填写内容详情"
	        }
	    });
	}

	function getObjectURL(file){
		var url = null;
		if(window.createObjectURL!=undefined){
			url = window.createObjectURL(file);
		}else if(window.URL!=undefined){
			url = window.URL.createObjectURL(file);
		}else if(window.webkitURL!=undefined){
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	}
	
	$("#img_up").change(function(){
		
		var reader=new FileReader();
		  reader.onload=function(e){
		  console.log( reader.result);  //或者 e.target.result都是一样的，都是base64码
		}  
		reader.readAsDataURL(this.files[0]);
		
		reader.onload = function (e) {
		       base64Code=this.result;
		        //把得到的base64赋值到img标签显示
		       $("#img_show").attr("src",base64Code);
		       $('#_img').val(base64Code);
		       $('#imgOld').val('');
		     }
		
		
//		var objUrl = getObjectURL(this.files[0]);
//		if(objUrl){
//			//$(this).next("img").attr("src", objUrl);
//			$("#img1").attr("src",objUrl);
//			$("#img").val(objUrl);
//		}
	})

	

	