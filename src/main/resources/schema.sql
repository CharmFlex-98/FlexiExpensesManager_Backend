CREATE TABLE IF NOT EXISTS users (
    uid TEXT PRIMARY KEY,
    username TEXT,
    email TEXT,
     last_login TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
        modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC')
);

CREATE TABLE IF NOT EXISTS devices (
    device_id TEXT PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    sdk_version TEXT NOT NULL,
    android_version TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_devices (
    user_id TEXT REFERENCES users(uid) ON DELETE CASCADE,
    device_id TEXT REFERENCES devices(device_id) ON DELETE CASCADE,
    last_used TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    PRIMARY KEY (user_id, device_id)  -- Composite key
);