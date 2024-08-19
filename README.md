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

## erd 설계
![image](https://github.com/user-attachments/assets/eb5c66c4-647d-4d2b-b45a-97f4c27e8824)

- 삭제된 게시글이나 댓글은 use_yn을 false로 처리해 실제 삭제대신 보이지 않게 처리했습니다.
- 신고된 포스트잇도 마찬가지로 일시적으로 use_yn을 false로 바꾸고 만약 신고가 거절되면 다시 true로 바꿔 댓글에 나타나도록 했습니다.

## api 명세
| Post, Multipart, Comment, Report | Notice, Scrap, User |
|:----------------------------------------------------------------:|:---------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/a5b24be9-85f8-4db1-b2f2-2407960079b0" style="width: 500px"> | <img src="https://github.com/user-attachments/assets/2a16ec45-74d7-47ee-8d05-a16890a460f8" style="width: 500px"> |

