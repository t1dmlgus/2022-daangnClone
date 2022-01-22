// 상품 등록
function registerProduct(evt) {

    evt.preventDefault();

    var data = $("#upload-form")[0];
    //let data = $("#upload-form").serializeObject();
    var formData = new FormData(data);
    console.log(formData);

    $.ajax({
        type:"POST",
        url:"/api/product/register",
        data : formData,
        contentType: false,                     // 기본 : x-www-form-urlencoded -> 사진전송 못함 , false 로 파싱되는 것을 방지
        processData: false,                     // contentType을 false로 줬을 때, QueryString 자동 설정됨. false 로 해제
        encType: "multipart/form-data",         // ajax나 form에 써도 됨
        dataType: "json"

    }).done(res=>{
        console.log(res);
        alert(res.message);
        location.href=`/product/randing`;
    }).fail(error=>{
        console.log(error);
        alert(JSON.stringify(error.responseText));
    });

}