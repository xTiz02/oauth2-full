export const environment = {
    authorize_uri: 'http://localhost:9595/authorization-server/oauth2/authorize?',
    client_id : 'client',
    redirect_uri: 'http://localhost:4200/home',
    scope: 'openid profile email phone address',
    response_type: 'code',
    response_mode: 'form_post',
    code_challenge_method: 'S256',
    token_url: 'http://localhost:9595/authorization-server/oauth2/token',
    grant_type: 'authorization_code',
    resource_url: 'http://localhost:8081/users/',
    logout_url: 'http://localhost:9595/authorization-server/logout',
    secret_pkce: 'secret'
  };