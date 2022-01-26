let page = 1;

// (1) 스토리 로드하기
function storyLoad() {

    $.ajax({

        url:`/api/product/randing?page=${page}`,
        dataType:"json",


    }).done(res=>{
        console.log(res);
        console.log(page);
        res.data.forEach(element=>{
//            console.log(element);
            let storyItem = getStoryItem(element);
            $("#sec_content").append(storyItem);
        })
    }).fail(error=>{
        console.log('오류', error);
    });
}

storyLoad();

function getStoryItem(element) {

    let item =`
               <div class="cont">
                    <div class="cont_img">
                        <a href="/product/${element.productId}">
                            <img src="${element.coverImage}" alt="상품_이미지">
                        </a>
                    </div>
                    <div class="cont_desc">
                        <div>
                            <div class="title">${element.title}</div>
                            <div class="gray1">
                                <div class="location">${element.place}</div>
                                <div class="registerTime">${element.registerTime}</div>
                            </div>
                            <div class="price">${element.price}</div>
                            <div class="won">원</div>
                        </div>
                        <div class="service">
                            <div href="#" class="comment">
                                <img src="/img/comment.png" alt="댓글">
                                <span class="commentCount">n</span>
                            </div>
                            <div>
                            `
                            if(element.likesStatus){
                                item +=`<img class="likeStatus" src="/img/heart.png" alt="좋아요">`
                            }else{
                                item +=`<img class="likeStatus" src="/img/likes.png" alt="빈 좋아요">`
                            }
                            item +=
                            `
                            </div>
                            <span class="likeCount">${element.likesCount}</span>
                        </div>
                    </div>
               </div>
    `
    return item;
}


// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {

//    console.log("스크롤중");
//    console.log("윈도우 scrollTop", $(window).scrollTop());
//    console.log("문서의 높이", $(document).height());
//    console.log("윈도우 높이", $(window).height());

    // 윈도우 scrollTop = 문서의 높이 - 윈도우 높이
    let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
    console.log(checkNum);

    if( checkNum< 1 && checkNum > -1){
        page++;
        console.log(page);
        storyLoad();
    }
});