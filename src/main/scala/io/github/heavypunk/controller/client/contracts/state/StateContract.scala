package io.github.heavypunk.controller.client.contracts.state

import com.fasterxml.jackson.annotation.JsonProperty

case class ControllerPingResponse(
    @JsonProperty("service-name") val serviceName: String,
    var success: Boolean
)

case class ControllerServerRunningResponse(
    @JsonProperty("is-running") val isRunning: Boolean,
    var success: Boolean,
    var error: String
)
