// board.html의 card 관련 js코드

$(document).on('nestableDragStop', function(event, el) {
    // el을 받아와서 원하는 로직을 수행함
    const columnId = $(el).closest('.column').attr('id').split('-')[1];
    const cardId = $(el).attr('id').split('-')[1];
    const cardPos = $(el).index();
    const authToken = getAuthorizationToken();

    // // 카드 이동 요청을 위해 필요한 id와 위치
    // console.log('columnId:' + columnId);
    // console.log('cardId:' + cardId);
    // console.log('cardPos:' + cardPos);

    // AJAX 호출하여 DB에 카드 이동 요청
    $.ajax({
        url: `/cards/${cardId}/to/${columnId}/${cardPos}`,
        method: 'PUT',
        headers: {
            'Authorization': authToken
        },
        success: function (card) {
            console.log('카드 이동이 성공적으로 완료되었습니다. ')
        },
        error: function (error) {
            console.error('카드 이동 중 오류가 발생했습니다:', error);
        }
    });
});

const commentTemplate =
    `
        <li class="list-group-item" id="comment-{commentId}">
        <table style="width: 100%;">
            <tr>
                <td style="width: 50%;">
                    <span class="comment-author" style="font-size: 15px;">{email}</span>
                </td>
                <td style="width: 50%; text-align: right;">
                    <span class="comment-time" style="font-size: 15px;">{modified_at}</span>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <hr class="comment-divider">
                    <p class="small">{contents}</p>
                </td>
            </tr>
        </table>
    </li>
    `

// comment-icon 클릭 시 card 댓글을 표시하는 사이드바 토글
function loadComments(card) {
    const cardId = $(card).attr('id').split('-')[1];
    const cardName = $(card).find('h3').text();
    const authToken = getAuthorizationToken();

    // 숨겨진 input에 카드ID 전달
    $('#formCardId').val(cardId);

    // 전체 코멘트 화면에서 삭제
    $('#comment-list').empty();

    // 오프캔버스 제목 변경
    $('#offcanvasRightLabel').text(cardName);

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
            'Authorization' : authToken,
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