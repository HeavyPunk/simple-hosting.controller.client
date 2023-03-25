package contracts.server

import com.fasterxml.jackson.annotation.JsonProperty

class StartServerRequest(
    @JsonProperty("save-stdout") val saveStdout: Boolean,
    @JsonProperty("save-stderr") val saveStderr: Boolean
)

class StartServerResponse(
    val success: Boolean,
    val error: String
)

class StopServerRequest(
    val force: Boolean
)

class StopServerResponse(
    val success: Boolean,
    val error: String
)
