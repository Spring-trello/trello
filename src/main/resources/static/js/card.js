// board.html의 card 관련 js코드

$(document).on('nestableDragStop', function (event, el) {
    // el을 받아와서 원하는 로직을 수행함
    const columnId = $(el).closest('.column').attr('id').split('-')[1];
    const cardId = $(el).attr('id').split('-')[1];
    const cardPos = $(el).index();

    const originalColumnId = $(el).find('#originalColumnId').val();
    const originalPosition = $(el).find('#cardPosition').val();

    console.log(originalPosition);
    const authToken = getAuthorizationToken();

    // 드롭 순간의 정보를 해당 카드로부터 가져오기

    $.ajax({
        url: `/cards/${cardId}/to/${columnId}/${cardPos}`,
        method: 'PUT',
        headers: {
            'Authorization': authToken
        },
        contentType: 'application/json',
        data: JSON.stringify({
            originalColumnId: originalColumnId,// 드롭한 순간에 서버에서 조회한 카드 정보,
            originalPosition: originalPosition
        }),
        success:

            function (card) {
                console.log('카드 이동이 성공적으로 완료되었습니다. ')
                $(el).find('originalColumnId').val(originalColumnId);
                $(el).find('originalPosition').val(originalPosition);

                console.log($(el).find('originalPosition').val());
                location.reload();
            }
        ,
        error: function (error) {
            console.error('카드 이동 중 오류가 발생했습니다:', error);
        }
    })
    ;

})
;

// const commentTemplate =
//     `
//         <li class="list-group-item" id="comment-{commentId}">
//         <table style="width: 100%;">
//             <tr>
//                 <td style="width: 50%;">
//                     <p class="comment-author">{email}</p>
//                 </td>
//                 <td style="width: 50%; text-align: right;">
//                     <p class="comment-time">{modified_at}</p>
//                 </td>
//             </tr>
//             <tr>
//                 <td colspan="2">
//                     <hr class="comment-divider">
//                     <p class="comment-contents">{contents}</p>
//                 </td>
//             </tr>
//         </table>
//     </li>
//     `

const commentTemplate =
    `
    <li class="list-group-item" id="comment-{comment_id}">
        <span class="deleteComment" onclick="deleteComment($(this).closest('.list-group-item'))">&times;</span>
        <div class="comment-info">
            <div class="comment-author">{email}</div>
            <div class="comment-time">{modified_at}</div>
        </div>
        <div class="comment-contents">
            <p>{contents}</p>
        </div>
    </li>
    `

// comment-icon 클릭 시 card 댓글을 표시하는 모달 토글
function loadComments(card) {
    const cardId = $(card).attr('id').split('-')[1];
    const cardName = $(card).find('h3').text();
    const authToken = getAuthorizationToken();

    // 숨겨진 input에 카드ID 전달
    $('#formCardId').val(cardId);

    // 전체 코멘트 화면에서 삭제
    $('#comment-list').empty();

    // 모달 제목 변경
    $('#commentModalLabel').html('<span class="badge custom-badge"> </span>' + cardName);

    // 모달 뱃지 색깔 변경
    const cardColor = $(card).find('.card-color-bar').css('background-color');
    $('.custom-badge').css('background-color', cardColor);

    // 모달 설명 변경
    const cardDescript = $(card).find('.card-description').text();
    $('#modalCardDescript').text(cardDescript);


    console.log("btn click, cardId: " + cardId);

    // 코멘트 불러오기
    $.ajax({
        url: `/comments/${cardId}`,
        method: 'GET',
        headers: {'Authorization': authToken},
        success: function (comments) {
            comments.forEach((comment) => {
                // console.log("id: " + comment.id);
                // console.log("email: " + comment.email);
                // console.log("contents: " + comment.contents);
                // console.log("modAt: " + comment.modifiedAt);
                loadComment(comment);
            })
            console.log("댓글 목록을 불러왔습니다.");
        },
        error: (error) => console.error('댓글 목록을 불러오는 중 오류가 발생했습니다.', error)
    })
}

function loadComment(comment) {
    const newCommentHtml = commentTemplate
        .replace("{comment_id}", comment.id)
        .replace("{email}", comment.email)
        .replace("{contents}", comment.contents)
        .replace("{modified_at}", comment.modifiedAt);

    $(newCommentHtml).appendTo('#comment-list')
}

function createCommentForm() {
    const authToken = getAuthorizationToken();
    const card_id = $('#formCardId').val();
    const contents = $('#commentContent').val();
    const card = $(`#card-${card_id}`);

    console.log("cardId: " + card_id);
    console.log("contents: " + contents);

    // AJAX 호출
    $.ajax({
        url: '/comments',
        method: 'POST',
        headers: {
            'Authorization': authToken,
        },
        contentType: 'application/json',
        data: JSON.stringify({
            cardId: card_id,
            contents: contents
        }),
        success: function () {
            console.log("댓글이 작성되었습니다.");

            // 전체 댓글 리스트 다시 불러오기
            loadComments(card);
        },
        error: (error) => console.log("댓글 작성 중 오류가 발생했습니다.")
    });
}

// 코멘트 삭제 요청
function deleteComment(comment) {
    const authToken = getAuthorizationToken();
    const commentId = $(comment).attr('id').split('-')[1];

    const confirmDelete = confirm("이 댓글을 삭제하시겠습니까?");
    if (confirmDelete) {
        $.ajax({
            url: `/comments/${commentId}`,
            method: 'DELETE',
            headers: {'Authorization': authToken},
            success: () => {
                console.log("댓글이 성공적으로 삭제되었습니다.");
                comment.remove();
            },
            error: (error) => {
                console.error('댓글 삭제 중 오류가 발생했습니다:', error)
                alert(error.responseText);
            }
        })
    }
}