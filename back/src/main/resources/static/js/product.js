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
like.init();

$(document).ready(function(){

     // 1. 채팅하기
     $('.chat').click(function(){

        var userId = $('.chat').attr('val');
        console.log(userId);

            $.ajax({

                type: "POST",
                url: "/api/chat/room/"+userId,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'

            }).done(function(aa){
                console.log(aa);
                location.href=`/chat/room?roomId=`+aa.data.roomId;

            }).fail(function(error){
                console.log(error.responseText);

            })
    })

     // 2. 채팅 목록 조회
     $('.chatlist').click(function(){

        var sellerId = $('.chatlist').attr('val');
        console.log(sellerId);

            $.ajax({

                type: "GET",
                url: "/chat/rooms/"+sellerId

            }).done(function(aa){
                console.log(aa);
                //location.href=`/chat/rooms/`+aa;

            }).fail(function(error){
                console.log(error);

            })
    })


})

function callFunction(obj) {
    alert("업데이트 예정입니다.");
}

