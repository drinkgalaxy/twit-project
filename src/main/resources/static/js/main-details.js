// --------------- 하트 클릭

const emptyHeartSrc = '../static/image/empty-heart.png';
const filledHeartSrc = '../static/image/filled-heart.png';

// 하트 이미지 요소를 선택
const heartImage = document.getElementById('heart-image');

// 하트 이미지를 클릭했을 때 실행
heartImage.addEventListener('click', () => {
    // 현재 이미지가 빈 하트일 경우 채워진 하트로 변경하고, 채워진 하트일 경우 빈 하트로 변경
    if (heartImage.src.includes('empty-heart.png')) {
        heartImage.src = filledHeartSrc;
    } else {
        heartImage.src = emptyHeartSrc;
    }
});

// ---------------- 피드백 붙이기 모달창
    // 모달 창 열기
    function openFeedbackModal() {
    document.querySelector('.modal-overlay').style.display = 'block';
    document.querySelector('.feedback-modal').style.display = 'block';
}

    // 모달 창 닫기
    function closeFeedbackModal() {
    document.querySelector('.modal-overlay').style.display = 'none';
    document.querySelector('.feedback-modal').style.display = 'none';
}

    // 버튼 클릭 시 모달 창 열기
    document.querySelector('.feedback-button').addEventListener('click', openFeedbackModal);

    // 회색 배경 클릭 시 모달 창 닫기
    document.querySelector('.modal-overlay').addEventListener('click', closeFeedbackModal);


// ---------------- 신고 사유 작성 모달창
document.addEventListener('DOMContentLoaded', () => {
    // 모달 창 열기
    function openNotifyModal() {
        document.querySelector('.notice-overlay').style.display = 'block';
        document.querySelector('.notice-modal').style.display = 'block';
    }

    // 모달 창 닫기
    function closeNotifyModal() {
        document.querySelector('.notice-overlay').style.display = 'none';
        document.querySelector('.notice-modal').style.display = 'none';
    }

    // 버튼 클릭 시 모달 창 열기
    document.querySelector('.notice-button').addEventListener('click', openNotifyModal);

    // 회색 배경 클릭 시 모달 창 닫기
    document.querySelector('.notice-overlay').addEventListener('click', closeNotifyModal);

});
