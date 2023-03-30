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
如果没有配置，本插件会尝试做一些特殊处理，~~一般情况下也能正常使用~~  
`api.openai.com` IP地址变更，套了一层 `cloudflare`, 特殊处理失效, 表现为 `403`-`text/html` 响应 

开启聊天默认使用 `chat` (chat_prefix) 触发, 基于 `/v1/chat/completions` 默认模型 `gpt-3.5-turbo`  
开启问答默认使用 `Q&A` (question_prefix) 触发, 基于 `/v1/completions` 默认模型 `text-davinci-003`  
开启图片生成默认使用 `?` (image_prefix) 触发  
停止聊天或问答默认使用 `stop` 触发  
重载配置默认使用 `openai-reload` (reload_prefix) 触发，使用后将重新加载配置，无需重启 `Mirai Console`

`/v1/chat/completions` 消耗 `tokens` 比 `/v1/completions` 小，但是聊天特化，请自行注意两者的区别

默认情况下 `权限检查` 是关闭的, 需要在基本配置中配置开启 (开启后会在日志中给出权限ID)

预置 `prompt` (也称语境或人格)  
用法例子 `chat #猫娘`  
自定义配置请阅读 [预置语境](#预置语境)  
预设的加载优先级 开始指令时指定(`chat #xxx`) > 用户绑定的预设 > 群绑定的预设  

**Since 1.1.0** 添加 `@` 触发聊天配置(手机端回复消息时会附带@, 请注意不要误触)  
**Since 1.2.0** 将 `chat` 功能对接至 <https://platform.openai.com/docs/api-reference/chat>, 节省 Usage  
**Since 1.2.2** Fake SSLSocket  
**Since 1.3.0** 添加经济系统对接 和 预置语境  
**Since 1.3.1** 添加一些可能会带来BUG的配置 `立刻开始聊天`, `保持前缀检查`  
**Since 1.4.0** 修改经济检查逻辑，仅在聊天开始前检查是否余额  
**Since 1.4.1** 添加绑定预设的引用 `~`

## 效果

聊天  
![chat](example/screenshot/chat.jpg)
问答  
![completion](example/screenshot/completion.jpg)
图片  
![image](example/screenshot/image.jpg)
预置语境  
![bind](example/screenshot/bind.png)

## 配置

`openai.yml` 基本配置

*   `proxy` 代理 协议支持 `socks` 和 `http`, 例如 `socks://127.0.0.1:7890`
*   `completion_prefix` 自定义模型触发前缀, 用来测试模型及消耗, 默认 `> `
*   `image_prefix` 图片生成触发前缀, 默认 `? `
*   `chat_prefix` 聊天模型触发前缀, 默认 `chat`
*   `question_prefix` 问答模型触发前缀, 默认 `Q&A`
*   `reload_prefix` 重载配置触发前缀, 默认 `openai-reload`
*   `economy_set_prefix` 经济设置触发前缀, 默认 `tokens`
*   `bind_set_prefix` 绑定设置触发前缀, 默认 `bind`
*   `stop` 停止聊天或问答, 默认 `stop`
*   `token` [Secret Key](https://platform.openai.com/account/api-keys), 插件第一次启动时会要求输入，不用再次编辑文件
*   `error_reply` 发生错误时回复用户，默认 `true`
*   `end_reply` 停止聊天时回复用户，默认 `false`
*   `chat_limit` 聊天服务个数限制
*   `chat_by_at` 聊天模型触发于`@`，默认 `false`
*   `has_permission` 权限检查, 为 `true` 时开启
*   `at_once` 立刻开始聊天/问答(即不会发送 `聊天/问答将开始`, 而是直接接着指令开始聊天) 默认 `false`
*   `keep_prefix_check` 保持前缀检查(即一定要附带前缀/`@`才会触发对话)，默认 `false`
*   `has_economy` 经济系统, 为 `true` 时开启 @see <https://github.com/cssxsh/mirai-economy-core>

`completion.yml` 自定义模型详细配置

*   `model` 模型
*   `max_tokens` 回答长度
*   `temperature` 逆天程度，取值 `0.0~2.0`

`image.yml` 图片生成模型详细配置

*   `number` 图片张数
*   `size` 可选的范围是固定的，请不要更改
*   `format` 下载方式，请不要更改

`chat.yml` 聊天模型详细配置

*   `gpt_model` 模型
*   `timeout` 等待停止时间
*   `max_tokens` 回答长度
*   `temperature` 逆天程度，取值 `0.0~2.0`

`question.yml` 问答模型详细配置

*   `model` 模型
*   `timeout` 等待停止时间
*   `max_tokens` 回答长度
*   `temperature` 逆天程度，取值 `0.0~2.0`

`economy.yml` 经济相关

*   `sign_plus_assign` 签到增加的tokens数量，默认 `1024`

### 注册

<https://juejin.cn/post/7175153557941780541>

### 测试

在线测试  
<https://platform.openai.com/playground>

官方例子  
<https://platform.openai.com/examples>

## 对接经济系统

@see <https://github.com/cssxsh/mirai-economy-core>

`openai.com` 通过 `tokens`, 分词数量（一般来说句子越长，分词越长）来计算费用。  
为了防止某些用户过度消耗 `tokens`, 导致额度耗尽。  
对接后，经济系统将为每个用户计算 `tokens` 额度，个人的可用 `tokens` 为 `0` 时，聊天功能将拒绝响应并提示。

`管理员(未开启权限检查)` 或者 `持有经济权限的用户(已开启权限检查)` 可用为用户设置 `tokens` 额度

用法为 `tokens 114514 @12345` 为用户 `12345` 分配 `114514` 个 `tokens`  
或者 `tokens 12345` 为全部群成员分配 `114514` 个 `tokens`

## 预置语境

有些人也将其形容为 `人格`, 实际上这个功能是告诉机器人你需要扮演什么角色或者提供什么功能  

配置方法, 在插件数据目录 `data/xyz.cssxsh.mirai.plugin.mirai-openai-plugin` 下新建 `XXX.txt`  
然后填入你需要预置的内容

使用方法，在 `chat` 后面附加 `#XXX`, 例如 `chat #猫娘`  
或者使用 `bind` 为当前用户绑定一个默认 `prompt`, 例如 `bind 猫娘`  
当消息为群消息，且当前用户为管理员/群主时绑定的对象将是 `群` ，即为 `群` 设置预设

由于 `chat` 会在有附加的内容时(附加的内容会被当作预设处理)，不启用绑定预设  
所以添加一个新功能, 使用 `~` 或者 `.` 来引用绑定预设, 例如 `chat ~ ...`

## 安装

### MCL 指令安装

**请确认 mcl.jar 的版本是 2.1.0+**  
`./mcl --update-package xyz.cssxsh.mirai:mirai-openai-plugin --channel maven-stable --type plugins`

### 手动安装

1.  从 [Releases](https://github.com/cssxsh/mirai-openai-plugin/releases) 或者 [Maven](https://repo1.maven.org/maven2/xyz/cssxsh/mirai/mirai-openai-plugin/) 下载 `mirai2.jar`
2.  将其放入 `plugins` 文件夹中

## [爱发电](https://afdian.net/@cssxsh)

![afdian](.github/afdian.jpg)
