
//db.createCollection("oauth2_server_full")
//use oauth2_server_full
db.client_app.insertMany(
    [
        {
            "_id":ObjectId("65f49b1bdde221152eb838f6"),
            "clientId": "client",
            "clientSecret": "$2a$10$QHLrXJ8nlnDlOXEDUnV6iefGNOkKaW9pqJwZcpF.5HrO42PRPzjcO",
            "durationInMinutes": 10,
            "requireProofKey": false,
            "authenticationMethods": ["client_secret_basic"],
            "authorizationGrantTypes": ["authorization_code", "refresh_token", "client_credentials"],
            "redirectUris": ["https://oauthdebugger.com/debug","http://localhost:4200/home"],
            "postLogoutRedirectUris": ["http://localhost:4200/home"],
            "scopes": ["openid", "email", "profile","address","phone"],
            "_class":"org.prd.authserver.persistence.entity.ClientApp"
        },
        {
            "_id": ObjectId("65f46777b91a283e6dae49e9"),
            "clientId": "my-own-client",
            "clientSecret": "$2a$10$QHLrXJ8nlnDlOXEDUnV6iefGNOkKaW9pqJwZcpF.5HrO42PRPzjcO",
            "durationInMinutes": 10,
            "requireProofKey": true,
            "authenticationMethods": ["client_secret_basic"],
            "authorizationGrantTypes": ["authorization_code", "refresh_token", "client_credentials"],
            "redirectUris": ["https://oauthdebugger.com/debug","http://localhost:4200/home"],
            "postLogoutRedirectUris": ["http://localhost:4200/home"],
            "scopes": ["openid", "ALL"],
            "_class":"org.prd.authserver.persistence.entity.ClientApp"
        }
    ]

)