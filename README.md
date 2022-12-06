# [Mirai OpenAi Plugin](https://github.com/cssxsh/mirai-openai-plugin)

> Mirai Console 下的 OpenAi Chat Bot 插件

[![maven-central](https://img.shields.io/maven-central/v/xyz.cssxsh.mirai/mirai-openai-plugin)](https://search.maven.org/artifact/xyz.cssxsh.mirai/mirai-openai-plugin)
[![test](https://github.com/cssxsh/mirai-openai-plugin/actions/workflows/test.yml/badge.svg)](https://github.com/cssxsh/mirai-openai-plugin/actions/workflows/test.yml)

Be based on <https://chat.openai.com/>  
OpenAi 目前对注册有一定要求，请先阅读 [注册](#注册)

## 效果

![screenshot](example/screenshot.jpg)

## 配置

*   `completion_prefix` 触发前缀, 默认 `> `
*   `token` [OPENAI_TOKEN](https://beta.openai.com/account/api-keys), 插件第一次启动时会要求输入，不用编辑文件,  

### 注册

<https://zhuanlan.zhihu.com/p/589287744>

## 安装

### MCL 指令安装

**请确认 mcl.jar 的版本是 2.1.0+**  
`./mcl --update-package xyz.cssxsh.mirai:mirai-openai-plugin --channel maven-stable --type plugin`

### 手动安装

1.  从 [Releases](https://github.com/cssxsh/mirai-openai-plugin/releases) 或者 [Maven](https://repo1.maven.org/maven2/xyz/cssxsh/mirai/mirai-openai-plugin/) 下载 `mirai2.jar`
2.  将其放入 `plugins` 文件夹中