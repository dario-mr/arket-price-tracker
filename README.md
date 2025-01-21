# Arket Price Tracker

Track the price of Arket items and get notified via email if the desired price is reached

## Environment variables

- `PORT`: server port on which to run the app (default: `8086`)
- `PROFILE`: spring profile to apply (default: `prod`)
    - `dev`: vaadin production mode is off; in-memory DB is used
    - `prod`: vaadin production mode is on; prod DB is used
- `DB_PASSWORD`: database password (default: `null`)
- `SENDER_PASSWORD`: sender's email password, not necessarily the actual account password - e.g. in the case of gmail,
  this is the "app password" (default: `null`)
