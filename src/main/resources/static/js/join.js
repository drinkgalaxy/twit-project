document.addEventListener('DOMContentLoaded', function() {
    const joinButton = document.querySelector('.join-button');
    if (joinButton) {
        joinButton.addEventListener('click', function() {
            const loginId = document.querySelector('.input-id').value;
            const nickname = document.querySelector('.input-nickname').value;
            const password = document.querySelector('.input-password').value;

            // 모든 필드 입력 확인
            if (!loginId || !nickname || !password) {
                alert('모든 항목을 입력해주세요.');
                return;
            }

            // JSON 데이터 준비
            var data = {
                loginId: loginId,
                password: password,
                nickname: nickname
            };

            // 서버로 POST 요청
            fetch('/api/users/registration', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (response.ok) {
                        alert('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.');
                        window.location.href = '/login'; // 로그인 페이지 이동
                    } else {
                        throw new Error('회원가입에 실패했습니다. 다시 시도해주세요.')
                    }
                })
                .catch(function (error) {
                    // 오류 발생 시 캐치
                    console.error('Error: ', error);
                    alert(error.message);
                });
        });
    }
});
