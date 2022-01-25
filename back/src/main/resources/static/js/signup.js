
jQuery.fn.serializeObject = function() {
  var obj = null;
  try {
      if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) {
          var arr = this.serializeArray();
          if(arr){ obj = {};
          jQuery.each(arr, function() {
              obj[this.name] = this.value; });
          }
      }
  }catch(e) {
      alert(e.message);
  }finally {}
  return obj;
}

// (1) 회원가입
function join(evt) {

    evt.preventDefault();

    //let data = $("#signupForm").serialize();
    let data = $("#signupForm").serializeObject();
    console.log(data);

    $.ajax({
        type:"POST",
        url:"/api/user/signup",
        data: JSON.stringify(data),
        contentType:"application/json; charset=utf-8",
        dataType:"json"

    }).done(res=>{
        console.log(res);
        alert(res.message);
        location.href=`/`;
    }).fail(error=>{
        console.log(error);
        alert(error.responseJSON.data.email);
    });

}