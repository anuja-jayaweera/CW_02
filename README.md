# CW_02 — Malabe Tuk-Tuk Spares Depot

JavaFX desktop application for managing inventory, dealers, stock alerts, and sales at a three-wheeler spare parts depot.

## Overview

This coursework project (CW_02) builds a spare-parts management system that:

- Cleans messy legacy text files into a consistent format
- Manages inventory (add, update, delete, view)
- Searches items by category, price range, and keyword
- Monitors low-stock items against a threshold
- Selects random dealers and sorts them by location
- Supports a Point of Sale (POS) cart with discounts and audit logging

## Tech Stack

| Component | Details |
|-----------|---------|
| Language | Java 9+ |
| UI | JavaFX 21 (Controls + FXML) |
| Build | Maven (`pom.xml`, Maven Wrapper) |
| Testing | JUnit 5.12.1 |

## How to Run

### Prerequisites

- JDK 9 or newer (JavaFX 21 works best with JDK 17+)
- Maven, or use the included wrapper (`mvnw` / `mvnw.cmd`)

### Launch the app

From the project root (`CW_02`):

```bash
# Windows
.\mvnw.cmd clean javafx:run

# macOS / Linux
./mvnw clean javafx:run
```

Main entry point: `com.example.cw_02.Applications.MainApp`  
(Alternative launcher: `com.example.cw_02.Applications.AppLauncher`)

### Run tests

```bash
.\mvnw.cmd test
```

## Features

### 1. Data Cleaning
Converts inconsistent legacy files into cleaned pipe-separated records:

| Input | Output |
|-------|--------|
| `inventory_legacy.txt` | `inventory_cleaned.txt` |
| `dealers_legacy.txt` | `dealers_cleaned.txt` |

Cleaning handles mixed separators (`,`, `;`, `|`), missing values, `Rs.` price prefixes, category casing, and multiple date formats.

### 2. Inventory Management
- **Add** a new part (blocks duplicate codes)
- **Update** item details
- **Delete** an item by code
- **View** inventory in a table

Cleaned inventory format:

```text
Code|Name|Brand|Price|Quantity|Category|Date|Image|Threshold
```

Example:

```text
P001|Bajaj 4-Stroke Piston|Bajaj|4500.00|15|Engine|2023-10-12|piston4s.jpg|10
```

### 3. Multi-Criteria Search
Find parts that match **all** of:

- Category
- Price range (min–max)
- Keyword in the item name

### 4. Low Stock Monitoring
Flags items where quantity is below the configured threshold (default: 10).

### 5. Random Dealer Selection
Picks up to 4 dealers from `dealers_cleaned.txt` and sorts them by location.

Cleaned dealer format:

```text
Code|Name|Phone|Location
```

### 6. POS Cart
Checkout flow with:

- Quantity and stock validation
- **5% bulk discount** when quantity ≥ 3
- **10% synergy discount** when the cart includes both Engine and Electrical items
- Transaction receipt and audit logging

## Project Structure

```text
CW_02/
├── pom.xml
├── inventory_legacy.txt / inventory_cleaned.txt
├── dealers_legacy.txt / dealers_cleaned.txt
├── images/                          # Part images
├── flowchart_dark.svg / .json       # DataCleaner flowchart
└── src/
    ├── main/java/com/example/cw_02/
    │   ├── Applications/            # MainApp, AppLauncher
    │   ├── Controllers/             # FXML controllers
    │   └── classes/                 # Business logic
    │       ├── DataCleaner.java
    │       ├── InventoryManager.java
    │       ├── InventoryStore.java
    │       ├── PartItem.java
    │       ├── MultiCriteriaSearch.java
    │       ├── LowStock.java
    │       ├── RandomDealers.java
    │       ├── POSCart.java
    │       └── AuditLogger.java
    ├── main/resources/com/example/cw_02/   # FXML views
    └── test/java/...                       # JUnit tests
```

## Main Menu

The home screen opens these modules:

1. Clean Legacy Data files  
2. Add An Item To the Inventory  
3. Delete An Item From the Inventory  
4. Update Item Details  
5. View Inventory  
6. Search for an item in the inventory  
7. Check items on low stock  
8. Select Random Dealers  

## Notes

- Work with the **cleaned** text files at the project root after running the data cleaner.
- Part images live under `images/` and are referenced by filename in inventory records.
- `flowchart_dark.svg` documents the `DataCleaner` inventory/dealers pipelines.

## Author

Anuja Jayaweera

