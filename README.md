# 🚚 Package Delivery System

A Java console application that **calculates delivery costs** and **estimates delivery times** for packages using a fleet of vehicles.  
This repo contains a small but complete implementation (pricing, coupon rules, scheduling) built as a coding challenge. It emphasizes correct arithmetic (`BigDecimal`), modular design, unit testing, and reproducible CLI usage.

---

## Overview

- Input: base delivery cost, package list (id, weight, distance, coupon code), and (for the second challenge) vehicle fleet info.  
- Output: per-package discount, total delivery cost, and estimated delivery time (hours).  
- Algorithms:
  - **Shipment selection:** 0/1 knapsack (max number of packages per trip, tie-breaker = heavier weight) or greedy fallback for large capacities.
  - **Vehicle scheduling:** priority queue ordered by earliest vehicle availability (min-heap). Vehicles travel at fixed speed and return to base, enabling multiple trips.
  - **Precision:** `BigDecimal` used for money/time arithmetic; careful rounding/truncation rules applied for display.

---

## Features

- Compute delivery price: base rate + weight x 10 + distance x 5 (configurable in code).
- Apply coupons (OFR001, OFR002, OFR003) based on distance/weight ranges, Also you can introduce new coupons.
- Schedule deliveries across multiple vehicles (respecting payload capacity and speed).
- Estimate per-package delivery times (hours) considering trip start times and travel times.

---

## 🛠 Requirements

- Java 24.0.2 (OpenJDK / Eclipse Temurin) — or any modern JDK (11+ will work for most code).
- Maven 3.6+ (build and test)
- Git (recommended)
- Recommended IDE: IntelliJ IDEA

---

## Build & Test

Clone the repository:

```bash
git clone https://github.com/sominshah/PackageDeliverySystem.git
cd PackageDeliverySystem
```

Run unit tests:

```bash
mvn clean test
```

Build a runnable (fat) JAR (example uses Maven Shade plugin; see `pom.xml` or instructions below):

```bash
mvn clean package
# artifact: target/<artifact>-<version>-shaded.jar or target/pcs-1.0-SNAPSHOT.jar
```

Run (example — second challenge using sample input file):

```bash
java -jar target/pcs-1.0-SNAPSHOT.jar < examples/input-second-challenge.txt
```

Or run via Maven exec plugin:

```bash
mvn compile exec:java -Dexec.mainClass="com.product.delivery.system.App"
```

---

## Coverage
[![Codecov](https://codecov.io/gh/sominshah/PackageDeliverySystem/branch/master/graph/badge.svg?token=917342f3-478e-4935-a8f7-2b696ecf1165)](https://codecov.io/gh/sominshah/PackageDeliverySystem)

---

## Example Input (Second Challenge)

```
100 5
PKG1 50 30 OFR001
PKG2 75 125 OFFR0008
PKG3 175 100 OFFR003
PKG4 110 60 OFR002
PKG5 155 95 NA
2 70 200
```

### Expected sample output (one valid expected result; implementation details of scheduling may vary slightly):

```
PKG1 0 750 3.98
PKG2 0 1475 1.78
PKG3 0 2350 1.42
PKG4 105 1395 0.85
PKG5 0 2125 4.19
```

> Note: Exact display formatting or rounding may differ depending on small implementation choices (rounding mode, display precision). The important part is that cost and times use correct arithmetic and consistent rounding.

---

## Design Notes & Decisions

- **Why `BigDecimal`?** Monetary and fractional-hour arithmetic require decimal precision without float rounding errors. We use explicit scales and `RoundingMode` for display vs internal calculations.
- **Knapsack vs Greedy:** Exact DP knapsack maximizes package count with a weight-based tiebreaker. For large capacities (where DP would allocate too much memory/time), a greedy fallback is used — this keeps runtime predictable.
- **Vehicle scheduling:** Vehicles are managed by a min-heap keyed on availability time. Each trip chooses the set of packages to load (subject to capacity) and calculates the trip's round-trip time (2 × farthest distance / speed). Delivery time for each package is `tripStart + distance / speed`.
- **Separation of concerns:** pricing logic is in `DeliveryCostCalculator`, coupon logic in `CouponService`, scheduling in `DeliveryScheduler`, and models (Product/Vehicle) in `models/`.

---

##  Tests & Coverage

- Tests live in `src/test/java/...`. Run with:

```bash
mvn test
```

---


## 🧩 Project Structure

```
src/
 ├── main/java/com/product/delivery/system/
 │    ├── App.java
 │    ├── ProductDeliverySystem.java
 │    ├── features/
 │    │   ├── pricing/
 │    │   └── delivery/
 │    ├── interfaces/
 │    ├── models/
 │    └── services/
 └── test/java/com/product/delivery/system/
      └── (unit tests)
examples/
 ├── input-first-challenge.txt
 └── input-second-challenge.txt
README.md
```

---

### 🔹 Scalability

The `PackageDeliverySystem` is designed with **scalability in mind**, both in terms of features and performance:

1. **Modular Architecture:**

   * Each component (ProductService, VehicleService, DeliveryScheduler, DeliveryCostCalculator) is **decoupled**, making it easy to extend or replace modules without affecting the rest of the system.

2. **Concurrent Vehicle Scheduling:**

   * The system supports multiple vehicles operating simultaneously, allowing **parallel deliveries** and efficient utilization of resources.

3. **Extensible Coupon & Pricing Engine:**

   * New discount rules or delivery rates can be added without changing existing code, supporting **future growth in business logic**.

4. **Data Handling:**

   * Designed to handle increasing numbers of products and vehicles, thanks to **dynamic data structures** and **efficient scheduling algorithms**.

5. **Future Enhancements:**

   * Can be extended to integrate with databases, web services for **large-scale operations**, including thousands of simultaneous deliveries.

---

## 💬 Contribution & Contact

If you'd like to improve or extend the project (new coupon rules, real maps integration, REST API), please file a PR or issue on the repo.

**Author:** Somin Ali — https://github.com/sominshah  
Email: ssssomin4@gmail.com

---
