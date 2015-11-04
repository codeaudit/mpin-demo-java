from __future__ import unicode_literals

"""HTTP server settings"""
address = '127.0.0.1'
port = 8011

"""Set Access-Control-Allow-Origin header"""
# allowOrigin = ['*']

"""Time synchronization

To be able to perform time based verification, by default RPS syncs its time
with CertiVox servers. If you set it to False, you should still sync the server
using an accurate NTP time server!
"""
# syncTime = False

"""The location of your keys file (relative to mpin-backend/servers/dta)."""
credentialsFile = '/home/georgi/mpinopt/credentials.json'

"""Entropy sources

D-TA supports multiple ways to gather entropy random, urandom, certivox or
combination of those.
"""
# EntropySources = 'dev_urandom:100'  # Default
# EntropySources = 'certivox:100'
# EntropySources = 'dev_urandom:60,certivox:40'

"""The length of the seed for the client"""
# seedValueLength = 100  # Default

"""CertiVox server secret share acquisition

- dta - get server secret from CertiVox dta automatically on start
- credentials.json - get server secret from credentials.json (key: certivox_server_secret)
- manual - service will prompt for it
- the secret itself

You can get your CertiVox server secret by:
    ./scripts/getServerSecretShare.py credentials.json
which will output your credentials json including certivox_server_secret.
NOTE: Don't pipe it directly to the same file - you'll lose your original
      credentials file.
Alternatively you can copy only your certivox_server_secret value and supply it
either manually or via config.py setting the certivoxServerSecret to the
corresponding value.
"""
# certivoxServerSecret = 'dta'  # Default

"""Local DTA address."""
DTALocalURL = 'http://127.0.0.1:8001'

"""Access number options

- accessNumberExpireSeconds - The default time client will show the access number
- accessNumberExtendValiditySeconds - Validity of the access number (on top of accessNumberExpireSeconds)
- accessNumberUseCheckSum - Should access number have checksum
"""
# accessNumberExpireSeconds = 60  # Default
# accessNumberExtendValiditySeconds = 5  # Default
# accessNumberUseCheckSum = True  # Default

"""Authentication options

- waitForLoginResult -For the mobile flow. Wait the browser login before showing the Done/Logout button.
"""
waitForLoginResult = False
# VerifyUserExpireSeconds = 3600  # Default
# maxInvalidLoginAttempts = 3  # Default

"""One time password options"""
# requestOTP = True
# OTTLength = 16  # Default

"""RPA options

- RPAPermitUserURL - RPA Revocation endpoint
- RegisterForwardUserHeaders - Coma separated list of headers
    - '' - do not forward headers
    - * - forward all headers
- LogoutURL - RPA Logout url. For logout using the mobile client.
"""
RPAVerifyUserURL = 'http://127.0.0.1:8080/mpin/mpinVerify'
# RPAPermitUserURL = 'http://127.0.0.1:8005/mpinPermitUser'
RPAAuthenticateUserURL = '/mpin/mpinAuthenticate'
RegisterForwardUserHeaders = ''
#LogoutURL = '/mpin/logout'

"""PIN pad client options"""
rpsBaseURL = '/mpin'
#rpsPrefix = 'rps'  # Default
# setDeviceName = True

"""Key value storage options"""
storage = 'memory'

# storage = 'redis'
# redisHost = '127.0.0.1'  # Default
# redisPort = 6379  # Default
# redisDB = 0  # Default
# redisPassword = None  # Default
# redisPrefix = 'mpin'  # Default

# storage = 'json'
# fileStorageLocation = './mpin_storage.json'
