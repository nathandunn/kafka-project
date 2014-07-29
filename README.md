Twitter piece
-------------

Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

I believe I have mine here at:

https://apps.twitter.com/app/6565432/keys

. . . though these might be backwards 

consumer token: <Called the API Key under application settings>
consumer secret: <Called the API Secret under application settings>

access token:  <Called the access token under "Your access token">
access secret: <Called the access secret under "Your access secret">

Based largely on this example (hosebird client):

https://github.com/twitter/hbc/blob/master/hbc-example/