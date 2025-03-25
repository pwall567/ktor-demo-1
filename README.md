# ktor-demo-1

Demonstration for `kjson-ktor`

## Notes

This Customer Server runs on port 8101 (configurable in `application.conf`) and it expects the `ktor-demo-2` Party
Server to be running at `http://localhost:8102` hard-coded in `PartyClientImpl`.

The Party Server has 4 built-in parties:

| Party Id | Party Name              |
|----------|-------------------------|
| 1        | Alpha Mail Co           |
| 2        | Beater retreats Ltd     |
| 3        | Gamma Raze Inc          |
| 4        | Delta Hairlines Pty Ltd |

Calls to the Customer Server invoke the Party Server to get the party name, and the different endpoints illustrate the
different technology approaches available.

### `/customer/single/{id}`

This will return a single customer using a simple synchronous call.

### `/customer/list/{ids}`

This will return multiple customers (ids are separate by period '`.`'), using a synchronous call and iterating through
response array.
No data will be visible until the last party has been received.

### `/customer/channel/{ids}`

This will return multiple customers asynchronously using a `Channel`.
As each party is received, the related customer will be presented in the endpoint response.

### `/customer/flow/{ids}`

This will return multiple customers asynchronously using a `Flow`.
As each party is received, the related customer will be presented in the endpoint response.

### `"/display/{ids}"`

This will return multiple customers asynchronously, with each customer being formatted in a Mustache template as it is
received.
This demonstration clearly shows that each object in an array can be fully processed and displayed even while later
objects in the stream are still being received.
