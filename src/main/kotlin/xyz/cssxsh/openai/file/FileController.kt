package xyz.cssxsh.openai.file

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import xyz.cssxsh.openai.*

/**
 * [Files](https://platform.openai.com/docs/api-reference/files)
 */
public class FileController(private val client: OpenAiClient) {

    /**
     * [List files](https://platform.openai.com/docs/api-reference/files/list)
     */
    public suspend fun list(): List<FileInfo> {
        val response = client.http.get("${client.config.api}/files")
        val body = response.body<ListWrapper<FileInfo>>()

        return body.data
    }

    /**
     * [Upload file](https://platform.openai.com/docs/api-reference/files/upload)
     */
    public suspend fun create(file: InputProvider, purpose: String): FileInfo {
        val response = client.http.submitFormWithBinaryData("${client.config.api}/files", formData {
            append("file", file)
            append("purpose", purpose)
        })

        return response.body()
    }

    /**
     * [Delete file](https://platform.openai.com/docs/api-reference/files/delete)
     */
    public suspend fun delete(fileId: String): FileInfo {
        val response = client.http.delete("${client.config.api}/files/${fileId}")

        return response.body()
    }

    /**
     * [Retrieve file](https://platform.openai.com/docs/api-reference/files/retrieve)
     */
    public suspend fun retrieve(fileId: String): FileInfo {
        val response = client.http.get("${client.config.api}/files/${fileId}")

        return response.body()
    }

    /**
     * [Retrieve file content](https://platform.openai.com/docs/api-reference/files/retrieve-contents)
     */
    public suspend fun download(fileId: String): ByteReadChannel {
        val response = client.http.get("${client.config.api}/files/${fileId}/content")

        return response.bodyAsChannel()
    }
}