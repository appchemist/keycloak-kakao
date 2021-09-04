# 카카오 OAuth Keycloak Identity Provider

Keycloak 서버에서 카카오 소셜 로그인을 지원하기 위한 기능 제공

## 설치 방법
1. 프로젝트 홈 디렉토리에서 `mvn package` 수행
2. 생성된 결과물 `keycloak-kakao-1.0-SNAPSHOT.jar`을 `$KEYCLOAK_HOME/standalone/deployments`로 복사
3. `cp $KEYCLOAK_HOME/themes/base/admin/resources/partials/realm-identity-provider-linkedin-ext.html $KEYCLOAK_HOME/themes/base/admin/resources/partials/realm-identity-provider-kakao-ext.html`
4. `cp $KEYCLOAK_HOME/themes/base/admin/resources/partials/realm-identity-provider-linkedin.html $KEYCLOAK_HOME/themes/base/admin/resources/partials/realm-identity-provider-kakao.html` 