// 모달 창 열기
function openFeedbackModal(button) {
    const reportedComments = button.getAttribute('data-report-reportedComments');

    const feedbackContents = document.getElementById('feedback-contents');

    feedbackContents.innerHTML = `<p>${reportedComments}</p>`;

    document.getElementById('modal-overlay').style.display = 'block';
    document.getElementById('feedback-box').style.display = 'block';
}

// 모달 창 닫기
function closeFeedbackModal() {
    document.getElementById('modal-overlay').style.display = 'none';
    document.getElementById('feedback-box').style.display = 'none';
}

// ---- 신고 수락/거절
// 신고 수락
function acceptReport(button) {
    const reportId = button.getAttribute('data-report-id');

    fetch(`/api/report/${reportId}/accept`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (response.ok) {
                alert("신고가 수락되었습니다.");
                window.location.reload();
            } else {
                alert("신고 수락에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("신고 수락 과정에서 오류가 발생했습니다.");
        });
}

// 신고 거절
function rejectReport(button) {
    const reportId = button.getAttribute('data-report-id');

    fetch(`/api/report/${reportId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (response.ok) {
                alert("신고가 거절되었습니다.");
                window.location.reload();
            } else {
                alert("신고 거절에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("신고 거절 과정에서 오류가 발생했습니다.");
        });
}