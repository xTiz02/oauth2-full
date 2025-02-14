//insert all data into mongo
db.oauth2_server_full.insertMany(
    [
        {
            "clientId": "client",
            "clientSecret": "$2a$10$QHLrXJ8nlnDlOXEDUnV6iefGNOkKaW9pqJwZcpF.5HrO42PRPzjcO",
            "durationInMinutes": 5,
            "requireProofKey": false,
            "authenticationMethods": ["client_secret_basic"],
            "authorizationGrantTypes": ["authorization_code", "refresh_token", "client_credentials"],
            "redirectUris": ["https://oauthdebugger.com/debug"],
            "scopes": ["openid", "READ_ALL_PRODUCTS", "READ_ONE_PRODUCT"]
        },
        {
            "clientId": "my-own-client",
            "clientSecret": "$2a$10$QHLrXJ8nlnDlOXEDUnV6iefGNOkKaW9pqJwZcpF.5HrO42PRPzjcO",
            "durationInMinutes": 5,
            "requireProofKey": true,
            "authenticationMethods": ["client_secret_basic"],
            "authorizationGrantTypes": ["authorization_code", "refresh_token", "client_credentials"],
            "redirectUris": ["https://oauthdebugger.com/debug"],
            "scopes": ["openid", "ALL"]
        }
    ]

)