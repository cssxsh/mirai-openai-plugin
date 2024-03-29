## 1.5.1 (23/04/19)

1.  feat: handle OpenAiException
2.  feat: config show_exception

## 1.5.0 (23/04/11)

1.  fix: Empty to ...
2.  fix: reset '***'
3.  feat: cname by properties, example `-Dxyz.cssxsh.openai.cname=false`
4.  feat: community-prompts from <https://chathub.gg/api/community-prompts> 

将会移动 `prompt` 到新路径 `data/xyz.cssxsh.mirai.plugin.mirai-openai-plugin/prompts`

## 1.4.2 (23/03/31)

1.  fix: prompt `~`

## 1.4.1 (23/03/30)

1.  fix: config-reload
2.  fix: prompt load
3.  feat: prompt `~`, close #25
4.  fix: ip
5.  feat: check cloudflare

## 1.4.0 (23/03/29)

1.  fix: economy and once
2.  feat: sign_plus_assign, close #22
3.  feat: start info config
4.  feat: group bind, close #19

## 1.3.3 (23/03/18)

1.  fix: economy all

## 1.3.2 (23/03/17)

1.  fix: depends on mirai-economy-core
2.  fix: MiraiOpenAiTokens register
3.  feat: has_economy config

## 1.3.1 (23/03/16)

1.  feat: at once
2.  feat: keep prefix check
3.  feat: chat end log

## 1.3.0 (23/03/15)

1.  feat: tokens economy
2.  feat: bind prompts

## 1.2.3 (23/03/07)

1.  fix：at chat 修复持有权限时AT一定会触发 `chat` 的问题

## 1.2.2 (23/03/06)

1.  feat: fake
2.  fix：ChatRequest

## 1.2.1 (23/03/03)

1.  fix：chat reply

## 1.2.0 (23/03/03)

1.  fix: finishReason close #11
2.  fix: proxy
3.  feat: chat api
4.  feat: audio api
5.  update: docs link

将 `chat` 功能对接至 <https://platform.openai.com/docs/api-reference/chat>

## 1.1.0 (23/02/17)

1.  fix: writeChannel close #2
2.  feat: chat by at close #5

添加 `@` 触发聊天配置(手机端回复消息时会附带@, 请注意不要误触)