# Parcel Delivery Spring Boot API

## Run
mvn spring-boot:run

## Test
mvn test

## APIs
POST /api/parcels
GET /api/parcels
GET /api/parcels/{id}
GET /api/parcels/tracking/{trackingNumber}
PATCH /api/parcels/{id}/status
POST /api/parcels/{id}/cancel

Create body:
{"senderName":"Alice","receiverName":"Bob","deliveryAddress":"Singapore"}

Status body:
{"status":"PICKED_UP"}
