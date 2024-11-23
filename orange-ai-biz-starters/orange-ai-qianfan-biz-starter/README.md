# orange-ai-qianfan-biz-starter

> `orange-ai-qianfan-biz-starter`项目基于`Spring Boot`框架，旨在简化百度智能云-千帆ModelBuilder的集成过程，为开发者提供便捷而强大的AI解决方案。
> 该项目通过预设配置和工具，帮助团队快速利用百度智能云-千帆ModelBuilder大模型在自然语言处理、图像识别等领域的先进能力，加速产品创新。
> 此外，它强调易用性和灵活性，提供了丰富的API接口以适应不同场景，并支持自定义设置，让新手和经验丰富的开发者都能根据业务需求调整参数或添加功能模块。
> 依托腾讯云的支持，该解决方案确保了服务的稳定性和数据的安全性，为企业应用提供可靠保障。

## 自定义配置

| 属性                           | 必填   | 描述                                                                                                                                                                                                                                                                                                                                                                                                   | 默认值 |
|------------------------------|------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----|
| spring.ai.qianfan.api-key    | true | 应用的API Key，说明： <br/>（1）在[千帆ModelBuilder控制台-应用列表](https://console.bce.baidu.com/qianfan/ais/console/applicationConsole/application/v1)查看 <br/> ![https://bce.bdstatic.com/doc/ai-cloud-share/WENXINWORKSHOP/image_cca681f.png](https://bce.bdstatic.com/doc/ai-cloud-share/WENXINWORKSHOP/image_cca681f.png)（2）如果应用列表无应用，需先创建应用，请参考[如何创建应用](https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Slkkydake)   |     |
| spring.ai.qianfan.secret-key | true | 应用的Secret Key，说明：<br/>（1）在[千帆ModelBuilder控制台-应用列表](https://console.bce.baidu.com/qianfan/ais/console/applicationConsole/application/v1)查看 <br/> ![https://bce.bdstatic.com/doc/ai-cloud-share/WENXINWORKSHOP/image_cca681f.png](https://bce.bdstatic.com/doc/ai-cloud-share/WENXINWORKSHOP/image_cca681f.png)（2）如果应用列表无应用，需先创建应用，请参考[如何创建应用](https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Slkkydake) |     |

## 案例

```yaml
spring:
  ai:
    qianfan:
      api-key: xxxxx
      secret-key: xxxxx
```