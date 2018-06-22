
	var validator;
	var $easyMoneyAddForm = $("#makeMoney-add-form");
	
	
	

	$(function() {
		
		var E = window.wangEditor;
        var editor = new E('#editor');
		var $text1 = $('#introduce');
        // 或者 var editor = new E( document.getElementById('editor') )
		editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
		editor.customConfig.onchange = function (html) {
            // 监控变化，同步更新到 textarea
            $text1.val(html);
        }
		editor.create();
		E.fullscreen.init('#editor');
		$text1.val(editor.txt.html());
        
	    validateRule();
	    
	    
	    

	    $("#makeMoney-add .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $easyMoneyAddForm.validate();
	        var flag = validator.form();
	        if (flag) {
	            if (name == "save") {
	                $.post(ctx + "makeMoney/add", $easyMoneyAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("easyMoneyTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	            if (name == "update") {
	                $.post(ctx + "makeMoney/update", $easyMoneyAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("easyMoneyTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#makeMoney-add .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		validator.resetForm();
		$MB.closeAndRestModal("makeMoney-add");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $easyMoneyAddForm.validate({
	        rules: {
	            title: {
	                required: true,
	                minlength: 3,
	                maxlength: 10,
	               
	            },
	            logo:{
	            	required: true
	            },
	            cash:{
	            	required: true
	            },
	            lineOne:{
	            	required: true
	            },
	            lineTwo:{
	            	required: true
	            },
	            lineThree:{
	            	required: true
	            },
	            lineFour:{
	            	required: true
	            },
	            participantsNum:{
	            	required: true
	            },
	            cycle:{
	            	required: true
	            },
	            introduce:{
	            	required: true
	            },
	            time_limit:{
	            	required: true
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
	                minlength: icon + "标题长度3到10个字符"
	            },
	            logo:{
	            	required: icon+"此项不能为空"
	            },
	            cash:{
	            	required: icon+"此项不能为空"
	            },
	            lineOne:{
	            	required: icon+"此项不能为空"
	            },
	            lineTwo:{
	            	required: icon+"此项不能为空"
	            },
	            lineThree:{
	            	required: icon+"此项不能为空"
	            },
	            lineFour:{
	            	required: icon+"此项不能为空"
	            },
	            participantsNum:{
	            	required: icon+"此项不能为空"
	            },
	            cycle:{
	            	required: icon+"此项不能为空"
	            },
	            introduce:{
	            	required: icon+"此项不能为空"
	            },
	            time_limit:{
	            	required: icon+"此项不能为空"
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
		       $('#logo').val(base64Code);
		       $('#imgOld').val('');
		     }
		
		
//		var objUrl = getObjectURL(this.files[0]);
//		if(objUrl){
//			//$(this).next("img").attr("src", objUrl);
//			$("#img1").attr("src",objUrl);
//			$("#img").val(objUrl);
//		}
	})
	
	
	$("#imgs_up").change(function(){
		
		var reader=new FileReader();
		  reader.onload=function(e){
		  console.log( reader.result);  //或者 e.target.result都是一样的，都是base64码
		}  
		reader.readAsDataURL(this.files[0]);
		
		reader.onload = function (e) {
		       base64Code=this.result;
		        //把得到的base64赋值到img标签显示
		       $("#imgs_up").before('<div class="img-div" style="margin-top:10px;">'
			       		+'<img style="width:250px;height:200px" src="'+base64Code+'" >'
			       		+'<button  type="button"   class="btn btn-secondary remove-img">移除</button>'
			       	+'</div>');
		     }
		
	})
	
	$('.imgs-div').on("click","button",function(){
		$(this).parent().remove();
	})
	
	function removeImg(){
		alert(1);
		$(this).prev().remove();
	}
	
	

	