# orange-ai-tencent-biz-starter

> `orange-ai-tencent-biz-starter`项目基于`Spring Boot`框架，旨在简化腾讯混元大模型的集成过程，为开发者提供便捷而强大的AI解决方案。
> 该项目通过预设配置和工具，帮助团队快速利用混元大模型在自然语言处理、图像识别等领域的先进能力，加速产品创新。
> 此外，它强调易用性和灵活性，提供了丰富的API接口以适应不同场景，并支持自定义设置，让新手和经验丰富的开发者都能根据业务需求调整参数或添加功能模块。
> 依托腾讯云的支持，该解决方案确保了服务的稳定性和数据的安全性，为企业应用提供可靠保障。

## 自定义配置

| 属性                          | 描述        | 必填    | 默认值                         |
|-----------------------------|-----------|-------|-----------------------------|
| spring.ai.tencent.secretId  | SecretId  | true  |                             |
| spring.ai.tencent.secretKey | SecretKey | true  |                             |
| spring.ai.tencent.endpoint  | 请求域名      | false | hunyuan.tencentcloudapi.com |

- [SecretId和SecretKey安全凭证申请](https://cloud.tencent.com/document/product/1729/101843#.E7.94.B3.E8.AF.B7.E5.AE.89.E5.85.A8.E5.87.AD.E8.AF.81)

## 案例

```yaml
spring:
  ai:
    tencent:
      secret-id: xxx
      secret-key: xxx
      endpoint: hunyuan.tencentcloudapi.com
```