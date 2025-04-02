# 账单管理系统 API 文档

本文档提供账单管理系统的API接口说明，包括请求方式、URL、请求参数和返回参数等信息。

## 基础信息

- 基础URL: `http://localhost:8080`
- 所有接口返回格式统一为：

```json
{
  "code": 200,  // 状态码，200表示成功，其他值表示失败
  "message": "success",  // 消息，成功为"success"，失败时为错误信息
  "data": {}  // 返回的数据，具体格式根据接口不同而不同
}
```

## 1. 标签管理

### 1.1 获取标签列表

获取系统中所有的标签信息。

- **URL**: `/api/tags`
- **方法**: GET
- **请求参数**: 无
- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "餐饮",
      "inoutType": 1,  // 1-支出,2-入账,3-不计入收支
      "tagType": 2,    // 1-支付方式,2-账单类型
      "accountType": null, // 1-储蓄账户,2-信用账户
      "icon": "food_icon",
      "userId": 1
    },
    {
      "id": 2,
      "name": "交通",
      "inoutType": 1,
      "tagType": 2,
      "accountType": null,
      "icon": "transport_icon",
      "userId": 1
    },
    {
      "id": 3,
      "name": "支付宝",
      "inoutType": 3,
      "tagType": 1,
      "accountType": 1,
      "icon": "alipay_icon",
      "userId": 1
    }
  ]
}
```

### 1.2 获取标签详情

根据ID获取特定标签的详细信息。

- **URL**: `/api/tags/{id}`
- **方法**: GET
- **路径参数**:
  - `id`: 标签ID
- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "餐饮",
    "inoutType": 1,
    "tagType": 2,
    "accountType": null,
    "icon": "food_icon",
    "userId": 1
  }
}
```

### 1.3 创建标签

创建一个新的标签。

- **URL**: `/api/tags`
- **方法**: POST
- **请求体**:

```json
{
  "name": "住宿",
  "inoutType": 1,
  "tagType": 2,
  "accountType": null,
  "icon": "house_icon",
  "userId": 1
}
```

- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 4,
    "name": "住宿",
    "inoutType": 1,
    "tagType": 2,
    "accountType": null,
    "icon": "house_icon",
    "userId": 1
  }
}
```

### 1.4 更新标签

根据ID更新标签信息。

- **URL**: `/api/tags/{id}`
- **方法**: PUT
- **路径参数**:
  - `id`: 标签ID
- **请求体**:

```json
{
  "name": "住房",
  "inoutType": 1,
  "tagType": 2,
  "accountType": null,
  "icon": "house_icon",
  "userId": 1
}
```

- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

### 1.5 删除标签

根据ID删除标签。

- **URL**: `/api/tags/{id}`
- **方法**: DELETE
- **路径参数**:
  - `id`: 标签ID
- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

## 2. 账单管理

### 2.1 查询账单列表

根据条件查询用户的账单列表。

- **URL**: `/api/bills/query`
- **方法**: POST
- **请求体**:

```json
{
  "userId": 1,
  "month": "2023-04", // 可选，按月份筛选
  "inoutType": 1 // 可选，1-支出,2-入账,3-不计入收支
}
```

- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "userId": 1,
      "amount": 100.50,
      "billDate": "2023-04-01 12:00:00",
      "desc": "午餐费用",
      "inoutType": 1,
      "createTime": "2023-04-01 12:05:30",
      "updateTime": "2023-04-01 12:05:30"
    },
    {
      "id": 2,
      "userId": 1,
      "amount": 30.00,
      "billDate": "2023-04-01 18:30:00",
      "desc": "公交车费",
      "inoutType": 1,
      "createTime": "2023-04-01 18:35:20",
      "updateTime": "2023-04-01 18:35:20"
    }
  ]
}
```

### 2.2 获取账单详情

根据ID获取账单详情。

- **URL**: `/api/bills/{id}`
- **方法**: GET
- **路径参数**:
  - `id`: 账单ID
- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "userId": 1,
    "amount": 100.50,
    "billDate": "2023-04-01 12:00:00",
    "desc": "午餐费用",
    "inoutType": 1,
    "createTime": "2023-04-01 12:05:30",
    "updateTime": "2023-04-01 12:05:30"
  }
}
```

### 2.3 创建账单

创建一个新的账单。

- **URL**: `/api/bills`
- **方法**: POST
- **请求体**:

```json
{
  "userId": 1,
  "amount": 200.75,
  "billDate": "2023-04-02 09:30:00",
  "desc": "超市购物",
  "inoutType": 1,
  "tagIds": [1, 3]  // 标签ID列表
}
```

- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 3,
    "userId": 1,
    "amount": 200.75,
    "billDate": "2023-04-02 09:30:00",
    "desc": "超市购物",
    "inoutType": 1,
    "createTime": "2023-04-02 09:35:12",
    "updateTime": "2023-04-02 09:35:12"
  }
}
```

### 2.4 更新账单

根据ID更新账单信息。

- **URL**: `/api/bills/{id}`
- **方法**: PUT
- **路径参数**:
  - `id`: 账单ID
- **请求体**:

```json
{
  "userId": 1,
  "amount": 220.50,
  "billDate": "2023-04-02 09:30:00",
  "desc": "超市购物（修改）",
  "inoutType": 1,
  "tagIds": [2, 4]  // 可选，新的标签ID列表
}
```

- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

### 2.5 删除账单

根据ID删除账单。

- **URL**: `/api/bills/{id}`
- **方法**: DELETE
- **路径参数**:
  - `id`: 账单ID
- **成功响应**:

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

## 3. 错误响应

所有接口在发生错误时，都会返回统一格式的错误信息：

```json
{
  "code": 500,  // 错误代码，通常为500表示服务器错误，400表示客户端错误
  "message": "错误信息描述",  // 详细的错误信息
  "data": null
}
```

## 4. 状态码说明

- `200`: 请求成功
- `400`: 请求参数错误
- `404`: 资源不存在
- `500`: 服务器内部错误

## 5. 接口调试

### Postman 导入说明

1. 打开 Postman
2. 点击 `File` -> `Import`
3. 选择 `Link` 选项卡
4. 输入本文档的URL或直接导入本文档
5. 点击 `Import`

### 环境变量设置

建议在 Postman 中设置以下环境变量，方便接口调试：

- `baseUrl`: API的基础URL，例如 `http://localhost:8080` 