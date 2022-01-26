var like ={
    init: function(){
        $('#likebtn').click(function(){           // 좋아요 클릭

                console.log($('#likebtn').children().attr('val'));

                var data = $('#likebtn').attr('val');
                if($('#likebtn').children().attr('val')=="true"){

                    like.like(data);
                }else{
                    like.unlike(data);
                }


                console.log($('#likebtn').children().attr('val')=="true");
                if($('#likebtn').children().attr('val')=="true"){
                    $('#likebtn').children().children('img').attr("src","/img/likes.png");
                }else{
                    $('#likebtn').children().children('img').attr("src","/img/heart.png");
                }

            });

    },

        like: function(data){
             console.log(data);

            $.ajax({

                type: "DELETE",
                url: "/api/likes/"+data,
                contentType: 'application/json; charset=utf-8',
                dataType: 'json'

            }).done(function(aa){

                alert("좋아요 취소💔");
                $('#likebtn').children('div').attr('val','false');

            }).fail(function(error){
                alert('좋아요 등록 에러');
            })


        },
        unlike: function(data){

            console.log(data);

            $.ajax({

                type: "POST",
                url: "/api/likes/"+data,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'

            }).done(function(aa){
                alert("좋아요❤");
                $('#likebtn').children('div').attr('val','true');

            }).fail(function(error){
                alert('좋아요 취소 에러');

            })



        }


}

function callFunction(obj) {
    alert("업데이트 예정입니다.");
}

like.init();
