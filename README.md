# 카카오 OAuth Keycloak Identity Provider

Keycloak 서버에서 카카오 소셜 로그인을 지원하기 위한 기능 제공

## 설치 방법
1. 프로젝트 홈 디렉토리에서 `mvn package` 수행
2. 생성된 결과물 `keycloak-kakao-1.0-SNAPSHOT.jar`을 `$KEYCLOAK_HOME/standalone/deployments`로 복사
3. `cp $KEYCLOAK_HOME/themes/base/admin/resources/partials/realm-identity-provider-linkedin-ext.html $KEYCLOAK_HOME/themes/base/admin/resources/partials/realm-identity-provider-kakao-ext.html`
4. `cp $KEYCLOAK_HOME/themes/base/admin/resources/partials/realm-identity-provider-linkedin.html $KEYCLOAK_HOME/themes/base/admin/resources/partials/realm-identity-provider-kakao.html` 

### HAL Management Console을 이용한 배포
1. 프로젝트 홈 디렉토리에서 `mvn package` 수행
2. HAL Management Console(기본 주소 : 127.0.0.1:9990)에 접속
3. Deployments 탭으로 이동
4. Upload Deployment에서 1.에서 생성한 파일을 업로드