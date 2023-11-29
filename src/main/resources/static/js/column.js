// board.html

// 컬럼명 클릭해서 이름을 수정하는 함수
function enableEditTitle(columnElement) {
    if (!columnElement) return;

    const titleElement = columnElement.querySelector('.kanban__title h2');
    if (!titleElement) return;

    const handleTitleClick = () => {
        const currentTitle = titleElement.textContent;
        const inputElement = document.createElement('input');
        inputElement.value = currentTitle;

        const updateTitle = () => {
            const newTitle = inputElement.value;
            const materialIcon = document.createElement('i');

            // 보드 ID와 컬럼 ID를 추출하는 추가적인 코드
            const boardId = extractBoardId();
            const columnId = extractColumnId(columnElement);

            // 입력된 내용으로 새로운 h2 엘리먼트 생성
            const newH2 = createNewH2Element(newTitle, materialIcon, columnElement);
            if (inputElement.parentElement) {
                inputElement.replaceWith(newH2);
            }

            if (newTitle !== currentTitle) {
                // 제목이 바뀌었으면 AJAX 호출하여 DB에 업데이트
                updateColumnTitle(newH2.textContent, columnId, boardId);
            } else {
                console.log("기존 컬럼명과 동일합니다.");
            }
        };

        inputElement.addEventListener('keyup', (event) => {
            if (event.key === 'Enter') {
                updateTitle();
            }
        });

        titleElement.replaceWith(inputElement);
        inputElement.focus();
    };

    titleElement.addEventListener('click', handleTitleClick);
}

// 컬럼을 삭제하는 함수
function deleteColumn(columnElement) {
    const authToken = getAuthorizationToken();
    const columnId = extractColumnId(columnElement);
    const boardId = extractBoardId();

    console.log(columnId)
    // 컬럼 삭제를 위해 AJAX 호출
    $.ajax({
        url: `/columns/${columnId}?boardId=${boardId}`,
        method: 'DELETE',
        headers: { 'Authorization' : authToken },
        // contentType: 'application/json',
        // data: JSON.stringify({ boardId : boardId }),
        success: () => {
            console.log("삭제가 성공적으로 완료되었습니다.");
            // DB에 삭제요청 성공하면 화면에서도 삭제
            columnElement.remove();
        },
        error: (error) => console.error('컬럼 삭제 중 오류가 발생했습니다:', error)
    })
}

// 보드 ID 추출 로직
function extractBoardId() {
    const currentUrl = window.location.href;
    const parts = currentUrl.split('/');
    return parseInt(parts[parts.length - 1], 10);
}

// 컬럼 ID 추출 로직
function extractColumnId(columnElement) {
    const columnIdAttr = columnElement.id;
    const idParts = columnIdAttr.split('-');
    return idParts[idParts.length - 1];
}

// 새로운 제목을 갖는 h2 엘리먼트 생성
function createNewH2Element(newTitle, materialIcon, columnElement) {
    const newH2 = document.createElement('h2');
    materialIcon.classList.add('material-icons');
    materialIcon.textContent = newTitle;
    newH2.appendChild(materialIcon);
    newH2.onclick = () => enableEditTitle(columnElement);
    return newH2;
}

// 컬럼 제목을 업데이트하기 위한 AJAX 호출
function updateColumnTitle(newTitle, columnId, boardId) {
    const authToken = getAuthorizationToken();
    $.ajax({
        url: `/columns/${columnId}`,
        method: 'PUT',
        headers: { 'Authorization' : authToken },
        contentType: 'application/json',
        data: JSON.stringify({ boardId : boardId, title : newTitle }),
        success: () => console.log("수정이 성공적으로 완료되었습니다."),
        error: (error) => console.error('컬럼 수정 중 오류가 발생했습니다:', error)
    });
}