# 트잇 (Twit)
###  프로젝트 공유 게시판 사이트
- 뉴비 개발자들은 자신의 프로젝트 결과물을 다른 사람에게 노출하기 쉽지 않습니다.
- 그래서 개발자 지망생들이 서로의 결과물을 공유하고, 피드백하며 사용자 트래픽 경험까지 챙길 수 있는 프로젝트 공유 게시판을 개발하게 되었습니다.
- 공유된 프로젝트에 포스트잇을 붙이듯이 자유롭게 피드백을 남길 수 있어서 포스트잇의 트잇을 따 이름을 지었습니다.

- 배포주소

## 개발 일정
![image](https://github.com/user-attachments/assets/1d37df8e-b473-42a3-b8ca-3a8c17caea4a)

## 사용 스택
### Back-end
|   Java   |   Spring   |   Spring Boot   |   MySQL   |   AWS   |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/java-icon.svg" alt="icon" width="65" height="65" /></div> | <img alt="spring logo" src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" height="50" width="50" > | <img alt="spring-boot logo" src="https://t1.daumcdn.net/cfile/tistory/27034D4F58E660F616" width="65" height="65" > | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/mysql-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/aws-icon.svg" alt="icon" width="65" height="65" /></div> |

### Front-end
|     Html     |     CSS     |     JavaScript     |     Figma     |  
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | 
| <img alt="Html" src ="https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/HTML5_logo_and_wordmark.svg/440px-HTML5_logo_and_wordmark.svg.png" width="65" height="65" /> | <div style="display: flex; align-items: flex-start;"><img src="https://user-images.githubusercontent.com/111227745/210204643-4c3d065c-59ec-481d-ac13-cea795730835.png" alt="CSS" width="50" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/js-icon.svg" alt="icon" width="75" height="75" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://www.vectorlogo.zone/logos/figma/figma-icon.svg" width="100" height="65"/></div>  |

### Tools
| Github | Notion | 
| :--------: | :------: |
| <img alt="github logo" src="https://techstack-generator.vercel.app/github-icon.svg" width="65" height="65"> | <img alt="Notion logo" src="https://www.notion.so/cdn-cgi/image/format=auto,width=640,quality=100/front-static/shared/icons/notion-app-icon-3d.png" height="65" width="65"> |

## 기능 명세

## 화면 설계
|                             로그인 페이지                              |                          회원가입 페이지                          |
|:----------------------------------------------------------------:|:---------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/357da657-3e4d-45c9-92c4-cc52f58e3a2d" style="width: 300px"> | <img src="https://github.com/user-attachments/assets/d66b0423-cda0-4c74-87b5-552e3b9f539b" style="width: 300px"> |

|                             인트로 페이지 1                            |                          인트로 페이지 2                          |
|:----------------------------------------------------------------:|:---------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/3bf3ede0-0bf2-48aa-b37d-7e62bac2dff2" style="width: 500px"> | <img src="https://github.com/user-attachments/assets/97772413-a694-435e-b2cc-6d4bfd6a5b38" style="width: 500px"> |
- 트잇 서비스 사용방법이 간단하게 나와있는 페이지입니다.

|                             메인 페이지                              |                          글 업로드 페이지                          |
|:----------------------------------------------------------------:|:---------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/d8d439bb-894b-41ad-a874-694ee2ddb942" style="width: 500px"> | <img src="https://github.com/user-attachments/assets/cdb9199a-1d97-4ff7-9145-e571f5963c09" style="width: 500px"> |
- 게시글을 3개씩 페이징해서 불러옵니다.
- 내 정보 페이지로 이동하거나 로그아웃할 수 있습니다.
- 관리자가 올려둔 공지를 조회할 수 있습니다.
- 추후에 단어 검색 기능과 자유, 고민 게시판 기능을 추가할 예정입니다.
- 글 등록 시 제목과 한줄 소개, 설명, 파일을 첨부할 수 있습니다.
- 등록하기를 눌렀을 때, 만약 포스트잇이 10개 이상이면 글이 등록되고 10개 미만이면 글을 등록할 수 없습니다. 피드백을 더 많이 달아 포스트잇을 더 모아야합니다.

|                             게시글 상세 페이지                             |                          댓글 작성 모달창                          |
|:----------------------------------------------------------------:|:---------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/16ff6c3a-f5e6-429e-89da-d5e0182e6243" style="width: 500px"> | <img src="https://github.com/user-attachments/assets/78d6f64e-87be-483f-adb7-633d78524933" style="width: 500px"> |
- 마음에 드는 글은 하트를 눌러 스크랩할 수 있습니다.
- 피드백 붙이기 버튼을 누르면 포스트잇 형태로 댓글을 등록할 수 있습니다.
- 게시글 작성자는 글을 수정하거나 삭제할 수 있습니다.
- 피드백 한 개를 붙이면 포스트잇 한 개가 주어지고, 포스트잇 열 개를 모으면 게시글 한 개와 교환할 수 있습니다.
- 피드백 포스트잇은 삭제하거나 신고할 수 있습니다.

|                             댓글 신고 모달창                            |                          관리자 신고 관리 페이지                          |
|:----------------------------------------------------------------:|:---------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/cd252328-430e-4a19-9432-a930d3ff0181" style="width: 500px"> | <img src="https://github.com/user-attachments/assets/b1273c1e-46b0-4f4b-9f82-97aa697dd25a" style="width: 500px"> |
- 댓글을 신고할 때 신고 사유를 작성할 수 있습니다.
- 관리자는 신고 리스트를 확인하고, 신고를 수락하거나 거절할 수 있습니다.

|                             내 정보 페이지 1                            |                          내 정보 페이지 2                          |
|:----------------------------------------------------------------:|:---------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/88113d51-2961-49a7-9f84-d01e82e99c2b" style="width: 500px"> | <img src="https://github.com/user-attachments/assets/cf755e4f-2783-4f35-9352-143a1e48db54" style="width: 500px"> |
- 유저의 닉네임과 아이디를 보여줍니다. 닉네임과 비밀번호는 변경할 수 있습니다. 변경 시 닉네임 중복검사를 진행합니다.
- 내 정보 페이지에는 보유한 포스트잇 개수를 확인할 수 있습니다. 전체 몇 개인지, 몇 개 사용 가능한지, 글과 몇 번 교환했는지 알 수 있습니다.
- 내가 올린 게시글, 내가 피드백한 글, 내가 스크랩한 글로 바로 이동할 수 있습니다.
- 회원 탈퇴 버튼을 누르면 탈퇴할 수 있습니다.

## erd 설계
![image](https://github.com/user-attachments/assets/eb5c66c4-647d-4d2b-b45a-97f4c27e8824)

- 삭제된 게시글이나 댓글은 use_yn을 false로 처리해 실제 삭제대신 보이지 않게 처리했습니다.
- 신고된 포스트잇도 마찬가지로 일시적으로 use_yn을 false로 바꾸고 만약 신고가 거절되면 다시 true로 바꿔 댓글에 나타나도록 했습니다.

## api 명세
| Post, Multipart, Comment, Report | Notice, Scrap, User |
|:----------------------------------------------------------------:|:---------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/a5b24be9-85f8-4db1-b2f2-2407960079b0" style="width: 500px"> | <img src="https://github.com/user-attachments/assets/2a16ec45-74d7-47ee-8d05-a16890a460f8" style="width: 500px"> |

