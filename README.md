# GameLevelService
The game level service tracks game specific player levels

The level formula is 50*1.05^level + 25*level
## Environment
| Name | Value |
| --------- | --- |
| MONGO_URI | {mongo_uri} |
| DB_NAME | {db name to store data} |

### Endpoints

#### /games/{gameId}/players/{uuid}/xp/{xp} [POST]
Adds experience to a user's leveling-service account (And levels the player up if neccessary).

On level up a message will be sent to the eventsservice(Not Implemented), returns new balance.

**Response**
```json
{
"success": true,
"levelUp": false
}
```

- success: whether or not the increment succeeded
- err: optional error
- levelUp: whether or not the player leveled up


#### /games/{gameId}/players/{uuid} [GET]
Gets the current player level

**Response**
```json
{
"xp": 215,
"totalXp": 7231,
"lvl": 20,
"consumable": [19, 20]
}
```

- consumable: the levels that can be processed for a reward


#### /games/{gameId}/players/{uuid}/consume/{lvl} [POST] 
Consumes an unconsumed level

**Response**
```json
{
"success": false,
"err": "This level was already consumed"
}
```

- success: whether or not the consumption succeeded
- err: optional error
