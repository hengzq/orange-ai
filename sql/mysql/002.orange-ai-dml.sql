-- ---------------------------------------
-- orange-ai 服务预置数据
-- ---------------------------------------

use orange;

-- 默认用户ID
SET @u_id := -100;

-- 默认租户ID
SET @t_id := -100;

-- ----------------------------
-- 预置模型数据
-- ----------------------------
BEGIN;

delete
from ai_model
where id <= 1000;


# -------------------------------------------------------------------------------------------------------------------
# -----------------------------------     阿里大模型    ---------------------------------------------------------------
# -------------------------------------------------------------------------------------------------------------------
INSERT INTO ai_model (id, tenant_id, platform, name, code, type, enabled, sort, description, created_by)
VALUES (1, @t_id, 'ALI_BAI_LIAN', 'qwen2.5-3b-instruct', 'qwen2.5-3b-instruct', 'CHAT', 1, 1, '# Qwen2.5
Qwen2.5 是 Qwen 大型语言模型的最新系列。针对 Qwen2.5，我们发布了一系列基础语言模型和指令调优语言模型，参数规模从 5 亿到 720 亿不等。Qwen2.5 在 Qwen2 基础上进行了以下改进：

在我们最新的大规模数据集上进行预训练，包含多达18T个Token。

由于我们在这些领域的专业专家模型，模型的知识显著增多，编码和数学能力也大大提高。

在遵循指令、生成长文本（超过 8K 个标记）、理解结构化数据（例如表格）和生成结构化输出（尤其是 JSON）方面有显著改进。对系统提示的多样性更具弹性，增强了聊天机器人的角色扮演实现和条件设置。

支持超过 29 种语言，包括中文、英语、法语、西班牙语、葡萄牙语、德语、意大利语、俄语、日语、韩语、越南语、泰语、阿拉伯语等。

专业领域的专家语言模型，即用于编程的 Qwen2.5-Coder和用于数学的 Qwen2.5-Math，相比其前身 CodeQwen1.5 和 Qwen2-Math 有了实质性的改进。 具体来说，Qwen2.5-Coder 在包含 5.5 Ttokens 编程相关数据上进行了训练，使即使较小的编程专用模型也能在编程评估基准测试中表现出媲美大型语言模型的竞争力。 同时，Qwen2.5-Math 支持 中文和 英文，并整合了多种推理方法，包括CoT（Chain of Thought）、PoT（Program of Thought）和 TIR（Tool-Integrated Reasoning）。[API参考](https://help.aliyun.com/zh/model-studio/developer-reference/use-qwen-by-calling-api?spm=a2c4g.11186623.0.0.11fa48233xztbN)



- [阿里-文档](https://help.aliyun.com/zh/model-studio/getting-started/models?spm=a2c4g.11186623.0.0.599c2bdbUXk1Qm#ced16cb6cdfsy)',
        @u_id);
INSERT INTO ai_model (id, tenant_id, platform, name, code, type, enabled, sort, description, created_by)
VALUES (100, @t_id, 'ALI_BAI_LIAN', 'Cosplay动漫人物', 'wanx-style-cosplay-v1', 'TEXT_TO_IMAGE', 1, 1, '# 动漫人物生成
> Cosplay动漫人物生成通过输入人像图片和卡通形象图片，可快速生成人物卡通写真。[API参考](https://help.aliyun.com/zh/model-studio/developer-reference/cosplay-anime-character-generation-api?spm=a2c4g.11186623.0.0.7a711d1cyJUgTy)

| 模型名称 | 示例输入 | 示例输出 | 单价 | 免费额度 |
| - | - | - | - | - |
| wanx-style-cosplay-v1 | ![https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/6304772271/p826953.png](https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/6304772271/p826953.png) | ![https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/7304772271/p826954.png](https://help-static-aliyun-doc.aliyuncs.com/assets/img/zh-CN/7304772271/p826954.png) | 目前仅供免费体验。免费额度用完后不可调用，敬请关注后续动态。| 300张 有效期：百炼开通后180天内 |
- [阿里-文档地址](https://help.aliyun.com/zh/model-studio/getting-started/models?spm=a2c4g.11186623.0.0.599c2bdbUXk1Qm#32c314e53ena7)',
        @u_id);

# -------------------------------------------------------------------------------------------------------------------
# ---------------------------------     智谱AI大模型    ---------------------------------------------------------------
# -------------------------------------------------------------------------------------------------------------------
INSERT INTO ai_model (id, tenant_id, platform, name, code, type, enabled, sort, description, created_by)
VALUES (200, @t_id, 'ZHI_PU', 'GLM-4-Flash', 'GLM-4-Flash', 'CHAT', 1, 1, '# 智谱AI大模型

智谱AI 开放平台提供了包括通用大模型、图像大模型、超拟人大模型、向量大模型等多种模型。


## 语言模型

| 模型 | 描述 | 上下文 | 最大输出 |
| - | - | - | - |
| GLM-4-Flash | 	免费调用：智谱AI首个免费API，零成本调用大模型。 | 128K | 4K |', @u_id);

# -------------------------------------------------------------------------------------------------------------------
# ---------------------------------     腾讯大模型    -----------------------------------------------------------------
# -------------------------------------------------------------------------------------------------------------------
INSERT INTO ai_model (id, tenant_id, platform, name, code, type, enabled, sort, description, created_by)
VALUES (300, @t_id, 'TENCENT', 'hunyuan-turbo', 'hunyuan-turbo', 'CHAT', 1, 1, '# 混元生文


| 模型名称 | 能力和特征 | 分支版本 | 输入输出 |
| - | - | - | - |
| hunyuan-turbo | 混元全新一代大语言模型的预览版，采用全新的混合专家模型（MoE）结构，相比hunyuan-pro推理效率更快，效果表现更强。 | / | 最大输入28k,最大输出4k。|

- [https://cloud.tencent.com/document/product/1729/104753](https://cloud.tencent.com/document/product/1729/104753)

', @u_id);


COMMIT;