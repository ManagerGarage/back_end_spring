# API Documentation - Manager Garage System

## Base URL
```
http://localhost:8080/api
```

## Authentication
Hệ thống sử dụng JWT token cho authentication. Token cần được gửi trong header `Authorization` với format:
```
Authorization: Bearer <token>
```

---

## 1. Authentication APIs

### 1.1 Đăng ký tài khoản
**POST** `/auth/register`

**Request Body:**
```json
{
  "username": "string",
  "password": "string",
  "role": "DRIVER" | "ADMIN",
  "name": "string",
  "phone": "string",
  "dayBirth": "string",
  "address": "string",
  "license": "string"
}
```

**Response:**
```json
{
  "token": "jwt_token_string",
  "user": {
    "username": "string",
    "email": "string",
    "fullName": "string",
    "role": "string",
    "phone": "string",
    "address": "string",
    "dayBirth": "string",
    "license": "string",
    "statusDriver": "string"
  }
}
```

### 1.2 Đăng nhập
**POST** `/auth/login`

**Request Body:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Response:** (Tương tự như register)

---

## 2. Car Management APIs

### 2.1 Lấy danh sách tất cả xe
**GET** `/cars`

**Response:**
```json
[
  {
    "id": "number",
    "licensePlateNumber": "string",
    "typeCar": {
      "id": "number",
      "name": "string"
    },
    "statusCar": {
      "id": "number",
      "name": "string"
    },
    "createDay": "datetime"
  }
]
```

### 2.2 Tạo xe mới
**POST** `/cars`

**Request Body:**
```json
{
  "licensePlateNumber": "string",
  "typeCarName": "string",
  "statusCarName": "string"
}
```

### 2.3 Lấy xe theo ID
**GET** `/cars/{id}`

### 2.4 Cập nhật thông tin xe
**PUT** `/cars/{id}` hoặc **POST** `/cars/{id}`

**Request Body:**
```json
{
  "licensePlateNumber": "string",
  "typeCarName": "string",
  "statusCarName": "string"
}
```

### 2.5 Xóa xe
**DELETE** `/cars/{id}`

### 2.6 Lấy xe khả dụng theo loại bằng lái
**GET** `/cars/available-by-license?licenseName=string`

### 2.7 Lấy xe khả dụng theo loại xe
**GET** `/cars/available-by-type?typeCarName=string`

---

## 3. Driver Management APIs

### 3.1 Lấy danh sách tất cả tài xế
**GET** `/drivers`

**Response:**
```json
[
  {
    "id": "number",
    "name": "string",
    "phone": "string",
    "dayBirth": "string",
    "address": "string",
    "license": {
      "id": "number",
      "name": "string"
    },
    "statusDriver": {
      "id": "number",
      "name": "string"
    },
    "user": {
      "id": "number",
      "username": "string",
      "role": "string"
    }
  }
]
```

### 3.2 Lấy tài xế theo ID
**GET** `/drivers/{id}`

### 3.3 Cập nhật thông tin tài xế
**PUT** `/drivers/{id}` hoặc **POST** `/drivers/{id}`

**Request Body:**
```json
{
  "name": "string",
  "phone": "string",
  "dayBirth": "string",
  "address": "string",
  "licenseName": "string",
  "statusDriverName": "string"
}
```

### 3.4 Xóa tài xế
**DELETE** `/drivers/{id}`

### 3.5 Lấy tài xế khả dụng theo biển số xe
**GET** `/drivers/available-by-car?licensePlateNumber=string`

---

## 4. License Management APIs

### 4.1 Lấy danh sách tất cả bằng lái
**GET** `/licenses`

**Response:**
```json
[
  {
    "id": "number",
    "name": "string"
  }
]
```

### 4.2 Lấy bằng lái theo ID
**GET** `/licenses/{id}`

### 4.3 Tạo bằng lái mới
**POST** `/licenses`

**Request Body:**
```json
{
  "name": "string"
}
```

### 4.4 Cập nhật bằng lái
**PUT** `/licenses/{id}`

**Request Body:**
```json
{
  "name": "string"
}
```

### 4.5 Xóa bằng lái
**DELETE** `/licenses/{id}`

---

## 5. Schedule Management APIs

### 5.1 Lấy danh sách tất cả lịch trình
**GET** `/schedules`

**Response:**
```json
[
  {
    "id": "number",
    "tripPurpose": "string",
    "car": {
      "id": "number",
      "licensePlateNumber": "string"
    },
    "driver": {
      "id": "number",
      "name": "string"
    },
    "departurePoint": "string",
    "destination": "string",
    "startTime": "datetime",
    "estimatedTimeEnd": "datetime",
    "actualTimeEnd": "datetime",
    "statusSchedule": {
      "id": "number",
      "name": "string"
    },
    "createDay": "datetime"
  }
]
```

### 5.2 Tạo lịch trình mới
**POST** `/schedules`

**Request Body:**
```json
{
  "tripPurpose": "string",
  "carLicensePlateNumber": "string",
  "driverName": "string",
  "departurePoint": "string",
  "destination": "string",
  "startTime": "datetime",
  "estimatedTimeEnd": "datetime"
}
```

