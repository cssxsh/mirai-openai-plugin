# [Mirai OpenAI Plugin](https://github.com/cssxsh/mirai-openai-plugin)

> Mirai Console 下的 OpenAI Chat Bot 插件

[![maven-central](https://img.shields.io/maven-central/v/xyz.cssxsh.mirai/mirai-openai-plugin)](https://search.maven.org/artifact/xyz.cssxsh.mirai/mirai-openai-plugin)
[![test](https://github.com/cssxsh/mirai-openai-plugin/actions/workflows/test.yml/badge.svg)](https://github.com/cssxsh/mirai-openai-plugin/actions/workflows/test.yml)
[![MiraiForum](https://img.shields.io/badge/post-on%20MiraiForum-yellow)](https://mirai.mamoe.net/topic/1849)

Be based on <https://chat.openai.com/>  
OpenAI 目前对注册有一定要求，请先阅读 [注册](#注册), 然后创建 [Secret Key](https://platform.openai.com/account/api-keys)  
注意，不是 `Cookie Token`, 是 `api-key`  
~~另外，可以直连，不需要代理，只是注册账号的时候需要代理~~   
`api.openai.com` 已列入黑名单，你需要配置代理

开启聊天默认使用 `chat` (chat_prefix) 触发  
开启问答默认使用 `Q&A` (question_prefix) 触发  
开启图片生成默认使用 `?` (image_prefix) 触发  
停止聊天或问答默认使用 `stop` 触发  
默认情况下 `权限检查` 是关闭的, 需要在基本配置中配置开启(会在日志中给出权限ID)  

**Since 1.1.0** 添加 `@` 触发聊天配置(手机端回复消息时会附带@, 请注意不要误触)  
**Since 1.2.0** 将 `chat` 功能对接至 <https://platform.openai.com/docs/api-reference/chat>, 节省 Usage

## 效果

![chat](example/screenshot/chat.jpg)
![completion](example/screenshot/completion.jpg)
![image](example/screenshot/image.jpg)

## 配置

`openai.yml` 基本配置

*   `proxy` 代理
*   `completion_prefix` 自定义模型触发前缀, 默认 `> `
*   `image_prefix` 图片生成触发前缀, 默认 `? `
*   `chat_prefix` 聊天模型触发前缀, 默认 `chat`
*   `question_prefix` 问答模型触发前缀, 默认 `Q&A`
*   `reload_prefix` 重载配置触发前缀, 默认 `openai-reload`
*   `stop` 停止聊天或问答, 默认 `stop`
*   `token` [Secret Key](https://platform.openai.com/account/api-keys), 插件第一次启动时会要求输入，不用编辑文件
*   `error_reply` 发生错误时回复用户，默认 `true`
*   `end_reply` 停止聊天时回复用户，默认 `false`
*   `chat_limit` 聊天服务个数限制
*   `chat_by_at` 聊天模型触发于`@`，默认 `false`
*   `has_permission` 权限检查, 为 `true` 开启

`completion.yml` 自定义模型详细配置

*   `model` 模型
*   `max_tokens` 回答长度

`image.yml` 图片生成模型详细配置

*   `number` 图片张数
*   `size` 可选的范围是固定的，请不要更改
*   `format` 下载方式，请不要更改

`chat.yml` 聊天模型详细配置

*   `gpt_model` 模型
*   `timeout` 等待停止时间
*   `max_tokens` 回答长度

`question.yml` 问答模型详细配置

*   `timeout` 等待停止时间
*   `max_tokens` 回答长度

### 注册

<https://juejin.cn/post/7175153557941780541>

### 测试

在线测试  
<https://platform.openai.com/playground>

官方例子  
<https://platform.openai.com/examples>

## 安装

### MCL 指令安装

**请确认 mcl.jar 的版本是 2.1.0+**  
`./mcl --update-package xyz.cssxsh.mirai:mirai-openai-plugin --channel maven-stable --type plugins`

### 手动安装

1.  从 [Releases](https://github.com/cssxsh/mirai-openai-plugin/releases) 或者 [Maven](https://repo1.maven.org/maven2/xyz/cssxsh/mirai/mirai-openai-plugin/) 下载 `mirai2.jar`
2.  将其放入 `plugins` 文件夹中

## [爱发电](https://afdian.net/@cssxsh)

![afdian](.github/afdian.jpg)
