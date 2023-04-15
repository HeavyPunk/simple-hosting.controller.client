package io.github.heavypunk.controller
package client
package contracts.server

import com.fasterxml.jackson.annotation.JsonProperty

case class StartServerRequest(
    @JsonProperty("save-stdout") val saveStdout: Boolean,
    @JsonProperty("save-stderr") val saveStderr: Boolean
)

case class StartServerResponse(
    val success: Boolean,
    val error: String
)

case class StopServerRequest(
    val force: Boolean
)

case class StopServerResponse(
    val success: Boolean,
    val error: String
)
