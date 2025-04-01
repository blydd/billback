# 账单管理系统 API 文档说明

## 文档说明

本目录包含账单管理系统的API接口文档和Postman导入文件，方便前端开发人员进行调试和集成。

## 文件说明

- `api_documentation.md` - 详细的API文档，包含所有接口的说明、请求参数和返回示例
- `postman_collection.json` - 可直接导入Postman的接口集合文件
- `issues.md` - 已知问题和需要改进的地方，在开发过程中请参考此文件

## 使用方法

### API文档

直接打开 `api_documentation.md` 文件，查看各接口的详细说明。文档包含：

- 接口URL
- 请求方法
- 请求参数
- 响应格式
- 示例数据

### Postman导入

1. 打开Postman客户端
2. 点击左上角的 `File` -> `Import`
3. 选择 `File` 选项卡，然后选择 `postman_collection.json` 文件
4. 点击 `Import` 按钮

导入后，你将看到名为"账单管理系统 API"的接口集合，包含所有可用的API端点。

### 设置环境变量

为了方便测试，建议在Postman中设置环境变量：

1. 点击右上角的 `Environment` 下拉菜单
2. 点击 `Add` 创建新环境
3. 添加以下变量：
   - `baseUrl`: 设置为API的基础地址，例如 `http://localhost:8080`
4. 保存环境并在右上角选择该环境

完成这些设置后，你就可以直接点击发送请求来测试API了。

## 错误处理

所有API都使用统一的错误响应格式：

```json
{
  "code": 500,  // 错误代码
  "message": "错误信息",  // 错误详情
  "data": null  // 数据为空
}
```

## 已知问题

系统中存在一些已知问题，在调用API时需要特别注意。详情请参考 `issues.md` 文件。

## 联系我们

如有任何问题或需要进一步支持，请联系后端开发团队。 