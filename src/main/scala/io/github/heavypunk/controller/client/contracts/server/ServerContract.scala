package io.github.heavypunk.controller.client.contracts.server

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

case class SendMessageRequest(
    val message: String,
    @JsonProperty("post-system") val postSystem: String
)

case class SendMessageResponse(
    val response: String,
    val error: String,
    val success: Boolean
)

case class GetServerInfoRequest(
    @JsonProperty("post-system") val postSystem: String
)

case class GetServerInfoResponse(
    @JsonProperty("onlinePlayers") val onlinePlayers: List[String],
    val properties : Map[String, String],
    val error: String,
    val success: Boolean
)

case class GetServerLogsRequest(
    val page: Int
)

case class GetServerLogsLogRecord(
    @JsonProperty("Id") val id: Long,
    @JsonProperty("Record") val record: String
)

case class GetServerLogsResponse(
    @JsonProperty("Logs") val logs: List[GetServerLogsLogRecord],
    val error: String,
    val success: Boolean
)

final case class CheckServerRunningResponse(
    @JsonProperty("is-running") val isRunning: Boolean,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String,
)
