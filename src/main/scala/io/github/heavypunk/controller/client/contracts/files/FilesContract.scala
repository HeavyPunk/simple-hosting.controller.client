package io.github.heavypunk.controller.client.contracts.files

import com.fasterxml.jackson.annotation.JsonProperty

final case class PollTaskRequest(
    @JsonProperty("task-id") val taskId: String,
)

final case class PollTaskResponse(
    @JsonProperty("task-status") val taskStatus: String,
    @JsonProperty("task-error") val taskError: String,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String
)

final case class PushFileToS3Request(
    @JsonProperty("s3-bucket") val s3Bucket: String,
    @JsonProperty("s3-file-name") val s3FileName: String,
    @JsonProperty("local-file-path") val localFilePath: String,
)

final case class PushFileToS3Response(
    @JsonProperty("task-id") val taskId: String,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String,
)

final case class PullFileFromS3Request(
    @JsonProperty("s3-bucket") val s3Bucket: String,
    @JsonProperty("s3-file-name") val s3FileName: String,
    @JsonProperty("local-file-path") val localFilePath: String,
)

final case class PullFileFromS3Response(
    @JsonProperty("task-id") val taskId: String,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String,
)