### 5.3 Hoàn thành lịch trình
**POST** `/schedules/complete`

**Request Body:**
```json
{
  "scheduleId": "number",
  "actualTimeEnd": "datetime"
}
```

---

## 5.4 Tự động cập nhật trạng thái lịch trình

Hệ thống tự động cập nhật trạng thái lịch trình mỗi phút:

### **Logic tự động:**
1. **Khi đến startTime** → Chuyển sang "Đang thực hiện nhiệm vụ" + Cập nhật xe/tài xế sang "Đang công tác"
2. **Khi quá estimatedTimeEnd + 30 phút** → Chuyển sang "Đã hoàn thành nhiệm vụ" + Cập nhật xe/tài xế về "Rảnh"

### **API quản lý trạng thái:**

#### Trigger cập nhật thủ công
**POST** `/schedule-status/trigger-update`

#### Xem thống kê trạng thái
**GET** `/schedule-status/summary`

**Response:**
```json
{
  "statusCounts": {
    "Chưa tới giờ thực hiện nhiệm vụ": 5,
    "Đang thực hiện nhiệm vụ": 2,
    "Đã hoàn thành nhiệm vụ": 10
  },
  "totalSchedules": 17,
  "success": true
}
```

#### Xem lịch trình theo trạng thái
**GET** `/schedule-status/by-status/{statusName}`

---

## 6. Type Car Management APIs

### 6.1 Lấy danh sách tất cả loại xe
**GET** `/type-cars`

**Response:**
```json
[
  {
    "id": "number",
    "name": "string"
  }
]
```

### 6.2 Tạo loại xe mới
**POST** `/type-cars?name=string`

---

## 7. Status Car Management APIs

### 7.1 Lấy danh sách tất cả trạng thái xe
**GET** `/status-cars`

**Response:**
```json
[
  {
    "id": "number",
    "name": "string"
  }
]
```

---

## 8. Vehicle License Mapping APIs

### 8.1 Lấy danh sách tất cả mapping xe-bằng lái
**GET** `/vehicle-license-mappings`

**Response:**
```json
[
  {
    "id": "number",
    "typeCar": {
      "id": "number",
      "name": "string"
    },
    "license": {
      "id": "number",
      "name": "string"
    }
  }
]
```

### 8.2 Tạo mapping mới
**POST** `/vehicle-license-mappings`

**Request Body:**
```json
{
  "typeCarName": "string",
  "licenseName": "string"
}
```

---

## 9. Driver Info APIs

### 9.1 Lấy danh sách tất cả bằng lái
**GET** `/driver/licenses`

### 9.2 Lấy danh sách tất cả trạng thái tài xế
**GET** `/driver/status-drivers`

### 9.3 Lấy thông tin tài xế theo username
**GET** `/driver/info?username=string`

---

## 10. Time Management APIs

### 10.1 Lấy thời gian Việt Nam hiện tại
**GET** `/time/vietnam`

**Response:**
```json
{
  "vietnamTime": "2025-01-29 20:30:45",
  "systemTime": "2025-01-29T20:30:45.123",
  "utcTime": "2025-01-29T13:30:45.123",
  "vietnamZone": "Asia/Ho_Chi_Minh",
  "systemZone": "Asia/Ho_Chi_Minh"
}
```

### 10.2 Test validation thời gian
**GET** `/time/test-validation`

**Response:**
```json
{
  "currentTime": "2025-01-29 20:30:45",
  "pastTime": "2025-01-29 19:30:45",
  "futureTime": "2025-01-29 21:30:45",
  "isPastValid": false,
  "isFutureValid": true
}
```

---

## Error Responses

Tất cả API có thể trả về các lỗi sau:

### 400 Bad Request
```json
{
  "timestamp": "datetime",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation error message",
  "path": "/api/endpoint"
}
```

### 404 Not Found
```json
{
  "timestamp": "datetime",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found",
  "path": "/api/endpoint"
}
```

### 409 Conflict
```json
{
  "timestamp": "datetime",
  "status": 409,
  "error": "Conflict",
  "message": "Resource already exists",
  "path": "/api/endpoint"
}
```

### 403 Forbidden
```json
{
  "timestamp": "datetime",
  "status": 403,
  "error": "Forbidden",
  "message": "Driver license mismatch",
  "path": "/api/endpoint"
}
```

---

## Notes

1. **Authentication**: Tất cả API đều được cấu hình để cho phép truy cập mà không cần authentication (permitAll), nhưng hệ thống vẫn hỗ trợ JWT token.

2. **Validation**: Các request body có validation annotations, đảm bảo dữ liệu đầu vào hợp lệ.

3. **DateTime Format**: Sử dụng ISO 8601 format cho các trường datetime.

4. **Timezone**: Hệ thống sử dụng múi giờ Việt Nam (Asia/Ho_Chi_Minh - UTC+7) cho tất cả các trường thời gian.

5. **CORS**: API đã được cấu hình để hỗ trợ CORS cho tất cả origins.

6. **Database**: Hệ thống sử dụng MySQL database với connection string được cấu hình trong `application.properties`. 