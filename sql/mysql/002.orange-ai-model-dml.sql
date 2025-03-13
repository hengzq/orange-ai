-- ---------------------------------------
-- orange-ai 模型数据管理
-- ---------------------------------------

use orange;

BEGIN;

insert into orange.ai_model (id, tenant_id, platform, name, code, type, enabled, sort, description, created_by, created_at, updated_by, updated_at)
values  (1, -100, 'ALI_BAI_LIAN', 'qwen2.5-3b-instruct', 'qwen2.5-3b-instruct', 'CHAT', 1, 1, '# Qwen2.5
Qwen2.5 是 Qwen 大型语言模型的最新系列。针对 Qwen2.5，我们发布了一系列基础语言模型和指令调优语言模型，参数规模从 5 亿到 720 亿不等。Qwen2.5 在 Qwen2 基础上进行了以下改进：

在我们最新的大规模数据集上进行预训练，包含多达18T个Token。

由于我们在这些领域的专业专家模型，模型的知识显著增多，编码和数学能力也大大提高。

在遵循指令、生成长文本（超过 8K 个标记）、理解结构化数据（例如表格）和生成结构化输出（尤其是 JSON）方面有显著改进。对系统提示的多样性更具弹性，增强了聊天机器人的角色扮演实现和条件设置。

支持超过 29 种语言，包括中文、英语、法语、西班牙语、葡萄牙语、德语、意大利语、俄语、日语、韩语、越南语、泰语、阿拉伯语等。

专业领域的专家语言模型，即用于编程的 Qwen2.5-Coder和用于数学的 Qwen2.5-Math，相比其前身 CodeQwen1.5 和 Qwen2-Math 有了实质性的改进。 具体来说，Qwen2.5-Coder 在包含 5.5 Ttokens 编程相关数据上进行了训练，使即使较小的编程专用模型也能在编程评估基准测试中表现出媲美大型语言模型的竞争力。 同时，Qwen2.5-Math 支持 中文和 英文，并整合了多种推理方法，包括CoT（Chain of Thought）、PoT（Program of Thought）和 TIR（Tool-Integrated Reasoning）。[API参考](https://help.aliyun.com/zh/model-studio/developer-reference/use-qwen-by-calling-api?spm=a2c4g.11186623.0.0.11fa48233xztbN)



- [阿里-文档](https://help.aliyun.com/zh/model-studio/getting-started/models?spm=a2c4g.11186623.0.0.599c2bdbUXk1Qm#ced16cb6cdfsy)', -100, '2024-10-30 10:30:03', null, '2024-10-30 10:30:03'),
        (100, -100, 'ALI_BAI_LIAN', 'Cosplay动漫人物', 'wanx-style-cosplay-v1', 'TEXT_TO_IMAGE', 1, 1, '# 动漫人物生成
> Cosplay动漫人物生成通过输入人像图片和卡通形象图片，可快速生成人物卡通写真。[API参考](https://help.aliyun.com/zh/model-studio/developer-reference/cosplay-anime-character-generation-api?spm=a2c4g.11186623.0.0.7a711d1cyJUgTy)

| 模型名称 | 示例输入 | 示例输出 | 单价 | 免费额度 |
| - | - | - | - | - |
| wanx-style-cosplay-v1 | ![https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/6304772271/p826953.png](https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/6304772271/p826953.png) | ![https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/7304772271/p826954.png](https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/7304772271/p826954.png) | 目前仅供免费体验。免费额度用完后不可调用，敬请关注后续动态。| 300张 有效期：百炼开通后180天内 |
- [阿里-文档地址](https://help.aliyun.com/zh/model-studio/getting-started/models?spm=a2c4g.11186623.0.0.599c2bdbUXk1Qm#32c314e53ena7)', -100, '2024-10-30 10:30:04', null, '2024-10-30 10:30:04'),
        (200, -100, 'ZHI_PU', 'GLM-4-Flash', 'GLM-4-Flash', 'CHAT', 1, 1, '# 智谱AI大模型

智谱AI 开放平台提供了包括通用大模型、图像大模型、超拟人大模型、向量大模型等多种模型。


## 语言模型

| 模型 | 描述 | 上下文 | 最大输出 |
| - | - | - | - |
| GLM-4-Flash | 	免费调用：智谱AI首个免费API，零成本调用大模型。 | 128K | 4K |', -100, '2024-10-30 10:30:04', null, '2024-10-30 10:30:04'),
        (300, -100, 'TENCENT', 'hunyuan-turbo', 'hunyuan-turbo', 'CHAT', 1, 1, '# 混元生文


| 模型名称 | 能力和特征 | 分支版本 | 输入输出 |
| - | - | - | - |
| hunyuan-turbo | 混元全新一代大语言模型的预览版，采用全新的混合专家模型（MoE）结构，相比hunyuan-pro推理效率更快，效果表现更强。 | / | 最大输入28k,最大输出4k。|

- [https://cloud.tencent.com/document/product/1729/104753](https://cloud.tencent.com/document/product/1729/104753)

', -100, '2024-10-30 10:30:05', null, '2024-10-30 10:30:05'),
        (400, -100, 'QIAN_FAN', 'ERNIE-4.0-8K', 'completions_pro', 'CHAT', 1, 1, '# ERNIE-4.0-8K

> ERNIE 4.0是百度自研的旗舰级超大规模⼤语⾔模型，相较ERNIE 3.5实现了模型能力全面升级，广泛适用于各领域复杂任务场景；支持自动对接百度搜索插件，保障问答信息时效，支持5K tokens输入+2K tokens输出。ERNIE-4.0-8K是模型的一个版本，本文介绍了相关API及使用。

## 参考
- [https://cloud.baidu.com/doc/WENXINWORKSHOP/s/clntwmv7t](https://cloud.baidu.com/doc/WENXINWORKSHOP/s/clntwmv7t)', -100, '2024-10-30 10:30:05', null, '2024-10-30 10:30:05'),
        (1840206536911179778, -100, 'ALI_BAI_LIAN', 'wanx-v1', 'wanx-v1', 'TEXT_TO_IMAGE', 1, 1, null, -100, '2024-09-29 09:46:42', -100, '2024-09-29 09:46:42'),
        (1840207643985137666, -100, 'ALI_BAI_LIAN', 'flux-dev', 'flux-dev', 'TEXT_TO_IMAGE', 1, 1, null, -100, '2024-09-29 09:51:06', -100, '2024-09-29 09:51:06'),
        (1844994502859640833, -100, 'TENCENT', 'hunyuan-turbo', 'hunyuan-turbo', 'CHAT', 1, 1, '# 混元生文


| 模型名称 | 能力和特征 | 分支版本 | 输入输出 |
| - | - | - | - |
| hunyuan-turbo | 混元全新一代大语言模型的预览版，采用全新的混合专家模型（MoE）结构，相比hunyuan-pro推理效率更快，效果表现更强。 | / | 最大输入28k,最大输出4k。|

- [https://cloud.tencent.com/document/product/1729/104753](https://cloud.tencent.com/document/product/1729/104753)

', -100, '2024-10-12 14:52:22', -100, '2024-10-14 09:32:10'),
        (1851440802895372289, -100, 'QIAN_FAN', 'ERNIE-4.0-8K', 'completions_pro', 'CHAT', 1, 1, '# ERNIE-4.0-8K

> ERNIE 4.0是百度自研的旗舰级超大规模⼤语⾔模型，相较ERNIE 3.5实现了模型能力全面升级，广泛适用于各领域复杂任务场景；支持自动对接百度搜索插件，保障问答信息时效，支持5K tokens输入+2K tokens输出。ERNIE-4.0-8K是模型的一个版本，本文介绍了相关API及使用。

## 参考
- [https://cloud.baidu.com/doc/WENXINWORKSHOP/s/clntwmv7t](https://cloud.baidu.com/doc/WENXINWORKSHOP/s/clntwmv7t)', -100, '2024-10-30 09:47:40', -100, '2024-10-30 10:25:17'),
        (1851797712169570305, -100, 'QIAN_FAN', 'Stable-Diffusion-XL', 'sd_xl', 'TEXT_TO_IMAGE', 1, 1, '# Stable-Diffusion-XL

> Stable-Diffusion-XL是业内知名的跨模态大模型，由StabilityAI研发并开源，有着业内领先的图像生成能力。本文介绍了相关API。

## 功能介绍
> 调用本接口，根据用户输入的文本生成图片。


## 参考
- [https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Klkqubb9w](https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Klkqubb9w)', -100, '2024-10-31 09:25:54', -100, '2024-10-31 09:25:54'),
        (1888139537350930434, -100, 'DEEP_SEEK', 'deepseek-chat', 'deepseek-chat', 'CHAT', 1, 1, '#  模型介绍

1. deepseek-chat 模型已经升级为 DeepSeek-V3；deepseek-reasoner 模型为新模型 DeepSeek-R1。
2. 思维链为deepseek-reasoner模型在给出正式回答之前的思考过程，其原理详见推理模型。
3. 如未指定 max_tokens，默认最大输出长度为 4K。请调整 max_tokens 以支持更长的输出。
3. 关于上下文缓存的细节，请参考DeepSeek 硬盘缓存。
4. deepseek-reasoner的输出 token 数包含了思维链和最终答案的所有 token，其计价相同。



## 参考
- [https://api-docs.deepseek.com/zh-cn/quick_start/pricing](https://api-docs.deepseek.com/zh-cn/quick_start/pricing)

', -100, '2025-02-08 16:15:20', -100, '2025-02-10 09:32:16'),
        (1891736597643333634, -100, 'ALI_BAI_LIAN', 'text-embedding-v2', 'text-embedding-v2', 'EMBEDDING', 1, 1, '## Embedding

Embedding将文本、图像、音频、视频等数据类型表示为数学空间中的向量。通过计算向量之间的距离或夹角，可以量化数据的相似度，从而作用于分类、检索、推荐等任务。

## 参考
- [https://help.aliyun.com/zh/model-studio/user-guide/embedding?spm=a2c4g.11186623.help-menu-2400256.d_1_0_7.4717322cvbQHhf&scm=20140722.H_2842587._.OR_help-T_cn~zh-V_1](https://help.aliyun.com/zh/model-studio/user-guide/embedding?spm=a2c4g.11186623.help-menu-2400256.d_1_0_7.4717322cvbQHhf&scm=20140722.H_2842587._.OR_help-T_cn~zh-V_1)', -100, '2025-02-18 14:28:46', -100, '2025-02-18 14:39:53'),
        (1898938587893399554, -100, 'ALI_BAI_LIAN', 'deepseek-r1', 'deepseek-r1', 'CHAT', 1, 1, '', -100, '2025-03-10 11:26:55', -100, '2025-03-10 11:26:55'),
        (1898938804977991681, -100, 'ALI_BAI_LIAN', 'qwq-plus-latest', 'qwq-plus-latest', 'CHAT', 1, 1, null, -100, '2025-03-10 11:27:46', -100, '2025-03-10 11:27:46');

COMMIT;