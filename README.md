# [Mirai OpenAI Plugin](https://github.com/cssxsh/mirai-openai-plugin)

> Mirai Console 下的 OpenAI Chat Bot 插件

[![maven-central](https://img.shields.io/maven-central/v/xyz.cssxsh.mirai/mirai-openai-plugin)](https://search.maven.org/artifact/xyz.cssxsh.mirai/mirai-openai-plugin)
[![test](https://github.com/cssxsh/mirai-openai-plugin/actions/workflows/test.yml/badge.svg)](https://github.com/cssxsh/mirai-openai-plugin/actions/workflows/test.yml)

Be based on <https://chat.openai.com/>  
OpenAI 目前对注册有一定要求，请先阅读 [注册](#注册), 然后创建 [TOKEN](https://beta.openai.com/account/api-keys)  
开启聊天默认使用 `chat` (chat_prefix) 触发  
开启问答默认使用 `Q&A` (question_prefix) 触发  
开启图片生成默认使用 `?` (image_prefix) 触发  
停止聊天或问答默认使用 `stop` 触发  

## 效果

![chat](example/screenshot/chat.jpg)
![completion](example/screenshot/completion.jpg)
![image](example/screenshot/image.jpg)

## 配置

`openai.yml` 基本配置

*   `completion_prefix` 自定义模型触发前缀, 默认 `> `
*   `image_prefix` 图片生成触发前缀, 默认 `? `
*   `chat_prefix` 聊天模型触发前缀, 默认 `chat`
*   `question_prefix` 问答模型触发前缀, 默认 `Q&A`
*   `reload_prefix` 重载配置触发前缀, 默认 `openai-reload`
*   `stop` 停止聊天或问答, 默认 `stop`
*   `token` [OPENAI_TOKEN](https://beta.openai.com/account/api-keys), 插件第一次启动时会要求输入，不用编辑文件
*   `error_reply` 发生错误时回复用户，默认 `true`
*   `end_reply` 停止聊天时回复用户，默认 `false`
*   `chat_limit` 聊天服务个数限制
*   `has_permission` 权限检查

`completion.yml` 自定义模型详细配置

*   `model` 模型
*   `max_tokens` 回答长度

`image.yml` 图片生成模型详细配置

*   `number` 图片张数
*   `size` 可选的范围是固定的，请不要更改
*   `format` 下载方式，请不要更改

`chat.yml` 聊天模型详细配置

*   `timeout` 等待停止时间
*   `max_tokens` 回答长度

`question.yml` 问答模型详细配置

*   `timeout` 等待停止时间
*   `max_tokens` 回答长度

### 注册

<https://blog.csdn.net/duoshehuan6005/article/details/128184450>

### 测试

在线测试  
<https://beta.openai.com/playground>

官方例子  
<https://beta.openai.com/examples>

## 安装

### MCL 指令安装

**请确认 mcl.jar 的版本是 2.1.0+**  
`./mcl --update-package xyz.cssxsh.mirai:mirai-openai-plugin --channel maven-stable --type plugin`

### 手动安装

1.  从 [Releases](https://github.com/cssxsh/mirai-openai-plugin/releases) 或者 [Maven](https://repo1.maven.org/maven2/xyz/cssxsh/mirai/mirai-openai-plugin/) 下载 `mirai2.jar`
2.  将其放入 `plugins` 文件夹中

## [爱发电](https://afdian.net/@cssxsh)

![afdian](example/sponsor/afdian.jpg)
