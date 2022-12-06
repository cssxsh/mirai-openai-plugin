# [Mirai OpenAI Plugin](https://github.com/cssxsh/mirai-openai-plugin)

> Mirai Console 下的 OpenAI Chat Bot 插件

[![maven-central](https://img.shields.io/maven-central/v/xyz.cssxsh.mirai/mirai-openai-plugin)](https://search.maven.org/artifact/xyz.cssxsh.mirai/mirai-openai-plugin)
[![test](https://github.com/cssxsh/mirai-openai-plugin/actions/workflows/test.yml/badge.svg)](https://github.com/cssxsh/mirai-openai-plugin/actions/workflows/test.yml)

Be based on <https://chat.openai.com/>  
OpenAI 目前对注册有一定要求，请先阅读 [注册](#注册)

## 效果

![completion](example/screenshot/completion.jpg)
![image](example/screenshot/image.jpg)

## 配置

`openai.yml`

*   `completion_prefix` 问答触发前缀, 默认 `> `
*   `image_prefix` 图片生成触发前缀, 默认 `? `
*   `token` [OPENAI_TOKEN](https://beta.openai.com/account/api-keys), 插件第一次启动时会要求输入，不用编辑文件

`completion.yml`

*   `max_tokens` 回答长度

`image.yml`

*   `number` 图片张数
*   `size` 可选的范围是固定的，请不要更改
*   `format` 下载方式，请不要更改

### 注册

<https://zhuanlan.zhihu.com/p/589287744>

## 安装

### MCL 指令安装

**请确认 mcl.jar 的版本是 2.1.0+**  
`./mcl --update-package xyz.cssxsh.mirai:mirai-openai-plugin --channel maven-stable --type plugin`

### 手动安装

1.  从 [Releases](https://github.com/cssxsh/mirai-openai-plugin/releases) 或者 [Maven](https://repo1.maven.org/maven2/xyz/cssxsh/mirai/mirai-openai-plugin/) 下载 `mirai2.jar`
2.  将其放入 `plugins` 文件夹中