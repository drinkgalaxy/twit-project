// ---------- 닉네임 변경과 중복 체크
let isNicknameAvailable = false; // 닉네임 중복 확인 상태를 저장할 변수

// 닉네임 변경 버튼 클릭 시 폼과 버튼 표시
document.querySelector('.nickname-change-button').addEventListener('click', () => {
    document.querySelector('.input-nickname').style.display = 'block';
    document.querySelector('.nickname-check').style.display = 'block';
    document.querySelector('.nickname-done').style.display = 'block';
});

// 중복 확인 버튼 클릭 시 닉네임 중복 체크
document.querySelector('.nickname-check').addEventListener('click', async () => {
    const nickname = document.querySelector('.input-nickname').value.trim();

    if (!nickname) {
        document.querySelector('.nickname-message').textContent = '닉네임을 입력해 주세요.';
        document.querySelector('.nickname-message').style.color = 'red';
        document.querySelector('.nickname-message').style.display = 'block';
        isNicknameAvailable = false;
        return;
    }

    try {
        const response = await fetch(`/api/users/checkNickname?nickname=${encodeURIComponent(nickname)}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            document.querySelector('.nickname-message').textContent = await response.text();
            document.querySelector('.nickname-message').style.color = 'blue';
            isNicknameAvailable = true; // 닉네임이 사용 가능하다고 설정
        } else {
            document.querySelector('.nickname-message').textContent = await response.text();
            document.querySelector('.nickname-message').style.color = 'red';
            isNicknameAvailable = false; // 닉네임이 이미 존재한다고 설정
        }
        document.querySelector('.nickname-message').style.display = 'block';
    } catch (error) {
        console.error('Error:', error);
        document.querySelector('.nickname-message').textContent = '중복 확인 중 오류가 발생했습니다.';
        document.querySelector('.nickname-message').style.color = 'red';
        document.querySelector('.nickname-message').style.display = 'block';
        isNicknameAvailable = false; // 오류 발생 시 닉네임 확인 실패로 설정
    }
});

// 완료 버튼 클릭 시 닉네임 변경 요청
document.querySelector('.nickname-done').addEventListener('click', async () => {
    if (!isNicknameAvailable) {
        alert('중복 확인을 먼저 해주세요.');
        return;
    }

    const userId = document.querySelector('.box').getAttribute('data-user-id'); // 사용자 ID를 적절히 가져와야 함
    const nickname = document.querySelector('.input-nickname').value.trim();

    if (!nickname) {
        alert('닉네임을 입력해 주세요.');
        return;
    }

    try {
        const response = await fetch(`/api/users/nickname/${userId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ nickname }),
        });

        if (response.ok) {
            const userResponse = await response.json();
            alert('닉네임이 성공적으로 변경되었습니다.');
            // 폼을 숨기고 버튼 상태 복원
            document.querySelector('.input-nickname').style.display = 'none';
            document.querySelector('.nickname-check').style.display = 'none';
            document.querySelector('.nickname-done').style.display = 'none';
            document.querySelector('.nickname-message').style.display = 'none';
            window.location.reload(); // 페이지 새로고침
        } else {
            const errorMessage = await response.text();
            alert(errorMessage);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('닉네임 변경 중 오류가 발생했습니다.');
    }
});


// ------- 비밀번호 변경

function togglePasswordChange() {
    const inputPassword = document.querySelector('.input-password');
    const passwordDone = document.querySelector('.password-done');

    if (inputPassword.style.display === 'none') {
        inputPassword.style.display = 'block';
        passwordDone.style.display = 'block';
    } else {
        inputPassword.style.display = 'none';
        passwordDone.style.display = 'none';
    }
}

async function changePassword() {
    const userId = document.querySelector('.box').getAttribute('data-user-id');
    const newPassword = document.querySelector('.input-password').value;

    if (!newPassword) {
        alert('비밀번호를 입력해주세요.');
        return;
    }

    try {
        const response = await fetch(`/api/users/password/${userId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ password: newPassword }),
        });

        if (response.ok) {
            alert('비밀번호가 성공적으로 변경되었습니다.');
            togglePasswordChange();
        } else {
            return response.text().then(errorMessage => {
                alert(errorMessage);
            });
        }
    } catch (error) {
        console.error('Error:', error);
        alert('비밀번호 변경 중 오류가 발생했습니다.');
    }
}
