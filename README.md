# Running the app

`sbt run` from the root of the repo
`npm start` from the `/front_end` directory

The front end runs a little server to bundle and serve the javascript, so you'll hit `localhost:3000` to view the app in browser.

# Ideal to-dos
- restructure front end components into folders
- generate content from backend that is sent out to subscribers
- build out a test POST endpoint, and notify open apps of the change
- rewrite sample css in BEM
- remove all deprecated methods from scala controller
  - rewrite scala websockets with a same-origin check
  - rewrite scala websockets using streams+futures

# Resources
- WebSocket web api: https://developer.mozilla.org/en-US/docs/Web/API/WebSocket
- Play WebSockets: https://www.playframework.com/documentation/2.5.x/ScalaWebSockets
- Play Examples:
  - https://github.com/ticofab/simple-play-websocket-server
  - https://github.com/playframework/play-scala-websocket-example
- Websocket testing tool: https://www.websocket.org/echo.html
- BEM: https://csswizardry.com/2013/01/mindbemding-getting-your-head-round-bem-syntax/
- React: https://facebook.github.io/react/
