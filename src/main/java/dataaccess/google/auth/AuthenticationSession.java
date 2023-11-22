package dataaccess.google.auth;

record AuthenticationSession(CodeVerifier verifier, String code, String redirectUrl, String clientSecret) {}
