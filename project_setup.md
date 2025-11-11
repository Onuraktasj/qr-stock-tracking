# QR Stok Takip – Monorepo Setup (Cursor)
> Hedef: Flutter (mobil) + Spring Boot (sunucu) + PostgreSQL temelli, QR kodlu stok takibi MVP’sini tek komutla kurmak.

[Bu dosya, Cursor editöründe `@cursor implement everything from project_setup.md` komutu ile çalıştırılacak.]

---

## Monorepo Yapısı
qr-stock-tracking/
  mobile/ (Flutter 3.x app)
  server/ (Spring Boot 3.x)
  .github/workflows/
  docker-compose.yml
  README.md

---

## Backend
Spring Boot 3.x + Java 21 + PostgreSQL + Flyway + JWT Security + RBAC
Entities: ProductType, Hall, Machine, Package, StockLedger, Order, OrderLine, Allocation, User

Servisler:
- ShortCodeService: UUID -> Base32 + mod97 checksum -> 4-4-4 format
- HMACService: sign(payload, secret)
- QrPayloadFactory: {pkg, pt, pcs, len, dt, sig}

API Endpoints:
- /auth/login
- /lookups
- /packages
- /stock/in|move|out|count|adjust
- /orders, /allocations
- /reports/stock

Dockerfile + docker-compose (PostgreSQL + server)
409 concurrency koruması OUT işlemlerinde.

---

## Frontend
Flutter 3.x (Android+iOS)
Paketler: mobile_scanner, dio, drift, riverpod, freezed, json_serializable, connectivity_plus

Ekranlar:
Login, Home, IN, MOVE, OUT, COUNT, ALLOC, Reports
Offline Kuyruk: Drift tablosu pending_txn, FIFO, backoff retry
Dio API Client: JWT, retry
UI: Basit, büyük butonlar, 2-3 adımda işlem.

---

## CI/CD
GitHub Actions
- server.yml: Maven verify, Docker build
- mobile.yml: flutter analyze/test/build