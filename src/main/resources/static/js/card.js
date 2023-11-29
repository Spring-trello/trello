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

// comment-icon 클릭 시 card 댓글을 표시하는 사이드바 토글
function loadComments(el) {
    const cardId = $(el).closest('.dd-item').attr('id').split('-')[1];
    const authToken = getAuthorizationToken();

    console.log("btn click, cardId: " + cardId);

    $.ajax({
        url: `/comments/${cardId}`,
        method: 'GET',
        headers: {'Authorization': authToken},
        success: function (comments) {
            console.log("댓글 목록을 불러왔습니다.");
            comments.forEach((comment) => {
                console.log("contents:" + comment.contents, "modAt:" + comment.modifiedAt);
            })
        },
        error: (error) => console.error('댓글 목록을 불러오는 중 오류가 발생했습니다.', error)
    })
}