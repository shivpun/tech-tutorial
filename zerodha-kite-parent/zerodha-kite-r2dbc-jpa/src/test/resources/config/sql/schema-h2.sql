CREATE TABLE SB_KITE_USER_LOGIN_TBL (
	SB_KITE_USER_ID bigserial PRIMARY KEY,
	SB_KITE_NICK_NAME VARCHAR(256),
	SB_KITE_USER_NAME VARBINARY(1000),
	SB_KITE_PASSWORD VARBINARY(1000),
	SB_KITE_PIN VARBINARY(1000)
);