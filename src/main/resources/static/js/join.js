// ID 중복 확인 함수
async function checkId() {
    const loginId = document.getElementById('loginId').value;

    // 아이디가 영어 알파벳만 포함되도록 체크
    if (!/^[a-zA-Z]{4,20}$/.test(loginId)) {
        document.getElementById('id-check-result').textContent = '아이디는 4자 이상 20자 이하의 영어 알파벳이어야 합니다.';
        document.getElementById('id-check-result').className = 'message error';
        return false;
    }

    if (!loginId) {
        document.getElementById('id-check-result').textContent = '아이디를 입력해주세요.';
        document.getElementById('id-check-result').className = 'message error';
        return false;
    }

    const response = await fetch(`/api/users/checkId?loginId=${encodeURIComponent(loginId)}`);
    const result = await response.text();

    if (response.ok) {
        document.getElementById('id-check-result').textContent = '사용 가능한 아이디입니다.';
        document.getElementById('id-check-result').className = 'message success';
        return true;
    } else {
        document.getElementById('id-check-result').textContent = result;
        document.getElementById('id-check-result').className = 'message error';
        return false;
    }
}

// 닉네임 중복 확인 함수
async function checkNickname() {
    const nickname = document.getElementById('nickname').value;
    const nicknameCheckResult = document.getElementById('nickname-check-result');

    // 닉네임 길이 검증
    if (!nickname) {
        nicknameCheckResult.textContent = '닉네임을 입력해주세요.';
        nicknameCheckResult.className = 'message error';
        return false;
    } else if (nickname.length < 1 || nickname.length > 10) {
        nicknameCheckResult.textContent = '닉네임은 1글자 이상, 10글자 이내로 입력해주세요.';
        nicknameCheckResult.className = 'message error';
        return false;
    }

    // 닉네임 중복 확인
    const response = await fetch(`/api/users/checkNickname?nickname=${encodeURIComponent(nickname)}`);
    const result = await response.text();

    if (response.ok) {
        nicknameCheckResult.textContent = '사용 가능한 닉네임입니다.';
        nicknameCheckResult.className = 'message success';
        return true;
    } else {
        nicknameCheckResult.textContent = result;
        nicknameCheckResult.className = 'message error';
        return false;
    }
}


// 폼 제출 이벤트 처리
document.getElementById('join-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const isIdValid = await checkId();
    const isNicknameValid = await checkNickname();
    const password = document.getElementById('password').value;

    if (!password) {
        alert('비밀번호를 입력해주세요.');
        return;
    }

    if (password.length < 4 || password.length > 20) {
        alert('비밀번호는 4자 이상 20자 이하로 입력해주세요.');
        return;
    }

    if (isIdValid && isNicknameValid) {
        const loginId = document.getElementById('loginId').value;
        const nickname = document.getElementById('nickname').value;

        const formData = {
            loginId,
            nickname,
            password
        };

        const response = await fetch('/api/users/registration', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            alert('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.');
            window.location.href = '/login';
        } else {
            const error = await response.text();
            alert('회원가입 실패: ' + error);
        }
    } else {
        alert('아이디와 닉네임 중복 확인을 해주세요.');
    }
});
