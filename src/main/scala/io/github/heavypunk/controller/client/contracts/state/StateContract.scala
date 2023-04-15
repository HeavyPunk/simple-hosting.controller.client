package io.github.heavypunk.controller.client.contracts.state

import com.fasterxml.jackson.annotation.JsonProperty

case class ControllerPingResponse(
    @JsonProperty("service-name") val serviceName: String,
    var success: Boolean
)
