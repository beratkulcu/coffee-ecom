# CoffeeCommerce

CoffeeCommerce, mikroservis mimarisi ile geliştirilen kapsamlı ve profesyonel bir B2C e-ticaret altyapısıdır. Bu projede kahveye dair her şey (kahve çekirdeği, öğütülmüş kahve, ekipman, bardak, abonelik vb.) satılabilecektir. Proje, sıfırdan kurumsal düzenle geliştirilmekte ve her parçası profesyonel mimari ilkelerle planlanmaktadır.

## 🚀 Amaç
Gerçek dünya senaryolarına uygun bir e-ticaret sistemi kurarak; Spring Boot, Docker, Kafka, Redis, Elasticsearch gibi teknolojilerle ölçeklenebilir, bakımı kolay, test edilebilir ve güvenli bir yapı oluşturmak.

---

## 📚 Proje Kapsamı

### 📊 Mikroservisler
- `auth-service`: Kimlik doğrulama, JWT, refresh token, roller
- `user-service`: Kullanıcı profili, adresler, yetkiler
- `product-service`: Ürün, kategori, stok, varyant, Elasticsearch entegrasyonu
- `order-service`: Sipariş, sepet, ödeme
- `coupon-service`: Kampanya, kupon kodu
- `notification-service`: Mail, SMS, push
- `config-server`: Ortak konfigürasyon merkezi
- `gateway-server`: API geçidi, rate-limit, JWT filtre
- `discovery-server`: Servis kaydı ve keşfetme (Eureka)

---

## 🚧 Altyapı Teknolojileri
- Java 17
- Spring Boot 3.4.x
- Spring Cloud 2024.x
- PostgreSQL (Docker - 5432)
- Redis (Docker - 6379)
- Kafka + Zookeeper (Docker - 9092/2181)
- Elasticsearch 8 (Docker - 9200)
- Docker Compose
- Maven
- Lombok, JPA, Validation, Spring Security
- Prometheus, Grafana, Zipkin (ileride eklenecek)

---

## 💼 Temel Yapı Taşları

### 🔖 `AbstractBaseEntity`
Tüm entity'lerin extend edeceği base class:
- createdBy
- createdDate
- lastModifiedBy
- lastModifiedDate

### 🔢 `ApiResponse<T>`
Tüm controller dönüşlerinde kullanılacak ortak response yapısı.

### ❌ `Validations`
Sabit hata mesajlarını içeren final class.

### 🧱 `GlobalExceptionHandler`
Hataları tek noktadan kontrol altına alan yapı.

### ⚖️ `Util`
Regex, telefon numarası doğrulama, email formatı gibi şablon fonksiyonlar için ortak yardımcı class.

---

## 🔑 Roller ve Erişim

3 ana rol bulunur:
- `ROLE_ADMIN`: Admin panel ve yetkili operasyonlar
- `ROLE_USER`: Standart B2C müşteri
- `ROLE_SELLER`: Satıcılar, ilan yükleyenler

JWT token'lar bu rollere göre üretilir ve endpoint bazlı filtrelenir.

---

## 📆 Token Sistemi
- JWT ile kimlik doğrulama
- Refresh token sistemi
- Her kullanıcı için JWT ve Refresh Token ayrı tabloda tutulur

---

## 🚀 Kurulum & Çalıştırma

```bash
# Docker ortamını başlat
cd coffee-ecom
docker-compose up -d

# auth-service'i localde çalıştır
cd services/auth-service
mvn spring-boot:run
```

---

## 📊 Yol Haritası
- [x] Monorepo dizin yapısı oluşturuldu
- [x] Docker servisleri ayağa kaldırıldı (Postgres, Redis, Kafka, Elastic)
- [x] Base entity yapısı kuruldu
- [x] Rollere göre mimari şekillendirildi
- [ ] AuthService - register/login endpointleri
- [ ] JWT token üretimi ve refresh mantığı
- [ ] User-service bağlantısı
- [ ] Role bazlı endpoint yetkilendirme
- [ ] Admin panel servisleri
- [ ] Product + Order + Notification yapılarının tamamlanması

---

## 🚀 Katkı
Kod yapısı profesyonel mikroservis mimarisine uygun şekilde inşaa edilmektedir. Pull request açmadan önce lütfen kod standartlarına uyunuz ve testlerinizi ekleyiniz.

---

Hazırlayan: Berat Kulcu
