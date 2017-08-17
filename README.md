# GameLevelService
The game level service tracks game specific player levels

The level formula is `500*1.05^level + 200*level`
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


### Levels

- Level 1: 500 xp
- Level 2: 725 xp
- Level 3: 951 xp
- Level 4: 1178 xp
- Level 5: 1407 xp
- Level 6: 1638 xp
- Level 7: 1870 xp
- Level 8: 2103 xp
- Level 9: 2338 xp
- Level 10: 2575 xp
- Level 11: 2814 xp
- Level 12: 3055 xp
- Level 13: 3297 xp
- Level 14: 3542 xp
- Level 15: 3789 xp
- Level 16: 4039 xp
- Level 17: 4291 xp
- Level 18: 4546 xp
- Level 19: 4803 xp
- Level 20: 5063 xp