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

final case class RemoveFileRequest(
    @JsonProperty("path") val pathToFile: String,
)

final case class RemoveFileResponse(
    @JsonProperty("task-id") val taskId: String,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String,
)

final case class CreateFileRequest(
    @JsonProperty("path") val pathToFile: String,
    @JsonProperty("content-base64") val contentBase64: String,
)

final case class CreateFileResponse(
    @JsonProperty("task-id") val taskId: String,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String
)

final case class CreateDirectoryRequest(
    @JsonProperty("path") val pathToDirectory: String,
)

final case class CreateDirectoryResponse(
    @JsonProperty("task-id") val taskId: String,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String
)

final case class ListDirectoryRequest(
    @JsonProperty("path") val pathToDirectory: String,
)

final case class ListDirectoryResponse(
    @JsonProperty("files") val files: Seq[FileNode],
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String
)

final case class FileNode(
    @JsonProperty("path") val path: String,
    @JsonProperty("type") val nodeType: String,
    @JsonProperty("size") val size: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("extension") val extension: String
)

final case class AcceptTaskRequest(
    @JsonProperty("task-id") val taskId: String,
)

final case class GetFileContentRequest(
    @JsonProperty("path") val pathToFile: String,
)

final case class GetFileContentResponse(
    @JsonProperty("content-base64") val contentBase64: String,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("error") val error: String
)
