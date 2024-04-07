# ULearn

## Description

這是一個獨立開發的線上校內問答網站, 讓用戶可以自由的在上面發問以及回答問題.  旨在解決學生遇到問題時, 可以第一時間找到與自己擁有相同問題的人一起討論.
## User Requirements

### User

* 學生
* 管理員
* 老師(待考慮)
### Functional Requirements

* **學生**
  * 登入 (通過學校email)
  * 提問, 發問, 回覆(可添加圖片)
  * 可查詢問題, 關註, 投票, 即收藏
  * 可將問題過濾查詢 (依據標簽)
  * 聲望值 (通過發文數量, 收集點讚數量, 以及收藏量, 來增加)
  * 上傳歷屆考卷即解答, 評論, 看同意次數確定可信程度 (暫定使用爬蟲完成)
  * 線上聊天室
* **管理員**
  * 刪除違規文章
  * 封禁賬號
### Non Functional Requirements

- 用戶的點讚將定時被存入數據庫, 來防止有人頻繁點擊刷新投票
- 能在巔峰時段正確處理使用者的請求
- AES加密用戶密碼
## Techstack

* Vue 前端框架
* SpringBoot 後端框架
* HuTool 工具類
* Sa-Token 用戶登錄Token, 權限管控
* SpringDoc 文檔撰寫工具
* RocketMQ 做消息推送, 削峰
* lombok 精簡代碼
* Redis 緩存
* MySQL 持久層
* Quartz 定時任務
* FastDFS 存儲用戶影片, 圖片, 以及獲取
* Elastic Search 做全局查詢
* Nginx 反向代理, 服務器負載均衡
* Docker 容器